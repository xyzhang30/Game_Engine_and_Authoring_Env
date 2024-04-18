package oogasalad.view.authoring_environment.authoring_screens;

import oogasalad.model.annotations.ChoiceType;

public enum PolicyType {
  @ChoiceType(singleChoice = true) ADVANCE_TURN,
  @ChoiceType(singleChoice = true) ADVANCE_ROUND,
  @ChoiceType(singleChoice = true) ROUND_POLICY,
  @ChoiceType(singleChoice = true) TURN_POLICY,
  @ChoiceType(singleChoice = true) STRIKE_POLICY,
  @ChoiceType(singleChoice = true) WIN_CONDITION
}
