package harr.lang.nodes;

import harr.lang.*;

public class WhileNode extends Node {
  private Node condition;
  private Node body;
  
  public WhileNode(Node condition, Node body) {
    this.condition = condition;
    this.body = body;
  }
  
  public HarrObject eval(Context context) throws HarrException {
    while (condition.eval(context).isTrue()) {
      body.eval(context);
    }
    return HarrRuntime.getNil();
  }
}