package it.univaq.disim.sose.project.delivery.business.impl.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import it.univaq.disim.sose.project.delivery.business.BusinessException;
import it.univaq.disim.sose.project.delivery.business.DeliveryService;
import it.univaq.disim.sose.project.delivery.business.model.Address;
import it.univaq.disim.sose.project.delivery.business.model.DistanceMatrix;

@Service
public class RestDeliveryServiceImpl implements DeliveryService {
	private static Logger LOGGER = LoggerFactory.getLogger(RestDeliveryServiceImpl.class);
	
	@Value("#{cfg.url}")
	private String baseURL;
	@Value("#{cfg.key}")
	private String key;
	@Value("#{cfg.feePerKm}")
	private double feePerKm;
	
	@Override
	public double getDeliveryFee(Address origin, Address destination) {
		try {
			// Build URL
			UriComponents uriComponents = UriComponentsBuilder
						.fromUriString(baseURL)
		                .queryParam("origins", origin.toString())
		                .queryParam("destinations", destination.toString())
		                .queryParam("key", key)
		                .build()
		                .encode();
			String url = uriComponents.toUriString();
			LOGGER.info("Request: " + url);
			
			// Connect (in HTTPs)
			HttpsURLConnection c = null;

			URL u = new URL(url);
			c = (HttpsURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.connect();
			int status = c.getResponseCode();

			LOGGER.info("Response: " + status);
			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();

				if (c != null) {
					c.disconnect();
				}
				
				DistanceMatrix dm = new Gson().fromJson(sb.toString(), DistanceMatrix.class);
				LOGGER.info(dm.toString());
				
				// Compute the fee based on a fee/km (value return by the API is in meters)
				return dm.getRows()[0].getElements()[0].getDistance().getValue() * this.feePerKm / 1000;
			default:
				if (c != null) {
					c.disconnect();
				}
				throw new Exception("Http Error: " + status);
			}
		} catch (Exception  e) {
			LOGGER.error("error", e);
			throw new BusinessException(e);
		}
	}
}
