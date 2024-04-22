package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.player.Player;

@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class EightBallCommand implements Command {


    private final List<Double> arguments;

    /**
     * Constructs an instance of AdvanceTurnCommand with a list of arguments. This constructor does
     * not actually do anything, and exists for the sake of consistency across commands.
     *
     * @param arguments, an empty list
     */

    @ExpectedParamNumber(1)
    public EightBallCommand(List<Double> arguments) {
      this.arguments = arguments;
    }



    @Override
  public void execute(GameEngine engine) {
    GameObject eight =
        engine.getGameObjectContainer().getGameObject((int) Math.round(arguments.get(0)));
    eight.setVisible(false);
    Player activePlayer =
        engine.getPlayerContainer().getPlayer(engine.getPlayerContainer().getActive());
    eight.getScoreable().ifPresent(scoreable -> scoreable.setTemporaryScore(activePlayer.areAllScoreablesInvisible()? 100: -100));

  }
}
