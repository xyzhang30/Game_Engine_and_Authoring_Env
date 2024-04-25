//package oogasalad.view;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.mock;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javafx.scene.input.MouseButton;
//import javafx.scene.layout.StackPane;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.shape.Shape;
//import javafx.stage.Stage;
//import oogasalad.view.enums.NonStrikeableType;
//import oogasalad.view.authoring_environment.authoring_screens.StrikeableElementSelectionScreen;
//import oogasalad.view.controller.AuthoringController;
//import org.junit.jupiter.api.Test;
//import org.testfx.framework.junit5.ApplicationTest;
//import org.testfx.util.WaitForAsyncUtils;
//
//public class StrikeableElementSelectionTest extends ApplicationTest {
//
//  private StrikeableElementSelectionScreen screen;
//  private StackPane authoringBox;
//  private Rectangle draggableRectangle;
//  private AuthoringController mockController;
//
//  @Override
//  public void start(Stage stage) {
//    mockController = mock(AuthoringController.class);
//
//    authoringBox = new StackPane();
//    Map<Shape, List<Double>> posMap = new HashMap<>();
//    Map<Shape, NonStrikeableType> nonStrikeableMap = new HashMap<>();
//    List<Shape> strikeableList = new ArrayList<>();
//    Map<Shape, String> imageMap = new HashMap<>();
//
//    this.screen = new StrikeableElementSelectionScreen(mockController, authoringBox, posMap,
//        nonStrikeableMap, strikeableList, imageMap);
//
//    stage.setScene(screen.getScene());
//    stage.show();
//  }
//
//  @Test
//  public void testAddingShapeToAuthoringBoxByDragging() {
//    WaitForAsyncUtils.waitForFxEvents();
//
//    interact(() -> {
//      screen.createDraggableShapeTemplates();
//    });
//
//    WaitForAsyncUtils.waitForFxEvents();
//
//    draggableRectangle = lookup("#draggableRectangle").query();
//    assertNotNull(draggableRectangle, "Draggable rectangle was not found.");
//
//    double dragStartX =
//        draggableRectangle.localToScreen(draggableRectangle.getBoundsInLocal()).getMinX()
//            + draggableRectangle.getWidth() / 2;
//    double dragStartY =
//        draggableRectangle.localToScreen(draggableRectangle.getBoundsInLocal()).getMinY()
//            + draggableRectangle.getHeight() / 2;
//
//    double dropX = authoringBox.localToScreen(authoringBox.getBoundsInLocal()).getMinX()
//        + authoringBox.getWidth() / 2;
//    double dropY = authoringBox.localToScreen(authoringBox.getBoundsInLocal()).getMinY()
//        + authoringBox.getHeight() / 2;
//
//    System.out.println("Drag start point: (" + dragStartX + ", " + dragStartY + ")");
//    System.out.println("Drop point: (" + dropX + ", " + dropY + ")");
//
//    moveTo(dragStartX, dragStartY);
//    press(MouseButton.PRIMARY);
//    moveTo(dropX, dropY);
//    release(MouseButton.PRIMARY);
//
//    WaitForAsyncUtils.waitForFxEvents();
//
//    System.out.println(
//        "Rectangle dropped: " + authoringBox.getChildren().contains(draggableRectangle));
//
//    // Verify the rectangle is within the authoringBox
////    interact(() -> {
////      assertTrue(authoringBox.getChildren().contains(draggableRectangle), "The draggable rectangle should be within the authoring box.");
////    });
//  }
//
//
//}
