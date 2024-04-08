package oogasalad.view.AuthoringScreens;

import java.awt.Image;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.view.Window;

public abstract class AuthoringScreen {

  double screenWidth = Window.SCREEN_WIDTH;
  double screenHeight = Window.SCREEN_HEIGHT;
  StackPane root;
  Scene scene;

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


}
