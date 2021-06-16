package com.meli.app.service;

import org.springframework.stereotype.Service;

import com.meli.app.model.BlackListIp;
import com.meli.app.repository.BlackListIpRepository;

/**
 * @author James
 * @version: 2021/06/15
 */
@Service
public class BlackListIpService {
	
	private final BlackListIpRepository blackListIpRepository;
	
	public BlackListIpService(BlackListIpRepository blackListIpRepository) {
		this.blackListIpRepository = blackListIpRepository;
	}
	
	/**
	 * @param id IP to validate if exists
	 * @return if exists or no an IP
	 */
	public boolean existsById(String id) {
		return blackListIpRepository.existsById(id);
	}
	
	/**
	 * @param blackListIp IP to save
	 * @return blackListIp with IP
	 */
	public BlackListIp save(BlackListIp blackListIp) {
		return blackListIpRepository.save(blackListIp);
	}
}
