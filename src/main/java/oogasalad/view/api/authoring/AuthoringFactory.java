package oogasalad.view.api.authoring;

import java.util.List;
import javafx.scene.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface AuthoringFactory {

  Logger LOGGER = LogManager.getLogger(AuthoringFactory.class);

  String RESOURCE_FOLDER_PATH = "view.";
  String UI_FILE_PREFIX = "UIElements";

  List<Node> createGameObjectsConfiguration();
  List<Node> createSurfacesConfiguration();
  List<Node> createCollidablesConfiguration();
  List<Node> createPlayersConfiguration();
  void resetAuthoringElements();


}
