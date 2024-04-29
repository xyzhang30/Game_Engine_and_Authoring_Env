package oogasalad.view.api.authoring;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

/**
 * Interface representing a factory for creating various UI elements in the authoring environment.
 * Implementing classes should provide concrete behavior for these methods to create specific UI
 * elements.
 *
 * @ Judy He
 */
public interface UIElementFactory {

  /**
   * Creates a horizontal container with specified spacing, width, height, and child nodes.
   *
   * @param spacing the spacing between child nodes.
   * @param width   the width of the container.
   * @param height  the height of the container.
   * @param n       the child nodes to be added to the container.
   * @return an HBox container with the specified attributes and child nodes.
   */
  HBox createHContainer(int spacing, int width, int height, Node... n);

  /**
   * Creates a vertical container with specified spacing, width, height, and child nodes.
   *
   * @param spacing the spacing between child nodes.
   * @param width   the width of the container.
   * @param height  the height of the container.
   * @param n       the child nodes to be added to the container.
   * @return a VBox container with the specified attributes and child nodes.
   */
  VBox createVContainer(int spacing, int width, int height, Node... n);

  /**
   * Creates a slider with specified parameters.
   *
   * @param id            the identifier for the slider.
   * @param width         the width of the slider.
   * @param min           the minimum value for the slider.
   * @param max           the maximum value for the slider.
   * @param majorTickUnit the major tick unit for the slider.
   * @return a slider with the specified attributes.
   */
  Slider createSlider(String id, int width, int min, int max, int majorTickUnit);

  /**
   * Creates a button with specified parameters.
   *
   * @param id        the identifier for the button.
   * @param labelText the text label for the button.
   * @param width     the width of the button.
   * @param height    the height of the button.
   * @return a button with the specified attributes.
   */
  Button createButton(String id, String labelText, int width, int height);

  /**
   * Creates a combo box with specified parameters.
   *
   * @param id         the identifier for the combo box.
   * @param items      the list of items to be displayed in the combo box.
   * @param promptText the prompt text to be displayed in the combo box.
   * @param width      the width of the combo box.
   * @param height     the height of the combo box.
   * @return a combo box with the specified attributes.
   */
  <T> ComboBox<T> createComboBox(String id, List<T> items, String promptText, int width,
      int height);

  /**
   * Creates a check combo box with specified parameters.
   *
   * @param id     the identifier for the check combo box.
   * @param items  the list of items to be displayed in the check combo box.
   * @param width  the width of the check combo box.
   * @param height the height of the check combo box.
   * @return a check combo box with the specified attributes.
   */
  <T> CheckComboBox<T> createCheckComboBox(String id, List<T> items, int width, int height);

  /**
   * Creates a text field with specified parameters.
   *
   * @param id     the identifier for the text field.
   * @param width  the width of the text field.
   * @param height the height of the text field.
   * @return a text field with the specified attributes.
   */
  TextField createTextField(String id, int width, int height);

  /**
   * Creates a checkbox with specified parameters.
   *
   * @param id     the identifier for the checkbox.
   * @param width  the width of the checkbox.
   * @param height the height of the checkbox.
   * @return a checkbox with the specified attributes.
   */
  CheckBox createCheckBox(String id, int width, int height);

  /**
   * Creates a list view with specified parameters.
   *
   * @param id     the identifier for the list view.
   * @param width  the width of the list view.
   * @param height the height of the list view.
   * @return a list view with the specified attributes.
   */
  ListView<String> createListView(String id, int width, int height);

  /**
   * Creates a constant parameters popup window allowing user to enter values for a specified number
   * of parameters.
   *
   * @param numParam the number of parameters to be entered by the user.
   * @param item     the name of the item for which the parameters are being entered.
   * @return a list of integers representing the entered parameters.
   */
  List<Integer> createConstantParamsPopup(int numParam, String item);

  List<Integer> getParams(Button confirmSaveParam, List<TextArea> textAreas,
      List<Integer> params, Stage popupStage, VBox vbox);

}
