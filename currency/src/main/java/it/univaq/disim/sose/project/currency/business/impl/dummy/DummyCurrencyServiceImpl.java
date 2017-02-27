package it.univaq.disim.sose.project.currency.business.impl.dummy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.project.currency.business.CurrencyService;

@Service
public class DummyCurrencyServiceImpl implements CurrencyService {
	private static Logger LOGGER = LoggerFactory.getLogger(DummyCurrencyServiceImpl.class);
	private static final double MIN = 0;
	private static final double MAX = 5;

	@Override
	public double getRate(String from, String to) {
		double rate = MIN + (Math.random() * (MAX - MIN));
		LOGGER.info("Rate: " + rate);
		return rate;
	}
}
