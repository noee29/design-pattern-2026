package fr.fges.action;

public class ExitAction implements MenuAction {

    @Override
    public void execute() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}