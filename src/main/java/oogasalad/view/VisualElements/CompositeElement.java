package oogasalad.view.VisualElements;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import java.util.HashMap;
import java.util.Map;
import oogasalad.model.api.CollidableRecord;

public class CompositeElement {
  private final Map<Integer, VisualElement> elementMap;
  private Map<Integer, Shape> shapes = new HashMap<>();

  public CompositeElement(){
    elementMap = new HashMap<>();
  }

  /**
   * Syncs the map of ID to ViewElements according to the provided list of model data.
   * @param models  The list of model data to sync to.
   */
  public void update(List<CollidableRecord> models){
    for (CollidableRecord model : models){
      elementMap.putIfAbsent(model.id(), new GameElement(model.id()));
      elementMap.get(model.id()).update(model);
    }
  }

  /**
   * Returns the Node corresponding to the object with given ID.
   * @param id  The ID number of the desired object.
   * @return Node  A javafx Node representing the object.
   */
  public Node getNode(int id){
    return elementMap.get(id).getNode();
  }



  @Deprecated
  public Shape getShape(int id) {
    return shapes.get(id);
  }
  @Deprecated
  public void updateShape(int id, double x, double y, boolean visible) {
    Shape shape = shapes.get(id);
    if (shape != null) {
      shape.setLayoutX(x);
      shape.setLayoutY(y);
      shape.setVisible(visible);
    }
  }
}
