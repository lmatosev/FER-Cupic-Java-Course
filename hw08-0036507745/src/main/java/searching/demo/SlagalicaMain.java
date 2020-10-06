package searching.demo;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * A program which accepts the puzzle configuration and displays the solution on
 * the screen if the solution is found.
 * 
 * @author Lovro Matošević
 *
 */
public class SlagalicaMain {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Invalid number of arguments. Exiting");
			System.exit(1);
		}

		String conf = args[0];
		if (conf.length() != 9) {
			System.out.println("Invalid argument. Exiting.");
			System.exit(1);
		}
		if (conf.contains("0") && conf.contains("1") && conf.contains("2") && conf.contains("3") && conf.contains("4")
				&& conf.contains("5") && conf.contains("6") && conf.contains("7") && conf.contains("8")) {
			int[] polje = new int[9];
			for (int i = 0; i < polje.length; i++) {
				polje[i] = Integer.parseInt(String.valueOf(conf.charAt(i)));
			}
			Slagalica<KonfiguracijaSlagalice> slagalica = new Slagalica<>(new KonfiguracijaSlagalice(polje));
			Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfvs(slagalica, slagalica, slagalica);

			if (rješenje == null) {
				System.out.println("Nisam uspio pronaći rješenje.");
			} else {
				SlagalicaViewer.display(rješenje);
			}
		}

	}
}
