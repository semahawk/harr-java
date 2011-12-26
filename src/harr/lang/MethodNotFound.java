package harr.lang;

/**
  Exception thrown when a unknown method is called.
*/
public class MethodNotFound extends HarrException {
  public MethodNotFound(String method) {
    super(method + " not found");
    setRuntimeClass("MethodNotFound");
  }
}