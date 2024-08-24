package de.propra.rover;

public class RoverControl {

    /**
     * Nimmt den aktuellen Spielzustand entgegen, wendet die eingegebenen Befehle an und
     * berechnet den neuen Spielzustand nach Abarbeitung aller Befehle.
     * @param game Aktueller Spielzustand mit Hindernissen, Rover-Position und -Ausrichtung
     * @param input ein String, der nur aus den Zeichen 'l', 'r', 'f', 'b' besteht; darf leer sein
     * @return Neuer Spielzustand mit denselben Hindernissen, neuer Rover-Position und -Ausrichtung
     */
    public static IGame control(IGame game, String input) {
        if(input.isEmpty()) {
            return game;
        } 
        for(char command : input.toCharArray()) {
            game = switch (command) {
                case 'l', 'L' -> turnLeft(game);
                case 'r', 'R' -> turnRight(game);
                case 'f', 'F' -> moveForward(game);
                case 'b', 'B' -> moveBackward(game);
                default -> game;
            };
        }
        return game;
    }

    private static boolean isObstacle(IGame game, long x, long y) {
        return game.getWorld().get((int) y).get((int) x) == '#';
    }

    private static IGame turnLeft(IGame game) {
        Direction newRoverDirection = switch (game.getRoverDirection()) {
            case NORTH -> Direction.WEST;
            case EAST -> Direction.NORTH;
            case SOUTH -> Direction.EAST;
            case WEST -> Direction.SOUTH;
        };

        return new Game(game.getWorld(), game.getRoverX(), game.getRoverY(), newRoverDirection);
    }

    private static IGame turnRight(IGame game) {
        Direction newRoverDirection = switch (game.getRoverDirection()) {
            case NORTH -> Direction.EAST;
            case EAST -> Direction.SOUTH;
            case SOUTH -> Direction.WEST;
            case WEST -> Direction.NORTH;
        };
        return new Game(game.getWorld(), game.getRoverX(), game.getRoverY(), newRoverDirection);
    }

    private static IGame moveForward(IGame game) {
        long newY = game.getRoverY();
        long newX = game.getRoverX();

        if(game.getRoverDirection().equals(Direction.WEST)) {
            newX--;
        } else if(game.getRoverDirection().equals(Direction.EAST)) {
            newX++;
        } else if(game.getRoverDirection().equals(Direction.NORTH)) {
            newY--;
        } else {
            newY++;
        }

        if(isObstacle(game, newX, newY)) {
            System.out.println("Hindernis erkannt");
            return game;
        } else {
            return new Game(game.getWorld(), newX, newY, game.getRoverDirection());
        }
    }

    private static IGame moveBackward(IGame game) {
        long newY = game.getRoverY();
        long newX = game.getRoverX();

        if(game.getRoverDirection().equals(Direction.WEST)) {
            newX++;
        } else if(game.getRoverDirection().equals(Direction.EAST)) {
            newX--;
        } else if(game.getRoverDirection().equals(Direction.NORTH)) {
            newY++;
        } else {
            newY--;
        } 

        if(isObstacle(game, newX, newY)) {
            System.out.println("Hindernis erkannt");
            return game;
        } else {
            return new Game(game.getWorld(), newX, newY, game.getRoverDirection());
        }
    }
}
