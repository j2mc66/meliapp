package com.meli.app.integrations;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author James
 * @version: 2021/06/15
 */
@FeignClient(url="http://api.ipapi.com/", name="ipapi")
@Component
public interface IpApiClient {
	
	/**
	 * Method that searches for country and country code by IP
	 * @param ip to find
	 * @param accessCode code to authenticate
	 * @return detail IP information
	 */
	@GetMapping("/{ip}")
	IpApiResponse getInfo(@PathVariable("ip") String ip, @RequestParam("access_key") String accessCode);

}
