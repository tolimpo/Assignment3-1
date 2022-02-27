package cs3500.freecell.model;

import java.awt.Color;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;

/**
 * Enumeration of the suits that a card can have.
 */
public enum Suit {
  CLUBS("♣"), HEARTS("♥"), SPADES("♠"), DIAMONDS("♦");

  private final String pip;

  Suit(String pip) {
    this.pip = pip;
  }

  /**
   * Returns the suit's pip.
   * @return the suit's pip, as a String
   */
  String getPip() {
    return this.pip;
  }

  /**
   * Returns the suit's color.
   * @return the suit's color, as a {@link Color}
   */
  Color getColor() {
    if (this == CLUBS || this == SPADES) {
      return BLACK;
    }
    else {
      return RED;
    }
  }
}
