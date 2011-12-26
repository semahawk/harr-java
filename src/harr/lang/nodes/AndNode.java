package harr.lang.nodes;

import harr.lang.*;

public class AndNode extends Node {
  private Node receiver;
  private Node argument;
  
  /**
    receiver && argument
  */
  public AndNode(Node receiver, Node argument) {
    this.receiver = receiver;
    this.argument = argument;
  }
  
  public HarrObject eval(Context context) throws HarrException {
    HarrObject receiverEvaled = receiver.eval(context);
    if (receiverEvaled.isTrue())
      return argument.eval(context);
    return receiverEvaled;
  }
}