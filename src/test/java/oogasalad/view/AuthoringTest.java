//package oogasalad.view;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//import javafx.application.Platform;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import javafx.stage.Window;
//import oogasalad.view.controller.GameController;
//import oogasalad.view.game_environment.TitleScreen;
//import org.junit.jupiter.api.Test;
//import org.testfx.framework.junit5.ApplicationTest;
//
//public class AuthoringTest extends ApplicationTest {
//
//  private GameController controller;
//  private Parent testRoot;
//
//  @Override
//  public void start(Stage stage) {
//    controller = new GameController();
//    TitleScreen screen = new TitleScreen(controller);
//    testRoot = screen.getRoot();
//    stage.setScene(new Scene(testRoot));
//    stage.show();
//  }
//
//  @Test
//  public void testAuthorButtonOpensAuthoringEnvironment() throws InterruptedException {
//    clickOn("Author");
//
//    final CountDownLatch latch = new CountDownLatch(1);
//    Platform.runLater(latch::countDown);
//    assertTrue(latch.await(2, TimeUnit.SECONDS));
//
//    boolean newWindowOpened = false;
//    for (Window window : Window.getWindows()) {
//      if (window instanceof Stage && window.isShowing()) {
//        newWindowOpened = true;
//        break;
//      }
//    }
//
//    assertTrue(newWindowOpened, "The authoring environment was not opened.");
//  }
//}
