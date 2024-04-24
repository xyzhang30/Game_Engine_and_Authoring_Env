package oogasalad.view.authoring_environment.panels;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;

public class TextFieldListener implements ChangeListener<String> {
  private final String gameObjectField;
  private final ShapeProxy shapeProxy;

  public TextFieldListener(String gameObjectField, ShapeProxy shapeProxy) {
    this.gameObjectField = gameObjectField;
    this.shapeProxy = shapeProxy;
  }

  // TODO: FIX DESIGN (NO SWITCH)
  @Override
  public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
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


