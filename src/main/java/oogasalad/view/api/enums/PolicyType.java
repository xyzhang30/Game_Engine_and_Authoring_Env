package oogasalad.view.api.enums;

import oogasalad.model.annotations.AvailableCommands;
import oogasalad.model.annotations.ChoiceType;

public enum PolicyType {
  @ChoiceType(singleChoice = false) @AvailableCommands(commandPackage = "command") ADVANCE_TURN,
  @ChoiceType(singleChoice = false) @AvailableCommands(commandPackage = "command") ADVANCE_ROUND,
  @ChoiceType(singleChoice = true) @AvailableCommands(commandPackage = "condition") ROUND_POLICY,
  @ChoiceType(singleChoice = true) @AvailableCommands(commandPackage = "turn") TURN_POLICY,
  @ChoiceType(singleChoice = true) @AvailableCommands(commandPackage = "strike") STRIKE_POLICY,
  @ChoiceType(singleChoice = true) @AvailableCommands(commandPackage = "condition") WIN_CONDITION,
  @ChoiceType(singleChoice = true) @AvailableCommands(commandPackage = "rank") RANK_COMPARATOR,
  @ChoiceType(singleChoice = true) @AvailableCommands(commandPackage = "checkstatic") STATIC_CHECKER
}
