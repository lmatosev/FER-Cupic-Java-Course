package coloring.demo;

import java.util.Arrays;

import coloring.algorithms.Coloring;
import coloring.algorithms.Pixel;
import coloring.algorithms.SubspaceExploreUtil;
import marcupic.opjj.statespace.coloring.FillAlgorithm;
import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;

/**
 * The second demonstrational program for the coloring app. Includes 3 custom
 * fill algorithms.
 */
public class Bojanje2 {

	public static void main(String[] args) {
		FillApp.run(FillApp.OWL, Arrays.asList(bfs));
	}

	/**
	 * A fill algorithm using bfs.
	 */
	private static final FillAlgorithm bfs = new FillAlgorithm() {

		/**
		 * Returns the algorithm name.
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfs!";
		}

		/**
		 * Fills the picture area.
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfs(col, col, col, col);
		}
	};

	/**
	 * A fill algorithm using dfs.
	 */
	private static final FillAlgorithm dfs = new FillAlgorithm() {

		/**
		 * Returns the algorithm name.
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj dfs!";
		}

		/**
		 * Fills the picture area.
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.dfs(col, col, col, col);
		}
	};

	/**
	 * A fill algorithm using bfvs.
	 */
	private static final FillAlgorithm bfvs = new FillAlgorithm() {

		/**
		 * Returns the algorithm name.
		 */
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfvs!";
		}

		/**
		 * Fills the picture area.
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfsv(col, col, col, col);
		}
	};
}
