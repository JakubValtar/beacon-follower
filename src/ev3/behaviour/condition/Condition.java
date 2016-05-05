package ev3.behaviour.condition;


import ev3.Context;
import ev3.behaviour.Node;
import ev3.behaviour.Result;

public class Condition<T> implements Node {

  public interface Predicate<T> {
    boolean test(T value);
  }

  public interface Value<T> {
    T get(Context context);
  }


  private Predicate<T> predicate;
  private Value<T> value;

  Condition(Value<T> value, Predicate<T> predicate) {
    this.value = value;
    this.predicate = predicate;
  }

  @Override
  public Result run(Context context) {
    T value = this.value.get(context);
    boolean result = predicate.test(value);
    return result ? Result.SUCCESS : Result.FAIL;
  }

  @Override
  public void reset() { }
}
