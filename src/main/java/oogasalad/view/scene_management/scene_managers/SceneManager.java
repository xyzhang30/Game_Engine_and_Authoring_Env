package oogasalad.view.scene_management.scene_managers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javax.xml.parsers.ParserConfigurationException;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_element.GameStatusManager;
import oogasalad.view.scene_management.scene_element.SceneElementFactory;
import oogasalad.view.scene_management.scene_element.SceneElementHandler;
import oogasalad.view.scene_management.element_parsers.SceneElementParser;
import oogasalad.view.scene_management.scene_element.SceneElementStyler;
import org.xml.sax.SAXException;

/**
 * Parent class for managing different scenes during game play; provides shared functionality
 * including element creation and access to root and scene
 *
 * @author Doga Ozmen, Jordan Haytaian
 */
public class SceneManager {

  private final Pane root;
  private final Scene scene;
  private final SceneElementParser sceneElementParser;
  private final SceneElementFactory sceneElementFactory;
  private final SceneElementStyler sceneElementStyler;
  private GameStatusManager gameStatusManager;


  /**
   * Constructor initializes scene, root, sceneElementParser, and sceneElementFactory which are
   * necessary to update scenes with new elements
   *
   * @param gameController handles model/view interactions
   * @param screenWidth    screen width to be used for scaling ratio based elements
   * @param screenHeight   screen height to be used for scaling ratio based elements
   */
  public SceneManager(GameController gameController, double screenWidth,
      double screenHeight) {
    root = new Pane();
    scene = new Scene(root);

    sceneElementParser = new SceneElementParser();
    sceneElementStyler = new SceneElementStyler(root);
    gameStatusManager = new GameStatusManager();
    sceneElementFactory = new SceneElementFactory(screenWidth, screenHeight, sceneElementStyler,
        new SceneElementHandler(gameController, this, gameStatusManager));
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
   * Getter for root node
   *
   * @return root node of scene
   */
  public Pane getRoot() {
    return root;
  }

  /**
   * Getter for sceneElementStyler, used to change stylesheets
   *
   * @return instance of 'SceneElementStyler'
   */
  SceneElementStyler getSceneElementStyler() {
    return sceneElementStyler;
  }

  /**
   * Creates scene elements by parsing filepath for element parameters and passing parameters to
   * SceneElementFactory instance
   *
   * @param filePath xml file containing element parameters
   * @return Pane containing created elements
   */
  Pane createSceneElements(String filePath) {
    try {
      List<Map<String, String>> sceneElementParameters = sceneElementParser.getElementParametersFromFile(
          filePath);
      return sceneElementFactory.createSceneElements(sceneElementParameters);

    } catch (ParserConfigurationException | SAXException | IOException e) {
      //TODO: Exception Handling
      return null;
    }
  }

  /**
   * Resets root by removing all children
   */
  void resetRoot() {
    root.getChildren().clear();
  }

}
