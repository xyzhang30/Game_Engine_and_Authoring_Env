package oogasalad.view.scene_management.scene_managers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.xml.parsers.ParserConfigurationException;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.ViewGameObjectRecord;
import oogasalad.model.gameparser.GameLoaderView;
import oogasalad.view.Warning;
import oogasalad.view.api.enums.SupportedLanguage;
import oogasalad.view.api.enums.ThemeType;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;
import oogasalad.view.database.Leaderboard;
import oogasalad.view.scene_management.element_parsers.SceneElementParser;
import oogasalad.view.scene_management.scene_element.GameStatusManager;
import oogasalad.view.scene_management.scene_element.SceneElementFactory;
import oogasalad.view.scene_management.scene_element.SceneElementStyler;
import oogasalad.view.scene_management.scene_element.scene_element_handler.SceneElementHandler;
import oogasalad.view.visual_elements.CompositeElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * Manages different screens (scenes) within the game, such as the title screen, menu screen, game
 * screen, and transition screen. It updates and transitions between screens based on game state and
 * player interactions.
 *
 * @author Doga Ozmen, Jordan Haytaian
 */
public class SceneManager {

  private final Pane root;
  private final Scene scene;
  private final SceneElementParser sceneElementParser;
  private final SceneElementFactory sceneElementFactory;
  private final SceneElementStyler sceneElementStyler;
  private final String manageGamePath = "data/scene_elements/managePermissionElements.xml";
  private final String titleSceneElementsPath = "data/scene_elements/titleSceneElements.xml";
  private final String menuSceneElementsPath = "data/scene_elements/menuSceneElements.xml";
  private final String gameManagementElementsPath =
      "data/scene_elements/gameManagementElements.xml";
  private final String transitionElementsPath = "data/scene_elements/transitionElements.xml";
  private final String gameOverSceneElementsPath = "data/scene_elements/gameOverElements.xml";
  private final String pauseElementsPath = "data/scene_elements/pauseElements.xml";
  private final String helpInstructionElementsPath =
      "data/scene_elements/helpInstructionElements.xml";
  private final String languageSelectionElementsPath =
      "data/scene_elements/languageSelectionElements.xml";
  private final String loginElementsPath =
      "data/scene_elements/loginElements.xml";
  private final String currentPlayersElementsPath =
      "data/scene_elements/currentPlayersElements.xml";
  private final String leaderboardElementsPath =
      "data/scene_elements/leaderboardElements.xml";
  private final String addFriendScenePath =
      "data/scene_elements/addFriends.xml";
  private CompositeElement compositeElement;
  private final GameStatusManager gameStatusManager;
  private Leaderboard leaderboard;
  private Pane pauseElements;
  private Pane transitionElements;
  private int currentRound;
  private SupportedLanguage selectedLanguage;
  private final DatabaseController databaseController;

  private static final Logger LOGGER = LogManager.getLogger(SceneManager.class);
  private static final Warning WARNING = new Warning();


  /**
   * Constructor initializes scene, root, sceneElementParser, and sceneElementFactory which are
   * necessary to update scenes with new elements
   *
   * @param gameController        handles model/view interactions
   * @param screenWidth           screen width to be used for scaling ratio based elements
   * @param screenHeight          screen height to be used for scaling ratio based elements
   * @param databaseController    handles database interactions
   * @param currentPlayersManager manages players
   */
  public SceneManager(GameController gameController, DatabaseController databaseController,
      double screenWidth,
      double screenHeight, List<String> currentPlayersManager) {
    root = new Pane();
    scene = new Scene(root);
    selectedLanguage = SupportedLanguage.ENGLISH;
    sceneElementParser = new SceneElementParser();
    sceneElementStyler = new SceneElementStyler(root);
    gameStatusManager = new GameStatusManager();
    sceneElementFactory = new SceneElementFactory(screenWidth, screenHeight, sceneElementStyler,
        new SceneElementHandler(gameController, databaseController, this, gameStatusManager,
            currentPlayersManager));
    createLanguageSelectionScene();
    this.databaseController = databaseController;
  }

  /**
   * Gets the x coordinate of the left side of the game board by querying composite element
   *
   * @return double representing the x coordinate of the left side of the game board
   */
  public double getGameBoardLeftBound() {
    return compositeElement.getGameBoardLeftBound();
  }


  /**
   * Gets the x coordinate of the right side of the game board by querying composite element
   *
   * @return double representing the x coordinate of the right side of the game board
   */
  public double getGameBoardRightBound() {
    return compositeElement.getGameBoardRightBound();
  }

  /**
   * Gets the x coordinate of the right side of the game board by querying composite element
   *
   * @return double representing the x coordinate of the right side of the game board
   */
  public double getGameBoardUpBound() {
    return compositeElement.getGameBoardUpBound();
  }

  /**
   * Gets the x coordinate of the right side of the game board by querying composite element
   *
   * @return double representing the x coordinate of the right side of the game board
   */
  public double getGameBoardDownBound() {
    return compositeElement.getGameBoardDownBound();
  }

  /**
   * Getter for the scene
   *
   * @return scene displaying game visuals
   */
  public Scene getScene() {
    return scene;
  }

  /**
   * Creates a language selection scene by resetting the root and creating new elements from
   * language selection scene xml file
   */
  public void createLanguageSelectionScene() {
    resetRoot();
    root.getChildren().add(createSceneElements(languageSelectionElementsPath));

    Text titleNode = new Text("Language"); // or use any other relevant text or control
    titleNode.setId("languageElement");
    root.getChildren().add(titleNode);
  }

  /**
   * Creates a title scene by resetting the root and creating new elements from title scene xml
   * file
   */
  public void createTitleScene() {
    resetRoot();
    root.getChildren().add(createSceneElements(titleSceneElementsPath));

    Text titleNode = new Text("Title"); // or use any other relevant text or control
    titleNode.setId("titleElement");
    root.getChildren().add(titleNode);


  }

  /**
   * Creates a menu scene by resetting the root and creating new elements from menu scene xml file
   */
  public void createMenuScene() {
    resetRoot();
    root.getChildren().add(createSceneElements(menuSceneElementsPath));
  }

  /**
   * Called when game is resumed, removes pause screen elements
   */
  public void removePauseSheen() {
    root.getChildren().remove(pauseElements);
  }

  /**
   * Updates game elements and stat display from GameRecord info
   *
   * @param gameRecord represents updated state of game
   */
  public void update(GameRecord gameRecord, Map<Integer, String> playerMap, String gameName) {
    compositeElement.update(gameRecord.gameObjectRecords());
    gameStatusManager.update(gameRecord.players(), gameRecord.turn(), gameRecord.round(),
        playerMap);
    root.requestFocus();
    checkEndRound(gameRecord, playerMap, gameName);
  }

  /**
   * Called when next round is started, removes transition screen elements
   */
  public void removeTransitionSheen() {
    root.getChildren().remove(transitionElements);
    root.requestFocus();
  }

  public void createManagePermissionsScene() {
    resetRoot();
    root.getChildren().add(createSceneElements(manageGamePath));
  }

  /**
   * Getter for root node
   *
   * @return root node of scene
   */
  public Pane getRoot() {
    return root;
  }

  /**
   * Creates a pause display by adding elements created from pause xml file
   */
  public void createPauseDisplay() {
    if (!root.getChildren().contains(pauseElements)) {
      root.getChildren().add(pauseElements);
    }
  }

  public void createHelpInstructions() {
    resetRoot();
    root.getChildren().add(createSceneElements(helpInstructionElementsPath));

    Text helpNode = new Text("Help Instructions");
    helpNode.setId("helpElement");
    root.getChildren().add(helpNode);
  }

  /**
   * Changes the theme by prompting the element styler to switch style sheets
   *
   * @param selectedTheme theme selected by user
   */
  public void changeTheme(ThemeType selectedTheme) {
    sceneElementStyler.changeTheme(selectedTheme);
  }

  /**
   * Setter for selected language
   *
   * @param language represents user selected language
   */
  public void setLanguage(SupportedLanguage language) {
    selectedLanguage = language;
  }

  /**
   * Makes the game scene including game board elements, striker input, and game stat display
   *
   * @param compositeElement game board elements
   * @param gameRecord       holds score, turn, and round info for stat display
   */
  public void makeGameScene(CompositeElement compositeElement, GameRecord gameRecord) {
    this.compositeElement = compositeElement;
    pauseElements = createSceneElements(pauseElementsPath);
    transitionElements = createSceneElements(transitionElementsPath);
    addGameManagementElementsToGame(gameRecord);
    addGameElementsToGame();
    root.requestFocus();
  }

  public void changeMod(List<ViewGameObjectRecord> recordList) {
    compositeElement.updateMod(recordList);
  }

  private void createTransitionDisplay() {
    root.getChildren().add(transitionElements);
  }

  public void createGameOverScene() {
    resetRoot();
    root.getChildren().add(createSceneElements(gameOverSceneElementsPath));
    gameStatusManager.restoreLastUpdate();
  }


  /**
   * Creates scene elements from a specified XML file. This method parses the file and uses a
   * factory to generate UI components based on the extracted parameters.
   *
   * @param filePath the path to the XML file containing scene elements specifications.
   * @return a Pane containing the created scene elements, or null if an error occurs.
   */
  private Pane createSceneElements(String filePath) {
    try {
      // Parse the file to get a list of element parameters
      List<Map<String, String>> sceneElementParameters = sceneElementParser.getElementParametersFromFile(
          filePath);
      return sceneElementFactory.createSceneElements(sceneElementParameters, selectedLanguage);
    } catch (ParserConfigurationException e) {
      logError("Parser configuration error", e);
    } catch (SAXException e) {
      // Handle errors related to XML parsing
      logError("XML parsing error", e);
    } catch (IOException e) {
      // Handle I/O errors
      logError("I/O error when reading the file", e);
    }
    return null;
  }

  /**
   * Logs errors to the system's error logging service or standard output. This is a simple way to
   * centralize error handling and could be replaced with a more robust logging framework.
   *
   * @param message the error message to log
   * @param e       the exception that was caught
   */
  private void logError(String message, Exception e) {
    LOGGER.error(message + e.getMessage());
    WARNING.showAlert(scene, AlertType.ERROR, message, null, e.getMessage());
  }

  private void addGameManagementElementsToGame(GameRecord gameRecord) {
    resetRoot();
    currentRound = gameRecord.round();
    Pane sceneElements = createSceneElements(gameManagementElementsPath);
    root.getChildren().addAll(sceneElements);

  }

  private void addGameElementsToGame() {
    compositeElement.addElementsToRoot(root);
  }

  private void resetRoot() {
    root.getChildren().clear();
  }


  private void checkEndRound(GameRecord gameRecord, Map<Integer, String> playerMap,
      String gameName) {
    if (gameRecord.gameOver()) {
      databaseController.addGameResult(playerMap, gameRecord.players(), gameName);
      gameStatusManager.update(gameRecord.players(), gameRecord.turn(), gameRecord.round(),
          playerMap);
      createGameOverScene();
    } else if (gameRecord.round() != currentRound) {
      currentRound = gameRecord.round();
      createTransitionDisplay();
    }
  }

  public void createLoginScene() {
    resetRoot();
    root.getChildren().add(createSceneElements(loginElementsPath));
  }

  public void createCurrentPlayersScene() {
    resetRoot();
    root.getChildren().add(createSceneElements(currentPlayersElementsPath));
  }

  public void createLeaderboardScene() {
    resetRoot();
    root.getChildren().add(createSceneElements(leaderboardElementsPath));
  }

//  public void displayErrorMessage(String message) {
//    Alert alert = new Alert(Alert.AlertType.ERROR);
//    alert.setTitle("Error");
//    alert.setHeaderText(null);
//    alert.setContentText(message);
//    alert.showAndWait();
//  }

  public void createAddFriendScene() {
    resetRoot();
    root.getChildren().add(createSceneElements(addFriendScenePath));
  }


}
