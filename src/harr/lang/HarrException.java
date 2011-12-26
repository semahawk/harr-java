package harr.lang;

/**
  Exception that can be catched from inside the runtime.
*/
public class HarrException extends Exception {
  private HarrClass runtimeClass;
  
  /**
    Creates a new exception from a runtime class.
    @param runtimeClass Class of the exception from whitin the language.
  */
  public HarrException(HarrClass runtimeClass, String message) {
    super(message);
    this.runtimeClass = runtimeClass;
  }

  public HarrException(HarrClass runtimeClass) {
    super();
    this.runtimeClass = runtimeClass;
  }
  
  public HarrException(String runtimeClassName, String message) {
    super(message);
    setRuntimeClass(runtimeClassName);
  }
  
  /**
    Creates a new exception from the Exception runtime class.
  */
  public HarrException(String message) {
    super(message);
    this.runtimeClass = HarrRuntime.getExceptionClass();
  }
  
  /**
    Wrap an exception to pass it to the runtime.
  */
  public HarrException(Exception inner) {
    super(inner);
    setRuntimeClass(inner.getClass().getName());
  }
  
  /**
    Returns the runtime instance (the exception inside the language) of this exception.
  */
  public HarrObject getRuntimeObject() {
    HarrObject instance = runtimeClass.newInstance(this);
    instance.setInstanceVariable("message", new ValueObject(getMessage()));
    return instance;
  }

  public HarrClass getRuntimeClass() {
    return runtimeClass;
  }

  protected void setRuntimeClass(String name) {
    runtimeClass = HarrRuntime.getExceptionClass().subclass(name);
  }
}