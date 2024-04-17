package oogasalad.view.authoring_environment.panels;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ClonableShape extends Shape implements Cloneable {
  public ClonableShape(double width, double height) {
    super();
  }

  @Override
  public ClonableShape clone() {
    try {
      return (ClonableShape) super.clone();
    } catch (CloneNotSupportedException e) {
      // This should never happen since we support cloning
      throw new InternalError(e);
    }
  }
}
