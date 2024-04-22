package oogasalad.model.database;

import java.sql.Connection;
import java.sql.SQLException;
import oogasalad.model.gameengine.player.Player;

public class DataAccessObject {
  public Player getPlayer(int id) {
    try (Connection conn = DatabaseConfig.getConnection()) {
      // Use the connection to retrieve player data
      // ...
    } catch (SQLException e) {
      // Handle exceptions
      e.printStackTrace();
    }

    return null;
  }

}
