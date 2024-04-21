package oogasalad.view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.AnimationManager;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.testfx.assertions.api.Assertions.assertThat;

public class GameControllerTest extends DukeApplicationTest {

  @Test
  public void testGameLifecycle() throws Exception {
//    AnimationManager animationManager = new AnimationManager();
//
//    GameController gameController = new GameController(mockWidthProperty(), mockHeightProperty());
//
//
//    gameController.openMenuScreen();
//
//    String selectedGame = "singlePlayerMiniGolf";
//    gameController.startGamePlay(selectedGame);
//
//    gameController.hitPointScoringObject(0.5, 45);
//    boolean isRoundOver = gameController.runGameAndCheckStatic(1.0);
//    assertThat(isRoundOver).isTrue();
//

  }

  private ReadOnlyDoubleProperty mockWidthProperty() {
    ReadOnlyDoubleWrapper widthWrapper = new ReadOnlyDoubleWrapper(800); // Example width
    return widthWrapper.getReadOnlyProperty();
  }

  private ReadOnlyDoubleProperty mockHeightProperty() {
    ReadOnlyDoubleWrapper heightWrapper = new ReadOnlyDoubleWrapper(600); // Example height
    return heightWrapper.getReadOnlyProperty();
  }
}
