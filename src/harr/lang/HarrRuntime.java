package harr.lang;

/**
  Language runtime. Mostly helper methods for retrieving global values.
*/
public class HarrRuntime {
  static HarrClass objectClass;
  static HarrObject mainObject;
  static HarrObject nilObject;
  static HarrObject trueObject;
  static HarrObject falseObject;
  
  public static HarrClass getObjectClass() {
    return objectClass;
  }

  public static HarrObject getMainObject() {
    return mainObject;
  }

  public static HarrClass getRootClass(String name) {
    // objectClass is null when boostrapping
    return objectClass == null ? null : (HarrClass) objectClass.getConstant(name);
  }

  public static HarrClass getExceptionClass() {
    return getRootClass("Exception");
  }
  
  public static HarrObject getNil() {
    return nilObject;
  }
  
  public static HarrObject getTrue() {
    return trueObject;
  }

  public static HarrObject getFalse() {
    return falseObject;
  }
  
  public static HarrObject toBoolean(boolean value) {
    return value ? HarrRuntime.getTrue() : HarrRuntime.getFalse();
  }
}
