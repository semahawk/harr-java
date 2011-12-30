package harr;

import java.io.Reader;
import java.io.StringReader;
import java.io.FileReader;

import harr.Interactive;
import harr.Version;
import harr.lang.Bootstrapper;

public class Main {
  public static void usage() {
    System.out.println("usage: harr file.harr\n");
    System.out.println("options:");
    System.out.println("  -e              Evaluate code inline");
    System.out.println("  -d              Run in debug mode");
    System.out.println("  --version       Print version and exit");
  }

  public static void main(String[] args) throws Exception {
    Reader reader = null;
    boolean debug = false;
    
    if (args.length >= 1){
      for (int i = 0; i < args.length; i++) {
        if (args[i].equals("-e")) reader = new StringReader(args[++i]);
        else if (args[i].equals("-d")) debug = true;
        else if (args[i].equals("--version")) {
          System.out.println("Harr! version " + Version.MAJOR + "." + Version.MINOR + "." + Version.PATCH);
          System.exit(1);
        }
        else reader = new FileReader(args[i]);
      }

      Bootstrapper.run().eval(reader);
    } else {
      Interactive.run();
    }
  }
}
