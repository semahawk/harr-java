package harr.lang.nodes;

import harr.lang.*;

public class LiteralNode extends Node {
  HarrObject value;
  
  public LiteralNode(HarrObject value) {
    this.value = value;
  }
  
  public HarrObject eval(Context context) throws HarrException {
    return value;
  }
}