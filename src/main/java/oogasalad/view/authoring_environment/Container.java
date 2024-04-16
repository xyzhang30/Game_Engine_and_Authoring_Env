package oogasalad.view.authoring_environment;

import java.util.Arrays;
import java.util.List;
import oogasalad.view.authoring_environment.panels.Panel;

public class Container {
  private List<Panel> panels;
  public Container(Panel ... c) { // Lab Browser/Advanced
    panels = Arrays.asList(c);
  }
  public List<Panel> getPanels() {
    return panels;
  }
  public void setPanels(List<Panel> panels) {
    this.panels = panels;
  }

}
