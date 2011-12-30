package harr;

import java.io.Reader;
import java.io.StringReader;
import java.io.PrintWriter;

import harr.lang.Bootstrapper;

import jline.*;

// TODO: Make it show the evaled values:
//       
//       Harr!> 2 + 2 * 2
//       => 6
//       Harr!> a = 10 / 2
//       => 5
//       Harr!> a
//       => 5

public class Interactive {
  public static void run() throws Exception {
    System.out.println("The interactive result-spitting beast for Harr! language.");
    System.out.println("Type \"exit\" or \"quit\" to get yerself outta here.\n");

    ConsoleReader reader = new ConsoleReader();
    reader.setBellEnabled(false);

    String line;
    PrintWriter out = new PrintWriter(System.out);

    while ((line = reader.readLine("Harr!> ")) != null){
      if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")){
        break;
      }

      Reader lineReader = new StringReader(line);

      try {
        Bootstrapper.run().eval(lineReader);
      } catch (Exception ex){
        out.println(ex.getMessage());
      }

      out.flush();
    }
  }
}
