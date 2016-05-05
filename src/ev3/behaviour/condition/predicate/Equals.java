package ev3.behaviour.condition.predicate;

import ev3.behaviour.condition.IntCondition;

public class Equals implements IntCondition.Predicate {

  private int test;

  public Equals(int test) {
    this.test = test;
  }

  @Override
  public boolean test(int value) {
    return value == test;
  }

}
