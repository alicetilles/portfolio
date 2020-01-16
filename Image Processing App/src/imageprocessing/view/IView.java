package imageprocessing.view;

import java.awt.image.BufferedImage;

import imageprocessing.controller.Features;

/**
 * The user interface of the image processing package. To be implemented in v3.0.
 */
public interface IView {

  /** Adds an image on the display and removes the current image on display.
   * @param image The image to be displayed
   */
  void displayImage(BufferedImage image);

  /**
   * Prompts the user to choose a .jpg, .gif, or .png image to load in the view.
   * It returns the path that the user has chosen for the controller to process.
   */
  void openLoadDialogue();

  /**
   * Prompts the user to choose a .txt file in memory which has a series of
   * batch-processing commands.
   */
  void openBatchLoadDialogue();

  /**
   * Prompts the user to choose where to save the currently open image into a file.
   */
  void openSaveDialogue();

  /** Returns the most previously loaded filepath of this View.
   */
  boolean openUnsavedChanges();

  String getFilePath();

  /**
   * Prompts the user to choose a country of which they want to make a flag of.
   * @return the country chosen
   */
  String flagDialog();

  /**
   * Prompts the user to enter the orientation of the desired rainbow image they want drawn.
   * @return the chosen orientation
   */
  String rainbowDialog();

  /**
   * Prompts the user to choose a width in pixels for their image (a flag or rainbow).
   * @return the width chosen
   */
  int widthDialog();

  /**
   * Prompts the user to enter a height between 54 (minimum for a flag) and 1000.
   * @return the height that the user has selected
   */
  int heightDialog();

  /**
   * Prompts the user to select a number between 1 and 150 (a reasonable maximum), which
   * will be the square size in pixels of the checkerboard they'd like to create.
   * @return the square size in pixels of the checkerboard
   */
  int checkerboardDialog();

  /**
   * Allows or disallows the undo button to be clicked on.
   * @param b If true, enable undo menu item. False to disable.
   */
  void toggleUndo(boolean b);

  /**
   * Allows or disallows the redo button to be clicked on.
   * @param b If true, enable redo menu item. False to disable.
   */
  void toggleRedo(boolean b);

  /**
   * Allows or disallows the adjustment menu items to be clicked.
   * @param b If true, enable adjustment menu items and save item. False to disable.
   */
  void toggleAdjustments(boolean b);

  /** Sets the size of the image so that the image panel is 100 pixels wider and taller
   * than the input width and height specifications.
   * @param width The desired width
   * @param height The desired height
   */
  void setSize(int width, int height);

  /**
   * Adds every menu item as listeners to the features interface.
   * @param features The features interface which implements our model
   */
  void addFeatures(Features features);

  /**
   * Throws up a popup to the user that something went wrong.
   */
  public void errorDialog();
}
