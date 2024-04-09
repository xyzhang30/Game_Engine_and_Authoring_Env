package oogasalad.view.Controlling;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oogasalad.view.AuthoringScreens.BallSelectionScreen;
import oogasalad.view.AuthoringScreens.GoalSelectionScreen;
import oogasalad.view.AuthoringScreens.ImageType;
import oogasalad.view.AuthoringScreens.ObstacleSelectionScreen;

/**
 * Class to handle transitions between authoring environment scenes and communications with backend
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public class AuthoringController {
  private Stage stage;

  public AuthoringController(Stage stage){
    this.stage = stage;
  }

  /**
   * Starts the next selection process by creating the applicable scene and showing it on the stage
   * @param imageType the selection process that has just finished
   * @param authoringBox holds the user's current game configurations
   */
  public void startNextSelection(ImageType imageType, StackPane authoringBox){
    switch(imageType){
      case BACKGROUND -> {
        GoalSelectionScreen goalSelectionScreen = new GoalSelectionScreen(this, authoringBox);
          stage.setScene(goalSelectionScreen.getScene());
      }
      case GOAL -> {
        ObstacleSelectionScreen obstacleSelectionScreen = new ObstacleSelectionScreen(this, authoringBox);
        stage.setScene(obstacleSelectionScreen.getScene());
      }
      case OBSTACLE -> {
        BallSelectionScreen ballSelectionScreen = new BallSelectionScreen(this, authoringBox);
        stage.setScene(ballSelectionScreen.getScene());
      }
    }
  }

}
