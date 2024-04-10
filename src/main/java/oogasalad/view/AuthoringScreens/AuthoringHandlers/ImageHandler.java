package oogasalad.view.AuthoringScreens.AuthoringHandlers;

import java.net.MalformedURLException;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;

import java.io.File;
import javafx.stage.Stage;
import oogasalad.view.AuthoringScreens.ImageType;

public class ImageHandler {

  private Stage stage;

  public ImageHandler(Stage stage) {
  }

  public static void applyImageToShape(Shape shape, ImageType imageType) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(getImageFolder(imageType)));
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
    );
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      try {
        Image image = new Image(file.toURI().toURL().toString());
        shape.setFill(new ImagePattern(image));
      } catch (MalformedURLException e) {
        e.printStackTrace(); // Proper error handling here
      }
    }
  }

  private static String getImageFolder(ImageType imageType) {
    String path = System.getProperty(("user.dir"));
    switch (imageType) {
      case BACKGROUND -> {
        return path + "/data/background_images";
      }
      case NONCONTROLLABLE_ELEMENT -> {
        return path + "/data/noncontrollable_images";
      }
      case CONTROLLABLE_ELEMENT -> {
        return path + "/data/controllable_images";
      }
      default -> {
        return path + "/data";
      }
    }

  }
}
