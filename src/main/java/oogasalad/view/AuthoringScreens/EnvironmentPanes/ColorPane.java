package oogasalad.view.AuthoringScreens.EnvironmentPanes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;

public class ColorPane extends VBox {
  private ColorPicker colorPicker;
  private Button imageButton;

  public ColorPane() {
    this.setAlignment(Pos.TOP_RIGHT);
    this.setPadding(new Insets(50, 50, 0, 0));
    this.setSpacing(10);

    createColorPicker();
    createImageButton();
  }

  private void createColorPicker() {
    colorPicker = new ColorPicker();
    this.getChildren().add(colorPicker);
  }

  private void createImageButton() {
    imageButton = new Button("Image");
    this.getChildren().add(imageButton);
  }

  public ColorPicker getColorPicker() {
    return colorPicker;
  }

  public Button getImageButton() {
    return imageButton;
  }

}
