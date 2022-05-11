package com.nttdata.account.model.dto;

import java.time.LocalDateTime;

import com.nttdata.account.model.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    private String id;
    private String accountNumber;
    private String currency;
    private String accountType;
    private LocalDateTime operationDate = LocalDateTime.now();
    private ClientDto client;
    
    
    public AccountDto (Account account) {
    	
    	super();
    	this.id = account.getId();
    	this.accountNumber = account.getAccountNumber();
    	this.currency = account.getCurrency();
    	this.accountType = account.getCurrency();
    	this.operationDate = account.getOperationDate();
    	this.client = new ClientDto(account.getClient());
    }
    
    public Account asAccount() {
    	 return new Account(id,accountNumber,currency,accountType,operationDate, client.asClient());
    }
}
