package de.tomasino.shoppinglist;

public class EinkaufsItem {
    private String text;
    private boolean erledigt;
    private boolean nichtVerfuegbar;

    public EinkaufsItem(String text) {
        this.text = text;
        this.erledigt = false;
        this.nichtVerfuegbar = false;
    }

    public String getText() { return text; }
    public boolean isErledigt() { return erledigt; }
    public void setErledigt(boolean erledigt) { this.erledigt = erledigt; }

    public boolean isNichtVerfuegbar() { return nichtVerfuegbar; }
    public void setNichtVerfuegbar(boolean nichtVerfuegbar) { this.nichtVerfuegbar = nichtVerfuegbar; }
}
