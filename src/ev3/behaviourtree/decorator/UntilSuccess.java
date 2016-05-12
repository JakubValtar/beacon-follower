package ev3.behaviourtree.decorator;

import ev3.Context;
import ev3.behaviourtree.Node;
import ev3.behaviourtree.Result;

public class UntilSuccess implements Node {

  private final Node node;

  public UntilSuccess(Node node) {
    this.node = node;
  }

  @Override
  public Result run(Context context) {
    Result result = node.run(context);
    switch (result) {
      case SUCCESS:
        return Result.SUCCESS;
      case FAIL:
        return Result.RUNNING;
      default:
        return result;
    }
  }

}
