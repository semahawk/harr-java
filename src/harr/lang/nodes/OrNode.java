package harr.lang.nodes;

import harr.lang.*;

public class OrNode extends Node {
  private Node receiver;
  private Node argument;
  
  /**
    receiver || argument
  */
  public OrNode(Node receiver, Node argument) {
    this.receiver = receiver;
    this.argument = argument;
  }
  
  public HarrObject eval(Context context) throws HarrException {
    HarrObject receiverEvaled = receiver.eval(context);
    if (receiverEvaled.isTrue())
      return receiverEvaled;
    return argument.eval(context);
  }
}