package ev3;

public class LocalMath {

  // linear interpolation between (a) and (b)
  // ratio: (0, 1); 0 -> (a), 1 -> (b)
  public static float mix(float a, float b, float ratio) {
    return a * (1 - ratio) + b * ratio;
  }

  // limit (val) to the range (min(a, b), max(a, b))
  public static float clip(float val, float a, float b) {
    val = Math.min(val, Math.max(a, b));
    val = Math.max(val, Math.min(a, b));
    return val;
  }

  // normalize val from range (a, b) to (0, 1)
  public static float norm(float val, float a, float b) {
    return (val - a) / (b - a);
  }
}
