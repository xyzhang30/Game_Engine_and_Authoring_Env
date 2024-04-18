package oogasalad.view.authoring_environment.panels;

import java.awt.Choice;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.nio.file.Path;
import java.util.Objects;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import oogasalad.model.annotations.ChoiceType;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.api.data.Rules;
import oogasalad.view.authoring_environment.authoring_screens.PolicyType;

public class PolicyPanel implements Panel{

  private final AuthoringProxy authoringProxy;
  private final StackPane canvas;
  private final AnchorPane rootPane;
  private final AnchorPane containerPane;

  public PolicyPanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane rootPane,
      AnchorPane containerPane, StackPane canvas) {
    this.authoringProxy = authoringProxy;
    this.rootPane = rootPane;
    this.containerPane = containerPane;
    this.canvas = canvas;
    createElements();
    handleEvents();
  }

  @Override
  public void createElements() {
    Field[] fields = PolicyType.class.getDeclaredFields();
    for (Field policyType : fields) {
      String policyLabel = String.join(" ",policyType.getName().split("_")) + ": ";
      System.out.println(policyLabel);

      if (policyType.isAnnotationPresent(ChoiceType.class)) {
        ChoiceType choiceTypeAnnotation = policyType.getAnnotation(ChoiceType.class);
//        System.out.println("annotation:" + choiceTypeAnnotation);
        createPolicySelectionDropdown(policyLabel, choiceTypeAnnotation.singleChoice());
      }
    }
  }

  private void createPolicySelectionDropdown(String policyNameLabel, boolean singleChoice) {
    System.out.println(new TextField(policyNameLabel));
    containerPane.getChildren().add(new TextField(policyNameLabel));
  }

  private Path getCommandPackage(String policyNameLabel) {
    return null;
  }


  @Override
  public void handleEvents() {

  }
}
