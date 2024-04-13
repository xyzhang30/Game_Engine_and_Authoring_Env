package oogasalad.view.GameScreens.GameplayPanel;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class GamePanelLocalView {
  private final Bounds sourceBounds;
  private final Pane localView;
  private final Rectangle camera;
  public GamePanelLocalView(Node source){
    sourceBounds = source.getBoundsInParent();
    localView = new AnchorPane(source);
    camera = new Rectangle();
    setCamera(0,0,sourceBounds.getWidth(),sourceBounds.getHeight());
  }
  public Pane getPane(){
    return localView;
  }
  public void setCamera(double x, double y, double w, double h){
    camera.setX(x);
    camera.setY(y);
    camera.setWidth(w);
    camera.setHeight(h);
    TransformToLocal();
  }
  public void TransformToLocal(){
    double scaleX = camera.getWidth()/sourceBounds.getWidth();
    double scaleY = camera.getHeight()/sourceBounds.getHeight();
    localView.setScaleX(scaleX);
    localView.setScaleY(scaleY);
    localView.setTranslateX(sourceBounds.getCenterX()*(scaleX-1)-camera.getX());
    localView.setTranslateY(sourceBounds.getCenterY()*(scaleY-1)-camera.getY());
  }
}
