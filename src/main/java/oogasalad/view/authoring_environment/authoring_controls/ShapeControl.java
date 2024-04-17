//package oogasalad.view.authoring_environment.authoring_controls;
//
//import java.util.ArrayList;
//import java.util.List;
//import javafx.geometry.Bounds;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Shape;
//import oogasalad.view.authoring_environment.panels.ShapePanel;
//
//public class ShapeControl {
//  ShapePanel shapePanel;
//  Shape shape;
//  Coordinate startPos;
//  Coordinate translatePos;
//
//  public ShapeControl(ShapePanel shapePanel, Shape shape) {
//    this.shapePanel = shapePanel;
//    this.shape = shape;
//  }
//
//  public void makeSelectable() {
//    shape.setStroke(Color.YELLOW);
//    shape.setStrokeWidth(3);
//    for (Shape currShape : shapePanel.getSelectableShapeList()) {
//      if (currShape != shape) {
//        currShape.setStrokeWidth(0);
//      }
//    }
//  }
//
//  public void initDrag(MouseEvent event) {
//    startPos = new Coordinate(event.getSceneX(), event.getSceneY());
//    translatePos = new Coordinate(shape.getTranslateX(), shape.getTranslateY());
//  }
//
//  public Shape completeDrag(MouseEvent event) {
//    Coordinate offset = new Coordinate(event.getSceneX() - startPos.x(), event.getSceneY() - startPos.y());
//    Coordinate newTranslatePos = new Coordinate(translatePos.x() + offset.x(), translatePos.y() + offset.y());
//    Shape newShape = Shape.union(shape, shape);
//    newShape.setTranslateX(newTranslatePos.x());
//    newShape.setTranslateY(newTranslatePos.y());
//    return newShape;
//  }
//
//  public void checkForAuthoringBoxIntersection() {
//    Bounds shapeBounds = shape.getBoundsInParent();
//    Bounds authoringBoxBounds = shapePanel.getAuthoringBox().getBoundsInParent();
//    if (!authoringBoxBounds.contains(shapeBounds)) {
//      shape.setVisible(false);
//    }
//  }
//
//  public void addToAuthoringBox() {
//    Bounds shapeBounds = shape.getBoundsInParent();
//    Bounds authoringBoxBounds = shapePanel.getAuthoringBox().getBoundsInParent();
//    shape.setStrokeWidth(0);
//    if (authoringBoxBounds.contains(shapeBounds)) {
//      List<Double> posList = new ArrayList<>();
//      posList.add(AnchorPane.getTopAnchor(shape));
//      posList.add(AnchorPane.getLeftAnchor(shape));
//      shapePanel.getShapePositionMap().put(shape, posList);
//    }
//  }
//  public void changeAngle(double angle) {
//    shape.setRotate(angle);
//  }
//  public void changeXSize(double xScale) {
//    shape.setScaleX(xScale);
//  }
//  public void changeYSize(double yScale) {
//    shape.setScaleY(yScale);
//  }
//
//}
