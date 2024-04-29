package oogasalad.view.authoring_environment.panels;

import java.util.List;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import oogasalad.view.api.authoring.Panel;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;

/**
 * ColorPanel is a panel that allows users to select a color for a given shape. The panel contains a
 * ColorPicker that can be used to change the color of a shape.
 *
 * @author Judy He
 */
public class ColorPanel implements Panel {

  private final ShapeProxy shapeProxy;
  private final AnchorPane containerPane;
  private ColorPicker colorPicker;

  /**
   * Constructs a ColorPanel with the specified ShapeProxy and container pane.
   *
   * @param shapeProxy    the proxy object representing the shape to be manipulated
   * @param containerPane the AnchorPane in which the panel's components will be added
   */
  public ColorPanel(ShapeProxy shapeProxy, AnchorPane containerPane) {
    this.containerPane = containerPane;
    this.shapeProxy = shapeProxy;
    createElements();
    handleEvents();
  }

  /**
   * Creates the elements for the ColorPanel. Initializes the ColorPicker and adds it to the
   * container pane.
   */
  @Override
  public void createElements() {
    colorPicker = new ColorPicker();
    colorPicker.setPrefSize(200, 100);
    AnchorPane.setTopAnchor(colorPicker, 50.0);
    AnchorPane.setRightAnchor(colorPicker, 50.0);
    colorPicker.setId("colorPicker");
    containerPane.getChildren().add(colorPicker);
  }

  /**
   * Handles the events for the ColorPanel. Sets up an action event handler for the ColorPicker to
   * change the shape's color.
   */
  @Override
  public void handleEvents() {
    colorPicker.setOnAction(event -> {
      if (shapeProxy.getShape() != null) {
        Color color = ((ColorPicker) event.getSource()).getValue();
        shapeProxy.getShape().setFill(color);
        shapeProxy.getGameObjectAttributesContainer().setColor(
            List.of((int) Math.round(color.getRed() * 255),
                (int) Math.round(color.getGreen() * 255),
                (int) Math.round(color.getBlue() * 255)), shapeProxy.getCurrentMod());
        shapeProxy.getGameObjectAttributesContainer()
            .setImagePath(null, shapeProxy.getCurrentMod());
      }
    });
  }


}
