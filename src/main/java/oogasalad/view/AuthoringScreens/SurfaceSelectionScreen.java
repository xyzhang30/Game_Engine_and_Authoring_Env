package oogasalad.view.AuthoringScreens;

import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ColorPicker;

public class SurfaceSelectionScreen extends AuthoringScreen {

  private final int backgroundWidth = 980;
  private final int backgroundHeight = 980;
  private Rectangle backgroundBox;

  public SurfaceSelectionScreen() {
    createScene();
  }

  public void createScene() {
    root = new StackPane();
    createBackgroundBox();
    createOptionBox();
    scene = new Scene(root, screenWidth, screenHeight);
    scene.setFill(Color.PURPLE);
  }

  public Scene getScene() {
    return scene;
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

  private void changeBackgroundColor(Color color){
    backgroundBox.setFill(color);
  }
}
