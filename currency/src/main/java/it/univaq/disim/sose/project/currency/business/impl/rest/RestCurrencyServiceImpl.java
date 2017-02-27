package it.univaq.disim.sose.project.currency.business.impl.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import it.univaq.disim.sose.project.currency.business.BusinessException;
import it.univaq.disim.sose.project.currency.business.CurrencyService;
import it.univaq.disim.sose.project.currency.business.model.FixerResponse;

@Service
public class RestCurrencyServiceImpl implements CurrencyService {
	private static Logger LOGGER = LoggerFactory.getLogger(RestCurrencyServiceImpl.class);
	
	@Value("#{cfg.url}")
	private String baseURL;

	@Override
	public double getRate(String from, String to) {
		
		if(from.equals(to))
			return 1;
		
		try {
			// Build URL
			UriComponents uriComponents = UriComponentsBuilder
						.fromUriString(baseURL)
		                .queryParam("base", from)
		                .build()
		                .encode();
			String url = uriComponents.toUriString();
			LOGGER.info("Request: " + url);
			
			// Connect (in HTTPs)
			HttpURLConnection c = null;

			URL u = new URL(url);
			c = (HttpURLConnection) u.openConnection();
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
				
				FixerResponse fixerResponse = new Gson().fromJson(sb.toString(), FixerResponse.class);
				LOGGER.info(fixerResponse.toString());
				
				return Double.parseDouble( fixerResponse.getRates().getRate(to) );
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
