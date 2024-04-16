package oogasalad.view.authoring_environment.panels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import oogasalad.view.authoring_environment.authoring_controls.Coordinate;

public class ShapePanel implements Panel {

  protected final ShapeProxy shapeProxy;
  protected final AuthoringProxy authoringProxy;
  protected final StackPane authoringBox;
  protected final AnchorPane rootPane;
  private final Set<Shape> addedShapes = new HashSet<>();
  private final List<Shape> templateShapes = new ArrayList<>();
  private Coordinate startPos;
  private Coordinate translatePos;
  private Slider xSlider;
  private Slider ySlider;
  private Slider angleSlider;

  public ShapePanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane rootPane, StackPane authoringBox) {
    this.shapeProxy = shapeProxy;
    this.authoringProxy = authoringProxy;
    this.rootPane = rootPane;
    this.authoringBox = authoringBox;
    createElements();
    handleEvents();
  }
  @Override
  public void createElements() {
    createSizeAndAngleSliders(); // strategy
    createTemplateShapes(); // strategy
  }
  @Override
  public void handleEvents() {
    for (Shape shape: templateShapes) {
      handleShapeEvents(shape);
    }
  }

  private void handleShapeEvents(Shape shape) {
    shape.setOnMouseClicked(event -> makeSelectable(shape));
    shape.setOnMousePressed(event -> initDrag(shape, event));
    shape.setOnMouseDragged(event -> completeDrag(shape, event));
    shape.setOnMouseReleased(event -> addToAuthoringBox());
  }

  private void createTemplateShapes() {
    Rectangle rectangle = new Rectangle(100, 50, Color.BLACK);
    AnchorPane.setRightAnchor(rectangle, 150.0);
    AnchorPane.setTopAnchor(rectangle, 300.0);
    rectangle.setId("rectangleTemplate");

    Ellipse ellipse = new Ellipse(30, 30);
    ellipse.setFill(Color.BLACK);
    AnchorPane.setRightAnchor(ellipse, 50.0);
    AnchorPane.setTopAnchor(ellipse, 300.0);
    ellipse.setId("ellipseTemplate");

    templateShapes.addAll(List.of(rectangle, ellipse));
    rootPane.getChildren().addAll(List.of(rectangle, ellipse));
  }
//  private void addElements() {
//    for (Shape shape : shapePositionMap.keySet()) {
//      shape.setOnMousePressed(null);
//      shape.setOnMouseClicked(null);
//      shape.setOnMouseDragged(null);
//      AnchorPane.setTopAnchor(shape, shapePositionMap.get(shape).get(0));
//      AnchorPane.setLeftAnchor(shape, shapePositionMap.get(shape).get(1));
//      rootPane.getChildren().add(shape);
//    }
//  }
  private void makeSelectable(Shape shape) {
    shapeProxy.setShape(shape);
    shape.setStroke(Color.YELLOW);
    shape.setStrokeWidth(3);
    for (Shape currShape : addedShapes) {
      updateSlider(shape.getScaleX(), shape.getScaleY(), shape.getRotate());
      if (currShape != shape) {
        currShape.setStrokeWidth(0);
      }
    }
  }
  private void initDrag(Shape shape, MouseEvent event) {
    // TODO: make a copy for keeping a template -> BETTER DESGIN?
    Shape duplicateShapeTemplate = Shape.union(shape, shape);
    rootPane.getChildren().add(duplicateShapeTemplate);
    handleShapeEvents(duplicateShapeTemplate);

    startPos = new Coordinate(event.getSceneX(), event.getSceneY());
    translatePos = new Coordinate(shape.getTranslateX(), shape.getTranslateY());
  }
  private void completeDrag(Shape shape, MouseEvent event) {
    Coordinate offset = new Coordinate(event.getSceneX() - startPos.x(), event.getSceneY() - startPos.y());
    Coordinate newTranslatePos = new Coordinate(translatePos.x() + offset.x(), translatePos.y() + offset.y());
    shape.setTranslateX(newTranslatePos.x());
    shape.setTranslateY(newTranslatePos.y());
  }
  private boolean isInAuthoringBox() {
    Bounds shapeBounds = shapeProxy.getShape().getBoundsInParent();
    Bounds authoringBoxBounds = authoringBox.getBoundsInParent();
    return authoringBoxBounds.contains(shapeBounds);
  }
  private void addToAuthoringBox() {
    if (isInAuthoringBox()) {
      shapeProxy.getShape().setStrokeWidth(0);
      addedShapes.add(shapeProxy.getShape());
      Coordinate coordinate = new Coordinate(AnchorPane.getLeftAnchor(shapeProxy.getShape()), AnchorPane.getTopAnchor(shapeProxy.getShape()));
      authoringProxy.addShapePosition(shapeProxy.getShape(), coordinate);
    }
    else {
      shapeProxy.getShape().setVisible(false);
    }
  }
  private void createSizeAndAngleSliders() {
    VBox sliderContainerBox = new VBox();
    sliderContainerBox.setPrefSize(200, 10);
    AnchorPane.setTopAnchor(sliderContainerBox, 400.0);
    AnchorPane.setRightAnchor(sliderContainerBox, 50.0);
    sliderContainerBox.setAlignment(Pos.CENTER_RIGHT);

    xSlider = createSizeSlider("X Scale", sliderContainerBox);
    xSlider.setId("XSizeSlider");
    ySlider = createSizeSlider("Y Scale", sliderContainerBox);
    ySlider.setId("YSizeSlider");
    angleSlider = createAngleSlider(sliderContainerBox);
    angleSlider.setId("angleSlider");

    xSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeXSize(newValue.doubleValue()));
    ySlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeYSize(newValue.doubleValue()));
    angleSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeAngle(newValue.doubleValue()));

    rootPane.getChildren().add(sliderContainerBox);
  }
  private Slider createAngleSlider(VBox sliderContainerBox) {
    Slider slider = new Slider();
    slider.setPrefWidth(200);
    slider.setMin(0);
    slider.setMax(360);
    slider.setValue(0);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(20);
    slider.setOrientation(Orientation.HORIZONTAL);

    Label label = new Label("Angle");

    HBox sliderContainer = new HBox(label, slider);
    sliderContainer.setSpacing(10);

    sliderContainerBox.getChildren().add(sliderContainer);
    return slider;
  }
  private Slider createSizeSlider(String labelText, VBox sliderContainerBox) {
    Slider slider = new Slider();
    slider.setPrefWidth(200);
    slider.setMin(0.2);
    slider.setMax(2);
    slider.setValue(1);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(0.1);
    slider.setOrientation(Orientation.HORIZONTAL);

    Label label = new Label(labelText);
    HBox sliderContainer = new HBox(label, slider);
    sliderContainer.setSpacing(10);

    sliderContainerBox.getChildren().add(sliderContainer);
    return slider;
  }
  private void updateSlider(double xScale, double yScale, double angle) {
    xSlider.setValue(xScale);
    ySlider.setValue(yScale);
    angleSlider.setValue(angle);
  }
  private void changeAngle(double angle) {
    shapeProxy.getShape().setRotate(angle);
  }
  private void changeXSize(double xScale) {
    shapeProxy.getShape().setScaleX(xScale);
  }
  private void changeYSize(double yScale) {
    shapeProxy.getShape().setScaleY(yScale);
  }



}
