package cs3500.freecell.model.multimove;

import java.util.List;

import cs3500.freecell.model.Card;
import cs3500.freecell.model.CascadePile;

public class MultimoveCascadePile extends CascadePile {
  private final MultimoveModel model;

  MultimoveCascadePile(MultimoveModel model) {
    this.model = model;
  }

  @Override
  protected void moveBuildTo(int startCardIndex, List<Card> sourcePile) {
    int sourceSize = sourcePile.size();
    List<Card> build = sourcePile.subList(startCardIndex, sourceSize);
    int buildSize = build.size();
    if (this.validBuild(build)
        && this.enoughIntermediateSpots(buildSize)) {
      int currentIndex = startCardIndex;


      while (currentIndex < sourceSize) {
        this.stack.add(sourcePile.remove(startCardIndex));
        currentIndex++;
      }
    } else {
      throw new IllegalArgumentException("Not a valid move!");
    }
  }

  private boolean validBuild(List<Card> build) {
    if (!this.validMove(build.get(0), this.stack.get(this.stack.size() - 1))) {
      return false;
    }
    for (int i = 0; i < build.size() - 1; i++) {
      if (!this.validMove(build.get(i + 1), build.get(i))) {
        return false;
      }
    }

    return true;
  }

  private boolean enoughIntermediateSpots(int buildSize) {
    int numFreeOpen = 0;
    int count = 0;
    while (count < model.getNumOpenPiles()) {
      if (model.getNumCardsInOpenPile(count) == 0) {
        numFreeOpen++;
      }
      count++;

    }

    int numFreeCascade = 0;
    count = 0;
    while (count < model.getNumCascadePiles()) {
      if (model.getNumCardsInCascadePile(count) == 0) {
        numFreeCascade++;
      }
      count++;
    }

    if (this.stack.size() == 0) {
      numFreeCascade--;
    }

    return buildSize <= (numFreeOpen + 1) * Math.pow(2, numFreeCascade);
  }


}
