package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class that is used to evaluate a postfix expression which is accepted through
 * the command line.
 * 
 * @author Lovro Matošević
 *
 */

public class StackDemo {

	/**
	 * The main method accepts an input through the command line and passes it to
	 * the evaluatePostfix method.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		if (args.length > 1 || args.length == 0) {
			System.out.println("Invalid number of arguments. Exiting");
			System.exit(0);
		}
		String input = args[0];

		try {
			int result = evaluatePostfix(input);
			System.out.println("Expression evaluates to " + result);
		} catch (Exception ex) {
			System.out.println("Invalid input.");
			System.out.println("Exiting.");
			System.exit(0);
		}
	}

	/**
	 * 
	 * @param input
	 */

	private static int evaluatePostfix(String input) {
		if (input.isBlank()) {
			throw new IllegalArgumentException("Input can't be empty");
		}
		ObjectStack stack = new ObjectStack();
		String[] inputSplit = input.strip().split(" ");
		stack.push(Integer.parseInt(inputSplit[0]));
		stack.push(Integer.parseInt(inputSplit[1]));
		for (int i = 2; i < inputSplit.length; i++) {
			try {
				int num = Integer.parseInt(inputSplit[i]);
				stack.push(num);
				continue;
			} catch (NumberFormatException ex) {

			}
			int num1 = (int) stack.pop();
			int num2 = (int) stack.pop();
			if (inputSplit[i].equals("+")) {

				int result = num1 + num2;
				stack.push(result);
			} else if (inputSplit[i].equals("-")) {

				int result = num2 - num1;
				stack.push(result);
			} else if (inputSplit[i].equals("/")) {

				if (num1 == 0) {
					System.out.println("Error. Impossible to divide by zero.");
					throw new IllegalArgumentException();
				}
				int result = (int) ((double) num2 / (double) num1);
				stack.push(result);
			} else if (inputSplit[i].equals("*")) {

				int result = num1 * num2;
				stack.push(result);
			} else if (inputSplit[i].equals("%")) {

				if (num1 == 0) {
					System.out.println("Error. Impossible to divide by zero");
					throw new IllegalArgumentException();
				}
				int result = num2 % num1;
				stack.push(result);
			} else {

			}
		}

		if (stack.size() != 1) {
			System.out.println("Error. Expression is invalid.");
			throw new IllegalArgumentException();
		}
		return (int) stack.pop();

	}
}
