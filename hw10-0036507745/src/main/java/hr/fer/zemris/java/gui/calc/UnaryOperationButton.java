package hr.fer.zemris.java.gui.calc;

import java.util.function.UnaryOperator;

import javax.swing.JButton;

/**
 * 
 * Class which represents a button which activates a unary operation. Allows
 * inverse operations to be added.
 * 
 * @author Lovro Matošević
 *
 */
public class UnaryOperationButton extends JButton implements InversibleButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2722986512058287388L;

	/**
	 * Name of the operation
	 */
	private String operation;
	/**
	 * Name of the inverse operation
	 */
	private String inverseOperation;
	/**
	 * Boolean value which indicates which of the two operations is active
	 */
	private boolean isInverse;

	/**
	 * Constructor which initializes the button.
	 * 
	 * @param operation        - name of the regular operation
	 * @param inverseOperation - name of the inverse operation
	 * @param calc             - calculator being operated
	 * @param op               - the regular operation
	 * @param invOp            - the inverse operation
	 */
	public UnaryOperationButton(String operation, String inverseOperation, CalcModelImpl calc, UnaryOperator<Double> op,
			UnaryOperator<Double> invOp) {
		super(operation);
		this.operation = operation;
		this.inverseOperation = inverseOperation;
		this.addActionListener(l -> {
			if (this.isInverse) {
				double result = invOp.apply(calc.getValue());
				calc.setValue(result);
			} else {
				double result = op.apply(calc.getValue());
				calc.setValue(result);
			}
		});
	}

	/**
	 * Switches the active operation and changes the name appropriately.
	 */
	@Override
	public void switchInverse() {
		if (this.isInverse) {
			this.isInverse = false;
			this.setText(this.operation);
		} else {
			this.isInverse = true;
			this.setText(this.inverseOperation);
		}
	}

}
