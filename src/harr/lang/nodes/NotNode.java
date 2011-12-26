package harr.lang.nodes;

import harr.lang.*;

/**
  Negate a value.
*/
public class NotNode extends Node {
  private Node receiver;
  
  /**
    !receiver
  */
  public NotNode(Node receiver) {
    this.receiver = receiver;
  }
  
  public HarrObject eval(Context context) throws HarrException {
    if (receiver.eval(context).isTrue())
      return HarrRuntime.getFalse();
    return HarrRuntime.getTrue();
  }
}