# API ideas (tentative/discarded)

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

### GameDisplayApi
Manages a playable game and encapsulates the graphics. //Maybe separate the graphics from the logic for Single Responsibility
```java
public interface GameDisplayApi{
  Pane getDisplayPane();
  void update();
}
```

### AuthoringEnvironmentApi
Manages the authoring environment window.
```java
public interface AuthoringEnvironmentApi{
  Pane getAuthoringPane();
}
```
