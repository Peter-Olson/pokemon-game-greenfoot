import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * TextImage.java
 * 
 * This class is used for images that have text built on them. This class uses the special
 * custom font (.TTF) "pokemon_classic" (POKEMON_FONT) for I/O communication with the user.
 * 
 * Source: pokemon_classic.TTF was retrieved from dafont.com by the creator TheLouster115,
 *         aka Luis Silva (https://www.dafont.com/luis-silva.d6380)
 * 
 * -----------------------------------------------------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIELDS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC FIELDS
 * --------------------------------------------------------------------
 * - none
 * 
 * PRIVATE FIELDS
 * --------------------------------------------------------------------
 * - POKEMON_FONT    : The font used for TextImages. Custom fonts
 *                     currently are not working (2/9/22)
 * 
 * - text            : The text to put on this TextImage
 * 
 * - imageLoc        : The location path of this image
 * - image           : The GreenfootImage of this image
 * 
 * - timer           : Used to allow images to disappear after a given
 *                     time has passed
 * - EXPIRATION_TIME : The total time that a non-permanent text-image
 *                     will stay up, in total frames
 * - EXPIRES         : Used to tell whether a TextImage will expire or
 *                     not
 * 
 * - WIDTH, HEIGHT   : The width and height of this TextImage
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CONSTRUCTORS @@@@@@@@@@@@@@@@@@@@@@@@
 * - TextImage( int WIDTH, int HEIGHT, String text, Color color, int fontSize, boolean EXPIRES,
                int textX, int textY, boolean isBold ) :
 *      Create an empty box with text on top
 * 
 * - TextImage( String imageLoc, String text, Color color, int fontSize,
 *              boolean EXPIRES, int textX, int textY ) :
 *      Create an image with text on top
 * 
 * - TextImage( GreenfootImage image, String text, Color color, int fontSize,
 *              boolean EXPIRES, int textX, int textY ) :
 *      Create an image with text on top
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ METHODS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC METHODS
 * --------------------------------------------------------------------
 * - act() : non-permant TextImages are removed after the expiration
 *           time limit
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * -----------------------------------------------------------------------------------------------------------------------------
 * 
 * @author Peter Olson
 * @version 1/30/22
 * @see PokeBattle.java
 */
public class TextImage extends Actor {
    
    //@@Does not work -- custom fonts not supported, from what I can tell
    private final String POKEMON_FONT = "Pokemon Classic Regular"; //Non-logical font name: "Dialog.plain"
    
    private String text;
    
    private String imageLoc;
    
    private GreenfootImage image;
    
    private int timer;
    private final int EXPIRATION_TIME = 400; //works out to about 1 second every 100
    private final boolean EXPIRES;
    
    private final int WIDTH, HEIGHT;
    
    /**
     * Create a TextImage object using the gived image location and the given text input
     * 
     * @param WIDTH The width of the box to print the text
     * @param HEIGHT The height of the box to print the text
     * @param text The text to set on top of this image using the custom font
     * @param color The color to make the text
     * @param fontSize The font size of the text
     * @param EXPIRES True if this image should expire, false otherwise
     * @param textX The x location of this text
     * @param textY The y location of this text
     * @param boolean isBold True if the text should be bold, false otherwise
     * @see GreenfootImage.setFont( Font font )
     * @see GreenfootImage.setColor( Color color )
     * @see GreenfootImage.drawString( String text, int x, int y )
     * @see Actor.setImage( String imageLoc )
     */
    public TextImage( int WIDTH, int HEIGHT, String text, Color color, int fontSize, boolean EXPIRES,
                      int textX, int textY, boolean isBold ) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.text = text;
        this.EXPIRES = EXPIRES;
        
        image = new GreenfootImage( this.WIDTH, this.HEIGHT );
        image.setFont( new Font( POKEMON_FONT, isBold, false, fontSize ) );
        image.setColor( color );
        
        //@@DEBUG -- uncomment to see rectangles bounding boxes
        //image.drawRect( 0, 0, this.WIDTH, this.HEIGHT );
        //image.fillRect( 0, 0, this.WIDTH, this.HEIGHT );
        //@@END
        
        image.drawString( text, textX, textY );
        setImage( image );
        
        timer = 0;
    }
    
    /**
     * Create a TextImage object using the gived image location and the given text input
     * 
     * @param imageLoc The image location of this TextImage
     * @param text The text to set on top of this image using the custom font
     * @param color The color to make the text
     * @param fontSize The font size of the text
     * @param EXPIRES True if this image should expire, false otherwise
     * @param textX The x location of this text
     * @param textY The y location of this text
     * @see GreenfootImage.setFont( Font font )
     * @see GreenfootImage.setColor( Color color )
     * @see GreenfootImage.drawString( String text, int x, int y )
     * @see Actor.setImage( String imageLoc )
     */
    public TextImage( String imageLoc, String text, Color color, int fontSize, boolean EXPIRES, int textX, int textY ) {
        this.imageLoc = imageLoc;
        this.text = text;
        this.EXPIRES = EXPIRES;
        
        image = new GreenfootImage( imageLoc );
        image.setFont( new Font( POKEMON_FONT, fontSize ) );
        image.setColor( color );
        image.drawString( text, textX, textY );
        setImage( image );
        
        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();
        
        timer = 0;
    }
    
    /**
     * Create a TextImage object using the gived image location and the given text input
     * 
     * @param image The GreenfootImage to be used
     * @param text The text to set on top of this image using the custom font
     * @param color The color to make the text
     * @param fontSize The font size of the text
     * @param EXPIRES True if this image should expire, false otherwise
     * @param textX The x location of this text
     * @param textY The y location of this text
     * @see GreenfootImage.setFont( Font font )
     * @see GreenfootImage.setColor( Color color )
     * @see GreenfootImage.drawString( String text, int x, int y )
     * @see Actor.setImage( String imageLoc )
     */
    public TextImage( GreenfootImage image, String text, Color color, int fontSize, boolean EXPIRES, int textX, int textY ) {
        this.imageLoc = "";
        this.text = text;
        this.EXPIRES = EXPIRES;
        
        image.setFont( new Font( POKEMON_FONT, fontSize ) );
        image.setColor( color );
        image.drawString( text, textX, textY );
        setImage( image );
        
        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();
        
        timer = 0;
    }
    
    /**
     * Act - do whatever the TextImage wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     * 
     * @see World.removeObject( Actor actor )
     */
    public void act() {
        if( EXPIRES && timer++ == EXPIRATION_TIME )
            getWorld().removeObject( this );
    }
}
