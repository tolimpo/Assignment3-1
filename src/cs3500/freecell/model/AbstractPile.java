package cs3500.freecell.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstraction of piles, as all piles need
 * the fields and methods included in this class.
 */
public abstract class AbstractPile {
  protected List<Card> stack = new ArrayList<>();

  void add(Card c) {
    this.stack.add(c);
  }

  int size() {
    return this.stack.size();
  }

  Card get(int index) {
    return this.stack.get(index);
  }

  protected void remove(Card c) {
    this.stack.remove(c);
  }

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

  abstract boolean validMove(Card mover, Card target);

}
