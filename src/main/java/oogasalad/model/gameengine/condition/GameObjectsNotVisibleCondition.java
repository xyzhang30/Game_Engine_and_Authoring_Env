package oogasalad.model.gameengine.condition;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;


@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class GameObjectsNotVisibleCondition implements Condition {

  private final List<Integer> arguments;
  private List<GameObject> gameObjects;

  public GameObjectsNotVisibleCondition(List<Integer> arguments, Map<Integer, GameObject> gameObjectMap) {
    this.arguments = arguments;
    gameObjects = arguments.stream().map(gameObjectMap::get).collect(Collectors.toList());

  }

  @Override
  public boolean evaluate(GameEngine engine) {
    return gameObjects.stream()
        .noneMatch(GameObject::getVisible);
  }

}
