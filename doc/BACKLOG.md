### 6 Use Cases - Konur Nordberg

## Use Case 1: Loading a Pre-Designed Game

* Title: Load Existing Game from Configuration
* Actor: Actor: Player
* Description: The player selects and loads a pre-designed game using its unique identifier or name.
  The system reads the game's configuration from a JSON file and initializes the game state, ready
  for
  the player to begin or resume playing.

Steps:

1. Player opens the game library and browses for games.
2. Player selects a game based on name or thumbnail.
3. System retrieves the game's JSON configuration file.
4. System parses the JSON file and initializes the game state.
5. System displays the game's initial screen to the player.

## Use Case 2: Creating a New Game Element

* Title: Design New Game Element
* Actor: Game Designer
* Description: The game designer uses the Authoring Environment to create a new game
  element with custom properties like size, color, and physics attributes.

Steps:

1. Game designer selects the option to create a new game element.
2. Designer specifies properties such as size, color, and physical attributes.
3. Designer places the element in the game environment.
4. System updates the game's configuration file with the new element.

## Use Case 3: Defining Game Rules

* Title: Set Custom Game Rules
* Actor: Game Designer
* Description: The game designer defines custom game rules, such as scoring conditions or
  object interactions, which are stored in the game's configuration file.

Steps:

1. Game designer accesses the rules configuration section.
2. Designer sets specific conditions and outcomes for scoring and interactions.
3. System validates the rules for consistency and feasibility.
4. System saves the rules within the game's JSON configuration file.

## Use Case 4: Saving Game Progress

* Title: Save Player Game Progress
* Actor: Player
* Description: During gameplay, the player can save their current progress, which the system
  will store, allowing them to resume from the same state later.

Steps:

1. Player chooses to save the game from the pause menu.
2. System serializes the current game state to a savable format.
3. System writes the saved state to a persistent storage medium.
4. Player receives confirmation that the game progress has been saved.

## Use Case 5: Editing Game Environment

* Title: Modify Game Environment
* Actor: Game Designer
* Description: The game designer alters the game environment, such as changing backgrounds or
  layout, and saves these changes to be reflected in the game.

Steps:

1. Game designer opens the game environment editor.
2. Designer selects a new background image or alters the layout.
3. System shows a preview of the new environment.
4. Designer confirms changes, and the system updates the game configuration.

## Use Case 6: Multiplayer Game Session Initialization

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
