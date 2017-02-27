package it.univaq.disim.sose.project.delivery.business.model;

public class Address {
	private String street;
	private String city;
	private String country;
	
	@Override
	public String toString() {
		return this.street + " " + this.city + " " + this.country;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}	
}
