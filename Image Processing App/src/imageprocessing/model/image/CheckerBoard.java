package imageprocessing.model.image;

/**
 * This class represents a checkerboard image. It can create an 8 square x 8 square checkerboard,
 * and return information about the object. It is a type of Image, which means it can have
 * adjustments applied to it.
 */
public class CheckerBoard extends AbstractImage implements IImage {
  private int squareSize;

  /**
   * Creates a Checkerboard based on a user-specified square size.
   * @param squareSize The width/height dimension of the square, in pixels
   */
  public CheckerBoard(int squareSize) {
    super.height = (squareSize * 8);
    super.width = (squareSize * 8);
    this.squareSize = squareSize;
    super.data = new Pixel[super.width][super.height];
    this.draw();
  }

  /**
   * Draws the checkerboard object - a helper method used by the constructor.
   */
  private void draw() {
    int left = 0;

    // Initializes this.data by creating a pixel in each spot.
    for (int i = 0; i < super.width; i++) {
      for (int j = 0; j < super.height; j++) {
        Pixel uninitPixel = new Pixel(0, 0, 255);
        super.data[j][i] = uninitPixel;
      }
    }

    // For each row,
    for (int horizontal = 0; horizontal < 8; horizontal++) {
      int top = 0;

      // For each column,
      for (int vertical = 0; vertical < 4; vertical++) {
        if (horizontal % 2 == 0) {
          drawWhiteSquare(left, top);
        } else {
          drawBlackSquare(left, top);
        }
        top += this.squareSize;
        if ((horizontal + 1) % 2 == 0) {
          drawWhiteSquare(left, top);
        } else {
          drawBlackSquare(left, top);
        }
        top += this.squareSize;
      }
      left += this.squareSize;
    }
  }


  /** Creates a white square object in this.data, - a helper method used by draw.
   * @param left The x-coordinate of the top left corner of the new square.
   * @param top The y-coordinate of the top left corner of the new square.
   */
  private void drawWhiteSquare(int left, int top) {
    for (int x = left; x < left + this.squareSize; x++) {
      for (int y = top; y < top + this.squareSize; y++) {
        Pixel newPixel = new Pixel(255, 255, 255);
        super.data[x][y] = newPixel;
      }
    }
  }

  /** Creates a black square object in this.data, - a helper method used by draw.
   * @param left The x-coordinate of the top left corner of the new square.
   * @param top The y-coordinate of the top left corner of the new square.
   */
  private void drawBlackSquare(int left, int top) {
    for (int x = left; x < left + this.squareSize; x++) {
      for (int y = top; y < top + this.squareSize; y++) {
        Pixel newPixel = new Pixel(0, 0, 0);
        super.data[x][y] = newPixel;
      }
    }
  }

}
