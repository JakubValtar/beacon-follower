package ev3.behaviourtree.composite;

import ev3.Context;
import ev3.behaviourtree.Node;
import ev3.behaviourtree.Result;

// Executes children in order until one fails (returns FAIL)
// If all children succeed, returns SUCCESS
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

}
