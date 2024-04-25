package oogasalad.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.panels.PolicyPanel;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
import org.junit.jupiter.api.Test;

public class PolicyAuthoingTest {

  @Test
  public void Test(){
    PolicyPanel policyPanel = new PolicyPanel(new AuthoringProxy(), new ShapeProxy(), new AnchorPane(), new AnchorPane(), new StackPane(),
        uiElementFactory);
  }

}
