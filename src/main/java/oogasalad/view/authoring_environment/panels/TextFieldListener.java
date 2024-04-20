package oogasalad.view.authoring_environment.panels;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
    switch (gameObjectField) {
      case "elasticity" ->  shapeProxy.getGameObjectAttributesContainer().setElasticity(Double.parseDouble(newValue));
      case "mass" -> shapeProxy.getGameObjectAttributesContainer().setMass(Double.parseDouble(newValue));
      case "sFriction" -> shapeProxy.getGameObjectAttributesContainer().setsFriction(Double.parseDouble(newValue));
      case "kFriction" -> shapeProxy.getGameObjectAttributesContainer().setkFriction(Double.parseDouble(newValue));
    }
  }
}
