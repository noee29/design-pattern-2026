package fr.fges.policy;

/** Interface pour déterminer si le jour courant est pendant le weekend (pattern Strategy). */
public interface DayPolicy {
    boolean isWeekend();
}