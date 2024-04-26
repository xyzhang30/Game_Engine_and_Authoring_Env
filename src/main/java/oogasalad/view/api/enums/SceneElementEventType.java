package oogasalad.view.api.enums;

/**
 * Represents an event type; used to specify which type of handler should be added to nodes
 *
 * @author Jordan Haytaian (Doga Ozmen)
 */
public enum SceneElementEventType {
  START_LANGUAGE,
  START_TITLE,
  START_MENU,
  START_AUTHORING,
  START_GAME,
  PAUSE,
  RESUME,
  SAVE,
  LOAD,
  POWER_HEIGHT,
  SET_POWER,
  SET_ANGLE,
  NEXT_ROUND,
  SET_ROUND,
  SET_TURN,
  SET_SCORE,
  CHANGE_THEME,
  NEW_GAME_WINDOW,

  USER_TEXT,
  PASSWORD_TEXT,
  LOGIN,
  CREATE_USER,
  HELP,
  SET_ENGLISH,
  SET_SPANISH,
  SET_FRENCH,
  START_LOGIN,
  LEADERBOARD
}
