package harr.lang;

import harr.lang.nodes.Node;

/**
  Anything that can be evaluated inside a context must implement this interface.
*/
public interface Evaluable {
  HarrObject eval(Context context) throws HarrException;
}
