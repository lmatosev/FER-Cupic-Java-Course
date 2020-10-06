package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.UnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * 
 * A displayable calculator which provides some basic calculator operations.
 * Some of the operations also have appropriate inverse operations implemented
 * which are activated by checking the inv checkbox.
 * 
 * @author Lovro Matošević
 *
 */
public class CalculatorFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Calculator which is being operated
	 */
	private static CalcModelImpl calc;
	/**
	 * List of all the buttons which implement inverse operations
	 */
	private List<InversibleButton> list;
	/**
	 * Stack being used for push and pop operations
	 */
	private Stack<Double> stack;

	/**
	 * Constructor which initializes and displays the frame itself.
	 */
	public CalculatorFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * Helper method used to initialize the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		list = new ArrayList<InversibleButton>();
		stack = new Stack<>();

		JLabel screen = new JLabel("");
		screen.setOpaque(true);
		screen.setBackground(Color.YELLOW);
		screen.setFont(screen.getFont().deriveFont(30f));
		screen.setPreferredSize(new Dimension(100, 100));
		screen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		calc.addCalcValueListener((calc) -> {
			screen.setText(calc.toString());
		});
		NumberButton[] numButtons = new NumberButton[10];
		for (int i = 0; i < 10; i++) {
			numButtons[i] = new NumberButton(String.valueOf(i), calc);
		}

		JCheckBox inv = new JCheckBox("Inv");
		inv.addActionListener(l -> {
			for (var button : this.list) {
				button.switchInverse();
			}
		});
		cp.add(screen, new RCPosition(1, 1));
		cp.add(inv, new RCPosition(5, 7));

		cp.add(numButtons[0], new RCPosition(5, 3));
		cp.add(numButtons[1], new RCPosition(4, 3));
		cp.add(numButtons[2], new RCPosition(4, 4));
		cp.add(numButtons[3], new RCPosition(4, 5));
		cp.add(numButtons[4], new RCPosition(3, 3));
		cp.add(numButtons[5], new RCPosition(3, 4));
		cp.add(numButtons[6], new RCPosition(3, 5));
		cp.add(numButtons[7], new RCPosition(2, 3));
		cp.add(numButtons[8], new RCPosition(2, 4));
		cp.add(numButtons[9], new RCPosition(2, 5));

		JButton switchSign = new JButton("+/-");
		switchSign.setPreferredSize(new Dimension(20, 15));
		switchSign.addActionListener((e) -> {
			calc.swapSign();
		});
		cp.add(switchSign, new RCPosition(5, 4));

		JButton decimal = new JButton(".");
		decimal.setPreferredSize(new Dimension(20, 15));
		decimal.addActionListener((e) -> {
			calc.insertDecimalPoint();
		});
		cp.add(decimal, new RCPosition(5, 5));

		JButton equals = new JButton("=");
		equals.setPreferredSize(new Dimension(20, 15));
		equals.addActionListener((e) -> {
			if (calc.isActiveOperandSet() && calc.getPendingBinaryOperation() != null) {
				calc.setValue(calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
				calc.setPendingBinaryOperation(null);
			}
		});
		cp.add(equals, new RCPosition(1, 6));

		DoubleBinaryOperator divider = (num1, num2) -> num1 / num2;
		BinaryOperationButton div = new BinaryOperationButton("/", "/", calc, divider, divider);
		div.setPreferredSize(new Dimension(20, 15));
		cp.add(div, new RCPosition(2, 6));

		DoubleBinaryOperator multiplier = (num1, num2) -> num1 * num2;
		BinaryOperationButton mul = new BinaryOperationButton("*", "*", calc, multiplier, multiplier);
		mul.setPreferredSize(new Dimension(20, 15));
		cp.add(mul, new RCPosition(3, 6));

		DoubleBinaryOperator subtractor = (num1, num2) -> num1 - num2;
		BinaryOperationButton sub = new BinaryOperationButton("-", "-", calc, subtractor, subtractor);
		sub.setPreferredSize(new Dimension(20, 15));
		cp.add(sub, new RCPosition(4, 6));

		DoubleBinaryOperator power = (num1, num2) -> Math.pow(num1, num2);
		DoubleBinaryOperator root = (num1, num2) -> Math.pow(num1, 1 / num2);
		BinaryOperationButton pow = new BinaryOperationButton("x^n", "x^(1/n)", calc, power, root);
		pow.setPreferredSize(new Dimension(20, 15));
		cp.add(pow, new RCPosition(5, 1));
		list.add(pow);

		BinaryOperationButton add = new BinaryOperationButton("+", "+", calc, Double::sum, Double::sum);
		add.setPreferredSize(new Dimension(20, 15));
		cp.add(add, new RCPosition(5, 6));

		JButton clr = new JButton("clr");
		clr.setPreferredSize(new Dimension(20, 15));
		clr.addActionListener((e) -> {
			calc.clear();
		});
		cp.add(clr, new RCPosition(1, 7));

		JButton reset = new JButton("reset");
		reset.setPreferredSize(new Dimension(20, 15));
		reset.addActionListener((e) -> {
			calc.clearAll();
		});
		cp.add(reset, new RCPosition(2, 7));

		UnaryOperator<Double> reciprocate = (num) -> 1 / num;
		UnaryOperationButton recipr = new UnaryOperationButton("1/x", "1/x", calc, reciprocate, reciprocate);
		recipr.setPreferredSize(new Dimension(20, 15));
		cp.add(recipr, new RCPosition(2, 1));
		list.add(recipr);

		UnaryOperator<Double> sinus = (num) -> Math.sin(num);
		UnaryOperator<Double> arcSinus = (num) -> Math.asin(num);
		UnaryOperationButton sin = new UnaryOperationButton("sin", "arcsin", calc, sinus, arcSinus);
		sin.setPreferredSize(new Dimension(20, 15));
		cp.add(sin, new RCPosition(2, 2));
		list.add(sin);

		UnaryOperator<Double> logarithm = (num) -> Math.log10(num);
		UnaryOperator<Double> invLogarithm = (num) -> Math.pow(10, num);
		UnaryOperationButton log = new UnaryOperationButton("log", "10^x", calc, logarithm, invLogarithm);
		log.setPreferredSize(new Dimension(20, 15));
		cp.add(log, new RCPosition(3, 1));
		list.add(log);

		UnaryOperator<Double> cosine = (num) -> Math.cos(num);
		UnaryOperator<Double> arcCosine = (num) -> Math.acos(num);
		UnaryOperationButton cos = new UnaryOperationButton("cos", "arccos", calc, cosine, arcCosine);
		log.setPreferredSize(new Dimension(20, 15));
		cp.add(cos, new RCPosition(3, 2));
		list.add(cos);

		UnaryOperator<Double> naturalLog = (num) -> Math.log(num);
		UnaryOperator<Double> invNaturalLog = (num) -> Math.pow(Math.E, num);
		UnaryOperationButton ln = new UnaryOperationButton("ln", "e^x", calc, naturalLog, invNaturalLog);
		ln.setPreferredSize(new Dimension(20, 15));
		cp.add(ln, new RCPosition(4, 1));
		list.add(ln);

		UnaryOperator<Double> tangens = (num) -> Math.tan(num);
		UnaryOperator<Double> arcTangens = (num) -> Math.atan(num);
		UnaryOperationButton tan = new UnaryOperationButton("tan", "arctan", calc, tangens, arcTangens);
		tan.setPreferredSize(new Dimension(20, 15));
		cp.add(tan, new RCPosition(4, 2));
		list.add(tan);

		UnaryOperator<Double> cotangent = (num) -> 1.0 / Math.tan(num);
		UnaryOperator<Double> arcCotangent = (num) -> Math.PI / 2 - Math.atan(num);
		UnaryOperationButton ctg = new UnaryOperationButton("ctg", "arcctg", calc, cotangent, arcCotangent);
		ctg.setPreferredSize(new Dimension(20, 15));
		cp.add(ctg, new RCPosition(5, 2));
		list.add(ctg);

		JButton push = new JButton("push");
		push.setPreferredSize(new Dimension(20, 15));
		push.addActionListener((e) -> {
			this.stack.push(calc.getValue());
		});
		cp.add(push, new RCPosition(3, 7));

		JButton pop = new JButton("pop");
		pop.setPreferredSize(new Dimension(20, 15));
		pop.addActionListener((e) -> {
			if (stack.isEmpty()) {
				System.out.println("Stack is empty. Can't pop.");
			} else {
				calc.setValue(this.stack.pop());
			}
		});
		cp.add(pop, new RCPosition(4, 7));

	}

	public static void main(String[] args) {
		calc = new CalcModelImpl();
		SwingUtilities.invokeLater(() -> {
			new CalculatorFrame().setVisible(true);
		});
	}
}
