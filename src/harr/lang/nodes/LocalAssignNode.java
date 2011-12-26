package harr.lang.nodes;

import harr.lang.*;

public class LocalAssignNode extends Node {
  private String name;
  private Node expression;
  
  public LocalAssignNode(String name, Node expression) {
    this.name = name;
    this.expression = expression;
  }
  
  public HarrObject eval(Context context) throws HarrException {
    HarrObject value = expression.eval(context);
    context.setLocal(name, value);
    return value;
  }
}