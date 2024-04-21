package oogasalad.view.authoring_environment.panels;

import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ShapeProxy {
  private Shape shape;
  private GameObjectAttributesContainer gameObjectAttributesContainer = new GameObjectAttributesContainer();
  private int shapeCount;

  public Shape getShape() {
    return shape;
  }
  public void setShape(Shape shape) {
    this.shape = shape;
    resetGameObjectAttributesContainer();
  }
  public int getShapeCount() {
    return shapeCount;
  }

  public void setShapeCount(int shapeCount) {
    this.shapeCount = shapeCount;
  }

  public GameObjectAttributesContainer getGameObjectAttributesContainer() {
    return gameObjectAttributesContainer;
  }

  public void resetGameObjectAttributesContainer() {
    gameObjectAttributesContainer = new GameObjectAttributesContainer();
    if (shape != null) gameObjectAttributesContainer.setId(Integer.parseInt(shape.getId()));
  }

  public List<Shape> createTemplateShapes() {
    Rectangle rectangle = new Rectangle(100, 50, Color.BLACK);
    rectangle.setId(String.valueOf(shapeCount++));
    AnchorPane.setRightAnchor(rectangle, 150.0);
    AnchorPane.setTopAnchor(rectangle, 300.0);

    Ellipse ellipse = new Ellipse(30, 30);
    ellipse.setId(String.valueOf(shapeCount++));
    ellipse.setFill(Color.BLACK);
    AnchorPane.setRightAnchor(ellipse, 50.0);
    AnchorPane.setTopAnchor(ellipse, 300.0);

    return List.of(rectangle, ellipse);
  }

}
