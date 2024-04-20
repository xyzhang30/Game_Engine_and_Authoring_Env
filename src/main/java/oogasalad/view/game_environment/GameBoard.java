package oogasalad.view.game_environment;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import oogasalad.view.visual_elements.CompositeElement;

/**
 * Transforms a node
 */
public class GameBoard {

  private AnchorPane gameBoardContainer;

  public GameBoard(CompositeElement modelElements) {
    gameBoardContainer = buildMapView(modelElements);
  }

  private AnchorPane buildMapView(CompositeElement elements) {
    AnchorPane mapViewBase = new AnchorPane();
    for (int id : elements.idList()) {
      mapViewBase.getChildren().add(elements.getNode(id));
    }
    return mapViewBase;
  }

  public Pane getPane() {
    return gameBoardContainer;
  }

}
