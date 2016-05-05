package ev3.behaviour.condition.value;

import ev3.Context;
import ev3.behaviour.condition.FloatCondition;

public class BeaconDirection implements FloatCondition.Value {

  @Override
  public float get(Context context) {
    return context.beaconDirection;
  }

}
