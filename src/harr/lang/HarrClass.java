package harr.lang;

import java.util.HashMap;

/**
  Class in the runtime.
  Classes store methods and constants. Each object in the runtime has a class.
*/
public class HarrClass extends HarrObject {
  private String name;
  private HarrClass superClass;
  private HashMap<String, Method> methods;
  HashMap<String, HarrObject> constants;
  
  /**
    Creates a class inheriting from superClass.
  */
  public HarrClass(String name, HarrClass superClass) {
    super("Class");
    this.name = name;
    this.superClass = superClass;
    methods = new HashMap<String, Method>();
    constants = new HashMap<String, HarrObject>();
  }
  
  /**
    Creates a class inheriting from Object.
  */
  public HarrClass(String name) {
    this(name, HarrRuntime.getObjectClass());
  }
  
  public String getName() {
    return name;
  }
  
  public HarrClass getSuperClass() {
    return superClass;
  }
  
  public void setConstant(String name, HarrObject value) {
    constants.put(name, value);
  }

  public HarrObject getConstant(String name) {
    if (constants.containsKey(name)) return constants.get(name);
    if (superClass != null) return superClass.getConstant(name);
    return HarrRuntime.getNil();
  }
  
  public boolean hasConstant(String name) {
    if (constants.containsKey(name)) return true;
    if (superClass != null) return superClass.hasConstant(name);
    return false;
  }
  
  public Method lookup(String name) throws MethodNotFound {
    if (methods.containsKey(name)) return methods.get(name);
    if (superClass != null) return superClass.lookup(name);
    throw new MethodNotFound(name);
  }

  public boolean hasMethod(String name) {
    if (methods.containsKey(name)) return true;
    if (superClass != null) return superClass.hasMethod(name);
    return false;
  }
  
  public void addMethod(String name, Method method) {
    methods.put(name, method);
  }
  
  /**
    Creates a new instance of the class.
  */
  public HarrObject newInstance() {
    return new HarrObject(this);
  }
  
  /**
    Creates a new instance of the class, storing the value inside a ValueObject.
  */
  public HarrObject newInstance(Object value) {
    return new ValueObject(this, value);
  }
  
  /**
    Creates a new subclass of this class.
  */
  public HarrClass newSubclass(String name) {
    HarrClass klass = new HarrClass(name, this);
    HarrRuntime.getObjectClass().setConstant(name, klass);
    return klass;
  }
  
  /**
    Creates or returns a subclass if it already exists.
  */
  public HarrClass subclass(String name) {
    HarrClass objectClass = HarrRuntime.getObjectClass();
    if (objectClass.hasConstant(name)) return (HarrClass) objectClass.getConstant(name);
    return newSubclass(name);
  }
  
  /**
    Returns true if klass is a subclass of this class.
  */
  public boolean isSubclass(HarrClass klass) {
    if (klass == this) return true;
    if (klass.getSuperClass() == null) return false;
    if (klass.getSuperClass() == this) return true;
    return isSubclass(klass.getSuperClass());
  }
  
  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if ( !(other instanceof HarrClass) ) return false;
    return name == ((HarrClass)other).getName();
  }
}
