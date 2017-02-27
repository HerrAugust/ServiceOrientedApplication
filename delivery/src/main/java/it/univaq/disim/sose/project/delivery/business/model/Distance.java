package it.univaq.disim.sose.project.delivery.business.model;

public class Distance {
	private String text;
	private int value;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Distance " + this.text + " (" + this.value + "m)";
	}
}
