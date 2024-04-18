//package oogasalad.view;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.mock;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//import javafx.application.Platform;
//import javafx.scene.Scene;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//import javafx.stage.Window;
//import oogasalad.view.controller.AuthoringController;
//import oogasalad.view.controller.GameController;
//import oogasalad.view.game_environment.TitleScreen;
//import org.junit.jupiter.api.Test;
//import util.DukeApplicationTest;
//
//public class IntegratedAuthoringTests extends DukeApplicationTest {
//
//  private GameController gameController;
//  private AuthoringController mockAuthoringController;
//  private Stage mainStage;
//
//  @Override
//  public void start(Stage stage) {
//    this.mainStage = stage;
//    gameController = new GameController(stage.widthProperty(),stage.heightProperty());
//    TitleScreen titleScreen = new TitleScreen(gameController);
//    stage.setScene(new Scene(titleScreen.getRoot()));
//    stage.show();
//  }
//
//  @Test
//  public void testFlowFromTitleToBackgroundSelection() throws InterruptedException {
//    clickOn("Author");
//
//    mockAuthoringController = getMockAuthoringControllerInjectedIntoBackgroundScreen();
//
//    setValue(lookup(".color-picker").query(), Color.BLUE);
//    clickOn("Next");
//  }
//
//  private boolean waitForWindowWithTitle(String title) throws InterruptedException {
//    final CountDownLatch latch = new CountDownLatch(1);
//    Platform.runLater(latch::countDown);
//    assertTrue(latch.await(2, TimeUnit.SECONDS), "Timeout waiting for the screen to load.");
//
//    for (Window window : Window.getWindows()) {
//      if (window instanceof Stage stage) {
//        if (stage.getTitle() != null && stage.getTitle().equals(title) && stage.isShowing()) {
//          return true;
//        }
//      }
//    }
//    return false;
//  }
//
//  private AuthoringController getMockAuthoringControllerInjectedIntoBackgroundScreen() {
//    return mock(AuthoringController.class);
//  }
//}