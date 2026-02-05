package fr.fges.ui;

import java.util.Scanner;

public class UserInput {

    private final Scanner scanner = new Scanner(System.in);

    public String getString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public int getIntAtLeast(String prompt, int min) {
        while (true) {
            try {
                int value = Integer.parseInt(getString(prompt));
                if (value >= min) return value;
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid number.");
        }
    }

    public String readChoice() {
        return scanner.nextLine();
    }
}