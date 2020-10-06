package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * 
 * Action used for cutting the selected part of text.
 * 
 * @author Lovro Matošević
 *
 */
public class CutAction extends LocalizableAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9126172137820890315L;

	
	/**
	 * Main constructor which accepts a string key which is used to set the
	 * localized name of the action and a provider.
	 * 
	 * @param key      - key used for localized name
	 * @param provider - provider used for localization
	 */
	public CutAction(String key, ILocalizationProvider provider) {
		super(key,provider);
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_CONTROL | KeyEvent.VK_X);
		this.putValue(Action.SHORT_DESCRIPTION, provider.getString("cut_description"));
		provider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, provider.getString("cut_description"));
			}
		});
	}

	/**
	 * Cuts the selected part of the text.
	 */
	@Override
	public void actionPerformed(ActionEvent arg) {

		new DefaultEditorKit.CutAction().actionPerformed(arg);

	}

}
