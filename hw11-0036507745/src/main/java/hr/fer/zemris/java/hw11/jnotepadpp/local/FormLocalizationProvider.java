package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * 
 * This class is used to provide effective communication between the
 * localization classes and an instance of {@link JFrame}. When frame is opened,
 * connect is called and when it is closed, disconnect is called.
 * 
 * @author Lovro Matošević
 *
 */

public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * 
	 * The main constructor which accepts an {@link ILocalizationProvider} and a {@link JFrame}.
	 * 
	 * @param provider - the provider which will be used for translation
	 * @param frame - frame which will be registered
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);

		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				connect();
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				disconnect();
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

}
