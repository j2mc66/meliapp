package com.meli.app.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * @author James
 * @version: 2021/06/15
 */
@Getter
@Setter
public class IpInfoResponse {

	private String country;
	
	private String countryCode;
	
	private String currency;
	
	private double currencyValue;
}
