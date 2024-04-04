package oogasalad.view.VisualElements;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import oogasalad.model.api.CollidableRecord;
import oogasalad.model.api.ViewCollidableRecord;

// Temporary imports to be expunged after data dependency established *see Constructor*
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class for a single View Element for the Game engine.
 */
public class GameElement implements VisualElement {

  private Node myNode;
  private int id;

  public GameElement(int id) {
    this.id = id;
  }

  /**
   * getter for element id
   *
   * @return element id
   */
  public int getId() {
    return id;
  }

  /**
   * Updates the Element properties.
   *
   * @param model This Element's corresponding model object.
   */
  @Override
  public void update(CollidableRecord model) {
    myNode.setTranslateX(model.x());
    myNode.setTranslateY(model.y());
    myNode.setVisible(model.visible());
  }

  /**
   * Returns the Node housed by this Element.
   *
   * @return Node  This Element's Node.
   */
  @Override
  public Node getNode() {
    return myNode;
  }

  public void createElement(ViewCollidableRecord record) {
    id = record.collidableId();
    //ask about color
    Color color = getColorFromRecord(record);
    myNode = getShapeFromRecord(record, color, record.xdimension(), record.ydimention());
  }

  //use reflection in the future
  private Shape getShapeFromRecord(ViewCollidableRecord record, Color color, double width, double height) {
    String shape = record.shape();
    switch (shape.toLowerCase()) {
      case "rectangle" -> {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(color);
        return rectangle;
      }
      case "circle" -> {
        Circle circle = new Circle(width);
        circle.setFill(color);
        return circle;
      }
    }
    return null;
  }

  private Color getColorFromRecord(ViewCollidableRecord record){
    List<Integer> rgbValues = record.color();
    return Color.rgb(rgbValues.get(0), rgbValues.get(1), rgbValues.get(2));
  }

}
