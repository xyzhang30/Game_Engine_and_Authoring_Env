import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    try {
      // Create ObjectMapper instance
      ObjectMapper objectMapper = new ObjectMapper();

      // Create a JSON object to hold all fields
      JsonData jsonData = new JsonData();

      // Populate each field using separate methods
      populateGameName(jsonData);
      populateCollidableObjects(jsonData);
      populatePlayers(jsonData);
      populateVariables(jsonData);
      populateRules(jsonData);

      // Write the combined JSON object to a file
      objectMapper.writeValue(new File("output.json"), jsonData);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void populateGameName(JsonData jsonData) {
    // Set gameName field
    jsonData.setGameName("sampleMiniGolf");
  }

  private static void populateCollidableObjects(JsonData jsonData) {
    // Populate collidableObjects field
    List<CollidableObject> collidableObjects = new ArrayList<>();
    // Populate collidableObjects list with CollidableObject instances
    // ...

    jsonData.setCollidableObjects(collidableObjects);
  }

  private static void populatePlayers(JsonData jsonData) {
    // Populate players field
    List<Player> players = new ArrayList<>();
    // Populate players list with Player instances
    // ...

    jsonData.setPlayers(players);
  }

  private static void populateVariables(JsonData jsonData) {
    // Populate variables field
    List<Variables> variables = new ArrayList<>();
    // Populate variables list with Variables instances
    // ...

    jsonData.setVariables(variables);
  }

  private static void populateRules(JsonData jsonData) {
    // Populate rules field
    List<Rules> rules = new ArrayList<>();
    // Populate rules list with Rules instances
    // ...

    jsonData.setRules(rules);
  }
}
