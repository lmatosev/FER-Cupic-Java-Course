package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	void preferedSizeTest1() {
		JPanel p = new JPanel(new CalcLayout(2));

		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));

		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	void preferedSizeTest2() {
		JPanel p = new JPanel(new CalcLayout(2));

		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));

		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	void constraintExceptionTest1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));

		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(0, 2)));
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(4, 26)));
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(7, 8)));
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(-4, -25)));
	}

	@Test
	void constraintExceptionTest2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));

		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(1, 4)));
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(1, 2)));
	}

	@Test
	void constraintExceptionTest3() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));

		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 40));

		p.add(l1,new RCPosition(2, 2));
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(2, 2)));
	}

}
