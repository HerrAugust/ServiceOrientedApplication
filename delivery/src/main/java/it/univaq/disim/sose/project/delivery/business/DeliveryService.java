package it.univaq.disim.sose.project.delivery.business;

import it.univaq.disim.sose.project.delivery.business.model.Address;

public interface DeliveryService {
	double getDeliveryFee(Address origin, Address destination);
}
