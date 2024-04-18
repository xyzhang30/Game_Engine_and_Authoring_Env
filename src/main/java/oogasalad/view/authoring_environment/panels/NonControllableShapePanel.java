package oogasalad.view.authoring_environment.panels;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import oogasalad.view.authoring_environment.authoring_screens.GameObjectType;

public class NonControllableShapePanel extends ShapePanel {

  private ComboBox<GameObjectType> nonControllableTypeDropdown;

  public NonControllableShapePanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy,
      AnchorPane rootPane, AnchorPane containerPane, StackPane canvas) {
    super(authoringProxy, shapeProxy, rootPane, containerPane, canvas);
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
    System.out.println("new");
    nonControllableTypeDropdown = new ComboBox<>();
    nonControllableTypeDropdown.getItems()
        .addAll(GameObjectType.SURFACE, GameObjectType.COLLIDABLE);
    nonControllableTypeDropdown.setPromptText("Select Obstacle Type");
    AnchorPane.setRightAnchor(nonControllableTypeDropdown, 300.0);
    AnchorPane.setTopAnchor(nonControllableTypeDropdown, 50.0);
    System.out.println("here");
    nonControllableTypeDropdown.setPrefSize(200, 50);
    containerPane.getChildren().add(nonControllableTypeDropdown);
  }

  private void handleNonControllableTypeSelection() {
    nonControllableTypeDropdown.valueProperty().addListener((obs, oldVal, nonControllableType) -> {
      if (shapeProxy.getShape() != null && nonControllableType != null) {
        authoringProxy.addNonControllableShape(shapeProxy.getShape(), nonControllableType);

      }
    });
  }
}
