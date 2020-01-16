package imageprocessing.model;

import java.awt.image.BufferedImage;
import java.io.IOException;

import imageprocessing.model.image.Country;
import imageprocessing.model.image.Orientation;

/**
 * This is the model interface of the imageprocessing package. It can load and save images, apply
 * different types of filters to open images and draw flags, rainbows and checkerboards.
 */
public interface IModel {

  /**
   * Gets a BufferedImage from the controller and creates an Image object from it.
   * @param input BufferedImage to create an image out of.
   * @param name Name of the internal representation of the image.
   */

  void passImage(BufferedImage input, String name) throws IOException;

  /** Converts an image an Image in our model architecture into a BufferedImage.
   * @param title The name of the image to be converted
   * @return The BufferedImage version of that image
   */
  BufferedImage getImage(String title);

  /**
   * Creates a filtered version of the current image with a blur using a built-in kernel.
   * Doesn't mutate the image.
   *
   * @param title the name of the image the user wants blurred
   */
  void applyBlur(String title);

  /**
   * Creates a filtered version of the current image -- sharpened -- using a built-in kernel.
   * Doesn't mutate the image.
   *
   * @param title the name of the image the user wants sharpened
   */
  void applySharpen(String title);

  /**
   * Creates a filtered version of the current image -- in sepia.
   * Doesn't mutate the image.
   *
   * @param title the name of the image the user wants in sepia
   */
  void applySepia(String title);

  /**
   * Creates a filtered version of the current image -- in greyscale.
   * Doesn't mutate the image.
   *
   * @param title the name of the image the user wants in greyscale
   */
  void applyGreyscale(String title);

  /**
   * Creates a filtered version of the current image -- in dithered format,
   * appropriate for dot-matrix printers.
   * Doesn't mutate the image.
   * @param title the name of the image the user wants dithered
   */
  void applyDither(String title);

  /**
   * Creates a mosaic of the current image using a simplified k-means. The user specifies the
   * number of seeds. The more seeds, the more fine the dots in the mosaic.
   * Doesn't mutate the image.
   * @param title the name of the image the user wants mosaic'ed
   * @param seed the number of seeds (chunks) of the desired mosaic
   */
  void applyMosaic(String title, int seed);

  /**
   * Initializes a rainbow image -- 7 stripes of color, with user specified orientation and size.
   *
   * @param height The height, in pixels, of the desired rainbow image
   * @param width The width, in pixels, of the desired rainbow image
   * @param o The orientation of the desired rainbow image (vertical or horizontal)
   */
  void drawRainbow(int height, int width, Orientation o);

  /**
   * Initializes a checkerboard image (alternating equally-sized black and white squares),
   * where the square size is user-specified. A checkerboard is 8 squares x 8 squares.
   * @param squareSize the length/width of a square, in pixels
   */
  void drawCheckerBoard(int squareSize);


  /**
   * Initializes a flag image of a user-specified country and size.
   *
   * @param width the width of the desired flag, in pixels.
   * @param c the country of the desired flag. Currently supports France, Switzerland and Greece.
   */
  void drawFlag(int width, Country c);
}
