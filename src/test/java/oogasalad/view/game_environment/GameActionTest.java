package oogasalad.view.game_environment;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_element.scene_element_handler.DatabaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.mockito.Mockito.*;

public class GameActionTest extends ApplicationTest {


  private GameController gameController;
  private DatabaseController databaseController;
  private DatabaseHandler databaseHandler;
  private ListView<String> listView;


  @Override
  public void start(Stage stage) {
    gameController = mock(GameController.class);  // Assuming you are using Mockito
    databaseController = mock(DatabaseController.class);
    databaseHandler = mock(DatabaseHandler.class);

    when(databaseController.getNewGameTitles()).thenReturn(
        FXCollections.observableArrayList("NewGame1", "NewGame2"));
    when(gameController.getSavedGameTitles()).thenReturn(
        FXCollections.observableArrayList("SavedGame1", "SavedGame2"));

    listView = new ListView<>();
    listView.setItems(FXCollections.observableArrayList("Game1", "Game2"));
    listView.setCellFactory(lv -> new ListCell<String>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item != null) {
          setText(item);
          setOnMouseClicked(event -> gameController.startGamePlay("data/playable_games/" + item));
        } else {
          setText(null);
          setOnMouseClicked(null);
        }
      }
    });

    Scene scene = new Scene(listView);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testSelectGameAction() {
    clickOn(".list-cell", MouseButton.PRIMARY);

    verify(gameController).startGamePlay("data/playable_games/Game1");
  }
}
