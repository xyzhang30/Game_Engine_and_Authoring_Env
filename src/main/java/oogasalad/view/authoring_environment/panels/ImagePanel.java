package oogasalad.view.authoring_environment.panels;

import java.io.File;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;

public class ImagePanel implements Panel {

  private final ShapeProxy shapeProxy;
  private final AuthoringProxy authoringProxy;
  private final AnchorPane containerPane;
  private Button imageButton;
  private static final String RESOURCE_FOLDER_PATH = "view.";
  private static final String UI_FILE_PREFIX = "UIElements";
  private final String language = "English"; // PASS IN LANGUAGE
  private final ResourceBundle resourceBundle;

  public ImagePanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane containerPane) {
    this.containerPane = containerPane;
    this.authoringProxy = authoringProxy;
    this.shapeProxy = shapeProxy;
    this.resourceBundle = ResourceBundle.getBundle(
        RESOURCE_FOLDER_PATH + UI_FILE_PREFIX + language);
    createElements();
    handleEvents();
  }

  @Override
  public void createElements() {
    imageButton = new Button(resourceBundle.getString("imageButton"));
    imageButton.setId("imageButton");
    imageButton.setPrefSize(200, 100);
    AnchorPane.setTopAnchor(imageButton, 160.0);
    AnchorPane.setRightAnchor(imageButton, 50.0);
    containerPane.getChildren().add(imageButton);

  }

  @Override
  public void handleEvents() {
    imageButton.setOnAction(event -> {
      String relativePath = chooseImage();
      if (relativePath != null && shapeProxy.getShape() != null) {
        shapeProxy.getGameObjectAttributesContainer().setColor(null);
        shapeProxy.getGameObjectAttributesContainer().setImagePath(relativePath);
        String imgPath = Paths.get(relativePath).toUri().toString();
        shapeProxy.getShape().setFill(new ImagePattern(new Image(imgPath)));
      }
    });

  }

  private String chooseImage() {
    FileChooser fileChooser = new FileChooser();

    File initialDirectory = new File("data/");
    fileChooser.setInitialDirectory(initialDirectory);

    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
    );
    File file = null;
    while (file == null) {
      file = fileChooser.showOpenDialog(new Stage());
    }
    return file.getPath();
  }
}
