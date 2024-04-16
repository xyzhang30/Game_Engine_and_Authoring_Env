package oogasalad.view;
import javafx.scene.Node;

public class ElementStyleManager {

  public ElementStyleManager(){}

  public void styleElements(StyleType elementType, Node node){
    switch(elementType) {
      case TITLE_BUTTON -> node.getStyleClass().add(".title-button");
      case TITLE -> node.getStyleClass().add(".title");
    }
  }

}
