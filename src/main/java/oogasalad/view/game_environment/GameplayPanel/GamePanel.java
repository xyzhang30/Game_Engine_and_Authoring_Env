package oogasalad.view.game_environment.GameplayPanel;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import oogasalad.view.visual_elements.CompositeElement;

/**
 * Transforms a node
 */
public class GamePanel {

  private final Pane localDisplay;
  private final TransformableNode transformer;

  public GamePanel(CompositeElement elements) {
    AnchorPane source = new AnchorPane();
    for (int id : elements.idList()) {
      source.getChildren().add(elements.getNode(id));
    }
    transformer = new TransformableNode(source);
    localDisplay = new AnchorPane(transformer.getPane());
  }

  public Pane getPane() {
    return localDisplay;
  }

  public void setCamera(double x, double y, double w, double h) {
    transformer.setFocus(x, y);
  }

  public void zoomIn() {
    transformer.zoom(1.05);
  }

  public void zoomOut() {
    transformer.zoom(0.95);
  }
}
