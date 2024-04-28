package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import oogasalad.view.api.enums.SceneElementEvent;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.SceneManager;

public class DatabaseHandler {

  private final GameController gameController;
  private final DatabaseController databaseController;
  private final List<String> currentPlayersManager;
  private final SceneManager sceneManager;
  private TextField usernameTextField;
  private TextField passwordField;
  private String avatarUrlField;
  private Map<SceneElementEvent, Consumer<Node>> eventMap;


  public DatabaseHandler(GameController gameController, SceneManager sceneManager,
      DatabaseController databaseController,
      List<String> currentPlayersManager) {
    this.gameController = gameController;
    this.sceneManager = sceneManager;
    this.databaseController = databaseController;
    this.currentPlayersManager = currentPlayersManager;
    createEventMap();
  }

  /**
   * Creates an event handler for the specified node and event type. The handler will invoke the
   * appropriate event function when the event occurs on the given node.
   *
   * @param node  The node to which the event handler will be attached.
   * @param event The event type as a string that specifies the event to handle.
   */
  public void createElementHandler(Node node, String event) {
    Consumer<Node> consumer = eventMap.get(SceneElementEvent.valueOf(event));
    consumer.accept(node);
  }

  private void createEventMap() {
    eventMap = new HashMap<>();
    eventMap.put(SceneElementEvent.LOGIN,
        this::createLoginHandler); //opens the currentplayers screen with the user that has been entered
    eventMap.put(SceneElementEvent.CREATE_USER,
        this::createUserCreatorHandler); //opens the currentplayers scene with the new user and adds new user to database(sends to backend?)
    eventMap.put(SceneElementEvent.USER_TEXT, this::createUsernameHandler); //saves the username
    eventMap.put(SceneElementEvent.PASSWORD_TEXT, this::createPasswordHandler); //saves the password
    eventMap.put(SceneElementEvent.START_LOGIN,
        this::createStartLoginHandler); //goes back to the login/createuser screen
    eventMap.put(SceneElementEvent.LEADERBOARD,
        this::createLeaderboardHandler); //opens the leaderboard scene
    eventMap.put(SceneElementEvent.UPDATE_CURRENT_PLAYERS,
        this::setCurrentPlayers); //current players displayed on listview
    eventMap.put(SceneElementEvent.LEADERBOARD_SCORES,
        this::setLeaderboard); //make sure listview is populated w leaderboard

  }

  private void createLoginHandler(Node node) {
    //avatar should be from a button the same way that we choose the controllable or background images
    System.out.println("createLoginHandler in scene element handler called");
    node.setOnMouseClicked(e ->
    {
          boolean userLoggedIn = databaseController.loginUser(usernameTextField.getText(),
              passwordField.getText()); //true of user logged in
          if (userLoggedIn) {
            currentPlayersManager.add(usernameTextField.getText());
            sceneManager.createCurrentPlayersScene();
          }
    });
    //add another or continue to play (new screen) shows current players like is this good or move on
    //open the currentplayers screen with this player added to it
  }

  private void createUserCreatorHandler(Node node) {

    //this should be from a button the same way that we choose the controllable or background images
    node.setOnMouseClicked(e -> {
      try {
        boolean userCreated = databaseController.canCreateUser(usernameTextField.getText(),
            passwordField.getText(), avatarUrlField);
        System.out.println(userCreated);
        if (userCreated) {
          // user created
          sceneManager.createCurrentPlayersScene();
          currentPlayersManager.add(usernameTextField.getText());
          System.out.println("createLoginHandler: user created");
        } else {
          // user already exists or can't be created
          sceneManager.displayErrorMessage("User already exists or could not be created.");
        }
      } catch (Exception ex) {
        sceneManager.displayErrorMessage("Error: " + ex.getMessage());
      }
    });
    //add the new user to the database
    //open the currentplayers screen with this player added to it
  }

  private void createPasswordHandler(Node node) {
    passwordField = (TextField) node;
    //save the password and add to database with username if not already there

  }

  private void createUsernameHandler(Node node) {
    usernameTextField = (TextField) node;
    //save username and add to database if not already there
  }

  private void createAvatarHandler(Node node) {
    //needs to be integrated
  }

  private void createStartLoginHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.createLoginScene());

  }

  private void createLeaderboardHandler(Node node) {
    node.setOnMouseClicked(e -> sceneManager.createLeaderboardScene());
  }

  private void setCurrentPlayers(Node node) {
    ListView<String> node2 = (ListView<String>) node;
    node2.setItems(FXCollections.observableList(currentPlayersManager));
  }

  private void setLeaderboard(Node node) {
    gameController.getGameName();
    databaseController.leaderboardSet((ListView<String>) node);
  }

//  private void createCurrentPlayersHandler(Node node) {
//    node.setOnMouseClicked(e -> sceneManager.createCurrentPlayersScene());
//  }
}
