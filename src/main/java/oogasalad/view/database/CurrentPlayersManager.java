package oogasalad.view.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * Manages the display and updates of current players in the game.
 * This class is responsible for handling the visual representation of the players' list in the user interface.
 *
 * @author Doga Ozmen
 */
public class CurrentPlayersManager {

  private Text playersDisplayTitle;
  private ListView<String> playersListView;

  /**
   * Constructor initializes the ListView and optionally the title Text.
   */
  public CurrentPlayersManager() {
    playersListView = new ListView<>();
    playersDisplayTitle = new Text("Current Players:");
  }

  /**
   * Sets the Text element for displaying the title above the players list.
   *
   * @param playersDisplayTitle Text element for displaying the title.
   */
  public void setPlayersDisplayTitle(Text playersDisplayTitle) {
    this.playersDisplayTitle = playersDisplayTitle;
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
   * Adds a new player's username to the ListView.
   *
   * @param username the username of the player to add.
   */
  public void saveUserInfo(String username) {
    ObservableList<String> items = playersListView.getItems();
    items.add(username);
    playersListView.setItems(items);
  }

}
