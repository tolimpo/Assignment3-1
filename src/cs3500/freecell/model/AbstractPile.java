package cs3500.freecell.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstraction of piles, as all piles need
 * the fields and methods included in this class.
 */
abstract class AbstractPile {
  protected List<Card> stack = new ArrayList<>();

  /*
  Removed a bunch of useless methods that operated on the stack in the class,
  added getStack() so that the stack can be operated outside the class.
  I did this to clean up the code.
   */

  List<Card> getStack() {
    return this.stack;
  }

  // This has been changed to allow moving builds.
  void moveFrom(int sourceCardIndex, AbstractPile destinationPile) {
    if (sourceCardIndex == this.stack.size() - 1) {
      destinationPile.moveTo(this.stack.get(sourceCardIndex), this);
    } else {
      destinationPile.moveBuildTo(sourceCardIndex, this.stack);
    }
  }

  /*
  This allows the functionality of SimpleFreecellModel to remain the
  same, but can be overridden for multimove.
   */
  protected void moveBuildTo(int startCardIndex, List<Card> sourcePile) {
    throw new IllegalArgumentException("Given index not the end of pile, card cannot be moved!");
  }

  void moveTo(Card cardInQuestion, AbstractPile sourcePile) {
    Card target = null;
    if (this.stack.size() != 0) {
      target = this.stack.get(this.stack.size() - 1);
    }
    if (this.validMove(cardInQuestion, target)) {
      sourcePile.getStack().remove(cardInQuestion);
      this.stack.add(cardInQuestion);
    } else {
      throw new IllegalArgumentException("Not a valid move!");
    }
  }

  abstract boolean validMove(Card mover, Card target);

}
