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

public class SurfaceSelectionScreen extends AuthoringScreen{


  private final int backgroundWidth = 980;
  private final int backgroundHeight = 980;


  public SurfaceSelectionScreen(){
    createScene();
  }

  public void createScene(){
    root = new StackPane();
    createBackgroundBox();
    createBackgroundOptionBox();
    scene = new Scene(root, screenWidth, screenHeight);
    scene.setFill(Color.PURPLE);
  }

  public Scene getScene(){
    return scene;
  }

  private void createBackgroundBox(){
    Rectangle backgroundBox = new Rectangle(backgroundWidth, backgroundHeight);
    backgroundBox.setStroke(Color.BLACK);
    backgroundBox.setStrokeWidth(10);
    StackPane.setAlignment(backgroundBox, Pos.TOP_LEFT);
    StackPane.setMargin(backgroundBox, new Insets(50, 0, 0, 50));
    root.getChildren().add(backgroundBox);
  }

  private void createBackgroundOptionBox(){
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setPrefSize(200, 100);
    StackPane.setAlignment(colorPicker, Pos.TOP_RIGHT);
    StackPane.setMargin(colorPicker, new Insets(50, 50, 0, 0));
    root.getChildren().addAll(colorPicker);


    Button imageButton = new Button("Image");
    imageButton.setPrefSize(200, 100);
    StackPane.setAlignment(imageButton, Pos.TOP_RIGHT);
    StackPane.setMargin(imageButton, new Insets(160, 50, 0, 0));
    root.getChildren().add(imageButton);
  }

}
