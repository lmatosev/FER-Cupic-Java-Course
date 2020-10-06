package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * 
 * Demonstration for the third subtask but this time using a LinkedListIndexedCollection.
 *
 */

public class Podzadatak3LinkedListDemo {
	public static void main(String[] args) {

		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");

		ElementsGetter getter = col.createElementsGetter();

		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());

		col.clear();

		System.out.println("Jedan element: " + getter.getNextElement());

	}
}
