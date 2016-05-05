package ev3.behaviour.condition.predicate;

import ev3.behaviour.condition.FloatCondition;

public class InBetween implements FloatCondition.Predicate {

  private float low;
  private float high;

  public InBetween(float low, float high) {
    this.low = low;
    this.high = high;
  }

  @Override
  public boolean test(float value) {
    return !Float.isNaN(value) &&
        !Float.isInfinite(value) &&
        value >= low && value <= high;
  }

}
