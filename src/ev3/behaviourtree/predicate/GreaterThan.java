package ev3.behaviourtree.predicate;

import ev3.behaviourtree.Condition;

public class GreaterThan implements Condition.Predicate {

  private final int test;

  public GreaterThan(int test) {
    this.test = test;
  }

  @Override
  public boolean test(int value) {
    return value > test;
  }

}
