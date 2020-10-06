package coloring.algorithms;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used in implementing an algorithm to fill a part of the picture.
 * 
 * @author Lovro Matošević
 *
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel> {
	/**
	 * The reference pixel
	 */
	private Pixel reference;
	/**
	 * The reference picture
	 */
	private Picture picture;
	/**
	 * Color to be used for filling
	 */
	private int fillColor;
	/**
	 * Color of the reference pixel
	 */
	private int refColor;

	/**
	 * The main constructor which accepts a pixel, a picture and the fill color.
	 * 
	 * @param reference - the reference pixel
	 * @param picture   - the reference picture
	 * @param fillColor - the fill color
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.getX(), reference.getY());
	}

	/**
	 * Returns the reference pixel.
	 */
	@Override
	public Pixel get() {
		return this.reference;
	}

	/**
	 * Returns a list of neighbouring pixels.
	 */
	@Override
	public List<Pixel> apply(Pixel arg0) {
		List<Pixel> lista = new ArrayList<>();
		if (arg0.getX() < picture.getWidth() - 1 && arg0.getX() > 1 && arg0.getY() > 01
				&& arg0.getY() < picture.getHeight() - 1) {
			Pixel p1 = new Pixel(arg0.getX(), arg0.getY() + 1);
			Pixel p2 = new Pixel(arg0.getX(), arg0.getY() - 1);
			Pixel p3 = new Pixel(arg0.getX() + 1, arg0.getY());
			Pixel p4 = new Pixel(arg0.getX() - 1, arg0.getY());
			lista.add(p1);
			lista.add(p2);
			lista.add(p3);
			lista.add(p4);
		}
		return lista;
	}

	/**
	 * Sets the provided pixel color to the fill color.
	 */
	@Override
	public void accept(Pixel arg0) {
		picture.setPixelColor(arg0.getX(), arg0.getY(), this.fillColor);
	}

	/**
	 * Checks if the provided pixel is of the same color as the reference pixel.
	 * Returns true if it is and false else.
	 */
	@Override
	public boolean test(Pixel arg0) {
		if (this.picture.getPixelColor(arg0.getX(), arg0.getY()) == this.refColor) {
			return true;
		}
		return false;
	}

}
