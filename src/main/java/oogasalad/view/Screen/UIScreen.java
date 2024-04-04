package oogasalad.view.Screen;

import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import oogasalad.view.Controller;

public abstract class UIScreen {

  private final Screen screen = Screen.getPrimary();
  final double sceneWidth = screen.getBounds().getWidth() - 100;
  final double sceneHeight = screen.getBounds().getHeight() - 100;
  Controller controller;

  public abstract Parent getRoot();

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
