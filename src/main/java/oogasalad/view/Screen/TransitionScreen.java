package oogasalad.view.Screen;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Text;


public class TransitionScreen extends UIScreen {
  protected Text title;
  protected Text additionalInfo;
  private final Group root;

  public TransitionScreen() {
    // Hardcoding titleText to "Mini Golf Completed"
    String titleText = "Mini Golf Completed";
    this.title = new Text(titleText);
    this.additionalInfo = new Text();
    root = new Group();
    setup();
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  private void setup() {
    setToThemeFont(title, 36);
    title.setEffect(createDropShadow());
    // Update layout calculations to account for new title text
    title.setLayoutX(sceneWidth / 2 - title.getBoundsInLocal().getWidth() / 2);
    title.setLayoutY(sceneHeight / 4); // Position the title at 1/4th the height
    root.getChildren().add(title); // Add title to the group

    // Optionally call this if you have additional info to display
    // For instance, "Click to continue" or showing the score
    // setAdditionalInfo("Your Score: 100"); // Example additional info
  }

  public void setAdditionalInfo(String info) {
    additionalInfo.setText(info);
    setToThemeFont(additionalInfo, 24);
    additionalInfo.setEffect(createDropShadow());
    // Adjust layout for additionalInfo based on the actual text bounds
    additionalInfo.setLayoutX(sceneWidth / 2 - additionalInfo.getBoundsInLocal().getWidth() / 2);
    additionalInfo.setLayoutY(sceneHeight / 3); // Position below the title
    if (!root.getChildren().contains(additionalInfo)) {
      root.getChildren().add(additionalInfo);
    }
  }

  // Method to be potentially overridden by subclasses
  protected void setupTransitionDetails() {
    // This method can be overridden in subclasses to set up additional transition details
  }
}
