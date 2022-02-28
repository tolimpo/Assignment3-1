package cs3500.freecell.model.multimove;

import cs3500.freecell.model.AbstractPile;
import cs3500.freecell.model.Card;

public class MultimoveCascadePile extends AbstractPile {
  @Override
  protected boolean validMove(Card mover, Card target) {
    return false;
  }
}
