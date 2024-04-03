package oogasalad.view.Screen;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.SceneManager;

/**
 * Introductory screen when player first starts game, offers option to play or author
 * @author Jordan Haytaian
 */
public class TitleScreen extends UIScreen {

  public TitleScreen(SceneManager sceneManager){
    this.sceneManager = sceneManager;
    createScene();
  }

  private void createScene(){
    StackPane stackPane = new StackPane();
    createTitle(stackPane);
    createButtons(stackPane);
    scene = new Scene(stackPane, sceneWidth, sceneHeight);
  }

  private void createTitle(StackPane stackPane){
    double titleX = sceneWidth / 2 - 400;
    double titleY = sceneHeight / 5;
    Text title = new Text(titleX, titleY, "Fysics Fun");

    setToThemeFont(title, 50);
    title.setEffect(createDropShadow());

    stackPane.getChildren().add(title);
  }

  private void createButtons(StackPane stackPane) {
    double buttonX = sceneWidth / 2 - 400;
    double textX = sceneWidth / 2 - 450;
    double playButtonY = sceneHeight / 2;
    double authorButtonY = sceneHeight * 3 / 4;
    double playTextY = sceneHeight / 2 - 50;
    double authorTextY = sceneHeight * 3 / 4 - 50;
    double buttonWidth = 200;
    double buttonHeight = 50;

    Rectangle playButton = new Rectangle(buttonX, playButtonY, buttonWidth, buttonHeight);
    playButton.setEffect(createDropShadow());
    addPlayButtonEventHandling(playButton);

    Rectangle authorButton = new Rectangle(buttonX, authorButtonY, buttonWidth, buttonHeight);
    authorButton.setEffect(createDropShadow());

    Text play = new Text(textX, playTextY, "Play");
    setToThemeFont(play, 25);

    Text author = new Text(textX, authorTextY, "Author");
    setToThemeFont(author, 25);
  }

  private void addPlayButtonEventHandling(Rectangle playButton){
    playButton.setOnMouseClicked(e -> sceneManager.setScreenTypeMenu());
  }

}
