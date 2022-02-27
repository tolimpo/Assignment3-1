package cs3500.freecell.model;

/**
 * Enumeration of the ranks of playing cards.
 */
public enum Rank {
  ACE(1, "A"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"),
  SIX(6, "6"), SEVEN(7, "7"), EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"),
  JACK(11, "J"), QUEEN(12, "Q"), KING(13, "K");

  private final int value;
  private final String faceValue;

  Rank(int value, String faceValue) {
    this.value = value;
    this.faceValue = faceValue;
  }

  /**
   * Return the numerical value of the card's rank.
   * @return the value, as an integer
   */
  int getValue() {
    return this.value;
  }

  /**
   * Return the face value of the card (A, 2, ..., 10, J, Q, K).
   * @return the face value, as a String
   */
  String getFaceValue() {
    return this.faceValue;
  }
}
