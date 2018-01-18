/**
 * 
 */
package org.talan.account.manager.models;

/**
 * @author Driss
 *
 */
public enum Directions {
	OUT("out"),
	IN("in");
	
	private final String code;

	private Directions(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
