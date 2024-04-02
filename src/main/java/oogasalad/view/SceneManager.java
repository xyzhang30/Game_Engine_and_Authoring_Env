package oogasalad.view;

import oogasalad.model.api.GameRecord;
import oogasalad.model.api.CollidableRecord;
import oogasalad.view.CompositeElement;

public class SceneManager {
  private CompositeElement compositeElement;

  public SceneManager() {
    this.compositeElement = new CompositeElement();
  }

  public void update(GameRecord gameRecord) {
    for (CollidableRecord collidable : gameRecord.collidables()) {
      compositeElement.updateShape(collidable.id(), collidable.x(), collidable.y(), collidable.visible());
    }
  }
}
