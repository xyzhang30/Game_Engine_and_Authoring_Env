package oogasalad.view.visual_elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.ViewGameObjectRecord;

public class CompositeElement {

  private final Map<Integer, VisualElement> elementMap;

  public CompositeElement(List<ViewGameObjectRecord> recordList) {
    elementMap = new HashMap<>();
    for (ViewGameObjectRecord viewRecord : recordList) {
      elementMap.putIfAbsent(viewRecord.id(), new GameElement(viewRecord));
    }
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
      root.getChildren().add(element.getNode());
    }
  }
}
