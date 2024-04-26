package oogasalad.view.authoring_environment.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import oogasalad.view.api.authoring.Panel;
/**
 * Represents a container that holds a list of panels within the authoring environment.
 * It is used to manage and organize panels as part of the user interface.
 *
 * @author Judy He
 */
public class Container {

  private List<Panel> panels;

  /**
   * Constructs a container with a given list of panels.
   *
   * @param p A list of Panel objects to be set in the container.
   */
  public Container(Panel... p) { // Lab Browser/Advanced
    panels = Arrays.asList(p);
  }

  /**
   * Sets the list of panels for the container.
   *
   * @param panels A list of Panel objects to be set in the container.
   */
  public void setPanels(List<Panel> panels) {
    this.panels = new ArrayList<>();
    this.panels = panels;
  }

}
