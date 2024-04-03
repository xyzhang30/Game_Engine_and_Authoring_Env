package oogasalad.view.Screen;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import oogasalad.view.SceneManager;

/**
 * Scene allows player to select from list of available games
 * @author Jordan Haytaian
 */
public class MenuScreen extends UIScreen {


  public MenuScreen(List<String> titles, SceneManager sceneManager){
    createScene(titles);
    this.sceneManager = sceneManager;
  }

  private void createScene(List<String> titles){
    StackPane stackPane = new StackPane();
    createTitle(stackPane);
    createMenu(stackPane, titles);
    scene = new Scene(stackPane, sceneWidth, sceneHeight);
  }

  private void createTitle(StackPane stackPane){
    double titleX = sceneWidth / 2 - 400;
    double titleY = sceneHeight / 5;
    Text title = new Text(titleX, titleY, "Game Options");

    setToThemeFont(title, 50);
    title.setEffect(createDropShadow());

    stackPane.getChildren().add(title);
  }

  private void createMenu(StackPane stackPane, List<String> titles){
    ObservableList<String> observableList = FXCollections.observableList(titles);
    ListView<String> listView = new ListView<>(observableList);
    listView.setPrefSize(sceneWidth - 300, sceneHeight - 400);
    listView.setLayoutX(100);
    listView.setLayoutY(300);
    stackPane.getChildren().add(listView);
  }

}
