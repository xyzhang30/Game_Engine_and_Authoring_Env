package oogasalad.model.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.List;
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
      /**

      new Database().registerUser("player1", "password1", "avatar1.png");
      new Database().registerUser("player2", "password1", "avatar1.png");
      new Database().registerUser("player3", "password1", "avatar1.png");
      new Database().registerUser("player4", "password1", "avatar1.png");
      new Database().registerGame("Game 1", "player1", 2, true);
      new Database().registerGame("Game 2", "player2", 2, true);
      new Database().registerGame("Game 3", "player3", 2, true);
      new Database().registerUser("player9", "password1", "avatar1.png");
      new Database().assignPermissionToPlayers("Game 1", List.of("player3","player2"),
          "None");
      int id = new Database().addGameInstance("Game 3");
      new Database().addGameScore(id,  "player1", 20, true);
      new Database().addGameScore(id,  "player2", 10, false);

      System.out.println(new Database().getGeneralHighScoresForGame("Game 3", 3));
      System.out.println(new Database().getPlayerHighScoresForGame("Game 2", "player2",
          3));
      System.out.println(new Database().getPlayableGameIds("player3", 2));
      System.out.println(new Database().getPlayableGameIds("player3", 1));
      System.out.println(new Database().loginUser("player3", ""));
      System.out.println(new Database().loginUser("player3", "password1"));
       **/
    }

  }

