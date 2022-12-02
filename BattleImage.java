import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * BattleImage.java
 * 
 * This class defines the images of the Pokemon when they are battling. Effects may be applied
 * to these images for the battle animations
 * 
 * -----------------------------------------------------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIELDS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC FIELDS
 * --------------------------------------------------------------------
 * - none
 * 
 * PRIVATE FIELDS
 * --------------------------------------------------------------------
 * - currentImageLoc    : The current image location of this BattleImage
 * - image              : The GreenfootImage of this BattleImage
 * 
 * - WIDTH, HEIGHT      : The width and height (in pixels) of this BattleImage
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CONSTRUCTORS @@@@@@@@@@@@@@@@@@@@@@@@
 * - BatteImage( String imageLoc ) : Create a BattleImage given the image location passed in
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ METHODS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC METHODS
 * --------------------------------------------------------------------
 * - act()          : Does nothing currently
 * 
 * - getWidth()     : Gets the width of this BattleImage
 * - getHeight()    : Gets the height of this BattleImage
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * -----------------------------------------------------------------------------------------------------------------------------
 * 
 * @author Peter Olson
 * @version 1/25/22
 */
public class BattleImage extends Actor {
    
    private String currentImageLoc;
    private GreenfootImage image;
    
    private final int WIDTH, HEIGHT;
    
    /**
     * Create a new BattleImage object
     * 
     * @param imageLoc The location of this Pokemon's image
     * @see Actor.setImage( String imageLoc )
     */
    public BattleImage( String imageLoc ) {
        this.currentImageLoc = imageLoc;
        setImage( currentImageLoc );
        image = new GreenfootImage( this.currentImageLoc );
        WIDTH = image.getWidth();
        HEIGHT = image.getHeight();
    }
    
    /**
     * Act - do whatever the BattleImage wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        // Add your action code here.
    }
    
    /**
     * Gets the width of this BattleImage image
     * 
     * @return int The width of this image in pixels
     */
    public int getWidth() {
        return this.WIDTH;
    }
    
    /**
     * Gets the height of this BattleImage image
     * 
     * @return int The height of this image in pixels
     */
    public int getHeight() {
        return this.HEIGHT;
    }
}
