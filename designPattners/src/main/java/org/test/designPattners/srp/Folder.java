package org.test.designPattners.srp;

/*
 * Idea of this design pattern is that every class show do only what it is intened for.
 * The model object should only take care of model creation 
 * There should not be any logic for persistance or any other transformation
 * The problem is that if we deviate from srp principle then we will have GOD object.
 * It would become really hard to refactor this class latters point in time.
 * If we want to save object this object if it has lot of nested object
 * 
 */
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Folder {

	private List<String> files = new ArrayList<>();

	private static int count = 0;

	public void addFile(String file) {

		files.add("" + (++count) + file);

	}

	public void removeFile(int index) {
		files.remove(index);
	}

	public String toString() {
		return String.join(System.lineSeparator(), files);
	}

	// This deviates from the SRP principle because the class is also dealing with
	// Persistance which should not have been case
	// This class becomes god object

	public void save(String filename) throws FileNotFoundException {
		// new way to write to file
		try (PrintStream out = new PrintStream(filename)) {
			out.print(toString());
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		Folder folder = new Folder();
		folder.addFile("Index");
		folder.addFile("Project1");
		System.out.println(folder);
		folder.save("test.txt");
		System.out.println("After removing data");
		folder.removeFile(1);
		System.out.println(folder);

	}

}
