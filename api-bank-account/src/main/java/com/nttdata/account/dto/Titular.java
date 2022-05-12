package com.nttdata.account.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Titular {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String dni;

    private String debitCard;


}