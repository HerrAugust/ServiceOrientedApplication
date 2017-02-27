package it.univaq.disim.sose.project.currency.business.model;

public class Rates {
	private String EUR;
	private String GBP;
	private String USD;
	
	public String getEUR() {
		return EUR;
	}
	
	public void setEUR(String eUR) {
		EUR = eUR;
	}

	public String getGBP() {
		return GBP;
	}

	public void setGBP(String gBP) {
		GBP = gBP;
	}

	public String getUSD() {
		return USD;
	}

	public void setUSD(String uSD) {
		USD = uSD;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\t\t EUR=" + this.EUR + "\n");
		sb.append("\t\t USD=" + this.USD + "\n");
		sb.append("\t\t GBP=" + this.GBP + "\n");
		
		return sb.toString();
	}
	
	public String getRate(String currency) {
		switch(currency) {
			case "EUR": return this.EUR;
			case "USD": return this.USD;
			case "GBP": return this.GBP;
			default: return "?";
		}
	}
}
