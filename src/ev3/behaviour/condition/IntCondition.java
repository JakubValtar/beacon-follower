package ev3.behaviour.condition;


import ev3.Context;
import ev3.behaviour.Node;
import ev3.behaviour.Result;

public class IntCondition implements Node {

  public interface Predicate {
    boolean test(int value);
  }

  public interface Value {
    int get(Context context);
  }


  private Predicate predicate;
  private Value value;

  public IntCondition(Value value, Predicate predicate) {
    this.value = value;
    this.predicate = predicate;
  }

  @Override
  public Result run(Context context) {
    int value = this.value.get(context);
    boolean result = predicate.test(value);
    return result ? Result.SUCCESS : Result.FAIL;
  }

  @Override
  public void reset() { }
}
