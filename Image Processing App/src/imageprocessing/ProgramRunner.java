package imageprocessing;

import java.io.FileReader;
import java.io.IOException;

import imageprocessing.controller.ControllerImpl;
import imageprocessing.controller.IController;
import imageprocessing.model.IModel;
import imageprocessing.model.ModelImpl;
import imageprocessing.view.IView;
import imageprocessing.view.ViewImpl;

/**
 * This class is a driver for the image processing package.
 */
public class ProgramRunner {

  /**
   * Main method of a driver. Imports images, manipulates them and outputs the result. Also
   * generates images such as rainbows and flags.
   */
  public static void main(String[] args) throws IOException {
    IModel model = new ModelImpl();
    IView view = new ViewImpl();

    if (args[0].equals("-script")) {
      FileReader in = new FileReader(args[1]);
      IController controller = new ControllerImpl(model, in);
      controller.goGo();

    } else if (args[0].equals("-interactive")) {
      Readable in = null;
      IController controller = new ControllerImpl(model, in);
      controller.setView(view);
    }

  }
}
