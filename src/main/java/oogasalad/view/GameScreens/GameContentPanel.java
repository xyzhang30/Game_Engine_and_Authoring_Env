package oogasalad.view.GameScreens;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.view.VisualElements.CompositeElement;

public class GameContentPanel {
  private final Pane globalView;
  private double scaleX;
  private double scaleY;
  private double offsetX;
  private double offsetY;
  public GameContentPanel(CompositeElement compositeElement){
    globalView = new AnchorPane();
    addNodes(compositeElement);
    globalView.getChildren().add(new Rectangle(globalView.getBoundsInLocal().getWidth(),globalView.getBoundsInLocal().getHeight(),Color.TRANSPARENT));
    scaleX = 1;
    scaleY = 1;
    offsetX = 0;
    offsetY = 0;
  }
  public void addNodes(CompositeElement cm){
    for (int i : cm.idList()) {
      globalView.getChildren().add(cm.getNode(i));
    }
  }
  public void SyncLocalView(){
    globalView.setScaleX(scaleX);
    globalView.setScaleY(scaleY);
    globalView.setTranslateX(globalView.getBoundsInLocal().getCenterX()*(scaleX-1)-offsetX);
    globalView.setTranslateY(globalView.getBoundsInLocal().getCenterY()*(scaleY-1)-offsetY);
  }
  public void modifyScope(double del){
    scaleX *= del;
    scaleY *= del;
    SyncLocalView();
  }
  public Pane getPane(){
    SyncLocalView();
    return globalView;
  }
}
