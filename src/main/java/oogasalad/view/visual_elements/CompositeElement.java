package oogasalad.view.visual_elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
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
   * Returns the Node corresponding to the object with given ID.
   *
   * @param id The ID number of the desired object.
   * @return Node  A javafx Node representing the object.
   */
  public Node getNode(int id) {
    return elementMap.get(id).getNode();
  }

  public List<Integer> idList() {
    return elementMap.keySet().stream().toList();
  }
}