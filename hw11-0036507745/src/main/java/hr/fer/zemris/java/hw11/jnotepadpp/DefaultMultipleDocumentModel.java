package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.nio.sctp.InvalidStreamException;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * 
 * Represents an implementation of the model which can store zero, one or more
 * documents. Also has the concept of the current document.
 * 
 * @author Lovro Matošević
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2515137507092721994L;

	/**
	 * List of stored documents
	 */
	private List<SingleDocumentModel> documents;
	/**
	 * The current document
	 */
	private SingleDocumentModel current;
	/**
	 * List of stored listeners
	 */
	private List<MultipleDocumentListener> listeners;
	/**
	 * Icon used to indicate if the document is modified
	 */
	private ImageIcon modified;
	/**
	 * Icon used to indicate that the document was not modified
	 */
	private ImageIcon notModified;
	/**
	 * Provider used for translation
	 */
	private ILocalizationProvider provider;

	/**
	 * The main constructor which accepts and sets the provider. Initializes the
	 * model.
	 * 
	 * @param provider - used for translation
	 */
	public DefaultMultipleDocumentModel(ILocalizationProvider provider) {
		this.provider = provider;
		this.loadIcons();
		this.documents = new ArrayList<>();
		this.listeners = new ArrayList<>();
		this.current = null;
		this.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				DefaultMultipleDocumentModel model = DefaultMultipleDocumentModel.this;
				try {
					SingleDocumentModel newCurrentModel = model.documents.get(model.getSelectedIndex());
					model.notifyListenersCurrentDocumentChanged(model.current, newCurrentModel);
					model.current = newCurrentModel;
				} catch (Exception ignorable) {
				}
			}
		});
	}

	/**
	 * Helper method used to load icons used by
	 * {@link DefaultMultipleDocumentModel}.
	 */
	private void loadIcons() {
		InputStream is = this.getClass().getResourceAsStream("icons/green_disk.png");
		if (is == null) {
			throw new InvalidStreamException();
		}
		try {
			byte[] bytes = is.readAllBytes();
			this.notModified = new ImageIcon(bytes);
			is = this.getClass().getResourceAsStream("icons/red_disk.png");
			bytes = is.readAllBytes();
			this.modified = new ImageIcon(bytes);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Returns the iterator which can be used for iterating over this model.
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new DocumentIterator();
	}

	/**
	 * Creates a new document
	 * 
	 * @return document - new document
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		Path path = null;
		String text = "";

		SingleDocumentModel document = new DefaultSingleDocumentModel(path, text);
		this.addModifiedListenerToDocument(document);
		this.documents.add(document);
		this.notifyListenersCurrentDocumentChanged(this.current, document);
		this.current = document;
		JScrollPane scrollPane = new JScrollPane(document.getTextComponent());
		this.addTab("unnamed", scrollPane);
		this.setSelectedIndex(this.getTabCount() - 1);
		this.setIconAt(this.getTabCount() - 1, this.modified);

		return document;
	}

	/**
	 * Returns the current document
	 * 
	 * @return document - the current document
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.current;
	}

	/**
	 * Loads a new document from the provided path
	 * 
	 * @param path - new document's location
	 * @return document - the resulting document
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);

		SingleDocumentModel document;

		if ((document = this.containsDocument(path)) != null) {
			this.setSelectedIndex(this.documents.indexOf(document));
			this.notifyListenersCurrentDocumentChanged(this.current, document);
			this.current = document;
			return document;
		}
		String text = null;
		try {
			text = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		document = new DefaultSingleDocumentModel(path, text);
		this.documents.add(document);
		JScrollPane scrollPane = new JScrollPane(document.getTextComponent());
		this.addTab(path.getFileName().toString(), scrollPane);
		this.setToolTipTextAt(this.getTabCount() - 1, path.toString());
		this.notifyListenersCurrentDocumentChanged(this.current, document);
		this.current = document;
		this.setSelectedIndex(this.documents.indexOf(document));
		this.setIconAt(this.getTabCount() - 1, this.modified);

		this.addModifiedListenerToDocument(document);

		return document;
	}

	/**
	 * Helper method used to add a listener which changes the icon when the provided
	 * document is modified or changes the tab title when the document's path is
	 * modified.
	 * 
	 */
	private void addModifiedListenerToDocument(SingleDocumentModel document) {
		document.addSingleDocumentListener(new SingleDocumentListener() {

			/**
			 * Used to set icons when the document is modified.
			 */
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				DefaultMultipleDocumentModel multipleModel = DefaultMultipleDocumentModel.this;
				ImageIcon icon;
				if (model.isModified()) {
					icon = multipleModel.modified;
				} else {
					icon = multipleModel.notModified;
				}
				multipleModel.setIconAt(multipleModel.documents.indexOf(model), icon);
			}

			/**
			 * Used to set tab title when the path is modified.
			 */
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				DefaultMultipleDocumentModel multipleModel = DefaultMultipleDocumentModel.this;
				multipleModel.setTitleAt(multipleModel.documents.indexOf(model),
						model.getFilePath().getFileName().toString());

			}
		});
	}

	/**
	 * Helper method used to return the document with the provided path if it
	 * exists.
	 * 
	 * @param path - path of the document which is being searched
	 * @return document - the document which was found or null if it does not exist
	 *         in this model
	 */
	private SingleDocumentModel containsDocument(Path path) {
		for (SingleDocumentModel document : this.documents) {
			if (document.getFilePath() == null) {
				continue;
			}
			if (document.getFilePath().equals(path)) {
				return document;
			}
		}
		return null;
	}

	/**
	 * Saves the current document to the provided path
	 * 
	 * @param model   - the document to be saved
	 * @param newPath - path where the document will be saved, can be null if the
	 *                current document's path should be used
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {

		Path path = newPath;
		if (newPath == null) {
			path = model.getFilePath();
		}

		if (this.containsDocument(path) != model && this.containsDocument(path) != null) {
			String[] options = { provider.getString("ok") };

			JOptionPane.showOptionDialog(this.getParent(), provider.getString("document_model_file_exists"),
					provider.getString("warning"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
					options, options[0]);
			return;

		}

		try {
			Files.writeString(path, model.getTextComponent().getText());
		} catch (IOException e) {
			String[] options = { provider.getString("ok") };

			JOptionPane.showOptionDialog(this.getParent(), provider.getString("document_model_writing_error"),
					provider.getString("error"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options,
					options[0]);
			return;
		}

		if (newPath != null) {
			String[] options = { provider.getString("ok") };

			JOptionPane.showOptionDialog(this.getParent(), provider.getString("document_model_file_saved"),
					provider.getString("information"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
					null, options, options[0]);
			model.setFilePath(newPath);
			this.setToolTipTextAt(this.getSelectedIndex(), newPath.toString());
		}
		model.setModified(false);
	}

	/**
	 * Closes the provided document
	 * 
	 * @param model - the document to be closed
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		this.removeTabAt(this.documents.indexOf(model));
		this.documents.remove(model);
		if (this.isEmpty()) {
			this.current = null;
			this.notifyListenersCurrentDocumentChanged(model, this.current);
			return;
		}
		this.current = this.documents.get(0);
		this.notifyListenersCurrentDocumentChanged(model, this.current);
	}

	/**
	 * Adds the provided listener to this model
	 * 
	 * @param l - listener to be added
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.add(l);
	}

	/**
	 * Removes the provided listener from this model
	 * 
	 * @param l - listener to be removed
	 */
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.remove(l);
	}

	/**
	 * Returns the number of stored documents
	 * 
	 * @return number - number of stored documents
	 */
	@Override
	public int getNumberOfDocuments() {
		return this.documents.size();
	}

	/**
	 * Returns the document stored at the provided index
	 * 
	 * @param index - index of the document
	 * @return document - the document at the provided index
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		return this.documents.get(index);
	}

	/**
	 * Helper method used to notify all the registered listener that the current
	 * document was changed.
	 * 
	 * @param previous - previous current document
	 * @param current  - current document
	 */
	private void notifyListenersCurrentDocumentChanged(SingleDocumentModel previous, SingleDocumentModel current) {

		for (var listener : this.listeners) {
			listener.currentDocumentChanged(previous, current);
		}
	}

	/**
	 * Helper method used to check if the document list is empty.
	 */
	public boolean isEmpty() {
		return this.documents.size() == 0;
	}

	/**
	 * 
	 * Iterator used for iterating over the documents which are stored in this
	 * model.
	 *
	 */
	private class DocumentIterator implements Iterator<SingleDocumentModel> {
		/**
		 * Tracks the index of the document which should be returned.
		 */
		private int current;
		/**
		 * List of documents which is being iterated.
		 */
		private List<SingleDocumentModel> modelList;

		/**
		 * Constructor which initializes the document list and the current index.
		 */
		public DocumentIterator() {
			this.current = 0;
			this.modelList = new ArrayList<>(DefaultMultipleDocumentModel.this.documents);
		}

		/**
		 * Checks if there are any remaining elements to be returned.
		 * 
		 * @return value - true if there are still elements to be returned and false
		 *         else
		 */
		@Override
		public boolean hasNext() {
			return this.current < modelList.size();
		}

		/**
		 * Returns the next document.
		 */
		@Override
		public SingleDocumentModel next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			SingleDocumentModel document = this.modelList.get(current);
			this.current++;
			return document;
		}

	}

}
