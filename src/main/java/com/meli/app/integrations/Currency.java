package com.meli.app.integrations;

import lombok.Getter;
import lombok.Setter;

/**
 * @author James
 * @version: 2021/06/15
 */
@Getter
@Setter
public class Currency {

	private String code;
	
	private String name;
	
	private String symbol;
	
	public Currency() {
		
	}
	
	public Currency(String code, String name, String symbol ) {
		this.code = code;
		this.name = name;
		this.symbol = symbol;
	}
}
