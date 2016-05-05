package ev3.behaviour.action;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import ev3.Context;
import ev3.behaviour.Node;
import ev3.behaviour.Result;

public class Action implements Node {

  private Future<Boolean> future;

  public interface BackgroundTask {
    boolean runInBackground(Context context);
  }

  private BackgroundTask task;

  public Action(BackgroundTask task) {
    this.task = task;
  }

  @Override
  public final Result run(final Context context) {
    if (future == null) {
      future = context.executor.submit(new Callable<Boolean>() {
        public Boolean call() {
          return task.runInBackground(context);
        }
      });
    }
    if (future.isDone()) {
      try {
        boolean result = future.get();
        return result ? Result.SUCCESS : Result.FAIL;
      } catch (InterruptedException | ExecutionException | CancellationException e) {
        e.printStackTrace();
        return Result.FAIL;
      } finally {
        future = null;
      }
    }
    return Result.RUNNING;
  }

  @Override
  public void reset() {
    if (future != null && !future.isDone()) {
      future.cancel(true);
      try {
        future.get();
      } catch (InterruptedException | ExecutionException | CancellationException e) { }
    }
    future = null;
  }
}
