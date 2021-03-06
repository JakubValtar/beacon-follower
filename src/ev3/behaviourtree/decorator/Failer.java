package ev3.behaviourtree.decorator;

import ev3.Context;
import ev3.behaviourtree.Node;
import ev3.behaviourtree.Result;

public class Failer implements Node {

  private final Node node;

  public Failer(Node node) {
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

}
