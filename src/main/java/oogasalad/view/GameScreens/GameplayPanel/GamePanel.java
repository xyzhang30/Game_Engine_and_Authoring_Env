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
  public void addGameContentNodes(CompositeElement cm){
    for (int i : cm.idList()) {
      globalView.getChildren().add(cm.getNode(i));
    }
  }
  public void setDimensions(double width, double height){
    double scaleX = width/globalView.getBoundsInLocal().getWidth();
    double scaleY = height/globalView.getBoundsInLocal().getHeight();
    globalView.setScaleX(scaleX);
    globalView.setScaleY(scaleY);
    globalView.setTranslateX(globalView.getBoundsInLocal().getCenterX()*(scaleX-1));
    globalView.setTranslateY(globalView.getBoundsInLocal().getCenterY()*(scaleY-1));
  }
}
