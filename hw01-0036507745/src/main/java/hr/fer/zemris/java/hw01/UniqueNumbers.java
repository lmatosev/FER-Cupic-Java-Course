package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred koji predstavlja binarno stablo i sadrži neke jednostavne metode poput dodavanja vrijednosti
 * u stablo i izračuna veličine stabla.
 * @author Lovro Matošević
 * @version 1.0
 */

public class UniqueNumbers {
	
	/**
	 * Main metoda traži korisnika da unosi cijele brojeve koji će biti dodani u stablo. Unos prestaje
	 * kada korisnik upiše 'kraj'.
	 * @param args ne koristi se
	 */

	public static void main(String[] args) {
		TreeNode glava = null;
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Unesite broj >");
			String next = sc.next();
			if (next.equals("kraj")) {
				break;
			}
			try {
				int value = Integer.parseInt(next);
				if (containsValue(glava, value)) {
					System.out.println("Broj već postoji. Preskačem.");
				} else {
					glava = addNode(glava, value);
					System.out.println("Dodano.");
				}

			} catch (NumberFormatException ex) {
				System.out.println("'" + next + "'" + "nije cijeli broj");
			}
		}

		System.out.print("Ispis od najmanjeg: ");
		printAscending(glava);
		System.out.println();
		System.out.print("Ispis od najvećeg: ");
		printDescending(glava);

		sc.close();
	}
	
	/**
	 * Metoda koja ispisuje elemente stabla silazno.
	 * @param glava korijen stabla
	 */

	private static void printDescending(TreeNode glava) {
		if (glava != null) {
			printDescending(glava.right);
			System.out.printf("%d ", glava.value);
			printDescending(glava.left);
		}
	}
	
	/**
	 * Metoda koja ispisuje elemente stabla uzlazno.
	 * @param glava korijen stabla
	 */

	private static void printAscending(TreeNode glava) {
		if (glava != null) {
			printAscending(glava.left);
			System.out.printf("%d ", glava.value);
			printAscending(glava.right);
		}

	}
	/**
	 * Metoda provjerava sadrži li stablo čiji je korijen <code>glava</code> vrijednost <code>value</code>
	 * 
	 * 
	 * @param glava korijen stabla
	 * @param value	
	 * @return <code>true</code> ako stablo sadrži zadanu vrijednost, odnosno <code>false</code> ako ne sadrži
	 */

	public static boolean containsValue(TreeNode glava, int value) {

		if (glava == null) {
			return false;
		}
		if (glava.value == value) {
			return true;
		} else {
			if (glava.value < value) {
				return containsValue(glava.right, value);
			} else {
				return containsValue(glava.left, value);
			}
		}
	}
	
	/**
	 * Razred koji se koristi kao pomoćna struktura za stablo
	 * 
	 *
	 */

	public static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;

		public TreeNode(int value) {
			this.value = value;
		}

	}
	
	/**
	 * Metoda koja služi za dodavanje novih vrijednosti u stablo.
	 * 
	 * @param glava korijen stabla
	 * @param value vrijednost koja se dodaje u stablo
	 * @return TreeNode 
	 */

	public static TreeNode addNode(TreeNode glava, int value) {

		if (glava == null) {
			return new TreeNode(value);
		}

		if (glava.value < value) {
			glava.right = addNode(glava.right, value);
		} else if (glava.value > value) {
			glava.left = addNode(glava.left, value);
		}

		return glava;

	}
	
	/**
	 * Metoda računa i vraća cijeli broj koji predstavlja veličinu stabla.
	 * 
	 * @param glava korijen stabla
	 * @return int veličina stabla 
	 */

	public static int treeSize(TreeNode glava) {

		if (glava == null) {
			return 0;
		}

		if (glava.left == null && glava.right == null) {
			return 1;
		}

		return 1 + treeSize(glava.left) + treeSize(glava.right);

	}

}
