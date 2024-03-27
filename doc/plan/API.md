# API

## Front End

### ControlSchemeApi (idea 1)
Manages the input.
```java
public interface ControlSchemeApi{
  
}
```

### InputBindApi (idea 2)
Associates an input from the user device to a command to be sent to the backend.
```java
public interface InputBindApi{
  void BindInput();
}
```


## Backend

### GameEngineExternal (Noah)
```java
interface GameEngineExternal {

  void start(int id);

  void pause();

  void resume();

  void update(int id1, int id2);

  void applyForce(double magnitude, double direction, int id);
  
  void reset();
}
```
