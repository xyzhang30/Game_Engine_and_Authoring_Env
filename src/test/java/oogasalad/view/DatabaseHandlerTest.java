package oogasalad.view;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_element.scene_element_handler.DatabaseHandler;
import oogasalad.view.scene_management.scene_managers.SceneManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import static org.mockito.Mockito.*;

public class DatabaseHandlerTest {

  @Mock
  private GameController gameController;
  @Mock
  private SceneManager sceneManager;
  @Mock
  private DatabaseController databaseController;
  @Mock
  private Pane mockNode;

  private DatabaseHandler databaseHandler;
  private TextField usernameTextField;
  private TextField passwordField;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    ArrayList<String> currentPlayersList = new ArrayList<>();
    databaseHandler = new DatabaseHandler(gameController, sceneManager, databaseController, currentPlayersList);
    usernameTextField = new TextField("testUser");
    passwordField = new TextField("testPass");
    databaseHandler.createUsernameHandler(usernameTextField);
    databaseHandler.createPasswordHandler(passwordField);
  }

}
