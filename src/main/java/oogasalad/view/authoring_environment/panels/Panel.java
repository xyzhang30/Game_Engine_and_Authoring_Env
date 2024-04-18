package oogasalad.view.authoring_environment.panels;

public interface Panel {

  void createElements() throws NoSuchFieldException;

  void handleEvents();

}
