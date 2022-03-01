import cs3500.freecell.model.Card;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;


/**
 * JUnit test cases for the simple freecell model.
 */
public class FreecellModelTest extends AbstractModelTest {

  @Override
  protected FreecellModel<Card> generateModel() {
    return FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
  }


}
