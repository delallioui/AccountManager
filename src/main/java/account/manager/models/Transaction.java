package account.manager.models;

public class Transaction {
	private int amount;
	private String direction;
	private String thirdPartyId;
	
	
	
	public Transaction(int amount, String direction, String thirdPartyId) {
		super();
		this.amount = amount;
		this.direction = direction;
		this.thirdPartyId = thirdPartyId;
	}
	public String getThirdPartyId() {
		return thirdPartyId;
	}
	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
