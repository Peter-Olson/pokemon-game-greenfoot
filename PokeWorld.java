import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * PokeWorld.java
 * 
 * Adds extra functionality to worlds
 * 
 * -------------------------------------------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIELDS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC FIELDS
 * --------------------------------------------------------------------
 * - STARTING_POINTS : The total starting points for creating a Pokemon. Whether creating a
 *                     Pokemon using a default constructor, or using a Pokemon that allows the
 *                     user to specify its attributes, there is a maximum number of points that
 *                     can be spent on that Pokemon, according to the STARTING_POINTS value
 * - POINTS_GIVEN_ON_LEVEL_UP : How many points are awarded to the Pokemon when it levels up
 * - NEW_MOVE_COST            : The cost of add a new move for this Pokemon
 * - MINIMUM_POINTS_TO_SPEND  : The minimum amount of points that must be spent when spending
 *                              points
 * - MAX_NUMBER_OF_MOVES      : The maximum number of moves that a Pokemon is allowed to know
 * - MAX_NUMBER_OF_TYPES      : The maximum number of types that a Pokemon is allowed to have
 * - MIN_NUMBER_OF_TYPES      : The minimum number of types that a Pokemon must have
 * 
 * - TOTAL_TYPES              : The total types of Pokemon / moves that exist in the world
 * 
 * - ERROR                    : The value associated with errors, or with results not fitting within
 *                              an expected range of values
 * 
 * PRIVATE FIELDS
 * --------------------------------------------------------------------
 * - none -
 * 
 * PROTECTED FIELDS
 * --------------------------------------------------------------------
 * - none -
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ METHODS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC METHODS
 * --------------------------------------------------------------------
 * - getImageWidth( String fileName )           : Gets the width of the image associated with this file path
 * - getImageHeight( String fileName )          : Gets the height of the image associated with this file path
 * 
 * - getTextWidth( String text, int fontSize, double pixelToFontSizeRatio ) : Gets the width of the text (in pixels)
 * 
 * - getFile( String filePath )                 : Gets the File associated with the given file path
 * - getFileText( File file )                   : Gets the text in the given File
 * - writeToFile( String fileLoc, String text ) : Overwrites the File associated with the file path with the text
 * - addToFile( String fileLoc, String text )   : Adds the text to the end of the File associated with the file path
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CLASSES @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PROTECTED CLASSES
 * --------------------------------------------------------------------
 * Pair.java
 * - FIELDS
 *      * first  : The first Actor in the Pair
 *      * second : The second Actor in the Pair
 * - CONSTRUCTORS
 *      * Pair( Actor first, Actor second )     : Create a Pair given the two actors
 * - METHODS
 *      * getFirst()                : Gets the first Actor in the Pair
 *      * getSecond()               : Gets the second Actor in the Pair
 *      * setFirst( Actor actor )   : Sets the first Actor in the Pair to the new Actor
 *      * setSecond( Actor actor )  : Sets the second Actor in the Pair to the new Actor
 * 
 * -------------------------------------------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * 
 * @author Peter Olson 
 * @version 1/25/22
 */
public class PokeWorld extends World {

    public static final int STARTING_POINTS          = 200;
    
    public static final int POINTS_GIVEN_ON_LEVEL_UP = 5;
    
    public static final int NEW_MOVE_COST            = 5;
    public static final int MINIMUM_POINTS_TO_SPEND  = 5;
    public static final int MAX_NUMBER_OF_MOVES      = 4;
    public static final int MIN_NUMBER_OF_MOVES      = 1;

    public static final int MAX_NUMBER_OF_TYPES      = 2;
    public static final int MIN_NUMBER_OF_TYPES      = 1;
    
    public static final int MIN_ATTRIBUTE            = 5;
    
    public static final int MAX_POKEMON_TYPES        = 2;
    public static final int TOTAL_TYPES              = 16;
    
    public static final int MIN_FIELDS_FOR_MOVE      = 7;
    public static final int TOTAL_FIELDS_FOR_MOVE    = 80;
    
    public static final double CRITICAL_HIT_RATIO    = 256; // (X/2)/256, where X is equal to the Pokemon's speed
    
    public static final String POKEMON_MOVES_FILE    = "./pokemonMoves.txt";
    
    //Used for throwing exceptions, or for tokens or values not being found or meeting criteria
    public static final int ERROR                    = -1;
    
    /**
     * Constructor for objects of class PokeWorld.
     * 
     */
    public PokeWorld( int width, int height, int cellSize ) {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super( width, height, cellSize ); 
    }
    
    /**
     * Gets the width of an image given its file path
     * 
     * @param fileName The file path of this image
     * @return int The width of the image found at the given file path
     * @see GreenfootImage.getWidth()
     */
    public static int getImageWidth( String fileName ) {
        GreenfootImage img = new GreenfootImage( fileName );
        return img.getWidth();
    }
    
    /**
     * Gets the height of an image given its file path
     * 
     * @param fileName The file path of this image
     * @return int The width of the image found at the given file path
     * @see GreenfootImage.getHeight();
     */
    public static int getImageHeight( String fileName ) {
        GreenfootImage img = new GreenfootImage( fileName );
        return img.getHeight();
    }
    
    /**
     * Gets the width (in pixels) of a line of text of the default font
     * 
     * @param text The text to find the width of (in pixels)
     * @param fontSize The size of this font (its height in pixels, approximately)
     * @param pixelToFontSizeRatio The ratio of height to width
     * @return int The width (in pixels) of this text
     */
    public static int getTextWidth( String text, int fontSize, double pixelToFontSizeRatio ) {
        return (int)(text.length() * fontSize / pixelToFontSizeRatio);
    }
    
    /**
     * Scale a GreenfootImage down to fit within a specified width and height
     * 
     * @see pokedexExperience()
     * @see GreenfootImage.getWidth()
     * @see GreenfootImage.getHeight()
     * @see GreenfootImage.scale( int width, int height )
     */
    public static void scaleImageToMaxLimit( GreenfootImage image, int limitWidth, int limitHeight ) {
        int widthImg = image.getWidth();
        int heightImg = image.getHeight();
        int largestDimension = widthImg > heightImg ? widthImg : heightImg;
        double scaleRatio = widthImg > heightImg ? (double)limitWidth / (double)largestDimension
                                                 : (double)limitHeight / (double)largestDimension;
        image.scale( (int)((double)widthImg * scaleRatio), (int)((double)heightImg * scaleRatio ) );
    }
    
    /**
     * Capitalized the first letter of a token and return that token
     * 
     * @param token The token to capitalize the first letter of
     * @return The same token with the first letter capitalized
     */
    public static String capFirstLetter( String token ) {
        if( !Character.isLetter( token.charAt(0) ) ) return token;
        
        if( token.length() == 1 ) return token.toUpperCase();
        
        return String.valueOf( token.charAt(0) ).toUpperCase() + token.substring( 1, token.length() );
    }
    
    /**
     * Gets a File based on the file name and the relative path
     * 
     * @param String filePath The path of the File to be found
     * @return File The File found from this name. If not found, throws a FileNotFoundException
     */
    public static File getFile( String filePath ) throws FileNotFoundException {
        File dir = new File(".");
        File[] filesList = dir.listFiles();
        for( File file: filesList ) {
            if( file.getName().equals( filePath ) ) return file;
        }
        
        throw new FileNotFoundException("File not found. Path of file not found: " + filePath );
        
    }
    
    /**
     * Gets the contents of the File as a String
     * 
     * @param file The file to get the text from
     * @return String The contents of the File converted to a String
     * @see addToPokedex( PokemonActor pokemon, PokemonActor[] pokemonList )
     */
    public static String getFileText( File file ) {
        Scanner scanner = null;
        try {
            scanner = new Scanner( file );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        String text = "";
        while( scanner.hasNextLine() ) {
            text += scanner.nextLine();
        }
        
        scanner.close();
        
        return text;
    }
    
    /**
     * Overwrite an existing File
     * 
     * @param fileLoc The location of the File to write to
     * @param text The text to write to the File
     */
    public static void writeToFile( String fileLoc, String text ) {
        FileWriter fw = null;
        try {
            fw = new FileWriter( fileLoc );
            fw.write( text );
            fw.close();
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }
    
    /**
     * Add to an existing File
     * 
     * @param fileLoc The location of the File to write to
     * @param text The text to write to the File
     */
    public static void addToFile( String fileLoc, String text ) {
        FileWriter fw = null;
        try {
            fw = new FileWriter( fileLoc, true );
            fw.write( text );
            fw.close();
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }
    
    /**
     * Holds the pair of Actors
     * 
     * @see act()
     */
    protected class Pair {
        
        private Actor first;
        private Actor second;
        
        /**
         * Creates a pair of Actors
         * 
         * @param first The first Actor in the Pair
         * @param second The second Actor in the Pair
         */
        public Pair( Actor first, Actor second ) {
            this.first = first;
            this.second = second;
        }
        
        /**
         * Get the first Actor in the Pair
         * 
         * @return Actor The first Actor in the pair
         */
        public Actor getFirst() {
            return this.first;
        }
        
        /**
         * Get the second Actor in the Pair
         * 
         * @return Actor The second Actor in the Pair
         */
        public Actor getSecond() {
            return this.second;
        }
        
        /**
         * Set the first Actor to a new Actor
         * 
         * @param first The Actor to change the first Actor to
         */
        public void setFirst( Actor first ) {
            this.first = first;
        }
        
        /**
         * Set the second Actor to a new Actor
         * 
         * @param second The Actor to change the second Actor to
         */
        public void setSecond( Actor second ) {
            this.second = second;
        }
    }
}
