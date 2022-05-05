package com.nttdata.account.app.utils;

import org.springframework.beans.BeanUtils;

import com.nttdata.account.model.Account;
import com.nttdata.account.model.dto.AccountDto;

public class AppUtils {
	
	public static AccountDto entityToDto(Account account){
		AccountDto accountDto =new AccountDto();
        BeanUtils.copyProperties(account,accountDto);
        return accountDto;
    }

    public static Account dtoToEntity(AccountDto accountDto){
    	Account account =new Account();
        BeanUtils.copyProperties(accountDto,account);
        return account;
    }

}
