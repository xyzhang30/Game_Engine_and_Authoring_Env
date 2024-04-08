package oogasalad.view.AuthoringScreens;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.view.Window;

public abstract class AuthoringScreen {

  double screenWidth = Window.SCREEN_WIDTH;
  double screenHeight = Window.SCREEN_HEIGHT;
  StackPane root;
  Scene scene;
  ColorPicker colorPicker;

  String chooseImage(ImageType imageType) {
    FileChooser fileChooser = new FileChooser();

    File initialDirectory = new File(getImageFolder(imageType));
    fileChooser.setInitialDirectory(initialDirectory);

    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
    );
    File selectedFile = fileChooser.showOpenDialog(new Stage());
    return selectedFile.getAbsolutePath();
    //TODO: this may be null, handle this case
  }

  private String getImageFolder(ImageType imageType) {
    switch (imageType) {
      case BACKGROUND -> {
        return "C:\\Users\\jhayt\\CS308\\oogasalad_team01\\data\\background_images";
      }
      default -> {
        return "C:\\Users\\jhayt\\CS308\\oogasalad_team01\\data";
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


    Button imageButton = new Button("Image");
    imageButton.setPrefSize(200, 100);
    StackPane.setAlignment(imageButton, Pos.TOP_RIGHT);
    StackPane.setMargin(imageButton, new Insets(160, 50, 0, 0));
    root.getChildren().add(imageButton);
  }

  abstract void changeColor();


}
