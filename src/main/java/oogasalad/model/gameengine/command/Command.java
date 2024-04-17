package oogasalad.model.gameengine.command;

import oogasalad.model.gameengine.GameEngine;

/**
 * The Command interface represents a generic command to be executed in the oogasalad game engine
 * <p>
 * The purpose of this interface is to define a common structure for all commands defined in the
 * Game Engine, allowing for the details of each individual command to be abstracted away. Command
 * objects are created using reflection.
 * <p>
 * This interface is a functional interface, meaning that it only provides a single method, execute,
 * which accepts a GameEngine to be updated. It's constructor takes in a list of its arguments.
 * <p>
 * Implementations of this interface define the behavior of specific commands
 * <p>
 * To use the Command interface, you can create concrete implementations for specific commands. For
 * example:
 * <pre>{@code
 * public class AdvanceRound implements Command {
 *     public void execute(GameEngine engine) {
 *         engine.advanceRound();
 *     }
 * }
 * }</pre>
 *
 * @author Noah Loewy
 */

@FunctionalInterface

public interface Command {

  /**
   * Executes the command with the given arguments, updating the GameEngine as needed
   *
   * @param engine The instance of the game engine that the command will query/update
   */

  void execute(GameEngine engine);

}
