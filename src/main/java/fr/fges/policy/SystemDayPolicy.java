package fr.fges.policy;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class SystemDayPolicy implements DayPolicy {

    @Override
    public boolean isWeekend() {
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}