package oogasalad.view;

import java.util.Arrays;
import java.util.List;
import oogasalad.model.api.GameElementProperties;

public enum GameElementType {
  ControllableElement(Arrays.asList(GameElementProperties.MOVABLE, GameElementProperties.COLLIDABLE,
      GameElementProperties.CONTROLLABLE)),
  NonControllableStationaryElement(List.of(GameElementProperties.COLLIDABLE)),
  NonControllableMovingElement(Arrays.asList(GameElementProperties.COLLIDABLE,
      GameElementProperties.MOVABLE));

  private final List<GameElementProperties> properties;

  GameElementType(List<GameElementProperties> properties) {
    this.properties = properties;
  }
}
