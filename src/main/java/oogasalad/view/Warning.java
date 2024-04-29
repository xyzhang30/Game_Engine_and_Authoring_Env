package oogasalad.view;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Warning {

  public static void showAlert(Scene scene, AlertType alertType, String title, String headerText,
      String message) {
    Alert alert = new Alert(alertType);
    alert.initOwner(scene.getWindow());
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public static void showAlert(Alert.AlertType alertType, String title, String headerText,
      String contentText) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    alert.showAndWait();
  }

}
