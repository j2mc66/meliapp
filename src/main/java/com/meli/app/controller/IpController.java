package com.meli.app.controller;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meli.app.model.BlackListIp;
import com.meli.app.service.BlackListIpService;
import com.meli.app.service.IpService;
import com.meli.app.util.Constants;

/**
 * @author James
 * @version: 2021/06/15
 */
@RestController
@RequestMapping("ip")
@Validated
public class IpController {
	
	private final IpService ipService;
	private final BlackListIpService blackListIpService;
	
	public IpController(IpService ipService, BlackListIpService blackListIpService) {
		this.ipService = ipService;
		this.blackListIpService = blackListIpService;
	}	

	/**
	 * Method that returns the IP information
	 * @param ip to find
	 * @return The IP information
	 */
	@GetMapping
	public IpInfoResponse get(
			@Valid @Pattern(regexp = Constants.IP_PATTERN, message = "El parametro ingresado no es una ip valida") @RequestParam String ip) {
		return ipService.get(ip);
	}
	
	/**
	 * Method that save the IP in back list
	 * @param blackListIp to save
	 * @return blackListIp with IP
	 */
	@PostMapping
	public BlackListIp saveIpBlackList(@Valid @RequestBody BlackListIp blackListIp  ) {
		return blackListIpService.save(blackListIp);
	}

}