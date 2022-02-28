package cs3500.freecell.view;

import java.io.IOException;

import cs3500.freecell.model.FreecellModelState;

/**
 * This is the implementation of the {@link FreecellView} interface. It represents a simple
 * text view for the game of Freecell. It supports translating the model into a string of text,
 * as well as adding said string to an Appendable output. It also supports adding arbitrary
 * text the Appendable output.
 */
public class FreecellTextView implements FreecellView {
  private final FreecellModelState<?> model;
  private Appendable output;

  /**
   * Constructs a FreecellTextView object, using a FreecellModelState.
   * @param model an object created using an implementation
   *              of {@link FreecellModelState}
   */
  public FreecellTextView(FreecellModelState<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.model = model;
    this.output = null;
  }

  /**
   * Constructs a FreecellTextView object, using a FreecellModelState and Appendable output.
   * @param model an object created using an implementation
   *              of {@link FreecellModelState}
   * @param output an Appendable object to add the text view to
   */
  public FreecellTextView(FreecellModelState<?> model, Appendable output) {
    this(model);
    this.output = output;
  }

  @Override
  public String toString() {
    try {
      StringBuilder textView = new StringBuilder();
      for (int i = 0; i < 4; i++) {
        textView.append("F").append(i + 1).append(":");
        for (int j = 0; j < model.getNumCardsInFoundationPile(i); j++) {
          if (j < model.getNumCardsInFoundationPile(i) - 1) {
            textView.append(" ").append(model.getFoundationCardAt(i, j).toString()).append(",");
          } else {
            textView.append(" ").append(model.getFoundationCardAt(i, j).toString());
          }
        }
        textView.append("\n");
      }
      for (int i = 0; i < model.getNumOpenPiles(); i++) {
        textView.append("O").append(i + 1).append(":");
        if (model.getNumCardsInOpenPile(i) == 1) {
          textView.append(" ").append(model.getOpenCardAt(i).toString());
        }
        textView.append("\n");
      }
      for (int i = 0; i < model.getNumCascadePiles(); i++) {
        textView.append("C").append(i + 1).append(":");
        for (int j = 0; j < model.getNumCardsInCascadePile(i); j++) {
          if (j < model.getNumCardsInCascadePile(i) - 1) {
            textView.append(" ").append(model.getCascadeCardAt(i, j).toString()).append(",");
          } else {
            textView.append(" ").append(model.getCascadeCardAt(i, j).toString());
          }
        }
        if (i < model.getNumCascadePiles() - 1) {
          textView.append("\n");
        }
      }
      return textView.toString();
    } catch (Exception e) {
      return "";
    }
  }

  @Override
  public void renderBoard() throws IOException {
    this.output.append(this.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.output.append(message);
  }
}
