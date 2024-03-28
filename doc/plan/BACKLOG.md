<<<<<<< Updated upstream

## Use Cases

## Doga's Use Cases

### User Selects a Game Mode

* The main menu presents options for different game modes (e.g., Pool, Air Hockey).
* User interaction with the menu leads to the selection of a game mode, and the frontend loads the
  appropriate game interface.

```java
public class MainMenuUI {

  public void displayGameOptions() {
    // Display game mode options on the screen
  }

  public void handleGameSelection(int selectedGameMode) {
    switch (selectedGameMode) {
      case POOL_GAME:
        loadPoolGameUI();
        break;
      case AIR_HOCKEY_GAME:
        loadAirHockeyGameUI();
        break;
      // Add cases for other games
    }
  }
}

```

### User Interacts with Game Space

* The game space (field, table, etc.) is interactive, allowing for direct user actions like aiming
  and shooting.
* The frontend captures user inputs (clicks, drags: for power of hit) and translates them into
  in-game actions.

```java
public class GameSpaceUI {

  public void initializeGameSpace() {
    // Set up interactive game space
  }

  public void handleUserInput() {
    // Translate user input into game actions
  }
}

```

### User Encounters and Overcomes Obstacles

* The game dynamically presents obstacles within the game space.
* The user must navigate or interact with these obstacles, with the UI providing feedback on success
  or failure.
* This is testing the idea of maybe having an auditory compnent so that when the ball strikes an
  obstacle, a sound is played

```java
public class ObstacleUI {

  public void displayObstacles() {
    // Render obstacles on the game space
  }

  public void handleCollisionWithObstacles() {
    // Provide visual and auditory feedback on collisions
  }
}

```

### User Aims and Shoots a Shot/Hit

* For games requiring aim and power mechanisms (like pool, and potentially mini golf and air
  hockey) , the UI provides controls for setting direction and strength.
  The system visualizes the aim path and responds to the execution command.

```java
public class ShotControllerUI {

  public void displayAimControls() {
    // Show aim direction and power adjustment UI
  }

  public void executeShot(double direction, double power) {
    // Visual feedback on the shot path and result
  }
}

```

### User Takes Turns in Multiplayer Mode

* For when we implement active multiplayer (ex: air hockey).
* The UI indicates the active player and turn status in multiplayer sessions.
* Controls are enabled or disabled based on the turn, ensuring players act at the correct times.

```java
public class TurnManagementUI {

  public void indicateActivePlayer(int playerId) {
    // Highlight the UI elements for the active player
  }

  public void toggleControlAccessibility(boolean isTurn) {
    // Enable or disable controls based on whether it's the user's turn
  }
}
```

### User Progresses Through Levels or Rounds

* The frontend displays level/round information, tracking progression within the selected game.
* Transitions between levels or rounds are handled and UI elements update accordingly.

```java
public class LevelProgressionUI {

  public void displayCurrentLevel(int level) {
    // Show current level or round info to the user
  }

  public void transitionToNextLevel() {
    // Animate and load the next level or round
  }
}

```

### Noah's Use Cases (nl190)

1. **During gameplay, when the golf ball collides with a wall or other solid collidable object.**

- **Actions:**
    - The collision should cause the golf ball to bounce off the wall or paddle, simulating a
      bounce.
    - The wall or paddle remains unaffected, preserving its immutability within the game
      environment.

2. **When the golf ball sufficiently enters the hole.**

- **Actions:**
    - The golf ball's motion halts as its velocity is reduced to zero.
    - The ball becomes invisible, representing its placement within the hole.
    - The player's score increments by one stroke, signifying successful completion.
    - The scoreboard updates to reflect the updated player scores.
    - The game progresses to the next hole or initiates the next player's turn.

3. **Following the execution of a stroke by a player, once the golf ball stops moving.**

- **Actions:**
    - The stroke contributes to the active player's score.
    - The scoreboard updates to reflect the change in scores.
    - The active player transitions to an inactive status.
    - The next player becomes active, initiating their turn in the gameplay sequence.

4. **When the golf ball collides with a water hazard during gameplay.**

- **Actions:**
    - The stroke is added to the active player's score.
    - The scoreboard updates to reflect the updated scores.
    - The golf ball's position is reset to its previous location.
    - The ball's velocity is reduced to zero, indicating a stoppage in motion.
    - The turn concludes, and the active status of players is adjusted accordingly.

5. **When a new player creates an account and joins a pre-existing league and plays his first
   league
   game.**

- **Actions:**
    - The player's username, password, and id is added to the `Players` database
    - The player's id is added to the `members` column of the `Leagues` database
    - The game results, referencing both player's ids, as well as the league's id, is added to
      the `Games` database, so it can be included in future standings queries.

6. **A User manually clicks the `pause` button and then later resumes the game with the `resume
   button`. The game designer, forseeing this, implemented a rule that the game cannot be paused
   by a user more than 3 times.
   does this.

- **Actions**:
    - When the paused button is clicked, one of two things can happen.
        - If the player who clicked pause is currently the active player, a splash screen will be
          shown to the user, with a `resume` option
        - If an inactive player clicks pause, or the player has used all 3 pauses, nothing will
          happen.
    - The objects stop moving while the pause splash screen is displayed
    - When the `resume` button is clicked, the objects will resume moving with the same
      properties as before the pause.

## 6 Use Cases - Konur Nordberg

# Use Case 1: Loading a Pre-Designed Game

* Title: Load Existing Game from Configuration
* Actor: Actor: Player
* Description: The player selects and loads a pre-designed game using its unique identifier or name.
  The system reads the game's configuration from a JSON file and initializes the game state, ready
  for the player to begin or resume playing.

Steps:

1. Player opens the game library and browses for games.
2. Player selects a game based on name or thumbnail.
3. System retrieves the game's JSON configuration file.
4. System parses the JSON file and initializes the game state.
5. System displays the game's initial screen to the player.

# Use Case 2: Creating a New Game Element

* Title: Design New Game Element
* Actor: Game Designer
* Description: The game designer uses the Authoring Environment to create a new game
  element with custom properties like size, color, and physics attributes.

Steps:

1. Game designer selects the option to create a new game element.
2. Designer specifies properties such as size, color, and physical attributes.
3. Designer places the element in the game environment.
4. System updates the game's configuration file with the new element.

# Use Case 3: Defining Game Rules

* Title: Set Custom Game Rules
* Actor: Game Designer
* Description: The game designer defines custom game rules, such as scoring conditions or
  object interactions, which are stored in the game's configuration file.

Steps:

1. Game designer accesses the rules configuration section.
2. Designer sets specific conditions and outcomes for scoring and interactions.
3. System validates the rules for consistency and feasibility.
4. System saves the rules within the game's JSON configuration file.

# Use Case 4: Saving Game Progress

* Title: Save Player Game Progress
* Actor: Player
* Description: During gameplay, the player can save their current progress, which the system
  will store, allowing them to resume from the same state later.

Steps:

1. Player chooses to save the game from the pause menu.
2. System serializes the current game state to a savable format.
3. System writes the saved state to a persistent storage medium.
4. Player receives confirmation that the game progress has been saved.

# Use Case 5: Editing Game Environment

* Title: Modify Game Environment
* Actor: Game Designer
* Description: The game designer alters the game environment, such as changing backgrounds or
  layout, and saves these changes to be reflected in the game.

Steps:

1. Game designer opens the game environment editor.
2. Designer selects a new background image or alters the layout.
3. System shows a preview of the new environment.
4. Designer confirms changes, and the system updates the game configuration.

# Use Case 6: Multiplayer Game Session Initialization

* Title: Initialize Multiplayer Game Session
* Actor: Player
* Description: Players can initiate or join a multiplayer game session. The system handles player
  connections, synchronizes game state, and maintains a consistent experience across devices.

Steps:

1. Host player sets up a new multiplayer game and invites others.
2. Invited players receive notifications and join the game.
3. System establishes a network connection between players' devices.
4. System synchronizes the game state across all devices.
5. All players see the game's starting state and begin playing simultaneously.
