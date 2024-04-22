package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;

public class EightBallCommand implements Command {

  private final List<Double> arguments;
  @ExpectedParamNumber(value = 1, paramDescription = {"(double) game object ID of 8 ball"})
  public EightBallCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  @Override
  public void execute(GameEngine engine) {
    engine.getGameObjectContainer().getGameObject((int) Math.round(arguments.get(0))).setVisible(false);
    int active = engine.getPlayerContainer().getActive();
    engine.getGameObjectContainer().toStaticState();
    boolean didActiveWin =
        engine.getPlayerContainer().getPlayer(active).areAllScoreablesInvisible();
    for (PlayerRecord pr : engine.getPlayerContainer().getPlayerRecords()) {
      engine.getPlayerContainer().getPlayer(pr.playerId())
          .applyGameResult((active == pr.playerId()) == didActiveWin);
    }
  }
}
