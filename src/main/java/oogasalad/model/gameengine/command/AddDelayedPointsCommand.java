package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Optional;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;

public class AddDelayedPointsCommand implements Command {

  private final List<Double> arguments;

  public AddDelayedPointsCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) {
    GameObject c = engine.getCollidableContainer().getGameObject((int) Math.round(arguments.get(0)));
    Optional<Scoreable> optionalOwnable = c.getScoreable();
    optionalOwnable.ifPresent(ownable -> ownable.setTemporaryScore(arguments.get(1)));
  }

}
