package oogasalad.view.AuthoringScreens;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.view.Controlling.AuthoringController;
import oogasalad.view.Window;

/**
 * Parent class for all screens related to creating a new game in authoring environment
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public abstract class AuthoringScreen {

  double screenWidth = Window.SCREEN_WIDTH;
  double screenHeight = Window.SCREEN_HEIGHT;
  StackPane authoringBox;
  List<Shape> selectableShapes;
  Map<Shape, Boolean> newTemplateMap;
  StackPane root;
  Scene scene;
  ColorPicker colorPicker;
  Button imageButton;
  AuthoringController controller;
  Slider xSlider;
  Slider ySlider;
  Slider angleSlider;
  Shape selectedShape;
  final int authoringBoxWidth = 980;
  final int authoringBoxHeight = 980;

  public AuthoringScreen(AuthoringController controller, StackPane authoringBox) {
    this.controller = controller;
    this.authoringBox = authoringBox;
    selectableShapes = new ArrayList<>();
    newTemplateMap = new HashMap<>();
    createScene();
  }

  /**
   * Returns the scene for the current selection process
   *
   * @return scene for the current selection process
   */
  public Scene getScene() {
    return scene;
  }

  /**
   * Determines behavior when current selection is finalized; triggered by next or submit button
   */
  abstract void endSelection();

  /**
   * Creates the scene elements and adds them to the root
   */
  abstract void createScene();

  /**
   * Specifies the type of images to offer (background, goal, obstacle, or ball)
   *
   * @return enum representing type of images to offer based on current selection screen
   */
  abstract ImageType getImageType();

  /**
   * Opens a file dialogue box for user to choose image and displays image in currently selected
   * shape
   */
  void createImageHandler() {
    imageButton.setOnAction(event -> {
      Image image = chooseImage(getImageType());
      if (image != null && selectedShape != null) {
        selectedShape.setFill(new ImagePattern(image));
      }
    });
  }

  /**
   * Creates a file dialogue box for user to choose image, initial folder is determined from current
   * selection screen
   *
   * @param imageType the type of elements the user is selecting (background, goal, obstacle, or
   *                  ball)
   * @return the selected image
   */
  Image chooseImage(ImageType imageType) {
    FileChooser fileChooser = new FileChooser();

    File initialDirectory = new File(getImageFolder(imageType));
    fileChooser.setInitialDirectory(initialDirectory);

    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
    );
    File selectedFile = fileChooser.showOpenDialog(new Stage());
    String imagePath = selectedFile.getAbsolutePath();

    try {
      File file = new File(imagePath);
      String imageUrl = file.toURI().toURL().toString(); // Convert the file path to a URL
      return new Image(imageUrl);
    } catch (MalformedURLException e) {
      return null;
      //TODO: exception handling
    }
  }

  /**
   * Creates buttons for color and image selection and their respective event handlers
   */
  void createShapeDisplayOptionBox() {
    colorPicker = new ColorPicker();
    colorPicker.setPrefSize(200, 100);
    StackPane.setAlignment(colorPicker, Pos.TOP_RIGHT);
    StackPane.setMargin(colorPicker, new Insets(50, 50, 0, 0));
    createColorPickerHandler();
    root.getChildren().addAll(colorPicker);

    imageButton = new Button("Image");
    imageButton.setPrefSize(200, 100);
    StackPane.setAlignment(imageButton, Pos.TOP_RIGHT);
    StackPane.setMargin(imageButton, new Insets(160, 50, 0, 0));
    createImageHandler();
    root.getChildren().add(imageButton);
  }

  /**
   * Creates next button for user to progress to next selection screen
   */
  void createNextButton() {
    Button nextButton = new Button("Next");
    nextButton.setPrefSize(100, 50);
    nextButton.setOnMouseClicked(event -> endSelection());
    StackPane.setAlignment(nextButton, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(nextButton, new Insets(0, 50, 50, 0));
    root.getChildren().add(nextButton);
  }

  /**
   * Creates a rectangle and circle template that users can drag onto gameboard
   */
  void createDraggableShapeTemplates() {
    Rectangle rectangle = new Rectangle(100, 50, Color.BLACK);
    StackPane.setAlignment(rectangle, Pos.TOP_RIGHT);
    StackPane.setMargin(rectangle, new Insets(300, 150, 0, 0));
    makeSelectable(rectangle);
    makeDraggable(rectangle);

    Ellipse ellipse = new Ellipse(30, 30);
    ellipse.setFill(Color.BLACK);
    StackPane.setAlignment(ellipse, Pos.TOP_RIGHT);
    StackPane.setMargin(ellipse, new Insets(300, 50, 0, 0));
    makeSelectable(ellipse);
    makeDraggable(ellipse);

    root.getChildren().addAll(rectangle, ellipse);
  }

  /**
   * Creates slider for user to change shape size
   */
  void createSizeAndAngleSliders() {
    VBox sliderContainerBox = new VBox();
    sliderContainerBox.setPrefSize(200, 10);
    sliderContainerBox.setAlignment(Pos.CENTER_RIGHT);
    sliderContainerBox.setPadding(new Insets(-100, 50, 0, 0));

    xSlider = createSizeSlider("X Scale", sliderContainerBox);
    ySlider = createSizeSlider("Y Scale", sliderContainerBox);
    angleSlider = createAngleSlider(sliderContainerBox);

    xSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeXSize(newValue.doubleValue()));
    ySlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeYSize(newValue.doubleValue()));
    angleSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeAngle(newValue.doubleValue()));

    root.getChildren().add(sliderContainerBox);
  }

  /**
   * Updates authoring box by adding selected game objects to the stackpane
   */
  void addNewSelectionsToAuthoringBox(){
    Group selections = new Group();
    for (Shape shape : selectableShapes){
      Bounds shapeBounds = shape.getBoundsInParent();
      Bounds authoringBoxBounds = authoringBox.getBoundsInParent();

      if(selectedShape == shape){
        shape.setStrokeWidth(0);
      }

      if (authoringBoxBounds.contains(shapeBounds)) {
        selections.getChildren().add(shape);
      }
    }

    authoringBox.getChildren().add(selections);
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
    sliderContainer.setAlignment(Pos.CENTER_RIGHT);
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
    sliderContainer.setAlignment(Pos.CENTER_RIGHT);
    sliderContainer.setSpacing(10);

    sliderContainerBox.getChildren().add(sliderContainer);
    return slider;
  }

  private void changeAngle(double angle) {
    if (selectedShape != null) {
      selectedShape.setRotate(angle);
    }
  }

  private void changeXSize(double xScale) {
    if (selectedShape != null) {
      selectedShape.setScaleX(xScale);
    }
  }

  private void changeYSize(double yScale) {
    if (selectedShape != null) {
      selectedShape.setScaleY(yScale);
    }
  }

  private void makeDraggable(Shape shape) {
    double[] startPos = new double[2];
    double[] translatePos = new double[2];
    shape.setOnMousePressed((MouseEvent event) -> {

      if (newTemplateMap.get(shape)) {
        createDraggableShapeTemplates();
        //TODO: duplicate show is created (not sure how to fix this without instanceOf check)
        // For example, if rectangle is dragged a new rectangle AND circle is created
      }

      startPos[0] = event.getSceneX();
      startPos[1] = event.getSceneY();
      translatePos[0] = shape.getTranslateX();
      translatePos[1] = shape.getTranslateY();
    });

    shape.setOnMouseDragged((MouseEvent event) -> {
      double offsetX = event.getSceneX() - startPos[0];
      double offsetY = event.getSceneY() - startPos[1];
      double newTranslateX = translatePos[0] + offsetX;
      double newTranslateY = translatePos[1] + offsetY;
      shape.setTranslateX(newTranslateX);
      shape.setTranslateY(newTranslateY);
    });

    shape.setOnMouseReleased((MouseEvent event) -> {
      checkForAuthoringBoxIntersection(shape);
    });
  }

  private void checkForAuthoringBoxIntersection(Shape shape) {
    //TODO: consider if changing visibility is sufficient
    Bounds shapeBounds = shape.getBoundsInParent();
    Bounds authoringBoxBounds = authoringBox.getBoundsInParent();

    if (!authoringBoxBounds.contains(shapeBounds)) {
      shape.setVisible(false);
    }
  }

  private void makeSelectable(Shape shape) {
    selectableShapes.add(shape);
    newTemplateMap.put(shape, true);
    shape.setOnMouseClicked(event -> {
      selectedShape = shape;
      shape.setStroke(Color.YELLOW);
      updateSlider(shape.getScaleX(), shape.getScaleY(), shape.getRotate());
      shape.setStrokeWidth(3);
      for (Shape currShape : selectableShapes) {
        if (currShape != selectedShape) {
          currShape.setStrokeWidth(0);
        }
      }
    });
  }

  private void updateSlider(double xScale, double yScale, double angle) {
    xSlider.setValue(xScale);
    ySlider.setValue(yScale);
    angleSlider.setValue(angle);
  }

  private void createColorPickerHandler() {
    colorPicker.setOnAction(
        event -> {
          if (selectedShape != null) {
            selectedShape.setFill(((ColorPicker) event.getSource()).getValue());
          }
        });
  }

  private String getImageFolder(ImageType imageType) {
    String path = System.getProperty(("user.dir"));
    switch (imageType) {
      case BACKGROUND -> {
        return path + "/data/background_images";
      }
      case GOAL -> {
        return path + "/data/goal_images";
      }
      case OBSTACLE -> {
        return path + "/data/obstacle_images";
      }
      default -> {
        return path + "/data";
      }
    }

  }
}
