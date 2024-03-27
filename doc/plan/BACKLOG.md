## Use Cases

### User Selects a Game Mode (Doga)
* The main menu presents options for different game modes (e.g., Pool, Air Hockey). 
* User interaction with the menu leads to the selection of a game mode, and the frontend loads the appropriate game interface.

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

### User Interacts with Game Space (Doga)
* The game space (field, table, etc.) is interactive, allowing for direct user actions like aiming and shooting. 
* The frontend captures user inputs (clicks, drags: for power of hit) and translates them into in-game actions.

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


### User Encounters and Overcomes Obstacles (Doga)
* The game dynamically presents obstacles within the game space.
* The user must navigate or interact with these obstacles, with the UI providing feedback on success or failure.
* This is testing the idea of maybe having an auditory compnent so that when the ball strikes an obstacle, a sound is played
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


### User Aims and Shoots a Shot/Hit (Doga)
* For games requiring aim and power mechanisms (like pool, and potentially mini golf and air hockey) , the UI provides controls for setting direction and strength.
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


### User Takes Turns in Multiplayer Mode (Doga)
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

### User Progresses Through Levels or Rounds (Doga)
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



