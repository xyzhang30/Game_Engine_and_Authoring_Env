package oogasalad.model.gameengine.checkstatic;

import oogasalad.model.annotations.IsCommand;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.gameengine.gameobject.GameObjectContainer;

@FunctionalInterface
@IsCommand(isCommand = false)
public interface StaticChecker {

  public boolean isStatic(GameObjectRecord record);

}
