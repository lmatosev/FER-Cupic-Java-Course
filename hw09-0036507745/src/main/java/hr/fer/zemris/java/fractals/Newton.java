package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.*;

/**
 * 
 * Class used to draw fractals using the Newton-Raphson iteration.
 * 
 * @author Lovro Matošević
 *
 */
public class Newton {

	public static void main(String[] args) {

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		int i = 1;
		Scanner sc = new Scanner(System.in);
		List<Complex> lista = new ArrayList<>();

		while (true) {
			System.out.printf("Root %d> ", i);
			String line = sc.nextLine();

			if (line.isBlank()) {
				System.out.println("Invalid input.Exiting");
				sc.close();
				System.exit(1);
			}

			if (line.trim().equals("done")) {
				if (i > 2) {
					break;
				} else {
					System.out.println("Invalid input. There should be at least two roots. Exiting");
					sc.close();
					System.exit(1);
				}
			}

			try {
				Complex num = parseComplex(line);
				lista.add(num);
			} catch (Exception e) {
				System.out.println("Invalid input for complex root. Exiting");
				sc.close();
				System.exit(1);
			}
			i++;

		}
		sc.close();

		System.out.println("Image of fractal will apear shortly. Thank you.");

		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE,
				lista.toArray(new Complex[lista.size()]));

		IFractalProducer producer = new FractalNewtonProducer(crp);
		FractalViewer.show(producer);

	}

	/**
	 * 
	 * A class which produces the Newton fractal.
	 *
	 */
	private static class FractalNewtonProducer implements IFractalProducer {
		private static final int MAX_ITER = 500;
		private static final double CONVERGENCE_TRESHOLD = 0.001;
		private static final double ROOT_TRESHOLD = 0.002;
		private ComplexRootedPolynomial polynomial;
		private ExecutorService pool;

		/**
		 * Main constructor for the producer which accepts a ComplexRootedPolynomial
		 * 
		 * @param crp - polynomial that will be used while drawing
		 */
		public FractalNewtonProducer(ComplexRootedPolynomial crp) {
			this.polynomial = crp;
			this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					daemonicThreadFactory());
		}

		/**
		 * 
		 * Returns an instance of a factory which produces daemonic threads.
		 * 
		 */
		private ThreadFactory daemonicThreadFactory() {
			return new Factory();
		}

		/**
		 * 
		 * Factory for threads used while producing the fractal.
		 *
		 */
		private static class Factory implements ThreadFactory {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			int yRange = 8 * Runtime.getRuntime().availableProcessors();
			short[] data = new short[width * height];

			int yCount = height / yRange;
			List<Future<Void>> rezultati = new ArrayList<>();

			for (int i = 0; i < yRange; i++) {
				int yMin = i * yCount;
				int yMax = (i + 1) * yCount - 1;
				if (i == yCount - 1) {
					yMax = height - 1;
				}
				Callable<Void> job = new Job(yMin, yMax, width, height, reMin, reMax, imMin, imMax, data, polynomial);
				rezultati.add(pool.submit(job));
			}
			for (Future<Void> job : rezultati) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);
		}

		/**
		 * 
		 * Job used for drawing Newton fractals.
		 *
		 */
		private static class Job implements Callable<Void> {
			private int yMin;
			private int yMax;
			private int width;
			private int height;
			private double reMin;
			private double reMax;
			private double imMin;
			private double imMax;
			private ComplexRootedPolynomial rootedPolynomial;
			private short[] data;
			private ComplexPolynomial derived;
			private ComplexPolynomial polynomial;

			/**
			 * Main constructor for this class.
			 * 
			 * @param yMin       - minimal value for y
			 * @param yMax       - maximal value for y
			 * @param width      - image width
			 * @param height     - image height
			 * @param reMin      - minimal real
			 * @param reMax      - maximal real
			 * @param imMin      - minimal imaginary
			 * @param imMax      - maximal imaginary
			 * @param data       - data array
			 * @param polynomial - polynomial that will be used
			 */
			public Job(int yMin, int yMax, int width, int height, double reMin, double reMax, double imMin,
					double imMax, short[] data, ComplexRootedPolynomial polynomial) {
				this.yMin = yMin;
				this.yMax = yMax;
				this.width = width;
				this.height = height;
				this.reMin = reMin;
				this.reMax = reMax;
				this.imMin = imMin;
				this.imMax = imMax;
				this.rootedPolynomial = polynomial;
				this.data = data;
				this.derived = polynomial.toComplexPolynom().derive();
				this.polynomial = polynomial.toComplexPolynom();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Void call() throws Exception {

				for (int y = yMin; y < yMax + 1; y++) {
					int offset = y * width;
					for (int x = 0; x < width; x++) {
						Complex c = mapToComplexPlain(x, y, width, height, reMin, reMax, imMin, imMax);
						Complex zn = c;
						Complex znold;
						int iter = 0;
						double module;
						do {
							Complex numerator = polynomial.apply(zn);
							Complex denominator = derived.apply(zn);
							znold = zn;
							Complex fraction = numerator.divide(denominator);
							zn = zn.sub(fraction);
							module = znold.sub(zn).module();
							iter++;
						} while (module > CONVERGENCE_TRESHOLD && iter < MAX_ITER);
						int index = rootedPolynomial.indexOfClosestRootFor(zn, ROOT_TRESHOLD);
						data[offset++] = (short) (index + 1);
					}
				}

				return null;
			}

			/**
			 * Helper method used to map a point to the complex plain.
			 * 
			 * @param x      - the x coordinate
			 * @param y      - the y coordinate
			 * @param width  - image width
			 * @param height - image height
			 * @param reMin  - minimal real
			 * @param reMax  - maximal real
			 * @param imMin  - minimal imaginary
			 * @param imMax  - maximal imaginary
			 * @return complex - the resulting complex number
			 */
			private Complex mapToComplexPlain(int x, int y, int width, int height, double reMin, double reMax,
					double imMin, double imMax) {
				double re = ((double) x / width) * (reMax - reMin) + reMin;
				double im = ((double) (height - 1 - y) / height) * (imMax - imMin) + imMin;
				return new Complex(re, im);
			}

		}

	}

	/**
	 * Helper method used for parsing complex numbers.
	 * 
	 * @param s - the input string
	 * @return complex - the number that was parsed
	 */
	private static Complex parseComplex(String s) {

		if (s.isBlank() == true) {
			throw new IllegalArgumentException("Invalid input.");
		}
		if (s.contains("+-") || s.contains("-+")) {
			throw new IllegalArgumentException("Invalid input.");
		}

		String inputModified = s;
		Double real, imaginary;
		char sign = '+';

		if (s.charAt(0) == '-' || s.charAt(0) == '+') {
			sign = s.charAt(0);
			inputModified = s.substring(1);
		}

		int indexPositive = inputModified.indexOf("+");
		int indexNegative = inputModified.indexOf("-");

		if (indexPositive > 0) {
			String[] inputSplit = inputModified.split("\\+");
			if (sign == '-') {
				real = -1 * Double.parseDouble(inputSplit[0]);
			} else {
				real = Double.parseDouble(inputSplit[0]);
			}
			inputSplit[1] = inputSplit[1].replace("i", "");
			if (inputSplit[1].isBlank()) {
				imaginary = 1.0;
			} else {
				imaginary = Double.parseDouble(inputSplit[1]);
			}

		} else if (indexNegative > 0) {
			String[] inputSplit = inputModified.split("\\-");
			if (sign == '-') {
				real = -1 * Double.parseDouble(inputSplit[0]);
			} else {
				real = Double.parseDouble(inputSplit[0]);
			}
			inputSplit[1] = inputSplit[1].replace("i", "");
			if (inputSplit[1].isBlank()) {
				imaginary = -1.0;
			} else {
				imaginary = -1 * Double.parseDouble(inputSplit[1]);
			}
		} else if (s.contains("i") == false) {
			real = Double.parseDouble(s);
			imaginary = (double) 0;
		} else {
			if (inputModified.equals("i")) {
				if (sign == '+') {
					imaginary = 1.0;
				} else {
					imaginary = -1.0;
				}
			} else {
				inputModified = s.replace("i", "");
				StringBuilder sb = new StringBuilder();
				sb.append(inputModified);
				imaginary = Double.parseDouble(sb.toString());
			}
			real = (double) 0;
		}

		return new Complex(real, imaginary);
	}

}
