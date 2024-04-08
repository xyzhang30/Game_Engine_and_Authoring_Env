package oogasalad.view.AuthoringScreens;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ColorPicker;
import javafx.scene.shape.Shape;
import oogasalad.view.Controlling.AuthoringController;

public class SurfaceSelectionScreen extends AuthoringScreen {

  private final int backgroundWidth = 980;
  private final int backgroundHeight = 980;
  private Rectangle backgroundBox;

  public SurfaceSelectionScreen(AuthoringController controller) {
    this.controller = controller;
    createScene();
  }

  public void createScene() {
    root = new StackPane();
    createBackgroundBox();
    createOptionBox();
    createNextButton();
    scene = new Scene(root, screenWidth, screenHeight);
  }

  private void createBackgroundBox() {
    backgroundBox = new Rectangle(backgroundWidth, backgroundHeight);
    backgroundBox.setStroke(Color.BLACK);
    backgroundBox.setFill(Color.WHITE);
    backgroundBox.setStrokeWidth(10);
    StackPane.setAlignment(backgroundBox, Pos.TOP_LEFT);
    StackPane.setMargin(backgroundBox, new Insets(50, 0, 0, 50));
    root.getChildren().add(backgroundBox);
  }

  void changeColor() {
    colorPicker.setOnAction(
        event -> changeBackgroundColor(((ColorPicker) event.getSource()).getValue()));
  }

  void changeImage() {
    imageButton.setOnAction(event -> {
      Image image = chooseImage(ImageType.BACKGROUND);
      if (image != null) {
        backgroundBox.setFill(new ImagePattern(image)); // Use the Image object as before
      }
    });
  }

  void endSelection(){
    controller.startNextSelection(ImageType.BACKGROUND, backgroundBox);
  }

  private void changeBackgroundColor(Color color) {
    backgroundBox.setFill(color);
  }
}
