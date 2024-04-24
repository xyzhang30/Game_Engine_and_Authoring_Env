package oogasalad.view.authoring_environment.panels;

import java.util.List;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

public class DefaultUIElementFactory implements UIElementFactory{
  @Override
  public Slider createSlider(String id, int width, int min, int max, int majorTickUnit) {
    Slider slider = new Slider();
    slider.setId(id);
    slider.setPrefWidth(width);
    slider.setMin(min);
    slider.setMax(max);
    slider.setValue(0);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(1);
    slider.setOrientation(Orientation.HORIZONTAL);

    return slider;
  }

  @Override
  public Button createButton(String id, String labelText, int width, int height) {
    Button button = new Button(labelText);
    button.setPrefSize(width, height);
    return button;
  }

  @Override
  public ComboBox<Class<? extends Enum<?>>> createComboBox(String id, List<Class<? extends Enum<?>>> items, String promptText, int width, int height) {
    ComboBox<Class<? extends Enum<?>>> comboBox = new ComboBox<>();
    comboBox.setId(id);
    comboBox.getItems().addAll(items);
    comboBox.setPromptText(promptText);
    comboBox.setPrefSize(width, height);
    return comboBox;
  }

  @Override
  public CheckComboBox<Class<? extends Enum<?>>> createCheckComboBox(String id, List<Class<? extends Enum<?>>> items, int width, int height) {
    CheckComboBox<Class<? extends Enum<?>>> checkComboBox = new CheckComboBox<>();
    checkComboBox.setId(id);
    checkComboBox.getItems().addAll(items);
    checkComboBox.setPrefSize(width, height);
    return checkComboBox;
  }

  @Override
  public TextField createTextField(String id, String labelText, int width, int height) {
    TextField textField = new TextField();
    textField.setId(id);
    textField.setPrefSize(width, height);
    return textField;
  }

  @Override
  public CheckBox createCheckBox(String id, int width, int height) {
    CheckBox checkBox = new CheckBox();
    checkBox.setId(id);
    checkBox.setPrefSize(width, height);
    return checkBox;
  }

  @Override
  public ListView<String> createListView(String id, int width, int height) {
    ListView<String> listView = new ListView<>();
    listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    listView.setPrefSize(width, height);
    return listView;
  }
}
