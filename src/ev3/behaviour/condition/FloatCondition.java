package ev3.behaviour.condition;


import ev3.Context;
import ev3.behaviour.Node;
import ev3.behaviour.Result;

public class FloatCondition implements Node {

  public interface Predicate {
    boolean test(float value);
  }

  public interface Value {
    float get(Context context);
  }


  private Predicate predicate;
  private Value value;

  public FloatCondition(Value value, Predicate predicate) {
    this.value = value;
    this.predicate = predicate;
  }

  @Override
  public Result run(Context context) {
    float value = this.value.get(context);
    boolean result = predicate.test(value);
    return result ? Result.SUCCESS : Result.FAIL;
  }

  @Override
  public void reset() { }
}
