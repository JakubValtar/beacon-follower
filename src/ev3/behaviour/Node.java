package ev3.behaviour;

import ev3.Context;

public interface Node {

  Result run(Context context);
  void reset();

}
