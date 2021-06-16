package com.meli.app.integrations;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author James
 * @version: 2021/06/15
 */
@Getter
@Setter
public class IpApiResponse {
	
	@JsonProperty("country_code")
	private String countryCode;
	
	@JsonProperty("country_name")
	private String countryName;
	
	public IpApiResponse(){
		
	}
	
	public IpApiResponse(String countryCode, String countryName) {
		this.countryCode = countryCode;
		this.countryName = countryName;
	}

}
