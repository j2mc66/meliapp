package com.meli.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.meli.app.AppApplication;
import com.meli.app.model.BlackListIp;
import com.meli.app.repository.BlackListIpRepository;

/**
 * @author James
 * @version: 2021/06/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = AppApplication.class)
@AutoConfigureMockMvc
public class IpControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private BlackListIpRepository backListIpRepository;
	
	@Test
	public void getIpInfo_thenStatus200() throws Exception {

	    mvc.perform(get("/ip?ip=201.182.250.84")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.country", is("Colombia")))
	    	.andExpect(jsonPath("$.countryCode", is("CO")))
	    	.andExpect(jsonPath("$.currency", is("COP")));
	}
	
	@Test
	public void getIpInfo_thenStatus403() throws Exception {
		
		backListIpRepository.save(new BlackListIp("5.6.7.8"));

	    mvc.perform(get("/ip?ip=5.6.7.8")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isForbidden())
	    	.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
	        .andExpect(result -> assertEquals("403 FORBIDDEN \"Ip bloqueada\"", result.getResolvedException().getMessage()));
	}
	
	@Test
	public void getIpInfo_thenStatus400() throws Exception {
		
		backListIpRepository.save(new BlackListIp("5.6.7.8"));

	    mvc.perform(get("/ip?ip=5.6.7.888")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isBadRequest())
	    	.andExpect(jsonPath("$.message", is("get.ip: El parametro ingresado no es una ip valida")));
	}
}
