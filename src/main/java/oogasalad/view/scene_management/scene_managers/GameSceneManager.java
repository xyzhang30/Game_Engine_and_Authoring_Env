package oogasalad.view.scene_management.scene_managers;

import javafx.scene.layout.Pane;
import oogasalad.model.api.GameRecord;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_element.GameStatusManager;
import oogasalad.view.visual_elements.CompositeElement;

public class GameSceneManager extends SceneManager{
  private CompositeElement compositeElement;
  private GameStatusManager gameStatusManager;
  private Pane pauseElements;
  private Pane transitionElements;
  private int currentRound;
  private final String gameManagementElementsPath = "data/scene_elements/gameManagementElements.xml";
  private final String transitionElementsPath = "data/scene_elements/transitionElements.xml";
  private final String gameOverSceneElementsPath = "data/scene_elements/gameOverElements.xml";
  private final String pausePath = "data/scene_elements/pauseElements.xml";


  /**
   * Constructor initializes scene, root, sceneElementParser, and sceneElementFactory which are
   * necessary to update scenes with new elements
   *
   * @param gameController handles model/view interactions
   * @param screenWidth    screen width to be used for scaling ratio based elements
   * @param screenHeight   screen height to be used for scaling ratio based elements
   */
  public GameSceneManager(GameController gameController, double screenWidth,
      double screenHeight) {
    super(gameController, screenWidth, screenHeight);
  }

  /**
   * Called when game is resumed, removes pause screen elements
   */
  public void removePauseSheen() {
    getRoot().getChildren().remove(pauseElements);
  }

  /**
   * Updates game elements and stat display from GameRecord info
   *
   * @param gameRecord represents updated state of game
   */
  public void update(GameRecord gameRecord) {
    compositeElement.update(gameRecord.gameObjectRecords());
    gameStatusManager.update(gameRecord.players(), gameRecord.turn(), gameRecord.round());
    getRoot().requestFocus();
    checkEndRound(gameRecord);
  }

  /**
   * Called when next round is started, removes transition screen elements
   */
  void removeTransitionSheen() {
    getRoot().getChildren().remove(transitionElements);
    getRoot().requestFocus();
  }

  /**
   * Creates a pause display by adding elements created from pause xml file
   */
  void createPauseDisplay() {
    if (!getRoot().getChildren().contains(pauseElements)) {
      getRoot().getChildren().add(pauseElements);
    }
  }

  /**
   * Makes the game screen including game board elements, striker input, and game stat display
   *
   * @param compositeElement game board elements
   * @param gameRecord       holds score, turn, and round info for stat display
   */
  public void makeGameScreen(CompositeElement compositeElement, GameRecord gameRecord) {
    this.compositeElement = compositeElement;
    pauseElements = createSceneElements(pausePath);
    transitionElements = createSceneElements(transitionElementsPath);
    addGameManagementElementsToGame(gameRecord);
    addGameElementsToGame();
    getRoot().requestFocus();
  }


  private void createTransitionDisplay() {
    getRoot().getChildren().add(transitionElements);
  }

  private void createGameOverScene() {
    resetRoot();
    getRoot().getChildren().add(createSceneElements(gameOverSceneElementsPath));
  }

  private void addGameManagementElementsToGame(GameRecord gameRecord) {
    resetRoot();
    currentRound = gameRecord.round();
    Pane sceneElements = createSceneElements(gameManagementElementsPath);
    getRoot().getChildren().addAll(sceneElements);

  }

  private void addGameElementsToGame() {
    compositeElement.addElementsToRoot(getRoot());
  }

  private void checkEndRound(GameRecord gameRecord) {
    if (gameRecord.gameOver()) {
      createGameOverScene();
      gameStatusManager.update(gameRecord.players(), gameRecord.turn(), gameRecord.round());
    } else if (gameRecord.round() != currentRound) {
      currentRound = gameRecord.round();
      createTransitionDisplay();

    }
  }

}
