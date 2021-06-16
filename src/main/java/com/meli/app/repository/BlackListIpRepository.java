package com.meli.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meli.app.model.BlackListIp;

/**
 * @author James
 * @version: 2021/06/15
 */
public interface BlackListIpRepository extends JpaRepository<BlackListIp, String> {

}
