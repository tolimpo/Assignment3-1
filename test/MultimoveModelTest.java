import org.junit.Test;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.CascadePile;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.Rank;
import cs3500.freecell.model.Suit;
import cs3500.freecell.view.FreecellTextView;

import static org.junit.Assert.assertEquals;

public class MultimoveModelTest extends AbstractModelTest {

  @Override
  protected FreecellModel<Card> generateModel() {
    return FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE);
  }

  @Test
  public void testMoveValidBuild() {
    // test setup
    Card aceClubs = new Card(Suit.CLUBS, Rank.ACE);
    assertEquals(aceClubs, game2unshuffled.getCascadeCardAt(0, 6));
    game2unshuffled.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 5);
    assertEquals(aceClubs, game2unshuffled.getCascadeCardAt(5, 6));

    // moving a build of two cards
    Card twoDiamonds = new Card(Suit.DIAMONDS, Rank.TWO);
    game2unshuffled.move(PileType.CASCADE, 5, 5, PileType.CASCADE, 0);
    assertEquals(twoDiamonds, game2unshuffled.getCascadeCardAt(0, 6));
    assertEquals(aceClubs, game2unshuffled.getCascadeCardAt(0, 7));

    // moving a build of three cards
    Card threeClubs = new Card(Suit.CLUBS, Rank.THREE);
    game2unshuffled.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 5);
    assertEquals(threeClubs, game2unshuffled.getCascadeCardAt(5, 5));
    assertEquals(twoDiamonds, game2unshuffled.getCascadeCardAt(5, 6));
    assertEquals(aceClubs, game2unshuffled.getCascadeCardAt(5, 7));

    // moving a build of four cards
    Card fourDiamonds = new Card(Suit.DIAMONDS, Rank.FOUR);
    game2unshuffled.move(PileType.CASCADE, 5, 4, PileType.CASCADE, 0);
    assertEquals(fourDiamonds, game2unshuffled.getCascadeCardAt(0, 5));
    assertEquals(threeClubs, game2unshuffled.getCascadeCardAt(0, 6));
    assertEquals(twoDiamonds, game2unshuffled.getCascadeCardAt(0, 7));
    assertEquals(aceClubs, game2unshuffled.getCascadeCardAt(0, 8));

    // moving a build of five cards
    Card fiveClubs = new Card(Suit.CLUBS, Rank.FIVE);
    game2unshuffled.move(PileType.CASCADE, 0, 4, PileType.CASCADE, 5);
    assertEquals(fiveClubs, game2unshuffled.getCascadeCardAt(5, 4));
    assertEquals(fourDiamonds, game2unshuffled.getCascadeCardAt(5, 5));
    assertEquals(threeClubs, game2unshuffled.getCascadeCardAt(5, 6));
    assertEquals(twoDiamonds, game2unshuffled.getCascadeCardAt(5, 7));
    assertEquals(aceClubs, game2unshuffled.getCascadeCardAt(5, 8));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidBuild() {
    // test setup
    game2unshuffled.move(PileType.CASCADE, 3, 6, PileType.OPEN, 0);
    game2unshuffled.move(PileType.CASCADE, 3, 5, PileType.OPEN, 1);

    // exception thrown here, destination card works, but the build is invalid
    game2unshuffled.move(PileType.CASCADE, 5, 4, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveGoodBuildButCannotGoOnDestination() {
    // test setup
    game2unshuffled.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 5);

    // exception thrown here, build is valid, but destination card forbids it
    game2unshuffled.move(PileType.CASCADE, 5, 5, PileType.CASCADE, 4);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuildOverMaxLimitNoEmptyCascade() {
    // test setup
    game2unshuffled.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 5);
    game2unshuffled.move(PileType.CASCADE, 5, 5, PileType.CASCADE, 0);
    game2unshuffled.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 5);
    game2unshuffled.move(PileType.CASCADE, 5, 4, PileType.CASCADE, 0);
    game2unshuffled.move(PileType.CASCADE, 0, 4, PileType.CASCADE, 5);

    // exception thrown here, trying to move a build of 6 card
    game2unshuffled.move(PileType.CASCADE, 5, 3, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuildOverMaxLimitOneEmptyCascade() {
    // test setup (to get one empty cascade)
    int currSourcePile = 3;
    int currCardIndex = 6;
    int destPile = 0;
    while (game2unshuffled.getNumCardsInCascadePile(7) != 0) {
      game2unshuffled.move(PileType.CASCADE,
          currSourcePile, currCardIndex, PileType.FOUNDATION, destPile);
      if (currSourcePile == 3) {
        currSourcePile = 7;
        currCardIndex--;
      } else {
        currSourcePile = 3;
      }
    }

    // filling up open piles
    for (int i = 6; i > 2; i--) {
      game2unshuffled.move(PileType.CASCADE, 0, i, PileType.OPEN, i - 3);
    }

    // moving to create a build...
    game2unshuffled.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 4);
    // moving a build of 2 cards (maximum in this case)
    game2unshuffled.move(PileType.CASCADE, 4, 5, PileType.CASCADE, 1);

    // exception thrown here, trying to move build of 3 cards
    game2unshuffled.move(PileType.CASCADE, 1, 5, PileType.CASCADE, 4);

  }

  @Test
  public void testMoveBuildToEmptyCascade() {
    // test setup (to get one empty cascade)
    int currSourcePile = 3;
    int currCardIndex = 6;
    int destPile = 0;
    while (game2unshuffled.getNumCardsInCascadePile(7) != 0) {
      game2unshuffled.move(PileType.CASCADE,
          currSourcePile, currCardIndex, PileType.FOUNDATION, destPile);
      if (currSourcePile == 3) {
        currSourcePile = 7;
        currCardIndex--;
      } else {
        currSourcePile = 3;
      }
    }


    Card aceDiamonds = new Card(Suit.DIAMONDS, Rank.ACE);
    assertEquals(aceDiamonds, game2unshuffled.getCascadeCardAt(1, 6));
    game2unshuffled.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 4);
    assertEquals(aceDiamonds, game2unshuffled.getCascadeCardAt(4, 6));

    Card twoClubs = new Card(Suit.CLUBS, Rank.TWO);
    assertEquals(twoClubs, game2unshuffled.getCascadeCardAt(4, 5));
    game2unshuffled.move(PileType.CASCADE, 4, 5, PileType.CASCADE, 7);
    assertEquals(twoClubs, game2unshuffled.getCascadeCardAt(7, 0));
    assertEquals(aceDiamonds, game2unshuffled.getCascadeCardAt(7, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveBuildToEmptyCascadeNuance() {
    // test setup (to get one empty cascade)
    int currSourcePile = 3;
    int currCardIndex = 6;
    int destPile = 0;
    while (game2unshuffled.getNumCardsInCascadePile(7) != 0) {
      game2unshuffled.move(PileType.CASCADE,
          currSourcePile, currCardIndex, PileType.FOUNDATION, destPile);
      if (currSourcePile == 3) {
        currSourcePile = 7;
        currCardIndex--;
      } else {
        currSourcePile = 3;
      }
    }

    // moving to create a build...
    game2unshuffled.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 5);
    game2unshuffled.move(PileType.CASCADE, 5, 5, PileType.CASCADE, 0);
    game2unshuffled.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 5);
    game2unshuffled.move(PileType.CASCADE, 5, 4, PileType.CASCADE, 0);
    // moving a build of 5, which is the max because the pile will be moved to an empty cascade
    game2unshuffled.move(PileType.CASCADE, 0, 4, PileType.CASCADE, 5);

    /*
    Exception thrown here, testing the nuance of when the move is to
    an empty cascade pile, the destination pile does not count as empty.
    There is four empty open, and one empty cascade. Using the formula,
    the max build that can be moved is 10. But since this build of 6
    is moving to an empty cascade, the max build because 5.
     */
    game2unshuffled.move(PileType.CASCADE, 5, 3, PileType.CASCADE, 7);
  }
}