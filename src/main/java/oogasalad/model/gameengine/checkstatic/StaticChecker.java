package oogasalad.model.gameengine.checkstatic;

import oogasalad.model.annotations.IsCommand;
import oogasalad.model.api.GameObjectRecord;

@FunctionalInterface
@IsCommand(isCommand = false)
public interface StaticChecker {

  boolean isStatic(GameObjectRecord record);

}
