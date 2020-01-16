package imageprocessing.model.image;

/**
 * A flag is a type of image object. Flags have a specific pattern which can be scaled with size.
 * Currently supports France, Switzerland and Greece.
 */
public class Flag extends AbstractImage implements IImage {

  /** Creates a Flag object by initializing pixels and then filling them in with color.
   *
   * @param width The specified user-inputted width of the flag image
   * @param country The user-chosen country
   */

  public Flag(int width, Country country) {
    super.width = width;

    // The Swiss flag is one of only two square sovereign-state flags, the other being
    // the flag of Vatican City. - Wikipedia.
    if (country.equals(Country.FRANCE) || country.equals(Country.GREECE)) {
      super.height = (int) (width * 0.6);
    } else if (country.equals(Country.SWITZERLAND)) {
      super.height = width;
    }

    // Initialize array
    super.data = new Pixel[super.height][super.width];

    // Fill array with blank pixels
    for (int i = 0; i < super.width; i++) {
      for (int j = 0; j < super.height; j++) {
        Pixel uninitPixel = new Pixel(255, 255, 255);
        super.data[j][i] = uninitPixel;
      }
    }

    if (country.equals(Country.FRANCE)) {
      this.drawFrench();
    } else if (country.equals(Country.SWITZERLAND)) {
      this.drawSwiss();
    } else if (country.equals(Country.GREECE)) {
      this.drawGreek();
    }
  }


  /**
   * Creates a French flag as this flag.
   */
  private void drawFrench() {
    double width = (double)super.width;
    int stripeWidth = (int)Math.ceil(width / 3);

    //draw blue stripe
    for (int x = 0; x < super.height; x++) {
      for (int y = 0; y < stripeWidth; y++) {
        Pixel newPixel = new Pixel(0, 35, 149);
        super.data[x][y] = newPixel;
      }
    }

    //draw white stripe
    for (int x = 0; x < super.height; x++) {
      for (int y = stripeWidth; y < stripeWidth * 2; y++) {
        Pixel newPixel = new Pixel(255, 255, 255);
        super.data[x][y] = newPixel;
      }
    }

    //draw red stripe
    for (int x = 0; x < super.height; x++) {
      for (int y = stripeWidth * 2; y < super.width; y++) {
        Pixel newPixel = new Pixel(237, 41, 57);
        super.data[x][y] = newPixel;
      }
    }

  }

  /**
   * Creates a Greek flag as this flag.
   */
  private void drawGreek() {
    double height = (double)super.height;
    int stripeWidth = (int)Math.ceil(height / 9.0);

    // Paint the background blue
    for (int x = 0; x < super.height; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(13, 94, 175);
        super.data[x][y] = newPixel;
      }
    }

    //Draw white stripes
    for (int x = stripeWidth; x < stripeWidth * 2; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(255, 255, 255);
        super.data[x][y] = newPixel;
      }
    }

    for (int x = stripeWidth * 3; x < stripeWidth * 4; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(255, 255, 255);
        super.data[x][y] = newPixel;
      }
    }

    for (int x = stripeWidth * 5; x < stripeWidth * 6; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(255, 255, 255);
        super.data[x][y] = newPixel;
      }
    }

    for (int x = stripeWidth * 7; x < stripeWidth * 8; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(255, 255, 255);
        super.data[x][y] = newPixel;
      }
    }

    // draw blue corner square
    for (int x = 0; x < stripeWidth * 5; x++) {
      for (int y = 0; y < stripeWidth * 5; y++) {
        Pixel newPixel = new Pixel(13, 94, 175);
        super.data[x][y] = newPixel;
      }
    }
    // draw horizontal white cross
    for (int x = stripeWidth * 2; x < stripeWidth * 3; x++) {
      for (int y = 0; y < stripeWidth * 5; y++) {
        Pixel newPixel = new Pixel(255, 255, 255);
        super.data[x][y] = newPixel;
      }
    }
    //draw vertical white cross
    for (int x = 0; x < stripeWidth * 5; x++) {
      for (int y = stripeWidth * 2; y < stripeWidth * 3; y++) {
        Pixel newPixel = new Pixel(255, 255, 255);
        super.data[x][y] = newPixel;
      }
    }
  }

  /**
   * Draws the Swiss flag as this flag.
   */
  private void drawSwiss() {
    int square = (int) (super.height * 0.2);

    // Draw red background.
    for (int x = 0; x < super.height; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(255, 0, 0);
        super.data[x][y] = newPixel;
      }
    }
    //Draw white cross horizontal
    for (int x = square * 2; x < square * 3; x++) {
      for (int y = square; y < square * 4; y++) {
        Pixel newPixel = new Pixel(255, 255, 255);
        super.data[x][y] = newPixel;
      }
    }

    //Draw white cross Vertical
    for (int x = square; x < square * 4; x++) {
      for (int y = square * 2; y < square * 3; y++) {
        Pixel newPixel = new Pixel(255, 255, 255);
        super.data[x][y] = newPixel;
      }
    }
  }


}
