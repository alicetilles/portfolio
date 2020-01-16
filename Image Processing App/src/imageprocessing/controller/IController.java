package imageprocessing.controller;

import java.io.IOException;

import imageprocessing.view.IView;

/**
 * The controller parses input, and determines what the model and view should do with
 * what the user wants based on the input file. It passes control around.
 */
public interface IController {

  /** Takes as input a path, selected by the user in the view GUI filechooser, and processes the
   * text file as a batch process.
   * @param inputFilePath The .txt file path
   * @throws IOException If the .txt file can not be found
   */
  public void goBatch(String inputFilePath) throws IOException;

  /**
   * The goGo method initializes a batch process with a command line argument. It sends
   * the input stream to goUniversal, which parses the input text file and gets the model
   * to process the input.
   *
   * @throws IOException              If the input file to be parsed can not be found
   * @throws IllegalArgumentException If the input has an unrecognized command
   * @throws NullPointerException     If the model can't be found
   */
  public void goGo() throws IOException, IllegalArgumentException, NullPointerException;

  /** Adds a GUI view class from the MVC architecture for this controller to communicate with.
   * @param v The view to associate with this controller
   */
  void setView(IView v);
}
