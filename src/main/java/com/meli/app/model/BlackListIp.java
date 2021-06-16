package com.meli.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.meli.app.util.Constants;

import lombok.Getter;
import lombok.Setter;

/**
 * @author James
 * @version: 2021/06/15
 */
@Entity
@Table
@Getter
@Setter
public class BlackListIp {
	
	@Id
	@Pattern(regexp = Constants.IP_PATTERN, message = "El parametro ingresado no es una ip valida")
	private String ip;
	
	public BlackListIp() { }
	
	public BlackListIp(String ip) {
		this.ip = ip;
	}
}
