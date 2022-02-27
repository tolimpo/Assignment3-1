import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test cases for the Freecell view.
 */
public class FreecellTextViewTest {

  SimpleFreecellModel game;
  FreecellTextView gameView;
  String initialView;
  String view1;
  String view2;
  String view3;

  StringBuilder output;
  FreecellTextView gameView2;

  @Before
  public void initData() {
    game = new SimpleFreecellModel();
    gameView = new FreecellTextView(game);
    initialView = "F1:\n"
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
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠";
    view1 = "F1:\n"
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
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠";
    view2 = "F1: A♣\n"
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
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠";
    view3 = "F1: A♣, 2♣\n"
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
        + "C5: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "C6: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C7: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C8: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠";

    output = new StringBuilder();
    gameView2 = new FreecellTextView(game, output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtInvalidPileIndex() {
    new FreecellTextView(null);
  }

  @Test
  public void testToStringReturnsEmptyString() {
    assertEquals("", gameView.toString());
  }

  @Test
  public void testToString() {
    game.startGame(game.getDeck(), 8, 4, false);
    assertEquals(initialView, gameView.toString());
    game.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    assertEquals(view1, gameView.toString());
    game.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(view2, gameView.toString());
    game.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 0);
    assertEquals(view3, gameView.toString());
  }

  @Test
  public void testRenderBoard() {
    try {
      game.startGame(game.getDeck(), 8, 4, false);
      gameView2.renderBoard();
      assertEquals(initialView, output.toString());
      game.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
      gameView2.renderBoard();
      assertEquals(initialView + view1, output.toString());
      game.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
      gameView2.renderBoard();
      assertEquals(initialView + view1 + view2, output.toString());
      game.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 0);
      gameView2.renderBoard();
      assertEquals(initialView + view1 + view2 + view3, output.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Test failed.");
    }
  }

  @Test
  public void testRenderMessage() {
    try {
      game.startGame(game.getDeck(), 8, 4, false);
      gameView2.renderBoard();
      assertEquals(initialView, output.toString());
      gameView2.renderMessage("Hello there!");
      assertEquals(initialView + "Hello there!", output.toString());
      gameView2.renderMessage("Test passed!");
      assertEquals(initialView + "Hello there!" + "Test passed!", output.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Test failed.");
    }
  }
}