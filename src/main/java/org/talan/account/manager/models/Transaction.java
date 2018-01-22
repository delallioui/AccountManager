package org.talan.account.manager.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
	private Float amount;
	private String direction;
	private String thirdPartyId;
}
