package ev3.behaviour.composite;

import ev3.Context;
import ev3.behaviour.Node;
import ev3.behaviour.Result;

public class Sequence implements Node {

  private Node[] children = new Node[0];
  private int position = 0;

  public Sequence(Node... nodes) {
    if (nodes != null) {
      children = nodes;
    }
  }

  @Override
  public Result run(Context context) {
    while (position < children.length) {
      Node node = children[position];
      Result result = node.run(context);
      switch (result) {
        case SUCCESS:
          position++;
          break;
        case FAIL:
          position = 0;
          return Result.FAIL;
        default:
          return result;
      }
    }
    position = 0;
    return Result.SUCCESS;
  }

  @Override
  public void reset() {
    for (Node node : children) node.reset();
    position = 0;
  }

}
