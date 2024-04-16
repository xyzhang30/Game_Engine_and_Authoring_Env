package oogasalad.view.authoring_environment.containers;

import java.util.Arrays;
import java.util.List;
import oogasalad.view.authoring_environment.panels.Panel;

public class Container {

  private final List<Panel> panels;
  public Container(Panel ... c) { // Lab Browser/Advanced
    panels = Arrays.asList(c);
  }
  public List<Panel> getPanels() {
    return panels;
  }

}
