package oogasalad.view;

import javafx.scene.shape.Shape;
import java.util.HashMap;
import java.util.Map;

public class CompositeElement {
  private Map<Integer, Shape> shapes = new HashMap<>();

  public Shape getShape(int id) {
    return shapes.get(id);
  }

  public void updateShape(int id, double x, double y, boolean visible) {
    Shape shape = shapes.get(id);
    if (shape != null) {
      shape.setLayoutX(x);
      shape.setLayoutY(y);
      shape.setVisible(visible);
    }
  }
}
