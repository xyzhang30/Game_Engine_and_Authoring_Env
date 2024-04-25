package oogasalad.view.authoring_environment.panels;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

public interface UIElementFactory {
  HBox createHContainer(int spacing, int width, int height, Node...n);
  VBox createVContainer(int spacing, int width, int height, Node...n);
  Slider createSlider(String id, int width, int min, int max, int majorTickUnit);
  Button createButton(String id, String labelText, int width, int height);
  <T> ComboBox<T> createComboBox(String id, List<T> items, String promptText, int width, int height);
  <T> CheckComboBox<T> createCheckComboBox(String id, List<T> items, int width, int height);
  TextField createTextField(String id, int width, int height);
  CheckBox createCheckBox(String id, int width, int height);
  ListView<String> createListView(String id, int width, int height);

}
