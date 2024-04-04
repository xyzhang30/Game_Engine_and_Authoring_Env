package oogasalad.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import oogasalad.Pair;
import oogasalad.view.VisualElements.CompositeElement;

/**
 * Class to maintain intersections and detect which collisions are active
 *
 * @author Jordan Haytaian
 */
public class CollisionManager {

  private Shape[][] intersections;
  private CompositeElement compositeElement;

  public CollisionManager() {}

  /**
   * Traverses intersection matrix and to create list of ids that are currently intersecting
   *
   * @return A list of the ID pairs that are intersecting
   */
  public List<Pair> getIntersections() {
    List<Pair> intersectionList = new ArrayList<>();
    for (int i=0;i<compositeElement.idList().size()-1;i++){
      for (int j=i+1;j<compositeElement.idList().size();j++){
        if (compositeElement.getNode(compositeElement.idList().get(i)).intersects(
            compositeElement.getNode(compositeElement.idList().get(j)).getBoundsInLocal())){
          intersectionList.add(new Pair(compositeElement.idList().get(i),compositeElement.idList().get(j)));
        }
      }
    }
    return intersectionList;
  }

  /**
   * Assigns the composite element to the given composite element
   * @param newCompositeElement the new composite element
   */
  public void setNewCompositeElement(CompositeElement newCompositeElement) {
    this.compositeElement = newCompositeElement;
  }

//  private void createIntersectionMatrix() {
//    int maxId = -1;
//    for (Integer currId : elementMap.keySet()) {
//      if (currId > maxId) {
//        maxId = currId;
//      }
//    }
//
//    intersections = new Shape[maxId][maxId];
//
//    for (Map.Entry<Integer, Node> currEntry : elementMap.entrySet()) {
//      for (Map.Entry<Integer, Node> interEntry : elementMap.entrySet()) {
//
//        int currId = currEntry.getKey();
//        int interId = interEntry.getKey();
//
//        if (currId != interId) {
//          Shape currShape = (Shape) currEntry.getValue();
//          Shape interShape = (Shape) interEntry.getValue();
//          Shape currIntersection = Shape.intersect(currShape, interShape);
//
//          intersections[currId][interId] = currIntersection;
//        }
//      }
//    }
//  }


//  public void triggerCollisions(){
//    Map<Integer,Map<Integer, Command>> collisionMap = null;
//    for (Integer i : collisionMap.keySet()){
//      for (Integer j : collisionMap.get(i).keySet()){
//        if (compositeElement.getNode(i).intersects(compositeElement.getNode(j).getBoundsInLocal())){
//          collisionMap.get(i).get(j).execute(engine);
//        }
//      }
//    }
//  }
}
