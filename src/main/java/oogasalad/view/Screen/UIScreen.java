package oogasalad.view.Screen;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public abstract class UIScreen extends Pane{
  private final Screen screen = Screen.getPrimary();
  final double sceneWidth = getScreenWidth() - 100;
  final double sceneHeight = getScreenHeight() - 100;
  Scene scene;

  /**
   * Getter method for scene
   * @return scene specific to UIScreen subclass
   */
//  public Scene getScene(){
//    return scene;
//  }

  /**
   * Styles text to match theme font
   * @param text text to style
   * @param size font size of text
   */
  void setToThemeFont(Text text, int size){
    text.setFont(Font.font("Arial", FontWeight.BOLD, size));
    text.setFill(Color.BLACK);
  }

  /**
   * Creates dropshadow that can be added to visual elements
   * @return dropshadow to be added to visual elements
   */
  DropShadow createDropShadow(){
    DropShadow dropShadow = new DropShadow();
    dropShadow.setColor(Color.DARKGRAY);
    dropShadow.setRadius(5);
    dropShadow.setOffsetX(3);
    dropShadow.setOffsetY(3);
    return dropShadow;
  }

  private double getScreenWidth(){
    return screen.getBounds().getWidth();
  }

  private double getScreenHeight(){
    return screen.getBounds().getHeight();
  }

  void show() {

  }
  void hide() {

  }
  void update(double dt) {

  }
}
