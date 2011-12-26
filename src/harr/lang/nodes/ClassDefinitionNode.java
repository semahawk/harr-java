package harr.lang.nodes;

import java.util.List;

import harr.lang.*;

public class ClassDefinitionNode extends Node {
  private String name;
  private String superName;
  private Node body;
  
  public ClassDefinitionNode(String name, String superName, Node body) {
    this.name = name;
    this.superName = superName;
    this.body = body;
  }
  
  public HarrObject eval(Context context) throws HarrException {
    HarrClass klass;
    // Default superclass to Object.
    if (superName == null) {
      klass = new HarrClass(name);
    } else {
      HarrClass superClass = (HarrClass) context.getCurrentClass().getConstant(superName);
      klass = new HarrClass(name, superClass);
    }
    
    // Evaluated the body of the class with self=class and class=class.
    body.eval(new Context(klass, klass));
    // Add the class as a constant
    context.getCurrentClass().setConstant(name, klass);
    
    return klass;
  }
}