package com.nttdata.account.model.dto;

import com.nttdata.account.model.Client;
import com.nttdata.account.model.ClientType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
	
	private String id;
	private String name;
	private String email;
	private String phone;
	private String address;
	private ClientType clientType;
	
	
	public ClientDto (Client client) {
		super();
		id =  client.getId();
		name = client.getName();
		email = client.getEmail();
		phone = client.getAddress();
		address= client.getAddress();
		clientType = client.getClientType();
		
		
	}
	
	public Client asClient() {
		
		return new Client(id,name,email,phone,address,clientType);
	}

}
