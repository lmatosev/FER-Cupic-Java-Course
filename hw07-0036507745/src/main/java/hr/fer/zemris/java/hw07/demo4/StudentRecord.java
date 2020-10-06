package hr.fer.zemris.java.hw07.demo4;

/**
 * 
 * A model of a student record. Stores basic student information.
 * 
 */
public class StudentRecord {
	/**
	 * Student's jmbag
	 */
	private String jmbag;
	/**
	 * Student's last name
	 */
	private String prezime;
	/**
	 * Student's name
	 */
	private String ime;
	/**
	 * Number of points on midterm exam
	 */
	private double brojBodMi;
	/**
	 * Number of points on the final exam
	 */
	private double brojBodZi;
	/**
	 * Number of lab points
	 */
	private double brojBodLab;
	/**
	 * Grade
	 */
	private int ocjena;

	/**
	 * The main constructor for this class.
	 * 
	 * @param jmbag      - student's jmbag
	 * @param prezime    - student's last name
	 * @param ime        - student's first name
	 * @param brojBodMi  - student's score on the midterm exam
	 * @param brojBodZi  - student's score on the final exam
	 * @param brojBodLab - student's lab score
	 * @param ocjena     - final grade
	 */

	public StudentRecord(String jmbag, String prezime, String ime, double brojBodMi, double brojBodZi,
			double brojBodLab, int ocjena) {
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.brojBodMi = brojBodMi;
		this.brojBodZi = brojBodZi;
		this.brojBodLab = brojBodLab;
		this.ocjena = ocjena;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(jmbag + " ");
		sb.append(prezime + " ");
		sb.append(ime + " ");
		sb.append(brojBodMi + " ");
		sb.append(brojBodZi + " ");
		sb.append(brojBodLab + " ");
		sb.append(ocjena);

		return sb.toString();
	}

	/**
	 * 
	 * @return string representing the student's jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * 
	 * @return student's last name
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * 
	 * @return student's name
	 */

	public String getIme() {
		return ime;
	}

	/**
	 * 
	 * @return midterm score
	 */
	public double getBrojBodMi() {
		return brojBodMi;
	}

	/**
	 * 
	 * @return finals score
	 */

	public double getBrojBodZi() {
		return brojBodZi;
	}

	/**
	 * 
	 * @return lab score
	 */

	public double getBrojBodLab() {
		return brojBodLab;
	}

	/**
	 * 
	 * @return final grade
	 */

	public int getOcjena() {
		return ocjena;
	}

}
