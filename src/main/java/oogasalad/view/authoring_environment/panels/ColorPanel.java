package oogasalad.view.authoring_environment.panels;

import java.util.List;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ColorPanel implements Panel {

  private final ShapeProxy shapeProxy;
  private final AnchorPane containerPane;
  private ColorPicker colorPicker;

  public ColorPanel(ShapeProxy shapeProxy, AnchorPane containerPane) {
    this.containerPane = containerPane;
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
    containerPane.getChildren().add(colorPicker);
  }

  @Override
  public void handleEvents() {
    colorPicker.setOnAction(event -> {
      if (shapeProxy.getShape() != null) {
        Color color = ((ColorPicker) event.getSource()).getValue();
        shapeProxy.getShape().setFill(color);
        shapeProxy.getGameObjectAttributesContainer().setColor(List.of((int) color.getRed()*255, (int)color.getGreen()*255, (int)color.getBlue()*255));
        shapeProxy.getGameObjectAttributesContainer().setImagePath(null);
      }
    });
  }


}
