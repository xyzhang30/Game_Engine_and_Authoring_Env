package oogasalad.view.Controlling;

import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import oogasalad.view.AuthoringScreens.AuthoringScreen;
import oogasalad.view.AuthoringScreens.GoalSelectionScreen;
import oogasalad.view.AuthoringScreens.ImageType;

public class AuthoringController {
  private Stage stage;

  public AuthoringController(Stage stage){
    this.stage = stage;
  }

  public void startNextSelection(ImageType imageType, Rectangle backgroundBox){
    //TODO: does it make sense to make backgroundbox a stackpane to hold all current selections?
    switch(imageType){
      case BACKGROUND -> {
        GoalSelectionScreen goalSelectionScreen = new GoalSelectionScreen(this, backgroundBox);
          stage.setScene(goalSelectionScreen.getScene());
      }
    }
  }

}
