package ev3.behaviourtree.predicate;

import ev3.behaviourtree.Condition;

public class InBetween implements Condition.Predicate {

  private final int low;
  private final int high;

  public InBetween(int low, int high) {
    this.low = low;
    this.high = high;
  }

  @Override
  public boolean test(int value) {
    return !Float.isNaN(value) &&
        value >= low && value <= high;
  }

}
