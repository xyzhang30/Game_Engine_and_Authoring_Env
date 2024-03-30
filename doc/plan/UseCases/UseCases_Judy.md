# Judy's Use Cases
1. Configuring a new game
- **Steps**
    1. User defines the parameters of the new game through dropdown menus.
    2. User add any objects/obstacles to the new game through a drag/drop mechanism
    3. View calls implementations of GameBuilder (following the Builder design pattern) to create JSON objects for policies, conditions, collidables and players.
    4. New JSON is created for the new game
    5. Model parses the new JSON file to configure new game in the backend
2. Mini golf: when golf ball goes into hole
- **Steps**
    1. View detects collision between Collidables hole and golf ball, identified by their ids.
    2. View passes collision and ids of Collidables to GameEngine
    3. Based on the game configuration, the GameEngine process the activity and returns the correct response to the View

3. Load pre-configured game
- Steps
    1. View calls GameParser to parse existing JSON file for configuring a new game
    2. GameParser calls GameEngine to configure the backend states for the new game

4. Air hockey: Attempt to move puck pass half line
- Steps
    1. View detects "collision" between half line and puck and passes the collision and colliding entities to GameEngine by id.
    2. Given collision and types of the Collidables identified by their id, the GameEngine follows pre-defined policies to return the correct response to View.
    3. View disables functionality for moving the puck further forward pass the half line

5. Mini golf: golf is shot out of bound or into water
- Steps
    1. View detects collision between game environment boundary/water and golf ball, passing the collision and colliding entities to GameEngine by id.
    2. Given collision and types of the Collidables identified by their id, the GameEngine follows pre-defined policies to return the correct response to View.
    3. GameEngine resets the game in the backend.
    4. View resets the game for the frontend (player, golfball back to starting position).

6. Air hockey: Player A scores a goal.
- Steps
    1. View detects collision between player B's goal and air hockey, passing the collision and colliding entities to GameEngine by id.
    2. Given collision and types of the Collidables identified by their id, the GameEngine follows pre-defined policies to return the correct response to View.
    3. GameEngine updates player A's score and checks win and turn conditions.
    4. GameEngine resets the game in the backend.
    5. If a win condition has been reached, the View generates new splash screen displaying the game result. Otherwise, View updates player A's score and resets the game for the frontend (pucks, hockey back to starting position).