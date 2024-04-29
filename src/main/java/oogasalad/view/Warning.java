package oogasalad.view;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Warning {

  public void showAlert(Scene scene, AlertType alertType, String title, String headerText,
      String message) {
    Alert alert = new Alert(alertType);
    alert.initOwner(scene.getWindow());
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
