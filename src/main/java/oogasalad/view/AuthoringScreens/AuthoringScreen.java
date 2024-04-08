package oogasalad.view.AuthoringScreens;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.view.Controlling.AuthoringController;
import oogasalad.view.Window;

public abstract class AuthoringScreen {

  double screenWidth = Window.SCREEN_WIDTH;
  double screenHeight = Window.SCREEN_HEIGHT;
  StackPane root;
  Scene scene;
  ColorPicker colorPicker;
  Button imageButton;
  AuthoringController controller;
  Shape selectedShape;

  public Scene getScene() {
    return scene;
  }

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
      javafx.scene.image.Image image = new Image(imageUrl);
      return image;
    } catch (MalformedURLException e) {
      e.printStackTrace();
      //TODO: this may be null, handle this case
    }
    return null;
  }

  private String getImageFolder(ImageType imageType) {
    String path = System.getProperty(("user.dir"));
    switch (imageType) {
      case BACKGROUND -> {
        return path + "/data/background_images";
      }
      default -> {
        return path + "/data";
      }
    }

  }

  void createOptionBox(){
    colorPicker = new ColorPicker();
    colorPicker.setPrefSize(200, 100);
    StackPane.setAlignment(colorPicker, Pos.TOP_RIGHT);
    StackPane.setMargin(colorPicker, new Insets(50, 50, 0, 0));
    changeColor();
    root.getChildren().addAll(colorPicker);


    imageButton = new Button("Image");
    imageButton.setPrefSize(200, 100);
    StackPane.setAlignment(imageButton, Pos.TOP_RIGHT);
    StackPane.setMargin(imageButton, new Insets(160, 50, 0, 0));
    changeImage();
    root.getChildren().add(imageButton);
  }

  void createNextButton(){
    Button nextButton = new Button("Next");
    nextButton.setPrefSize(100, 50);
    nextButton.setOnMouseClicked(event -> endSelection());
    StackPane.setAlignment(nextButton, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(nextButton, new Insets(0, 50, 50, 0));
    root.getChildren().add(nextButton);
  }

  void makeSelectable(Shape shape, List<Shape> gameElements) {
    shape.setOnMouseClicked(event -> {
      selectedShape = shape;
      shape.setStroke(Color.YELLOW);
      shape.setStrokeWidth(3);
      for(Shape element : gameElements){
        if(element != selectedShape){
          element.setStrokeWidth(0);
        }
      }
    });
  }

  abstract void endSelection();

  abstract void changeColor();

  abstract void changeImage();


}
