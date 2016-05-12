package ev3.behaviourtree;


import ev3.Context;

public class Condition implements Node {

  public interface Predicate {
    boolean test(int value);
  }

  public interface Value {
    int get(Context context);
  }


  private Predicate predicate;
  private Value value;

  public Condition(Value value, Predicate predicate) {
    this.value = value;
    this.predicate = predicate;
  }

  @Override
  public Result run(Context context) {
    int value = this.value.get(context);
    boolean result = predicate.test(value);
    return result ? Result.SUCCESS : Result.FAIL;
  }

}
