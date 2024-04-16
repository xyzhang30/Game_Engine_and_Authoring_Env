package oogasalad.view.visual_elements;

import javafx.scene.Node;
import oogasalad.model.api.CollidableRecord;

/**
 * Interface for a Visual Element. Represents a Node in a scene on view side.
 */
interface VisualElement {

  /**
   * Updates the Element properties.
   *
   * @param record Record for this Element's corresponding model object.
   */
  void update(CollidableRecord record);

  /**
   * Returns the Node represented by this VisualElement.
   *
   * @return Node  A javafx Node.
   */
  Node getNode();
}
