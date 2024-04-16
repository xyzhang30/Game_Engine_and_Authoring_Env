package oogasalad.view.game_environment.GameplayPanel;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import oogasalad.view.visual_elements.CompositeElement;

public class GamePanel {
  private final Pane globalView;
  private double scaleX;
  private double scaleY;
  private double offsetX;
  private double offsetY;
  public GamePanel(CompositeElement compositeElement){
    globalView = new AnchorPane();
    addGameContentNodes(compositeElement);

    scaleX = 1;
    scaleY = 1;
    offsetX = 0;
    offsetY = 0;
  }
  public Pane getGlobalView(){
    return globalView;
  }
  public void addGameContentNodes(CompositeElement cm){
    for (int i : cm.idList()) {
      globalView.getChildren().add(cm.getNode(i));
    }
  }
  public void TransformLocal(){
    globalView.setScaleX(scaleX);
    globalView.setScaleY(scaleY);
    globalView.setTranslateX(globalView.getBoundsInLocal().getCenterX()*(scaleX-1)-offsetX);
    globalView.setTranslateY(globalView.getBoundsInLocal().getCenterY()*(scaleY-1)-offsetY);
  }
  public void modifyScope(double del){
    scaleX *= del;
    scaleY *= del;
    TransformLocal();
  }
  public Pane getPane(){
    return globalView;
  }
}
