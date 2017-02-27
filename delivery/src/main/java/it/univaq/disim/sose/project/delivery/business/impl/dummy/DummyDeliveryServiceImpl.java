package it.univaq.disim.sose.project.delivery.business.impl.dummy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.project.delivery.business.DeliveryService;
import it.univaq.disim.sose.project.delivery.business.model.Address;

/**
 * Testing, in offline mode or when the quota is reached (up to X req./day for free)
 * @see root-context.xml
 */

@Service
public class DummyDeliveryServiceImpl implements DeliveryService {
	private static Logger LOGGER = LoggerFactory.getLogger(DummyDeliveryServiceImpl.class);
	private static final double MIN = 0;
	private static final double MAX = 100;

	@Override
	public double getDeliveryFee(Address origin, Address destination) {
		double fee = MIN + (Math.random() * (MAX - MIN));
		LOGGER.info("Fee: " + fee);
		return fee;
	}
}
