package org.talan.account.manager.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseStat {
	
	private Stat stat;
	private String meassage;
	
	public static final ResponseStat SUCCESS = new ResponseStat(Stat.success, "success");
	
	public static final ResponseStat FAILURE(String message) {
		return new ResponseStat(Stat.failure, message);
	}
	
	public enum Stat{
		success, failure;
	}
}
