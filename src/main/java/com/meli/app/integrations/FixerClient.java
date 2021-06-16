package com.meli.app.integrations;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author James
 * @version: 2021/06/15
 */
@FeignClient(url="http://data.fixer.io/api", name="fixer")
@Component
public interface FixerClient {

	/**
	 * Method that searches for country and country code by IP
	 * @param accessCode code to authenticate
	 * @param format
	 * @return rates from different countries
	 */
	@Cacheable("FixerResposes")
	@GetMapping("latest")
	FixerRespose getInfo(@RequestParam("access_key") String accessCode, @RequestParam String format);
}
