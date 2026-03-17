package fr.fges.ui;

public class ExitAction implements MenuEntry {

    @Override
    public String getLabel() {
        return "Exit";
    }

    @Override
    public void execute() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}