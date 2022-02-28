package cs3500.freecell;

import java.io.InputStreamReader;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.SimpleFreecellModel;
import cs3500.freecell.model.multimove.MultimoveModel;

/**
 * Main class to run the code for testing.
 */
public class Main {

  /**
   * Main method to run the code for testing.
   * @param args possible initial inputs
   */
  public static void main(String[] args) {
    FreecellModel<Card> model = new MultimoveModel();
    FreecellController<Card> ctrl = new SimpleFreecellController(model,
        new InputStreamReader(System.in), System.out);
    ctrl.playGame(model.getDeck(), 8, 4, false);
  }
}
