<<<<<<< Updated upstream
## Use Cases

## Doga's Use Cases

### User Selects a Game Mode 
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

### User Interacts with Game Space
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


### User Encounters and Overcomes Obstacles
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


### User Aims and Shoots a Shot/Hit
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
    - The wall or paddle remains unaffected, preserving its immutability within the game environment.

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
     - If an inactive player clicks pause, or the player has used all 3 pauses, nothing will happen.
   - The objects stop moving while the pause splash screen is displayed
   - When the `resume` button is clicked, the objects will resume moving with the same 
     properties as before the pause.