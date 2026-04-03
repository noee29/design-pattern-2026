package fr.fges.ui;

/** Interface pour toute action exécutable depuis le menu (pattern Command). */
public interface MenuEntry {
    String getLabel();
    void execute();
}