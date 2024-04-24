package oogasalad.view.authoring_environment.panels;

import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.controlsfx.control.CheckComboBox;

public interface UIElementFactory {
  Slider createSlider(String id, int width, int min, int max, int majorTickUnit);
  Button createButton(String id, String labelText, int width, int height);
  ComboBox<Class<? extends Enum<?>>> createComboBox(String id, List<Class<? extends Enum<?>>> items, String promptText, int width, int height);
  CheckComboBox<Class<? extends Enum<?>>> createCheckComboBox(String id, List<Class<? extends Enum<?>>> items, int width, int height);
  TextField createTextField(String id, String labelText, int width, int height);
  CheckBox createCheckBox(String id, int width, int height);
  ListView<String> createListView(String id, int width, int height);

}
