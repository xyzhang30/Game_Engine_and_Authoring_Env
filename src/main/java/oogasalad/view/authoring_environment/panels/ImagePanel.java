package oogasalad.view.authoring_environment.panels;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImagePanel implements Panel {

  private final ShapeProxy shapeProxy;
  private final AuthoringProxy authoringProxy;
  private final AnchorPane containerPane;
  private Button imageButton;

  public ImagePanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane containerPane) {
    this.containerPane = containerPane;
    this.authoringProxy = authoringProxy;
    this.shapeProxy = shapeProxy;
    createElements();
    handleEvents();
  }

  @Override
  public void createElements() {
    imageButton = new Button("Image");
    imageButton.setId("imageButton");
    imageButton.setPrefSize(200, 100);
    AnchorPane.setTopAnchor(imageButton, 160.0);
    AnchorPane.setRightAnchor(imageButton, 50.0);
    containerPane.getChildren().add(imageButton);

  }

  @Override
  public void handleEvents() {
    imageButton.setOnAction(event -> {
      String relativePath = chooseImage((Text) containerPane.lookup("#titleText"));
      if (relativePath != null && shapeProxy.getShape() != null) {
        authoringProxy.addImage(shapeProxy.getShape(), relativePath);
        System.out.println(relativePath);
        String imgPath = Paths.get(relativePath).toUri().toString();
        shapeProxy.getShape().setFill(new ImagePattern(new Image(imgPath)));
      }
    });

  }

  private String chooseImage(Text shapeType) {
    String type = shapeType.getText();
    FileChooser fileChooser = new FileChooser();

    File initialDirectory = new File(getImageFolder(type));
    fileChooser.setInitialDirectory(initialDirectory);

    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
    );
    File file = fileChooser.showOpenDialog(new Stage());
    return initialDirectory + FileSystems.getDefault().getSeparator() + file.getName();
  }

  private String getImageFolder(String type) {
    switch (type.toUpperCase()) {
      case "BACKGROUND" -> {
        return "data/background_images";
      }
      case "NON-CONTROLLABLE" -> {
        return "data/noncontrollable_images";
      }
      case "CONTROLLABLE" -> {
        return "data/controllable_images";
      }
      default -> {
        return "data/";
      }
    }
  }
}
