package it.univaq.disim.sose.project.delivery.business.model;

public class Duration {
	private String text;
	private int value;
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "Duration " + this.text + " (" + this.value + "s)";
	}
}
