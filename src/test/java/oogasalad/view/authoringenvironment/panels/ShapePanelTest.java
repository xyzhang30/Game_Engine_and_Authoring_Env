package oogasalad.view.authoringenvironment.panels;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import oogasalad.view.authoring_environment.panels.ShapePanel;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
import oogasalad.view.api.authoring.AuthoringFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class ShapePanelTest extends DukeApplicationTest {

  private ShapePanel shapePanel;
  private ShapeProxy mockShapeProxy;
  private AuthoringProxy mockAuthoringProxy;
  private AuthoringFactory mockAuthoringFactory;
  private AnchorPane containerPane;
  private StackPane canvas;
  private AnchorPane rootPane;

  @BeforeEach
  public void setUp() {
    Platform.runLater(() -> {
      try {
        mockShapeProxy = mock(ShapeProxy.class);
        Shape mockShape = new Rectangle(100, 100,
            Color.BLACK); // Creating a simple shape for testing

        List<Shape> mockTemplates = new ArrayList<>();
        mockTemplates.add(mockShape);
        when(mockShapeProxy.getTemplates()).thenReturn(mockTemplates);

        mockAuthoringProxy = mock(AuthoringProxy.class);
        mockAuthoringFactory = mock(AuthoringFactory.class);
        canvas = new StackPane();
        rootPane = new AnchorPane();
        containerPane = new AnchorPane();

        shapePanel = new ShapePanel(mockAuthoringFactory, mockShapeProxy, mockAuthoringProxy,
            canvas, rootPane, containerPane);

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
  public void testInitialization() {
    Platform.runLater(() -> {
      int expectedCount = mockAuthoringFactory.createGameObjectsConfiguration().size() +
          mockAuthoringFactory.createSurfacesConfiguration().size() +
          mockAuthoringFactory.createCollidablesConfiguration().size() +
          mockAuthoringFactory.createPlayersConfiguration().size() +
          mockShapeProxy.getTemplates().size();
      assertEquals(expectedCount, containerPane.getChildren().size(),
          "All elements should be initialized and added to the containerPane.");
    });
    waitForFxEvents();
  }

  @Test
  public void testTemplateShapeClick() {
    Platform.runLater(() -> {
      Shape templateShape = mockShapeProxy.getTemplates().get(0); // Ensure this isn't empty
      clickOn(templateShape);
      assertTrue(rootPane.getChildren().contains(templateShape),
          "Clicking a template shape should add its clone to the rootPane.");
    });
    waitForFxEvents();
  }

}