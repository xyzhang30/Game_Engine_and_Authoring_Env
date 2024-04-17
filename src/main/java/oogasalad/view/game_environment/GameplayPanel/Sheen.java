package oogasalad.view.game_environment.GameplayPanel;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * A Sheen to cover the game content. Serves as a universal color filter and bounds limiter.
 */
public class Sheen {

  private final Rectangle sheen;

  public Sheen() {
    sheen = new Rectangle();
    sheen.setOpacity(0);
  }

  public Node getNode() {
    return sheen;
  }

  public void setSize(Node content) {
    sheen.setWidth(content.getBoundsInLocal().getWidth());
    sheen.setHeight(content.getBoundsInLocal().getHeight());
  }
}
