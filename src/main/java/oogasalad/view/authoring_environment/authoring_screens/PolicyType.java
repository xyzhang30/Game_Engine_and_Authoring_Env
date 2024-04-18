package oogasalad.view.authoring_environment.authoring_screens;

import oogasalad.model.annotations.AvailableCommands;
import oogasalad.model.annotations.ChoiceType;

public enum PolicyType {
  @ChoiceType(singleChoice = false) @AvailableCommands(commandPackage = "statichandlers") ADVANCE_TURN,
  @ChoiceType(singleChoice = false) @AvailableCommands(commandPackage = "statichandlers") ADVANCE_ROUND,
  @ChoiceType(singleChoice = true) @AvailableCommands(commandPackage = "condition") ROUND_POLICY,
  @ChoiceType(singleChoice = true) @AvailableCommands(commandPackage = "turn") TURN_POLICY,
  @ChoiceType(singleChoice = true) @AvailableCommands(commandPackage = "strike") STRIKE_POLICY,
  @ChoiceType(singleChoice = true) @AvailableCommands(commandPackage = "condition") WIN_CONDITION
}
