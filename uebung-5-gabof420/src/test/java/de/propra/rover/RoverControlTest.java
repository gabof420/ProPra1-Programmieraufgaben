package de.propra.rover;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class RoverControlTest {

    @Test
    @DisplayName("Wenn es keine Kontroll-Befehle gibt, dann bleiben Position und Ausrichtung unverändert")
    void emptyInputSequence_NoMovement() {
        IGame game = new Game(miniWorld(), 0, 0, Direction.NORTH);

        IGame executedGame = RoverControl.control(game, "");

        assertThat(executedGame.getRoverX()).isZero();
        assertThat(executedGame.getRoverY()).isZero();
        assertThat(executedGame.getRoverDirection()).isEqualTo(Direction.NORTH);
    }

    private List<List<Character>> miniWorld() {
        return List.of(
                List.of(' ', ' ', ' '),
                List.of(' ', ' ', ' '),
                List.of(' ', ' ', ' '));
    }

    private List<List<Character>> obstacles() {
        return List.of(
                List.of(' ', '#', ' '),
                List.of('#', '#', ' '),
                List.of(' ', ' ', ' '));
    }

    @Test
    @DisplayName("Bewege Rover nach Oben ohne Hindernisse ohne Drehen ohne Rand") 
    void moveForward_NoObstacle() {
        IGame game = new Game(miniWorld(), 1, 1, Direction.NORTH);

        IGame execute = RoverControl.control(game, "F");

        assertThat(execute.getRoverX()).isEqualTo(1);
        assertThat(execute.getRoverY()).isEqualTo(0);
        assertThat(execute.getRoverDirection()).isEqualTo(Direction.NORTH);
    }

    @Test
    @DisplayName("Bewege Rover nach Unten ohne Hindernisse ohne Drehen ohne Rand")
    void moveBackward_NoObstacle() {
        IGame game = new Game(miniWorld(), 1, 1, Direction.NORTH);

        IGame executedGame = RoverControl.control(game, "B");

        assertThat(executedGame.getRoverX()).isOne();
        assertThat(executedGame.getRoverY()).isEqualTo(2);
        assertThat(executedGame.getRoverDirection()).isEqualTo(Direction.NORTH);
    }

    @Test
    @DisplayName("Ungültige Befehle ignorieren")
    void ignoreInvalidCommands() {
        IGame game = new Game(miniWorld(), 0, 0, Direction.NORTH);

        IGame executedGame = RoverControl.control(game, "VZZZ");

        assertThat(executedGame.getRoverX()).isZero();
        assertThat(executedGame.getRoverY()).isZero();
        assertThat(executedGame.getRoverDirection()).isEqualTo(Direction.NORTH);
    }

    @Test 
    @DisplayName("Drehen nach links")
    void turnLeft() {
        IGame game = new Game(miniWorld(), 0, 0, Direction.NORTH);

        IGame executeGame = RoverControl.control(game, "l");

        assertThat(executeGame.getRoverX()).isEqualTo(0);
        assertThat(executeGame.getRoverY()).isEqualTo(0);
        assertThat(executeGame.getRoverDirection()).isEqualTo(Direction.WEST);
    }

    @Test
    @DisplayName("Drehen nach rechts")
    void turnRight() {
        IGame game = new Game(miniWorld(), 0, 0, Direction.NORTH);

        IGame executeGame = RoverControl.control(game, "r");

        assertThat(executeGame.getRoverX()).isEqualTo(0);
        assertThat(executeGame.getRoverY()).isEqualTo(0);
        assertThat(executeGame.getRoverDirection()).isEqualTo(Direction.EAST);
    }

    @Test
    @DisplayName("Mehrfache Befehle hintereinander: Drehen und Bewegen")
    void multipleCommands() {
        IGame game = new Game(miniWorld(), 0, 0, Direction.NORTH);

        IGame executeGame = RoverControl.control(game, "rFfLbFBR");

        assertThat(executeGame.getRoverX()).isEqualTo(2);
        assertThat(executeGame.getRoverY()).isEqualTo(1);
        assertThat(executeGame.getRoverDirection()).isEqualTo(Direction.EAST);
    }

    @Test
    @DisplayName("Ignoriere keine Hindernisse")
    void dontIgnoreObstacles() {
        IGame game = new Game(obstacles(), 0, 0, Direction.NORTH);

        IGame executeGame = RoverControl.control(game, "RF");

        assertThat(executeGame.getRoverX()).isEqualTo(0);
        assertThat(executeGame.getRoverY()).isEqualTo(0);
        assertThat(executeGame.getRoverDirection()).isEqualTo(Direction.EAST);
    }
}