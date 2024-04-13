package oogasalad.view.GameScreens.GameplayPanel;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import oogasalad.view.VisualElements.CompositeElement;

public class GamePanel {
  private final Pane globalView;
  public GamePanel(){
    globalView = new AnchorPane();
  }
  public Pane getPane(){
    return globalView;
  }
  public Pane getGlobalView(){
    return globalView;
  }
  public void addGameContentNodes(CompositeElement cm){
    for (int i : cm.idList()) {
      globalView.getChildren().add(cm.getNode(i));
    }
  }
}
