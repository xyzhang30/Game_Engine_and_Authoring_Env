package oogasalad.view.authoring_environment.panels;

import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ShapeProxy{
  private Shape shape;

  public Shape getShape() {
    return shape;
  }

  public void setShape(Shape shape) {
    this.shape = shape;
  }

  public List<Shape> createTemplateShapes() {
    Rectangle rectangle = new Rectangle(100, 50, Color.BLACK);
    AnchorPane.setRightAnchor(rectangle, 150.0);
    AnchorPane.setTopAnchor(rectangle, 300.0);
    rectangle.setId("rectangleTemplate");

    Ellipse ellipse = new Ellipse(30, 30);
    ellipse.setFill(Color.BLACK);
    AnchorPane.setRightAnchor(ellipse, 50.0);
    AnchorPane.setTopAnchor(ellipse, 300.0);
    ellipse.setId("ellipseTemplate");

    return List.of(rectangle, ellipse);
  }

}
