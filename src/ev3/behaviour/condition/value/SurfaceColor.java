package ev3.behaviour.condition.value;

import ev3.Context;
import ev3.behaviour.condition.IntCondition;

public class SurfaceColor implements IntCondition.Value {

  @Override
  public int get(Context context) {
    return context.surfaceColor;
  }

}
