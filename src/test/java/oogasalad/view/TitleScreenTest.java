package oogasalad.view;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import oogasalad.view.GameScreens.TitleScreen;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import oogasalad.view.Controlling.GameController;

public class TitleScreenTest extends ApplicationTest {

  private GameController mockController;
  private Parent testRoot;

  @Override
  public void start(Stage stage) {
    mockController = Mockito.mock(GameController.class);


    TitleScreen screen = new TitleScreen(mockController);

    testRoot = screen.getRoot();
    stage.setScene(new javafx.scene.Scene(testRoot));
    stage.show();
  }

  @Test
  public void testAuthorButton() {

    clickOn("Author");


    Mockito.verify(mockController).openAuthorEnvironment();
  }

  @AfterEach
  void tearDown() throws Exception {
    FxToolkit.hideStage();
    release(new KeyCode[]{});
    release(new MouseButton[]{});
  }
}
