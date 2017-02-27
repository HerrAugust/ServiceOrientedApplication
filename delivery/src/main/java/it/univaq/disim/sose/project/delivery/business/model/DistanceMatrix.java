package it.univaq.disim.sose.project.delivery.business.model;

/**
 * Represents the Google Map Matrix object
 * It is used for JSON deserialization *
 */

public class DistanceMatrix {
	private String[] origin_addresses;
	private String[] destination_addresses;
	private Row[] rows;
	private String status;
	
	public String[] getOrigins() {
		return origin_addresses;
	}
	
	public void setOrigins(String[] origins) {
		this.origin_addresses = origins;
	}
	
	public String[] getDestinations() {
		return destination_addresses;
	}
	
	public void setDestinations(String[] destinations) {
		this.destination_addresses = destinations;
	}
	
	public Row[] getRows() {
		return rows;
	}
	
	public void setRows(Row[] rows) {
		this.rows = rows;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Distance Matrix");
		sb.append("\nStatus: " + this.status);
		sb.append("\nOrigins:" );
		for(String origin: this.origin_addresses)
			sb.append(" " + origin);
		sb.append("\nDestinations:" );
		for(String destination: this.destination_addresses)
			sb.append(" " + destination);
		for(Row row: this.rows)
			sb.append("\n" + row);
		
		return sb.toString();
	}
}
