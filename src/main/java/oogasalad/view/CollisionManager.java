package oogasalad.view;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

/**
 * Class to maintain intersections and detect which collisions are active
 *
 * @author Jordan Haytaian
 */
public class CollisionManager {

  private Shape[][] intersections;
  private CompositeElement compositeElement;

  public CollisionManager() {
  }

  /**
   * Traverses intersection matrix and to create list of ids that are currently intersecting
   *
   * @return A list of the ID pairs that are intersecting
   */
  public List<List<Integer>> getIntersections() {
    List<List<Integer>> intersectionList = new ArrayList<>();
    for (int currRow = 0; currRow < intersections.length; currRow++) {
      for (int currCol = currRow + 1; currCol < intersections[0].length; currCol++) {
        double intersectionWidth = intersections[currRow][currCol].getBoundsInLocal().getWidth();
        if (intersectionWidth != -1) {
          List<Integer> idList = new ArrayList<>();
          idList.add(currRow);
          idList.add(currCol);
          intersectionList.add(idList);
        }
      }
    }
    return intersectionList;
  }

  private void createIntersectionMatrix() {
    int maxId = -1;
    for (Integer currId : elementMap.keySet()) {
      if (currId > maxId) {
        maxId = currId;
      }
    }

    intersections = new Shape[maxId][maxId];

    for (Map.Entry<Integer, Node> currEntry : elementMap.entrySet()) {
      for (Map.Entry<Integer, Node> interEntry : elementMap.entrySet()) {

        int currId = currEntry.getKey();
        int interId = interEntry.getKey();

        if (currId != interId) {
          Shape currShape = (Shape) currEntry.getValue();
          Shape interShape = (Shape) interEntry.getValue();
          Shape currIntersection = Shape.intersect(currShape, interShape);

          intersections[currId][interId] = currIntersection;
        }
      }
    }
  }


}
