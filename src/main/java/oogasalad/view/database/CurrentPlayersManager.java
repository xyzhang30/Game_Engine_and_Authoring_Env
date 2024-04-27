package oogasalad.view.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * Manages the display and updates of current players logged in into the game.
 * This class is responsible for handling the visual representation of the players' list in the user interface.
 *
 * @author Doga Ozmen
 */
public class CurrentPlayersManager {

  private ListView<String> playersListView;


  public void setPlayersListView(ListView<String> playersListView){
    this.playersListView = playersListView;
  }


  /**
   * Gets the ListView for displaying current players.
   *
   * @return ListView for displaying players.
   */
  public ListView<String> getPlayersListView() {
    return playersListView;
  }

  /**
   * Updates the ListView with the current players by adding a new username.
   * This method assumes the username and password have been verified and the username should be added to the list.
   *
   * @param username the username of the player to add
   */
  public void saveUserInfo(String username) {
    if (playersListView != null) {
      ObservableList<String> items = playersListView.getItems();
      items.add(username);
      playersListView.setItems(items);
    }
  }

}
