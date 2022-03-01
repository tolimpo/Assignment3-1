import org.junit.Test;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.Rank;
import cs3500.freecell.model.Suit;

import static org.junit.Assert.assertEquals;

public class MultimoveModelTest extends AbstractModelTest {

  @Override
  protected FreecellModel<Card> generateModel() {
    return FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE);
  }

  @Test
  public void testMoveValidBuild() {
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
    // putting others into open piles so that the destination card
    // is correct, since this just testing an invalid build
    game2unshuffled.move(PileType.CASCADE, 3, 6, PileType.OPEN, 0);
    game2unshuffled.move(PileType.CASCADE, 3, 5, PileType.OPEN, 1);

    // exception thrown here
    game2unshuffled.move(PileType.CASCADE, 5, 4, PileType.CASCADE, 3);
  }

}