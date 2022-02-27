import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.Rank;
import cs3500.freecell.model.SimpleFreecellModel;
import cs3500.freecell.model.Suit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test cases for the freecell model.
 */
public class FreecellModelTest {
  SimpleFreecellModel game1A;
  SimpleFreecellModel game1B;
  SimpleFreecellModel game2unshuffled;
  SimpleFreecellModel game2shuffled;
  SimpleFreecellModel game3;
  List<Card> validDeck;
  List<Card> invalidDeck1;
  List<Card> invalidDeck2;

  @Before
  public void setup() {
    game1A = new SimpleFreecellModel();
    game1B = new SimpleFreecellModel();

    game2unshuffled = new SimpleFreecellModel();
    game2unshuffled.startGame(game2unshuffled.getDeck(), 8, 4, false);

    game2shuffled = new SimpleFreecellModel();
    game2shuffled.startGame(game2shuffled.getDeck(), 8, 4, true);

    game3 = new SimpleFreecellModel();
    game3.startGame(game3.getDeck(), 7, 3, false);

    validDeck = game2unshuffled.getDeck();

    List<Suit> suits = new ArrayList<>(Arrays.asList(Suit.CLUBS, Suit.DIAMONDS,
        Suit.HEARTS, Suit.SPADES));
    List<Rank> ranks = new ArrayList<>(Arrays.asList(Rank.KING, Rank.QUEEN, Rank.JACK, Rank.TEN,
        Rank.NINE, Rank.EIGHT, Rank.SEVEN, Rank.SIX, Rank.FIVE, Rank.FOUR, Rank.THREE, Rank.TWO,
        Rank.ACE));

    invalidDeck1 = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 10; j++) {
        invalidDeck1.add(new Card(suits.get(i), ranks.get(j)));
      }
    }

    List<Rank> duplicateRanks = new ArrayList<>(Arrays.asList(Rank.KING, Rank.QUEEN,
        Rank.JACK, Rank.TEN, Rank.NINE, Rank.EIGHT, Rank.SEVEN, Rank.SIX, Rank.FIVE,
        Rank.FIVE, Rank.THREE, Rank.TWO, Rank.ACE));

    invalidDeck2 = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        invalidDeck2.add(new Card(suits.get(i), duplicateRanks.get(j)));
      }
    }
  }

  @Test
  public void testGetDeck() {
    assertEquals(validDeck, game1A.getDeck());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameBadDeck1() {
    game1A.startGame(invalidDeck1,
        8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameBadDeck2() {
    game1A.startGame(invalidDeck2,
        8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidCascadePiles() {
    game1A.startGame(invalidDeck2,
        3, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidOpenPiles() {
    game1A.startGame(invalidDeck2,
        8, 0, false);
  }

  @Test
  public void testShuffle() {
    for (int i = 0; i < 52; i++) {
      assertEquals(game1A.getDeck().get(i), game1B.getDeck().get(i));
    }
    game1A.startGame(game1A.getDeck(), 8, 4, true);
    game1B.startGame(game1B.getDeck(), 8, 4, true);
    boolean shuffled = false;
    for (int i = 0; i < 52; i++) {
      if (!game1A.getDeck().get(i).equals(game1B.getDeck().get(i))) {
        shuffled = true;
        break;
      }
    }
    assertTrue(shuffled);
  }

  @Test
  public void testRestartGame() {
    Card aceDiamonds = game2unshuffled.getCascadeCardAt(1, 6);
    Card twoSpades = game2unshuffled.getCascadeCardAt(7, 5);
    Card fourSpades = game2unshuffled.getCascadeCardAt(7, 4);


    game2unshuffled.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 0);
    game2unshuffled.move(PileType.CASCADE, 7, 5, PileType.CASCADE, 1);
    game2unshuffled.move(PileType.CASCADE, 7, 4, PileType.OPEN, 0);

    assertEquals(aceDiamonds, game2unshuffled.getFoundationCardAt(0, 0));
    assertEquals(twoSpades, game2unshuffled.getCascadeCardAt(1, 6));
    assertEquals(fourSpades, game2unshuffled.getOpenCardAt(0));

    game2unshuffled.startGame(game2unshuffled.getDeck(), 8, 4, false);
    assertEquals(aceDiamonds, game2unshuffled.getCascadeCardAt(1, 6));
    assertEquals(twoSpades, game2unshuffled.getCascadeCardAt(7, 5));
    assertEquals(fourSpades, game2unshuffled.getCascadeCardAt(7, 4));


  }

  @Test(expected = IllegalStateException.class)
  public void testMoveGameNotStarted() {
    game1A.move(PileType.CASCADE,
        0, 6, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveGameInvalidSourcePile() {
    game2shuffled.move(PileType.CASCADE, 8, 6, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveGameInvalidCardIndex() {
    game2shuffled.move(PileType.OPEN, 0, 1, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveGameInvalidDestPile() {
    game2shuffled.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveSourceEmptyCascade() {
    int currSourcePile = 3;
    int currCardIndex = 6;
    int currDestPile = 0;
    while (game2unshuffled.getNumCardsInCascadePile(4) != 0) {
      game2unshuffled.move(PileType.CASCADE,
          currSourcePile, currCardIndex, PileType.FOUNDATION, currDestPile);
      if (currSourcePile == 0) {
        currSourcePile = 7;
      } else {
        currSourcePile--;
      }
      if (currSourcePile == 7) {
        currCardIndex--;
      }
      currDestPile = (currDestPile + 1) % 4;
    }

    game2unshuffled.move(PileType.CASCADE,
        4, 0, PileType.CASCADE, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveSourceEmptyOpen() {
    game2unshuffled.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveSourceFoundation() {
    game2unshuffled.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNotAtEndOfPile() {
    game2unshuffled.move(PileType.CASCADE, 3, 2, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveDestinationFullOpen() {
    game2unshuffled.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 0);
    game2unshuffled.move(PileType.CASCADE, 3, 5, PileType.FOUNDATION, 0);
  }

  @Test
  public void testMoveSourceCascade() {
    int currSourcePile = 3;
    int currCardIndex = 6;
    int currDestPile = 0;
    while (game2unshuffled.getNumCardsInCascadePile(7) != 0) {
      game2unshuffled.move(PileType.CASCADE,
          currSourcePile, currCardIndex, PileType.FOUNDATION, currDestPile);
      if (currSourcePile == 0) {
        currSourcePile = 7;
      } else {
        currSourcePile--;
      }
      if (currSourcePile == 7) {
        currCardIndex--;
      }
      currDestPile = (currDestPile + 1) % 4;
    }

    Card queenHearts = game2unshuffled.getCascadeCardAt(6, 0);
    game2unshuffled.move(PileType.CASCADE, 6, 0, PileType.CASCADE, 7);
    assertEquals(queenHearts, game2unshuffled.getCascadeCardAt(7, 0));
    game2unshuffled.move(PileType.CASCADE, 7, 0, PileType.CASCADE, 0);
    assertEquals(queenHearts, game2unshuffled.getCascadeCardAt(0, 1));
    game2unshuffled.move(PileType.CASCADE, 0, 1, PileType.OPEN, 0);
    assertEquals(queenHearts, game2unshuffled.getOpenCardAt(0));
  }

  @Test
  public void testMoveSourceOpen() {
    int currSourcePile = 3;
    int currCardIndex = 6;
    int currDestPile = 0;
    while (game2unshuffled.getNumCardsInCascadePile(7) != 0) {
      game2unshuffled.move(PileType.CASCADE,
          currSourcePile, currCardIndex, PileType.FOUNDATION, currDestPile);
      if (currSourcePile == 0) {
        currSourcePile = 7;
      } else {
        currSourcePile--;
      }
      if (currSourcePile == 7) {
        currCardIndex--;
      }
      currDestPile = (currDestPile + 1) % 4;
    }

    Card queenHearts = game2unshuffled.getCascadeCardAt(6, 0);
    game2unshuffled.move(PileType.CASCADE, 6, 0, PileType.OPEN, 0);
    assertEquals(queenHearts, game2unshuffled.getOpenCardAt(0));
    game2unshuffled.move(PileType.OPEN, 0, 0, PileType.CASCADE, 7);
    assertEquals(queenHearts, game2unshuffled.getCascadeCardAt(7, 0));
    game2unshuffled.move(PileType.CASCADE, 7, 0, PileType.OPEN, 0);
    game2unshuffled.move(PileType.OPEN, 0, 0, PileType.CASCADE, 3);
    assertEquals(queenHearts, game2unshuffled.getCascadeCardAt(3, 1));
    game2unshuffled.move(PileType.CASCADE, 3, 1, PileType.OPEN, 0);
    game2unshuffled.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 1);
    assertEquals(queenHearts, game2unshuffled.getFoundationCardAt(1, 11));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeToCascadeInvalid() {
    game2unshuffled.move(PileType.CASCADE, 3, 6, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeToEmptyFoundationInvalid() {
    game2unshuffled.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeToFoundationInvalid() {
    game2unshuffled.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 0);
    game2unshuffled.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenToCascadeInvalid() {
    game2unshuffled.move(PileType.CASCADE, 3, 6, PileType.OPEN, 0);
    game2unshuffled.move(PileType.OPEN, 0, 0, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenToEmptyFoundationInvalid() {
    game2unshuffled.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    game2unshuffled.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenToFoundationInvalid() {
    game2unshuffled.move(PileType.CASCADE, 3, 6, PileType.FOUNDATION, 0);
    game2unshuffled.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    game2unshuffled.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
  }

  @Test
  public void testIsGameOver() {
    assertFalse(game2unshuffled.isGameOver());

    int currSourcePile = 3;
    int currCardIndex = 6;
    int currDestPile = 0;
    while (game2unshuffled.getNumCardsInCascadePile(0) != 0) {
      game2unshuffled.move(PileType.CASCADE,
          currSourcePile, currCardIndex, PileType.FOUNDATION, currDestPile);
      if (currSourcePile == 0) {
        currSourcePile = 7;
      } else {
        currSourcePile--;
      }
      if (currSourcePile == 7) {
        currCardIndex--;
      }
      currDestPile = (currDestPile + 1) % 4;
    }

    assertTrue(game2unshuffled.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPileInvalidIndex() {
    game2unshuffled.getNumCardsInFoundationPile(5);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInFoundationPileGameNotStarted() {
    game1A.getNumCardsInFoundationPile(0);
  }

  @Test
  public void testGetNumCardsInFoundationPile() {
    assertEquals(0, game2unshuffled.getNumCardsInFoundationPile(0));
    game2unshuffled.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    assertEquals(1, game2unshuffled.getNumCardsInFoundationPile(0));
    game2unshuffled.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 0);
    assertEquals(2, game2unshuffled.getNumCardsInFoundationPile(0));
  }

  @Test
  public void testGetNumCascadePiles() {
    assertEquals(-1, game1A.getNumCascadePiles());
    assertEquals(8, game2unshuffled.getNumCascadePiles());
    assertEquals(7, game3.getNumCascadePiles());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePileInvalidIndex() {
    game2unshuffled.getNumCardsInCascadePile(8);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadePileGameNotStarted() {
    game1A.getNumCardsInCascadePile(1);
  }

  @Test
  public void testGetNumCardsInCascadePile() {
    assertEquals(7, game2unshuffled.getNumCardsInCascadePile(0));
    game2unshuffled.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    assertEquals(6, game2unshuffled.getNumCardsInCascadePile(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPileInvalidIndex() {
    game2unshuffled.getNumCardsInOpenPile(4);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenPileGameNotStarted() {
    game1A.getNumCardsInCascadePile(1);
  }

  @Test
  public void testGetNumCardsInOpenPile() {
    assertEquals(0, game2unshuffled.getNumCardsInOpenPile(0));
    game2unshuffled.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    assertEquals(1, game2unshuffled.getNumCardsInOpenPile(0));
  }

  @Test
  public void testGetNumOpenPiles() {
    assertEquals(-1, game1A.getNumOpenPiles());
    assertEquals(4, game2unshuffled.getNumOpenPiles());
    assertEquals(3, game3.getNumOpenPiles());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtInvalidPileIndex() {
    game2unshuffled.getFoundationCardAt(4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtInvalidCardIndex() {
    game2unshuffled.getFoundationCardAt(0, 3);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardAtGameNotStarted() {
    game1A.getFoundationCardAt(1, 0);
  }

  @Test
  public void testGetFoundationCardAt() {
    Card aceClubs = game2unshuffled.getCascadeCardAt(0, 6);
    game2unshuffled.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    assertEquals(aceClubs, game2unshuffled.getFoundationCardAt(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtInvalidPileIndex() {
    game2unshuffled.getCascadeCardAt(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtInvalidCardIndex() {
    game2unshuffled.getCascadeCardAt(0, 7);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardAtGameNotStarted() {
    game1A.getCascadeCardAt(1, 0);
  }

  @Test
  public void testGetCascadeCardAt() {
    Card aceClubs = new Card(Suit.CLUBS, Rank.ACE);
    Card sevenHearts = new Card(Suit.HEARTS, Rank.SEVEN);
    assertEquals(aceClubs, game2unshuffled.getCascadeCardAt(0, 6));
    assertEquals(sevenHearts, game2unshuffled.getCascadeCardAt(2, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtInvalidIndex() {
    game2unshuffled.getOpenCardAt(5);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardAtGameNotStarted() {
    game1A.getOpenCardAt(0);
  }

  @Test
  public void testGetOpenCardAt() {
    Card ace = game2unshuffled.getCascadeCardAt(0, 6);
    Card three = game2unshuffled.getCascadeCardAt(0, 5);
    assertNull(game2unshuffled.getOpenCardAt(0));
    assertNull(game2unshuffled.getOpenCardAt(1));
    game2unshuffled.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    game2unshuffled.move(PileType.CASCADE, 0, 5, PileType.OPEN, 1);
    assertEquals(ace, game2unshuffled.getOpenCardAt(0));
    assertEquals(three, game2unshuffled.getOpenCardAt(1));
  }
}
