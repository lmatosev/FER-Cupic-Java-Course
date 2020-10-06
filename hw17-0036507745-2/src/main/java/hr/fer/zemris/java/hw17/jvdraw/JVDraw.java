package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.function.Supplier;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw17.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.SaveAsAction;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * 
 * JVDraw is a simple GUI application for vector graphics. It enables the user
 * to choose foreground and background colors in the toolbar and then draw
 * lines, circles or filled circles.
 * 
 * 
 * @author Lovro Matošević
 *
 */

public class JVDraw extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8099367857404451577L;

	/**
	 * tool currently in use
	 */
	private Tool currentState;
	/**
	 * tool for painting lines
	 */
	private Tool lineTool;
	/**
	 * tool for painting circles
	 */
	private Tool circleTool;
	/**
	 * tool for painting filled circles
	 */
	private Tool filledCircleTool;
	/**
	 * canvas being used
	 */
	private JDrawingCanvas canvas;
	/**
	 * drawing model used
	 */
	private DrawingModel drawingModel;
	/**
	 * Path where the .jvd file is saved
	 */
	private Path savedPath;
	/**
	 * Action used for exiting the program
	 */
	private Action exit;

	/**
	 * Main constructor for this class.
	 */
	public JVDraw() {

		setTitle("JVDraw");
		setSize(1000, 750);
		this.setLocationRelativeTo(null);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				JVDraw.this.exit.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
			}
		});

		initGUI();
	}

	/**
	 * Helper method used to initialize the GUI
	 */
	private void initGUI() {

		this.drawingModel = new DrawingModelImpl();

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		cp.add(panel, BorderLayout.CENTER);

		this.canvas = new JDrawingCanvas(new Supplier<Tool>() {
			@Override
			public Tool get() {
				return JVDraw.this.currentState;
			}
		}, drawingModel);

		JList<GeometricalObject> list = new JList<>(new DrawingObjectListModel(drawingModel));
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				@SuppressWarnings("unchecked")
				JList<GeometricalObject> list = (JList<GeometricalObject>) e.getSource();
				if (e.getClickCount() == 2) {

					Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex());

					if (r.contains(e.getPoint())) {

						int index = list.locationToIndex(e.getPoint());

						GeometricalObject obj = list.getModel().getElementAt(index);
						GeometricalObjectEditor editor = obj.createGeometricalObjectEditor();

						if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit",
								JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
							try {
								editor.checkEditing();
								editor.acceptEditing();
							} catch (Exception ex) {
								Object[] options = { "OK" };
								JOptionPane.showOptionDialog(JVDraw.this, ex.getMessage(), "Error",
										JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE, null, options,
										options[0]);
							}

						}

					}

				}
			}
		});

		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				@SuppressWarnings("unchecked")
				JList<GeometricalObject> list = (JList<GeometricalObject>) e.getSource();

				if (!list.isSelectionEmpty()) {

					GeometricalObject obj = (GeometricalObject) list.getSelectedValue();

					if (e.getKeyChar() == '+') {

						JVDraw.this.drawingModel.changeOrder(obj, 1);
						list.setSelectedIndex(JVDraw.this.drawingModel.indexOf(obj));

					} else if (e.getKeyChar() == '-') {

						JVDraw.this.drawingModel.changeOrder(obj, -1);
						list.setSelectedIndex(JVDraw.this.drawingModel.indexOf(obj));

					} else if (e.getKeyCode() == KeyEvent.VK_DELETE) {

						JVDraw.this.drawingModel.remove(obj);

					}

				}
			}
		});
		JScrollPane pane = new JScrollPane(list);

		createMenu();
		createToolbar(panel);

		canvas.setBorder(BorderFactory.createLineBorder(Color.black));
		pane.setBorder(BorderFactory.createLineBorder(Color.black));

		panel.add(pane, BorderLayout.EAST);
		panel.add(canvas, BorderLayout.WEST);

	}

	/**
	 * Private method used to create the toolbar.
	 * 
	 * @param panel - panel on which the toolbar will be placed
	 */
	private void createToolbar(JPanel panel) {

		panel.setLayout(new BorderLayout());

		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		JColorArea fgColorArea = new JColorArea(Color.red);
		JColorArea bgColorArea = new JColorArea(Color.blue);

		ColorLabel label = new ColorLabel(fgColorArea, bgColorArea);

		getContentPane().add(label, BorderLayout.PAGE_END);

		tb.add(fgColorArea);
		tb.addSeparator();
		tb.add(bgColorArea);
		tb.addSeparator();

		JToggleButton lineButton = new JToggleButton("Line");
		JToggleButton circleButton = new JToggleButton("Circle");
		JToggleButton filledCircleButton = new JToggleButton("Filled circle");

		lineButton.setFont(new Font("Arial", Font.PLAIN, 18));
		circleButton.setFont(new Font("Arial", Font.PLAIN, 18));
		filledCircleButton.setFont(new Font("Arial", Font.PLAIN, 18));

		this.lineTool = new LineTool(drawingModel, fgColorArea, canvas);
		this.circleTool = new CircleTool(drawingModel, fgColorArea, canvas);
		this.filledCircleTool = new FilledCircleTool(drawingModel, bgColorArea, fgColorArea, canvas);

		lineButton.setSelected(true);
		this.currentState = lineTool;

		lineButton.addActionListener(l -> {
			this.currentState = lineTool;
		});

		circleButton.addActionListener(l -> {
			this.currentState = circleTool;
		});

		filledCircleButton.addActionListener(l -> {
			this.currentState = filledCircleTool;
		});

		ButtonGroup group = new ButtonGroup();

		group.add(lineButton);
		group.add(circleButton);
		group.add(filledCircleButton);

		tb.add(lineButton);
		tb.addSeparator();
		tb.add(circleButton);
		tb.addSeparator();
		tb.add(filledCircleButton);

		panel.add(tb, BorderLayout.PAGE_START);

	}

	/**
	 * Helper method used to create the menu.
	 */
	private void createMenu() {

		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("File");

		Action openAction = new OpenAction(this, this.drawingModel);

		Action saveAction = new SaveAction(this, this.drawingModel);

		Action saveAsAction = new SaveAsAction(this, this.drawingModel);

		Action exportAction = new ExportAction(this, this.drawingModel);

		Action exitAction = new ExitAction(this, this.drawingModel);

		this.exit = exitAction;

		JMenuItem open = new JMenuItem(openAction);

		JMenuItem save = new JMenuItem(saveAction);

		JMenuItem saveAs = new JMenuItem(saveAsAction);

		JMenuItem export = new JMenuItem(exportAction);

		JMenuItem exit = new JMenuItem(exitAction);

		menu.add(open);
		menu.add(save);
		menu.add(saveAs);
		menu.add(export);
		menu.add(exit);

		mb.add(menu);

		getContentPane().add(mb, BorderLayout.NORTH);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});

	}

	/**
	 * @return the savedPath
	 */
	public Path getSavedPath() {
		return savedPath;
	}

	/**
	 * @param savedPath the savedPath to set
	 */
	public void setSavedPath(Path savedPath) {
		this.savedPath = savedPath;
	}

}
