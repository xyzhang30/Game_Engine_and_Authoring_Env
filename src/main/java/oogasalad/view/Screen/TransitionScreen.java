package oogasalad.view.Screen;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Text;


public class TransitionScreen extends UIScreen {

  private final Group root;
  protected Text title;
  protected Text additionalInfo;

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
    title.setLayoutX(SCREEN_WIDTH / 2 - title.getBoundsInLocal().getWidth() / 2);
    title.setLayoutY(SCREEN_HEIGHT / 4);
    root.getChildren().add(title);
  }

  public void setAdditionalInfo(String info) {
    additionalInfo.setText(info);
    setToThemeFont(additionalInfo, 24);
    additionalInfo.setEffect(createDropShadow());
    additionalInfo.setLayoutX(SCREEN_WIDTH / 2 - additionalInfo.getBoundsInLocal().getWidth() / 2);
    additionalInfo.setLayoutY(SCREEN_HEIGHT / 3);
    if (!root.getChildren().contains(additionalInfo)) {
      root.getChildren().add(additionalInfo);
    }
  }

  protected void setupTransitionDetails() {

  }
}
