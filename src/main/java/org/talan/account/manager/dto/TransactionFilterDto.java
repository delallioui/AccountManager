package org.talan.account.manager.dto;

import org.talan.account.manager.models.Directions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionFilterDto {

	private String accountId;
	private Directions direction;
}
