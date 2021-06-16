package com.meli.app.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import com.meli.app.controller.IpInfoResponse;
import com.meli.app.integrations.CountriesClient;
import com.meli.app.integrations.CountryResponse;
import com.meli.app.integrations.Currency;
import com.meli.app.integrations.FixerClient;
import com.meli.app.integrations.FixerRespose;
import com.meli.app.integrations.IpApiClient;
import com.meli.app.integrations.IpApiResponse;
import com.meli.app.repository.BlackListIpRepository;
import com.meli.app.util.PropertiesClientsService;

/**
 * @author James
 * @version: 2021/06/15
 */
public class IpServiceTest {

	private static final double DELTA = 1e-15;

	private BlackListIpRepository blackListIpRepository = Mockito.mock(BlackListIpRepository.class);

	private BlackListIpService blackListIpService;

	private IpApiClient ipApiClient = Mockito.mock(IpApiClient.class);

	private CountriesClient countriesClient = Mockito.mock(CountriesClient.class);

	private FixerClient fixerClient = Mockito.mock(FixerClient.class);

	private PropertiesClientsService propertiesClientsService;

	private IpService ipService;
	
	@Before
	public void initUseCase() {
		blackListIpService = new BlackListIpService(blackListIpRepository);
		propertiesClientsService = new PropertiesClientsService("123", "456");

		ipService = new IpService(blackListIpService, ipApiClient, countriesClient, fixerClient,
				propertiesClientsService);
	}

	@Test
	public void getIpInfo() {
		IpApiResponse ipApiResponse = new IpApiResponse("CO", "Colombia");

		List<Currency> currencies = new ArrayList<>();
		currencies.add(new Currency("COP", "Colombian peso", "$"));

		CountryResponse countryResponse = new CountryResponse();
		countryResponse.setCurrencies(currencies);

		FixerRespose fixerRespose = new FixerRespose();
		fixerRespose.setSuccess(true);
		fixerRespose.setTimestamp(LocalDateTime.now().getLong(ChronoField.CLOCK_HOUR_OF_DAY));
		fixerRespose.setBase("EUR");
		fixerRespose.setDate(LocalDate.now());
		fixerRespose.setRates(Map.of("COP", 4500.2, "EUR", 1.0, "USD", 1.212187));

		when(ipApiClient.getInfo(anyString(), anyString())).thenReturn(ipApiResponse);
		when(countriesClient.getInfo(anyString(), anyString())).thenReturn(countryResponse);
		when(fixerClient.getInfo(anyString(), anyString())).thenReturn(fixerRespose);

		IpInfoResponse ipInfo = ipService.get("5.6.7.8");
		Assert.assertEquals(ipInfo.getCountry(), "Colombia");
		Assert.assertEquals(ipInfo.getCountryCode(), "CO");
		Assert.assertEquals(ipInfo.getCurrency(), "COP");
		Assert.assertEquals(ipInfo.getCurrencyValue(), 4500.2, DELTA);

	}
	
	@Test
	public void getIpInfoError1() {
		
		when(blackListIpRepository.existsById("5.6.7.8")).thenReturn(true);
		
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			ipService.get("5.6.7.8");
	    });

	    String expectedMessage = "Ip bloqueada";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));

	}
	
	@Test
	public void getIpInfoError2() {
		IpApiResponse ipApiResponse = new IpApiResponse(null, null);		

		when(ipApiClient.getInfo(anyString(), anyString())).thenReturn(ipApiResponse);
		
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			ipService.get("192.168.0.1");
	    });

	    String expectedMessage = "Ip no valida";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));

	}
	
	@Test
	public void getIpInfoError3() {
		IpApiResponse ipApiResponse = new IpApiResponse("CO", "Colombia");

		List<Currency> currencies = new ArrayList<>();
		currencies.add(new Currency("COP", "Colombian peso", "$"));

		CountryResponse countryResponse = new CountryResponse();
		countryResponse.setCurrencies(currencies);

		FixerRespose fixerRespose = new FixerRespose();
		fixerRespose.setSuccess(false);
		fixerRespose.setTimestamp(LocalDateTime.now().getLong(ChronoField.CLOCK_HOUR_OF_DAY));

		when(ipApiClient.getInfo(anyString(), anyString())).thenReturn(ipApiResponse);
		when(countriesClient.getInfo(anyString(), anyString())).thenReturn(countryResponse);
		when(fixerClient.getInfo(anyString(), anyString())).thenReturn(fixerRespose);
		
		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			ipService.get("5.6.7.8");
	    });

	    String expectedMessage = "No fue posible consultar la cotizacion actual de la moneda";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));

	}
}
