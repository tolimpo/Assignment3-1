import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.MockAppendable;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.SimpleFreecellModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test cases for the freecell controller.
 */
public class FreecellControllerTest {
  FreecellModel<Card> model;
  StringBuilder output;
  FreecellController<Card> ctrl;
  String initialBoard;
  String firstMove;
  FreecellController<Card> ctrlQuitFirst;
  FreecellController<Card> ctrlQuitSecond;
  FreecellController<Card> ctrlQuitThird;
  FreecellController<Card> ctrlSkipBadInputs1;
  FreecellController<Card> ctrlSkipBadInputs2;
  FreecellController<Card> ctrlSkipBadInputs3;
  FreecellController<Card> ctrlNoMoveFromFoundation;
  FreecellController<Card> ctrlBadSourcePileIndex;
  FreecellController<Card> ctrlBadCardIndex;
  FreecellController<Card> ctrlBadSourceDestIndex;
  FreecellController<Card> ctrlInvalidMoveToCascade;
  FreecellController<Card> ctrlInvalidMoveToOpen;
  FreecellController<Card> ctrlInvalidMoveToFoundation1;
  FreecellController<Card> ctrlInvalidMoveToFoundation2;
  FreecellController<Card> ctrlMoveOpenToCascade;
  FreecellController<Card> ctrlMoveOpenToFoundation;

  @Before
  public void setup() {
    model = new SimpleFreecellModel();
    output = new StringBuilder();
    ctrl = new SimpleFreecellController(model, new StringReader("C1 7 C6 Q"), output);
    initialBoard = "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C4: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C5: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n";
    firstMove = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C4: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C5: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦, A♣\n"
        + "C7: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n";
    ctrlQuitFirst = new SimpleFreecellController(model, new StringReader("Q"),
        output);
    ctrlQuitSecond = new SimpleFreecellController(model, new StringReader("C1 q"),
        output);
    ctrlQuitThird = new SimpleFreecellController(model, new StringReader("C1 7 Q"),
        output);
    ctrlSkipBadInputs1 = new SimpleFreecellController(model, new StringReader("skip C1 7 C6 Q"),
        output);
    ctrlSkipBadInputs2 = new SimpleFreecellController(model, new StringReader("C1 foo 7 C6 Q"),
        output);
    ctrlSkipBadInputs3 = new SimpleFreecellController(model,
        new StringReader("C1 7 chocolate pretzel c6 C6 Q"), output);
    ctrlNoMoveFromFoundation = new SimpleFreecellController(model,
        new StringReader("C1 7 F1 F1 1 C6 Q"), output);
    ctrlBadSourcePileIndex = new SimpleFreecellController(model,
        new StringReader("C0 7 C6 Q"), output);
    ctrlBadCardIndex = new SimpleFreecellController(model,
        new StringReader("C1 4 C6 Q"), output);
    ctrlBadSourceDestIndex = new SimpleFreecellController(model,
        new StringReader("C1 7 O5 Q"), output);
    ctrlInvalidMoveToCascade = new SimpleFreecellController(model,
        new StringReader("C1 7 C5 Q"), output);
    ctrlInvalidMoveToOpen = new SimpleFreecellController(model,
        new StringReader("C1 7 O1 C2 7 O1 Q"), output);
    ctrlInvalidMoveToFoundation1 = new SimpleFreecellController(model,
        new StringReader("C5 6 F1 Q"), output);
    ctrlInvalidMoveToFoundation2 = new SimpleFreecellController(model,
        new StringReader("C1 7 F1 C2 7 F1 Q"), output);
    ctrlMoveOpenToCascade = new SimpleFreecellController(model,
        new StringReader("C1 7 O1 O1 1 C6 Q"), output);
    ctrlMoveOpenToFoundation = new SimpleFreecellController(model,
        new StringReader("C1 7 O1 O1 1 F1 Q"), output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullModel() {
    FreecellController<Card> badCtrl = new SimpleFreecellController(null,
        new StringReader("C1 7 C6 Q"), output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullReadable() {
    FreecellController<Card> badCtrl = new SimpleFreecellController(model,
        null, output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullAppendable() {
    FreecellController<Card> badCtrl = new SimpleFreecellController(model,
        new StringReader("C1 7 C6 Q"), null);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGameWriteToAppendableFails() {
    FreecellController<Card> mockCtrl = new SimpleFreecellController(model,
        new StringReader("C1 7 C6 Q"), new MockAppendable());
    mockCtrl.playGame(model.getDeck(), 8, 4, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGameReadFromReadableFails() {
    FreecellController<Card> mockCtrl = new SimpleFreecellController(model,
        new StringReader("C1 7 C6"), output);
    mockCtrl.playGame(model.getDeck(), 8, 4, false);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameNullDeck() {
    ctrl.playGame(null, 8, 4, false);
  }

  @Test
  public void testPlayGameBadNumCascade() {
    ctrl.playGame(model.getDeck(), 2, 4, false);
    assertEquals("Could not start game.", output.toString());
  }

  @Test
  public void testPlayGameBadNumOpen() {
    ctrl.playGame(model.getDeck(), 8, 0, false);
    assertEquals("Could not start game.", output.toString());
  }

  @Test
  public void testPlayGameQuitFirstInput() {
    ctrlQuitFirst.playGame(model.getDeck(), 8, 4, false);
    assertEquals(initialBoard + "Game quit prematurely.", output.toString());
  }

  @Test
  public void testPlayGameQuitSecondInput() {
    ctrlQuitSecond.playGame(model.getDeck(), 8, 4, false);
    assertEquals(initialBoard + "Game quit prematurely.", output.toString());
  }

  @Test
  public void testPlayGameQuitThirdInput() {
    ctrlQuitThird.playGame(model.getDeck(), 8, 4, false);
    assertEquals(initialBoard + "Game quit prematurely.", output.toString());
  }

  @Test
  public void testPlayGameSkipBadInputs1() {
    ctrlSkipBadInputs1.playGame(model.getDeck(), 8, 4, false);
    assertEquals(initialBoard + firstMove + "Game quit prematurely.", output.toString());
  }

  @Test
  public void testPlayGameSkipBadInputs2() {
    ctrlSkipBadInputs2.playGame(model.getDeck(), 8, 4, false);
    assertEquals(initialBoard + firstMove + "Game quit prematurely.", output.toString());
  }

  @Test
  public void testPlayGameSkipBadInputs3() {
    ctrlSkipBadInputs3.playGame(model.getDeck(), 8, 4, false);
    assertEquals(initialBoard + firstMove + "Game quit prematurely.", output.toString());
  }

  @Test
  public void testPlayGameNoMoveFromFoundation() {
    ctrlNoMoveFromFoundation.playGame(model.getDeck(), 8, 4, false);
    String result = "\nF1: A♣\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C4: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C5: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "Invalid move. Try again. Cannot move card from foundation pile!\n"
        + "Game quit prematurely.";
    assertEquals(initialBoard + result, output.toString());
  }

  @Test
  public void testPlayGameBadSourcePileIndex() {
    ctrlBadSourcePileIndex.playGame(model.getDeck(), 8, 4, false);
    assertEquals(initialBoard + "Invalid move. Try again. Provided pile index is invalid!\n"
        + "Game quit prematurely.", output.toString());
  }

  @Test
  public void testPlayGameBadCardIndex() {
    ctrlBadCardIndex.playGame(model.getDeck(), 8, 4, false);
    assertEquals(initialBoard + "Invalid move. Try again. Given index not the end of pile, card "
        + "cannot be moved!\nGame quit prematurely.", output.toString());
  }

  @Test
  public void testPlayGameBadDestPileIndex() {
    ctrlBadSourceDestIndex.playGame(model.getDeck(), 8, 4, false);
    assertEquals(initialBoard + "Invalid move. Try again. Provided pile index is invalid!\n"
        + "Game quit prematurely.", output.toString());
  }

  @Test
  public void testPlayGameInvalidMoveToCascade() {
    ctrlInvalidMoveToCascade.playGame(model.getDeck(), 8, 4, false);
    assertEquals(initialBoard + "Invalid move. Try again. Not a valid move!\n"
        + "Game quit prematurely.", output.toString());
  }

  @Test
  public void testPlayGameInvalidMoveToOpen() {
    ctrlInvalidMoveToOpen.playGame(model.getDeck(), 8, 4, false);
    String result = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♣\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C4: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C5: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "Invalid move. Try again. Not a valid move!\n"
        + "Game quit prematurely.";
    assertEquals(initialBoard + result, output.toString());
  }

  @Test
  public void testPlayGameInvalidMoveToFoundation1() {
    ctrlInvalidMoveToFoundation1.playGame(model.getDeck(), 8, 4, false);
    assertEquals(initialBoard + "Invalid move. Try again. Not a valid move!\n"
        + "Game quit prematurely.", output.toString());
  }

  @Test
  public void testPlayGameInvalidMoveToFoundation2() {
    ctrlInvalidMoveToFoundation2.playGame(model.getDeck(), 8, 4, false);
    String result = "\nF1: A♣\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C4: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C5: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "Invalid move. Try again. Not a valid move!\n"
        + "Game quit prematurely.";
    assertEquals(initialBoard + result, output.toString());
  }

  @Test
  public void testPlayGameMoveOpenToCascade() {
    ctrlMoveOpenToCascade.playGame(model.getDeck(), 8, 4, false);
    String result = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♣\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C4: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C5: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C4: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C5: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦, A♣\n"
        + "C7: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "Game quit prematurely.";
    assertEquals(initialBoard + result, output.toString());
  }

  @Test
  public void testPlayGameMoveOpenToFoundation() {
    ctrlMoveOpenToFoundation.playGame(model.getDeck(), 8, 4, false);
    String result = "\nF1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: A♣\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C4: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C5: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "\nF1: A♣\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C2: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C3: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C4: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C5: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "Game quit prematurely.";
    assertEquals(initialBoard + result, output.toString());
  }

  @Test
  public void testPlayGameComplete() {
    StringBuilder moves = new StringBuilder();
    int currSourcePile = 4;
    int currCardIndex = 7;
    int currDestPile = 4;
    for (int i = 0; i < 52; i++) {
      moves.append("C").append(currSourcePile)
          .append(" ")
          .append(currCardIndex)
          .append(" ")
          .append("F").append(currDestPile)
          .append(" ");
      if (currSourcePile == 1) {
        currSourcePile = 8;
        currCardIndex--;
      } else {
        currSourcePile--;
      }
      currDestPile = (currDestPile - 1) % 4;
      if (currDestPile == 0) {
        currDestPile = 4;
      }
    }

    StringReader winGameMoves = new StringReader(moves.toString());

    FreecellController<Card> completeGame = new SimpleFreecellController(model,
        winGameMoves, output);
    completeGame.playGame(model.getDeck(), 8, 4, false);

    String outputString = output.toString();
    int outputSize = outputString.length();

    // Testing game over message at end of output
    assertEquals("Game over.", outputString.substring(outputSize - 10, outputSize));
    // Testing the model isGameOver() to see if the game is really over
    assertTrue(model.isGameOver());
  }

  @Test
  public void testPlayGameCompleteWithInvalidInputs() {
    StringBuilder moves = new StringBuilder();
    int currSourcePile = 4;
    int currCardIndex = 7;
    int currDestPile = 4;
    for (int i = 0; i < 52; i++) {
      moves.append("C").append(currSourcePile)
          .append(" ")
          .append(currCardIndex)
          .append(" ")
          .append("F").append(currDestPile)
          .append(" ");
      if (currSourcePile == 1) {
        currSourcePile = 8;
        currCardIndex--;
      } else {
        currSourcePile--;
      }
      currDestPile = (currDestPile - 1) % 4;
      if (currDestPile == 0) {
        currDestPile = 4;
      }

      // introducing invalid moves
      if (i % 5 == 0) {
        moves.append("C").append(i * 12)
            .append(" ")
            .append(i * 8)
            .append(" ")
            .append("F").append(currDestPile - 10)
            .append(" ");
      }
    }

    StringReader winGameMoves = new StringReader(moves.toString());

    FreecellController<Card> completeGame = new SimpleFreecellController(model,
        winGameMoves, output);
    completeGame.playGame(model.getDeck(), 8, 4, false);

    String outputString = output.toString();
    int outputSize = outputString.length();

    // Testing game over message at end of output
    assertEquals("Game over.", outputString.substring(outputSize - 10, outputSize));
    // Testing the model isGameOver() to see if the game is really over
    assertTrue(model.isGameOver());
  }


}
