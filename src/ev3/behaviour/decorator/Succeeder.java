package ev3.behaviour.decorator;

import ev3.Context;
import ev3.behaviour.Node;
import ev3.behaviour.Result;

public class Succeeder implements Node {

  private Node node;

  Succeeder(Node node) {
    this.node = node;
  }

  @Override
  public Result run(Context context) {
    Result result = node.run(context);
    switch (result) {
      case SUCCESS:
        return Result.SUCCESS;
      case FAIL:
        return Result.SUCCESS;
      default:
        return result;
    }
  }

  @Override
  public void reset() {
    node.reset();
  }

}
