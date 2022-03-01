package cs3500.freecell.model;

/**
 * This is the implementation of the {@link FreecellModel} interface, using the {@link Card} class.
 * It represents a simple model for the game of Freecell. It handles various aspects of the game
 * state, including the deck used, whether the game has started, and the open, cascade, and
 * foundation piles. It can return various info about the game state, as well as movement of card
 * and checking if the game has been won.
 */
public class SimpleFreecellModel extends AbstractModel {

  /*
  All functionality is now in AbstractModel, except to create the cascade piles,
  which is specific to the SimpleFreecellModel implementation.
   */
  @Override
  protected void createCascadePile() {
    cascadePiles.add(new CascadePile());
  }
}
