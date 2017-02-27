package it.univaq.disim.sose.project.bank.webservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.project.bank.BankPT;
import it.univaq.disim.sose.project.bank.BankRequest;
import it.univaq.disim.sose.project.bank.BankResponse;
import it.univaq.disim.sose.project.bank.business.BankService;

@Component(value = "BankPTImpl")
public class BankPTImpl implements BankPT {

	private static Logger LOGGER = LoggerFactory.getLogger(BankPTImpl.class);
	
	@Autowired
	private BankService service; //� un'interface

	public accountResponse account(accountRequest parameters) {

		LOGGER.info("CALLED account ON bank");

		try {
			accountResponse aResponse = service.account(parameters);
			return aResponse;
		} 
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

	}
	
	public withdrawResponse withdraw(withdrawRequest parameters) {

		LOGGER.info("CALLED withdraw ON bank");

		try {
			withdrawResponse wResponse = service.withdraw(parameters);
			return withdrawResponse;
		} 
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

	}
	
	public refillResponse refill(refillRequest parameters) {

		LOGGER.info("CALLED account ON account");

		try {
			refillResponse rResponse = service.refill(parameters);
			return rResponse;
		} 
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

	}
	
}
