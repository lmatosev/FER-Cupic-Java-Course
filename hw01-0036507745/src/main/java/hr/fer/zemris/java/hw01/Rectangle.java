package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Jednostavan razred koji računa površinu i opseg pravokutnika na temelju unosa korisnika.
 * 
 * @author Lovro Matošević
 * @version 1.0
 */

public class Rectangle {
	
	/**
	 * Main metoda prvo provjerava jesu li uneseni parametri preko naredbenog retka. Ako jesu i ispravni su,
	 * računa se površina i opseg zadanog pravokutnika, inače se prekida s radom. Ako ništa nije uneseno preko
	 * naredbenog retka, program očekuje da korisnik unese ispravne parametre za širinu i visinu.
	 * 
	 * @param args moguće unijeti širinu i visinu kao parametre
	 */

	public static void main(String[] args) {

		double visina = 0, širina = 0;
		Scanner sc = new Scanner(System.in);

		if (args.length == 2) {
			try {
				širina=Double.parseDouble(args[0]);
				if(širina<=0) {
					System.out.println("Širina ne može biti negativna, prekidam s radom.");
					System.exit(0);
				}
			}catch(NumberFormatException ex) {
				System.out.println("Pogrešno unesena širina, prekidam s radom.");
				System.exit(0);
			}
			try {
				visina=Double.parseDouble(args[1]);
				if(visina<=0) {
					System.out.println("Visina ne može biti negativna, prekidam s radom.");
					System.exit(0);
				}
			}catch(NumberFormatException ex) {
				System.out.println("Pogrešno unesena visina, prekidam s radom");
				System.exit(0);
			}
			ispis(širina, visina);

		} else if (args.length == 0) {

			širina = unosŠirine(sc);
			visina = unosVisine(sc);
			ispis(širina, visina);

		} else {
			System.out.println("Nevaljan broj argumenata, prekidam s radom.");
			System.exit(0);
		}

		sc.close();

	}
	/**
	 *  Pomoćna metoda koja služi za unos visine.
	 * @param sc Scanner koji se predaje metodi.
	 * @return
	 */
	private static double unosVisine(Scanner sc) {
		while (true) {
			System.out.println("Unesite visinu >");
			String iduci = sc.next();
			try {
				double visina = Double.parseDouble(iduci);
				if (visina > 0) {
					return visina;
				} else {
					System.out.println("Unijeli ste negativnu vrijednost.");
				}
			} catch (NumberFormatException ex) {
				System.out.println("'" + iduci + "'" + "se ne može protumačiti kao broj");
			}
		}
	}
	
	/**
	 * Pomoćna metoda koja služi za unos širine.
	 * @param sc Scanner koji se predaje metodi
	 * @return
	 */

	private static double unosŠirine(Scanner sc) {
		while (true) {
			System.out.println("Unesite širinu >");
			String next = sc.next();
			try {
				double širina = Double.parseDouble(next);
				if (širina > 0) {
					return širina;
				} else {
					System.out.println("Unijeli ste negativnu vrijednost.");
				}
			} catch (NumberFormatException ex) {
				System.out.println("'" + next + "'" + "se ne može protumačiti kao broj");
			}
		}

	}
	
	/**
	 * Metoda ispisuje površinu i opseg pravokutnika sa širinom <code>širina</code> i visinom <code>visina</code>.
	 * @param širina širina pravokutnika
	 * @param visina visina pravokutnika
	 */

	private static void ispis(double širina, double visina) {

		double površina = širina * visina;
		double opseg = 2 * širina + 2 * visina;
		System.out.println("Pravokutnik širine " + širina + " i visine " + visina + " ima površinu " + površina
				+ " te opseg " + opseg);

	}

}
