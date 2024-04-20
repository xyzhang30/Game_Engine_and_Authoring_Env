package oogasalad.model.gameengine.checkstatic;

import java.util.List;
import oogasalad.model.api.GameObjectRecord;

public class VisibilityStaticChecker implements StaticChecker {

  private final List<Integer> arguments;

  public VisibilityStaticChecker(List<Integer> arguments) { this.arguments=arguments;
  }
  @Override
  public boolean isStatic(GameObjectRecord record) {
    if (arguments.isEmpty() || arguments.contains(record.id())) {
      return !record.visible();
    }
    return true;
  }
}
