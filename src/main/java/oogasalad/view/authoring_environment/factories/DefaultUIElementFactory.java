package oogasalad.view.authoring_environment.factories;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.view.api.authoring.UIElementFactory;
import org.controlsfx.control.CheckComboBox;

/**
 * DefaultUIElementFactory is a factory class for creating the default version of various UI
 * elements for the authoring environment. Implements the UIElementFactory interface to provide
 * methods for creating UI components.
 *
 * @author Judy He
 */
public class DefaultUIElementFactory implements UIElementFactory {

  /**
   * Creates a horizontal container (HBox) with the specified spacing, width, height, and child
   * nodes.
   *
   * @param spacing the spacing between child nodes in the container
   * @param width   the preferred width of the container
   * @param height  the preferred height of the container
   * @param n       the child nodes to be added to the container
   * @return a configured HBox container
   */
  @Override
  public HBox createHContainer(int spacing, int width, int height, Node... n) {
    HBox hBox = new HBox(n);
    hBox.setPrefSize(width, height);
    hBox.setSpacing(spacing);
    return hBox;
  }

  /**
   * Creates a vertical container (VBox) with the specified spacing, width, height, and child
   * nodes.
   *
   * @param spacing the spacing between child nodes in the container
   * @param width   the preferred width of the container
   * @param height  the preferred height of the container
   * @param n       the child nodes to be added to the container
   * @return a configured VBox container
   */
  @Override
  public VBox createVContainer(int spacing, int width, int height, Node... n) {
    VBox vBox = new VBox(n);
    vBox.setPrefSize(width, height);
    vBox.setSpacing(spacing);
    return vBox;
  }

  /**
   * Creates a slider (Slider) with the specified parameters.
   *
   * @param id            the ID of the slider
   * @param width         the preferred width of the slider
   * @param min           the minimum value of the slider
   * @param max           the maximum value of the slider
   * @param majorTickUnit the distance between major tick marks
   * @return a configured Slider instance
   */
  @Override
  public Slider createSlider(String id, int width, int min, int max, int majorTickUnit) {
    Slider slider = new Slider();
    slider.setId(id);
    slider.setPrefWidth(width);
    slider.setMin(min);
    slider.setMax(max);
    slider.setValue(1);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(majorTickUnit);
    slider.setOrientation(Orientation.HORIZONTAL);

    return slider;
  }

  /**
   * Creates a button (Button) with the specified parameters.
   *
   * @param id        the ID of the button
   * @param labelText the text label of the button
   * @param width     the preferred width of the button
   * @param height    the preferred height of the button
   * @return a configured Button instance
   */
  @Override
  public Button createButton(String id, String labelText, int width, int height) {
    Button button = new Button(labelText);
    button.setPrefSize(width, height);
    return button;
  }

  /**
   * Creates a combo box (ComboBox) with the specified parameters.
   *
   * @param id         the ID of the combo box
   * @param items      the list of items to be added to the combo box
   * @param promptText the prompt text displayed when the combo box is empty
   * @param width      the preferred width of the combo box
   * @param height     the preferred height of the combo box
   * @return a configured ComboBox instance
   */
  @Override
  public <T> ComboBox<T> createComboBox(String id, List<T> items, String promptText, int width,
      int height) {
    ComboBox<T> comboBox = new ComboBox<>();
    comboBox.setId(id);
    comboBox.getItems().addAll(items);
    comboBox.setPromptText(promptText);
    comboBox.setPrefSize(width, height);
    return comboBox;
  }

  /**
   * Creates a check combo box (CheckComboBox) with the specified parameters.
   *
   * @param id     the ID of the check combo box
   * @param items  the list of items to be added to the check combo box
   * @param width  the preferred width of the check combo box
   * @param height the preferred height of the check combo box
   * @return a configured CheckComboBox instance
   */
  @Override
  public <T> CheckComboBox<T> createCheckComboBox(String id, List<T> items, int width, int height) {
    CheckComboBox<T> checkComboBox = new CheckComboBox<>();
    checkComboBox.setId(id);
    checkComboBox.getItems().addAll(items);
    checkComboBox.setPrefSize(width, height);
    return checkComboBox;
  }

  /**
   * Creates a text field (TextField) with the specified parameters.
   *
   * @param id     the ID of the text field
   * @param width  the preferred width of the text field
   * @param height the preferred height of the text field
   * @return a configured TextField instance
   */
  @Override
  public TextField createTextField(String id, int width, int height) {
    TextField textField = new TextField();
    textField.setId(id);
    textField.setPrefSize(width, height);
    return textField;
  }

  /**
   * Creates a checkbox (CheckBox) with the specified parameters.
   *
   * @param id     the ID of the checkbox
   * @param width  the preferred width of the checkbox
   * @param height the preferred height of the checkbox
   * @return a configured CheckBox instance
   */
  @Override
  public CheckBox createCheckBox(String id, int width, int height) {
    CheckBox checkBox = new CheckBox();
    checkBox.setId(id);
    checkBox.setPrefSize(width, height);
    return checkBox;
  }

  /**
   * Creates a list view (ListView) with the specified parameters.
   *
   * @param id     the ID of the list view
   * @param width  the preferred width of the list view
   * @param height the preferred height of the list view
   * @return a configured ListView instance
   */
  @Override
  public ListView<String> createListView(String id, int width, int height) {
    ListView<String> listView = new ListView<>();
    listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    listView.setPrefSize(width, height);
    return listView;
  }

  /**
   * Creates a popup window for specifying constant parameters.
   *
   * @param numParam the number of parameters expected
   * @param item     the name of the item to be specified
   * @return a list of integer values representing the specified parameters
   */
  @Override
  public List<Integer> createConstantParamsPopup(int numParam, String item) {
    Stage popupStage = new Stage();
    popupStage.setTitle("Specify Command Parameters");

    List<Integer> params = new ArrayList<>();

    Label label = new Label(item + ": (expected " + numParam + ")");
    VBox vbox = new VBox(label);

    List<TextArea> textAreas = new ArrayList<>();

    for (int i = 0; i < numParam; i++) {
      TextArea input = new TextArea();
      input.setId(String.valueOf(i));
      textAreas.add(input);
      vbox.getChildren().add(input);
    }

    Button confirmSaveParam = new Button("save");
    confirmSaveParam.setDisable(
        true); //confirm button shouldn't do anything before user enters all params

    for (TextArea area : textAreas) {
      //only allow users to enter digits and the decimal point
      area.addEventFilter(KeyEvent.KEY_TYPED, event -> {
        String character = event.getCharacter();
        if (!character.matches("[0-9.]")) {
          event.consume();
        }
      });
      //only enable the confirm save button when user has entered all required params
      area.textProperty().addListener((observable, oldValue, newValue) -> {
        boolean allFilled = textAreas.stream()
            .noneMatch(textArea -> textArea.getText().trim().isEmpty());
        confirmSaveParam.setDisable(!allFilled);
      });
    }

    confirmSaveParam.setOnAction(e -> {
      for (TextArea area : textAreas) {
        String text = area.getText();
        if (!text.isEmpty()) {
          try {
            Integer value = Integer.parseInt(text);
            params.add(value);
          } catch (NumberFormatException ex) {
            // Handle invalid input
            System.out.println("Invalid input: " + text);
          }
        }
      }
      popupStage.close();
    });

    vbox.getChildren().add(confirmSaveParam);

    Scene scene = new Scene(vbox, 500, 300);
    popupStage.setScene(scene);

    popupStage.setResizable(false);
    popupStage.showAndWait();

    return params;
  }
}
