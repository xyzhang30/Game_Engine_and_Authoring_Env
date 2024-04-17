package oogasalad.view.VisualElements;

import javafx.scene.Node;
import oogasalad.model.api.GameObjectRecord;

/**
 * Interface for a Visual Element. Represents a Node in a scene on view side.
 */
interface VisualElement {

  /**
   * Updates the Element properties.
   *
   * @param record Record for this Element's corresponding model object.
   */
  void update(GameObjectRecord record);

  /**
   * Returns the Node represented by this VisualElement.
   *
   * @return Node  A javafx Node.
   */
  Node getNode();

}
