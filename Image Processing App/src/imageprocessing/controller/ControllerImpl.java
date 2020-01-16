package imageprocessing.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;

import imageprocessing.model.IModel;
import imageprocessing.model.image.Country;
import imageprocessing.model.image.Orientation;
import imageprocessing.view.IView;

/**
 * The controller takes input from the user and decides what to do. It is the client of the model
 * and it controls how and when the model is used. It also controls what must be shown by the view
 * and when. This controller applies filters and transformations onto any sort of image.
 */
public class ControllerImpl implements IController, Features {
  final Readable in;
  //  final Appendable out;
  IModel model;
  IView view;

  int flagCount;
  int rainbowCount;
  int checkerboardCount;
  String currentImage;
  Stack<String> undoStack;
  Stack<String> redoStack;

  boolean isSaved;


  /**
   * Initializes the controller of the imageprocessing package. The controller takes input from the
   * user and decides what to do. It also controls what must be shown by the view and when.
   *
   * @param model the model associated with this controller.
   * @param in    an object implementing the Readable interface to parse.
   */
  public ControllerImpl(IModel model, Readable in) {
    this.in = in;
    this.model = model;
    this.undoStack = new Stack<>();
    this.redoStack = new Stack<>();
    this.flagCount = 0;
    this.checkerboardCount = 0;
    this.rainbowCount = 0;
    this.currentImage = "./res/welcome.png";
    this.isSaved = false;

  }

  /** Adds a GUI view class from the MVC architecture for this controller to communicate with.
   * @param v The view to associate with this controller
   */
  public void setView(IView v) {
    view = v; // provide view with all the callbacks
    view.addFeatures(this);
    view.toggleAdjustments(false);
  }

  /** Takes as input a path, selected by the user in the view GUI filechooser, and processes the
   * text file as a batch process.
   * @param inputFilePath The .txt file path
   * @throws IOException If the .txt file can not be found
   */
  public void goBatch(String inputFilePath) throws IOException {
    File file = new File(inputFilePath);
    Scanner scan = new Scanner(file);
    this.goUniversal(scan);
  }

  /**
   * The goGo method initializes a batch process with a command line argument. It sends
   * the input stream to goUniversal, which parses the input text file and gets the model
   * to process the input.
   *
   * @throws IOException              If the input file to be parsed can not be found
   * @throws IllegalArgumentException If the input has an unrecognized command
   * @throws NullPointerException     If the model can't be found
   */
  public void goGo() throws IOException, IllegalArgumentException, NullPointerException {
    Scanner scan = new Scanner(this.in);
    this.goUniversal(scan);
    quit();
  }

  /**
   * This method ives control to the controller (this class) until the program ends. It parses
   * any type of text input via a Scanner object, and has a switch-case design to determine which
   * model methods should be run to complete the user's intended action(s).
   *
   * @throws IOException              If the input file to be parsed can not be found
   * @throws IllegalArgumentException If the input has an unrecognized command
   * @throws NullPointerException     If the model can't be found
   */
  private void goUniversal(Scanner scan) throws IOException, IllegalArgumentException,
          NullPointerException {

    // Checks that the specified model is not null (if it is, throws null pointer exception)
    Objects.requireNonNull(model);

    // Initialize command and object of command
    String command;
    String imageName;
    String path;

    // Parse the input file
    while (scan.hasNext()) {
      command = scan.next();

      // Switch-case design to parse input
      switch (command) {

        // Loading a file adds it to our open, available images, with a readable name
        case "load":

          // When loading, get the file name:
          String filename;
          try {
            filename = scan.next();
          } catch (NoSuchElementException e) {
            view.errorDialog();
            return;
          }

          // Get the image name:
          try {
            imageName = scan.next();
          } catch (NoSuchElementException e) {
            // If they don't specify, then just call it the same name as the file
            imageName = filename;
          }

          // Load the file name as the image name:
          loadFromPath(filename, imageName);
          this.currentImage = imageName;
          break;

        // Each of the next cases is a 'verb' to apply to the next image
        case "dither":

          try {
            imageName = scan.next();
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println("Must specify the name of the image to adjust.");
            return;
          }

          this.model.applyDither(imageName);
          break;

        case "blur":

          try {
            imageName = scan.next();
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println("Must specify the name of the image to adjust.");
            return;
          }
          this.model.applyBlur(imageName);
          break;


        case "sharpen":
          try {
            imageName = scan.next();
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println("Must specify the name of the image to adjust.");
            return;
          }
          this.model.applySharpen(imageName);
          break;


        case "sepia":
          try {
            imageName = scan.next();
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println("Must specify the name of the image to adjust.");
            return;
          }
          this.model.applySepia(imageName);
          break;


        case "greyscale":
          try {
            imageName = scan.next();
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println("Must specify the name of the image to adjust.");
            return;
          }
          this.model.applyGreyscale(imageName);
          break;


        case "mosaic":
          try {
            imageName = scan.next();
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println("Must specify the name of the image to adjust.");
            return;
          }

          // The next number is the number of seeds for the mosaic
          int seed;
          try {
            seed = Integer.parseInt(scan.next());
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println("Must specify number of seeds for mosaic'ing.");
            return;
          }


          this.model.applyMosaic(imageName, seed);
          break;


        case "checkerboard":
          int squareSize;
          try {
            squareSize = Integer.parseInt(scan.next());
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println("Must specify the size of the squares.");
            return;
          }
          this.model.drawCheckerBoard(squareSize);
          break;

        case "flag":
          String country;
          try {
            country = scan.next().toLowerCase();
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println("Must specify the name of the country to generate.");
            return;
          }

          // Get the width of the flag:
          int fWidth;
          try {
            fWidth = Integer.parseInt(scan.next());
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println("Must specify the size of the flag to generate.");
            return;
          }

          // Only France, Greece, and Switzerland are included in this package.
          if (country.equals("france")) {
            this.model.drawFlag(fWidth, Country.FRANCE);
          } else if (country.equals("switzerland")) {
            this.model.drawFlag(fWidth, Country.SWITZERLAND);
          } else if (country.equals("greece")) {
            this.model.drawFlag(fWidth, Country.GREECE);
          } else {
            view.errorDialog();
            System.out.println("Sorry, that country (" + country
                    + ") hasn't been installed yet."
                    + "Maybe in version 4.0?");
            return;
          }
          break;


        case "rainbow":

          String orientation;
          int rWidth;
          int rHeight;
          try {
            orientation = scan.next();
            rHeight = Integer.parseInt(scan.next());
            rWidth = Integer.parseInt(scan.next());
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println(("Must specify the rainbow's orientation, "
                    + "width, and height."));
            return;
          }

          if (orientation.equals("horizontal")) {
            this.model.drawRainbow(rHeight, rWidth, Orientation.HORIZONTAL);
          } else if (orientation.equals("vertical")) {
            this.model.drawRainbow(rHeight, rWidth, Orientation.VERTICAL);
          } else {
            view.errorDialog();
            System.out.println("Sorry, that orientation hasn't been installed yet."
                    + "Maybe in version 4.0?");
            return;
          }
          break;

        // This case saves an image, based on its english name (not path) to a file
        case "save":
          try {
            imageName = scan.next();
          } catch (NoSuchElementException e) {
            view.errorDialog();
            System.out.println("Must specify the name of the image to save.");
            return;
          }
          try {
            path = scan.next();
          } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Must specify a path to save the image");
          }

          saveToPath(imageName, path);
          break;

        default:
          view.errorDialog();
          return;
      }
    }
  }

  /**
   * Converts a string from the view into a Country -- parsing user input to pass to the model.
   *
   * @param input The name of the country from the button
   * @return The name of the country in a Country enum
   * @throws IllegalArgumentException If the country in the button isn't found
   */
  private Country stringToCountry(String input) throws IllegalArgumentException {
    try {

      if (input.equals("Switzerland")) {
        return Country.SWITZERLAND;
      } else if (input.equals("France")) {
        return Country.FRANCE;
      } else if (input.equals("Greece")) {
        return Country.GREECE;
      }
      else {
        view.errorDialog();
        return null;
      }
    }

    // If the user hits 'cancel' , it's no big deal!
    catch (NullPointerException e) {
      return null;
    }
  }


  /**
   * Converts a string from the view into an Orientation -- parsing user input to pass to the
   * model.
   *
   * @param input The name of the orientation from the view
   * @return The name of the orientation in an Orientation enum
   * @throws IllegalArgumentException If the orientation in the button isn't found
   */
  private Orientation stringToOrientation(String input) throws IllegalArgumentException {
    try {
      if (input.equals("Horizontal")) {
        return Orientation.HORIZONTAL;
      } else if (input.equals("Vertical")) {
        return Orientation.VERTICAL;
      }
      view.errorDialog();
      System.out.println("Orientation not installed yet. Bonus pack is $14.99.");
      return null;
    }

    // If the user hits 'cancel' , it's no big deal!
    catch (NullPointerException e) {
      return null;
    }

  }

  /**
   * When the user clicks on the 'load' button, get the path they specified in the
   * filechooser, add it to the saved images folder in the model, and display the image.
   */
  public void load() {
    // When the user clicks on the 'load' button, load the image in the model
    view.openLoadDialogue();
    String lpath = view.getFilePath();
    try {
      loadFromPath(lpath, lpath);
    } catch (IOException exception) {
      view.errorDialog();
      System.out.println("There was a problem loading that image.");
      return;
    }
    this.currentImage = lpath;
    // Creates a BufferedImage of the image specified by the path.
    try {
      BufferedImage buffered = this.model.getImage(lpath);
      view.setSize(buffered.getWidth(), buffered.getHeight());
      // Displays the image in the view.
      view.displayImage(buffered);
      try {
        view.setSize(buffered.getWidth(), buffered.getHeight());

        // Displays the image in the view.
        view.displayImage(buffered);

        // Since you have opened an image you can now apply adjustments
        this.view.toggleAdjustments(true);
      }

      // Operation has been cancelled by user
      catch (NullPointerException e) {
        return;
      }
    }
    catch (NullPointerException e) {
      return;
    }

  }

  /**
   * When the user clicks on the batch load button, allow them to choose the .txt file to
   * load up with a list of commands, and then process the file via the model.
   */
  public void batchLoad() throws IllegalArgumentException {

    // When the user clicks on the 'batch load' button, get the script and run it
    view.openBatchLoadDialogue();

    String lpath = view.getFilePath();
    try {
      this.goBatch(lpath);
    } catch (IOException exception) {
      view.errorDialog();
    }

    // Operation has been cancelled by user
    catch (NullPointerException exception) {
      return;
    }

  }


  /** Processes a script that the user writes while the program is running.
   * @param script the list of commands that the user has written
   */
  public void batchWrite(String script) {
    Scanner s = new Scanner(script);
    try {
      this.goUniversal(s);
    } catch (IOException exception) {
      view.errorDialog();
      System.out.println("Error - file is not found.");
    }
  }

  /**When the user clicks on the 'save' button, save the image.
   * @throws IOException If there is something wrong with the image path
   */
  public void save() throws IOException {
    // When the user clicks on the 'save' button, save
    view.openSaveDialogue();
    String spath = view.getFilePath();

    BufferedImage output = model.getImage(currentImage);

    ImageIO.write(output, "png", new FileOutputStream(spath));
    this.isSaved = true;

  }

  /**
   * For saving during batch processing.
   * @param file The input file path
   * @param spath the output file path
   * @throws IOException When the input or output paths are illegal
   */
  private void saveToPath(String file, String spath) throws IOException {
    BufferedImage output = model.getImage(file);
    ImageIO.write(output, "png", new FileOutputStream(spath));
  }

  private void loadFromPath(String path, String name) throws IOException {
    BufferedImage input;

    input = ImageIO.read(new FileInputStream(path));
    this.model.passImage(input, name);
  }

  /**
   * When the user clicks the quit button, the program exits, without saving anything.
   * @throws IOException if the place they want to save it to doesn't exist
   */
  public void quit() throws IOException {

    // If there are unsaved changes, as the user if they want to save the current image
    if (!undoStack.empty() && !isSaved ) {

      // Show the dialog box, which returns true if they want to save and false otherwise
      if (view.openUnsavedChanges()) {
        this.save();
        System.exit(0);
      }
      else {
        System.exit(0);
      }
    }
    else {
      System.exit(0);
    }
  }


  /**
   * When the user pressed 'undo', save the current image on the redo stack,
   * and display the top of the undo stack.
   */
  public void undo() {
    // Start by pushing the current image onto the redo stack.
    this.redoStack.push(this.currentImage);
    // Next make the current image the top item on the undo stack.
    this.currentImage = this.undoStack.pop();
    // Update the display.
    BufferedImage bufferedU = this.model.getImage(this.currentImage);
    this.view.displayImage(bufferedU);
    // Update the undo/redo state.
    updateUndoRedo();

    this.isSaved = false;
  }

  /**
   * When the user clicks redo, put the current image on the undo stack, and display
   * the image on the top of the redo stack.
   */
  public void redo() {
    // Start by pushing the current image onto the undo stack.
    this.undoStack.push(this.currentImage);
    // Next make the current image the top item on the redo stack.
    this.currentImage = this.redoStack.pop();
    // Update the display.
    BufferedImage bufferedR = this.model.getImage(this.currentImage);
    this.view.displayImage(bufferedR);
    // Update the undo/redo state.
    updateUndoRedo();
    this.isSaved = false;
  }

  /**
   * Toggles undo/redo availability in the view on state changes.
   */
  private void updateUndoRedo() {
    if (this.undoStack.empty()) {
      view.toggleUndo(false);
    } else {
      view.toggleUndo(true);
    }
    if (this.redoStack.empty()) {
      view.toggleRedo(false);
    } else {
      view.toggleRedo(true);
    }
  }


  /**
   * When the user clicks 'blur' button, add the current image to undo stack, apply
   * the adjustment with the model, and display it. Clear the redo stack.
   */
  public void blur() {
    // Push the current image to the undo stack before anything else
    this.undoStack.push(currentImage);

    // Apply the blur to the model.
    this.model.applyBlur(currentImage);

    // Update and display the current image.
    this.currentImage = this.currentImage + "-blur";
    BufferedImage buffer = this.model.getImage(currentImage);
    view.displayImage(buffer);

    // If you apply an adjustment, the redo stack is cleared.
    this.redoStack.clear();
    updateUndoRedo();
    this.isSaved = false;
  }

  /**
   * When the user clicks 'sharpen' button, add the current image to undo stack, apply
   * the adjustment with the model, and display it. Clear the redo stack.
   */
  public void sharpen() {

    // Push the current image to the undo stack before anything else
    this.undoStack.push(currentImage);

    // Apply sharpen to the model.
    this.model.applySharpen(currentImage);

    // Update and display the current image.
    this.currentImage = this.currentImage + "-sharpen";
    BufferedImage buffer = this.model.getImage(currentImage);
    view.displayImage(buffer);

    // If you apply an adjustment, the redo stack is cleared.
    this.redoStack.clear();
    updateUndoRedo();
    this.isSaved = false;
  }

  /**
   * When the user clicks 'dither' button, add the current image to undo stack, apply
   * the adjustment with the model, and display it. Clear the redo stack.
   */
  public void dither() {

    // Push the current image to the undo stack before anything else
    this.undoStack.push(currentImage);

    // Apply dither to the model.
    this.model.applyDither(currentImage);

    // Update and display the current image.
    this.currentImage = this.currentImage + "-dither";
    BufferedImage buffer = this.model.getImage(currentImage);
    view.displayImage(buffer);

    // If you apply an adjustment, the redo stack is cleared.
    this.redoStack.clear();
    updateUndoRedo();
    this.isSaved = false;
  }

  /**
   * When the user clicks 'mosaic' button, add the current image to undo stack, apply
   * the adjustment with the model, and display it. Clear the redo stack.
   * @param seed the number of seeds (clusters) of the mosaic (user-specified)
   */
  public void mosaic(int seed) {

    if (seed < 1 ) {
      view.errorDialog();
      System.out.println("Not enough seeds");
      return;
    }

    // Push the current image to the undo stack before anything else
    this.undoStack.push(currentImage);

    // Apply mosaic to the model.
    this.model.applyMosaic(currentImage, seed);

    // Update and display the current image.
    this.currentImage = this.currentImage + "-mosaic";
    BufferedImage buffer = this.model.getImage(currentImage);
    view.displayImage(buffer);

    // If you apply an adjustment, the redo stack is cleared.
    this.redoStack.clear();
    updateUndoRedo();
    this.isSaved = false;
  }

  /**
   * When the user clicks 'sepia' button, add the current image to undo stack, apply
   * the adjustment with the model, and display it. Clear the redo stack.
   */
  public void sepia() {

    // Push the current image to the undo stack before anything else
    this.undoStack.push(currentImage);

    // Apply sepia to the model.
    this.model.applySepia(currentImage);

    // Update and display the current image.
    this.currentImage = this.currentImage + "-sepia";
    BufferedImage buffer = this.model.getImage(currentImage);
    view.displayImage(buffer);

    // If you apply an adjustment, the redo stack is cleared.
    this.redoStack.clear();
    updateUndoRedo();
    this.isSaved = false;
  }

  /**
   * When the user clicks 'greyscale' button, add the current image to undo stack, apply
   * the adjustment with the model, and display it. Clear the redo stack.
   */
  public void greyscale() {

    // Push the current image to the undo stack before anything else
    this.undoStack.push(currentImage);

    // Apply greyscale to the model.
    this.model.applyGreyscale(currentImage);

    // Update and display the current image.
    this.currentImage = this.currentImage + "-greyscale";
    BufferedImage buffer = this.model.getImage(currentImage);
    view.displayImage(buffer);

    // If you apply an adjustment, the redo stack is cleared.
    this.redoStack.clear();
    updateUndoRedo();
    this.isSaved = false;
  }


  /**
   * When the user clicks 'flag' button, find out from the user via dialogs in the view
   * what country flag they want, and the width in pixels of the flag they want. Then,
   * draw, display, and save the flag.
   */
  public void flag() {

    String chosenFlagString = view.flagDialog();

    // If they click 'cancel', do not continue prompting them.
    if (chosenFlagString == null) {
      return;
    }

    // Convert the string to a Country (which is what our model is expecting)
    Country chosenFlag = stringToCountry(chosenFlagString);

    int chosenWidth = view.widthDialog();

    // If they click 'cancel', do not make anything.
    if (chosenWidth == 0) {
      return;
    }

    model.drawFlag(chosenWidth, chosenFlag);
    this.flagCount++;

    // Load the flag into the open images in the model, and save it as a file.
    BufferedImage bufferedFlag;
    if (this.flagCount == 1) {
      bufferedFlag = this.model.getImage("flag");
      view.displayImage(bufferedFlag);
      this.currentImage = "flag";
      this.undoStack.push(currentImage);
      this.view.toggleAdjustments(true);
    }

    // If this isn't the first flag, the name is "flag" with a number appended
    else {
      bufferedFlag = this.model.getImage("flag-" + (flagCount - 1));
      view.displayImage(bufferedFlag);
      this.currentImage = "flag-" + (flagCount - 1);
      this.undoStack.push(currentImage);
      this.view.toggleAdjustments(true);
    }

  }


  /**
   * When the user clicks 'rainbow' button, find out from the user via dialogs in the view
   * what orientation they want, and the height and width in pixels they want. Then,
   * draw, display, and save the rainbow.
   */
  public void rainbow() {
    String chosenRainbowOrientation = view.rainbowDialog();

    // If they click 'cancel', do not continue prompting them.
    if (chosenRainbowOrientation == null) {
      return;
    }

    Orientation chosenOrientation = stringToOrientation(chosenRainbowOrientation);

    int chosenWidth = view.widthDialog();

    // If they click 'cancel', do not make anything.
    if (chosenWidth == 0) {
      return;
    }

    int chosenHeight = view.heightDialog();

    // If they click 'cancel', do not make anything.
    if (chosenHeight == 0) {
      return;
    }

    model.drawRainbow(chosenHeight, chosenWidth, chosenOrientation);
    this.rainbowCount++;

    // Load the flag into the open images in the model

    BufferedImage bufferedRainbow;
    // If this is the first rainbow, the name of it is "rainbow"
    if (this.rainbowCount == 1) {
      bufferedRainbow = this.model.getImage("rainbow");
      view.displayImage(bufferedRainbow);
      this.currentImage = "rainbow";
      this.undoStack.push(currentImage);
      this.view.toggleAdjustments(true);
    }

    // If this isn't the first rainbow, the name is "rainbow" with a number appended
    else {
      bufferedRainbow = this.model.getImage("rainbow-" + (rainbowCount - 1));
      view.displayImage(bufferedRainbow);
      this.currentImage = "rainbow-" + (rainbowCount - 1);
      this.undoStack.push(currentImage);
      this.view.toggleAdjustments(true);
    }
  }


  /**
   * When the user clicks 'checkerboard' button, find out from the user via dialogs in the view
   * what the height and width in pixels of each square they want. Then,
   * draw, display, and save the checkerboard.
   */
  public void checkerboard() {
    int checkerboardSize = view.checkerboardDialog();

    // If they click 'cancel', do not make anything.
    if (checkerboardSize == 0) {
      return;
    }

    model.drawCheckerBoard(checkerboardSize);
    this.checkerboardCount++;


    BufferedImage bufferedCheckerboard;
    // If this is the first checkerboard, the name of it is "checkerboard"
    if (this.checkerboardCount == 1) {
      bufferedCheckerboard = this.model.getImage("checkerboard");
      view.displayImage(bufferedCheckerboard);
      this.currentImage = "checkerboard";
      this.undoStack.push(currentImage);
      this.view.toggleAdjustments(true);
    }

    // If this isn't the first checkerboard, the name is "checkerboard" with a number appended
    else {
      bufferedCheckerboard = this.model.getImage("checkerboard-" + (checkerboardCount - 1));
      view.displayImage(bufferedCheckerboard);
      this.currentImage = "checkerboard-" + (checkerboardCount - 1);
      this.undoStack.push(currentImage);
      this.view.toggleAdjustments(true);

    }

  }

}
