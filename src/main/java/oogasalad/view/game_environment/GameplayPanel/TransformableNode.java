package oogasalad.view.game_environment.GameplayPanel;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Wrapper tool for Generalized Node Transformations.
 * Used for transforming groups of visual elements.
 */
public class TransformableNode {
  private final Node source;
  private double focusX;
  private double focusY;

  /**
   * Constructor. Pass in a single node packed with anything in need of transforming.
   * @param basis  The source node to transform.
   */
  public TransformableNode(Node basis) {
    source = basis;
  }

  /**
   * Returns a Pane with the transformed node.
   * @return Pane  holds the transformed source node.
   */
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

  /**
   * Moves the transformation so that the top left corner aligns with the specified coordinates.
   * Note that the coordinates must be in terms of the global content.
   * @param x  horizontal coordinate in original node's scope.
   * @param y  vertical coordinate in original node's scope.
   */
  public void setFocus(double x, double y) {
    focusX = x;
    focusY = y;
    syncShiftX();
    syncShiftY();
  }

  /**
   * Sets the relative horizontal scaling.
   * @param scaleX  factor by which to horizontally scale the transformation relative to original.
   */
  public void setScaleX(double scaleX) {
    source.setScaleX(scaleX);
    syncShiftX();
  }

  /**
   * Sets the relative vertical scaling.
   * @param scaleY  factor by which to vertically scale the transformation relative to original.
   */
  public void setScaleY(double scaleY) {
    source.setScaleY(scaleY);
    syncShiftY();
  }

  /**
   * Scales the content relative to current scale.
   * @param zoomScale  factor by which to scale current transformation in both dimensions.
   */
  public void zoom(double zoomScale) {
    setScaleX(source.getScaleX() * zoomScale);
    setScaleY(source.getScaleY() * zoomScale);
  }

  /**
   * Automatically scales the content have the specified dimensions.
   * @param width  Target width of transformed content.
   * @param height  Target height of transformed content.
   */
  public void sizeToBounds(double width, double height) {
    setScaleX(width / source.getBoundsInLocal().getWidth());
    setScaleY(height / source.getBoundsInLocal().getHeight());
  }
}
