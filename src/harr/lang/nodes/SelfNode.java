package harr.lang.nodes;

import harr.lang.*;

public class SelfNode extends Node {
  public HarrObject eval(Context context) throws HarrException {
    return context.getCurrentSelf();
  }
}