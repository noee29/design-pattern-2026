package fr.fges.action;

import fr.fges.policy.DayPolicy;
import fr.fges.service.GameService;

public class WeekendSummaryAction implements MenuAction {

    private final GameService service;
    private final DayPolicy dayPolicy;

    public WeekendSummaryAction(GameService service, DayPolicy dayPolicy) {
        this.service = service;
        this.dayPolicy = dayPolicy;
    }

    @Override
    public void execute() {
        if (!dayPolicy.isWeekend()) {
            System.out.println("Weekend summary available only on weekends.");
            return;
        }

        System.out.println("Total games this weekend: " + service.getAllGames().size());
    }
}