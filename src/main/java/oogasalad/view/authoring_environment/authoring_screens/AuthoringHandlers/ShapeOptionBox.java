package oogasalad.view.authoring_environment.authoring_screens.AuthoringHandlers;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import oogasalad.view.authoring_environment.authoring_screens.ImageType;

public class ShapeOptionBox {

  private VBox container;
  private Shape selectedShape;
  private ColorPicker colorPicker;
  private Button imageButton;
  private Slider opacitySlider;
  private ImageType imageType;

  public ShapeOptionBox() {
    createComponents();
  }

  public VBox getContainer() {
    return this.container;
  }

  private void createComponents() {
    container = new VBox(10);
    container.setPadding(new Insets(5));

    colorPicker = new ColorPicker();
    colorPicker.setOnAction(event -> {
      if (selectedShape != null) {
        selectedShape.setFill(colorPicker.getValue());
      }
    });

    imageButton = new Button("Select Image");
    imageButton.setOnAction(event -> {
      ImageHandler.applyImageToShape(selectedShape, imageType);
    });

    opacitySlider = new Slider(0, 1, 1);
    opacitySlider.setShowTickLabels(true);
    opacitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (selectedShape != null) {
        selectedShape.setOpacity(newValue.doubleValue());
      }
    });

    container.getChildren().addAll(colorPicker, imageButton, opacitySlider);
  }

  public void setSelectedShape(Shape shape) {
    this.selectedShape = shape;
  }

  public void setImageType(ImageType imageType) {
    this.imageType = imageType;
  }
}
