package com.nttdata.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    private String contractNumber;

    private CustomerDTO customer;

    private boolean debtor;
}
