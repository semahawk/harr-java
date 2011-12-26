package harr.lang.nodes;

import harr.lang.*;
import java.util.ArrayList;

/**
  Collection of nodes.
*/
public class Nodes extends Node {
  private ArrayList<Node> nodes;
  
  public Nodes() {
    nodes = new ArrayList<Node>();
  }
  
  public void add(Node n) {
    nodes.add(n);
  }
  
  /**
    Eval all the nodes and return the last returned value.
  */
  public HarrObject eval(Context context) throws HarrException {
    HarrObject lastEval = HarrRuntime.getNil();
    for (Node n : nodes) {
      lastEval = n.eval(context);
    }
    return lastEval;
  }
}