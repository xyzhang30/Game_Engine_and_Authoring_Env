package oogasalad.view.authoring_environment.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
/**
 * The TextFieldListener class implements a ChangeListener to monitor changes in text fields associated
 * with game object attributes in the authoring environment. When the text in a monitored field changes,
 * the listener updates the corresponding attribute in the game object.
 *
 * @author Judy He
 */
public class TextFieldListener implements ChangeListener<String> {
  private final String gameObjectField;
  private final ShapeProxy shapeProxy;

  /**
   * Constructs a TextFieldListener instance.
   *
   * @param gameObjectField The field name representing the game object attribute to be updated.
   * @param shapeProxy      The ShapeProxy instance associated with the game object.
   */
  public TextFieldListener(String gameObjectField, ShapeProxy shapeProxy) {
    this.gameObjectField = gameObjectField;
    this.shapeProxy = shapeProxy;
  }

  /**
   * Handles changes to the observed text field and updates the corresponding attribute in the game
   * object accordingly.
   *
   * @param observable The observed text field.
   * @param oldValue   The previous value of the text field.
   * @param newValue   The new value of the text field.
   */
  @Override
  public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    // TODO: FIX DESIGN (NO SWITCH)
    if (!newValue.isEmpty() && isNumeric(newValue)) {
      switch (gameObjectField) {
        case "mass" -> shapeProxy.getGameObjectAttributesContainer().setMass(Double.parseDouble(newValue));
        case "sFriction" -> shapeProxy.getGameObjectAttributesContainer().setsFriction(Double.parseDouble(newValue));
        case "kFriction" -> shapeProxy.getGameObjectAttributesContainer().setkFriction(Double.parseDouble(newValue));
        case "controllableXSpeed" -> shapeProxy.getGameObjectAttributesContainer().setControllableXSpeed(Integer.parseInt(newValue));
        case "controllableYSpeed" -> shapeProxy.getGameObjectAttributesContainer().setControllableYSpeed(Integer.parseInt(newValue));
      }
    }
  }

  private boolean isNumeric(String strNum) {
    if (strNum == null) {
      return false;
    }
    try {
      double d = Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }
}


