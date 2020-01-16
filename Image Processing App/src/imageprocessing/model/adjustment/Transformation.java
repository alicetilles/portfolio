package imageprocessing.model.adjustment;

import imageprocessing.model.image.IImage;

/**
 * This class represents a transformation to apply to an image. A transformation has a 2D 3x3
 * matrix.
 */
public class Transformation implements Adjustment {

  private double[][] matrix;

  /**
   * Constructs a transformation object. A transformation has a 2D 3x3 matrix.
   *
   * @param matrix A 2D 3x3 array of doubles.
   */
  public Transformation(double[][] matrix) {
    this.matrix = matrix;
  }


  /** This constructor makes the object by identifying the input string as a built-in
   * transformation, such as greyscale. If it finds it, then it creates it. If it does not
   * find the specified transformation, it throws an error.
   * It is a helper method used by the constructor.
   *
   * @param transformationName The name of the transformation to create.
   * @return The matrix of the filter specified
   */
  private double[][] getFilterByName(Transformations transformationName) {
    if (transformationName.equals(Transformations.GREYSCALE)) {
      double[][] greyScale =  { {0.2126, 0.7152, 0.0722},
                                {0.2126, 0.7152, 0.0722},
                                {0.2126, 0.7152, 0.0722} };
      return greyScale;
    }

    else if (transformationName.equals(Transformations.SEPIA)) {
      double[][] sepia =  { {0.393, 0.769, 0.189},
                            {0.349, 0.686, 0.168},
                            {0.272, 0.534, 0.131} };
      return sepia;
    }

    // This else statement is reached if a valid enum is inputted, but hasn't been implemented yet.
    // It preserves the image.
    else {
      double[][] identity =  { {1, 0, 0},
                               {0, 1, 0},
                               {0, 0, 1} };
      return identity;
    }
  }


  /**
   * A transformation has a "matrix" which is a 2D array of doubles.
   * This constructor is for a filter object for common names (e.g., greyscale, sepia).
   * @param transformationName The transformation type
   */
  public Transformation(Transformations transformationName) {
    this.matrix = getFilterByName(transformationName);
  }

  /**
   * Returns a copy of the matrix of this Transformation.
   *
   * @return A 2D array of doubles.
   */
  public double[][] getData() {
    return this.matrix.clone();
  }

  /** Applies this object onto an image input without mutating it, and outputs a new
   * image object with the adjustment.
   * @param inputImage Image to change
   * @return Altered cloned image
   */
  public IImage apply(IImage inputImage) {
    return inputImage.transform(this);
  }


}
