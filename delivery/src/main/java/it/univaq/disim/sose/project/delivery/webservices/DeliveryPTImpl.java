package it.univaq.disim.sose.project.delivery.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.project.delivery.DeliveryFeeRequest;
import it.univaq.disim.sose.project.delivery.DeliveryFeeResponse;
import it.univaq.disim.sose.project.delivery.DeliveryPT;
import it.univaq.disim.sose.project.delivery.business.DeliveryService;
import it.univaq.disim.sose.project.delivery.business.model.Address;

@Component(value="DeliveryPTImpl")
public class DeliveryPTImpl implements DeliveryPT {
	
	@Autowired
	private DeliveryService service;

	@Override
	public DeliveryFeeResponse deliveryFee(DeliveryFeeRequest parameters) {

		Address origin = new Address();
		origin.setStreet( parameters.getOrigin().getStreet() );
		origin.setCity( parameters.getOrigin().getCity() );
		origin.setCountry( parameters.getOrigin().getCountry() );
		
		Address destination = new Address();
		destination.setStreet( parameters.getDestination().getStreet() );
		destination.setCity( parameters.getDestination().getCity() );
		destination.setCountry( parameters.getDestination().getCountry() );
		
		double deliveryFee = service.getDeliveryFee(origin, destination);
		
		DeliveryFeeResponse response = new DeliveryFeeResponse();
		response.setDeliveryFee(deliveryFee);
		
		return response;
	}
}
