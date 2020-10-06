package searching.slagalica;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * Class used to represent and solve the puzzle.
 * 
 * @author Lovro Matošević
 *
 */
public class Slagalica<S> implements Supplier<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {
	private KonfiguracijaSlagalice konf;

	/**
	 * The constructor which accepts a puzzle configuration.
	 * 
	 * @param konfiguracija
	 */
	public Slagalica(KonfiguracijaSlagalice konfiguracija) {
		this.konf = konfiguracija;
	}

	/**
	 * Used to test if the provided configuration is equal to the final
	 * configuration.
	 */
	@Override
	public boolean test(KonfiguracijaSlagalice arg0) {
		int[] polje = arg0.getPolje();
		for (int i = 0; i < polje.length - 1; i++) {
			if (polje[i] != i + 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Tries all possible valid moves. And returns a list of transitions.
	 */
	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice arg0) {
		int index = arg0.indexOfSpace();
		List<Transition<KonfiguracijaSlagalice>> lista = new ArrayList<>();
		Transition<KonfiguracijaSlagalice> tr1;
		Transition<KonfiguracijaSlagalice> tr2;
		Transition<KonfiguracijaSlagalice> tr3;
		Transition<KonfiguracijaSlagalice> tr4;
		int[] poljeOrig = arg0.getPolje();
		int[] polje;
		switch (index) {
		case 0:
			polje = this.switchPlaces(poljeOrig, 0, 1);
			tr1 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 0, 3);
			tr2 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			lista.add(tr1);
			lista.add(tr2);
			return lista;
		case 1:
			polje = this.switchPlaces(poljeOrig, 1, 0);
			tr1 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 1, 2);
			tr2 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 1, 4);
			tr3 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			lista.add(tr1);
			lista.add(tr2);
			lista.add(tr3);
			return lista;
		case 2:
			polje = this.switchPlaces(poljeOrig, 2, 1);
			tr1 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 2, 5);
			tr2 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			lista.add(tr1);
			lista.add(tr2);
			return lista;
		case 3:
			polje = this.switchPlaces(poljeOrig, 3, 0);
			tr1 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 3, 4);
			tr2 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 3, 6);
			tr3 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			lista.add(tr1);
			lista.add(tr2);
			lista.add(tr3);
			return lista;
		case 4:
			polje = this.switchPlaces(poljeOrig, 4, 1);
			tr1 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 4, 3);
			tr2 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 4, 5);
			tr3 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 4, 7);
			tr4 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			lista.add(tr1);
			lista.add(tr2);
			lista.add(tr3);
			lista.add(tr4);
			return lista;
		case 5:
			polje = this.switchPlaces(poljeOrig, 5, 2);
			tr1 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 5, 4);
			tr2 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 5, 8);
			tr3 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			lista.add(tr1);
			lista.add(tr2);
			lista.add(tr3);
			return lista;
		case 6:
			polje = this.switchPlaces(poljeOrig, 6, 3);
			tr1 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 6, 7);
			tr2 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			lista.add(tr1);
			lista.add(tr2);
			return lista;
		case 7:
			polje = this.switchPlaces(poljeOrig, 7, 4);
			tr1 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 7, 6);
			tr2 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 7, 8);
			tr3 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			lista.add(tr1);
			lista.add(tr2);
			lista.add(tr3);
			return lista;
		case 8:
			polje = this.switchPlaces(poljeOrig, 8, 7);
			tr1 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			polje = this.switchPlaces(poljeOrig, 8, 5);
			tr2 = new Transition<>(new KonfiguracijaSlagalice(polje), 1);
			lista.add(tr1);
			lista.add(tr2);
			return lista;
		default:
			break;
		}
		return lista;
	}

	/**
	 * Used for switching the places of two fields.
	 * 
	 * @param poljeOrig - the original configuration
	 * @param i         - the first index to be switched
	 * @param j         - the second index to be switched
	 * @return polje - new configuration after switching the 2 elements
	 */
	private int[] switchPlaces(int[] poljeOrig, int i, int j) {
		int[] polje = new int[9];
		for (int k = 0; k < poljeOrig.length; k++) {
			polje[k] = poljeOrig[k];
		}
		int temp = polje[j];
		polje[j] = polje[i];
		polje[i] = temp;
		return polje;
	}

	/**
	 * Returns the current configuration.
	 */
	@Override
	public KonfiguracijaSlagalice get() {
		return this.konf;
	}

}
