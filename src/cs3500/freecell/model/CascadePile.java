package cs3500.freecell.model;

/**
 * Represents a cascade pile for the game of freecell.
 */
public class CascadePile extends AbstractPile {

  @Override
  boolean validMove(Card mover, Card target) {
    if (target == null) {
      return true;
    } else {
      return mover.getIntValue() + 1 == target.getIntValue()
          && !mover.getColor().equals(target.getColor());
    }
  }
}
