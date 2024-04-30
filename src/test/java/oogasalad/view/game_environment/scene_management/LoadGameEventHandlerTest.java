package oogasalad.view.game_environment.scene_management;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_element.scene_element_handler.DatabaseHandler;
import oogasalad.view.scene_management.scene_element.scene_element_handler.LoadGameEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import util.DukeApplicationTest;

/**
 * Unit tests for the LoadGameEventHandler class.
 */
public class LoadGameEventHandlerTest extends DukeApplicationTest {

  private LoadGameEventHandler loadGameEventHandler;
  private GameController gameController;
  private DatabaseController databaseController;
  private DatabaseHandler databaseHandler;

  @BeforeEach
  public void setup() {
    gameController = mock(GameController.class);
    databaseController = mock(DatabaseController.class);
    databaseHandler = mock(DatabaseHandler.class);

    loadGameEventHandler = new LoadGameEventHandler(gameController, databaseController, databaseHandler);

    when(databaseController.getNewGameTitles()).thenReturn(FXCollections.observableArrayList("NewGame1", "NewGame2"));
    when(gameController.getSavedGameTitles()).thenReturn(FXCollections.observableArrayList("SavedGame1", "SavedGame2"));
  }

  @Test
  public void testCreateElementHandlerForNewGame() {
    ListView<String> listView = new ListView<>();
    Platform.runLater(() -> {
      loadGameEventHandler.createElementHandler(listView, "START_NEW_GAME");
    });
    WaitForAsyncUtils.waitForFxEvents();

    assertNotNull(listView.getItems());
    assert(!listView.getItems().isEmpty());
  }

  @Test
  public void testCreateElementHandlerForSavedGame() {
    ListView<String> listView = new ListView<>();
    Platform.runLater(() -> {
      loadGameEventHandler.createElementHandler(listView, "START_SAVED_GAME");
    });
    WaitForAsyncUtils.waitForFxEvents();

    assertNotNull(listView.getItems());
    assert(!listView.getItems().isEmpty());
  }

}
