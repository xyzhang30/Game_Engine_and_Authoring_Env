package oogasalad.view.Screen;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * A pane that contains control buttons for the game such as Pause and Save.
 *
 * @author Doga Ozmen
 */
public class ControlPane extends Pane {
  private Button pauseButton;
  private Button saveButton;

  public ControlPane() {
    initializeButtons();
    layoutButtons();
  }

  private void initializeButtons() {
    // Initialize pause button
    pauseButton = new Button("Pause");
    pauseButton.setOnAction(e -> handlePause());

    // Initialize save button
    saveButton = new Button("Save");
    saveButton.setOnAction(e -> handleSave());
  }

  private void layoutButtons() {
    HBox buttonLayout = new HBox(10); // Horizontal layout with spacing of 10
    buttonLayout.getChildren().addAll(pauseButton, saveButton);
    this.getChildren().add(buttonLayout); // Add the layout to the ControlPane
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
