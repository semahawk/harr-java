package harr.lang.nodes;

import harr.lang.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
  Method call.
*/
public class CallNode extends Node {
  private Node receiver;
  private String method;
  private List<Node> arguments;
  
  /**
    Call method on receiver with arguments: receiver.method(arguments)
  */
  public CallNode(String method, Node receiver, List<Node> arguments) {
    this.method = method;
    this.receiver = receiver;
    this.arguments = arguments;
  }
  
  public CallNode(String method, Node receiver, Node argument) {
    this(method, receiver, Arrays.asList(new Node[] { argument }));
  }

  public CallNode(String method, List<Node> arguments) {
    this(method, null, arguments);
  }

  public CallNode(String method) {
    this(method, null);
  }
  
  public void setReceiver(Node receiver) {
    this.receiver = receiver;
  }
  
  /**
    Make the method call.
  */
  public HarrObject eval(Context context) throws HarrException {
    // If no receiver and not arguments were specied and a local variable or the same
    // name exists, then it's a local variable access.
    if (receiver == null && arguments == null && context.hasLocal(method)) {
      return context.getLocal(method);
    }
    
    // Default receiver to self.
    HarrObject evaledReceiver;
    if (receiver == null) {
      evaledReceiver = context.getCurrentSelf();
    } else {
      evaledReceiver = receiver.eval(context);
    }
    
    // Evaluated each argument in the calling context.
    ArrayList<HarrObject> evaledArguments = new ArrayList<HarrObject>();
    if (arguments != null) {
      for (Node arg : arguments) evaledArguments.add(arg.eval(context));
    }
    
    // Call the method.
    return evaledReceiver.call(method, (HarrObject[])evaledArguments.toArray(new HarrObject[0]));
  }
}