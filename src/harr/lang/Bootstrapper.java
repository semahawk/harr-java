package harr.lang;

import java.io.*;

/**
  Bootstrapper.run() is called to initialize the runtime.
  Core classes are created and methods are added.
*/
public class Bootstrapper {
  static public Context run() {
    // Create core classes
    HarrClass objectClass = new HarrClass("Object");
    HarrRuntime.objectClass = objectClass;
    // Each method sent or added on the root context of a script are evaled on the main object.
    HarrObject main = new HarrObject();
    HarrRuntime.mainObject = main;
    HarrClass classClass = new HarrClass("Class");
    objectClass.setHarrClass(classClass); // Object is a class
    classClass.setHarrClass(classClass); // Class is a class
    main.setHarrClass(objectClass);
    
    // Register core classes into the root context
    objectClass.setConstant("Object", objectClass);
    objectClass.setConstant("Class", classClass);
    // There is just one instance of nil, true, false, so we store those in constants.
    HarrRuntime.nilObject = objectClass.newSubclass("NilClass").newInstance(null);
    HarrRuntime.trueObject = objectClass.newSubclass("TrueClass").newInstance(true);
    HarrRuntime.falseObject = objectClass.newSubclass("FalseClass").newInstance(false);
    HarrClass stringClass = objectClass.newSubclass("String");
    HarrClass numberClass = objectClass.newSubclass("Number");
    HarrClass integerClass = numberClass.newSubclass("Integer");
    HarrClass floatClass = numberClass.newSubclass("Float");
    HarrClass exceptionClass = objectClass.newSubclass("Exception");
    exceptionClass.newSubclass("IOException");
    exceptionClass.newSubclass("TypeError");
    exceptionClass.newSubclass("MethodNotFound");
    exceptionClass.newSubclass("ArgumentError");
    exceptionClass.newSubclass("FileNotFound");
    
    // Add methods to core classes.
    
    //// Object
    objectClass.addMethod("rawr", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        for (HarrObject arg : arguments) System.out.println(arg.toJavaObject());
        return HarrRuntime.getNil();
      }
    });
    objectClass.addMethod("class", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        return receiver.getHarrClass();
      }
    });
    objectClass.addMethod("eval", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        Context context = new Context(receiver);
        String code = arguments[0].asString();
        return context.eval(code);
      }
    });
    objectClass.addMethod("loot", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        Context context = new Context();
        String filename = arguments[0].asString();
        try {
          return context.eval(new FileReader(filename));
        } catch (FileNotFoundException e) {
          throw new HarrException("FileNotFound", "File not found: " + filename);
        }
      }
    });
    
    //// Class
    classClass.addMethod("new", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        HarrClass self = (HarrClass) receiver;
        HarrObject instance = self.newInstance();
        if (self.hasMethod("initialize")) instance.call("initialize", arguments);
        return instance;
      }
    });
    classClass.addMethod("name", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        HarrClass self = (HarrClass) receiver;
        return new ValueObject(self.getName());
      }
    });
    classClass.addMethod("superclass", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        HarrClass self = (HarrClass) receiver;
        return self.getSuperClass();
      }
    });

    //// Exception
    exceptionClass.addMethod("initialize", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        if (arguments.length == 1) receiver.setInstanceVariable("message", arguments[0]);
        return HarrRuntime.getNil();
      }
    });
    exceptionClass.addMethod("message", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        return receiver.getInstanceVariable("message");
      }
    });
    objectClass.addMethod("raise!", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        String message = null;
        if (receiver.hasInstanceVariable("message")) message = receiver.getInstanceVariable("message").asString();
        throw new HarrException(receiver.getHarrClass(), message);
      }
    });
    
    //// Integer
    integerClass.addMethod("+", new OperatorMethod<Integer>() {
      public HarrObject perform(Integer receiver, Integer argument) throws HarrException {
        return new ValueObject(receiver + argument);
      }
    });
    integerClass.addMethod("-", new OperatorMethod<Integer>() {
      public HarrObject perform(Integer receiver, Integer argument) throws HarrException {
        return new ValueObject(receiver + argument);
      }
    });
    integerClass.addMethod("*", new OperatorMethod<Integer>() {
      public HarrObject perform(Integer receiver, Integer argument) throws HarrException {
        return new ValueObject(receiver * argument);
      }
    });
    integerClass.addMethod("/", new OperatorMethod<Integer>() {
      public HarrObject perform(Integer receiver, Integer argument) throws HarrException {
        return new ValueObject(receiver / argument);
      }
    });
    integerClass.addMethod("<", new OperatorMethod<Integer>() {
      public HarrObject perform(Integer receiver, Integer argument) throws HarrException {
        return HarrRuntime.toBoolean(receiver < argument);
      }
    });
    integerClass.addMethod(">", new OperatorMethod<Integer>() {
      public HarrObject perform(Integer receiver, Integer argument) throws HarrException {
        return HarrRuntime.toBoolean(receiver > argument);
      }
    });
    integerClass.addMethod("<=", new OperatorMethod<Integer>() {
      public HarrObject perform(Integer receiver, Integer argument) throws HarrException {
        return HarrRuntime.toBoolean(receiver <= argument);
      }
    });
    integerClass.addMethod(">=", new OperatorMethod<Integer>() {
      public HarrObject perform(Integer receiver, Integer argument) throws HarrException {
        return HarrRuntime.toBoolean(receiver >= argument);
      }
    });
    integerClass.addMethod("==", new OperatorMethod<Integer>() {
      public HarrObject perform(Integer receiver, Integer argument) throws HarrException {
        return HarrRuntime.toBoolean(receiver == argument);
      }
    });
    
    //// String
    stringClass.addMethod("+", new OperatorMethod<String>() {
      public HarrObject perform(String receiver, String argument) throws HarrException {
        return new ValueObject(receiver + argument);
      }
    });
    stringClass.addMethod("size", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        String self = receiver.asString();
        return new ValueObject(self.length());
      }
    });
    stringClass.addMethod("substring", new Method() {
      public HarrObject call(HarrObject receiver, HarrObject arguments[]) throws HarrException {
        String self = receiver.asString();
        if (arguments.length == 0) throw new ArgumentError("substring", 1, 0);
        int start = arguments[0].asInteger();
        int end = self.length();
        if (arguments.length > 1) end = arguments[1].asInteger();
        return new ValueObject(self.substring(start, end));
      }
    });
    
    // Return the root context on which everything will be evaled. By default, everything is evaled on the
    // main object.
    return new Context(main);
  }
}
