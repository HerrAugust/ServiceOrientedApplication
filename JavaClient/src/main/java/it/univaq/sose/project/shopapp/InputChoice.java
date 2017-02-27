package it.univaq.sose.project.shopapp;

import java.util.Scanner;

public class InputChoice {
	Scanner scanner = new Scanner(System.in);

	/*
	 * Returns the choice between 'min' and 'max'
	 * and 0 if an error occurred
	 */
	/**
	 * Read an integer from the standard input
	 * @param min
	 * @param max
	 * @return 0 if an error occurred, the choice as an integer between 'min' and 'max' otherwise
	 */
	public int readChoice(int min, int max) {		
		int choice;	
		
		// Parse integer
		try {
			String line = scanner.nextLine();
			choice = Integer.parseInt(line);
		} catch(Exception e) {
			System.out.println("Input must be an integer");
			return 0;
		}
		
		// Check boundaries
		if(choice<min || choice>max) {
			System.out.println("Input must be in [" + min + "," + max + "]");
			return 0;
		}
		
		return choice;
	}
}
