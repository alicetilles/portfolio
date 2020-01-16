package imageprocessing.model.adjustment;

import imageprocessing.model.image.IImage;
import imageprocessing.model.image.Image;
import imageprocessing.model.image.Pixel;

/**
 * This class is a type of adjustment that can be applied to an image. It uses the Floyd-Steinberg
 * algorithm to make an image suitable for dot-matrix printers.
 *
 */
public class Dither implements Adjustment {


  /**
   * Takes as input an integer color between 0 and 255. Converts it to
   * white or black (0 or 255), whichever it is closest to.
   * @param oldColor the value of the old color
   * @return the new value, 0 or 255
   */
  private int convertToBW(int oldColor) {
    if (oldColor <= 127) {
      return 0;
    }
    return 255;
  }



  /**
   * Applies this object onto an image input without mutating it, and outputs a new image object
   * with the adjustment.
   *
   * @param input Image to change
   * @return Altered cloned image
   */
  @Override
  public IImage apply(IImage input) {

    // Apply greyscale
    Transformation grey = new Transformation(Transformations.GREYSCALE);
    IImage newInput = grey.apply(input);

    // Get the data of input image
    Pixel[][] originalData = newInput.getData().clone();
    Pixel[][] newData = new Pixel[originalData.length][originalData[0].length];

    // Initialize output image and pixels
    for (int r = 0; r < originalData.length; r++) {
      for (int c = 0; c < originalData[0].length; c++) {
        newData[r][c] = new Pixel(0,0,0);
      }
    }


    // for each position r,c in image,
    for (int r = 0; r < originalData.length; r++) {
      for (int c = 0; c < originalData[0].length; c++) {

        // old_color = color-component of pixel (r,c)
        int old_color_red = originalData[r][c].getRed();

        // Convert each channel to white or black, whichever it is closest to
        int new_color_red = convertToBW(old_color_red);

        // Calculate the error for each channel
        int error_red = old_color_red - new_color_red;

        // Set color of new pixel at current position
        newData[r][c].add(new_color_red);
        newData[r][c] = new Pixel(new_color_red, new_color_red, new_color_red);

        // ADJUST SURROUNDING PIXELS
        // add (7/16 * error) to pixel on the right (r,c+1)
        if (c != originalData[0].length - 1 ) {
          originalData[r][c + 1].add( (7.0 / 16.0) * error_red);
        }

        // add (3/16 * error) to pixel on the next-row-left (r+1,c-1)
        if (c != 0 && r != originalData.length - 1 ) {
          originalData[r + 1][c - 1].add(3.0 / 16.0 * error_red);
        }

        // add (5/16 * error) to pixel below in next row (r+1,c)
        if (r != originalData.length - 1 ) {
          originalData[r + 1][c].add(5.0 / 16.0 * error_red);
        }

        // add (1/16 * error) to pixel on the next-row-right (r+1,c+1)
        if (r != originalData.length - 1 && c != originalData[0].length - 1 ) {
          originalData[r + 1][c + 1].add(1.0 / 16.0 * error_red);
        }

      }
    }

    Image ditheredImage = new Image(newData);

    return ditheredImage;
  }

}
