package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Loads a list of all students and calls methods that filter out students.
 * 
 * @author Lovro Matošević
 *
 */
public class StudentDemo {

	public static void main(String[] args) {

		Path path = Paths.get("src/main/java/hr/fer/zemris/java/hw07/demo4/studenti.txt");
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			System.out.println("Error while reading.");
			System.exit(1);
		}
		List<StudentRecord> records = convert(lines);

		long broj = vratiBodovaViseOd25(records);
		System.out.printf("Zadatak 1%n=========%n%d%n", broj);

		long broj5 = vratiBrojOdlikasa(records);
		System.out.printf("Zadatak 2%n=========%n%d%n", broj5);

		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		System.out.printf("Zadatak 3%n=========%n");
		for (var s : odlikasi) {
			System.out.println(s);
		}

		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		System.out.printf("Zadatak 4%n=========%n");
		for (var s : odlikasiSortirano) {
			System.out.println(s);
		}

		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		System.out.printf("Zadatak 5%n=========%n");
		for (var s : nepolozeniJMBAGovi) {
			System.out.println(s);
		}

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		System.out.printf("Zadatak 6%n=========%n");

		for (var s : mapaPoOcjenama.entrySet()) {
			System.out.println(s);
		}

		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		System.out.printf("Zadatak 7%n=========%n");
		for (var s : mapaPoOcjenama2.entrySet()) {
			System.out.println(s);
		}

		System.out.printf("Zadatak 8%n=========%n");
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		for (var s : prolazNeprolaz.entrySet()) {
			System.out.println(s);
		}

	}

	/**
	 * Returns a map of students whose keys are true/false and values are the lists
	 * of students who passed for the key true, and a list of students who failed
	 * for the key false.
	 * 
	 * @param records - list of student records
	 * @return map of students
	 */

	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(s -> s.getOcjena() == 1));
	}

	/**
	 * Returns a map of students which has grades as keys and number of students
	 * with the appropriate grade as value.
	 * 
	 * @param records - list of student records
	 * @return map of students
	 */

	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.toMap(StudentRecord::getOcjena, ocj -> 1, (ocj1, ocj2) -> ocj1 + ocj2));

	}

	/**
	 * Returns a map of students which has grades as keys and a list of student with
	 * the appropriate grade as value.
	 * 
	 * @param records - list of student records
	 * @return map of students
	 */

	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getOcjena));
	}

	/**
	 * Returns a list of students whose final grade is equal to 1 sorted by jmbag.
	 * 
	 * @param records - list of student records
	 * @return list of students which satisfy the condition
	 */

	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getOcjena() == 1).map(s -> s.getJmbag())
				.sorted(Comparator.comparing(s -> s.toString())).collect(Collectors.toList());
	}

	/**
	 * Returns a list of students whose final grade is 5 sorted by scores.
	 * 
	 * @param records - list of student records
	 * @return list of students which satisfy the condition
	 */

	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter((s) -> s.getOcjena() == 5).sorted(new Comparator<StudentRecord>() {
			@Override
			public int compare(StudentRecord s1, StudentRecord s2) {
				double sum1 = s1.getBrojBodLab() + s1.getBrojBodMi() + s1.getBrojBodZi();
				double sum2 = s2.getBrojBodLab() + s2.getBrojBodMi() + s2.getBrojBodZi();
				if (sum2 - sum1 == 0) {
					return 0;
				}
				int result = sum2 - sum1 > 0 ? 1 : -1;
				return result;
			}
		}).collect(Collectors.toList());
	}

	/**
	 * Returns a list of students whose final grade is 5.
	 * 
	 * @param records - list of student records
	 * @return list of students which satisfy the condition
	 */

	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter((s) -> s.getOcjena() == 5).collect(Collectors.toList());
	}

	/**
	 * Determines the number of students whose final grade is 5.
	 * 
	 * @param records - list of student records
	 * @return count of students which satisfy the condition
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter((s) -> s.getOcjena() == 5).count();
	}

	/**
	 * Determines the number of students whose score sum is greater than 25.
	 * 
	 * @param records - list of student records
	 * @return count of students which satisfy the condition
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter((s) -> s.getBrojBodLab() + s.getBrojBodMi() + s.getBrojBodZi() > 25).count();
	}

	/**
	 * Converts a list of strings to a list of student records.
	 * 
	 * @param lines -strings to be converted
	 * @return list of student records
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> lista = new ArrayList<>();

		for (String line : lines) {
			if (!line.isBlank()) {
				String[] lineSplit = line.split("\t");
				String jmbag = lineSplit[0];
				String prezime = lineSplit[1];
				String ime = lineSplit[2];
				double brojBodMi = Double.parseDouble(lineSplit[3]);
				double brojBodZi = Double.parseDouble(lineSplit[4]);
				double brojBodLab = Double.parseDouble(lineSplit[5]);
				int ocjena = Integer.parseInt(lineSplit[6]);
				StudentRecord record = new StudentRecord(jmbag, prezime, ime, brojBodMi, brojBodZi, brojBodLab, ocjena);

				lista.add(record);
			}
		}

		return lista;
	}
}
