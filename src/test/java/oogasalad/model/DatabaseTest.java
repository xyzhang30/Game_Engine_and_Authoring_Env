package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import oogasalad.model.database.DataCreateObject;
import oogasalad.model.database.DatabaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class DatabaseTest {

  private DatabaseConfig dbConfig;

  @BeforeEach
  public void setUp() {
    dbConfig = new DatabaseConfig();

  }

    @Test
    public void testCheckGameExists() throws SQLException {
      new DataCreateObject().registerUser("player1", "password1", "avatar1.png");
      new DataCreateObject().registerUser("player2", "password1", "avatar1.png");
      new DataCreateObject().registerUser("player3", "password1", "avatar1.png");
      new DataCreateObject().registerUser("player4", "password1", "avatar1.png");
      new DataCreateObject().registerGame("Game 1", "player1", 2, true);
      new DataCreateObject().registerGame("Game 2", "player2", 2, true);
      new DataCreateObject().registerGame("Game 3", "player3", 2, true);
      new DataCreateObject().registerUser("player9", "password1", "avatar1.png");
      new DataCreateObject().assignPermissionToPlayers("Game 1", List.of("player3","player2"),
          "None");
      int id = new DataCreateObject().addGameInstance("Game 3");
      new DataCreateObject().addGameScore(id,  "player1", 20, true);
      new DataCreateObject().addGameScore(id,  "player2", 10, false);
    }


  }

