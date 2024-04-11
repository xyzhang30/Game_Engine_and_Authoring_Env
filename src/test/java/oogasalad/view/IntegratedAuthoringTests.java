package oogasalad.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import oogasalad.view.Controlling.AuthoringController;
import oogasalad.view.Controlling.GameController;
import oogasalad.view.GameScreens.TitleScreen;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class IntegratedAuthoringTests extends DukeApplicationTest {

  private GameController gameController;
  private AuthoringController mockAuthoringController;
  private Stage mainStage;

  @Override
  public void start(Stage stage) {
    this.mainStage = stage;
    gameController = new GameController();
    TitleScreen titleScreen = new TitleScreen(gameController);
    stage.setScene(new Scene(titleScreen.getRoot()));
    stage.show();
  }

  @Test
  public void testFlowFromTitleToBackgroundSelection() throws InterruptedException {
    clickOn("Author");


    mockAuthoringController = getMockAuthoringControllerInjectedIntoBackgroundScreen();

    setValue(lookup(".color-picker").query(), Color.BLUE);
    clickOn("Next");
  }

  private boolean waitForWindowWithTitle(String title) throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    Platform.runLater(latch::countDown);
    assertTrue(latch.await(2, TimeUnit.SECONDS), "Timeout waiting for the screen to load.");

    for (Window window : Window.getWindows()) {
      if (window instanceof Stage) {
        Stage stage = (Stage) window;
        if (stage.getTitle() != null && stage.getTitle().equals(title) && stage.isShowing()) {
          return true;
        }
      }
    }
    return false;
  }

  private AuthoringController getMockAuthoringControllerInjectedIntoBackgroundScreen() {
     return mock(AuthoringController.class);
  }
}