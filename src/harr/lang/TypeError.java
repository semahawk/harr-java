package harr.lang;

/**
  Exception raised when an unexpected object type is passed to as a method argument.
*/
public class TypeError extends HarrException {
  public TypeError(String expected, Object actual) {
    super("Expected type " + expected + ", got " + actual.getClass().getName());
    setRuntimeClass("TypeError");
  }
}