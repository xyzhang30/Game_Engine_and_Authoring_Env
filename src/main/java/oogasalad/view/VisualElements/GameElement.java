package oogasalad.view.VisualElements;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import oogasalad.model.api.CollidableRecord;

// Temporary imports to be expunged after data dependency established *see Constructor*
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import oogasalad.model.api.ViewCollidableRecord;
import oogasalad.model.gameparser.GameLoaderView;

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
    myNode = getShapeFromRecord(record, color);
    myNode.setTranslateX(record.xdimension());
    myNode.setTranslateY(record.ydimention());
  }

  private Shape getShapeFromRecord(ViewCollidableRecord record, Color color) {
    String shape = record.shape();
    switch (shape.toLowerCase()) {
      case "rectangle" -> {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(color);
      }
      case "circle" -> {
        Circle circle = new Circle();
        circle.setFill(color);
      }
      default -> {
        return null;
      }
    }
  }
}
