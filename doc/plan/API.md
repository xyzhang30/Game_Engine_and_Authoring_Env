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

NOTE: Move everything to api folder