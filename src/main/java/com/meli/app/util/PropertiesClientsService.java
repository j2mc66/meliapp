package com.meli.app.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author James
 * @version: 2021/06/15
 */
@Getter
@Setter
@Component
public class PropertiesClientsService {

	private String ipApiAccessKey;

	private String fixerAccessKey;

	public PropertiesClientsService(@Value("${ipapi.access_key}") String ipApiAccessKey,
			@Value("${fixer.access_key}") String fixerAccessKey) {
		
		this.ipApiAccessKey = ipApiAccessKey;
		this.fixerAccessKey = fixerAccessKey;

	}
}
