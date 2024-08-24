package de.propra.rover;

import java.util.List;

public class Game implements IGame {
    private final List<List<Character>> world;
    private final long roverX;
    private final long roverY;
    private final Direction roverDirection;

    /**
     * Erstellt eine neue Welt
     * @param world Repräsentation der Hindernisse in der Welt: ' ' = kein Hindernis, '#' = Hindernis; enthält NICHT den Rover selbst
     * @param roverX x-Position des Rovers; die Koordinaten sind nicht negativ und beginnen oben links bei (0,0).
     * @param roverY y-Position des Rovers
     * @param roverDirection aktuelle Ausrichtung des Rovers
     */
    Game(List<List<Character>> world, long roverX, long roverY, Direction roverDirection) {
        this.world = world;
        this.roverX = roverX;
        this.roverY = roverY;
        this.roverDirection = roverDirection;
    }

    @Override
    public IGame execute(String input) {
        // braucht keine Implementierung, wird im Clojure-Code implementiert
        return null;
    }

    @Override
    public long getRoverX() {
        return this.roverX;
    }

    @Override
    public long getRoverY() {
        return this.roverY;
    }

    @Override
    public Direction getRoverDirection() {
        return this.roverDirection;
    }

    @Override
    public List<List<Character>> getWorld() {
        return this.world;
    }
}
