package harr.lang;

import java.util.HashMap;

/**
  Any object, instance of a class, inside the runtime.
  Objects store a class and instance variables.
*/
public class HarrObject {
  private HarrClass yourLangClass;
  private HashMap<String, HarrObject> instanceVariables;
  
  /**
    Creates an instance of class yourLangClass.
  */
  public HarrObject(HarrClass yourLangClass) {
    this.yourLangClass = yourLangClass;
    this.instanceVariables = new HashMap<String, HarrObject>();
  }
  
  public HarrObject(String className) {
    this(HarrRuntime.getRootClass(className));
  }
  
  public HarrObject() {
    this(HarrRuntime.getObjectClass());
  }
  
  public HarrClass getHarrClass() {
    return yourLangClass;
  }
  
  public void setHarrClass(HarrClass klass) {
    yourLangClass = klass;
  }
  
  public HarrObject getInstanceVariable(String name) {
    if (hasInstanceVariable(name))
      return instanceVariables.get(name);
    return HarrRuntime.getNil();
  }

  public boolean hasInstanceVariable(String name) {
    return instanceVariables.containsKey(name);
  }
  
  public void setInstanceVariable(String name, HarrObject value) {
    instanceVariables.put(name, value);
  }
  
  /**
    Call a method on the object.
  */
  public HarrObject call(String method, HarrObject arguments[]) throws HarrException {
    return yourLangClass.lookup(method).call(this, arguments);
  }

  public HarrObject call(String method) throws HarrException {
    return call(method, new HarrObject[0]);
  }
  
  /**
    Only false and nil are not true.
  */
  public boolean isTrue() {
    return !isFalse();
  }
  
  /**
    Only false and nil are false. This is overridden in ValueObject.
  */
  public boolean isFalse() {
    return false;
  }

  /**
    Only nil is nil. This is overridden in ValueObject.
  */
  public boolean isNil() {
    return false;
  }
  
  /**
    Convert to a Java object. This is overridden in ValueObject.
  */
  public Object toJavaObject() {
    return this;
  }
  
  public <T> T as(Class<T> clazz) throws TypeError {
    if (clazz.isInstance(this)){
      return clazz.cast(this);
    }
    throw new TypeError(clazz.getName(), this);
  }
  
  public String asString() throws TypeError {
    return as(ValueObject.class).getValueAs(String.class);
  }

  public Integer asInteger() throws TypeError {
    return as(ValueObject.class).getValueAs(Integer.class);
  }

  public Float asFloat() throws TypeError {
    return as(ValueObject.class).getValueAs(Float.class);
  }
}
