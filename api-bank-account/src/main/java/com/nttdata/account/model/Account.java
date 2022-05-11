package com.nttdata.account.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Mono;

@Document(collection = "account")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
    private String id;

    private String accountNumber;

    private String currency;

    private String accountType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationDate = LocalDateTime.now();

    public Client client;
    
    public Account(String accountNumber, String currency, String accountType, LocalDateTime operationDate) {
		super();
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.accountType = accountType;
		this.operationDate = operationDate;
	}
	
	

	
	

}
