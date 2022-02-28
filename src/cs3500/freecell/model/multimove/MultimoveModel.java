package cs3500.freecell.model.multimove;


import cs3500.freecell.model.AbstractModel;

public class MultimoveModel extends AbstractModel {

  @Override
  protected void createCascadePile() {
    cascadePiles.add(new MultimoveCascadePile(this));
  }
}
