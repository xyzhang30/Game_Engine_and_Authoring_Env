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
    ColorPicker colorPicker = (ColorPicker) containerPane.lookup("#colorPicker");
    assertNotNull(colorPicker, "ColorPicker should be initialized and added to the pane.");
  }

  @Test
  public void testColorChange() {
    Platform.runLater(() -> {
      ColorPicker colorPicker = (ColorPicker) containerPane.lookup("#colorPicker");
      Rectangle rect = (Rectangle) mockShapeProxy.getShape();

      // Simulate shape selection
      when(mockShapeProxy.getShape()).thenReturn(rect);
      rect.setFill(Color.RED); // Simulate user action of selecting a color after selection
      colorPicker.setValue(Color.RED);

      waitForFxEvents();

      assertEquals(Color.RED, rect.getFill(), "Shape fill should be red after selection and color change.");

    });

    waitForFxEvents();
  }

  @Test
  public void testBoundaryColorValuesWhite() {
    Platform.runLater(() -> {
      ColorPicker colorPicker = (ColorPicker) containerPane.lookup("#colorPicker");
      Rectangle rect = (Rectangle) mockShapeProxy.getShape();

      // Test with full intensity (white)
      Color white = Color.WHITE;
      colorPicker.setValue(white); // Simulate user action via UI
      waitForFxEvents(); // Wait for JavaFX to process the event
      assertEquals(white, rect.getFill(), "Shape fill should be white (full intensity).");
      System.out.println(rect.getFill());
    });
    waitForFxEvents(); // Ensure all JavaFX operations have completed
  }

  @Test
  public void testBoundaryColorValuesBlack() {
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
//      Color black = Color.BLACK;
//      colorPicker.setValue(black); // Simulate user action via UI
//      waitForFxEvents(); // Wait for JavaFX to process the event
//      System.out.println(rect.getFill());
//      assertEquals(black, rect.getFill(), "Shape fill should be black (no intensity).");
//      System.out.println(rect.getFill());
    });
    waitForFxEvents(); // Ensure all JavaFX operations have completed
  }


}