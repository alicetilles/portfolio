package imageprocessing.model.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

import imageprocessing.model.adjustment.Filter;
import imageprocessing.model.adjustment.Transformation;

/**
 * An image has a height, width, and RGB values.
 * Besides retrieving those, the methods it can do depends on the type of image.
 */
public interface IImage {

  /** Returns the height, in pixels, of the flag.
   * @return the height, in pixels, of the flag
   */
  int getHeight();


  /** Returns the width, in pixels, of the flag.
   * @return the width, in pixels, of the flag
   */
  int getWidth();

  /** Returns the data of this image, by converting a 2D array of Pixel objects
   * into a 3D array of int objects. The purpose of this method is to make the data readable
   * by the ImageUtil class, or any 'outsiders' who do not have Pixel objects.
   * @return A 3D array of values representing RGB values of this image
   */
  int[][][] get3Ddata();

  Pixel[][] getData();

  void writeImageToFile(String s) throws IOException;

  BufferedImage convertToBufferedImage(String filename) throws IOException;

  /** This applies a transformation to the image without modifying the image.
   *
   * @param inputTransformation The transformation to be applied
   * @return The Image after the filter has been applied.
   */
  IImage transform(Transformation inputTransformation);

  IImage applyFilter(Filter inputFilter);
}
