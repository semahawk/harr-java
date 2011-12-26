package harr.lang.nodes;

import harr.lang.*;

/**
  Get the value of a constant.
*/
public class ConstantNode extends Node {
  private String name;
  
  public ConstantNode(String name) {
    this.name = name;
  }
  
  public HarrObject eval(Context context) throws HarrException {
    return context.getCurrentClass().getConstant(name);
  }
}