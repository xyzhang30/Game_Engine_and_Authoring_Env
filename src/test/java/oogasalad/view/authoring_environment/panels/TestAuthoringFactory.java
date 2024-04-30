package oogasalad.view.authoring_environment.panels;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Label;
import oogasalad.view.api.authoring.AuthoringFactory;
import oogasalad.view.api.enums.CollidableType;
import oogasalad.view.authoring_environment.util.GameObjectAttributesContainer;

public class TestAuthoringFactory implements AuthoringFactory {
  @Override
  public List<Node> createGameObjectsConfiguration() {
    // Return simple mock or dummy implementations as needed
    return Collections.singletonList(new Label("GameObject Config"));
  }

  @Override
  public List<Node> createSurfacesConfiguration() {
    return Collections.singletonList(new Label("Surface Config"));
  }

  @Override
  public List<Node> createCollidablesConfiguration() {
    return Collections.singletonList(new Label("Collidable Config"));
  }

  @Override
  public List<Node> createPlayersConfiguration() {
    return Collections.singletonList(new Label("Player Config"));
  }

  @Override
  public void resetAuthoringElements(GameObjectAttributesContainer gameObj,
      Map<Integer, Map<CollidableType, List<Integer>>> playersMap) {
    // Implement as needed for testing
  }
}
