package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class used for storing values. Stores an object of any type, or null.
 * Provides methods for basic arithmetics between ValueWrappers storing numbers.
 * 
 * @author Lovro Matošević
 *
 */
public class ValueWrapper {
	/**
	 * The value stored.
	 */
	private Object value;

	/**
	 * Accepts a value which is then stored.
	 * 
	 * @param value - the value to be stored
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Increments current value by the value of provided object.
	 * 
	 * @param incValue - incrementing value
	 */
	public void add(Object incValue) {
		Operation oper = new Operation(value, incValue, "+");
		this.value = oper.result;
	}

	/**
	 * Subtracts from the current value.
	 * 
	 * @param decValue - value to be subtracted
	 */
	public void subtract(Object decValue) {
		Operation oper = new Operation(value, decValue, "-");
		this.value = oper.result;
	}

	/**
	 * Multiplies the current value by the provided value.
	 * 
	 * @param mulValue - value to be multiplied by
	 */
	public void multiply(Object mulValue) {
		Operation oper = new Operation(value, mulValue, "*");
		this.value = oper.result;
	}

	/**
	 * Divides the current value by the provided value.
	 * 
	 * @param divValue - value to be divided with
	 */
	public void divide(Object divValue) {
		Operation oper = new Operation(value, divValue, "/");
		this.value = oper.result;
	}

	/**
	 * Compares the stored value to the provided value.
	 * 
	 * @param withValue - value to be compared with
	 * @return returns 1 if the stored value is greater, -1 if it is lesser and 0 if
	 *         it is equal to the provided value.
	 */
	public int numCompare(Object withValue) {
		Operation oper = new Operation(value, withValue, "compare");
		return (int) oper.result;
	}

	/**
	 * @return returns the stored value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Sets the stored value to the provided one.
	 * 
	 * @param obj -the value to be stored
	 */
	public void setValue(Object obj) {
		this.value = obj;
	}

	/**
	 * Class used for executing operations with objects.
	 *
	 */
	private class Operation {
		/**
		 * The first operand
		 */
		Object value1;
		/**
		 * The second operand
		 */
		Object value2;
		/**
		 * The result of the operation
		 */
		Object result;
		/**
		 * String which determines the type of operation
		 */
		String operation;

		/**
		 * Accepts two values and a string which determines the type of operation. Calls
		 * the calculate method to dermine the result.
		 * 
		 * @param value1    - first operand
		 * @param value2    -second operand
		 * @param operation - operation type
		 */
		public Operation(Object value1, Object value2, String operation) {
			this.isValid(value2);
			this.value1 = value1;
			this.value2 = value2;
			this.operation = operation;
			this.calculate();
		}

		/**
		 * Checks if the provided operand is valid.
		 * 
		 * @param value2 - operand
		 * @throws RuntimeException - if the operand is not valid
		 */
		private void isValid(Object value2) {
			if (value2 instanceof Integer || value2 instanceof Double || value2 instanceof String || value2 == null) {
				return;
			}
			throw new RuntimeException();
		}

		/**
		 * Calculates the result of the operation and stores it.
		 */
		public void calculate() {
			value1 = this.nullCheck(value1);
			value2 = this.nullCheck(value2);
			value1 = this.stringCheck(value1);
			value2 = this.stringCheck(value2);
			if (value1 instanceof Double || value2 instanceof Double) {
				double oper1;
				double oper2;
				if (value1 instanceof Integer) {
					oper1 = ((int) value1);
				} else {
					oper1 = (double) value1;
				}
				if (value2 instanceof Integer) {
					oper2 = ((int) value2);

				} else {
					oper2 = (double) value2;
				}
				switch (operation) {
				case "+":
					result = oper1 + oper2;
					break;
				case "-":
					result = oper1 - oper2;

					break;
				case "*":
					result = oper1 * oper2;

					break;
				case "/":
					result = oper1 / oper2;
					break;
				case "compare":
					if (oper1 - oper2 == 0) {
						result = 0;
					} else {
						result = oper1 - oper2 > 0 ? 1 : -1;
					}
				default:
					break;
				}
			} else {
				int oper1 = (int) value1;
				int oper2 = (int) value2;
				switch (operation) {
				case "+":
					result = oper1 + oper2;
					break;
				case "-":
					result = oper1 - oper2;

					break;
				case "*":
					result = oper1 * oper2;

					break;
				case "/":
					result = oper1 / oper2;
					break;
				case "compare":
					if (oper1 - oper2 == 0) {
						result = 0;
					} else {
						result = oper1 - oper2 > 0 ? 1 : -1;
					}
				default:
					break;
				}
			}
		}

		/**
		 * If the provided object is a string, converts it to a value.
		 * 
		 * @param value - object to be checked
		 * @throws RuntimeException - if the provided string was invalid, i.e. not a
		 *                          double or an integer
		 */
		private Object stringCheck(Object value) {
			if (value instanceof String) {
				String strValue = (String) value;
				try {
					if (strValue.contains(".") || strValue.contains("E")) {
						value = Double.parseDouble(strValue);
					} else {
						value = Integer.parseInt(strValue);
					}
				} catch (NumberFormatException ex) {
					throw new RuntimeException();
				}
			}
			return value;
		}

		/**
		 * Used for null checking.
		 * 
		 * @param value - object to be checked and converted
		 */
		private Object nullCheck(Object value) {
			if (value == null) {
				return 0;
			}
			return value;
		}

	}
}
