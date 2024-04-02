package oogasalad.view;

import javafx.scene.shape.Shape;
import oogasalad.model.api.CollidableRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.view.Screen.ScreenType;

public class SceneManager {
  private CompositeElement compositeElement;

  public SceneManager() {
    this.compositeElement = new CompositeElement();
  }

  public void update(GameRecord gameRecord) {
    // Iterate through collidables provided in the game record
    for (CollidableRecord collidable : gameRecord.collidables()) {
      Shape shape = compositeElement.getShape(collidable.id());
      if (shape != null) { // If the shape exists, update its properties
        updateShapeProperties(shape, collidable);
      }
      // If there's no shape for the collidable ID, no action is taken
    }
  }

  private void updateShapeProperties(Shape shape, CollidableRecord collidable) {
    // Assuming shape is a Circle for simplicity, but you would adjust based on your actual shape types
    shape.setLayoutX(collidable.x());
    shape.setLayoutY(collidable.y());
    shape.setVisible(collidable.visible());
    }

  public ScreenType getScreenType() {
    return null;
  }
}
