package de.propra.rover;

import java.util.List;

public interface IGame {
    IGame execute(String input);
    long getRoverX();
    long getRoverY();
    Direction getRoverDirection();

    /**
     * Repräsentation der 2D-Welt. Jeder Character ist entweder " " (kein Hindernis) oder "#" (Hindernis)
     * Die Koordinaten sind nicht negativ und beginnen oben links bei (0,0).
     * Der Rover selbst ist NICHT Teil dieser Repräsentation.
     */
    List<List<Character>> getWorld();
}
