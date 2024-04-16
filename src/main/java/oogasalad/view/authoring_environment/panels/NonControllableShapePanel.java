package oogasalad.view.authoring_environment.panels;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import oogasalad.view.authoring_environment.authoring_screens.NonControllableType;

public class NonControllableShapePanel extends ShapePanel {
  private final ComboBox<NonControllableType> nonControllableTypeDropdown = new ComboBox<>();;
  private final Map<Shape, NonControllableType> nonControllableMap = new HashMap<>();;

  public NonControllableShapePanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane rootPane,
      StackPane authoringBox) {
    super(authoringProxy, shapeProxy, rootPane, authoringBox);
  }

  @Override
  public void handleEvents() {
    super.handleEvents();
    handleNonControllableTypeSelection();
  }

  @Override
  public void createElements() {
    super.createElements();
    createNonControllableTypeSelection();
  }
  private void createNonControllableTypeSelection() {
    nonControllableTypeDropdown.getItems()
        .addAll(NonControllableType.SURFACE, NonControllableType.MOVABLE);
    nonControllableTypeDropdown.setPromptText("Select Obstacle Type");
    AnchorPane.setRightAnchor(nonControllableTypeDropdown, 50.0);
    AnchorPane.setBottomAnchor(nonControllableTypeDropdown, 300.0);
    nonControllableTypeDropdown.setPrefSize(200, 100);
    rootPane.getChildren().add(nonControllableTypeDropdown);
  }

  private void handleNonControllableTypeSelection() {
    nonControllableTypeDropdown.valueProperty().addListener((obs, oldVal, nonControllableType) -> {
      if (shapeProxy.getShape() != null && nonControllableType != null) {
        authoringProxy.addNonControllableShape(shapeProxy.getShape(), nonControllableType);
      }
    });
  }
}
