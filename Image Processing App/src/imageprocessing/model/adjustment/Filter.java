package imageprocessing.model.adjustment;

import imageprocessing.model.image.IImage;
import imageprocessing.model.image.Pixel;

/**
 * This class represents a filter to apply to an image. A filter has a "Kernel" which is a 2D array
 * of numbers with odd dimensions (3x3, 5x5, etc).
 */
public class Filter implements Adjustment {

  private double[][] kernel;

  /**
   * Constructs a filter object. A filter has a "Kernel" which is a 2D array of doubles with odd
   * dimensions (3x3, 5x5, etc).
   *
   * @param kernel A 2D array of doubles with odd dimensions (3x3, 5x5, etc).
   */
  public Filter(double[][] kernel) throws IllegalArgumentException {

    if ( (kernel.length % 2) == 0 || kernel.length != kernel[0].length ) {
      throw new IllegalArgumentException("Kernel must be nxn matrix where n is odd.");
    }

    this.kernel = kernel;
  }

  /**
   * A filter has a "Kernel" which is a 2D array of doubles with odd dimensions (3x3, 5x5, etc).
   * This constructor is for a filter object for common names (e.g., blur, sharpen).
   * @param filterType The filter type
   */
  public Filter(Filters filterType) {
    this.kernel = getFilterByName(filterType);
  }


  /** This constructor makes the Filter object by identifying the input string as a built-in
   * filter, such as sharpen or blur. If it finds the filter, then it creates it. If it does not
   * find the specified filter, it throws an error. It is a helper method used by the constructor.
   *
   * @param filterName The name of the filter to create.
   * @return The kernel of the filter specified
   */
  private double[][] getFilterByName(Filters filterName) {
    if (filterName.equals(Filters.BLUR)) {
      double[][] blurKernel =  { {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0},
                                 {1.0 / 8.0,  1.0 / 4.0, 1.0 / 18.0},
                                 {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0} };
      return blurKernel;
    }
    else {
      double[][] sharpenKernel =  { {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0},
                                    {-1.0 / 8.0,  1.0 / 4.0,  1.0 / 4.0,  1.0 / 4.0, -1.0 / 8.0},
                                    {-1.0 / 8.0,  1.0 / 4.0,  1.0,      1.0 / 4.0, -1.0 / 8.0},
                                    {-1.0 / 8.0,  1.0 / 4.0,  1.0 / 4.0,  1.0 / 4.0, -1.0 / 8.0},
                                    {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0}  };
      return sharpenKernel;
    }

  }


  /**
   * Returns a copy of the Kernel of this Filter.
   *
   * @return A 2D array of doubles.
   */
  public double[][] getData() {
    return this.kernel.clone();
  }

  /**
   * Checks if the image contains the given pixel position.
   *
   * @param x Position of the pixel to check.
   * @param y Position of the pixel to check.
   * @return True if x, and y values exist in this image. False otherwise.
   */
  private boolean isValidPixelPosition(IImage image, int x, int y) {

    return (x < image.get3Ddata().length && y < image.get3Ddata()[0].length && x >= 0 && y >= 0);
  }

  /** Applies this filter object to a specified pixel in a specified image. Helper method used by
   * apply.
   *
   * @param inputImage The image with a pixel to change.
   * @param x The x-coordinate of this pixel.
   * @param y The y-coordinate of this pixel.
   * @return The filtered pixel.
   */
  private Pixel applyToPixel(IImage inputImage, int x, int y) {

    // Get the kernel of the filter
    double[][] filterKernel = this.getData();
    int filterLength = (this.kernel.length - 1) / 2;
    int xStart = x - filterLength;
    int yStart = y - filterLength;

    double redSum = 0;
    double greenSum = 0;
    double blueSum = 0;


    // For each entry in the filter kernel
    for (int b = 0; b < filterKernel.length; b++) {
      xStart = x - filterLength;

      for (int a = 0; a < filterKernel.length; a++) {
        //System.out.println("a = " + a + ". b=" + b + ". kern size:" + filterKernel.length);

        // Gets the current filter
        double currentFilter = this.kernel[a][b];

        // Get the pixel to be altered
        if (this.isValidPixelPosition(inputImage, xStart, yStart)) {

          Pixel currentPixel = inputImage.getData()[xStart][yStart];
          redSum = redSum + (currentPixel.vectorRed(currentFilter));
          greenSum = greenSum + (currentPixel.vectorGreen(currentFilter));
          blueSum = blueSum + (currentPixel.vectorBlue(currentFilter));
          //System.out.println("Red = " + redSum + " green, blue = " + greenSum + blueSum);
        }
        xStart++;

      }
      yStart++;

    }

    // Round double values and cast to ints.
    Pixel newPixel = new Pixel((int) Math.round(redSum), (int) Math.round(greenSum),
            (int) Math.round(blueSum));
    //System.out.println(newPixel.toString());
    return newPixel;

  }


  /** This applies the filter to an image without modifying the image. We could not fully
   * implement this (it was taking much longer than calling the method in the Image object) but
   * given enough time, this method would be doing all the "work". This is what is commented out.
   * @param input The image to be filtered.
   * @return The Image after the filter has been applied.
   */
  public IImage apply(IImage input) {

    return input.applyFilter(this);

  }


}
