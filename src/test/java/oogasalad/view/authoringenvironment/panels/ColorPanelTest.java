package oogasalad.view.authoringenvironment.panels;

import java.util.List;
import javafx.application.Platform;
import javafx.scene.Scene;
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
  public void setUp() {
    // Mock the ShapeProxy to simulate shape interaction
    mockShapeProxy = new ShapeProxy() {
      // Mock implementation for test purposes, to track color changes
      private Color color;

      @Override
      public javafx.scene.shape.Shape getShape() {
        return new javafx.scene.shape.Rectangle() {{
          setFill(Color.WHITE); // Default color
        }};
      }

      @Override
      public GameObjectAttributesContainer getGameObjectAttributesContainer() {
        return new GameObjectAttributesContainer() {
          @Override
          public void setColor(List<Integer> colorComponents) {
            // Convert to Color and set
            color = new Color(colorComponents.get(0) / 255.0,
                colorComponents.get(1) / 255.0,
                colorComponents.get(2) / 255.0, 1.0);
          }
        };
      }
    };
  }
}