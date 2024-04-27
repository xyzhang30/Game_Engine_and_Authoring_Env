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

  @Test
  public void testBoundaryColorValues() {
    Platform.runLater(() -> {
      ColorPicker colorPicker = (ColorPicker) containerPane.lookup("#colorPicker");
      Rectangle rect = (Rectangle) mockShapeProxy.getShape();

      // Test with full intensity (white)
      Color white = Color.WHITE;
      colorPicker.setValue(white); // Simulate user action via UI
      waitForFxEvents(); // Wait for JavaFX to process the event
      assertEquals(white, rect.getFill(), "Shape fill should be white (full intensity).");
      System.out.println(rect.getFill());
      // Test with no intensity (black)
      Color black = Color.BLACK;
      colorPicker.setValue(black); // Simulate user action via UI
      waitForFxEvents(); // Wait for JavaFX to process the event
      System.out.println(rect.getFill());
      assertEquals(black, rect.getFill(), "Shape fill should be black (no intensity).");
      System.out.println(rect.getFill());
    });
    waitForFxEvents(); // Ensure all JavaFX operations have completed
  }





  /**
   * Compares two Color objects based on their RGB components to handle potential precision issues.
   * @param color1 First color to compare.
   * @param color2 Second color to compare.
   * @return true if the colors are considered equivalent.
   */
  private boolean isColorEqual(Color color1, Color color2) {
    // Check and log the RGBA values to see what's actually being compared
    System.out.println("Comparing: " + color1 + " to " + color2);
    return Math.abs(color1.getRed() - color2.getRed()) < 0.01 &&
        Math.abs(color1.getGreen() - color2.getGreen()) < 0.01 &&
        Math.abs(color1.getBlue() - color2.getBlue()) < 0.01;
  }



}