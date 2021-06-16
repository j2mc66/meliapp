package com.meli.app.integrations;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author James
 * @version: 2021/06/15
 */
@FeignClient(url="http://restcountries.eu/rest/v2/alpha", name="restcountries")
@Component
public interface CountriesClient {
	
	/**
	 * @param ip to find
	 * @param fields filter return fields
	 * @return currency by IP
	 */
	@Cacheable("CountryResponses")
	@GetMapping("/{ip}")
	CountryResponse getInfo(@PathVariable("ip") String ip, @RequestParam("fields") String fields);

}
