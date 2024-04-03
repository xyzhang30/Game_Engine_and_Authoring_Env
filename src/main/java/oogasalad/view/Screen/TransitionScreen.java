package oogasalad.view.Screen;

import javafx.scene.Group;
import javafx.scene.text.Text;

public class TransitionScreen extends UIScreen {
  protected Text title;
  protected Text additionalInfo; // Can be used or updated by subclasses

  public TransitionScreen(String titleText) {
    this.title = new Text(titleText);
    this.additionalInfo = new Text();
    root = new Group();
    setup();
  }

  private void setup() {
    setToThemeFont(title, 36);
    title.setEffect(createDropShadow());
    // Assuming sceneWidth and sceneHeight are calculated or set beforehand
    title.setLayoutX(sceneWidth / 2 - title.getBoundsInLocal().getWidth() / 2);
    title.setLayoutY(sceneHeight / 4); // Position the title at 1/4th the height
    root.getChildren().add(title); // Add title to the pane
  }

  public void setAdditionalInfo(String info) {
    additionalInfo.setText(info);
    setToThemeFont(additionalInfo, 24);
    additionalInfo.setEffect(createDropShadow());
    additionalInfo.setLayoutX(sceneWidth / 2 - additionalInfo.getBoundsInLocal().getWidth() / 2);
    additionalInfo.setLayoutY(sceneHeight / 3); // Position below the title
    if (!root.getChildren().contains(additionalInfo)) {
      root.getChildren().add(additionalInfo);
    }
  }

  // Method to be potentially overridden by subclasses
  protected void setupTransitionDetails() {

  }

}
