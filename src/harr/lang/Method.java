package harr.lang;

/**
  A method attached to a HarrClass.
*/
public abstract class Method {
  /**
    Calls the method.
    @param receiver  Instance on which to call the method (self).
    @param arguments Arguments passed to the method.
  */
  public abstract HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException;
}
