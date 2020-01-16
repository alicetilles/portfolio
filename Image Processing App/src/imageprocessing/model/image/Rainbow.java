package imageprocessing.model.image;

/**
 * This class creates and represents a Rainbow image object, with 7 colored stripes.
 */
public class Rainbow extends AbstractImage implements IImage {

  /** Creates an image of the rainbow with user-specified dimension and orientation. Does not
   * write to a file, but can.
   *
   * @param height The height, in px, of the desired rainbow image
   * @param width The width, in px, of the desired rainbow image
   * @param orientation The desired direction (vertical or horizontal) of the rainbow's stripes
   */
  public Rainbow(int height, int width, Orientation orientation) {
    super.height = height;
    super.width = width;
    super.data = new Pixel[super.height][super.width];

    //Initialize canvas.
    for (int i = 0; i < super.width; i++) {
      for (int j = 0; j < super.height; j++) {
        Pixel uninitPixel = new Pixel(0, 0, 255);
        super.data[j][i] = uninitPixel;
      }
    }

    if (orientation.equals(Orientation.HORIZONTAL)) {
      this.drawHorizontal();
    }
    else if (orientation.equals(Orientation.VERTICAL)) {
      this.drawVertical();
    }

  }

  /**
   * Creates a horizontal rainbow object.
   */
  private void drawHorizontal() {
    double height = (double)super.height;
    int stripeWidth = (int)Math.ceil(height / 7.0);
    int lastStripe = super.height - (stripeWidth * 6);

    //Red Stripe
    for (int x = 0; x < stripeWidth; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(255, 0, 0);
        super.data[x][y] = newPixel;
      }
    }
    //Orange Stripe
    for (int x = stripeWidth; x < stripeWidth * 2; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(255, 165, 0);
        super.data[x][y] = newPixel;
      }
    }

    //Yellow Stripe
    for (int x = stripeWidth * 2; x < stripeWidth * 3; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(255, 255, 0);
        super.data[x][y] = newPixel;
      }
    }

    //Green Stripe
    for (int x = stripeWidth * 3; x < stripeWidth * 4; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(0, 128, 0);
        super.data[x][y] = newPixel;
      }
    }

    //Blue Stripe
    for (int x = stripeWidth * 4; x < stripeWidth * 5; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(0, 0, 255);
        super.data[x][y] = newPixel;
      }
    }

    //Indigo Stripe
    for (int x = stripeWidth * 5; x < stripeWidth * 6; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(75, 0, 130);
        super.data[x][y] = newPixel;
      }
    }
    //Violet Stripe
    for (int x = stripeWidth * 6; x < stripeWidth * 6 + lastStripe; x++) {
      for (int y = 0; y < super.width; y++) {
        Pixel newPixel = new Pixel(238, 130, 238);
        super.data[x][y] = newPixel;
      }
    }
  }

  /**
   * Creates a vertically-striped rainbow image object.
   */
  private void drawVertical() {
    double width = (double)super.width;
    int stripeWidth = (int)Math.ceil(width / 7.0);
    int lastStripe = super.width - (stripeWidth * 6);

    //Red Stripe
    for (int x = 0; x < super.height; x++) {
      for (int y = 0; y < stripeWidth; y++) {
        Pixel newPixel = new Pixel(255, 0, 0);
        super.data[x][y] = newPixel;
      }
    }
    //Orange Stripe
    for (int x = 0; x < super.height; x++) {
      for (int y = stripeWidth; y < stripeWidth * 2; y++) {
        Pixel newPixel = new Pixel(255, 165, 0);
        super.data[x][y] = newPixel;
      }
    }

    //Yellow Stripe
    for (int x = 0; x < super.height; x++) {
      for (int y = stripeWidth * 2; y < stripeWidth * 3; y++) {
        Pixel newPixel = new Pixel(255, 255, 0);
        super.data[x][y] = newPixel;
      }
    }

    //Green Stripe
    for (int x = 0; x < super.height; x++) {
      for (int y = stripeWidth * 3; y < stripeWidth * 4; y++) {
        Pixel newPixel = new Pixel(0, 128, 0);
        super.data[x][y] = newPixel;
      }
    }

    //Blue Stripe
    for (int x = 0; x < super.height; x++) {
      for (int y = stripeWidth * 4; y < stripeWidth * 5; y++) {
        Pixel newPixel = new Pixel(0, 0, 255);
        super.data[x][y] = newPixel;
      }
    }

    //Indigo Stripe
    for (int x = 0; x < super.height; x++) {
      for (int y = stripeWidth * 5; y < stripeWidth * 6; y++) {
        Pixel newPixel = new Pixel(75, 0, 130);
        super.data[x][y] = newPixel;
      }
    }

    //Violet Stripe
    for (int x = 0; x < super.height; x++) {
      for (int y = stripeWidth * 6; y < stripeWidth * 6 + lastStripe; y++) {
        Pixel newPixel = new Pixel(238, 130, 238);
        super.data[x][y] = newPixel;
      }
    }
  }
}

