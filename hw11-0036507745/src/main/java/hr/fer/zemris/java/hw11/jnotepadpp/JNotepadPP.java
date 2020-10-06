package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

import com.sun.nio.sctp.InvalidStreamException;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.AscendingSortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CreateAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.DescendingSortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.InvertCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatisticsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToLowercaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToUppercaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UniqueAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * 
 * A simple file text editor. JNotepad++ allows the user to work with multiple
 * documents at the same time. A number of tools for editing the text are also
 * available. Language can be changed from the Languages/Jezici/Sprache menu.
 * 
 * @author Lovro Matošević
 *
 */

public class JNotepadPP extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 751653141532945136L;

	/**
	 * Model capable of holding multiple documents.
	 */
	private MultipleDocumentModel model;
	/**
	 * Action used to create a new file
	 */
	private Action create;
	/**
	 * Action used to open a new file
	 */
	private Action open;
	/**
	 * Action used to save the current file
	 */
	private Action save;
	/**
	 * Action used to save the current file to the chosen location
	 */
	private Action saveAs;
	/**
	 * Action used to close the current document
	 */
	private Action close;
	/**
	 * Action used to cut the selected part of text
	 */
	private Action cut;
	/**
	 * Action used to copy the selected part of text
	 */
	private Action copy;
	/**
	 * Action used to paste text to where the caret is pointing
	 */
	private Action paste;
	/**
	 * Action used when exiting the program
	 */
	private Action exit;
	/**
	 * Action used to show the statistics of the current file
	 */
	private Action statistics;
	/**
	 * Action used to switch the selected part of the text to upper case
	 */
	private Action toUppercase;
	/**
	 * Action used to switch the selected part of the text to lower case
	 */
	private Action toLowercase;
	/**
	 * Action used to invert the case of the selected part of the text
	 */
	private Action invertCase;
	/**
	 * Action used to sort the selected part of the text ascending
	 */
	private Action ascending;
	/**
	 * Action used to sort the selected part of the the text descending
	 */
	private Action descending;
	/**
	 * Action used to remove all duplicate lines from the selection
	 */
	private Action unique;
	/**
	 * Icon used for toolbar's new document button
	 */
	private ImageIcon newDocumentIcon;
	/**
	 * Icon used for toolbar's open document button
	 */
	private ImageIcon openDocumentIcon;
	/**
	 * Icon used for toolbar's save document button
	 */
	private ImageIcon saveDocumentIcon;
	/**
	 * Icon used for toolbar's statistics button
	 */
	private ImageIcon statisticsDocumentIcon;
	/**
	 * Icon used for toolbar's close button
	 */
	private ImageIcon closeIcon;
	/**
	 * The main panel for the frame
	 */
	private JPanel panel;
	/**
	 * Panel used as a status bar
	 */
	private JPanel statusBar;
	/**
	 * Listener for the caret
	 */
	private CaretListener listener;
	/**
	 * Label indicating the length of the current document
	 */
	private JLabel length;
	/**
	 * Label indicating how long is the selection and in which column and line the
	 * caret is in the current document
	 */
	private JLabel selection;
	/**
	 * Timer used for updating the clock on the status bar
	 */
	private Timer timer;
	/**
	 * Localization provider used for translation
	 */
	private FormLocalizationProvider flp;

	/**
	 * Main constructor for the {@link JNotepadPP}.
	 */
	public JNotepadPP() {

		setTitle("JNotepad++");
		setSize(1000, 500);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		this.flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				JNotepadPP.this.exit.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
				timer.stop();
			}
		});

		initGUI();
	}

	/**
	 * Helper method used to initialize the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		model = new DefaultMultipleDocumentModel(flp);
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		cp.add(panel);
		panel.add((JTabbedPane) model, BorderLayout.CENTER);

		JTabbedPane tabbedPane = (JTabbedPane) model;
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				StringBuilder sb = new StringBuilder();
				try {
					if (model.getDocument(tabbedPane.getSelectedIndex()).getFilePath() == null) {
						sb.append("(unnamed) - ");
					} else {
						sb.append(model.getDocument(tabbedPane.getSelectedIndex()).getFilePath().toString())
								.append(" - ");
					}
				} catch (Exception ignorable) {
				}
				sb.append("JNotepad++");
				JNotepadPP.this.setTitle(sb.toString());
			}
		});

		loadIcons();
		createActions();
		createMenu();
		createToolbar();
		createStatusBar();

		listener = new CaretStatusBarListener(flp);

		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (currentModel != null) {
					listener.caretUpdate(new CaretEvent(currentModel.getTextComponent()) {

						/**
						 * 
						 */
						private static final long serialVersionUID = 6674085363598676094L;

						@Override
						public int getMark() {
							return currentModel.getTextComponent().getCaret().getMark();
						}

						@Override
						public int getDot() {
							return currentModel.getTextComponent().getCaret().getDot();
						}
					});
				}

				if (previousModel == null && currentModel != null) {
					currentModel.getTextComponent().addCaretListener(listener);
				} else if (currentModel == null && previousModel != null) {
					previousModel.getTextComponent().removeCaretListener(listener);
				} else if (currentModel != null && previousModel != null) {
					previousModel.getTextComponent().removeCaretListener(listener);
					currentModel.getTextComponent().addCaretListener(listener);
				} else {
					throw new IllegalArgumentException("Both arguments cannot be null");
				}

				if (currentModel == null) {
					save.setEnabled(false);
					saveAs.setEnabled(false);
					close.setEnabled(false);
					statistics.setEnabled(false);
				}
			}
		});
	}

	/**
	 * 
	 * Listener used to update the menu items and status bar labels when the caret
	 * changes.
	 *
	 */
	private class CaretStatusBarListener implements CaretListener {

		/**
		 * Localization provider used for translation
		 */
		private ILocalizationProvider provider;

		/**
		 * Constructor which accepts and sets the provider.
		 * 
		 * @param provider - used for translation
		 */
		public CaretStatusBarListener(ILocalizationProvider provider) {
			this.provider = provider;
		}

		/**
		 * Updates menu items and status bar labels depending on the caret selection
		 */
		@Override
		public void caretUpdate(CaretEvent e) {
			JTextArea editor = (JTextArea) e.getSource();
			int length = editor.getText().length();
			int line = 1;
			int column = 1;

			int caretPosition = editor.getCaretPosition();
			try {
				line = editor.getLineOfOffset(caretPosition);
				column = caretPosition - editor.getLineStartOffset(line);
				line++;
				column++;
			} catch (BadLocationException ignorable) {
			}

			int selection = Math.abs(e.getMark() - e.getDot());

			if (selection != 0) {
				toUppercase.setEnabled(true);
				toLowercase.setEnabled(true);
				invertCase.setEnabled(true);
				ascending.setEnabled(true);
				descending.setEnabled(true);
				unique.setEnabled(true);
			} else {
				toUppercase.setEnabled(false);
				toLowercase.setEnabled(false);
				invertCase.setEnabled(false);
				ascending.setEnabled(false);
				descending.setEnabled(false);
				unique.setEnabled(false);
			}

			updateStatusBarInfo(length, line, column, selection);
		}

		/**
		 * Helper method used to update the status bar labels
		 * 
		 * @param length    - length to be set
		 * @param line      - line number to be set
		 * @param column    - column number to be set
		 * @param selection - length of the selection to be set
		 */
		private void updateStatusBarInfo(int length, int line, int column, int selection) {
			JNotepadPP notepad = JNotepadPP.this;

			notepad.length.setText(CaretStatusBarListener.this.provider.getString("length") + ":" + length);

			StringBuilder sb = new StringBuilder();
			sb.append("Ln:").append(line);
			sb.append(" Col:").append(column);
			sb.append(" Sel:").append(selection);

			notepad.selection.setText(sb.toString());
		}

	}

	/**
	 * Helper method used to load icons which the {@link JNotepadPP} uses.
	 */
	private void loadIcons() {

		InputStream is = this.getClass().getResourceAsStream("icons/new.png");
		if (is == null) {
			throw new InvalidStreamException();
		}
		try {
			byte[] bytes = is.readAllBytes();
			this.newDocumentIcon = new ImageIcon(bytes);
			is = this.getClass().getResourceAsStream("icons/open.png");
			bytes = is.readAllBytes();
			this.openDocumentIcon = new ImageIcon(bytes);
			is = this.getClass().getResourceAsStream("icons/save.png");
			bytes = is.readAllBytes();
			this.saveDocumentIcon = new ImageIcon(bytes);
			is = this.getClass().getResourceAsStream("icons/statistics.png");
			bytes = is.readAllBytes();
			this.statisticsDocumentIcon = new ImageIcon(bytes);
			is = this.getClass().getResourceAsStream("icons/close.png");
			bytes = is.readAllBytes();
			this.closeIcon = new ImageIcon(bytes);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Helper method used to create and initialize the actions.
	 */
	private void createActions() {
		this.create = new CreateAction("create", flp, model);

		this.open = new OpenAction("open", flp, model, this);

		this.saveAs = new SaveAsAction("save_as", flp, model, this);
		this.saveAs.setEnabled(false);

		this.save = new SaveAction("save", flp, model, this.saveAs);
		this.save.setEnabled(false);

		this.close = new CloseAction("close", flp, model, this, this.saveAs);
		this.close.setEnabled(false);

		this.exit = new ExitAction("exit", flp, model, this, this.saveAs);

		this.statistics = new StatisticsAction("statistics", flp, model, this);
		this.statistics.setEnabled(false);

		this.cut = new CutAction("cut", flp);

		this.copy = new CopyAction("copy", flp);

		this.paste = new PasteAction("paste", flp);

		this.toLowercase = new ToLowercaseAction("lowercase", flp, this.model);
		this.toLowercase.setEnabled(false);

		this.toUppercase = new ToUppercaseAction("uppercase", flp, this.model);
		this.toUppercase.setEnabled(false);

		this.invertCase = new InvertCaseAction("invertcase", flp, this.model);
		this.invertCase.setEnabled(false);

		this.ascending = new AscendingSortAction("ascending_sort", flp, this.model);
		this.ascending.setEnabled(false);

		this.descending = new DescendingSortAction("descending_sort", flp, this.model);
		this.descending.setEnabled(false);

		this.unique = new UniqueAction("unique_action", flp, this.model);
		this.unique.setEnabled(false);

	}

	/**
	 * Helper method used to create the menu which is shown on the top of the
	 * {@link JNotepadPP}
	 */
	private void createMenu() {
		JMenuBar mb = new JMenuBar();

		JMenu file = new JMenu(flp.getString("file"));
		mb.add(file);

		JMenuItem createItem = new JMenuItem(create);
		file.add(createItem);

		JMenuItem openItem = new JMenuItem(open);
		file.add(openItem);

		JMenuItem saveItem = new JMenuItem(save);
		file.add(saveItem);

		JMenuItem saveAsItem = new JMenuItem(saveAs);
		file.add(saveAsItem);

		file.addSeparator();

		JMenuItem closeItem = new JMenuItem(close);
		file.add(closeItem);

		file.add(new JMenuItem(exit));

		JMenu edit = new JMenu(flp.getString("edit"));
		mb.add(edit);
		edit.add(new JMenuItem(cut));
		edit.add(new JMenuItem(copy));
		edit.add(new JMenuItem(paste));

		JMenuItem statisticsItem = new JMenuItem(statistics);
		edit.add(statisticsItem);

		createItem.addActionListener((l) -> {
			save.setEnabled(true);
			saveAs.setEnabled(true);
			statistics.setEnabled(true);
			close.setEnabled(true);
		});

		openItem.addActionListener((l) -> {
			save.setEnabled(true);
			saveAs.setEnabled(true);
			statistics.setEnabled(true);
			close.setEnabled(true);
		});

		JMenu languages = new JMenu("Languages/Jezici/Sprache");

		mb.add(languages);

		JMenuItem english = new JMenuItem("English");
		english.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		});
		languages.add(english);

		JMenuItem german = new JMenuItem("Deutsch");
		german.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		});
		languages.add(german);

		JMenuItem croatian = new JMenuItem("Hrvatski");
		croatian.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		});
		languages.add(croatian);

		JMenu tools = new JMenu(flp.getString("tools"));

		mb.add(tools);
		JMenu changeCase = new JMenu(flp.getString("change_case"));

		tools.add(changeCase);

		JMenuItem lowercase = new JMenuItem(toLowercase);
		changeCase.add(lowercase);

		JMenuItem uppercase = new JMenuItem(toUppercase);
		changeCase.add(uppercase);

		JMenuItem invertcase = new JMenuItem(invertCase);
		changeCase.add(invertcase);

		JMenu sort = new JMenu(flp.getString("sort"));
		tools.add(sort);

		JMenuItem ascendingItem = new JMenuItem(ascending);
		sort.add(ascendingItem);

		JMenuItem descendingItem = new JMenuItem(descending);
		sort.add(descendingItem);

		JMenuItem uniqueItem = new JMenuItem(unique);
		tools.add(uniqueItem);

		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				file.setText(flp.getString("file"));
				edit.setText(flp.getString("edit"));
				tools.setText(flp.getString("tools"));
				changeCase.setText(flp.getString("change_case"));
				sort.setText(flp.getString("sort"));
				length.setText(flp.getString("length") + ":0");
			}
		});

		setJMenuBar(mb);

	}

	/**
	 * Helper method used to create the {@link JNotepadPP}'s toolbar.
	 */
	private void createToolbar() {

		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		JButton createButton = this.buttonCreator(create);
		createButton.setIcon(newDocumentIcon);
		createButton.addActionListener(l -> {
			save.setEnabled(true);
			saveAs.setEnabled(true);
			statistics.setEnabled(true);
			close.setEnabled(true);
		});

		tb.add(createButton);

		JButton openButton = this.buttonCreator(open);
		openButton.setIcon(openDocumentIcon);
		openButton.addActionListener(l -> {
			save.setEnabled(true);
			saveAs.setEnabled(true);
			statistics.setEnabled(true);
			close.setEnabled(true);
		});

		tb.add(openButton);

		JButton saveButton = this.buttonCreator(save);
		saveButton.setIcon(saveDocumentIcon);
		tb.add(saveButton);

		JButton statisticsButton = this.buttonCreator(statistics);
		statisticsButton.setIcon(statisticsDocumentIcon);
		tb.add(statisticsButton);

		JButton closeButton = this.buttonCreator(close);
		closeButton.setIcon(closeIcon);
		tb.add(closeButton);

		getContentPane().add(tb, BorderLayout.PAGE_START);

	}

	/**
	 * Helper method used to set all the buttons.
	 * 
	 * @param action - action to be linked with the button
	 * @return button - the resulting button
	 */
	private JButton buttonCreator(Action action) {
		JButton button = new JButton(action);
		button.setBorderPainted(false);
		button.setBorder(null);
		button.setMargin(new Insets(0, 0, 0, 0));
		return button;
	}

	/**
	 * Helper method used to create {@link JNotepadPP}'s status bar.
	 */
	private void createStatusBar() {

		this.statusBar = new JPanel();
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));

		this.length = new JLabel("Length:0");
		statusBar.add(this.length);
		statusBar.add(new JSeparator());
		this.selection = new JLabel("Ln:0 Col:0 Sel:0");
		statusBar.add(this.selection);
		statusBar.add(new JSeparator());

		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		JLabel clock = new JLabel(formatter.format(new Date()));
		ActionListener updateClock = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clock.setText(formatter.format(new Date()));
			}
		};
		timer = new Timer(1000, updateClock);
		timer.start();
		statusBar.add(clock);

		panel.add(statusBar, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

}
