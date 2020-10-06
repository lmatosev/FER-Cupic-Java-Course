package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.raytracer.model.*;

import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * 
 * Class implementing a simple ray caster. Speeds up the calculations using
 * parallelization.
 * 
 * @author Lovro Matošević
 *
 */
public class RayCasterParallel2 {

	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30);
	}

	/**
	 * Creates an animator for the ray tracer.
	 * 
	 * @return animator - animator used for animating the image produced by the
	 *         tracer
	 */
	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			long time;

			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}

			@Override
			public Point3D getViewUp() {
				return new Point3D(0, 0, 10);
			}

			@Override
			public Point3D getView() {
				return new Point3D(-2, 0, -0.5);
			}

			@Override
			public long getTargetTimeFrameDuration() {
				return 150;
			}

			@Override
			public Point3D getEye() {
				double t = (double) time / 10000 * 2 * Math.PI;
				double t2 = (double) time / 5000 * 2 * Math.PI;
				double x = 50 * Math.cos(t);
				double y = 50 * Math.sin(t);
				double z = 30 * Math.sin(t2);
				return new Point3D(x, y, z);
			}
		};
	}

	/**
	 * Produces a ray tracer.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D zAxis = view.sub(eye).normalize();
				Point3D vuv = viewUp.normalize();
				Point3D j = vuv.sub(zAxis.scalarMultiply(zAxis.scalarProduct(vuv)));

				Point3D yAxis = j.normalize();

				Point3D i = zAxis.vectorProduct(j);

				Point3D xAxis = i.normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene2();

				ForkJoinPool pool = new ForkJoinPool();
				ThreadJob job = new ThreadJob(eye, horizontal, vertical, width, height, red, green, blue, xAxis, yAxis,
						zAxis, screenCorner, scene, 0, width);
				pool.invoke(job);
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");

			}

			/**
			 * Tracer for this ray caster.
			 * 
			 * @param scene - scene being used
			 * @param ray   - ray from the eye viewpoint
			 * @param rgb   - color
			 */
			protected void tracer(Scene scene, Ray ray, short[] rgb) {
				rgb[0] = 0;
				rgb[1] = 0;
				rgb[2] = 0;

				RayIntersection closest = findClosestIntersection(scene, ray);

				if (closest == null) {
					return;
				}

				short[] color = determineColorFor(scene, closest, ray);

				rgb[0] = color[0];
				rgb[1] = color[1];
				rgb[2] = color[2];

			}

			/**
			 * Used to determine colors.
			 * 
			 * @param scene        - scene that is being used
			 * @param intersection - intersection which was found
			 * @param ray          - ray from the eye viewpoint
			 */
			private short[] determineColorFor(Scene scene, RayIntersection intersection, Ray ray) {
				short[] color = new short[3];
				color[0] = 15;
				color[1] = 15;
				color[2] = 15;

				for (var light : scene.getLights()) {
					Ray ray2 = Ray.fromPoints(light.getPoint(), intersection.getPoint());
					RayIntersection intersectionNew = findClosestIntersection(scene, ray2);
					if (intersectionNew != null) {
						if (intersectionNew.getDistance() + 0.0001 > light.getPoint().sub(intersection.getPoint())
								.norm()) {

							// diffuse reflections
							Point3D l = light.getPoint().sub(intersectionNew.getPoint());
							Point3D n = intersectionNew.getNormal();
							color[0] += intersectionNew.getKdr() * light.getR()
									* Math.max(n.scalarProduct(l.normalize()), 0);
							color[1] += intersectionNew.getKdg() * light.getG()
									* Math.max(n.scalarProduct(l.normalize()), 0);
							color[2] += intersectionNew.getKdb() * light.getB()
									* Math.max(n.scalarProduct(l.normalize()), 0);

							// specular reflections

							Point3D r = n.scalarMultiply((2.0 * l.scalarProduct(n)) / (n.norm())).sub(l).normalize();
							Point3D v = ray.start.sub(intersectionNew.getPoint()).normalize();
							double krn = intersectionNew.getKrn();

							color[0] += intersectionNew.getKrr() * light.getR() * Math.pow(r.scalarProduct(v), krn);
							color[1] += intersectionNew.getKrg() * light.getG() * Math.pow(r.scalarProduct(v), krn);
							color[2] += intersectionNew.getKrb() * light.getB() * Math.pow(r.scalarProduct(v), krn);

						}
					}

				}

				return color;
			}

			/**
			 * Helper method used to find the closest intersection for the provided ray.
			 * 
			 * @param scene - scene where the intersections are being searched for
			 * @param ray   - ray which is being checked
			 * @return intersection - the closest intersection that was found or null if
			 *         there was no intersection found
			 */

			private RayIntersection findClosestIntersection(Scene scene, Ray ray) {
				List<GraphicalObject> objects = scene.getObjects();
				RayIntersection returnIntersection = null;
				for (var object : objects) {
					RayIntersection intersection = object.findClosestRayIntersection(ray);
					if (returnIntersection == null) {
						returnIntersection = intersection;
					}
					if (intersection != null) {
						if (intersection.getDistance() < returnIntersection.getDistance()) {
							returnIntersection = intersection;
						}
					}
				}
				return returnIntersection;
			}

			/**
			 * 
			 * Job implemented using RecursiveAction. Used for drawing images produced by
			 * the ray tracer.
			 *
			 */
			class ThreadJob extends RecursiveAction {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				/**
				 * Point from which the eye is looking.
				 */
				private Point3D eye;
				private double horizontal;
				private double vertical;
				/**
				 * Image width
				 */
				private int width;
				/**
				 * Image height
				 */
				private int height;

				/**
				 * Vector representing the x axis.
				 */
				private Point3D xAxis;
				/**
				 * Vector representing the y axis.
				 */
				private Point3D yAxis;
				/**
				 * Vector representing the z axis.
				 */
				private Point3D zAxis;
				/**
				 * The corner of the screen.
				 */
				private Point3D screenCorner;
				/**
				 * Scene currently used.
				 */
				private Scene scene;
				/**
				 * Red component
				 */
				private short[] red;
				/**
				 * Green component
				 */
				private short[] green;
				/**
				 * Blue component
				 */
				private short[] blue;
				/**
				 * Minimal x
				 */
				private int xMin;
				/**
				 * Maximal x
				 */
				private int xMax;

				/**
				 * Constructor for the ThreadJob.
				 * 
				 * @param eye          - eye location
				 * @param horizontal   - width
				 * @param vertical     - height
				 * @param width        - width of the image
				 * @param height       - height of the image
				 * @param red          - red component
				 * @param green        - green component
				 * @param blue         - blue component
				 * @param xAxis        - x axis
				 * @param yAxis        - y axis
				 * @param zAxis        - z axis
				 * @param screenCorner - corner of the screen
				 * @param scene        - scene being used
				 * @param xMin         - minimal x
				 * @param xMax         - maximal x
				 */
				public ThreadJob(Point3D eye, double horizontal, double vertical, int width, int height, short[] red,
						short[] green, short[] blue, Point3D xAxis, Point3D yAxis, Point3D zAxis, Point3D screenCorner,
						Scene scene, int xMin, int xMax) {
					super();
					this.eye = eye;
					this.horizontal = horizontal;
					this.vertical = vertical;
					this.width = width;
					this.height = height;
					this.red = red;
					this.green = green;
					this.blue = blue;
					this.xMax = xMax;
					this.xMin = xMin;
					this.xAxis = xAxis;
					this.yAxis = yAxis;
					this.zAxis = zAxis;
					this.scene = scene;
					this.screenCorner = screenCorner;
				}

				/**
				 * {@inheritDoc} Used to compute the image.
				 */
				@Override
				protected void compute() {
					if (xMax - xMin < 50) {
						this.computeDirect();
						return;
					}
					ThreadJob job1 = new ThreadJob(eye, horizontal, vertical, width, height, red, green, blue, xAxis,
							yAxis, zAxis, screenCorner, scene, xMin + (xMax - xMin) / 2, xMax);
					ThreadJob job2 = new ThreadJob(eye, horizontal, vertical, width, height, red, green, blue, xAxis,
							yAxis, zAxis, screenCorner, scene, xMin, xMax - (xMax - xMin) / 2);
					invokeAll(job1, job2);
				}

				/**
				 * {@inheritDoc} Directly computes part of the whole image.
				 */
				private void computeDirect() {
					short[] rgb = new short[3];
					for (int y = 0; y < height; y++) {
						int offset = y * width;
						for (int x = xMin; x < xMax; x++) {
							Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((horizontal * x) / width))
									.sub(yAxis.scalarMultiply((vertical * y) / height));
							Ray ray = Ray.fromPoints(eye, screenPoint);

							tracer(scene, ray, rgb);

							red[offset + x] = rgb[0] > 255 ? 255 : rgb[0];
							green[offset + x] = rgb[1] > 255 ? 255 : rgb[1];
							blue[offset + x] = rgb[2] > 255 ? 255 : rgb[2];
						}
					}

				}

			}

		};

	}
}
