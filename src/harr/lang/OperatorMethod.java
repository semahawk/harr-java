package harr.lang;

/**
  Specialized method of operators (+, -, *, /, etc.)
*/
public abstract class OperatorMethod<T> extends Method {
  @SuppressWarnings("unchecked")
  public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
    T self = (T) receiver.as(ValueObject.class).getValue();
    T arg = (T) arguments[0].as(ValueObject.class).getValue();
    return perform(self, arg);
  }
  
  public abstract HarrObject perform(T receiver, T argument) throws HarrException;
}
