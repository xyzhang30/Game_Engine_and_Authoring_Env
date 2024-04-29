//package oogasalad.view;
//
//import static org.mockito.Mockito.mock;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javafx.scene.Scene;
//import javafx.scene.layout.StackPane;
//import javafx.scene.shape.Shape;
//import javafx.stage.Stage;
//import oogasalad.view.authoring_environment.authoring_screens.BackgroundSelectionScreen;
//import oogasalad.view.enums.NonStrikeableType;
//import oogasalad.view.controller.AuthoringController;
//import org.junit.jupiter.api.Test;
//import util.DukeApplicationTest;
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
//    Map<Shape, NonStrikeableType> nonStrikeableMap = new HashMap<>();
//    List<Shape> strikeableList = new ArrayList<>();
//    Map<Shape, String> imageMap = new HashMap<>();
//
//    mockController = mock(AuthoringController.class);
//
//    BackgroundSelectionScreen screen = new BackgroundSelectionScreen(
//        mockController, authoringBox, posMap, nonStrikeableMap, strikeableList, imageMap);
//
//    Scene scene = screen.getScene();
//    stage.setScene(scene);
//    stage.show();
//  }
//
//  @Test
//  public void testColorSelectionAndNextButtonPress() {
//    // color selection
//    //clickOn("#colorPicker").write("ff0000"); // Enters red color
//
//    sleep(500);
//    // click on the "Image" button
//    clickOn("#imageButton"); // Click on the "Image" button to open the file chooser dialog
//    //doubleClickOn("Chapel"); // Double-click on the desired file to select it
//    System.out.println(lookup(".file-chooser-dialog .file-list-view").queryAll());
//    //clickOn("Open");
//
//    // click on the "Next" button
//    //clickOn("#Next");
//
////    verify(mockController, times(1)).startNextSelection(
////        any(ImageType.class), any(StackPane.class), anyMap(), anyMap(), any(List.class), anyMap());
//  }
//
//
//}
//
