package ev3.value;

import ev3.Context;
import ev3.behaviourtree.Condition;

public class BeaconDistance implements Condition.Value {

  @Override
  public int get(Context context) {
    return context.beaconDistance;
  }

}
