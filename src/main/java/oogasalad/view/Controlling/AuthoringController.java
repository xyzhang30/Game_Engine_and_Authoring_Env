package oogasalad.view.Controlling;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oogasalad.view.AuthoringScreens.ControllableElementSelectionScreen;
import oogasalad.view.AuthoringScreens.ImageType;
import oogasalad.view.AuthoringScreens.NonControllableElementSelection;

/**
 * Class to handle transitions between authoring environment scenes and communications with backend
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public class AuthoringController {

  private Stage stage;

  public AuthoringController(Stage stage) {
    this.stage = stage;
  }

  /**
   * Starts the next selection process by creating the applicable scene and showing it on the stage
   *
   * @param imageType    the selection process that has just finished
   * @param authoringBox holds the user's current game configurations
   */
  public void startNextSelection(ImageType imageType, StackPane authoringBox) {
    switch (imageType) {
      case BACKGROUND -> {
        ControllableElementSelectionScreen controllableElementSelectionScreen =
            new ControllableElementSelectionScreen(this, authoringBox);
        stage.setScene(controllableElementSelectionScreen.getScene());
      }
      case CONTROLLABLE_ELEMENT -> {
        NonControllableElementSelection nonControllableElementSelection =
            new NonControllableElementSelection(this, authoringBox);
        stage.setScene(nonControllableElementSelection.getScene());
      }
      case NONCONTROLLABLE_ELEMENT -> {
        //interaction screen
      }
    }
  }

}
