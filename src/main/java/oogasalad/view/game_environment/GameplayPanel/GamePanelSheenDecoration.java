package oogasalad.view.game_environment.GameplayPanel;

import javafx.scene.shape.Rectangle;
import oogasalad.view.visual_elements.CompositeElement;

public class GamePanelSheenDecoration extends GamePanel {
  private final Rectangle globalSheen;
  public GamePanelSheenDecoration(CompositeElement compositeElement) {
    super(compositeElement);
    globalSheen = new Rectangle();
  }
  @Override
  public void addGameContentNodes(CompositeElement elements){
    super.addGameContentNodes(elements);
    globalSheen.setWidth(getGlobalView().getBoundsInLocal().getWidth());
    globalSheen.setHeight(getGlobalView().getBoundsInLocal().getHeight());
    globalSheen.setOpacity(0.5);
    getGlobalView().getChildren().add(globalSheen);
  }
}
