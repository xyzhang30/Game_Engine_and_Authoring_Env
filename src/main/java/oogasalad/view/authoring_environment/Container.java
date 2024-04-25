package oogasalad.view.authoring_environment;

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

  public Container(Panel... c) { // Lab Browser/Advanced
    panels = Arrays.asList(c);
  }

  public List<Panel> getPanels() {
    return panels;
  }

  public void setPanels(List<Panel> panels) {
    this.panels = panels;
  }

}
