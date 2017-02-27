package it.univaq.disim.sose.project.currency.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.project.currency.CurrencyPT;
import it.univaq.disim.sose.project.currency.RateRequest;
import it.univaq.disim.sose.project.currency.RateResponse;
import it.univaq.disim.sose.project.currency.business.CurrencyService;

@Component(value="CurrencyPTImpl")
public class CurrencyPTImpl implements CurrencyPT {
	
	@Autowired
	private CurrencyService service;

	@Override
	public RateResponse rate(RateRequest parameters) {
		
		double rate = service.getRate(parameters.getFrom().toString(), parameters.getTo().toString());
		
		RateResponse response = new RateResponse();
		response.setRate(rate);
		
		return response;
	}
}
