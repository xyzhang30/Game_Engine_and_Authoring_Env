package oogasalad.view.VisualElements;

import java.util.List;
import javafx.scene.Node;
import oogasalad.model.api.CollidableRecord;
import oogasalad.model.api.ViewCollidableRecord;

// Temporary imports to be expunged after data dependency established *see Constructor*
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Class for a single View Element for the Game engine.
 */
public class GameElement implements VisualElement {

  private final Node myNode;
  private final int id;

  public GameElement(ViewCollidableRecord viewData) {
    id = viewData.id();
    myNode = makeShape(viewData);
  }
  private Node makeShape(ViewCollidableRecord data){
    List<Integer> rgb = data.color();
    Color color = Color.rgb(rgb.get(0),rgb.get(1),rgb.get(2));
//    return switch (data.shape().toLowerCase()) { // Convert to reflection at later date
//      case "circle" -> return new Circle(data.startXpos(), data.startYpos(), data.width(), color);
//      case "rectangle" -> Rectangle rectangle = new Rectangle(data.width(), data.height(), color);
//      default -> null; // Throw type not found exception
//    };

    switch (data.shape().toLowerCase()) {
      case "circle" -> {return new Circle(data.startXpos(), data.startYpos(), data.width(), color);}
      case "rectangle" -> {Rectangle rectangle = new Rectangle(data.width(), data.height(), color);
      rectangle.setX(data.startXpos());
      rectangle.setY(data.startYpos());
      return rectangle;}
    };
    return null;
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
}
