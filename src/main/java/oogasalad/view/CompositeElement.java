package oogasalad.view;

import javafx.scene.shape.Shape;
import oogasalad.model.api.CollidableRecord;

public class CompositeElement implements VisualElement{

  public CompositeElement() {


  }

  @Override
  public void update(CollidableRecord record) {
    
  }

  @Override
  public void render() {

  }

  @Override
  public void updatePosition(double x, double y) {

  }

  @Override
  public void setVisible(boolean visible) {

  }

  public Shape getShape(int id) {
    return shape;
  }
}
