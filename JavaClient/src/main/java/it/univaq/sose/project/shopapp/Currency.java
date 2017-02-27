package it.univaq.sose.project.shopapp;

import java.util.HashMap;
import java.util.Map;

/*
 * Currency enumeration
 */

public class Currency {
	private final static Map<Integer, String> currencies = new HashMap<Integer, String>();
	static {
		currencies.put(1, "EUR");
		currencies.put(2, "GBP");
		currencies.put(3, "USD");
	}
	
	public static String getCurrency(int index) {
		return currencies.get(index);
	}
}
