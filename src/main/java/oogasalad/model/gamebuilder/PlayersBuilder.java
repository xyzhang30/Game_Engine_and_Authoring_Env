package oogasalad.model.gamebuilder;

import java.util.List;
import oogasalad.model.api.GameBuilder;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.data.ParserPlayer;

public class PlayersBuilder implements GameBuilder {

  @Override
  public <T> void buildGameField(GameData gameData, List<T> gameField) {
    List<ParserPlayer> players = (List<ParserPlayer>) gameField;
    gameData.setPlayers(players);
  }
}
