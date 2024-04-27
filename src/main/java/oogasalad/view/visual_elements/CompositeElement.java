package oogasalad.view.visual_elements;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.ViewGameObjectRecord;

public class CompositeElement {

  private final Map<Integer, VisualElement> elementMap;
  private Pane gameBoard;

  public CompositeElement(List<ViewGameObjectRecord> recordList)
      throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
      IllegalAccessException, InvocationTargetException {
    elementMap = new HashMap<>();
    gameBoard = new Pane();
    for (ViewGameObjectRecord viewRecord : recordList) {
      elementMap.putIfAbsent(viewRecord.id(), new GameElement(viewRecord));
    }
  }

  /**
   * Gets the x coordinate of the left side of the game board
   *
   * @return double representing the x coordinate of the left side of the game board
   */
  public double getGameBoardLeftBound() {
    return gameBoard.getBoundsInLocal().getMinX();
  }

  /**
   * Gets the x coordinate of the right side of the game board
   *
   * @return double representing the x coordinate of the right side of the game board
   */
  public double getGameBoardRightBound() {
    return gameBoard.getBoundsInLocal().getMaxX();
  }

  /**
   * Syncs the map of ID to ViewElements according to the provided list of model data.
   *
   * @param models The list of model data to sync to.
   */
  public void update(List<GameObjectRecord> models) {
    for (GameObjectRecord model : models) {
      elementMap.get(model.id()).update(model);
    }
  }

  /**
   * Iterates through all nodes contained in composite element and adds them to specified root
   *
   * @param root root node to add composite element o
   */
  public void addElementsToRoot(Pane root) {
    for (VisualElement element : elementMap.values()) {
      gameBoard.getChildren().add(element.getNode());
    }
    root.getChildren().add(gameBoard);
  }
}
