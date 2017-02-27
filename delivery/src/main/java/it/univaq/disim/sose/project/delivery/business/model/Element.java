package it.univaq.disim.sose.project.delivery.business.model;

public class Element {
	private Distance distance;
	private Duration duration;
	private String status;
	
	public Distance getDistance() {
		return distance;
	}
	
	public void setDistance(Distance distance) {
		this.distance = distance;
	}
	
	public Duration getDuration() {
		return duration;
	}
	
	public void setDuration(Duration duration) {
		this.duration = duration;
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
		
		sb.append("Element");
		sb.append("\n\t\t" + this.status); 
		sb.append("\n\t\t" + this.duration);
		sb.append("\n\t\t" + this.distance);
		
		return sb.toString();
	}
}
