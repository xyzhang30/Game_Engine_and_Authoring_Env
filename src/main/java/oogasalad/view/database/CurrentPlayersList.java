package oogasalad.view.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class CurrentPlayersList {

  //scene manager makes it and passes it to the handler
  private ObservableList<String> players = FXCollections.observableArrayList();
  private ListView<String> listView = new ListView<>(players);

  public void saveUserInfo(String username) {
    players.add(username);
  }

  public ListView<String> getListView() {
    return listView;
  }
}
