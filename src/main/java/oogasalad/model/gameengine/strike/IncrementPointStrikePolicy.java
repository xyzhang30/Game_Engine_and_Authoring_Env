package oogasalad.model.gameengine.strike;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;

/**
 * The IncrementPointStrikePolicy class is a concrete implementation of the StrikePolicy interface
 * which has a strike policy that increases the temporary score of a GameObject that is both
 * scoreable and strikeable.
 * <p>
 * This class utilizes the Optional type to handle cases where the strikeable might not have a
 * corresponding scoreable component. By using Optional, it avoids potential NullPointerExceptions
 * that might occur if attempting to directly invoke methods on a null reference. If the strikeable
 * is also a scoreable, the temporary score is incremented. Otherwise, no action is taken.
 */
@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class IncrementPointStrikePolicy implements StrikePolicy {


  /**
   * Returns a BiConsumer that defines the strike policy for a game engine. The BiConsumer takes an
   * integer representing the ID of the strikeable object and the game engine. It increments the
   * temporary score of the active player's strikeable object by 1.0, if it exists, or does nothing
   *
   * @return A BiConsumer representing the strike policy.
   */

  @Override
  public Consumer<GameEngine> getStrikePolicy() {
    return (engine) -> {
      Optional<Scoreable> optionalScoreable = engine.getPlayerContainer().getActive()
          .getStrikeable().asGameObject().getScoreable();
      optionalScoreable.ifPresent(scoreable -> scoreable.incrementTemporaryScore(1.0));
    };
  }
}
