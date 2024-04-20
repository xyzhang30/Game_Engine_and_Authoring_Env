package oogasalad.model.gameengine.checkstatic;

import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.gameengine.gameobject.GameObjectContainer;

@FunctionalInterface
public interface StaticChecker {

  public boolean isStatic(GameObjectRecord record);

}
