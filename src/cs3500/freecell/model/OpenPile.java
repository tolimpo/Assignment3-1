package cs3500.freecell.model;

/**
 * Represents an open pile for the game of freecell.
 */
public class OpenPile extends AbstractPile {

  @Override
  boolean validMove(Card mover, Card target) {
    return target == null;
  }
}
