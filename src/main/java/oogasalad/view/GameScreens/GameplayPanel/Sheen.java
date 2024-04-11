package oogasalad.view.GameScreens.GameplayPanel;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class Sheen {
  private final Rectangle sheen;
  public Sheen() {
    sheen = new Rectangle();
    sheen.setOpacity(0);
  }
  public Node getNode(){
    return sheen;
  }
  public void setSize(Node content){
    sheen.setWidth(content.getBoundsInLocal().getWidth());
    sheen.setHeight(content.getBoundsInLocal().getHeight());
  }
}
