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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
  Shape selectedShape;

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
      if (image != null) {
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
    selectedShape = rectangle;

    Circle circle = new Circle(30, Color.BLACK);
    StackPane.setAlignment(circle, Pos.TOP_RIGHT);
    StackPane.setMargin(circle, new Insets(300, 50, 0, 0));
    makeSelectable(circle);
    makeDraggable(circle);

    root.getChildren().addAll(rectangle, circle);
  }

  /**
   * Creates slider for user to change shape size
   */
  void createSizeSlider(){

    Slider slider = new Slider();
    slider.setPrefWidth(200);
    slider.setMin(0);
    slider.setMax(2);
    slider.setValue(1);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(0.1);
    slider.setOrientation(Orientation.HORIZONTAL);

    HBox sliderContainer = new HBox(slider);
    sliderContainer.setPrefSize(200, 10);

    sliderContainer.setAlignment(Pos.CENTER_RIGHT);
    sliderContainer.setPadding(new Insets(-100, 50, 0, 0));

    slider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeSize(newValue.doubleValue()));

    root.getChildren().add(sliderContainer);

  }

  private void changeSize(double fractionalValue){
    //changeSize of selected shape
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

  private void checkForAuthoringBoxIntersection(Shape shape){
    //TODO: delete goal if it is not placed on gameboard
  }

  private void makeSelectable(Shape shape) {
    selectableShapes.add(shape);
    newTemplateMap.put(shape, true);
    shape.setOnMouseClicked(event -> {
      selectedShape = shape;
      shape.setStroke(Color.YELLOW);
      shape.setStrokeWidth(3);
      for (Shape currShape : selectableShapes) {
        if (currShape != selectedShape) {
          currShape.setStrokeWidth(0);
        }
      }
    });
  }

  private void createColorPickerHandler() {
    colorPicker.setOnAction(
        event -> selectedShape.setFill(((ColorPicker) event.getSource()).getValue()));
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
      default -> {
        return path + "/data";
      }
    }

  }
}
