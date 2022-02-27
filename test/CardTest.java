import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.Rank;
import cs3500.freecell.model.Suit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * JUnit tests for the {@link Card} class and related enums ({@link Suit} and {@link Rank}).
 */
public class CardTest {
  Card card1A;
  Card card1B;
  Card card2;
  Card card3;
  Card card4;
  Card card5;
  Card card6;
  Card card7;
  String str;

  @Before
  public void setup() {
    card1A = new Card(Suit.CLUBS, Rank.SIX);
    card1B = new Card(Suit.CLUBS, Rank.SIX);
    card2 = new Card(Suit.DIAMONDS, Rank.SEVEN);
    card3 = new Card(Suit.HEARTS, Rank.QUEEN);
    card4 = new Card(Suit.SPADES, Rank.KING);
    card5 = new Card(Suit.CLUBS, Rank.FIVE);
    card6 = new Card(Suit.DIAMONDS, Rank.EIGHT);
    card7 = new Card(Suit.HEARTS, Rank.ACE);
    str = "foo";
  }

  @Test
  public void testToString() {
    assertEquals("6♣", card1A.toString());
    assertEquals("7♦", card2.toString());
    assertEquals("Q♥", card3.toString());
    assertEquals("K♠", card4.toString());
  }

  @Test
  public void testGetSuit() {
    assertEquals(Suit.CLUBS, card5.getSuit());
    assertEquals(Suit.DIAMONDS, card6.getSuit());
    assertEquals(Suit.HEARTS, card7.getSuit());
  }

  @Test
  public void testGetPip() {
    assertEquals("♣", card5.getPip());
    assertEquals("♦", card6.getPip());
    assertEquals("♥", card7.getPip());
  }

  @Test
  public void testGetColor() {
    assertEquals(Color.BLACK, card5.getColor());
    assertEquals(Color.RED, card6.getColor());
    assertEquals(Color.RED, card7.getColor());
  }

  @Test
  public void testGetRank() {
    assertEquals(Rank.FIVE, card5.getRank());
    assertEquals(Rank.EIGHT, card6.getRank());
    assertEquals(Rank.ACE, card7.getRank());
  }

  @Test
  public void testGetIntValue() {
    assertEquals(5, card5.getIntValue());
    assertEquals(8, card6.getIntValue());
    assertEquals(1, card7.getIntValue());
  }

  @Test
  public void testGetFaceValue() {
    assertEquals("5", card5.getFaceValue());
    assertEquals("8", card6.getFaceValue());
    assertEquals("A", card7.getFaceValue());
  }

  @Test
  public void testEquals() {
    assertEquals(card1A, card1B);
    assertNotEquals(card2, card3);
    assertNotEquals(card4, str);
  }

  @Test
  public void testHashCode() {
    assertEquals(card1A.hashCode(), card1B.hashCode());
    assertNotEquals(card2.hashCode(), card3.hashCode());
    assertNotEquals(card4.hashCode(), str.hashCode());
  }
}