package com.meli.app.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.meli.app.model.BlackListIp;
import com.meli.app.repository.BlackListIpRepository;

/**
 * @author James
 * @version: 2021/06/15
 */
public class BlackListServiceTest {

	private BlackListIpRepository blackListIpRepository = Mockito.mock(BlackListIpRepository.class);

	private BlackListIpService blackListIpService;

	@Before
	public void initUseCase() {
		blackListIpService = new BlackListIpService(blackListIpRepository);
	}

	@Test
	public void existsBlackListIp() {
		
		when(blackListIpRepository.existsById("5.6.7.8")).thenReturn(true);

		boolean exists = blackListIpService.existsById("5.6.7.8");
		assertThat(exists, is(true));
	}
	
	@Test
	public void saveTest() {
		
		BlackListIp blackListIp = new BlackListIp("5.6.7.8");
		when(blackListIpRepository.save(blackListIp)).thenReturn(blackListIp);

		BlackListIp blackListIpSaved = blackListIpService.save(blackListIp);
		assertThat(blackListIpSaved.getIp(), is("5.6.7.8"));
	}
}
