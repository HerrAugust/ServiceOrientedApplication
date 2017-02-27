package it.univaq.disim.sose.project.currency.business.model;

public class FixerResponse {
	private String base;
	private String date;
	private Rates rates;
	
	public String getBase() {
		return base;
	}
	
	public void setBase(String base) {
		this.base = base;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public Rates getRates() {
		return rates;
	}
	
	public void setRates(Rates rates) {
		this.rates = rates;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Fixer response: \n");
		sb.append("\tBase: " + this.base + "\n");
		sb.append("\tDate: " + this.date + "\n");
		sb.append("\tRates:\n" + this.rates);
		
		return sb.toString();
	}
}
