package oogasalad.view.VisualElements;

import javafx.scene.Node;
import oogasalad.model.api.CollidableRecord;

// Temporary imports to be expunged after data dependency established *see InitializeDefaultNode()
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class for a single View Element for the Game engine.
 */
public class GameElement implements VisualElement{
  private final Node myNode;
  private final int id;
  public GameElement(CollidableRecord model){
    id = model.id();
    myNode = InitializeDefaultNode(id);
    update(model);
  }

  /**
   * Initializes a new node's sprite based on data from the CSS file.
   * @param id  ID number of Node to initialize.
   */
  private Node InitializeDefaultNode(int id){
    return new Circle(10, Color.RED); // TO BE IMPLEMENTED! TALK TO ALISHA/JUDY
  }

  /**
   * Updates the Element properties.
   * @param model  This Element's corresponding model object.
   */
  public void update(CollidableRecord model){
    myNode.setTranslateX(model.x());
    myNode.setTranslateY(model.y());
    myNode.setVisible(model.visible());
  }

  /**
   * Returns the Node housed by this Element.
   * @return Node  This Element's Node.
   */
  public Node getNode(){
    return myNode;
  }
}
