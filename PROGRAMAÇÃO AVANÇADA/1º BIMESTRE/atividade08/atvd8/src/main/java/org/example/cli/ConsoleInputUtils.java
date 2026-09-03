package org.example.cli;

import java.util.Scanner;

public class ConsoleInputUtils {

    private ConsoleInputUtils() {
        // Utility class
    }

    public static int readInt(Scanner scanner) {
        try {
            int val = scanner.nextInt();
            scanner.nextLine();
            return val;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    public static double readDouble(Scanner scanner) {
        try {
            double val = scanner.nextDouble();
            scanner.nextLine();
            return val;
        } catch (Exception e) {
            scanner.nextLine();
            return -1.0;
        }
    }
}
