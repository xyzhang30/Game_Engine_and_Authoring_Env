package oogasalad.view.Screen;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 * Introductory screen when player first starts game, offers option to play or author
 * @author Jordan Haytaian
 */
public class TitleScreen extends UIScreen {
  private Scene scene;

  public TitleScreen(){
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

    Rectangle authorButton = new Rectangle(buttonX, authorButtonY, buttonWidth, buttonHeight);
    authorButton.setEffect(createDropShadow());

    Text play = new Text(textX, playTextY, "Play");
    setToThemeFont(play, 25);

    Text author = new Text(textX, authorTextY, "Author");
    setToThemeFont(author, 25);
  }

}
