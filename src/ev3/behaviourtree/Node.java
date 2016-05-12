package ev3.behaviourtree;

import ev3.Context;

// Node of the behavior tree
public interface Node {

  Result run(Context context);

}
