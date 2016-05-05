package ev3.behaviour.condition.predicate;

import ev3.behaviour.condition.FloatCondition;

public class GreaterThan implements FloatCondition.Predicate {

  private float test;

  GreaterThan(float test) {
    this.test = test;
  }

  @Override
  public boolean test(float value) {
    return value > test;
  }

}