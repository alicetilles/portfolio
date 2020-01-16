package imageprocessing.model;

import java.awt.image.BufferedImage;

import imageprocessing.model.image.Country;
import imageprocessing.model.image.Orientation;

/**
 * This class is only for testing the Model and Controller relationship,
 * to make sure that the controller is sending the correct methods to the model.
 */
public class MockModel implements IModel {
  public String code;

  public MockModel() {
    this.code = "";

  }

  public String getCode() {
    return this.code.trim();
  }

  /**
   /**
   * Gets a BufferedImage from the controller and creates an Image object from it.
   * @param input BufferedImage to create an image out of.
   * @param name Name of the internal representation of the image.
   */
  @Override
  public void passImage(BufferedImage input, String name) {
    this.code += " load";
  }

  /**
   * Creates a filtered version of the current image with a blur using a built-in kernel. Doesn't
   * mutate the image.
   *
   * @param title the name of the image the user wants blurred
   */
  @Override
  public void applyBlur(String title) {
    this.code += " blur";
  }

  /**
   * Creates a filtered version of the current image -- sharpened -- using a built-in kernel.
   * Doesn't mutate the image.
   *
   * @param title the name of the image the user wants sharpened
   */
  @Override
  public void applySharpen(String title) {
    this.code += " sharpen";
  }

  /**
   * Creates a filtered version of the current image -- in sepia. Doesn't mutate the image.
   *
   * @param title the name of the image the user wants in sepia
   */
  @Override
  public void applySepia(String title) {
    this.code += " sepia";
  }

  /**
   * Creates a filtered version of the current image -- in greyscale. Doesn't mutate the image.
   *
   * @param title the name of the image the user wants in greyscale
   */
  @Override
  public void applyGreyscale(String title) {
    this.code += " greyscale";
  }

  /**
   * Creates a filtered version of the current image -- in dithered format, appropriate for
   * dot-matrix printers. Doesn't mutate the image.
   *
   * @param title the name of the image the user wants dithered
   */
  @Override
  public void applyDither(String title) {
    this.code += " dither";
  }

  /**
   * Creates a mosaic of the current image using a simplified k-means. The user specifies the number
   * of seeds. The more seeds, the more fine the dots in the mosaic. Doesn't mutate the image.
   *
   * @param title the name of the image the user wants mosaic'ed
   * @param seed  the number of seeds (chunks) of the desired mosaic
   */
  @Override
  public void applyMosaic(String title, int seed) {
    this.code += " mosaic " + seed;
  }

  /**
   * Initializes a rainbow image -- 7 stripes of color, with user specified orientation and size.
   *
   * @param height The height, in pixels, of the desired rainbow image
   * @param width  The width, in pixels, of the desired rainbow image
   * @param o      The orientation of the desired rainbow image (vertical or horizontal)
   */
  @Override
  public void drawRainbow(int height, int width, Orientation o) {
    this.code += " rainbow h:" + height + " w:" + width + " " + o;
  }

  /**
   * Initializes a checkerboard image (alternating equally-sized black and white squares), where the
   * square size is user-specified. A checkerboard is 8 squares x 8 squares.
   *
   * @param squareSize the length/width of a square, in pixels
   */
  @Override
  public void drawCheckerBoard(int squareSize) {
    this.code += " checkerboard " + squareSize;
  }

  /**
   * Initializes a flag image of a user-specified country and size.
   *
   * @param width the width of the desired flag, in pixels.
   * @param c     the country of the desired flag. Currently supports France, Switzerland and
   *              Greece.
   */
  @Override
  public void drawFlag(int width, Country c) {
    this.code += " flag " + width + " " + c;
  }

  /** Converts an image an Image in our model architecture into a BufferedImage.
   * @param title The name of the image to be converted
   * @return The BufferedImage version of that image
   */
  @Override
  public BufferedImage getImage(String title) {
    return null;
  }
}
