package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.view.Warning;
import oogasalad.view.api.enums.SceneElementEvent;
import oogasalad.view.api.exception.IncorrectPasswordException;
import oogasalad.view.api.exception.UserNotFoundException;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.SceneManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseHandler {

  private final GameController gameController;
  private final DatabaseController databaseController;
  private final List<String> currentPlayersManager;
  private final SceneManager sceneManager;
  private TextField usernameTextField;
  private TextField passwordField;
  private ListView<String> playerPermissions;
  private ComboBox<String> publicComboBox;
  private String avatarUrlField;
  private String currentGame;
  private Map<SceneElementEvent, Consumer<Node>> eventMap;
  private Map<String, Boolean> playerPermissionMap;
  private static final Logger LOGGER = LogManager.getLogger(DatabaseHandler.class);


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
    eventMap.put(SceneElementEvent.PLAYER_PERMISSIONS, this::setUpPlayerPermissions);
    eventMap.put(SceneElementEvent.SUBMIT_PERMISSIONS, this::createFinishHandler);
    eventMap.put(SceneElementEvent.SET_PUBLIC, this::createPublicVsPrivateHandler);
  }

  public void createLoginHandler(Node node) {
    System.out.println("createLoginHandler in scene element handler called");
    node.setOnMouseClicked(e -> {
      try {
        boolean userLoggedIn = databaseController.loginUser(usernameTextField.getText(), passwordField.getText());
        if (userLoggedIn) {
          currentPlayersManager.add(usernameTextField.getText());
          sceneManager.createCurrentPlayersScene();
        }
      } catch (UserNotFoundException | IncorrectPasswordException ex) {
        LOGGER.error(ex.getMessage());
        Warning warning = new Warning();
        warning.showAlert(this.sceneManager.getScene(), AlertType.ERROR, "Login Error", null, ex.getMessage());
      } catch (Exception ex) {
        LOGGER.error("An unexpected error occurred during login: " + ex.getMessage());
        // Handle other unexpected errors
        Warning warning = new Warning();
        warning.showAlert(this.sceneManager.getScene(), AlertType.ERROR, "Login Error", null, "An unexpected error occurred during login.");
      }
    });
  }

  /**
   * Shows an alert dialog to the user with specified title and message.
   */
  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
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

  public void createPasswordHandler(Node node) {
    passwordField = (TextField) node;
    //save the password and add to database with username if not already there

  }

  public void createUsernameHandler(Node node) {
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

  public void setLeaderboard(Node node) {
    gameController.getGameName();
    databaseController.leaderboardSet((ListView<String>) node);
  }

  private void setUpPlayerPermissions(Node node) {
    playerPermissions = (ListView<String>) node;
    playerPermissionMap = gameController.getPlayerPermissions(currentGame);
    Map<String, CheckBox> playerCheckBoxMap = new HashMap<>(); // Map to store player names to their corresponding checkboxes

    ObservableList<String> playerNames = FXCollections.observableArrayList(playerPermissionMap.keySet());
    playerPermissions.setItems(playerNames);
    playerPermissions.setCellFactory(lv -> new ListCell<String>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
          setGraphic(null);
        } else {
          CheckBox checkBox = new CheckBox(); // Create a new checkbox for each cell
          boolean permission = playerPermissionMap.get(item);
          checkBox.setSelected(permission);

          setText(item);
          // Update the map when the checkbox is toggled
          checkBox.setOnAction(event -> {
            playerPermissionMap.put(item, checkBox.isSelected());
          });

          // Add the checkbox to the map with player's name as the key
          playerCheckBoxMap.put(item, checkBox);

          // Set only the checkbox as the graphic, omitting the player name
          setGraphic(checkBox);
        }
      }
    });
  }


  private void createFinishHandler(Node node) {
    node.setOnMouseClicked(e -> {
      List<String> checkedPlayers = new ArrayList<>();
      List<String> uncheckedPlayers = new ArrayList<>();

      for (String item : playerPermissions.getItems()) {
        if (playerPermissionMap.get(item)) {
              checkedPlayers.add(item);
            } else {
              uncheckedPlayers.add(item);
            }
          }



      if (publicComboBox.getSelectionModel().getSelectedItem().equals("Public")) {
        databaseController.setPublicPrivate(currentGame, true);
      } else {
        databaseController.setPublicPrivate(currentGame, false);
      }
      databaseController.writePlayerPermissions(currentGame, checkedPlayers, uncheckedPlayers);
      sceneManager.createMenuScene();
    });
  }

  private void createPublicVsPrivateHandler(Node node) {
    publicComboBox = (ComboBox<String>) node;
    ObservableList<String> publicPrivateList = FXCollections.observableArrayList("Public",
        "Private");
    publicComboBox.setItems(publicPrivateList);
    if (databaseController.isPublic(currentGame)) {
      publicComboBox.getSelectionModel().select("Public");
    } else {
      publicComboBox.getSelectionModel().select("Private");
    }
  }

  public void setGame(String gameTitle) {
    currentGame = gameTitle;
  }

//  private void createCurrentPlayersHandler(Node node) {
//    node.setOnMouseClicked(e -> sceneManager.createCurrentPlayersScene());
//  }
}
