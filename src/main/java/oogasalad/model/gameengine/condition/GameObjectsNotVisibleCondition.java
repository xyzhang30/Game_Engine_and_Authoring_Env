package oogasalad.model.gameengine.condition;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.annotations.VariableParamNumber;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;

/**
 * Represents a condition that evaluates whether certain game objects are not visible.
 * This condition is true if all specified game objects are not visible.
 *
 * @author Noah Loewy
 */


 @IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
@VariableParamNumber(isVariable = true)
public class GameObjectsNotVisibleCondition implements Condition {

  private final List<Integer> arguments;
  private final List<GameObject> gameObjects;

  /**
   * Constructs a GameObjectsNotVisibleCondition for the specified GameObjects.
   *
   * @param arguments The list of arguments for the condition.
   * @param gameObjectMap The map of game objects.
   */

  public GameObjectsNotVisibleCondition(List<Integer> arguments,
      Map<Integer, GameObject> gameObjectMap) {
    this.arguments = arguments;
    gameObjects = arguments.stream().map(gameObjectMap::get).collect(Collectors.toList());
  }

  /**
   * Evaluates whether all the specified game objects are not visible.
   *
   * @param engine The game engine in which the condition is evaluated.
   * @return true if all specified game objects are not visible, false otherwise.
   */

  @Override
  public boolean evaluate(GameEngine engine) {
    return gameObjects.stream()
        .noneMatch(GameObject::getVisible);
  }

}
