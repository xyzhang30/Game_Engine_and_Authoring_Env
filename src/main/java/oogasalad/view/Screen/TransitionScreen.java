package oogasalad.view.Screen;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Text;


public class TransitionScreen extends UIScreen {
  protected Text title;
  protected Text additionalInfo;
  private final Group root;

  public TransitionScreen() {
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
    title.setLayoutX(sceneWidth / 2 - title.getBoundsInLocal().getWidth() / 2);
    title.setLayoutY(sceneHeight / 4);
    root.getChildren().add(title);
  }

  public void setAdditionalInfo(String info) {
    additionalInfo.setText(info);
    setToThemeFont(additionalInfo, 24);
    additionalInfo.setEffect(createDropShadow());
    additionalInfo.setLayoutX(sceneWidth / 2 - additionalInfo.getBoundsInLocal().getWidth() / 2);
    additionalInfo.setLayoutY(sceneHeight / 3);
    if (!root.getChildren().contains(additionalInfo)) {
      root.getChildren().add(additionalInfo);
    }
  }

  protected void setupTransitionDetails() {

  }
}
