package oogasalad.view.scene_management;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import oogasalad.view.visual_elements.CompositeElement;

/**
 * Contains all nodes that create game board and holds them in a pane
 *
 * @author Jordan Haytaian
 */
public class GameBoard {

  private AnchorPane gameBoardContainer;

  /**
   * Constructor adds game board elements to a container pane
   *
   * @param modelElements game board elements
   */
  public GameBoard(CompositeElement modelElements) {
    gameBoardContainer = buildMapView(modelElements);
  }

  /**
   * Getter for container Pane
   *
   * @return container for game board elements
   */
  public Pane getPane() {
    return gameBoardContainer;
  }

  private AnchorPane buildMapView(CompositeElement elements) {
    AnchorPane mapViewBase = new AnchorPane();
    for (int id : elements.idList()) {
      mapViewBase.getChildren().add(elements.getNode(id));
    }
    return mapViewBase;
  }

}
