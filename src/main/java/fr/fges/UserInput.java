package fr.fges;

import java.util.Scanner;

public class UserInput {

    private final Scanner scanner;

    public UserInput() {
        this.scanner = new Scanner(System.in);
    }

    public String getString(String prompt) {
        System.out.printf("%s: ", prompt);
        return scanner.nextLine();
    }

    public int getIntAtLeast(String prompt, int minValue) {
        while (true) {
            try {
                int value = Integer.parseInt(getString(prompt));
                if (value >= minValue) {
                    return value;
                }
                System.out.println("Value must be greater than or equal to " + minValue + ".");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public int getIntAtLeastOther(String prompt, int minAllowed) {
        while (true) {
            int value = getIntAtLeast(prompt, 1);
            if (value >= minAllowed) {
                return value;
            }
            System.out.println("Value must be greater than or equal to " + minAllowed + ".");
        }
    }

    public String readChoice() {
        return scanner.nextLine();
    }
}
