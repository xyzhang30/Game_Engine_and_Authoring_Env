package oogasalad.view.authoring_environment.panels;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;

public class ColorPanel implements Panel {
  private final ShapeProxy shapeProxy;
  private final AnchorPane rootPane;
  private ColorPicker colorPicker;
  private List<Consumer<Shape>> observers;

  public ColorPanel(ShapeProxy shapeProxy, AnchorPane rootPane) {
    this.rootPane = rootPane;
    this.shapeProxy = shapeProxy;
    createElements();
    handleEvents();
  }
  @Override
  public void createElements() {
    colorPicker = new ColorPicker();
    colorPicker.setPrefSize(200, 100);
    AnchorPane.setTopAnchor(colorPicker, 50.0);
    AnchorPane.setRightAnchor(colorPicker, 50.0);
    colorPicker.setId("colorPicker");
    rootPane.getChildren().add(colorPicker);
  }

  @Override
  public void handleEvents() {
    colorPicker.setOnAction(event -> {
      if (shapeProxy.getShape() != null) {
        shapeProxy.getShape().setFill(((ColorPicker) event.getSource()).getValue());
      }
    });
  }


}
