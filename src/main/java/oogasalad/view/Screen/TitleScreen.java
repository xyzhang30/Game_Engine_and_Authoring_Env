package oogasalad.view.Screen;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.Controller;
import oogasalad.view.SceneManager;

/**
 * Introductory screen when player first starts game, offers option to play or author
 * @author Jordan Haytaian
 */
public class TitleScreen extends UIScreen {
  private final Group root;
  public TitleScreen(Controller controller){
    this.controller = controller;
    root = new Group();
    createScene();
  }

  /**
   * Getter for root, used to change scenes
   * @return root node of scene
   */
  public Scene getScene(){
    return scene;
  }
  @Override
  public Parent getRoot() {
    return root;
  }
  private void createScene(){
    createTitle();
    createButtons();
    scene = new Scene(root, sceneWidth, sceneHeight);
  }

  private void createTitle(){
    double titleX = sceneWidth / 2 - 300;
    double titleY = sceneHeight / 5;
    Text title = new Text(titleX, titleY, "Fysics Fun");

    setToThemeFont(title, 100);
    title.setEffect(createDropShadow());

    root.getChildren().add(title);
  }

  private void createButtons() {
    double buttonX = sceneWidth / 2 - 300;
    double playTextX = sceneWidth / 2 - 125;
    double authorTextX = sceneWidth / 2 - 160;
    double playButtonY = sceneHeight / 3;
    double authorButtonY = sceneHeight * 2 / 3;
    double playTextY = sceneHeight / 2 - 50;
    double authorTextY = sceneHeight * 3 / 4 + 25;
    double buttonWidth = 500;
    double buttonHeight = 150;

    Rectangle playButton = new Rectangle(buttonX, playButtonY, buttonWidth, buttonHeight);
    playButton.setFill(Color.LIGHTGRAY);
    playButton.setEffect(createDropShadow());
    addPlayButtonEventHandling(playButton);

    Rectangle authorButton = new Rectangle(buttonX, authorButtonY, buttonWidth, buttonHeight);
    authorButton.setFill(Color.LIGHTGRAY);
    authorButton.setEffect(createDropShadow());

    Text play = new Text(playTextX, playTextY, "Play");
    setToThemeFont(play, 65);

    Text author = new Text(authorTextX, authorTextY, "Author");
    setToThemeFont(author, 65);

    root.getChildren().addAll(playButton, authorButton, play, author);
  }

  private void addPlayButtonEventHandling(Rectangle playButton){
    playButton.setOnMouseClicked(e -> controller.openMenuScreen());
  }

}
