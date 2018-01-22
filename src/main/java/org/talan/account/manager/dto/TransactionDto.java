package org.talan.account.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDto {
	private String payerId;
	private Float amount;
	private String payeeId;
}
