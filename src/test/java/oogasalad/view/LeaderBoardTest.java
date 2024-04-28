package oogasalad.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.scene_management.scene_element.scene_element_handler.DatabaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
public class LeaderBoardTest extends DukeApplicationTest{

  private DatabaseHandler databaseHandler;
  private DatabaseController mockDatabaseController;
  private ListView<String> listView;

}
