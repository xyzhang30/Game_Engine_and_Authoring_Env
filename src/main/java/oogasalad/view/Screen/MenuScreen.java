package oogasalad.view.Screen;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.view.Controller;

/**
 * Scene allows player to select from list of available games
 *
 * @author Jordan Haytaian
 */
public class MenuScreen extends UIScreen {

  private final Group root;

  public MenuScreen(List<String> titles, Controller controller) {
    root = new Group();
    createScene(titles);
    this.controller = controller;
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  private void createScene(List<String> titles) {
    createTitle();
    createMenu(titles);
  }

  private void createTitle() {
    Text title = new Text("Game Options");
    setToThemeFont(title, 100);
    title.setEffect(createDropShadow());

    title.setX(SCREEN_WIDTH/2 - title.getLayoutBounds().getWidth()/2);
    title.setY(SCREEN_HEIGHT/3 - title.getLayoutBounds().getHeight()/2);

    root.getChildren().add(title);
  }

  private void createMenu(List<String> titles) {
    ObservableList<String> observableList = FXCollections.observableList(titles);
    ListView<String> listView = new ListView<>(observableList);
    listView.setPrefSize(SCREEN_WIDTH - 800, SCREEN_HEIGHT - 400);
    listView.setLayoutX(400);
    listView.setLayoutY(300);
    addListViewEventHandling(listView);
    styleMenu(listView);
    root.getChildren().add(listView);
  }

  private void styleMenu(ListView<String> listView) {
    listView.setCellFactory(param -> new ListCell<String>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
        } else {
          setText(item);
          setFont(Font.font("Arial", 25));
          setAlignment(Pos.CENTER);
        }
      }
    });
  }

  private void addListViewEventHandling(ListView<String> listView) {
    listView.setOnMouseClicked(e -> {
      System.out.println("Clicked");
      String item = listView.getSelectionModel().getSelectedItem();
      if (item != null) {
        controller.startGamePlay(listView.getSelectionModel().getSelectedItem());
      }
    });
  }

}
