package oogasalad.model.gameparser;

import java.util.List;

record StaticCheckRule(List<List<Object>> condition, List<List<Object>> action) {}
