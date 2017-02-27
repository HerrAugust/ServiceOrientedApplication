package it.univaq.sose.project.shopapp;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

// TODO: idea=one method per request

public class RemoteRequest {
	
	private final static String url = "http://localhost:8080/shop/services/shopservice";
	
	public static void getAccount(String username, String password, String currency) {
		// TODO
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
	}
}
