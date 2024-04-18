package oogasalad.view.authoring_environment.panels;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.shape.Shape;
import oogasalad.view.authoring_environment.Coordinate;

public class ShapePanel implements Panel {

  protected final ShapeProxy shapeProxy;
  protected final AuthoringProxy authoringProxy;
  protected final StackPane canvas;
  protected final AnchorPane rootPane;
  protected final AnchorPane containerPane;
  private final List<Shape> templateShapes = new ArrayList<>();
  private Coordinate startPos;
  private Coordinate translatePos;
  private Slider xSlider;
  private Slider ySlider;
  private Slider angleSlider;

  public ShapePanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane rootPane,
      AnchorPane containerPane, StackPane canvas) {
    this.shapeProxy = shapeProxy;
    this.authoringProxy = authoringProxy;
    this.rootPane = rootPane;
    this.containerPane = containerPane;
    this.canvas = canvas;
    createElements();
    handleEvents();
  }

  @Override
  public void createElements() {
    createSizeAndAngleSliders(); // strategy
    templateShapes.addAll(shapeProxy.createTemplateShapes()); // strategy
    containerPane.getChildren().addAll(templateShapes);
  }

  @Override
  public void handleEvents() {
    for (Shape shape : templateShapes) {
      handleShapeEvents(shape);
    }
  }

  // Refactor to the ShapeProxy -> separate into perform different handle events for shape in container (templates) vs shape in canvas
  private void handleShapeEvents(Shape shape) {
    shape.setOnMouseClicked(event -> setShapeOnClick(shape));
    shape.setOnMousePressed(event -> {
      try {
        setShapeOnDrag(shape, event);
      } catch (NoSuchMethodException e) {
        throw new RuntimeException(e);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(e);
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });
    shape.setOnMouseDragged(event -> setShapeOnCompleteDrag(shape, event));
    shape.setOnMouseReleased(event -> setShapeOnRelease(shape));
    // JavaFX drag and drop -> drop target
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
  private void setShapeOnClick(Shape shape) {
    shapeProxy.setShape(shape);
    shape.setStroke(Color.YELLOW);
    if (shape.getStrokeWidth() != 0) {
      shape.setStrokeWidth(5);
    } else {
      shape.setStrokeWidth(0);
    }
    updateSlider(shape.getScaleX(), shape.getScaleY(), shape.getRotate());
//    for (Shape currShape : authoringProxy.getControllables()) {
//      if (!currShape.equals(shape)) {
//        currShape.setStrokeWidth(0);
//      }
//    }
  }

  private void setShapeOnDrag(Shape shape, MouseEvent event)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    // TODO: make a copy for keeping a template -> BETTER DESGIN?
    System.out.println("DRAGGING");
    // shape.getClass(

    System.out.println(shape);
    rootPane.getChildren().add(shape);
    templateShapes.remove(shape);
    shapeProxy.setShape(shape);
    shape.setStroke(Color.GREEN);
    shape.setId(String.valueOf(authoringProxy.getControllables().size() + 1));

    // JavaFX drag and drop -> drop target - example, labe Reflection
    Shape duplicateShape = shape.getClass().getDeclaredConstructor()
        .newInstance(); // TODO: Handle exception

    templateShapes.add(duplicateShape);
    handleShapeEvents(duplicateShape);
    containerPane.getChildren().add(duplicateShape);

    startPos = new Coordinate(event.getSceneX(), event.getSceneY());
    translatePos = new Coordinate(shape.getTranslateX(), shape.getTranslateY());
  }

  private void setShapeOnCompleteDrag(Shape shape, MouseEvent event) {
    System.out.println("DRAGGED");
    System.out.println(shape);
    Coordinate offset = new Coordinate(event.getSceneX() - startPos.x(),
        event.getSceneY() - startPos.y());
    Coordinate newTranslatePos = new Coordinate(translatePos.x() + offset.x(),
        translatePos.y() + offset.y());
    shape.setTranslateX(newTranslatePos.x());
    shape.setTranslateY(newTranslatePos.y());
  }

  private boolean isInAuthoringBox(Shape shape) {
    Bounds shapeBounds = shape.getBoundsInParent();
    Bounds authoringBoxBounds = canvas.getBoundsInParent();
    System.out.println(shapeBounds);
    System.out.println(authoringBoxBounds);
    return authoringBoxBounds.contains(shapeBounds);
  }

  private void setShapeOnRelease(Shape shape) {
    System.out.println("RELEASED");
    System.out.println(shape);
    System.out.println(shape.getTranslateX());
    System.out.println(shape.getTranslateY());
    if (isInAuthoringBox(shape)) {
//      shape.setStrokeWidth(0);
      authoringProxy.addControllableShape(shape);
      Coordinate coordinate = new Coordinate(AnchorPane.getLeftAnchor(shape),
          AnchorPane.getTopAnchor(shape));
      authoringProxy.addShapePosition(shape, coordinate);
    } else {
      shape.setVisible(false);
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

    containerPane.getChildren().add(sliderContainerBox);
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
