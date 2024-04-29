package oogasalad.model.gameparser;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import javafx.scene.control.Alert.AlertType;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.view.Warning;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory for creating GameObjects
 *
 * @author Noah Loewy
 */
public class GameObjectFactory {

  private static final String filePath = "src/main/resources/model/gameObjects.properties";
  private static final Properties properties = new Properties();
  private static final Logger LOGGER = LogManager.getLogger(GameObjectFactory.class);
  private static final Warning WARNING = new Warning();

  private static void loadProperties() {
    try {
      properties.load(new FileInputStream(filePath));
    } catch (IOException e) {
      LOGGER.error("loading properties file error - " + e.getMessage());
      WARNING.showAlert(AlertType.ERROR, "loading properties file error", null, e.getMessage());
    }
  }

  public static GameObject createCollidable(GameObjectProperties co) {
    loadProperties();
    GameObject gameObject = new GameObject(co);
    co.properties().forEach(property -> {
      String methodName = properties.getProperty(property.toLowerCase());
      try {
        if (!methodName.isEmpty()) {
          Method method = GameObject.class.getMethod(methodName);
          method.invoke(gameObject);
        }
      } catch (Exception e) {
        LOGGER.warn(property + " is not a valid property");
      }
    });
    return gameObject;
  }
}
