package fr.fges.action;

public class ExitAction implements MenuAction {

    @Override
    public String getLabel() { return "Exit"; }

    @Override
    public void execute() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}