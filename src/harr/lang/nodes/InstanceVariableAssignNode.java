package harr.lang.nodes;

import harr.lang.*;

public class InstanceVariableAssignNode extends Node {
  private String name;
  private Node expression;
  
  public InstanceVariableAssignNode(String name, Node expression) {
    this.name = name;
    this.expression = expression;
  }
  
  public HarrObject eval(Context context) throws HarrException {
    HarrObject value = expression.eval(context);
    context.getCurrentSelf().setInstanceVariable(name, value);
    return value;
  }
}