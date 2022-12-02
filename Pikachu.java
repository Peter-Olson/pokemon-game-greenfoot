
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * Pikachu.java
 * 
 * Pikachu is a PokemonActor. Pikachu generally is an electric type Pokemon, focusing
 * on high speed and agility, while specializing in special electric type attacks
 * 
 * --------------------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIELDS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC FIELDS
 * --------------------------------------------------------------------
 * - none -
 * 
 * PRIVATE FIELDS
 * --------------------------------------------------------------------
 * - xTargetLoc     : The target x location for this Pokemon to travel to
 * - yTargetLoc     : The target y location for this Pokemon to travel to
 * - isTraveling    : Determines whether the Pokemon is currently traveling or not
 * - timer          : Used to slow down or speed up animations
 * - TRAVEL_SPEED   : Used for the frame speed
 * - WALK_SPEED     : How many pixels per repaint the Pokemon travels
 * - xRemaining     : How many pixels in the x direction the Pokemon still has to travel
 * - yRemaining     : How many pixels in the y direction the Pokemon still has to travel
 * - direction      : The current direction that this Pokemon is traveling. See TravelDirection enum
 *
 * - RIGHT          : 0 degrees, used for rotations
 * - DOWN           : 90 degrees, used for rotations
 * - LEFT           : 180 degrees, used for rotations
 * - UP             : 270 degrees, used for rotations
 * 
 * - WALKING_DOWN_IMAGES  : The list of images used to animate Pikachu walking down
 * - WALKING_UP_IMAGES    : The list of images used to animate Pikachu walking up
 * - WALKING_LEFT_IMAGES  : The list of images used to animate Pikachu walking left
 * - WALKING_RIGHT_IMAGES : The list of images used to animate Pikachu walking right
 * - switchedImage        : Used to determine whether Pikachu's image has been switch or not
 * - imageTimer           : Used to control image switch speed
 * 
 * - REG_PIKA_SOUND       : Pikachu's regular sound file. Pikachu makes a sound when the screen is clicked
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ENUMS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * - none
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CONSTRUCTORS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * - Pikachu( int uniqueID ) : Create a default Pikachu object
 * - Pikachu( String name, String imageName, String, battleImageName, String type, int HP, int attack,
 *            int defense, int specialAttack, int specialDefense, int speed,
 *            int evasion, int accuracy, int exp, int level, int wins,
 *            ArrayList<Move> moves, ArrayList<Item> items,
 *            double height, double weight, String description, int uniqueID )
 *             : Create a Pikachu with the given attributes. This Pokemon still must abide
 *               by the limits on starting Pokemon based on STARTING_POINTS
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ METHODS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC METHODS
 * ---------------------------------------------------------------------
 * - act()              : The method called repeatedly by the world PokeBattle.java
 * 
 * PRIVATE METHODS
 * ---------------------------------------------------------------------
 * - moveTowardsClickLocation()    : Moves this Pokemon towards the clicked location
 * - changeImages()                : Change images when this Pokemon moves
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PRIVATE CLASSES @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * - none
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * --------------------------------------------------------------------------------------------
 * 
 * @author Peter Olson
 * @version 1/18/22
 * @see PokemonActor.java
 * @see Pokemon.java
 * @see Pokedex.java
 * @see Move.java
 * @see Item.java
 * @see PokeWorld.java
 */
public class Pikachu extends PokemonActor implements Pokemon {
    
    //Traveling on screen
    private int xTargetLoc;
    private int yTargetLoc;
    private boolean isTraveling = false;
    private int timer = 0;
    private final int TRAVEL_SPEED = 3;
    private final int WALK_SPEED = 5;
    private int xRemaining, yRemaining;
    private TravelDirection direction = TravelDirection.UNKNOWN;
    
    //Used for rotations
    private final int RIGHT = 0;
    private final int DOWN  = 90;
    private final int LEFT  = 180;
    private final int UP    = 270;
    
    //Used for image animations
    private final String[] WALKING_DOWN_IMAGES  = {"./images/pikachu_sm_d.png","./images/pikachu_sm_d2.png"};
    private final String[] WALKING_UP_IMAGES    = {"./images/pikachu_sm_u.png","./images/pikachu_sm_u2.png"};
    private final String[] WALKING_LEFT_IMAGES  = {"./images/pikachu_sm_l.png","./images/pikachu_sm_l2.png"};
    private final String[] WALKING_RIGHT_IMAGES = {"./images/pikachu_sm_r.png","./images/pikachu_sm_r2.png"};
    private boolean switchedImage = false;
    private int imageTimer = 0;
    
    private final String REG_PIKA_SOUND = "./sounds/PikachuFixed.mp3";
    
    /**
     * Used for determining which way the Pokemon is traveling
     */
    public enum TravelDirection {
        UNKNOWN, RIGHT, LEFT, UP, DOWN;
    }
    
    /**
     * Create a Pikachu object with default fields
     * 
     * Checks for valid Pokemon creation (based on PokeWorld.STARTING_POINTS) by calling checkPokemonInit()
     * 
     * @see spendPoints( int points )
     * @see Item.getItemLineFromName( String itemName )
     * @see setCurrentItem( Item item )
     * @see addItem( Item item )
     * @see Item.getRandomItemLine()
     * @see setPokemonNumber()
     * @see checkPokemonInit()
     * @see InvalidPokemonPointsException
     */
    public Pikachu( int uniqueID ) throws InvalidMoveTotalException {
        super( uniqueID );

        NAME         = "Pikachu";
        IMAGE_NAME   = "./images/pikachu_sm_d2.png";
        BATTLE_IMAGE = "./images/battle_pikachu.png";
        TYPE         = "ELECTRIC";
        
        HP             = currentHP             = spendPoints( 20 );
        attack         = currentAttack         = spendPoints( 15 );
        defense        = currentDefense        = spendPoints( 20 );
        specialAttack  = currentSpecialAttack  = spendPoints( 35 );
        specialDefense = currentSpecialDefense = spendPoints( 15 );
        speed          = currentSpeed          = spendPoints( 45 );
        evasion        = currentEvasion        = spendPoints( 20 );
        accuracy       = currentAccuracy       = spendPoints( 15 );
                
        moves = new ArrayList<Move>();
        items = new ArrayList<Item>();
        
        try {
            Item potion = new Item( Item.getItemLineFromName( "Potion" ) );
            setCurrentItem( potion );
            addItem( potion );
            addItem( new Item( Item.getItemLineFromName( "Potion" ) ) );
            addItem( new Item( Item.getRandomItemLine() ) );
            addItem( new Item( Item.getItemLineFromName( "Potion" ) ) );
            addItem( new Item( Item.getItemLineFromName( "PP Max" ) ) );
        } catch( Item.InvalidItemTextFileInputException e ) {
            e.printStackTrace();
        } catch( Item.InvalidPropertiesNameException e ) {
            e.printStackTrace();
        } catch( Item.InvalidItemNameException e ) {
            e.printStackTrace();
        }
        
        height = 1.4;  //1 foot, 4 inches
        weight = 13.0; //13 pounds
        description = "It stores electricity in the electric sacs on its cheeks. " +
                      "When it releases pent-up energy in a burst, the electric " +
                      "power is equal to a lightning bolt.";
        
        criticalHitRatio = currentCriticalHitRatio = (speed/2.0)/PokeWorld.CRITICAL_HIT_RATIO;
                      
        pokemonNumber = setPokemonNumber();
        
        try {
            addMove( this.pokedex.getMoveFromName( "Thunder shock" ) );
            addMove( this.pokedex.getMoveFromName( "Thunder" ) );
            addMove( this.pokedex.getMoveFromName( "Thunder punch" ) );
        } catch( Pokedex.InvalidMovesFileException e ) {
            e.printStackTrace();
        } catch( InvalidPokemonPointsException e ) {
            e.printStackTrace();
        } catch( InvalidTypeException e ) {
            e.printStackTrace();
        }
        
        if( moves.size() < PokeWorld.MIN_NUMBER_OF_MOVES )
            throw new InvalidMoveTotalException("A Pokemon may have no fewer than " + PokeWorld.MIN_NUMBER_OF_MOVES +
                " move, but this Pokemon has " + moves.size() + " moves.");
        if( moves.size() > PokeWorld.MAX_NUMBER_OF_MOVES )
            throw new InvalidMoveTotalException("A Pokemon may have no more than " + PokeWorld.MAX_NUMBER_OF_MOVES +
                " moves, but this Pokemon has " + moves.size() + " moves.");
                
        try {
            checkPokemonInit();
        } catch( InvalidPokemonPointsException e ) {
            e.printStackTrace();
        } catch( InvalidPokemonValuesException e ) {
            e.printStackTrace();
        }
    }
    
    /**
     * Create a Pikachu object with the given fields
     * 
     * Checks for valid Pokemon creation (based on PokeWorld.STARTING_POINTS) by calling checkPokemonInit()
     * 
     * @param name The name of the Pikachu
     * @param imageName The image path / name of this Pikachu's image
     * @param type The Pokemon type of this Pikachu
     * @param HP The maximum HP of this Pikachu
     * @param attack The attack value of this Pikachu
     * @param defense The defense value of this Pikachu
     * @param specialAttack The special attack value of this Pikachu
     * @param specialDefense The special defense value of this Pikachu
     * @param speed The speed value of this Pikachu
     * @param evasion The evasion value of this Pikachu
     * @param accuracy The accuracy value of this Pikachu
     * @param exp This Pikachu's starting exp
     * @param level This Pikachu's starting level
     * @param wins This Pikachu's starting number of wins
     * @param moves This Pikachu's list of moves
     * @param items This Pikachu's list of items
     * @param height The height of this Pikachu, in feet and inches
     * @param weight The weight of this Pikachu, in pounds
     * @param description The description of this Pikachu
     * @param uniqueID The unique ID of this Pikachu
     * @see setPokemonNumber()
     * @see checkPokemonInit()
     */
    public Pikachu( String name, String imageName, String battleImageName, String type,
                    int HP, int attack, int defense, int specialAttack, int specialDefense, int speed,
                    int evasion, int accuracy, int exp, int level, int wins,
                    ArrayList<Move> moves, ArrayList<Item> items,
                    double height, double weight, String description,
                    int uniqueID ) throws InvalidMoveTotalException {
        super( name, imageName, battleImageName, type, HP, attack, defense, specialAttack, specialDefense,
               speed, evasion, accuracy, exp, level, wins, moves, items, height, weight, description, uniqueID );
        
        pokemonNumber = setPokemonNumber();
               
        try {
            checkPokemonInit();
        } catch( InvalidPokemonPointsException e ) {
            e.printStackTrace();
        } catch( InvalidPokemonValuesException e ) {
            e.printStackTrace();
        }
    }
    
    /**
     * Act - do whatever the Pikachu wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     * 
     * @see Greenfoot.getMouseInfo()
     * @see moveTowardsClickLocation()
     * @see changeImages()
     */
    public void act() {
        if( Greenfoot.mouseClicked( getWorld() ) ) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            xTargetLoc = mouse.getX();
            yTargetLoc = mouse.getY();
            this.isTraveling = true;
            
            /*The only way to get audio files to work is to make sure that the mp3 does not have tags ingrained
              within the file*/
            try {
                Greenfoot.playSound( REG_PIKA_SOUND );
            } catch( ArrayIndexOutOfBoundsException ignore ) { }
        }
        if( this.isTraveling ) {
            moveTowardsClickLocation();
            changeImages();
        }
    }
    
    /**
     * Move towards the clicked location while changing images
     * 
     * @see act()
     */
    private void moveTowardsClickLocation() {
        if( ++timer == TRAVEL_SPEED ) {
            int currentX = getX();
            int currentY = getY();
            
            if( direction == TravelDirection.UNKNOWN ) {
                
                //Location of Click is South-East of Pikachu
                if( xTargetLoc >= currentX && yTargetLoc >= currentY ) {
                    if( xTargetLoc - currentX > yTargetLoc - currentY ) {
                           direction = TravelDirection.RIGHT;
                    } else direction = TravelDirection.DOWN;
                    
                //Location of Click is South-West of Pikachu    
                } else if( xTargetLoc <= currentX && yTargetLoc >= currentY ) {
                    if( currentX - xTargetLoc > yTargetLoc - currentY ) {
                           direction = TravelDirection.LEFT;
                    } else direction = TravelDirection.DOWN;
                    
                //Location of Click is North-East of Pikachu
                } else if( xTargetLoc <= currentX && yTargetLoc <= currentY ) {
                    if( xTargetLoc - currentX > currentY - yTargetLoc ) {
                           direction = TravelDirection.RIGHT;
                    } else direction = TravelDirection.UP;
                    
                //Location of Click is North-West of Pikachu
                } else {
                    if( currentX - xTargetLoc > currentY - yTargetLoc ) {
                           direction = TravelDirection.LEFT;
                    } else direction = TravelDirection.UP;
                }
                
            }
            
            if( direction == TravelDirection.LEFT ) {
                if( currentX - xTargetLoc > WALK_SPEED ) setLocation( currentX - WALK_SPEED, currentY );
                else {
                    setLocation( currentX - (currentX - xTargetLoc), currentY );
                    direction = TravelDirection.UNKNOWN;
                }
            } else if( direction == TravelDirection.RIGHT ) {
                if( xTargetLoc - currentX > WALK_SPEED ) setLocation( currentX + WALK_SPEED, currentY );
                else {
                    setLocation( currentX + (xTargetLoc - currentX), currentY );
                    direction = TravelDirection.UNKNOWN;
                }
            } else if( direction == TravelDirection.UP ) {
                if( currentY - yTargetLoc > WALK_SPEED ) setLocation( currentX, currentY - WALK_SPEED );
                else {
                    setLocation( currentX, currentY - (currentY - yTargetLoc) );
                    direction = TravelDirection.UNKNOWN;
                }
            }  else { //TravelDirection.DOWN
                if( yTargetLoc - currentY > WALK_SPEED ) setLocation( currentX, currentY + WALK_SPEED );
                else {
                    setLocation( currentX, currentY + (yTargetLoc - currentY) );
                    direction = TravelDirection.UNKNOWN;
                }
            }
            
            currentX = getX();
            currentY = getY();
            
            if( currentX == xTargetLoc && currentY == yTargetLoc ) {
                isTraveling = false;
                direction = TravelDirection.UNKNOWN;
            }
            
            timer = 0;
        }
    }
    
    /**
     * Change the Pikachu's images as it travels
     * 
     * @see act()
     * @see setImage( String fileName )
     */
    private void changeImages() {
        ++imageTimer;
        //This algorithm only works for 2 image rotations
        if(        direction == TravelDirection.DOWN ) {
            if( !switchedImage && imageTimer % TRAVEL_SPEED == 0) {
                setImage( WALKING_DOWN_IMAGES[ switchedImage ? 1 : 0 ] );
                imageTimer = 0;
                switchedImage = true;
            } else if( imageTimer % TRAVEL_SPEED == 0 ) {
                setImage( WALKING_DOWN_IMAGES[ switchedImage ? 1 : 0 ] );
                imageTimer = 0;
                switchedImage = false;
            }
        } else if( direction == TravelDirection.UP ) {
            if( !switchedImage && imageTimer % TRAVEL_SPEED == 0) {
                setImage( WALKING_UP_IMAGES[ switchedImage ? 1 : 0 ] );
                imageTimer = 0;
                switchedImage = true;
            } else if( imageTimer % TRAVEL_SPEED == 0 ) {
                setImage( WALKING_UP_IMAGES[ switchedImage ? 1 : 0 ] );
                imageTimer = 0;
                switchedImage = false;
            }
        } else if( direction == TravelDirection.LEFT ) {
            if( !switchedImage && imageTimer % TRAVEL_SPEED == 0) {
                setImage( WALKING_LEFT_IMAGES[ switchedImage ? 1 : 0 ] );
                imageTimer = 0;
                switchedImage = true;
            } else if( imageTimer % TRAVEL_SPEED == 0 ) {
                setImage( WALKING_LEFT_IMAGES[ switchedImage ? 1 : 0 ] );
                imageTimer = 0;
                switchedImage = false;
            }
        } else if( direction == TravelDirection.RIGHT ) {
            if( !switchedImage && imageTimer % TRAVEL_SPEED == 0) {
                setImage( WALKING_RIGHT_IMAGES[ switchedImage ? 1 : 0 ] );
                imageTimer = 0;
                switchedImage = true;
            } else if( imageTimer % TRAVEL_SPEED == 0 ) {
                setImage( WALKING_RIGHT_IMAGES[ switchedImage ? 1 : 0 ] );
                imageTimer = 0;
                switchedImage = false;
            }
        } else {             // TravelDirection.UNKNOWN
            imageTimer = 0;
            switchedImage = false;
        }
    }
    
    /**
     * Sets the number of this Pokemon. If this Pokemon's name is found within the
     * POKEMON_POKEDEX_LIST_LOC file, then this Pokemon's number is fixed to the
     * number in the list. If this Pokemon's name is not found within the
     * POKEMON_POKEDEX_LIST_LOC file, then this Pokemon's number is fixed to the
     * last number in the list, and this Pokemon is added to the POKEMON_POKEDEX_LIST_LOC
     * file
     * 
     * @return int The number of this Pokemon
     * @see Pikachu(...)
     * @see Pokedex.addPokemon( String pokemonName )
     * @see Pokedex.getPokemonNumber( String pokemonName )
     * @see System.lineSeparator()
     * @see Pokedex.hasPokemonNumber( int pokemonNumber )
     * @see PokeWorld.addToFile( String fileLoc, String text )
     */
    private int setPokemonNumber() {
        Scanner scanner = null;
        try {
            scanner = new Scanner( new File( Pokedex.POKEMON_POKEDEX_LIST_LOC ) );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        pokedex.addPokemon( NAME );
        
        int pokemonNumber = pokedex.getPokemonNumber( NAME );
        
        if( !pokedex.hasPokemonNumber( pokemonNumber ) ) {
            String text = System.lineSeparator() + pokemonNumber + "@" + NAME;
            PokeWorld.addToFile( Pokedex.POKEMON_POKEDEX_LIST_LOC, text );
        }
        
        return pokemonNumber;
    }

}
