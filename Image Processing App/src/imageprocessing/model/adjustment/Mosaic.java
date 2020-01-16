package imageprocessing.model.adjustment;

import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

import imageprocessing.model.image.IImage;
import imageprocessing.model.image.Image;
import imageprocessing.model.image.Pixel;


/**
 * A mosaic is a type of adjustment on any type of Image. It requires a certain number of seeds
 * (defaults to 1000), and creates a mosaic pattern based on that number of seeds. The more seeds,
 * the finer the mosaic and the more similar to the original it will look. A single seed will be
 * the average color of the entire image.
 */
public class Mosaic implements Adjustment {


  /** Calculates the Euclidian distance between two points.
   *
   * @param pointA Point a
   * @param pointB Point b
   * @return The distance between the inputs
   */
  private static double getDistance(Integer[] pointA, Integer[] pointB ) {
    return Math.sqrt(Math.pow((pointA[0] - pointB[0]), 2)
            + Math.pow((pointA[1] - pointB[1]), 2));
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

    // If no seed number set, then do 1000 seeds
    return this.apply(input, 1000);

  }

  /**
   * Applies this object onto an image input without mutating it, and outputs a new image object
   * with the adjustment. Uses a specified number of seeds.
   *
   * @param input Image to change
   * @return Altered cloned image
   */
  public IImage apply(IImage input, int numSeeds) {

    // Initialize randomizer
    Random rand = new Random();

    // Initialize num pixels assigned to each cluster relationship
    HashMap<Integer, Integer> clusterToNumMembers = new HashMap<>();
    for (int i = 0; i < numSeeds; i++) {
      clusterToNumMembers.put(i, 0);
    }

    // Initialize cluster number with coordinates
    HashMap<Integer, Integer[]> clustersToCoordinates = new HashMap<Integer, Integer[]>();

    // Choose a random set of points on the image, create a cluster for it
    for (int i = 0; i < numSeeds; i++) {
      int randX = rand.nextInt(input.getHeight());
      int randY = rand.nextInt(input.getWidth());
      Integer[] coordinates = new Integer[2];
      coordinates[0] = randX;
      coordinates[1] = randY;
      clustersToCoordinates.put(i, coordinates);
    }

    // Initialize pixel -> cluster relationship / assignments
    HashMap<Integer[], Integer> pixelsToClusters = new HashMap<>();

    // Initialize list of all coordinates in Integer[] format
    ArrayList<Integer[]> allPixels = new ArrayList<>();

    // Each pixel should be paired to the seed closest to it
    for (int x = 0; x < input.getHeight(); x++) {
      for (int y = 0; y < input.getWidth(); y++) {


        // Initialize data points
        Integer[] currentCoordinates = new Integer[2];
        currentCoordinates[0] = x;
        currentCoordinates[1] = y;
        allPixels.add(currentCoordinates);

        // Initialize variable that contains the smallest distance
        double smallestDistance = getDistance(currentCoordinates, clustersToCoordinates.get(0));
        Integer closestPoint = 0;

        // For each cluster, calculate the distance between current point and that one
        for (int j = 0; j < numSeeds; j++) {

          Integer[] currentClusterPoint = clustersToCoordinates.get(j);
          double currentDistance = getDistance(currentCoordinates, currentClusterPoint);

          // If this distance is smaller than the saved smallest distance, then save new one
          if (currentDistance < smallestDistance ) {
            smallestDistance = currentDistance;
            closestPoint = j;
          }

        }


        // After finding the smallest distance to the current point, assign it to a cluster
        pixelsToClusters.put(currentCoordinates, closestPoint);
        Integer clusterAssigned = pixelsToClusters.get(currentCoordinates);

        // Increment number of members assigned to that cluster
        int currentNumMembers = clusterToNumMembers.get(closestPoint);
        clusterToNumMembers.put(closestPoint, currentNumMembers + 1);

      }
    }

    // Initialize cluster -> color relationship. Initialize each to 0.
    HashMap<Integer, Integer> clustersToRedColor = new HashMap<>();
    HashMap<Integer, Integer> clustersToGreenColor = new HashMap<>();
    HashMap<Integer, Integer> clustersToBlueColor = new HashMap<>();
    for (int i = 0; i < numSeeds; i++) {
      clustersToRedColor.put(i, 0);
      clustersToBlueColor.put(i, 0);
      clustersToGreenColor.put(i, 0);
    }




    // For each pixel, add the values to its cluster value
    for (int i = 0; i < allPixels.size(); i++) {
      Integer[] currentCoordinates = allPixels.get(i);
      int x = currentCoordinates[0];
      int y = currentCoordinates[1];

      Integer clusterAssigned = pixelsToClusters.get(currentCoordinates);

      // Get colors of this pixel
      int redValue = input.getData()[x][y].getRed();
      int greenValue = input.getData()[x][y].getGreen();
      int blueValue = input.getData()[x][y].getBlue();

      // Get so-far sums of this cluster
      int clusterRedValue = clustersToRedColor.get(clusterAssigned);
      int clusterGreenValue = clustersToGreenColor.get(clusterAssigned);
      int clusterBlueValue = clustersToBlueColor.get(clusterAssigned);

      // Add the current pixel values to this cluster
      Integer newRed = redValue + clusterRedValue;
      Integer newGreen = greenValue + clusterGreenValue;
      Integer newBlue = blueValue + clusterBlueValue;

      // Reset cluster -> color mapping
      clustersToRedColor.put(clusterAssigned, newRed);
      clustersToGreenColor.put(clusterAssigned, newGreen);
      clustersToBlueColor.put(clusterAssigned, newBlue);

    }

    // After getting the sum of each color, get the average
    for (int i = 0; i < numSeeds; i++) {

      Integer redValue = clustersToRedColor.get(i);
      Integer greenValue = clustersToGreenColor.get(i);
      Integer blueValue = clustersToBlueColor.get(i);

      // Get number of members for this cluster
      Integer numMembers = clusterToNumMembers.get(i);

      // Calculate and assign averages
      if (numMembers != 0 ) {
        clustersToRedColor.put(i, redValue / numMembers);
        clustersToGreenColor.put(i, greenValue / numMembers);
        clustersToBlueColor.put(i, blueValue / numMembers);
      }
      else {
        clustersToRedColor.put(i, redValue);
        clustersToGreenColor.put(i, greenValue);
        clustersToBlueColor.put(i, blueValue);
      }

    }

    // Finally, initialize output image and put the color as dictated by closest seed
    Pixel[][] newData = new Pixel[input.getHeight()][input.getWidth()];

    for (int i = 0; i < allPixels.size(); i++) {
      Integer[] currentCoordinates = allPixels.get(i);
      int x = currentCoordinates[0];
      int y = currentCoordinates[1];
      x = currentCoordinates[0];
      y = currentCoordinates[1];

      // Get the cluster assigned to this pixel
      Integer assignedCluster = pixelsToClusters.get(currentCoordinates);

      // Get the colors assigned to that cluster
      Integer redValue = clustersToRedColor.get(assignedCluster);
      Integer greenValue = clustersToGreenColor.get(assignedCluster);
      Integer blueValue = clustersToBlueColor.get(assignedCluster);

      // Assign the current pixel in output data to these colors
      Pixel newPixel = new Pixel(redValue, greenValue, blueValue);
      newData[x][y] = newPixel;

    }

    Image mosaic = new Image(newData);
    return mosaic;

  }


}
