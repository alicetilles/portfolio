package imageprocessing.view;

// Import packages needed for Swing.

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import imageprocessing.controller.Features;

/**
 * The user interface of the image processing package, a simple GUI with a menu bar on the top and a
 * main image displayed in the background. The menu bar has these items: File, Edit, Adjustments,
 * and Draw.
 */
public class ViewImpl extends JFrame implements IView {

  // Global fields required for setup.
  private JFrame mainFrame;

  // Fields required for menu:
  private JMenuBar menuBar;
  private JMenuItem blurMenuItem;
  private JMenuItem sharpenMenuItem;
  private JMenuItem ditherMenuItem;
  private JMenuItem mosaicMenuItem;
  private JMenuItem sepiaMenuItem;
  private JMenuItem greyscaleMenuItem;
  private JMenuItem flagMenuItem;
  private JMenuItem checkerBoardMenuItem;
  private JMenuItem rainbowMenuItem;
  private JMenuItem loadMenuItem;
  private JMenuItem saveMenuItem;
  private JMenuItem undoMenuItem;
  private JMenuItem redoMenuItem;
  private JMenuItem quitMenuItem;
  private JMenuItem batchLoadMenuItem;
  private JMenuItem batchWriteMenuItem;
  private JScrollPane imageScrollPane;

  private JPanel imagePanel;
  private JLabel fileSaveDisplay;
  private String path;


  /**
   * Initializes the window and all containers of the imageprocessing GUI.
   */
  public ViewImpl() throws IOException {
    prepareGui("./res/welcome.png");
  }


  /**
   * Creates the Java Swing GUI -- a simple border layout with the image in the center of the screen
   * and a menu bar with options on top.
   *
   * @param file The file to initialize the background image of the window to.
   * @throws IOException If that file can not be found
   */
  private void prepareGui(String file) throws IOException {
    /// always call the constructor of JFrame: it contains important initialization.
    mainFrame = new JFrame("Image Processing Software");
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setLayout(new BorderLayout());

    // setDefaultCloseOperation determines the behavior when the “close-window” button is clicked.
    // Options are to close the application, close the window but not the application, or do
    // nothing.
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Prepare the menu
    prepareMenuBar();

    // Prepare scrolling area
    prepareScrollPane(file);

    // Adjustments to the design of the GUI
    mainFrame.pack();
    mainFrame.setVisible(true);

  }


  /**
   * Sets the size of the image so that the image panel is 100 pixels wider and taller than the
   * input width and height specifications.
   *
   * @param width  The desired width
   * @param height The desired height
   */
  public void setSize(int width, int height) {
    imagePanel.setSize(width + 100, height + 100);
  }

  /**
   * Creates the adjustment menu, which has each type of adjustment of the image that the user can
   * click on to change the current image, and the File menu, which has 'administrative' type
   * capabilities.
   */
  private void prepareMenuBar() {

    // Create the menu bar:
    menuBar = new JMenuBar();

    // Add each category
    prepareFileMenuItems();
    prepareEditMenuItems();
    prepareAdjustmentMenuItems();
    prepareDrawMenuItems();

    // Add the menu bar to the frame at the top.
    mainFrame.setJMenuBar(menuBar);
    mainFrame.add(menuBar, BorderLayout.NORTH);
    menuBar.setVisible(true);

  }

  /**
   * Creates the File menu, which contains Load, Save, Batch Load, and Quit.
   */
  private void prepareFileMenuItems() {

    // If the user types "F" for "F"ile (VK_F), this menu opens up
    JMenu menuFile = new JMenu("File");
    menuFile.setMnemonic(KeyEvent.VK_F);

    // Sets the AccessibleContext associated with this JMenuBar.
    menuFile.getAccessibleContext().setAccessibleDescription("Open or save an image");

    // Add load item to this menu:
    loadMenuItem = new JMenuItem("Load", KeyEvent.VK_L); // If the person hits "L",  goes here
    loadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.META_MASK)); //
    loadMenuItem.getAccessibleContext().setAccessibleDescription("Load an image");
    menuFile.add(loadMenuItem);

    // Add save item to this menu:
    saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
    saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.META_MASK));
    saveMenuItem.getAccessibleContext().setAccessibleDescription("Save image");
    menuFile.add(saveMenuItem);

    // Separate batch load, and add Load batch script option
    menuFile.addSeparator();
    batchLoadMenuItem = new JMenuItem("Load Batch Script", KeyEvent.VK_B);
    batchLoadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.META_MASK));
    batchLoadMenuItem.getAccessibleContext().setAccessibleDescription("Load batch script");
    menuFile.add(batchLoadMenuItem);

    // Separate quit, and add Quit as a menu item
    // Write a batch script
    batchWriteMenuItem = new JMenuItem("Write Batch Script", KeyEvent.VK_W);
    batchWriteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
            ActionEvent.META_MASK));
    batchWriteMenuItem.getAccessibleContext().setAccessibleDescription("Write batch script");
    menuFile.add(batchWriteMenuItem);


    // Separate quit
    menuFile.addSeparator();

    quitMenuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
    quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.META_MASK));
    quitMenuItem.getAccessibleContext().setAccessibleDescription("Quit");
    menuFile.add(quitMenuItem);

    // Add this new menu to the bar.
    menuBar.add(menuFile);
  }

  /**
   * Creates the Edit menu button, which includes only Undo and Redo.
   */
  private void prepareEditMenuItems() {
    // Build the first menu (File):
    JMenu menuEdit = new JMenu("Edit");

    // If the user types "E" for "E"dit (VK_F), this menu opens up:
    menuEdit.setMnemonic(KeyEvent.VK_E);

    // Gets the AccessibleContext associated with this JMenuBar.
    menuEdit.getAccessibleContext().setAccessibleDescription(
            "Edit menu");

    // Add all edit item to this menu:
    undoMenuItem = new JMenuItem("Undo", KeyEvent.VK_Z);
    undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.META_MASK));

    undoMenuItem.getAccessibleContext().setAccessibleDescription("Undo last command");
    undoMenuItem.setActionCommand("undo");

    //Undo is only be enabled if the the undo stack is not empty
    undoMenuItem.setEnabled(false);
    menuEdit.add(undoMenuItem);

    redoMenuItem = new JMenuItem("Redo", KeyEvent.VK_R);
    redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.SHIFT_MASK));

    redoMenuItem.getAccessibleContext().setAccessibleDescription("Redo last command");

    //Redo is only be enabled if the redo stack is not empty
    redoMenuItem.setEnabled(false);
    menuEdit.add(redoMenuItem);

    // Add this new menu to the menu bar.
    menuBar.add(menuEdit);
  }

  /**
   * Adds each particular item to the Adjustment Menu, such as blur and greyscale.
   */
  private void prepareAdjustmentMenuItems() {

    // Build the adjustments menu
    JMenu menuAdj = new JMenu("Adjustments");

    // If the user types "A" for "A"djustments (VK_A), this menu opens up:
    menuAdj.setMnemonic(KeyEvent.VK_A);

    // Gets the AccessibleContext associated with this JMenuBar.
    menuAdj.getAccessibleContext().setAccessibleDescription("Change the image.");

    // Add all adjustments item to this menu:
    blurMenuItem = new JMenuItem("Blur", KeyEvent.VK_B); // If the person hits "b", it goes here
    // menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK)); //
    blurMenuItem.getAccessibleContext().setAccessibleDescription("Blur your image");
    menuAdj.add(blurMenuItem);

    sharpenMenuItem = new JMenuItem("Sharpen");
    sharpenMenuItem.getAccessibleContext().setAccessibleDescription("Sharpen your image");
    menuAdj.add(sharpenMenuItem);

    // Add a separator to categorize types of adjustments
    menuAdj.addSeparator();

    ditherMenuItem = new JMenuItem("Dither", KeyEvent.VK_D);
    ditherMenuItem.getAccessibleContext().setAccessibleDescription("Make your image in dither");
    menuAdj.add(ditherMenuItem);

    mosaicMenuItem = new JMenuItem("Mosaic", KeyEvent.VK_M);
    mosaicMenuItem.getAccessibleContext().setAccessibleDescription("Make your image in dither");
    menuAdj.add(mosaicMenuItem);

    // Add a seperator to categorize types of adjustments
    menuAdj.addSeparator();


    sepiaMenuItem = new JMenuItem("Sepia");
    sepiaMenuItem.getAccessibleContext().setAccessibleDescription("Make your image in sepia");
    menuAdj.add(sepiaMenuItem);

    greyscaleMenuItem = new JMenuItem("Greyscale", KeyEvent.VK_G);
    greyscaleMenuItem.getAccessibleContext().setAccessibleDescription("Make your image in "
            + "greyscale");
    menuAdj.add(greyscaleMenuItem);

    // Add this new menu to the bar.
    menuBar.add(menuAdj);

  }


  /**
   * Creates the Draw menu section, which includes Rainbow, Checkerboard, and Flag.
   */
  private void prepareDrawMenuItems() {
    JMenu menuDraw = new JMenu("Draw");
    menuDraw.setMnemonic(KeyEvent.VK_D);

    // Gets the AccessibleContext associated with this JMenuBar.
    menuDraw.getAccessibleContext().setAccessibleDescription("Draw menu");

    // Add all adjustments item to this menu:
    flagMenuItem = new JMenuItem("Flag", KeyEvent.VK_F); // If the person hits "F"
    flagMenuItem.getAccessibleContext().setAccessibleDescription("Draws Flag");
    menuDraw.add(flagMenuItem);

    rainbowMenuItem = new JMenuItem("Rainbow", KeyEvent.VK_R);
    rainbowMenuItem.getAccessibleContext().setAccessibleDescription("Draws Rainbow");
    menuDraw.add(rainbowMenuItem);

    checkerBoardMenuItem = new JMenuItem("Checkerboard", KeyEvent.VK_C);
    checkerBoardMenuItem.getAccessibleContext().setAccessibleDescription("Draws Checkerboard");
    menuDraw.add(checkerBoardMenuItem);

    // Add this new menu to the bar.
    menuBar.add(menuDraw);
  }

  /**
   * Creates the scrollable panel to hold an image.
   */
  private void prepareScrollPane(String file) throws IOException {

    // Creates the panel, adds a title, alters the size
    imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Current image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    imagePanel.setMaximumSize(null);
    fileSaveDisplay = new JLabel("File path will appear here");
    mainFrame.add(imagePanel);


    // Create the image in the background on display
    BufferedImage buffered = ImageIO.read(new FileInputStream(file));
    JLabel imageLabel = new JLabel("");
    imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setIcon(new ImageIcon(buffered));
    imagePanel.add(imageScrollPane);
    imagePanel.setVisible(true);

  }


  /**
   * Adds an image on the display and removes the current image on display.
   *
   * @param image The image to be displayed
   */
  public void displayImage(BufferedImage image) {

    // Remove current image
    this.imagePanel.remove(this.imagePanel);
    this.imagePanel.remove(this.imageScrollPane);

    // Build a new image panel
    JLabel imageLabel = new JLabel("");
    imageLabel.setIcon(new ImageIcon(image));
    this.imageScrollPane = new JScrollPane(imageLabel);
    imagePanel.add(imageScrollPane);

    // Repaint and update the new image
    this.imagePanel.revalidate();
    imagePanel.repaint();
    this.mainFrame.add(imagePanel);
  }


  /**
   * Returns the most previously loaded filepath of this View.
   *
   * @return the filepath stored in the view
   */
  public String getFilePath() {
    return path;
  }


  /**
   * Prompts the user to choose a .txt file in memory which has a series of batch-processing
   * commands.
   */
  public void openBatchLoadDialogue() {
    // Creates a JFileChooser options, which opens a dialog box that lets the user choose a file.
    final JFileChooser fchooser = new JFileChooser(".");

    // Limits the options to just .txt files
    FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt");
    fchooser.setFileFilter(filter);

    // The return value is the path that the user chooses.
    int retValue = fchooser.showOpenDialog(ViewImpl.this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      path = f.getAbsolutePath();
    }
  }

  /**
   * Prompts the user to choose a .jpg, .gif, or .png image to load in the view. It returns the path
   * that the user has chosen for the controller to process.
   */
  public void openLoadDialogue() {

    // Creates a JFileChooser options, which opens a dialog box that lets the user choose a file.
    final JFileChooser fchooser = new JFileChooser(".");

    // Limits the options to just .jpg, .gif, and .png images.
    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF and"
            + "PNG Images", "jpg", "gif", "png");
    fchooser.setFileFilter(filter);

    // The return value is the path that the user chooses.
    int retValue = fchooser.showOpenDialog(ViewImpl.this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      path = f.getAbsolutePath();
    }
  }


  /**
   * Prompts the user to choose where to save the currently open image into a file.
   */
  public void openSaveDialogue() {

    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(ViewImpl.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      fileSaveDisplay.setText(f.getAbsoluteFile().getAbsolutePath());
      path = f.getAbsoluteFile().getAbsolutePath();
    }
  }


  /**
   * Shows a dialog box which prompts user to save changes before they quit.
   *
   * @return true if the user wants to save changes, false otherwise
   */
  public boolean openUnsavedChanges() {

    int result = JOptionPane.showConfirmDialog(
            null,
            "You have unsaved changes. Do you want to save your work?",
            "no wait don't go",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);


    return (result == JOptionPane.YES_OPTION);

  }

  /**
   * Prompts the user to choose a country of which they want to make a flag of.
   *
   * @return the country chosen
   */
  public String flagDialog() {

    final String[] countryList = {"Greece", "France", "Switzerland"};
    JComboBox countryListComboBox = new JComboBox(countryList);
    countryListComboBox.setSelectedIndex(countryList.length - 1);
    String input = (String) JOptionPane.showInputDialog(null, "What country?",
            "Choose your country carefully", JOptionPane.QUESTION_MESSAGE,
            null, countryList, countryList[0]);

    return input;
  }

  /**
   * Prompts the user to enter the orientation of the desired rainbow image they want drawn.
   *
   * @return the chosen orientation
   */
  public String rainbowDialog() {
    final String[] orientationList = {"Horizontal", "Vertical"};
    JComboBox rainbowComboBox = new JComboBox(orientationList);
    rainbowComboBox.setSelectedIndex(orientationList.length - 1);
    String input = (String) JOptionPane.showInputDialog(null, "What orientation?",
            "makin' a rainbow", JOptionPane.QUESTION_MESSAGE,
            null, orientationList, orientationList[0]);
    return input;
  }

  /**
   * Prompts the user to choose a width in pixels for their image (a flag or rainbow).
   *
   * @return the width chosen
   */
  public int widthDialog() {

    // Initialize 1000 numbers to display to user to choose.
    String[] stringNumberList = new String[1000];
    int j = 0;
    for (int i = 54; i < stringNumberList.length - 1; i++) {
      stringNumberList[j] = Integer.toString(i);
      j++;
    }

    // Presents the user with a list of 1000 numbers to choose from.
    JComboBox widthComboBox = new JComboBox(stringNumberList);
    widthComboBox.setSelectedIndex(stringNumberList.length - 1);
    String input = (String) JOptionPane.showInputDialog(null,
            "What width (px)?",
            "Selecting the width for your image.", JOptionPane.QUESTION_MESSAGE,
            null, stringNumberList, stringNumberList[0]);

    try {
      return Integer.valueOf(input);
    }

    // If they cancel the operation, it's no big deal.
    catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * Prompts the user to enter a height between 54 (minimum for a flag) and 1000.
   *
   * @return the height that the user has selected
   */
  public int heightDialog() {

    // Initialize 1000 numbers to display to user to choose.
    String[] stringNumberList = new String[1000];
    int j = 0;
    for (int i = 54; i < stringNumberList.length - 1; i++) {
      stringNumberList[j] = Integer.toString(i);
      j++;
    }

    // Presents the user with a list of 1000 numbers to choose from.
    JComboBox heightComboBox = new JComboBox(stringNumberList);
    heightComboBox.setSelectedIndex(stringNumberList.length - 1);
    String input = (String) JOptionPane.showInputDialog(null, "What height?",
            "Selecting the height for your image.", JOptionPane.QUESTION_MESSAGE,
            null, stringNumberList, stringNumberList[0]);

    try {
      return Integer.valueOf(input);
    }

    // If they cancel the operation, it's no big deal.
    catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * Prompts the user to select a number between 1 and 150 (a reasonable maximum), which will be the
   * square size in pixels of the checkerboard they'd like to create.
   *
   * @return the square size in pixels of the checkerboard
   */
  public int checkerboardDialog() {

    // Initialize 150 numbers to display to user to choose.
    String[] stringNumberList = new String[150];
    int j = 0;
    for (int i = 1; i < stringNumberList.length - 1; i++) {
      stringNumberList[j] = Integer.toString(i);
      j++;
    }

    // Presents the user with a list of 1000 numbers to choose from.
    JComboBox heightComboBox = new JComboBox(stringNumberList);
    heightComboBox.setSelectedIndex(stringNumberList.length - 1);
    String input = (String) JOptionPane.showInputDialog(null,
            "How big do you want each square to be?",
            "popping the question", JOptionPane.QUESTION_MESSAGE,
            null, stringNumberList, stringNumberList[0]);

    try {
      return Integer.valueOf(input);
    }

    // If they cancel the operation, it's no big deal.
    catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * Throws up a popup to the user that something went wrong.
   */
  public void errorDialog() {
    JOptionPane.showMessageDialog(this.imagePanel, "There was an error.",
            "don't kill the messenger", JOptionPane.ERROR_MESSAGE);
  }


  /**
   * Allows or disallows the undo button to be clicked on.
   *
   * @param isClickable If the undo button should be clicked on or not
   */
  public void toggleUndo(boolean isClickable) {
    undoMenuItem.setEnabled(isClickable);
  }

  /**
   * Allows or disallows the redo button to be clicked on.
   *
   * @param isClickable If the redo button should be clicked on or not
   */
  public void toggleRedo(boolean isClickable) {
    redoMenuItem.setEnabled(isClickable);
  }

  /**
   * Allows or disallows the adjustment menu items to be clicked.
   *
   * @param isClickable if the button should be clickable or not
   */
  public void toggleAdjustments(boolean isClickable) {
    blurMenuItem.setEnabled(isClickable);
    sharpenMenuItem.setEnabled(isClickable);
    ditherMenuItem.setEnabled(isClickable);
    mosaicMenuItem.setEnabled(isClickable);
    sepiaMenuItem.setEnabled(isClickable);
    greyscaleMenuItem.setEnabled(isClickable);
    saveMenuItem.setEnabled(isClickable);
  }


  /**
   * Adds every menu item as listeners to the features interface.
   *
   * @param features The features interface which implements our model
   */
  @Override
  public void addFeatures(Features features) {

    //File menu action listeners
    loadMenuItem.addActionListener(l -> features.load());
    saveMenuItem.addActionListener(l -> {
      try {
        features.save();
      } catch (IOException e) {
        this.errorDialog();
        System.out.println("Couldn't save the file");
      }
    });
    batchLoadMenuItem.addActionListener(l -> features.batchLoad());
    JTextArea ta = new JTextArea(20, 20);
    batchWriteMenuItem.addActionListener(l -> {
      JOptionPane.showConfirmDialog(null, new JScrollPane(ta));
      features.batchWrite(ta.getText());
    });
    quitMenuItem.addActionListener(l -> {
      try {
        features.quit();
      } catch (IOException e) {
        this.errorDialog();
        System.out.println("Couldn't save the file");
      }
    });

    //Edit menu action listeners
    undoMenuItem.addActionListener(l -> features.undo());
    redoMenuItem.addActionListener(l -> features.redo());

    //Adjustment menu action listeners
    blurMenuItem.addActionListener(l -> features.blur());
    sharpenMenuItem.addActionListener(l -> features.sharpen());
    ditherMenuItem.addActionListener(l -> features.dither());
    mosaicMenuItem.addActionListener(l -> {
      try {
        features.mosaic(Integer.parseInt(JOptionPane.showInputDialog(
                "Enter a number to use as the seed:")));
      } catch (NumberFormatException e) {
        errorDialog();
      }
    });

    sepiaMenuItem.addActionListener(l -> features.sepia());
    greyscaleMenuItem.addActionListener(l -> features.greyscale());

    //Draw menu action listeners
    flagMenuItem.addActionListener(l -> features.flag());
    rainbowMenuItem.addActionListener(l -> features.rainbow());
    checkerBoardMenuItem.addActionListener(l -> features.checkerboard());
  }
}