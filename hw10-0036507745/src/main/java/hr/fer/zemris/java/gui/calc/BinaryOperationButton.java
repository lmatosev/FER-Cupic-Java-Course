package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

/**
 * 
 * Class which represents a button which activates a binary operation. Allows
 * inverse operations to be added.
 * 
 * @author Lovro Matošević
 *
 */
public class BinaryOperationButton extends JButton implements InversibleButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2491977077625981544L;

	/**
	 * Name of the regular operation
	 */
	private String operation;
	/**
	 * Name of the inverse operation
	 */
	private String inverseOperation;
	/**
	 * Boolean value which indicates which operation is active
	 */
	private boolean isInverse;
	/**
	 * Calculator being operated
	 */
	private CalcModelImpl calc;
	/**
	 * Regular operation
	 */
	private DoubleBinaryOperator op;
	/**
	 * Inverse operation
	 */
	private DoubleBinaryOperator invOp;
	/**
	 * Currently active operation
	 */
	private DoubleBinaryOperator activeOp;

	/**
	 * Constructor which initializes the button.
	 * 
	 * @param operation        - name of the regular operation
	 * @param inverseOperation - name of the inverse operation
	 * @param calc             - calculator being operated
	 * @param op               - regular operation
	 * @param invOp            - inverse operation
	 */
	public BinaryOperationButton(String operation, String inverseOperation, CalcModelImpl calc, DoubleBinaryOperator op,
			DoubleBinaryOperator invOp) {
		super(operation);
		this.operation = operation;
		this.inverseOperation = inverseOperation;
		this.calc = calc;
		this.op = op;
		this.invOp = invOp;
		this.activeOp = op;
		this.isInverse = false;
		this.addActionListener(l -> {
			this.operate();
		});
	}

	/**
	 * Helper method used to execute the binary operation.
	 */
	private void operate() {
		DoubleBinaryOperator current = calc.getPendingBinaryOperation();
		if (current == null) {
			calc.setPendingBinaryOperation(activeOp);
			calc.setActiveOperand(calc.getValue());
		} else {
			double result = current.applyAsDouble(calc.getActiveOperand(), calc.getValue());
			calc.setValue(result);
			calc.setActiveOperand(result);
			calc.setPendingBinaryOperation(activeOp);
		}
	}

	/**
	 * Switches the active operation and changes the name appropriately.
	 */
	@Override
	public void switchInverse() {
		if (this.isInverse) {
			this.isInverse = false;
			this.activeOp = op;
			this.setText(this.operation);
		} else {
			this.isInverse = true;
			this.activeOp = invOp;
			this.setText(this.inverseOperation);
		}
	}
}
