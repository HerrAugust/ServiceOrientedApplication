package it.univaq.disim.sose.project.shop.business.model;

public class Purchase {
	private Authentication account;
	private int itemID;
	private int quantity;
	
	public Authentication getAccount() {
		return account;
	}
	
	public void setAccount(Authentication account) {
		this.account = account;
	}
	
	public int getItemID() {
		return itemID;
	}
	
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
