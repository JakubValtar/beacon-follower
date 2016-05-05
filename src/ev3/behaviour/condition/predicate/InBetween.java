package ev3.behaviour.condition.predicate;

import ev3.behaviour.condition.FloatCondition;

public class InBetween implements FloatCondition.Predicate {

  private float low;
  private float high;

  InBetween(float low, float high) {
    this.low = low;
    this.high = high;
  }

  @Override
  public boolean test(float value) {
    return value >= low && value <= high;
  }

}
