package oogasalad.model.gameengine.command;

import java.util.List;
import java.util.Optional;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.Ownable;

public class AddDelayedPointsCommand implements Command {

  private final List<Double> arguments;

  public AddDelayedPointsCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) {
    Collidable c = engine.getCollidableContainer().getCollidable((int) Math.round(arguments.get(0)));
    Optional<Ownable> optionalOwnable = c.getOwnable();
    optionalOwnable.ifPresent(ownable -> ownable.setTemporaryScore(arguments.get(1)));
  }

}
