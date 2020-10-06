package hr.fer.zemris.java.raytracer.model;

/**
 * 
 * Class which represents a model of a sphere.
 * 
 * @author Lovro Matošević
 *
 */
public class Sphere extends GraphicalObject {
	/**
	 * Sphere center
	 */
	private Point3D center;
	/**
	 * Sphere radius
	 */
	private double radius;
	/**
	 * Red diffuse component
	 */
	private double kdr;
	/**
	 * Green diffuse component
	 */
	private double kdg;
	/**
	 * Blue diffuse component
	 */
	private double kdb;
	/**
	 * Red reflective component
	 */
	private double krr;
	/**
	 * Green reflective component
	 */
	private double krg;
	/**
	 * Blue reflective component
	 */
	private double krb;
	/**
	 * Shininess factor
	 */
	private double krn;

	/**
	 * The main constructor for this class.
	 * 
	 * @param center - sphere center
	 * @param radius - sphere radius
	 * @param kdr    - red diffuse component
	 * @param kdg    - green diffuse component
	 * @param kdb    - blue diffuse component
	 * @param krr    - red diffuse component
	 * @param krg    - green diffuse component
	 * @param krb    - blue diffuse component
	 * @param krn    - shininess factor
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Finds and returns the closest ray intersection.
	 * 
	 * @return intersection - the closest intersection that was found, and null if
	 *         no intersection was found.
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D toCenter = ray.start.sub(this.center);
		double a = ray.direction.scalarProduct(ray.direction);
		double b = 2 * toCenter.scalarProduct(ray.direction);
		double c = toCenter.scalarProduct(toCenter) - this.radius * this.radius;
		double discriminant = b * b - 4 * a * c;

		if (discriminant < 0.0) {
			return null;
		}

		double t;

		if (discriminant == 0.0) {
			t = -b / (2.0 * a);
		} else {
			double t1 = (-b + Math.sqrt(discriminant)) / (2.0 * a);
			double t2 = (-b - Math.sqrt(discriminant)) / (2.0 * a);

			t = t1 < t2 ? t1 : t2;
		}

		Point3D intersectionPoint = ray.start.add(ray.direction.scalarMultiply(t));
		double distance = intersectionPoint.sub(ray.start).norm();

		return new RayIntersection(intersectionPoint, distance, true) {

			@Override
			public Point3D getNormal() {
				return intersectionPoint.sub(Sphere.this.center).normalize();
			}

			@Override
			public double getKrr() {
				return Sphere.this.krr;
			}

			@Override
			public double getKrn() {
				return Sphere.this.krn;
			}

			@Override
			public double getKrg() {
				return Sphere.this.krg;
			}

			@Override
			public double getKrb() {
				return Sphere.this.krb;
			}

			@Override
			public double getKdr() {
				return Sphere.this.kdr;
			}

			@Override
			public double getKdg() {
				return Sphere.this.kdg;
			}

			@Override
			public double getKdb() {
				return Sphere.this.kdb;
			}
		};
	}

}
