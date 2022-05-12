package com.nttdata.account.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nttdata.account.dto.CustomerDTO;
import com.nttdata.account.dto.Titular;
import com.nttdata.account.models.Account;

public class AccountCreator {
	
	public static Account createValidAccount(){
		CustomerDTO c = new CustomerDTO("Luis Olivera Vasquez", "TP-01", "77380599");
        Titular t = new Titular("Jose Perez", "71710334", "1223-1223-1223-4655");
        List<Titular> lst = new ArrayList<>();
        lst.add(t);

        return Account.builder()
                .id("qw123b4i3382")
                .typeOfAccount("CURRENT_ACCOUNT")
                .accountNumber("123456")
                .amount(1000.0)
                .maxLimitMovementPerMonth(5)
                .commission(10.0)
                .movementPerMonth(0)
                .customerIdentityNumber("71720332")
                .minAmountAveragePerMonth(20.0)
                .amountAveragePerMonth(100.0)
                .customer(c)
                .titulares(lst)
                .dateOperation(LocalDateTime.now())
                .build();
    }

    public static Account createAccountToBeSaved(){
    	CustomerDTO c = new CustomerDTO("Luis Olivera Vasquez", "TP-01", "77380599");
        Titular t = new Titular("Jose Perez", "71710334", "1223-1223-1223-4655");
        List<Titular> lst = new ArrayList<>();
        lst.add(t);

        return Account.builder()
                .id("qw123b4i3382")
                .typeOfAccount("CURRENT_ACCOUNT")
                .accountNumber("123456")
                .amount(1000.0)
                .maxLimitMovementPerMonth(5)
                .commission(10.0)
                .movementPerMonth(0)
                .customerIdentityNumber("71720332")
                .minAmountAveragePerMonth(20.0)
                .amountAveragePerMonth(100.0)
                .customer(c)
                .titulares(lst)
                .dateOperation(LocalDateTime.now())
                .build();
    }

}
