package oogasalad.view.AuthoringScreens;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
  Map<Shape, NonControllableType> nonControllableMap;
  List<Shape> controllableList;
  double screenWidth = Window.SCREEN_WIDTH;
  double screenHeight = Window.SCREEN_HEIGHT;
  public StackPane authoringBox;
  List<Shape> selectableShapes;
  Map<Shape, Boolean> newTemplateMap;
  AnchorPane root;
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
  Map<Shape, List<Double>> posMap;
  Map<Shape, String> imageMap;

  public AuthoringScreen(AuthoringController controller, StackPane authoringBox,
      Map<Shape, List<Double>> posMap, Map<Shape, NonControllableType> nonControllableMap,
      List<Shape> controllableList, Map<Shape, String> imageMap) {
    this.nonControllableMap = nonControllableMap;
    this.controllableList = controllableList;
    this.posMap = posMap;
    this.imageMap = imageMap;
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
   * Updates buttons, dropdowns, textfields, etc. to correctly display user selected options when a
   * new shape is selected
   */
  abstract void updateOptionSelections();

  /**
   * Opens a file dialogue box for user to choose image and displays image in currently selected
   * shape
   */
  void createImageHandler() {
    imageButton.setOnAction(event -> {
      String relativePath = chooseImage(getImageType());
      if (relativePath != null && selectedShape != null) {
        imageMap.put(selectedShape, relativePath);
        System.out.println(relativePath);
        System.out.println(relativePath);
        String imgPath = Paths.get(relativePath).toUri().toString();
        selectedShape.setFill(new ImagePattern(new Image(imgPath)));
      }
    });
  }

  /**
   * Creates, positions, and styles title
   *
   * @param title the title text to display
   */
  void createTitle(String title) {
    Text titleText = new Text(title);
    titleText.setFont(Font.font("Arial", 30));
    AnchorPane.setTopAnchor(titleText, 5.0);
    AnchorPane.setLeftAnchor(titleText, 50.0);
    root.getChildren().add(titleText);
  }

  /**
   * Creates a file dialogue box for user to choose image, initial folder is determined from current
   * selection screen
   *
   * @param imageType the type of elements the user is selecting (background, goal, obstacle, or
   *                  ball)
   * @return the selected image
   */
  String chooseImage(ImageType imageType) {
    FileChooser fileChooser = new FileChooser();

    File initialDirectory = new File(getImageFolder(imageType));
    fileChooser.setInitialDirectory(initialDirectory);

    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
    );
    File file =  fileChooser.showOpenDialog(new Stage());
    return initialDirectory + FileSystems.getDefault().getSeparator() + file.getName();
  }



  /**
   * Creates buttons for color and image selection and their respective event handlers
   */
  void createShapeDisplayOptionBox() {
    colorPicker = new ColorPicker();
    colorPicker.setPrefSize(200, 100);
    AnchorPane.setTopAnchor(colorPicker, 50.0);
    AnchorPane.setRightAnchor(colorPicker, 50.0);
    createColorPickerHandler();
    root.getChildren().addAll(colorPicker);

    imageButton = new Button("Image");
    imageButton.setId("imageButton");
    imageButton.setPrefSize(200, 100);
    AnchorPane.setTopAnchor(imageButton, 160.0);
    AnchorPane.setRightAnchor(imageButton, 50.0);
    createImageHandler();
    root.getChildren().add(imageButton);
  }

  /**
   * Creates next button for user to progress to next selection screen
   */
  void createTransitionButton(String transitionText) {
    Button nextButton = new Button(transitionText);
    nextButton.setPrefSize(100, 50);
    nextButton.setOnMouseClicked(event -> endSelection());
    AnchorPane.setBottomAnchor(nextButton, 50.0);
    AnchorPane.setRightAnchor(nextButton, 50.0);
    root.getChildren().add(nextButton);
  }

  /**
   * Creates a rectangle and circle template that users can drag onto gameboard
   */
  public void createDraggableShapeTemplates() {
    Rectangle rectangle = new Rectangle(100, 50, Color.BLACK);
    AnchorPane.setRightAnchor(rectangle, 150.0);
    AnchorPane.setTopAnchor(rectangle, 300.0);
    makeSelectable(rectangle);
    rectangle.setId("draggableRectangle");
    makeDraggable(rectangle);

    Ellipse ellipse = new Ellipse(30, 30);
    ellipse.setFill(Color.BLACK);
    AnchorPane.setRightAnchor(ellipse, 50.0);
    AnchorPane.setTopAnchor(ellipse, 300.0);
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
    AnchorPane.setTopAnchor(sliderContainerBox, 400.0);
    AnchorPane.setRightAnchor(sliderContainerBox, 50.0);
    sliderContainerBox.setAlignment(Pos.CENTER_RIGHT);

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
  void addNewSelectionsToAuthoringBox() {
    for (Shape shape : selectableShapes) {
      Bounds shapeBounds = shape.getBoundsInParent();
      Bounds authoringBoxBounds = authoringBox.getBoundsInParent();

      if (selectedShape == shape) {
        shape.setStrokeWidth(0);
      }

      if (authoringBoxBounds.contains(shapeBounds)) {
        List<Double> posList = new ArrayList<>();
        posList.add(AnchorPane.getTopAnchor(shape));
        posList.add(AnchorPane.getLeftAnchor(shape));
        posMap.put(shape, posList);
      }
    }

  }

  void addElements() {
    for (Shape shape : posMap.keySet()) {
      shape.setOnMousePressed(null);
      shape.setOnMouseClicked(null);
      shape.setOnMouseDragged(null);
      AnchorPane.setTopAnchor(shape, posMap.get(shape).get(0));
      AnchorPane.setLeftAnchor(shape, posMap.get(shape).get(1));
      root.getChildren().add(shape);
    }
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
      updateOptionSelections();
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
    switch (imageType) {
      case BACKGROUND -> {
        return  "data/background_images";
      }
      case NONCONTROLLABLE_ELEMENT -> {
        return  "data/noncontrollable_images";
      }
      case CONTROLLABLE_ELEMENT -> {
        return  "data/controllable_images";
      }
      default -> {
        return  "data/";
      }
    }

  }
}

