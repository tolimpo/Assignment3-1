package cs3500.freecell.model;

/**
 * Represents a foundation pile for the game of freecell.
 */
public class FoundationPile extends AbstractPile {

  @Override
  void moveFrom(int sourceCardIndex, AbstractPile destinationPile) {
    throw new IllegalArgumentException("Cannot move card from Foundation Pile!");
  }


  @Override
  protected boolean validMove(Card mover, Card target) {
    if (target == null) {
      return mover.getRank() == Rank.ACE;
    } else {
      return mover.getSuit() == target.getSuit()
          && mover.getRank().getValue() - 1 == target.getRank().getValue();
    }
  }
}
