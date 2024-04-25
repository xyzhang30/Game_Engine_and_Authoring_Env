package oogasalad.model.database;

import java.util.Arrays;
import java.util.List;

public class MockDataInserter {

  public static void main(String[] args) {
    DataCreateObject dataCreateObject = new DataCreateObject();

    // Create mock players
    dataCreateObject.registerUser("player1", "password1", "avatar1.png", "English", 20, "[]", 100, "[]");
    dataCreateObject.registerUser("player2", "password2", "avatar2.png", "Spanish", 25, "[]", 100, "[]");

    // Create mock games
    dataCreateObject.createGame(1, "Mock Game 1");
    dataCreateObject.createGame(1, "Mock Game 2");

    // Assume you have a method in DataCreateObject that creates games.
    // Retrieve the player IDs and game IDs as needed, typically this would be retrieved from the database
    // For the purposes of this mock data insertion, we're assuming the IDs
    List<Integer> playerIds = Arrays.asList(1, 2); // The IDs should match the actual IDs from INSERT operations
    int gameId = 1; // The ID should match the actual ID from INSERT operations

    // Assign permissions
    dataCreateObject.assignPermissionToPlayers(gameId, playerIds, "PLAY");
  }
}
