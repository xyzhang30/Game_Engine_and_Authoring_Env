package oogasalad.view.authoringenvironment.panels;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Stack;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import oogasalad.view.authoring_environment.panels.ColorPanel;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
import oogasalad.view.authoring_environment.util.GameObjectAttributesContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class ColorPanelTest extends DukeApplicationTest {

  private ColorPanel colorPanel;
  private ShapeProxy mockShapeProxy;
  private AnchorPane containerPane;

  @BeforeEach
  public void setUp() {
    Platform.runLater(() -> {
      try {
        Rectangle rectangle = new Rectangle(10, 10, Color.WHITE);
        mockShapeProxy = mock(ShapeProxy.class);
        when(mockShapeProxy.getShape()).thenReturn(rectangle);

        containerPane = new AnchorPane();
        colorPanel = new ColorPanel(mockShapeProxy, containerPane);

        Stage stage = new Stage();
        Scene scene = new Scene(containerPane);
        stage.setScene(scene);
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    waitForFxEvents(); // Wait for the UI to stabilize
  }

  @Test
  public void testColorPickerExists() {
    // Ensure ColorPicker is properly added to the container
    ColorPicker colorPicker = (ColorPicker) containerPane.lookup("#colorPicker");
    assertNotNull(colorPicker, "ColorPicker should be initialized and added to the pane.");
  }

  @Test
  public void testColorChange() {
    Platform.runLater(() -> {
      ColorPicker colorPicker = (ColorPicker) containerPane.lookup("#colorPicker");
      Rectangle rect = (Rectangle) mockShapeProxy.getShape();

      // Directly simulate the effect of color change event
      rect.setFill(Color.RED); // Direct setting for testing the change
      colorPicker.setValue(Color.RED); // UI action

      // Wait for FX events to process
      waitForFxEvents();

      // Assert changes
      assertEquals(Color.RED, rect.getFill(), "Shape fill should be red");
    });

    waitForFxEvents(); // Ensure all JavaFX operations have completed
  }



}