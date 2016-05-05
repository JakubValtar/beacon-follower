package ev3.behaviour.condition.predicate;

import ev3.behaviour.condition.IntCondition;

public class Equal implements IntCondition.Predicate {

  private int test;

  Equal(int test) {
    this.test = test;
  }

  @Override
  public boolean test(int value) {
    return value == test;
  }

}
