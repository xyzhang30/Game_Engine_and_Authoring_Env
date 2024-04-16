package oogasalad.view.visual_elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import oogasalad.model.api.CollidableRecord;
import oogasalad.model.api.ViewCollidableRecord;

public class CompositeElement {

  private final Map<Integer, VisualElement> elementMap;

  public CompositeElement(List<ViewCollidableRecord> recordList) {
    elementMap = new HashMap<>();
    for (ViewCollidableRecord viewRecord : recordList) {
      elementMap.putIfAbsent(viewRecord.id(), new GameElement(viewRecord));
    }
  }

  /**
   * Syncs the map of ID to ViewElements according to the provided list of model data.
   *
   * @param models The list of model data to sync to.
   */
  public void update(List<CollidableRecord> models) {
    for (CollidableRecord model : models) {
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
