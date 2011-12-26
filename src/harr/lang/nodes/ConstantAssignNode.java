package harr.lang.nodes;

import harr.lang.*;

public class ConstantAssignNode extends Node {
  private String name;
  private Node expression;
  
  public ConstantAssignNode(String name, Node expression) {
    this.name = name;
    this.expression = expression;
  }
  
  public HarrObject eval(Context context) throws HarrException {
    HarrObject value = expression.eval(context);
    context.getCurrentClass().setConstant(name, value);
    return value;
  }
}