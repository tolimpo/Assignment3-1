package cs3500.freecell.model.multimove;

import cs3500.freecell.model.AbstractPile;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.CascadePile;

public class MultimoveCascadePile extends CascadePile {

  protected void moveFrom(int sourceCardIndex, AbstractPile destinationPile) {
    if (this.stack.size() - 1 == sourceCardIndex) {
      destinationPile.moveTo(this.stack.get(sourceCardIndex), this);
    } else {
      throw new IllegalArgumentException("Given index not the end of pile, card cannot be moved!");
    }
  }

  protected void moveTo(Card cardInQuestion, AbstractPile sourcePile) {
    Card target = null;
    if (this.stack.size() != 0) {
      target = this.stack.get(this.stack.size() - 1);
    }
    if (this.validMove(cardInQuestion, target)) {
      sourcePile.remove(cardInQuestion);
      this.stack.add(cardInQuestion);
    } else {
      throw new IllegalArgumentException("Not a valid move!");
    }
  }

}
