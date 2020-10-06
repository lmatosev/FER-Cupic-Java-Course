package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * 
 * Demonstration for the sixth subtask.
 *
 */

public class Podzadatak6Demo {

	public static void main(String[] args) {
		
		List col1 = new ArrayIndexedCollection();
		List col2 = new LinkedListIndexedCollection();
		col1.add("Ivana");
		col2.add("Jasna");
		
		Collection col3 = col1;
		Collection col4 = col2;
		
		col1.get(0);
		col2.get(0);
		//col3.get(0);
		//col4.get(0);
		
		col1.forEach(System.out::println);
		col2.forEach(System.out::println);
		col3.forEach(System.out::println);
		col4.forEach(System.out::println);

		
	}

}
