package oogasalad.view;

import javafx.stage.Stage;
import oogasalad.view.scene_management.GameWindow;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class WindowTest extends DukeApplicationTest {
  @Test
  void TestWindowConstructors() {
    runAsJFXAction(() -> new GameWindow(new Stage()) );
    runAsJFXAction(() -> new GameWindow());
  }
}
