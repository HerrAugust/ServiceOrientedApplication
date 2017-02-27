package it.univaq.sose.project.shopapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	private static final String USER_AGENT = "Mozilla/5.0";
	private static String url_viewAccount = "localhost:8000/accounts";
	private static String url_viewCatalog = "localhost:8000/products";
	private static String url_buyItem = "localhost:8000/products";
	
	public static void main(String[] args) {
		InputChoice inputChoice = new InputChoice();
		int choice = 0;
		List<String> response = null;
		
		/* Welcome */
		System.out.println("Welcome to the Java Shop Application");
		
		/* Select currency */
		while(choice<=0) {
			System.out.println("Please choose your currency");
			System.out.println("1) EUR");
			System.out.println("2) GBP");
			System.out.println("3) USD");
			System.out.println("4) Exit");
			System.out.print("Your choice: ");
			
			choice = inputChoice.readChoice(1, 4);
			
			if(choice==4) {
				System.out.println("Good bye!");
				return;
			}
		}
		String currency = Currency.getCurrency(choice);
		
		while(true) {
			/* Select action */
			choice = 0;
			while(choice <= 0) {
				System.out.println("Please choose an action");
				System.out.println("1) View bank account");
				System.out.println("2) View product catalog");
				System.out.println("3) Exit");
				System.out.print("Your choice: ");
				choice = inputChoice.readChoice(1, 3);
			}
			
			switch(choice) {
				case 1: /* View Account */
					System.out.println("Here is your account:");
					
					try{
					response = perform_viewAccount(currency);
					}catch(IOException e) { e.printStackTrace(); return; }

					//print result
					for(String m : response) {
						System.out.println(m);
					}
					break;
				case 2: /* View catalog */
					System.out.println("Here is the product catalog:");
					try {
						response = Main.perform_viewCatalog(currency);
					} catch (IOException e) { e.printStackTrace(); }
					for(String m : response) {
						System.out.println(m);
					}
					
					// nested BUY
						/* Buy product */
						System.out.println("Please choose a product to buy (write its ID): ");
						Scanner s = new Scanner(System.in);
						int id_toBuy = s.nextInt();
						try {
							response = Main.perform_buyProduct(id_toBuy);
						} catch (IOException e) { e.printStackTrace(); }
						
						/* Buy product result */
						for(String m : response) {
							System.out.println(m);
						}
						System.out.println("Thank you for buying that item!");
					break;
				default:
					System.out.println("Good bye!");
					return;
			}
		}
		
	}

	private static List<String> newRESTConnection(String url, String method) throws IOException {
		if(!method.equals("POST") && ! method.equals("GET")) 
			return null;
		
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod(method);
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		int responseCode = con.getResponseCode();
		System.out.println("Main.perform_viewAccount: Sending 'GET' request to URL : " + url_viewAccount);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		List<String> response = new LinkedList<String>();

		while ((inputLine = in.readLine()) != null) {
			response.add(inputLine);
		}
		in.close();
		
		return response;
	}
	
	private static List<String> perform_buyProduct(int id_toBuy) throws IOException {
		List<String> results = null;
		results = Main.newRESTConnection(url_buyItem  + "/" + id_toBuy + "/buy", "GET");
		return results;
	}
	
	private static List<String> perform_viewCatalog(String currency) throws IOException {
		List<String> results = null;
		results = Main.newRESTConnection(url_viewCatalog + "?currency" + currency, "GET");
		return results;
	}
	
	private static List<String> perform_viewAccount(String currency) throws IOException {
		List<String> results = null;
		results = Main.newRESTConnection(url_viewCatalog + "?currency" + currency, "GET");
		return results;
	}
	
	
}
