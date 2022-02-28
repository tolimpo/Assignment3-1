package cs3500.freecell.model.multimove;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.CascadePile;

public class MultimoveCascadePile extends CascadePile {

  @Override
  protected boolean validMove(Card mover, Card target) {
    return false;
  }
}
