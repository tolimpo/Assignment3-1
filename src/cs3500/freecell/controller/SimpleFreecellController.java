package cs3500.freecell.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;

/**
 * This is the implementation of the {@link FreecellController} interface. It represents a simple
 * controller for the game of Freecell. It handles user input and passes those requests to the
 * model. It informs the view to output certain text when the model throws exceptions, such as
 * during invalid moves.
 */
public class SimpleFreecellController implements FreecellController<Card> {
  private final FreecellModel<Card> model;
  private final Scanner scan;
  private final FreecellView view;
  private boolean quit;

  /**
   * Constructs a SimpleFreecellController object.
   * @param model an object from an implementation of {@link FreecellModel}
   * @param rd a Readable object, for input
   * @param ap an Appendable object, for output
   */
  public SimpleFreecellController(FreecellModel<Card> model, Readable rd, Appendable ap) {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("Provided arguments cannot be null!");
    }
    this.model = model;
    this.scan = new Scanner(rd);
    this.view = new FreecellTextView(model, ap);
    this.quit = false;

  }


  /**
   * Start and play a new game of freecell with the provided deck. This deck
   * should be used as-is. This method returns only when the game is over
   * (either by winning or by quitting).
   *
   * @param deck        the deck to be used to play this game
   * @param numCascades the number of cascade piles
   * @param numOpens    the number of open piles
   * @param shuffle     shuffle the deck if true, false otherwise
   * @throws IllegalStateException if writing to the Appendable object used by it fails or
   *                               reading from the provided Readable fails
   * @throws IllegalArgumentException if the deck provided to it are null
   */
  @Override
  public void playGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle) {
    if (deck == null) {
      throw new IllegalArgumentException("Null deck cannot be passed to playGame()!");
    }

    if (!this.startGame(deck, numCascades, numOpens, shuffle)) {
      return;
    }
    this.renderBoard();
    while (!this.model.isGameOver()) {

      String source = this.receiveInput(true);
      if (this.quit) {
        this.quitMessage();
        return;
      }
      String cardIndexStr = this.receiveInput(false);
      if (this.quit) {
        this.quitMessage();
        return;
      }
      String destination = this.receiveInput(true);
      if (this.quit) {
        this.quitMessage();
        return;
      }

      PileType sourcePileType = this.parsePileType(source);
      int sourcePileNumber = Integer.parseInt(source.substring(1)) - 1;
      int cardIndex = Integer.parseInt(cardIndexStr) - 1;
      PileType destPileType = this.parsePileType(destination);
      int destPileNumber = Integer.parseInt(destination.substring(1)) - 1;

      this.move(sourcePileType, sourcePileNumber, cardIndex, destPileType, destPileNumber);
    }


    try {
      this.view.renderMessage("Game over.");
    } catch (IOException e) {
      throw new IllegalStateException("Write to Appendable failed!");
    }

  }

  private void renderBoard() {
    try {
      this.view.renderBoard();
      this.view.renderMessage("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Write to Appendable failed!");
    }
  }

  private boolean startGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle) {
    try {
      this.model.startGame(deck, numCascades, numOpens, shuffle);
      return true;
    } catch (IllegalArgumentException e) {
      try {
        this.view.renderMessage("Could not start game.");
        return false;
      } catch (IOException ex) {
        throw new IllegalStateException("Write to Appendable failed!");
      }
    }
  }

  private String receiveInput(boolean pile) {
    String userInput;
    boolean valid;
    do {
      // Added this try/catch for possible Readable error.
      try {
        userInput = this.scan.next();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Read from Readable failed!");
      }
      if (userInput.equals("Q") || userInput.equals("q")) {
        this.quit = true;
        return userInput;
      }
      if (pile) {
        valid = this.validPileInput(userInput);
      } else {
        valid = this.validIndexInput(userInput);
      }
    }
    while (!valid);
    return userInput;
  }

  private boolean validPileInput(String userInput) {
    try {
      Integer.parseInt(userInput.substring(1));
    } catch (NumberFormatException | StringIndexOutOfBoundsException ex) {
      return false;
    }
    char pileType = userInput.charAt(0);
    return (pileType == 'C'
        || pileType == 'F'
        || pileType == 'O'
        || pileType == 'Q'
        || pileType == 'q');
  }

  private boolean validIndexInput(String userInput) {
    try {
      Integer.parseInt(userInput);
    } catch (NumberFormatException | StringIndexOutOfBoundsException ex) {
      return false;
    }
    return true;
  }

  private void quitMessage() {
    try {
      this.view.renderMessage("Game quit prematurely.");
    } catch (IOException e) {
      throw new IllegalStateException("Write to Appendable failed!");
    }
  }

  private PileType parsePileType(String type) {
    switch (type.charAt(0)) {
      case 'C':
        return PileType.CASCADE;
      case 'F':
        return PileType.FOUNDATION;
      case 'O':
        return PileType.OPEN;
      default:
        throw new IllegalStateException("Default should not happen!");
    }
  }

  private void move(PileType sourcePileType, int sourcePileNumber, int cardIndex,
                    PileType destPileType, int destPileNumber) {
    try {
      this.model.move(sourcePileType, sourcePileNumber, cardIndex, destPileType, destPileNumber);
      // this newLine character was added to separate gameboards as they appear on screen.
      this.view.renderMessage("\n");
      this.renderBoard();
    } catch (IllegalArgumentException | IOException iae) {
      try {
        this.view.renderMessage("Invalid move. Try again. " + iae.getMessage() + "\n");
      } catch (IOException e) {
        throw new IllegalStateException("Write to Appendable failed!");
      }
    }
  }
}
