package oogasalad.view.scene_management.scene_element.scene_element_handler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.view.Warning;
import oogasalad.view.api.enums.SceneElementEvent;
import oogasalad.view.api.exception.CreateNewUserException;
import oogasalad.view.api.exception.CreatingDuplicateUserException;
import oogasalad.view.api.exception.IncorrectPasswordException;
import oogasalad.view.api.exception.UserNotFoundException;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.SceneManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseHandler extends Handler{

  private final DatabaseController databaseController;
  private final List<String> currentPlayersManager;
  private TextField usernameTextField;
  private TextField passwordField;
  private ListView<String> playerPermissions;
  private ListView<String> friends;
  private Map<String, CheckBox> playerCheckBoxMap;
  private ComboBox<String> publicComboBox;
  private String avatarUrlField;
  private String currentGame;
  private Map<SceneElementEvent, Consumer<Node>> eventMap;
  private Map<String, Boolean> playerPermissionMap;
  private static final Logger LOGGER = LogManager.getLogger(DatabaseHandler.class);
  private static final Warning WARNING = new Warning();
  private Map<String, Boolean> friendsMap;
  private ComboBox<String> avatarComboBox;  // ComboBox for selecting an avatar

  public DatabaseHandler(GameController gameController, SceneManager sceneManager,
      DatabaseController databaseController,
      List<String> currentPlayersManager) {
    super(gameController, sceneManager);
    this.databaseController = databaseController;
    this.currentPlayersManager = currentPlayersManager;
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

  protected void createEventMap() {
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
    eventMap.put(SceneElementEvent.PLAYER_FRIENDS, this::setUpFriendPermissions);
    eventMap.put(SceneElementEvent.SUBMIT_FRIENDS, this::confirmFriendsHandler);
    eventMap.put(SceneElementEvent.SET_PUBLIC, this::createAccessibilityHandler);
  }

  public void createLoginHandler(Node node) {
    System.out.println("createLoginHandler in scene element handler called");
    node.setOnMouseClicked(e -> {
      try {
        boolean userLoggedIn = databaseController.loginUser(usernameTextField.getText(), passwordField.getText());
        if (userLoggedIn) {
          if(currentPlayersManager.contains(usernameTextField.getText())) {
            showAlert("Player Already Added to Game", "You can't play against yourself!");
          }
          else {
            currentPlayersManager.add(usernameTextField.getText());
            getSceneManager().createCurrentPlayersScene();
          }
        }
      } catch (UserNotFoundException | IncorrectPasswordException ex) {
        LOGGER.error(ex.getMessage());
        Warning warning = new Warning();
        warning.showAlert(getSceneManager().getScene(), AlertType.ERROR, "Login Error", null, ex.getMessage());
      } catch (Exception ex) {
        LOGGER.error("An unexpected error occurred during login: " + ex.getMessage());
        // Handle other unexpected errors
        Warning warning = new Warning();
        warning.showAlert(getSceneManager().getScene(), AlertType.ERROR, "Login Error", null, "An unexpected "
            + "error occurred during login.");
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
          Optional<String> selectedAvatar = showAvatarSelectionDialog();
          selectedAvatar.ifPresent(avatar -> {
            avatarUrlField = avatar;

            try {
              boolean userCreated = databaseController.canCreateUser(usernameTextField.getText(),
                  passwordField.getText(), avatarUrlField);
              System.out.println(userCreated);
              if (!userCreated) {
                LOGGER.error("User creation error - user already exists or could not be created.");
                throw new CreateNewUserException("User already exists or could not be created.");
              }
            } catch (CreateNewUserException | CreatingDuplicateUserException ex) {
              WARNING.showAlert(getSceneManager().getScene(), AlertType.ERROR,
                  "Creating New User Error", null, ex.getMessage());
            }
          });
        });
    //add the new user to the database
    //open the currentplayers screen with this player added to it
  }

  private Optional<String> showAvatarSelectionDialog() {
    Dialog<String> dialog = new Dialog<>();
    dialog.setTitle("Select Avatar");
    dialog.setHeaderText("Click on your desired avatar and then press ENTER");
    dialog.initOwner(getSceneManager().getScene().getWindow()); // Make sure it's modal in respect to the
    // application window
    dialog.initModality(Modality.WINDOW_MODAL); // Set modality to block user interaction with other windows

    ButtonType selectButtonType = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(selectButtonType, ButtonType.CANCEL);

    ComboBox<String> avatarComboBox = new ComboBox<>();
    ObservableList<String> avatars = loadAvatarNamesFromDirectory(); // Use the method to load avatar names dynamically
    avatarComboBox.setItems(avatars);
    avatarComboBox.setCellFactory(lv -> new ListCell<>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
          setGraphic(null);
        } else {
          Image img = new Image(getClass().getResourceAsStream("/view/avatar_images/" + item), 100, 100, true, false);
          ImageView imageView = new ImageView(img);
          setGraphic(imageView);
        }
      }
    });

    avatarComboBox.setButtonCell(new ListCell<>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
          setGraphic(null);
        } else {
          Image img = new Image(getClass().getResourceAsStream("/view/avatar_images/" + item), 50, 50, true, false);
          ImageView imageView = new ImageView(img);
          setGraphic(imageView);
        }
      }
    });

    dialog.getDialogPane().setContent(avatarComboBox);

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == selectButtonType) {
        return avatarComboBox.getSelectionModel().getSelectedItem();
      }
      return null;
    });

    return dialog.showAndWait();
  }


  private ObservableList<String> loadAvatarNamesFromDirectory() {
    try {
      // Adjust the path if necessary when running from a different setup
      return FXCollections.observableArrayList(
          Files.list(Paths.get(getClass().getResource("/view/avatar_images").toURI()))
              .filter(path -> path.toString().endsWith(".png"))
              .map(path -> path.getFileName().toString())
              .collect(Collectors.toList())
      );
    } catch (Exception e) {
      e.printStackTrace();
      return FXCollections.observableArrayList(); // return an empty list in case of error
    }
  }


  public void createPasswordHandler(Node node) {
    passwordField = (TextField) node;
    //save the password and add to database with username if not already there

  }

  public void createUsernameHandler(Node node) {
    usernameTextField = (TextField) node;
    //save username and add to database if not already there
  }




  private void createStartLoginHandler(Node node) {
    node.setOnMouseClicked(e -> getSceneManager().createLoginScene());

  }

  private void createLeaderboardHandler(Node node) {
    node.setOnMouseClicked(e -> getSceneManager().createLeaderboardScene());
  }

  private void setCurrentPlayers(Node node) {
    ListView<String> node2 = (ListView<String>) node;
    node2.setItems(FXCollections.observableList(currentPlayersManager));
  }

  public void setLeaderboard(Node node) {
    getGameController().getGameName();
    databaseController.leaderboardSet((ListView<String>) node);
  }

  private void setUpPlayerPermissions(Node node) {
    playerPermissions = (ListView<String>) node;
    playerPermissionMap = getGameController().getPlayerPermissions(currentGame);
    playerCheckBoxMap = new HashMap<>(); // Map to store player names to their corresponding checkboxes

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


  private void setUpFriendPermissions(Node node) {
    friends = (ListView<String>) node;
    friendsMap = databaseController.getFriends(currentPlayersManager.get(0));
    ObservableList<String> playerNames =
        FXCollections.observableArrayList(friendsMap.keySet());
    friends.setItems(playerNames);
    friends.setCellFactory(lv -> new ListCell<String>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
          setGraphic(null);
        } else {
          CheckBox checkBox = new CheckBox(); // Create a new checkbox for each cell
          boolean permission = friendsMap.get(item);
          checkBox.setSelected(permission);
          setText(item);
          // Update the map when the checkbox is toggled
          checkBox.setOnAction(event -> {
            friendsMap.put(item, checkBox.isSelected());
          });
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
      databaseController.setPublicPrivate(currentGame, publicComboBox.getSelectionModel().getSelectedItem());
      databaseController.writePlayerPermissions(currentGame, checkedPlayers, uncheckedPlayers);
      getSceneManager().createMenuScene();
    });
  }


  private void confirmFriendsHandler(Node node) {
    node.setOnMouseClicked(e -> {
      List<String> checkedPlayers = new ArrayList<>();
      List<String> uncheckedPlayers = new ArrayList<>();

      for (String item : friends.getItems()) {
        if (friendsMap.get(item)) {
          checkedPlayers.add(item);
        }
        else {
          uncheckedPlayers.add(item);
        }
      }
      databaseController.writeFriends(currentPlayersManager.get(0), checkedPlayers, uncheckedPlayers);
      getSceneManager().createMenuScene();
    });
  }

  private void createAccessibilityHandler(Node node) {
    publicComboBox = (ComboBox<String>) node;
    ObservableList<String> publicPrivateList = FXCollections.observableArrayList("public",
        "private", "friends");
    publicComboBox.setItems(publicPrivateList);
    publicComboBox.getSelectionModel().select(databaseController.getGameAccessibility(currentGame));
    publicComboBox.setOnAction(this::accessibilityBoxSelected);
  }

  private void accessibilityBoxSelected(ActionEvent event) {
    String mode = publicComboBox.getValue();
    friendsMap = databaseController.getFriends(currentPlayersManager.get(0));
    for (String s : playerCheckBoxMap.keySet()) {
      if (mode.equals("public")) {
        playerCheckBoxMap.get(s).setSelected(true);
        playerPermissionMap.put(s,true);
      }
      if (mode.equals("private")) {
        playerCheckBoxMap.get(s).setSelected(false);
        playerPermissionMap.put(s,false);
      }
      if (mode.equals("friends")) {
        playerCheckBoxMap.get(s).setSelected(friendsMap.containsKey(s) && friendsMap.get(s));
        playerPermissionMap.put(s,friendsMap.containsKey(s) && friendsMap.get(s));
      }
    }
  }

  public void setGame(String gameTitle) {
    currentGame = gameTitle;
  }

//  private void createCurrentPlayersHandler(Node node) {
//    node.setOnMouseClicked(e -> sceneManager.createCurrentPlayersScene());
//  }

}
