# API

## Front End

### ControlSchemeApi (idea 1) (Kevin, Jordan, Doga)
Manages the input.
```java
public interface ControlSchemeApi{
  
}
```

### InputBindApi (idea 2) (Kevin, Doga)
Associates an input from the user device to a command to be sent to the backend.
```java
public interface InputBindApi{
  void BindInput();
}
```


## Backend

### GameEngineExternal (Noah)
```java
interface GameEngineExternal implements StaticStateListener {

  void start(int id);

  void pause();

  void resume();

  void update(int id1, int id2);

  void applyForce(double magnitude, double direction, int id);
  
  void reset();

}
```

### GameEngineInternal(Noah)
```java
interface CollidableObjects {

  boolean isStateStatic();

  void update();
}

interface Collidable {
  void setVisible();
  void onForceApplied(double magnitude, double direction);
  void onCollision(Collidable other);
  double getX();
  double getY();
  double getVelocityx();
  double getVelocityy();
  double getMass();
}

interface StaticStateHandler {
  boolean isCondition();
  StaticStateHandler getNextHandler();
}

interface PlayerManager {
  void changeTurn(); //will need to incorporate some sort of turn policy ==> not fleshed out yet
  Player getPlayer(int id);
  ObservableList<Integer> getActiveIds();
}

interface Player {
  double getScore();
  void setScore(double score);
  int getSubturn();
  void setSubturn(int subturn);
}

interface Permissions {
  boolean isAllowed(int playerid, int gameState);
}
```

### Database External (Noah)
```java

public interface DatabaseAPI {
    void insertGameResult(String gameType, int winnerId, int loserId, int points, int 
        opponentPoints, Date timeOfMatch);
    List<Integer> getStandings(String leagueName); // For retrieving standings from the database
}


```
