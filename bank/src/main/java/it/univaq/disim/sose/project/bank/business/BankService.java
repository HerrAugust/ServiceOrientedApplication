package it.univaq.disim.sose.project.bank.business;

import it.univaq.disim.sose.project.bank.accountRequest;
import it.univaq.disim.sose.project.bank.accountResponse;
import it.univaq.disim.sose.project.bank.withdrawRequest;
import it.univaq.disim.sose.project.bank.withdrawResponse;
import it.univaq.disim.sose.project.bank.refillRequest;
import it.univaq.disim.sose.project.bank.refillResponse;

public interface BankService {

	accountResponse account(accountRequest parameters) throws BusinessException;
	withdrawResponse withdraw(withdrawRequest parameters) throws BusinessException;
	refillResponse refill(refillRequest parameters) throws BusinessException;
	
}
