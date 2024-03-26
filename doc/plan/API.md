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