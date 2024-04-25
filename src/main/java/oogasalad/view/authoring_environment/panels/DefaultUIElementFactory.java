package oogasalad.view.authoring_environment.panels;

import java.util.List;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

public class DefaultUIElementFactory implements UIElementFactory{

  @Override
  public HBox createHContainer(int spacing, int width, int height, Node... n) {
    HBox hBox = new HBox(n);
    hBox.setPrefSize(width, height);
    hBox.setSpacing(spacing);
    return hBox;
  }

  @Override
  public VBox createVContainer(int spacing,int width, int height, Node... n) {
    VBox vBox = new VBox(n);
    vBox.setPrefSize(width, height);
    vBox.setSpacing(spacing);
    return vBox;
  }

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
  public <T> ComboBox<T> createComboBox(String id, List<T> items, String promptText, int width, int height) {
    ComboBox<T> comboBox = new ComboBox<>();
    comboBox.setId(id);
    comboBox.getItems().addAll(items);
    comboBox.setPromptText(promptText);
    comboBox.setPrefSize(width, height);
    return comboBox;
  }

  @Override
  public <T> CheckComboBox<T> createCheckComboBox(String id, List<T> items, int width, int height) {
    CheckComboBox<T> checkComboBox = new CheckComboBox<>();
    checkComboBox.setId(id);
    checkComboBox.getItems().addAll(items);
    checkComboBox.setPrefSize(width, height);
    return checkComboBox;
  }

  @Override
  public TextField createTextField(String id, int width, int height) {
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
