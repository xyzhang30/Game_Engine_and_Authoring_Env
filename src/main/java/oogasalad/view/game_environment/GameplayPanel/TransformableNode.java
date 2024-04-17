package oogasalad.view.game_environment.GameplayPanel;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TransformableNode {

  private final Node source;
  private double focusX;
  private double focusY;

  public TransformableNode(Node basis) {
    source = basis;
  }

  public Pane getPane() {
    return new AnchorPane(source);
  }

  private void syncShiftX() {
    source.setTranslateX(0);
    double scaledOffset = focusX * source.getScaleX();
    source.setTranslateX(-source.getBoundsInParent().getMinX() - scaledOffset);
  }

  private void syncShiftY() {
    source.setTranslateY(0);
    double scaledOffset = focusY * source.getScaleY();
    source.setTranslateY(-source.getBoundsInParent().getMinY() - scaledOffset);
  }

  public void setFocus(double x, double y) {
    focusX = x;
    focusY = y;
    syncShiftX();
    syncShiftY();
  }

  public void setScaleX(double scaleX) {
    source.setScaleX(scaleX);
    syncShiftX();
  }

  public void setScaleY(double scaleY) {
    source.setScaleY(scaleY);
    syncShiftY();
  }

  public void zoom(double zoomScale) {
    setScaleX(source.getScaleX() * zoomScale);
    setScaleY(source.getScaleY() * zoomScale);
  }

  public void sizeToBounds(double width, double height) {
    setScaleX(width / source.getBoundsInLocal().getWidth());
    setScaleY(height / source.getBoundsInLocal().getHeight());
  }
}
