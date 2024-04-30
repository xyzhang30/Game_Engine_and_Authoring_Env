package oogasalad.view.game_environment.scene_management;

import java.sql.SQLException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oogasalad.view.api.exception.IncorrectPasswordException;
import oogasalad.view.api.exception.UserNotFoundException;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_element.scene_element_handler.DatabaseHandler;
import oogasalad.view.scene_management.scene_element.scene_element_handler.GamePlayManagementHandler;
import oogasalad.view.scene_management.scene_managers.SceneManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import util.DukeApplicationTest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.util.ArrayList;


/**
 * Test class for DatabaseHandler using DukeApplicationTest.
 *
 * @author  Doga Ozmen
 */
public class DatabaseHandlerTest extends DukeApplicationTest {

  private SceneManager sceneManager;
  private GameController gameController;
  private DatabaseController databaseController;
  private DatabaseHandler databaseHandler;

  @Override
  public void start(Stage stage) {
    gameController = mock(GameController.class);
    databaseController = mock(DatabaseController.class);
    GamePlayManagementHandler gamePlayManagementHandler = new GamePlayManagementHandler(
        gameController, sceneManager);
    sceneManager = new SceneManager(gameController, databaseController, 800, 600, new ArrayList<>());
    Scene scene = new Scene(sceneManager.getRoot(), 800, 600);
    stage.setScene(scene);
    stage.show();
  }



  @BeforeEach
  public void resetScene() {
    Stage stage = (Stage) sceneManager.getRoot().getScene().getWindow();
    Pane newRoot = new Pane();
    Scene newScene = new Scene(newRoot, 800, 600);
    stage.setScene(newScene);
    // Reinitialize your SceneManager or other components that depend on the root
    Scene scene2 = new Scene(sceneManager.getRoot(), 800, 600);
    stage.setScene(scene2);
    stage.show();
  }


  @Test
  public void testLoginHandler()
      throws UserNotFoundException, IncorrectPasswordException, SQLException {
    Button loginButton = new Button("Login");
    sceneManager.getRoot().getChildren().add(loginButton);
    databaseHandler.createElementHandler(loginButton, "LOGIN");

    when(databaseController.loginUser(anyString(), anyString())).thenReturn(true);

    clickOn(loginButton);

    verify(databaseController).loginUser(anyString(), anyString());
    verify(gameController, never()).pauseGame();
  }

  @Test
  public void testCreateUserHandler() {
    Button createUserButton = new Button("Create User");
    sceneManager.getRoot().getChildren().add(createUserButton);
    databaseHandler.createElementHandler(createUserButton, "CREATE_USER");

    clickOn(createUserButton);

    verify(databaseController).canCreateUser(anyString(), anyString(), anyString());
  }

}
