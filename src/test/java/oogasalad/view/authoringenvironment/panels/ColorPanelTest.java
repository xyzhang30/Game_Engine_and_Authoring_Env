package oogasalad.view.authoringenvironment.panels;

import java.util.List;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
  public void setUp() throws Exception {
    // Ensure JavaFX thread is properly initialized
    interact(() -> {
      containerPane = new AnchorPane();
      mockShapeProxy = new ShapeProxy() {
        @Override
        public javafx.scene.shape.Shape getShape() {
          return new javafx.scene.shape.Rectangle();
        }
        @Override
        public GameObjectAttributesContainer getGameObjectAttributesContainer() {
          return new GameObjectAttributesContainer();
        }
      };
      // Create the ColorPanel after initializing the containerPane
      colorPanel = new ColorPanel(mockShapeProxy, containerPane);

      // Setup stage and scene to host the test elements
      Stage stage = new Stage();
      stage.setScene(new javafx.scene.Scene(containerPane, 400, 400));
      stage.show();
    });
  }

  @Test
  public void testColorPickerExists() {
    // Ensure ColorPicker is properly added to the container
    ColorPicker colorPicker = (ColorPicker) containerPane.lookup("#colorPicker");
    assertNotNull(colorPicker, "ColorPicker should be initialized and added to the pane.");
  }

}