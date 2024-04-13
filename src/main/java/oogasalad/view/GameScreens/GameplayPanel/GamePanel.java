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
  public void addGameContentNodes(CompositeElement elements){
    for (int i : elements.idList()) {
      globalView.getChildren().add(elements.getNode(i));
    }
    Sheen sheen = new Sheen();
    sheen.setSize(globalView);
    globalView.getChildren().add(sheen.getNode());
  }
}
