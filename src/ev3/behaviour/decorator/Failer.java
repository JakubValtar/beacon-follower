package ev3.behaviour.decorator;

import ev3.Context;
import ev3.behaviour.Node;
import ev3.behaviour.Result;

public class Failer implements Node {

  private Node node;

  Failer(Node node) {
    this.node = node;
  }

  @Override
  public Result run(Context context) {
    Result result = node.run(context);
    switch (result) {
      case SUCCESS:
        return Result.FAIL;
      case FAIL:
        return Result.FAIL;
      default:
        return result;
    }
  }

  @Override
  public void reset() {
    node.reset();
  }

}
