package com.nttdata.account.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection ="client")
public class Client {
	
	//@Id
	private String id;
	private String name;
	private String email;
	private String phone;
	private String address;
	private ClientType clientType;

}
