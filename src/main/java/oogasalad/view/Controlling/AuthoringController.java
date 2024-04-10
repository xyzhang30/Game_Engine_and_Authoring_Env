package oogasalad.view.Controlling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import oogasalad.model.api.data.CollidableObject;
import oogasalad.model.api.data.Dimension;
import oogasalad.model.api.data.Position;
import oogasalad.view.AuthoringScreens.BackgroundSelectionScreen;
import oogasalad.view.AuthoringScreens.ControllableElementSelectionScreen;
import oogasalad.view.AuthoringScreens.ImageType;
import oogasalad.view.AuthoringScreens.InteractionSelectionScreen;
import oogasalad.view.AuthoringScreens.InteractionType;
import oogasalad.view.AuthoringScreens.NonControllableElementSelection;
import oogasalad.view.AuthoringScreens.NonControllableType;

/**
 * Class to handle transitions between authoring environment scenes and communications with backend
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public class AuthoringController {

  private Stage stage;
  private BuilderDirector builderDirector = new BuilderDirector();
  private List<Shape> controllables;
  private Map<Shape, NonControllableType> nonControllableTypeMap;

  public AuthoringController() {
    stage = new Stage();
    controllables = new ArrayList<>();
    nonControllableTypeMap = new HashMap<>();
  }

  public void startAuthoring() {
    Map<Shape, NonControllableType> map = new HashMap<>();
    BackgroundSelectionScreen scene = new BackgroundSelectionScreen(
        this, new StackPane(), map, new ArrayList<>());
    stage.setScene(scene.getScene());
    stage.show();
  }

  /**
   * Starts the next selection process by creating the applicable scene and showing it on the stage
   *
   * @param imageType    the selection process that has just finished
   * @param authoringBox holds the user's current game configurations
   */
  public void startNextSelection(ImageType imageType, StackPane authoringBox,
      Map<Shape, NonControllableType> nonControllableMap, List<Shape> controllableList) {
    switch (imageType) {
      case BACKGROUND -> {
        ControllableElementSelectionScreen controllableElementSelectionScreen =
            new ControllableElementSelectionScreen(this, authoringBox, nonControllableMap,
                controllableList);
        System.out.println("finished background, getting controllable");
        stage.setScene(controllableElementSelectionScreen.getScene());
      }
      case CONTROLLABLE_ELEMENT -> {
        NonControllableElementSelection nonControllableElementSelection =
            new NonControllableElementSelection(this, authoringBox, nonControllableMap,
                controllableList);
        System.out.println("finished controllable, getting noncontrollable");
        stage.setScene(nonControllableElementSelection.getScene());
      }
      case NONCONTROLLABLE_ELEMENT -> {
        InteractionSelectionScreen interactionSelectionScreen
            = new InteractionSelectionScreen(this, authoringBox, nonControllableMap,
            controllableList);
        System.out.println("finished noncontrollable, getting interaction");
        stage.setScene(interactionSelectionScreen.getScene());
      }
      case INTERACTION -> {
        System.out.println("writing to json!");
        boolean saveGameSuccess = submitGame();
        Alert alert = new Alert(AlertType.INFORMATION);
        if (saveGameSuccess) {
          alert.setTitle("Save Game Success");
          alert.setHeaderText(null);
          alert.setContentText("Game saved successfully!");
          alert.showAndWait();
        } else {
          alert.setTitle("Save Game Error");
          alert.setHeaderText(null);
          alert.setContentText("Saving game failed :(");
          alert.showAndWait();
        }
      }
    }
  }

  public void endAuthoring(Map<List<Shape>, Map<InteractionType, Integer>> interactionMap) {
    stage.close();
  }

  private boolean submitGame() {
    try {
      writeCollidablesRecord();
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }

  private void writeCollidablesRecord() {
    int collidableId = 0;
    List<CollidableObject> collidableObjects = new ArrayList<>();
    //controllables
    for (Shape shape : controllables) {
      Color c = (Color) (shape.getFill());
      List<String> properties = List.of("movable", "collidable", "controllable");
      String shapeName = (shape instanceof Circle) ? "Circle" : "Rectangle";
      CollidableObject collidableObject = new CollidableObject(collidableId,
          properties, 10,
          new Position(shape.getLayoutX(), shape.getLayoutY()), shapeName,
          new Dimension(shape.getLayoutBounds().getWidth(), shape.getLayoutBounds().getHeight()),
          List.of((int) c.getRed() * 255, (int) c.getGreen() * 255, (int) c.getBlue() * 255), 0.5, "");
      collidableObjects.add(collidableObject);
    }

    //nonControllables
  }

}
