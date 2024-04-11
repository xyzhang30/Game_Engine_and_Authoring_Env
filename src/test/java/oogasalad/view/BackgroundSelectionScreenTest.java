//package oogasalad.view;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javafx.scene.Scene;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Shape;
//import javafx.stage.Stage;
//import oogasalad.view.AuthoringScreens.NonControllableType;
//import org.junit.jupiter.api.Test;
//import oogasalad.view.AuthoringScreens.BackgroundSelectionScreen;
//import oogasalad.view.Controlling.AuthoringController;
//import util.DukeApplicationTest;
//
//import static org.mockito.Mockito.*;
//
//public class BackgroundSelectionScreenTest extends DukeApplicationTest {
//
//  private AuthoringController mockController;
//
//
//  @Override
//  public void start(Stage stage) {
//    StackPane authoringBox = new StackPane();
//    Map<Shape, List<Double>> posMap = new HashMap<>();
//    Map<Shape, NonControllableType> nonControllableMap = new HashMap<>();
//    List<Shape> controllableList = new ArrayList<>();
//
//    mockController = mock(AuthoringController.class);
//
//    BackgroundSelectionScreen screen = new BackgroundSelectionScreen(
//        mockController, authoringBox, posMap, nonControllableMap, controllableList);
//
//    Scene scene = screen.getScene();
//    stage.setScene(scene);
//    stage.show();
//  }
//
//  @Test
//  public void testColorSelectionAndNextButtonPress() {
//    setValue(lookup(".color-picker").query(), Color.BLUE);
//
//    clickOn("Next");
//
//    verify(mockController, times(1)).startNextSelection(any(), any(), any(), any(), any());
//  }
//}
