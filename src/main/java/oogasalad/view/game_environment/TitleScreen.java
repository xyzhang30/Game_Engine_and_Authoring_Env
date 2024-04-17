package oogasalad.view.game_environment;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.view.controller.GameController;

/**
 * Introductory screen when player first starts game, offers option to play or author
 *
 * @author Jordan Haytaian
 */
public class TitleScreen extends UIScreen {

  private final Group root;

  public TitleScreen(GameController controller) {
    this.controller = controller;
    root = new Group();
    createScene();
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  private void createScene() {
    createTitle();
    createButtons();
  }

  private void createTitle() {
    Text title = new Text("Fysics Fun");
    setToThemeFont(title, 100);
    title.setEffect(createDropShadow());

    title.setX(SCREEN_WIDTH / 2 - title.getLayoutBounds().getWidth() / 2);
    title.setY(SCREEN_HEIGHT * 0.3 - title.getLayoutBounds().getHeight() / 2);

    root.getChildren().add(title);
  }

  private void createButtons() {

    Button playButton = new Button("Play");
    playButton.setPrefSize(SCREEN_WIDTH * 0.3, SCREEN_HEIGHT * 0.1);
    playButton.setLayoutX(SCREEN_WIDTH / 2 - playButton.getPrefWidth() / 2);
    playButton.setLayoutY(SCREEN_HEIGHT * 0.4 - playButton.getPrefHeight() / 2);
    playButton.setFont(Font.font("Arial", 65));
    playButton.setEffect(createDropShadow());

    playButton.setOnMouseClicked(e -> controller.openMenuScreen());

    Button authorButton = new Button("Author");
    authorButton.setPrefSize(SCREEN_WIDTH * 0.3, SCREEN_HEIGHT * 0.1);
    authorButton.setLayoutX(SCREEN_WIDTH / 2 - authorButton.getPrefWidth() / 2);
    authorButton.setLayoutY(SCREEN_HEIGHT * 0.6 - authorButton.getPrefHeight() / 2);
    authorButton.setFont(Font.font("Arial", 65));
    authorButton.setEffect(createDropShadow());

    authorButton.setOnMouseClicked(e -> controller.openAuthorEnvironment());

    root.getChildren().addAll(playButton, authorButton);
  }
}
