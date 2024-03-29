# Example Games

## Games
### Game 1: air hockey
* Objects
  * puck
  * 2 goals
  * 2 controllers
  * "table"
  * walls
  * players 
* Functionality
  * When a game/round starts, the players control their respective controllers to makes contact with the puck and the puck slides on the table
  * The puck is reset to the middle every time after it goes into one of the goals
  * A point is added to the player who shot the puck into the goal
  * The game ends when a certain amount of time is reach, or when a player gets to a certain amount of points, etc., depending on the user-specified rules.

### Game 2: mini golf
* Objects
  * ball
  * 
* Functionality


## Explanation
#### Commonalities:
Air hockey and mini golf are both physics-based games that involves
* player-controllable object that applies a force/gives an initial velocity to the ball
* collision detection and event-handling after specific collisions
* the concept of turn/round/game and switching player permissions according to the current state of the game

#### Functional differences:
* In air hockey, both users will always have control permissions; in golf, permissions for each player changes according to who's turn it is
* In air hockey, the order of active players is constant (both players are always active), in mini golf, it is determined during game play based on the game status and state of each player