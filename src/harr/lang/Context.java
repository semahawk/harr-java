package harr.lang;

import java.util.HashMap;
import java.util.ArrayList;

import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import harr.lang.nodes.Node;


/**
  Evaluation context. Determines how a node will be evaluated.
  A context tracks local variables, self, and the current class under which
  methods and constants will be added.
  
  There are three different types of context:
  1) In the root of the script, self = main object, class = Object
  2) Inside a method body, self = instance of the class, class = method class
  3) Inside a class definition self = the class, class = the class
*/
public class Context {
  private HarrObject currentSelf;
  private HarrClass currentClass;
  private HashMap<String, HarrObject> locals;
  // A context can share local variables with a parent. For example, in the
  // try block.
  private Context parent;
  
  public Context(HarrObject currentSelf, HarrClass currentClass, Context parent) {
    this.currentSelf = currentSelf;
    this.currentClass = currentClass;
    this.parent = parent;
    if (parent == null) {
      locals = new HashMap<String, HarrObject>();
    } else {
      locals = parent.locals;
    }
  }
  
  public Context(HarrObject currentSelf, HarrClass currentClass) {
    this(currentSelf, currentClass, null);
  }
  
  public Context(HarrObject currentSelf) {
    this(currentSelf, currentSelf.getHarrClass());
  }
  
  public Context() {
    this(HarrRuntime.getMainObject());
  }
  
  public HarrObject getCurrentSelf() {
    return currentSelf;
  }

  public HarrClass getCurrentClass() {
    return currentClass;
  }
  
  public HarrObject getLocal(String name) {
    return locals.get(name);
  }
    
  public boolean hasLocal(String name) {
    return locals.containsKey(name);
  }
    
  public void setLocal(String name, HarrObject value) {
    locals.put(name, value);
  }
  
  /**
    Creates a context that will share the same attributes (locals, self, class)
    as the current one.
  */
  public Context makeChildContext() {
    return new Context(currentSelf, currentClass, this);
  }
  
  /**
    Parse and evaluate the content red from the reader (eg.: FileReader, StringReader).
  */
  public HarrObject eval(Reader reader) throws HarrException {
    try {
      HarrLexer lexer = new HarrLexer(new ANTLRReaderStream(reader));
      HarrParser parser = new HarrParser(new CommonTokenStream(lexer));
      Node node = parser.parse();
      if (node == null) return HarrRuntime.getNil();
      return node.eval(this);
    } catch (HarrException e) {
      throw e;
    } catch (Exception e) {
      throw new HarrException(e);
    }
  }
  
  public HarrObject eval(String code) throws HarrException {
    return eval(new StringReader(code));
  }
}
