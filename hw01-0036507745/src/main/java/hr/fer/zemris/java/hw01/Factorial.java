/**
 * 
 */
package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Jednostavan razred koji računa faktorijelu cijelog broja koji korisnik unese.
 * 
 * @author Lovro Matošević
 * @version 1.0
 */
public class Factorial {

	/**
	 * Main metoda čeka unos korisnika i potom pokušava izračunati faktorijelu unesenog broja.
	 * @param args ne koristi se
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("Unesite broj >");
			String next = sc.next();
			if (next.equals("kraj")) {
				break;
			} else {
				try {
					long factorial = calculateFactorial(next);
					System.out.println(next + "! = " + factorial);
				} catch (IllegalArgumentException ex) {
					continue;
				}
			}
		}
		System.out.println("Doviđenja.");
		sc.close();

	}
	
	/**
	 * Metoda koja računa faktorijel nekog broja. Argument mora biti string koji predstavlja cijeli broj
	 * između 3 i 20.
	 * 
	 * @param number broj koji se predaje metodi
	 * @return long metoda vraća long vrijednost koja predstavlja traženu faktorijelu.
	 * @throws IllegalArgumentException ako se kao argument preda vrijednost koja se ne može parsirati u cijeli
	 * broj ili ako je vrijednost izvan dopuštenih granica.
	 */

	public static long calculateFactorial(String number) {

		long factorial = 1;

		try {
			int num = Integer.parseInt(number);
			if (num >= 3 && num <= 20) {
				for (int i = 2; i <= num; i++) {
					factorial = factorial * i;
				}
				return factorial;
				
			} else {
				System.out.println("'" + num + "'" + " nije broj u dozvoljenom rasponu.");
				throw new IllegalArgumentException();
			}
		} catch (NumberFormatException ex) {
			System.out.println("'" + number + "'" + " nije cijeli broj.");
			throw new IllegalArgumentException();
		}

	}
}
