package oogasalad.view.VisualElements;

import javafx.scene.Node;
import oogasalad.model.api.CollidableRecord;

// Temporary imports to be expunged after data dependency established *see Constructor*
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import oogasalad.model.gameparser.GameLoaderView;

/**
 * Class for a single View Element for the Game engine.
 */
public class GameElement implements VisualElement{
  private final Node myNode;
  public GameElement(int id){
    // Specify type of node (shape/imageview/etc.), dimensions, color, etc. from data.
    GameLoaderView loader = new GameLoaderView("singlePlayerMiniGolf");
    Color color = loader.getViewCollidableInfo().get(id).color();
    myNode = new Circle( 10,Color.RED); // TO BE IMPLEMENTED! TALK TO ALISHA/JUDY
  }

  /**
   * Updates the Element properties.
   * @param model  This Element's corresponding model object.
   */
  @Override
  public void update(CollidableRecord model){
    myNode.setTranslateX(model.x());
    myNode.setTranslateY(model.y());
    myNode.setVisible(model.visible());
  }

  /**
   * Returns the Node housed by this Element.
   * @return Node  This Element's Node.
   */
  @Override
  public Node getNode(){
    return myNode;
  }
}
