package oogasalad.view.game_environment;

import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import oogasalad.view.Window;
import oogasalad.view.controller.GameController;

public abstract class UIScreen {

  double SCREEN_WIDTH = Window.SCREEN_WIDTH;
  double SCREEN_HEIGHT = Window.SCREEN_HEIGHT;
  GameController controller;

  public abstract Parent getRoot();

  public void setScreen(double width, double height) {
    SCREEN_WIDTH = width;
    SCREEN_HEIGHT = height;
  }

  /**
   * Styles text to match theme font
   *
   * @param text text to style
   * @param size font size of text
   */
  void setToThemeFont(Text text, int size) {
    text.setFont(Font.font("Arial", FontWeight.BOLD, size));
    text.setFill(Color.BLACK);
  }

  /**
   * Creates dropshadow that can be added to visual elements
   *
   * @return dropshadow to be added to visual elements
   */
  DropShadow createDropShadow() {
    DropShadow dropShadow = new DropShadow();
    dropShadow.setColor(Color.DARKGRAY);
    dropShadow.setRadius(5);
    dropShadow.setOffsetX(3);
    dropShadow.setOffsetY(3);
    return dropShadow;
  }

  void update(double dt) {

  }
}
