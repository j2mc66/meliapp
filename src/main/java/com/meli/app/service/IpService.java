package com.meli.app.service;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.meli.app.controller.IpInfoResponse;
import com.meli.app.integrations.CountriesClient;
import com.meli.app.integrations.CountryResponse;
import com.meli.app.integrations.FixerClient;
import com.meli.app.integrations.FixerRespose;
import com.meli.app.integrations.IpApiClient;
import com.meli.app.integrations.IpApiResponse;
import com.meli.app.util.PropertiesClientsService;

/**
 * @author James
 * @version: 2021/06/15
 */
@Service
public class IpService {

	private final BlackListIpService blackListIpService;

	private final IpApiClient ipApiClient;

	private final CountriesClient countriesClient;

	private final FixerClient fixerClient;

	private final PropertiesClientsService propertiesClientsService;

	public IpService(BlackListIpService blackListIpService, IpApiClient ipApiClient, CountriesClient countriesClient,
			FixerClient fixerClient, PropertiesClientsService propertiesClientsService) {
		this.blackListIpService = blackListIpService;
		this.ipApiClient = ipApiClient;
		this.countriesClient = countriesClient;
		this.fixerClient = fixerClient;
		this.propertiesClientsService = propertiesClientsService;
	}

	/**
	 * Method that find and organize the IP information
	 * @param ip to find
	 * @return The IP information
	 */
	public IpInfoResponse get(String ip) {
		
		//Validate if ip exists on black list
		if (blackListIpService.existsById(ip)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ip bloqueada");
		}

		IpInfoResponse ipInfoResponse = new IpInfoResponse();
		
		//Get ip info about country and country code
		IpApiResponse ipApiResponse = ipApiClient.getInfo(ip, propertiesClientsService.getIpApiAccessKey());

		if (ipApiResponse.getCountryCode() == null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Ip no valida");
		}

		ipInfoResponse.setCountry(ipApiResponse.getCountryName());
		ipInfoResponse.setCountryCode(ipApiResponse.getCountryCode());

		//Get ip currencies by country code
		CountryResponse countryResponse = countriesClient.getInfo(ipInfoResponse.getCountryCode(), "currencies");
		ipInfoResponse.setCurrency(countryResponse.getCurrencies().get(0).getCode());

		//Get all rates to select rate by currency code
		FixerRespose fixerResponse = fixerClient.getInfo(propertiesClientsService.getFixerAccessKey(), "1");
		
		if (!fixerResponse.isSuccess()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "No fue posible satisfactoria la consulta de la consulta de cotizacion actual de la moneda");
		}
		
		Map<String, Double> rates = fixerResponse.getRates();
		
		Double rate = rates.get(ipInfoResponse.getCurrency());
		
		if(rate == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No fue posible consultar la cotizacion actual de la moneda");
		}
		ipInfoResponse.setCurrencyValue(rate);

		return ipInfoResponse;
	}
}
