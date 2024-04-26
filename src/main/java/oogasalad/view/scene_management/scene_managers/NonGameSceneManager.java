package oogasalad.view.scene_management.scene_managers;

import oogasalad.view.api.enums.ThemeType;
import oogasalad.view.controller.GameController;

public class NonGameSceneManager extends SceneManager {
  private final String titleSceneElementsPath = "data/scene_elements/titleSceneElements.xml";
  private final String menuSceneElementsPath = "data/scene_elements/menuSceneElements.xml";
  private final String helpInstructionPath = "data/scene_elements/helpInstructionElements.xml";


  /**
   * Constructor initializes scene, root, sceneElementParser, and sceneElementFactory which are
   * necessary to update scenes with new elements
   *
   * @param gameController handles model/view interactions
   * @param screenWidth    screen width to be used for scaling ratio based elements
   * @param screenHeight   screen height to be used for scaling ratio based elements
   */
  public NonGameSceneManager(GameController gameController, double screenWidth,
      double screenHeight) {
    super(gameController, screenWidth, screenHeight);
  }

  /**
   * Creates a title scene by resetting the root and creating new elements from title scene xml
   * file
   */
  public void createTitleScene() {
    resetRoot();
    getRoot().getChildren().add(createSceneElements(titleSceneElementsPath));
  }

  /**
   * Creates a menu scene by resetting the root and creating new elements from menu scene xml file
   */
  public void createMenuScene() {
    resetRoot();
    getRoot().getChildren().add(createSceneElements(menuSceneElementsPath));
  }

  /**
   * Creates a help instruction scene by resetting the root and creating new elements from help
   * instruction scene xml file
   */
  public void createHelpInstructions() {
    resetRoot();
    getRoot().getChildren().add(createSceneElements(helpInstructionPath));
  }

  /**
   * Changes the theme by prompting the element styler to switch style sheets
   *
   * @param selectedTheme theme selected by user
   */
  void changeTheme(ThemeType selectedTheme) {
    sceneElementStyler.changeTheme(selectedTheme);
  }

}
