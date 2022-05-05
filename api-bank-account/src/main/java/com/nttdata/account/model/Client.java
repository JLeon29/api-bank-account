package com.nttdata.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
	
	private String id;
	private String name;
	private String email;
	private String phone;
	private String address;
	private ClientType clientType;

}
