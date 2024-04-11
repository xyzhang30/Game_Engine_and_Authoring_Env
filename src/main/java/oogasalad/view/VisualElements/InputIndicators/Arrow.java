package oogasalad.view.VisualElements.InputIndicators;

import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class Arrow {

  private final Line line;
  private double angle = 0; // Angle in degrees

  public Arrow(double startX, double startY, double endX, double endY) {
    line = new Line(startX, startY, endX, endY);
    line.setStrokeWidth(3);
    // Initial position and orientation
    line.getTransforms().add(new Rotate(angle, startX, startY));
  }

  public Line getLine() {
    return line;
  }

  public double getAngle() {
    return angle;
  }

  public void setAngle(double angle) {
    this.angle = angle;
    // Reset transforms to apply new angle
    line.getTransforms().clear();
    line.getTransforms().add(new Rotate(angle, line.getStartX(), line.getStartY()));
  }

  public void increaseAngle() {
    setAngle(angle + 5);
  }

  public void decreaseAngle() {
    setAngle(angle - 5);
  }
}
