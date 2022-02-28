package cs3500.freecell.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class AbstractModel implements FreecellModel<Card> {

  private List<Card> deck;
  private boolean gameStarted;
  private List<AbstractPile> openPiles;
  protected List<AbstractPile> cascadePiles;
  private List<AbstractPile> foundationPiles;

  /**
   * Constructs a SimpleFreecellModel object. It initializes a deck, as well as the piles.
   */
  public AbstractModel() {
    gameStarted = false;
    openPiles = new ArrayList<>();
    cascadePiles = new ArrayList<>();
    foundationPiles = new ArrayList<>();

    this.deck = new ArrayList<>();
    List<Suit> suits = new ArrayList<>(Arrays.asList(Suit.CLUBS, Suit.DIAMONDS,
        Suit.HEARTS, Suit.SPADES));
    List<Rank> ranks = new ArrayList<>(Arrays.asList(Rank.KING, Rank.QUEEN, Rank.JACK, Rank.TEN,
        Rank.NINE, Rank.EIGHT, Rank.SEVEN, Rank.SIX, Rank.FIVE, Rank.FOUR, Rank.THREE, Rank.TWO,
        Rank.ACE));
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 4; j++) {
        this.deck.add(new Card(suits.get(j), ranks.get(i)));
      }
    }

  }

  @Override
  public List<Card> getDeck() {
    return this.deck;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    if (!isValidDeck(deck)) {
      throw new IllegalArgumentException("Invalid deck!");
    }
    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("Must be at least 4 cascade piles!");
    }
    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("Must be at least 1 open pile!");
    }
    this.deck = deck;
    if (shuffle) {
      shuffleDeck();
    }
    if (gameStarted) {
      openPiles = new ArrayList<>();
      cascadePiles = new ArrayList<>();
      foundationPiles = new ArrayList<>();
    }
    for (int i = 0; i < numCascadePiles; i++) {
      this.createCascadePiles();
    }
    for (int i = 0; i < numOpenPiles; i++) {
      openPiles.add(new OpenPile());
    }
    for (int i = 0; i < 4; i++) {
      this.foundationPiles.add(new FoundationPile());
    }

    List<Card> dealDeck = new ArrayList<>(this.deck);
    int currentCascadePile = 0;
    while (!dealDeck.isEmpty()) {
      cascadePiles.get(currentCascadePile).add(dealDeck.remove(0));
      currentCascadePile = (currentCascadePile + 1) % numCascadePiles;
    }

    this.gameStarted = true;
  }



  /**
   * Determines if the deck is valid. A valid deck has 52 cards, no duplicates,
   * and no invalid cards.
   *
   * @param deck the deck to be checked
   * @return true if the deck is valid, false if deck is invalid
   */
  private boolean isValidDeck(List<Card> deck) {
    for (int i = 0; i < deck.size() - 1; i++) {
      for (int j = i + 1; j < deck.size(); j++) {
        if (deck.get(i).equals(deck.get(j))) {
          return false;
        }
      }
      if (!deck.get(i).isValidCard()) {
        return false;
      }
    }
    return deck.size() == 52;
  }

  /**
   * Shuffles {@code this.deck}.
   */
  private void shuffleDeck() {
    List<Card> shuffled = new ArrayList<>();
    Random rand = new Random();
    for (int i = 0; i < 52; i++) {
      shuffled.add(deck.remove(rand.nextInt(52 - i)));
    }
    this.deck = shuffled;
  }

  protected abstract void createCascadePiles();

  @Override
  public void move(PileType source, int pileNumber, int cardIndex,
                   PileType destination, int destPileNumber) {
    List<AbstractPile> sourcePiles = this.getPile(source);
    List<AbstractPile> destinationPiles = this.getPile(destination);

    this.checkGameStarted();
    this.checkValidSource(source, pileNumber, cardIndex);
    this.checkValidDestination(destination, destPileNumber);

    sourcePiles.get(pileNumber).moveFrom(cardIndex, destinationPiles.get(destPileNumber));

  }

  /**
   * Gets the field associated with the given PileType.
   * @param type the PileType
   * @return the piles themselves (a list)
   */
  private List<AbstractPile> getPile(PileType type) {
    switch (type) {
      case CASCADE:
        return this.cascadePiles;
      case OPEN:
        return this.openPiles;
      case FOUNDATION:
        return this.foundationPiles;
      default:
        throw new IllegalStateException("Default case should never happen!");
    }
  }

  private void checkValidSource(PileType source, int pileNumber, int cardIndex) {
    switch (source) {
      case CASCADE:
        this.checkValidIndex(this.cascadePiles.size(), pileNumber);
        this.checkValidIndex(this.cascadePiles.get(pileNumber).size(), cardIndex);
        break;
      case OPEN:
        this.checkValidIndex(this.openPiles.size(), pileNumber);
        this.checkValidIndex(this.openPiles.get(pileNumber).size(), cardIndex);
        break;
      case FOUNDATION:
        throw new IllegalArgumentException("Cannot move card from foundation pile!");
      default:
        throw new IllegalStateException("Default case should never happen!");
    }
  }

  private void checkValidDestination(PileType destination, int destPileNumber) {
    switch (destination) {
      case CASCADE:
        this.checkValidIndex(this.cascadePiles.size(), destPileNumber);
        break;
      case OPEN:
        this.checkValidIndex(this.openPiles.size(), destPileNumber);
        break;
      case FOUNDATION:
        this.checkValidIndex(this.foundationPiles.size(), destPileNumber);
        break;
      default:
        throw new IllegalStateException("Default case should never happen!");
    }
  }



  @Override
  public boolean isGameOver() {
    for (AbstractPile f : foundationPiles) {
      if (f.size() != 13) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getNumCardsInFoundationPile(int index) {
    this.checkGameStarted();
    this.checkValidIndex(4, index);
    return this.foundationPiles.get(index).size();
  }

  @Override
  public int getNumCascadePiles() {
    if (gameStarted) {
      return this.cascadePiles.size();
    }
    else {
      return -1;
    }
  }

  @Override
  public int getNumCardsInCascadePile(int index) {
    this.checkGameStarted();
    this.checkValidIndex(this.cascadePiles.size(), index);
    return this.cascadePiles.get(index).size();
  }

  @Override
  public int getNumCardsInOpenPile(int index) {
    this.checkGameStarted();
    this.checkValidIndex(this.openPiles.size(), index);
    return this.openPiles.get(index).size();
  }

  @Override
  public int getNumOpenPiles() {
    if (gameStarted) {
      return this.openPiles.size();
    } else {
      return -1;
    }
  }

  @Override
  public Card getFoundationCardAt(int pileIndex, int cardIndex) {
    this.checkGameStarted();
    this.checkValidIndex(this.foundationPiles.size(), pileIndex);
    this.checkValidIndex(this.foundationPiles.get(pileIndex).size(), cardIndex);
    return this.foundationPiles.get(pileIndex).get(cardIndex);
  }

  @Override
  public Card getCascadeCardAt(int pileIndex, int cardIndex) {
    this.checkGameStarted();
    this.checkValidIndex(this.cascadePiles.size(), pileIndex);
    this.checkValidIndex(this.cascadePiles.get(pileIndex).size(), cardIndex);
    return this.cascadePiles.get(pileIndex).get(cardIndex);
  }

  @Override
  public Card getOpenCardAt(int pileIndex) {
    this.checkGameStarted();
    this.checkValidIndex(this.openPiles.size(), pileIndex);
    if (this.openPiles.get(pileIndex).size() == 0) {
      return null;
    } else {
      return this.openPiles.get(pileIndex).get(0);
    }
  }

  private void checkGameStarted() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started!");
    }
  }

  private void checkValidIndex(int upperBound, int index) {
    if (index < 0 || index >= upperBound) {
      throw new IllegalArgumentException("Provided pile index is invalid!");
    }
  }
}
