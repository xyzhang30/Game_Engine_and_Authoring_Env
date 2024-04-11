package oogasalad.view.GameScreens;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import oogasalad.view.VisualElements.CompositeElement;

public class GameContentPanel {
  private final Pane globalView;
  private final Rectangle globalSheen;
  private double scaleX;
  private double scaleY;
  private double offsetX;
  private double offsetY;
  public GameContentPanel(CompositeElement compositeElement){
    globalView = new AnchorPane();
    globalSheen = new Rectangle();
    addGameContentNodes(compositeElement);
    scaleX = 1;
    scaleY = 1;
    offsetX = 0;
    offsetY = 0;
  }
  public void addGameContentNodes(CompositeElement cm){
    for (int i : cm.idList()) {
      globalView.getChildren().add(cm.getNode(i));
    }
    globalSheen.setWidth(globalView.getBoundsInLocal().getWidth());
    globalSheen.setHeight(globalView.getBoundsInLocal().getHeight());
    globalSheen.setOpacity(0);
    globalView.getChildren().add(globalSheen);
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
