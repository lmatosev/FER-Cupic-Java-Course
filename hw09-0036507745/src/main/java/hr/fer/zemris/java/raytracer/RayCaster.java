package hr.fer.zemris.java.raytracer;

import java.util.List; 
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * 
 * Class implementing a simple ray caster.
 * 
 * @author Lovro Matošević
 *
 */
public class RayCaster {

	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
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

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((horizontal * x) / width))
								.sub(yAxis.scalarMultiply((vertical * y) / height));
						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

						offset++;
					}
				}

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");

			}
		};
	}

	/**
	 * Tracer for this ray caster.
	 * 
	 * @param scene - scene being used
	 * @param ray   - ray from the eye viewpoint
	 * @param rgb   - color
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
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
	private static short[] determineColorFor(Scene scene, RayIntersection intersection, Ray ray) {
		short[] color = new short[3];
		color[0] = 15;
		color[1] = 15;
		color[2] = 15;

		for (var light : scene.getLights()) {
			Ray ray2 = Ray.fromPoints(light.getPoint(), intersection.getPoint());
			RayIntersection intersectionNew = findClosestIntersection(scene, ray2);
			if (intersectionNew != null) {
				if (intersectionNew.getDistance() + 0.0001 > light.getPoint().sub(intersection.getPoint()).norm()) {

					// diffuse reflections
					Point3D l = light.getPoint().sub(intersectionNew.getPoint());
					Point3D n = intersectionNew.getNormal();
					color[0] += intersectionNew.getKdr() * light.getR() * Math.max(n.scalarProduct(l.normalize()), 0);
					color[1] += intersectionNew.getKdg() * light.getG() * Math.max(n.scalarProduct(l.normalize()), 0);
					color[2] += intersectionNew.getKdb() * light.getB() * Math.max(n.scalarProduct(l.normalize()), 0);

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
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
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

}
