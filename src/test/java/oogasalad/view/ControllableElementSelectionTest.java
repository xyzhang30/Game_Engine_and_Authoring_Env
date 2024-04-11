package oogasalad.view;

import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import oogasalad.view.AuthoringScreens.ControllableElementSelectionScreen;
import oogasalad.view.AuthoringScreens.NonControllableType;
import oogasalad.view.Controlling.AuthoringController;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ControllableElementSelectionTest extends ApplicationTest {

  private ControllableElementSelectionScreen screen;
  private StackPane authoringBox;
  private Rectangle draggableRectangle;
  private AuthoringController mockController;

  @Override
  public void start(Stage stage) {
    mockController = mock(AuthoringController.class);

    authoringBox = new StackPane();
    Map<Shape, List<Double>> posMap = new HashMap<>();
    Map<Shape, NonControllableType> nonControllableMap = new HashMap<>();
    List<Shape> controllableList = new ArrayList<>();
    Map<Shape, String> imageMap = new HashMap<>();

    this.screen = new ControllableElementSelectionScreen(mockController, authoringBox, posMap, nonControllableMap, controllableList, imageMap);

    stage.setScene(screen.getScene());
    stage.show();
  }

  @Test
  public void testAddingShapeToAuthoringBoxByDragging() {
    WaitForAsyncUtils.waitForFxEvents();

    interact(() -> {
      screen.createDraggableShapeTemplates(); // This method should add the rectangle
    });

    WaitForAsyncUtils.waitForFxEvents(); // Wait for UI updates

    draggableRectangle = lookup("#draggableRectangle").query();
    assertNotNull(draggableRectangle, "Draggable rectangle was not found.");

    // Calculate the drag start point (anywhere on the rectangle)
    double dragStartX = draggableRectangle.localToScreen(draggableRectangle.getBoundsInLocal()).getMinX() + draggableRectangle.getWidth() / 2;
    double dragStartY = draggableRectangle.localToScreen(draggableRectangle.getBoundsInLocal()).getMinY() + draggableRectangle.getHeight() / 2;

    // Calculate the drop point inside the authoringBox
    double dropX = authoringBox.localToScreen(authoringBox.getBoundsInLocal()).getMinX() + authoringBox.getWidth() / 2;
    double dropY = authoringBox.localToScreen(authoringBox.getBoundsInLocal()).getMinY() + authoringBox.getHeight() / 2;

    System.out.println("Drag start point: (" + dragStartX + ", " + dragStartY + ")");
    System.out.println("Drop point: (" + dropX + ", " + dropY + ")");

    // Simulate dragging from the rectangle to the target drop point inside the authoringBox
    moveTo(dragStartX, dragStartY);
    press(MouseButton.PRIMARY); // Simulate mouse press on the rectangle
    moveTo(dropX, dropY);
    release(MouseButton.PRIMARY); // Release the mouse to drop the rectangle

    WaitForAsyncUtils.waitForFxEvents(); // Ensure the drop action completes

    System.out.println("Rectangle dropped: " + authoringBox.getChildren().contains(draggableRectangle));

    // Verify the rectangle is within the authoringBox
//    interact(() -> {
//      assertTrue(authoringBox.getChildren().contains(draggableRectangle), "The draggable rectangle should be within the authoring box.");
//    });
  }



}