package ev3.behaviour.condition.predicate;

import ev3.behaviour.condition.IntCondition;

public class OneOf implements IntCondition.Predicate {

  private int[] test;

  public OneOf(int... test) {
    if (test != null) {
      this.test = test;
    }
  }

  @Override
  public boolean test(int value) {
    for (int t : test) {
      if (value == t) return true;
    }
    return false;
  }

}
