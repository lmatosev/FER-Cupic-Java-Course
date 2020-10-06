package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * 
 * An implementation of the calculator model. Provides methods to add digits,
 * decimal points, set and clear the active operands and operations etc.
 * 
 * @author Lovro Matošević
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Boolean value which indicates if the calculator is editable
	 */
	private boolean isEditable;
	/**
	 * Boolean value which indicates if the current value is positive or negative
	 */
	private boolean isPositive;
	/**
	 * Current input
	 */
	private String input;
	/**
	 * Current value
	 */
	private double value;
	/**
	 * Current active operand
	 */
	private double activeOperand;
	/**
	 * Pending operation
	 */
	private DoubleBinaryOperator pendingOperation;
	/**
	 * Boolean value which indicates if the operand is set
	 */
	private boolean isOperandSet;
	/**
	 * List of listeners which are stored
	 */
	private List<CalcValueListener> listeners;
	/**
	 * Boolean value which indicates if the value was cleared before. Used as a
	 * helper value when deciding which value to set.
	 */
	private boolean wasCleared;

	/**
	 * The default constructor which instantiates the calculator.
	 */
	public CalcModelImpl() {
		super();
		this.input = "";
		this.value = 0;
		this.isEditable = true;
		this.isPositive = true;
		this.wasCleared = false;
		this.listeners = new ArrayList<>();
	}

	/**
	 * Adds the provided listener to the internal list.
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	/**
	 * Removes the provided listener from the internal list.
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	/**
	 * Helper method used to notify all the stored listeners.
	 */
	private void notifyRegisteredListeners() {
		for (var listener : this.listeners) {
			listener.valueChanged(this);
		}
	}

	/**
	 * Returns the current value.
	 * 
	 * @return value - the current value
	 */
	@Override
	public double getValue() {
		return this.value;
	}

	/**
	 * Sets the current value
	 * 
	 * @param value - the value to be set
	 */
	@Override
	public void setValue(double value) {
		this.value = value;
		this.input = String.valueOf(value);
		this.isEditable = false;
		this.notifyRegisteredListeners();
	}

	/**
	 * Returns true if the calculator is currently editable and false else.
	 */
	@Override
	public boolean isEditable() {
		return this.isEditable;
	}

	/**
	 * Clears the current input and sets the calculator to be editable.
	 */
	@Override
	public void clear() {
		this.input = "";
		this.isEditable = true;
		this.isPositive = true;
		this.notifyRegisteredListeners();
	}

	/**
	 * Resets the calculator.
	 */
	@Override
	public void clearAll() {
		this.clear();
		this.clearActiveOperand();
		this.pendingOperation = null;
		this.notifyRegisteredListeners();
		this.isEditable = true;
		this.isPositive = true;
	}

	/**
	 * Swaps the current sign of the value.
	 * 
	 * @throws CalculatorInputException - if the calculator is not editable
	 */
	@Override
	public void swapSign() throws CalculatorInputException {
		if (!this.isEditable) {
			throw new CalculatorInputException("Input not editable.");
		}
		this.setValue(-this.value);
		if (this.isPositive == true) {
			this.isPositive = false;
			return;
		}
		this.isPositive = true;
		this.notifyRegisteredListeners();
	}

	/**
	 * Inserts a decimal point to the input.
	 * 
	 * @throws CalculatorInputException - if the calculator is not editable or if
	 *                                  the input is currently empty
	 */
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!this.isEditable || this.input.isEmpty()) {
			throw new CalculatorInputException("Input not editable.");
		}
		if (this.input.contains(".")) {
			throw new CalculatorInputException("Input already contains a decimal point.");
		}
		this.input += ".";
		this.notifyRegisteredListeners();
	}

	/**
	 * Inserts the provided digit to the input.
	 * 
	 * @throws CalculatorInputException - if there is an error in input
	 * @throws IllegalArgumentException - if the provided input is not parsable to
	 *                                  double
	 */
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (this.getPendingBinaryOperation() != null) {
			if (!this.wasCleared) {
				this.clear();
				this.wasCleared = true;
			}
		} else {
			this.wasCleared = false;
		}
		if (this.input.equals("0")) {
			if (digit == 0) {
				return;
			} else {
				this.input = String.valueOf(digit);
				this.notifyRegisteredListeners();
				return;
			}
		}
		this.input += String.valueOf(digit);
		try {
			this.value = Double.parseDouble(this.input);
			if (!this.isPositive) {
				this.value = -this.value;
			}
			if (Double.isInfinite(this.value)) {
				throw new CalculatorInputException();
			}
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException();
		}
		this.notifyRegisteredListeners();
	}

	/**
	 * Returns true if the active operand is set and false else.
	 */
	@Override
	public boolean isActiveOperandSet() {
		return this.isOperandSet;
	}

	/**
	 * Returns the active operand.
	 * 
	 * @throws IllegalStateException - if the operand is not set
	 */
	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!this.isOperandSet) {
			throw new IllegalStateException("Operand is not set.");
		}
		return this.activeOperand;
	}

	/**
	 * Sets the active operand.
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.isOperandSet = true;
	}

	/**
	 * Clears the active operand.
	 */
	@Override
	public void clearActiveOperand() {
		this.isOperandSet = false;
	}

	/**
	 * Returns the pending binary operation.
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	/**
	 * Sets the pending binary operation to the provided one.
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		this.wasCleared = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (this.input.isEmpty()) {
			if (this.isPositive) {
				return "0";
			} else {
				return "-0";
			}
		}

		if (this.isPositive) {
			return this.input;
		}

		return "-" + this.input;
	}

}
