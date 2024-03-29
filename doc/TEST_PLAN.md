### Testing Plan

## Testability Strategies

1. Interface Segregation and Modular Design: By designing smaller, well-defined interfaces and
   classes, you make isolated testing easier. This approach allows for mocking or stubbing
   dependencies,
   facilitating unit tests for specific behaviors or interactions without needing the entire
   application context.
2. Exception Handling: Clearly defined custom exceptions for different error conditions
   allow for precise testing of failure scenarios. Implementing comprehensive error reporting
   mechanisms makes it possible to assert specific error conditions in your tests, improving
   reliability and debuggability.

## Test Scenarios (28 total, 4 per team member, we worked together on most)

1. Loading a Pre-Designed Game (Happy Path)
    * Action: User selects a game from a list and chooses to load it.
    * Expected Outcome: The game loads successfully, initializing the game state as defined in the
      game's JSON configuration file.
    * Testing Support: The GameParser.parse method can be called with a valid file path, expecting
      it to return a correctly populated GameObjectRecord. Mocking or using a predefined JSON file
      helps verify correct parsing and loading.

2. Creating a New Game Element with Invalid Parameters (Sad Path)
    * Action: Game Designer tries to create a new game element with invalid size parameters (e.g.,
      negative dimensions).
    * Expected Outcome: The system throws an InvalidInputException.
    * Testing Support: Call GameBuilder.createGame with a GameInfoRecord containing invalid size
      parameters. Assert that InvalidInputException is thrown, indicating validation works as
      expected.

3. Defining Custom Game Rules (Happy Path)
    * Action: Game Designer defines a custom scoring rule where colliding with a specific object
      awards points.
    * Expected Outcome: The game updates scores according to the custom rule during gameplay.
    * Testing Support: Utilize the ReflectiveFactory to instantiate a CollisionHandler that modifies
      the game's score. Simulate a collision in a test environment and assert the score update.

4. Multiplayer Game Session Initialization Failure (Sad Path)
    * Action: Player attempts to start a multiplayer game without network connectivity.
    * Expected Outcome: The system reports an error indicating the multiplayer session could not be
      initiated.
    * Testing Support: Simulate a network failure and call GameEngineExternal.start with a
      multiplayer game ID. Assert the system throws or returns an error related to network
      connectivity.

5. Saving Game Progress Successfully (Happy Path)
    * Action: Player chooses to save their current game progress.
    * Expected Outcome: Game state is successfully saved, allowing the player to resume from this
      point later.
    * Testing Support: Invoke GameEngineExternal.pause followed by a save function (to be
      implemented) passing current game state. Verify that the game state is saved correctly by
      checking the persistence layer directly or by loading the saved state in a new session.

6. Applying an Unreasonable Force to a Collidable (Sad Path)
    * Action: Game logic attempts to apply a force magnitude to a collidable that exceeds the
      defined game physics limits.
    * Expected Outcome: The system either clamps the value to a maximum allowed force or throws an
      exception for an invalid operation.
    * Testing Support: Call GameEngineInternal.GameManager.applyInitialVelocity with an excessive
      force magnitude and verify that an exception is thrown or the force is capped.

7. Editing Game Environment Successfully (Happy Path)
    * Action: Game Designer changes the game environment's background.
    * Expected Outcome: The new background is displayed when the game is next loaded or refreshed.
    * Testing Support: Use the GameBuilder.createGame method to update the game environment settings
      and then load the game. Assert the game loads with the new background by checking the returned
      GameRecord or observing the UI directly in an integration test.

8. Invalid File Path for Game Parsing (Sad Path)
    * Action: Attempt to parse a game configuration with a non-existent file path.
    * Expected Outcome: The system throws an InvalidFileException.
    * Testing Support: Call GameParser.parse with an invalid file path and assert that
      InvalidFileException is thrown, validating error handling for file operations.

9. Apply Initial Velocity Correctly (Happy Path)
    * Action: A player's character is given an initial push in a certain direction.
    * Expected Outcome: The character moves according to the specified velocity and direction.
    * Testing Support: Utilize GameEngineInternal.GameManager.applyInitialVelocity and assert the
      character's new position matches expected values.

10. Collision Between Non-collidable Objects (Sad Path)
    * Action: Two objects, marked as non-collidable, come into contact.
    * Expected Outcome: No collision event is triggered.
    * Testing Support: Simulate movement that causes overlap between two non-collidable objects and
      verify that no collision handling logic is executed.

11. Loading Nonexistent Game (Sad Path)
    * Action: User attempts to load a game with an ID that does not exist in the system.
    * Expected Outcome: An error message is displayed or an exception is thrown, indicating the game
      could not be found.
    * Testing Support: Call GameEngineExternal.start with a non-existent game ID and expect a
      specific error response or exception.

12. Successful Game Pause (Happy Path)
    * Action: Player pauses the game during play.
    * Expected Outcome: The game state is paused, stopping all movements and actions effectively.
    * Testing Support: Invoke GameEngineExternal.pause and assert the game enters a paused state,
      which could involve checking a state flag or observing halted game object movements.

13. Resuming a Game Fails Due to Uninitialized State (Sad Path)
    * Action: Attempt to resume a game that was never started or paused.
    * Expected Outcome: The system prevents the action and signals an error or warning to the user.
    * Testing Support: Without initiating or pausing a game, call GameEngineExternal.resume and
      expect an error or state verification.

14. Multiplayer Session Creation with Maximum Players (Happy Path)
    * Action: Host player creates a multiplayer session with the maximum allowed number of players.
    * Expected Outcome: The session is successfully created, and all players can join.
    * Testing Support: Simulate creating a multiplayer game with the max player limit and verify
      successful session creation and player joining.

15. Exceeding Maximum Players for Multiplayer Session (Sad Path)
    * Action: An attempt is made to add more players to a multiplayer session than the maximum
      allowed.
    * Expected Outcome: The additional player(s) cannot join, and an appropriate message is
      displayed or returned.
    * Testing Support: Simulate adding one more player than allowed to a session and assert that the
      operation fails as expected.

16. Saving Game Without Any Progress (Sad Path)
    * Action: Player attempts to save the game immediately after starting, without making any
      progress.
    * Expected Outcome: The system either prevents the save operation or saves the initial game
      state without error.
    * Testing Support: Invoke the save operation at the game's start and verify successful operation
      or prevention logic.

17. Reset Game to Initial State Successfully (Happy Path)
    * Action: During gameplay, the player chooses to reset the game back to its initial state.
    * Expected Outcome: The game resets, with all elements returned to their starting positions and
      conditions.
    * Testing Support: Call GameEngineExternal.reset during an active game session and assert that
      the game state matches the initial conditions.

18. Invalid Collision Handler Configuration (Sad Path)
    * Action: A collision handler is configured with invalid parameters, leading to an unexecutable
      lambda expression.
    * Expected Outcome: The system throws a configuration error or fails gracefully without applying
      the collision logic.
    * Testing Support: Attempt to create a CollisionHandler with invalid configurations and expect
      failure handling.

19. Display Active Players in a Multiplayer Game (Happy Path)
    * Action: In a multiplayer game, the current active players' list is requested to be displayed.
    * Expected Outcome: The UI updates to show a list or icons representing all active players.
    * Testing Support: Invoke GameViewExternal.PlayerViewManager.displayActivePlayers and check for
      UI changes reflecting the active players.

20. Load Game with Corrupted Configuration File (Sad Path)
    * Action: Attempt to load a game whose configuration file has become corrupted or is unreadable.
    * Expected Outcome: The loading process fails with an informative error message about the file
      issue.
    * Testing Support: Call GameParser.parse with a path to a corrupted file and assert that
      InvalidFileException or a similar error is thrown.

21. Dynamically Change Game Background (Happy Path)
    * Action: Game designer decides to update the game's background dynamically based on a specific
      in-game event.
    * Expected Outcome: The background changes seamlessly to the new image without interrupting
      gameplay.
    * Testing Support: Trigger the in-game event and verify that the background updates by checking
      the renderer or UI state.

22. Apply Force to Static Object (Sad Path)
    * Action: An attempt is made to apply a force to an object marked as static, which should not
      move.
    * Expected Outcome: The system ignores the force application to the static object, maintaining
      its position.
    * Testing Support: Use GameEngineInternal.GameManager.applyInitialVelocity on a static object
      and verify it remains unmoved.

23. Player Achieves High Score (Happy Path)
    * Action: During gameplay, a player achieves a new high score.
    * Expected Outcome: The high score is updated, displayed to the player, and saved for future
      sessions.
    * Testing Support: Simulate the scoring conditions, check for UI update with the new score, and
      verify the score is persisted.

24. Player Uses Undefined Game Object 
    * Action: A player tries to interact with a game object that has not been defined or is missing
      from the game configuration.
    * Expected Outcome: The game handles the error gracefully, either by ignoring the action or
      informing the player of the issue.
    * Testing Support: Trigger interaction with an undefined object and assert appropriate error
      handling or feedback is provided.

25. Update Game with New Element Successfully (Happy Path)
    * Action: Game designer adds a new interactive element to the game environment.
    * Expected Outcome: The new element appears in the game and interacts as configured.
    * Testing Support: After adding the new element, load the game and assert the presence and
      functionality of the element.

26. Removing a Player from an Active Game (Sad Path)
    * Action: Attempt to remove a player from an ongoing multiplayer game session.
    * Expected Outcome: The system prevents the removal, or if removal is allowed, handles it
      without disrupting the session for remaining players.
    * Testing Support: Simulate the removal operation and verify session continuity or prevention
      logic.

27. Game Element Reacts to User Input (Happy Path)
    * Action: A player interacts with a game element (e.g., clicks on an object).
    * Expected Outcome: The object reacts accordingly (e.g., moves, changes color, or triggers an
      event).
    * Testing Support: Simulate the interaction and verify the object's response by checking its
      state or observing UI changes.

28. Incorrect Game Logic Scripting (Sad Path)
    * Action: A game designer scripts custom game logic with syntax errors or logical flaws.
    * Expected Outcome: The system validates the script and returns feedback on errors, preventing
      the flawed logic from affecting gameplay.
    * Testing Support: Attempt to load the flawed script into the game and assert that validation
      catches the errors and provides feedback.





