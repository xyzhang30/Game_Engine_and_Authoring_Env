package oogasalad.view.GameScreens.GameplayPanel;

import javafx.scene.Node;

public abstract class TransformableNode {
  private final Node node;
  public TransformableNode(Node basis){
    node = basis;
  }
  public double getWidth(){
    return node.getBoundsInLocal().getWidth();
  }
  public double getHeight(){
    return node.getBoundsInLocal().getHeight();
  }
  public void setScaleX(){

  }
  public void scaleY(){

  }
  public void sizeToBounds(double width, double height){
    node.setScaleX();
    double scaleX = width/globalView.getBoundsInLocal().getWidth();
    double scaleY = height/globalView.getBoundsInLocal().getHeight();
    globalView.setScaleX(scaleX);
    globalView.setScaleY(scaleY);
    globalView.setTranslateX(globalView.getBoundsInLocal().getCenterX()*(scaleX-1));
    globalView.setTranslateY(globalView.getBoundsInLocal().getCenterY()*(scaleY-1));
  }

}
