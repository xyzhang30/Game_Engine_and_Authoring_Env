package oogasalad.view.Screen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * A pane that contains control buttons for the game such as Pause and Save.
 *
 * @author Doga Ozmen
 */
public class ControlPane extends HBox {

  private Button pauseButton;
  private Button saveButton;

  public ControlPane() {
    initializeButtons();
    layoutButtons();
  }

  private void initializeButtons() {
    // Initialize pause button
    pauseButton = new Button("Pause");
    pauseButton.setFocusTraversable(false);
    pauseButton.setOnAction(e -> handlePause());

    // Initialize save button
    saveButton = new Button("Save");
    saveButton.setFocusTraversable(false);
    saveButton.setOnAction(e -> handleSave());
  }

  private void layoutButtons() {
    this.setPadding(new Insets(5));
    this.setSpacing(10); // Horizontal layout with spacing of 10
    this.setStyle("-fx-background-color: #999999"); // Pull format from data
    this.getChildren().addAll(pauseButton, saveButton);
  }

  private void handlePause() {
    // this will be changed to actual pause logic
    System.out.println("Pause button clicked");
  }

  private void handleSave() {
    // this will be changed to actual save logic
    System.out.println("Save button clicked");
  }
}
