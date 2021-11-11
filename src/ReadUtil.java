import java.util.InputMismatchException;
import java.util.Scanner;

public class ReadUtil {
	private static final Scanner scanner = new Scanner(System.in);

	public static int readInt() {
		Integer number = null;
		while (number == null) {
			try {
				number = scanner.nextInt();
			} catch (InputMismatchException | NullPointerException e) {
				System.err.println("Indtast venligst et tal");
				scanner.nextLine();
			}
		}
		return number;
	}

	public static String readLine() {
		return scanner.nextLine();
	}
}
