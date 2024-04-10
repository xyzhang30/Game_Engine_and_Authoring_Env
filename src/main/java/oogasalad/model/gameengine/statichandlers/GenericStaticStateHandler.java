package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GenericStaticStateHandler implements StaticStateHandler{


  protected static final Logger LOGGER = LogManager.getLogger(GenericStaticStateHandler.class);

  private GenericStaticStateHandler next;
  private GenericStaticStateHandler prev;

  public void handle(GameEngine engine, RulesRecord rules) {
    if(canHandle(engine, rules)) {
      handleIt(engine,rules);
    }
    else if (next != null){
        next.handle(engine, rules);
   }
  }

  protected void setNext(GenericStaticStateHandler h) {
    next = h;
  }
  protected void setPrev(GenericStaticStateHandler h) {
    prev = h;
  }

  protected GenericStaticStateHandler getNext() {
    return next;
  }
  protected GenericStaticStateHandler getPrev() {
    return prev;
  }
  public abstract boolean canHandle(GameEngine engine, RulesRecord rules);
  public abstract void handleIt(GameEngine engine, RulesRecord rules);

  protected String toLogForm(Object o) {
      return o.toString().substring(o.toString().lastIndexOf(".") + 1,
          o.toString().lastIndexOf("@"));
    }
  }

