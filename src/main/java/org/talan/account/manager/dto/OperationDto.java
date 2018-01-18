package org.talan.account.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationDto {
	private String accountId;
	private Float amount;
}
