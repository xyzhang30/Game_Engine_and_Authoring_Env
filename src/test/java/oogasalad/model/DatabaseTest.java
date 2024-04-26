package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import oogasalad.model.database.Database;
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
      System.out.println(new Database().getGeneralHighScoresForGame("Game 3", 3));
      System.out.println(new Database().getPlayerHighScoresForGame("Game 2", "player2",
          3));
      System.out.println(new Database().getPlayableGameIds("player3", 2));
      System.out.println(new Database().getPlayableGameIds("player3", 1));
      System.out.println(new Database().loginUser("player3", ""));
      System.out.println(new Database().loginUser("player3", "password1"));
    }

  }

