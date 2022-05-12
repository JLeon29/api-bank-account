package com.nttdata.account.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Firmante {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String dni;

    private String phone;

}