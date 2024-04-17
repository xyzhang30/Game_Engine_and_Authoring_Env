package oogasalad.view.authoring_environment.authoring_screens.AuthoringHandlers;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UIControlFactory {

  /**
   * Creates a button with predefined styles and an action event.
   *
   * @param text     the text to display on the button.
   * @param fontSize the font size of the text.
   * @param width    the width of the button.
   * @param height   the height of the button.
   * @param action   the action to perform on button click.
   * @return the created Button.
   */
  public static Button createButton(String text, int fontSize, double width, double height,
      Runnable action) {
    Button button = new Button(text);
    button.setFont(Font.font(fontSize));
    button.setPrefSize(width, height);
    button.setOnAction(e -> action.run());
    return button;
  }

  /**
   * Creates a color picker with a default color.
   *
   * @param defaultColor the default color of the color picker.
   * @return the created ColorPicker.
   */
  public static ColorPicker createColorPicker(String defaultColor) {
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setValue(javafx.scene.paint.Color.valueOf(defaultColor));
    return colorPicker;
  }

  /**
   * Creates a slider with predefined settings.
   *
   * @param min       the minimum value of the slider.
   * @param max       the maximum value of the slider.
   * @param value     the initial value of the slider.
   * @param showTicks whether to show tick marks.
   * @param onChange  the action to perform when the slider value changes.
   * @return the created Slider.
   */
  public static Slider createSlider(double min, double max, double value, boolean showTicks,
      Runnable onChange) {
    Slider slider = new Slider(min, max, value);
    slider.setShowTickLabels(showTicks);
    slider.setShowTickMarks(showTicks);
    slider.valueProperty().addListener((observable, oldValue, newValue) -> onChange.run());
    return slider;
  }

  /**
   * Creates a vertical box (VBox) container with predefined padding and spacing for layout
   * purposes.
   *
   * @param spacing the spacing between elements in the VBox.
   * @param padding the padding inside the VBox.
   * @return the created VBox.
   */
  public static VBox createVBox(double spacing, Insets padding) {
    VBox vBox = new VBox(spacing);
    vBox.setPadding(padding);
    return vBox;
  }
}
