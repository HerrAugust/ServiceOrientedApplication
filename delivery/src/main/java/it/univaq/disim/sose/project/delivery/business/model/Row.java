package it.univaq.disim.sose.project.delivery.business.model;

public class Row {
	private Element[] elements;

	public Element[] getElements() {
		return elements;
	}

	public void setElements(Element[] elements) {
		this.elements = elements;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Row:");
		for(Element element: this.elements)
			sb.append("\n\t" + element);
		
		return sb.toString();
	}
}
