package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * 
 * Demonstrational program for the PrimListModel.
 * 
 * @author Lovro Matošević
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2552614965736703542L;

	/**
	 * Constructor which sets the size and effectively produces the frame.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		initGUI();
	}

	/**
	 * Helper method used to initialize the GUI
	 */
	private void initGUI() {
		Container cp = this.getContentPane();
		PrimListModel model = new PrimListModel();
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		cp.setLayout(new BorderLayout());
		JPanel upperPanel = new JPanel(new GridLayout(1, 2));
		cp.add(upperPanel, BorderLayout.CENTER);
		upperPanel.add(new JScrollPane(list1));
		upperPanel.add(new JScrollPane(list2));
		JButton next = new JButton("sljedeći");
		next.addActionListener(l -> {
			model.next();
		});
		cp.add(next, BorderLayout.PAGE_END);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
			;
		});

	}
}
