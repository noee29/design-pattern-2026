package fr.fges.ui;

import java.util.Scanner;

public class UserInput {

    private final Scanner scanner = new Scanner(System.in);

    public String getString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    public int getInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public int getIntBetween(String prompt, int min, int max) {
        while (true) {
            int value = getInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    public int getIntAtLeast(String prompt, int min) {
        while (true) {
            int value = getInt(prompt);
            if (value >= min) {
                return value;
            }
            System.out.println("Please enter a number at least " + min + ".");
        }
    }
}