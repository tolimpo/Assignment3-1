package cs3500.freecell.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a card, which has a suit and a rank.
 */
public class Card {
  private final Suit suit;
  private final Rank rank;

  /**
   * Constructs a Card object.
   *
   * @param suit the suit of the card
   * @param rank the rank (value) of the card
   */
  public Card(Suit suit, Rank rank) {
    this.suit = suit;
    this.rank = rank;
  }

  /**
   * Determines whether {@code this} is a valid card. A valid
   * card has a proper suit value and proper rank value.
   *
   * @return true if it is a valid card, false if not
   */
  public boolean isValidCard() {
    List<Suit> validSuits = new ArrayList<>(Arrays.asList(Suit.CLUBS, Suit.DIAMONDS,
        Suit.HEARTS, Suit.SPADES));
    List<Rank> validRanks = new ArrayList<>(Arrays.asList(Rank.KING, Rank.QUEEN, Rank.JACK,
        Rank.TEN, Rank.NINE, Rank.EIGHT, Rank.SEVEN, Rank.SIX, Rank.FIVE, Rank.FOUR,
        Rank.THREE, Rank.TWO, Rank.ACE));

    return validSuits.contains(this.suit) && validRanks.contains(this.rank);
  }

  /**
   * Formats the card into a string, in the form {@code "A♠"}.
   *
   * @return the card as a string
   */
  public String toString() {
    return this.getFaceValue() + this.getPip();
  }

  /**
   * Returns the suit of the card.
   * @return the suit, as a {@link Suit}
   */
  public Suit getSuit() {
    return this.suit;
  }

  /**
   * Returns the pip of the card.
   * @return the pip, as a String
   */
  public String getPip() {
    return this.suit.getPip();
  }

  /**
   * Returns the color of the card.
   * @return the color, as a {@link Color}
   */
  public Color getColor() {
    return this.suit.getColor();
  }

  /**
   * Returns the rank of the card.
   *
   * @return the rank, as a {@link Rank}
   */
  public Rank getRank() {
    return this.rank;
  }

  /**
   * Returns the value of the card.
   *
   * @return the value, as an integer
   */
  public int getIntValue() {
    return this.rank.getValue();
  }

  /**
   * Returns the face value of the card.
   *
   * @return the face value, as a String
   */
  public String getFaceValue() {
    return this.rank.getFaceValue();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Card) {
      Card that = (Card) o;
      return this.suit.equals(that.suit) && this.rank.equals(that.rank);
    } else {
      return false;
    }
  }

  // overriding hashCode adapted from
  // https://mkyong.com/java/java-how-to-overrides-equals-and-hashcode/
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + suit.hashCode();
    result = 31 * result + rank.hashCode();
    return result;
  }
}
