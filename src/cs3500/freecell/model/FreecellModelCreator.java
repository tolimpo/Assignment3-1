package cs3500.freecell.model;

import cs3500.freecell.model.multimove.MultimoveModel;

public class FreecellModelCreator {

  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  public static FreecellModel<Card> create(GameType type) {
    switch (type) {
      case SINGLEMOVE:
        return new SimpleFreecellModel();
      case MULTIMOVE:
        return new MultimoveModel();
      default:
        throw new IllegalStateException("Default should not happen!");
    }
  }
}