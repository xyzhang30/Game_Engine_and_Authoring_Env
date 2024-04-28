#### AUTHOR: Noah Loewy

#### DATE: 3/21/23

# General Backend Design Thoughts

## Similar Games:

- **Pool (8 ball, 9 ball), Billiards, Air Hockey, Mini Golf, 2D Soccer, Shuffleboard, Pinball,
  Bowling**

## Common Features:

### Balls and Moving Objects:

- **They all have Ball(s) or some moving object around the game space.**

### Game Space:

- **They all have a space the balls can roll on.**

### Obstacles:

- **They all (can) have obstacles that the ball(s) can inelastic off of. Balls can inelastic off each
  other.**

### Goals and Targets:

- **They all have holes, goals, pins, or items that when hit, cause an action to occur.**

### User-Controlled Agents:

- **Some have user-controlled agent(s) that can interact with the ball.**
    - Different Concrete Types of Agents:
        - Airhockey / soccer / pinball ==> agents just based on contact.
        - pool / billiard / mini golf / shuffleboard / bowling ==> agents require some sort of aim /
          power mechanism.

### Turn Mechanics:

- **They all have notions of a “turn” (in a broad sense). This will need to be an abstraction.**
    - In pool / billiards / golf / bowling / shuffle ball, a turn can be characterized as one shot (
      can have multiple in a row depending on result like in pool / bowling).
    - In 2D soccer / air hockey, the notion of a turn can be the nonstop play until a point is
      scored.

### Player Control:

- **They all have different player(s) that can control different things, and can only act at certain
  times when it is their “turn”.**

### Game Progression:

- **Some have “levels / frames / rounds” within a game.**
    - Golf / Bowling.

### Point Systems and Win Conditions:

- **They all have different point systems and win conditions, which we should try to abstract as
  best we can.**

## Additional Cool Features:

(I'd love to work on this kind of stuff if we get the games working quickly)

- Online Playing
- User Log In
- User Stats/Leaderboard in Database
