package harr.lang.nodes;

import harr.lang.*;

public class InstanceVariableNode extends Node {
  private String name;
  
  public InstanceVariableNode(String name) {
    this.name = name;
  }
  
  public HarrObject eval(Context context) throws HarrException {
    return context.getCurrentSelf().getInstanceVariable(name);
  }
}