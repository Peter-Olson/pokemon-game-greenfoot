import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import javax.tools.*;
import java.lang.reflect.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;
import java.lang.IllegalArgumentException;
import java.lang.ExceptionInInitializerError;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * OfficerJenny.java
 * 
 * This class checks PokemonActor java files to make sure that they are following all
 * standards and regulations. Any PokemonActor that does not follow all standards and
 * regulations will be destroyed and declared unfit for battle.
 * 
 * See DISQUALIFICATION_REASONS for the standards and requirements that Officer Jenny
 * checks
 * 
 * ----------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIELDS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC FIELDS
 * ----------------------------------------------------------------------------------
 * - none -
 * 
 * PRIVATE FIELDS
 * ----------------------------------------------------------------------------------
 * - COMPILE_SUCCESSFUL : Used in doesCompile( File javaFile )
 * -
 * - initActConstruct   : Used to tell World that OfficerJenny is in to initialize
 *                        functions that would normally be called within the
 *                        constructor. This is done since the Actor objects cannot
 *                        get a World reference when being initialized within their
 *                        constructor, per the way that Greenfoot works
 * -
 * - inspectionTestNumber     : Keeps track of the inspection test number
 * - DISQUALIFICATION_REASONS : The list of reasons for disqualification, which are
 *                              used to display to the user when their Pokemon is
 *                              disqualified
 * -
 * - POKEMON_ACTOR_MR_OLSON_METHODS_FILE_LOC : The file path location where the
 *                                             permanent methods from PokemonActor
 *                                             are stored
 * -
 * - WHISTLE_SOUND_LOC : The file path location for the whistle mp3
 * - TEXT_BOX_BIG      : The file path location for a large text box image
 * -
 * - leftPics          : The file path location list for animations of OJ walking
 *                       left
 * - rightPics         : The file path location list for animations of OJ walking
 *                       right
 * - upPics            : The file path location list for animations of OJ walking up
 * - downPics          : The file path location list for animations of OJ walking
 *                       down
 * - TOTAL_FRAMES      : The total number of frames used for walking animations
 * - IMAGE_TIMER       : Used to determine how fast images change for walking
 *                       animations
 * - imageCounter      : Used as a counter to know when to change images
 * - frameCounter      : Keeps track of the number of frames in the walking animation
 * 
 * - direction         : The current direction that OJ is traveling
 * - SPEED             : The speed of OJ (in pixels per frame)
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ENUMS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC ENUMS
 * ----------------------------------------------------------------------------------
 * - none
 * 
 * PRIVATE ENUMS
 * ----------------------------------------------------------------------------------
 * - Direction         : LEFT, RIGHT, UP, DOWN
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CONSTRUCTORS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * - OfficerJenny()    : Create an OJ object. This default constructor allows for the
 *                       Pokemon checking method to be called, whichs makes sure that
 *                       all active Pokemon follow all rules, standards, and
 *                       regulations
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ METHODS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC METHODS
 * ----------------------------------------------------------------------------------
 * - act()             : If this is the first time running the act() method, inspect
 *                       the Pokemon. Then move OJ if the arrow keys are being pressed
 * 
 * PRIVATE METHODS
 * ----------------------------------------------------------------------------------
 * - moveOJ()          : Move OJ when the arrow keys are pressed
 * 
 * - inspectPokemon()  : Inspect the active Pokemon to make sure that they are following
 *                       all rules, regulations, and standards related to their field values,
 *                       and the code within their Java files. 
 *                       
 * - disqualifyPokemon( PokemonActor pokemon, World world ) : Disqualify a Pokemon by removing
 *      it from the world and displaying the reason for the disqualification
 *      
 * - inspectPokemon( PokemonActor pokemon ) : Helper method to inspectPokemon() that checks
 *      the followings requirements:
 *      
 *      1) PokemonActors have spent 200 or less total points
 *      2) PokemonActors have both a default constructor and a regular constructor
 *      3) PokemonActors extend the PokemonActor class and implement the Pokemon interface
 *      4) PokemonActors do not have any hidden methods that increase any of their stats greatly
 *      5) PokemonActors do not have any methods that are being overriden
 *      6) PokemonActors do not have any hidden methods that illegally modify their set of moves
 *      7) PokemonActors do not have modified methods that were given by Mr. Olson
 * 
 * - checkStartingPoints( Constructor[] pokemonList ) : Checks if the Pokemon has points spent
 *      that is less than or equal to the STARTING_POINTS value
 * - checkConstructors( Constructor[] pokemonList ) : Checks if the Pokemon has both a default
 *      constructor and a non-default constructor
 * - checkClassHeader( File pokemonFile ) : Checks if the Pokemon class both extends the
 *      PokemonActor class and implements the Pokemon interface
 * - checkMethods( File pokemonFile ) : Checks the methods of this Pokemon to see if there are
 *      any methods that are illegally altering the Pokemon's stats to make it higher than
 *      the total points allowed
 * - checkOverrides( Method[] pokemonMethods, Method[] pokemonActorMethods ) : Checks if the
 *      Pokemon class has any overridden methods from its superclass, PokemonActor.java
 * - checkMoves( File pokemonFile ) : Checks if the Pokemon is illegally modifying any of
 *      their moves using Move.java's set methods, which should only be used by the
 *      World classes
 * - checkModifiedMethods( File pokemonFile ) : Check if any of the methods that Mr. Olson
 *      has written have been modified
 * 
 * - getMethods( File pokemonFile )     : Gets a list of Methods in the given Pokemon file
 * - getPokemonConstructors( File pokemonFile ) : Gets a list of constructors in the given
 *                                                Pokemon file
 * - getClassFromFile( File file )      : Gets the Java class derived from this Java file
 * 
 * - doesCompile( File javaFile )       : @@BROKEN - Will not work with Greenfoot classes
 *                                        Determines whether the given Java file will
 *                                        compile
 * 
 * - getJavaFile( PokemonActor pokemon ): Get the Java file that corresponds with this
 *                                        Pokemon
 * - getFiles()                         : Gets the list of Java files that are in the
 *                                        current directory, but are not files that are
 *                                        not PokemonActor subclasses
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CLASSES @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC CLASSES
 * ----------------------------------------------------------------------------------
 * ActivePokemonStateException : Exception class related to errors when trying to use
 *                               a Pokemon object that was not recorded as being
 *                               active within the world
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * ----------------------------------------------------------------------------------
 * 
 * @author Peter Olson
 * @version 1/25/22
 */
public class OfficerJenny extends Actor {
    
    private final int COMPILE_SUCCESSFUL = 0;
    
    /* Used to tell World that OfficerJenny is in to initialize functions that would normally be called within
     * the constructor. This is done since the Actor objects cannot get a World reference when being initialized
     * within their constructor, per the way that Greenfoot works
     */
    private boolean initActConstuct = false;
    
    private int inspectionTestNumber = 0;
    private final String[] DISQUALIFICATION_REASONS = {"1. Pokemon has spent more than 200 points \nor has spent more points than is \nallowed at the current level.",
                                                       "2. Pokemon does not have the correct number \nof constructors.",
                                                       "3. Pokemon does not extend the PokemonActor \nclass and implement the Pokemon interface.",
                                                       "4. Pokemon illegally increases their \nstats within certain methods.",
                                                       "5. Pokemon illegally overrides methods \ninherited from the PokemonActor class.",
                                                       "6. Pokemon illegally modifies their move set.",
                                                       "7. Pokemon uses modified methods \nthat are not allowed to be changed."};
    
    private final String POKEMON_ACTOR_MR_OLSON_METHODS_FILE_LOC = "./pokemonActorUnmodifiedMethods.txt";
    
    private final String WHISTLE_SOUND_LOC = "./sounds/whistle.mp3";
    private final String TEXT_BOX_BIG = "./images/pokemon_text_box_320x200.png";
    
    private final String[] leftPics  = {"./images/oj_left_1.png" ,"./images/oj_left_2.png" ,"./images/oj_left_3.png" ,"./images/oj_left_4.png" };
    private final String[] rightPics = {"./images/oj_right_1.png","./images/oj_right_2.png","./images/oj_right_3.png","./images/oj_right_4.png"};
    private final String[] upPics    = {"./images/oj_up_1.png"   ,"./images/oj_up_2.png"   ,"./images/oj_up_3.png"   ,"./images/oj_up_4.png"   };
    private final String[] downPics  = {"./images/oj_down_1.png" ,"./images/oj_down_2.png" ,"./images/oj_down_3.png" ,"./images/oj_down_4.png" };
    private final int TOTAL_FRAMES = 4;
    private final int IMAGE_TIMER  = 5;
    private int imageCounter       = 0;
    private int frameCounter       = 0;
    private Direction direction    = Direction.DOWN;
    private final int SPEED        = 5;
    
    /**
     * Direction that OJ is walking / facing
     */
    private enum Direction {
        LEFT, RIGHT, UP, DOWN;
    }
    
    /**
     * Default constructor. OfficerJenny inspects all PokemonActors in the world
     * when instantiated
     * 
     * @see inspectPokemon()
     */
    public OfficerJenny() {
        this.initActConstuct = true;
    }
    
    /**
     * Act - do whatever the OfficerJenny wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     * 
     * @see inspectPokemon()
     * @see moveOJ();
     */
    public void act() {
        boolean passInspection;
        if( this.initActConstuct ) {
            passInspection = inspectPokemon();
            this.initActConstuct = false;
        }
        
        moveOJ();
    }
    
    /**
     * Officer Jenny moves according the direction of the arrow keys pressed
     * 
     * @see act()
     * @see Direction enum
     * @see IMAGE_TIMER
     * @see SPEED
     * @see TOTAL_FRAMES
     */
    private void moveOJ() {
        if( Greenfoot.isKeyDown("left") ) {
            if( direction != Direction.LEFT )
                frameCounter = imageCounter = 0;
                
            direction = Direction.LEFT;
            if( imageCounter++ == IMAGE_TIMER ) {
                imageCounter = 0;
                setImage( leftPics[ frameCounter++ ] );
                setLocation( getX() - SPEED, getY() );
                
                if( frameCounter == TOTAL_FRAMES )
                    frameCounter = 0;
            }
        } else if( Greenfoot.isKeyDown("right") ) {
            if( direction != Direction.RIGHT )
                frameCounter = imageCounter = 0;
                
            direction = Direction.RIGHT;
            if( imageCounter++ == IMAGE_TIMER ) {
                imageCounter = 0;
                setImage( rightPics[ frameCounter++ ] );
                setLocation( getX() + SPEED, getY() );
                
                if( frameCounter == TOTAL_FRAMES )
                    frameCounter = 0;
            }
        } else if( Greenfoot.isKeyDown("down") ) {
            if( direction != Direction.DOWN )
                frameCounter = imageCounter = 0;
                
            direction = Direction.DOWN;
            if( imageCounter++ == IMAGE_TIMER ) {
                imageCounter = 0;
                setImage( downPics[ frameCounter++ ] );
                setLocation( getX(), getY() + SPEED );
                
                if( frameCounter == TOTAL_FRAMES )
                    frameCounter = 0;
            }
        }  else if( Greenfoot.isKeyDown("up") ) {
            if( direction != Direction.UP )
                frameCounter = imageCounter = 0;
                
            direction = Direction.UP;
            if( imageCounter++ == IMAGE_TIMER ) {
                imageCounter = 0;
                setImage( upPics[ frameCounter++ ] );
                setLocation( getX(), getY() - SPEED );
                
                if( frameCounter == TOTAL_FRAMES )
                    frameCounter = 0;
            }
        }
    }
     
    /**
     * Review the code of all instantiated PokemonActor's that are in the world.
     * 
     * Officer Jenny checks the following:
     * 1) PokemonActors have spent 200 or less total points
     * 2) PokemonActors have both a default constructor and a regular constructor
     * 3) PokemonActors extend the PokemonActor class and implement the Pokemon interface
     * 4) PokemonActors do not have any hidden methods that increase any of their stats greatly
     * 5) PokemonActors do not have any methods that are being overriden
     * 6) PokemonActors do not have any hidden methods that illegally modify their set of moves
     * 7) PokemonActors do not have modified methods that were given by Mr. Olson
     * 
     * @see OfficerJenny(...)
     * @see inspectPokemon( PokemonActor pokemon )
     */
    private boolean inspectPokemon() {
        boolean passInspection = true;
        
        World world = getWorld();
        List<PokemonActor> pokemonFound = getObjectsInRange( world.getWidth(), PokemonActor.class );
        if( pokemonFound.size() == 0 ) return false;
        
        for( PokemonActor pokemon: pokemonFound ) {
            passInspection &= inspectPokemon( pokemon );
            if( !passInspection ) {
                try {
                    disqualifyPokemon( pokemon, world );
                } catch( ActivePokemonStateException e ) {
                    e.printStackTrace();
                }
            }
        }
        
        return passInspection;
    }
    
    /**
     * Disqualify the Pokemon by removing it from the world and announcing the reasons for
     * the disqualification
     * 
     * @param pokemon The pokemon that is being disqualified
     * @param world The world object reference
     * @see inspectPokemon()
     * @see DISQUALIFICATION_REASONS
     */
    private void disqualifyPokemon( PokemonActor pokemon, World world ) throws ActivePokemonStateException {
        final int MEDIUM_FONT_SIZE = 14;
        
        boolean successfullyRemoved = ((PokeArena)world).removeActivePokemon( pokemon );
        if( !successfullyRemoved )
            throw new ActivePokemonStateException("Error: Tried to remove " + pokemon.getName() +
                " that did not exist within the active pokemon list stored within the word.");
        
        world.removeObject( pokemon );
        
        Greenfoot.playSound( WHISTLE_SOUND_LOC );
        
        world.addObject( new TextImage( TEXT_BOX_BIG,
                                        pokemon.getName() + " has been disqualified for the\n following reason:\n" +
                                            DISQUALIFICATION_REASONS[ inspectionTestNumber - 1 ],
                                        Color.BLACK,
                                        MEDIUM_FONT_SIZE,
                                        true, /* expires */
                                        35,
                                        75
                                      ),
                         world.getWidth()/2, world.getHeight()/2 );
        
    }
    
    /**
     * Review the code of the PokemonActor given
     * 
     * Officer Jenny checks the following:
     * 1) PokemonActors have spent 200 or less total points
     * 2) PokemonActors have both a default constructor and a regular constructor
     * 3) PokemonActors extend the PokemonActor class and implement the Pokemon interface
     * 4) PokemonActors do not have any hidden methods that increase any of their stats greatly
     * 5) PokemonActors do not have any methods that are being overriden
     * 6) PokemonActors do not have any hidden methods that illegally modify their set of moves
     * 7) PokemonActors do not have modified methods that were given by Mr. Olson
     * 
     * @see inspectPokemon()
     * @see getJavaFile( PokemonActor pokemon )
     * @see doesCompile( File javaFile )
     * @see getMethods( File javaFile )
     * @see getPokemonConstructors( File pokemonFile )
     * @see checkStartingPoints( Constructor[] constructorList )
     * @see checkConstructors( Constructor[] constructorList )
     * @see checkClassHeader( File pokemonFile )
     * @see checkMethods( File pokemonFile )
     * @see checkOverrides( Method[] pokemonMethods, Method[] pokemonActorMethods )
     * @see checkMoves( File pokemonFile )
     * @see checkModifiedMethods( File pokemonFile )
     * @see global variable: inspectionTestNumber -- used for debugging and for providing the reason for disqualification
     */
    private boolean inspectPokemon( PokemonActor pokemon ) {
        boolean passInspection = true;
        inspectionTestNumber = 0;
        
        //Get Java file that corresponds with Pokemon
        File pokemonFile = null;
        try {
            pokemonFile = getJavaFile( pokemon );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
            System.exit(0);
        }
        
        //Get Java file for PokemonActor
        File pokemonActorFile = null;
        try {
            pokemonActorFile = getJavaFile( new PokemonActor( 0 ) );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
            System.exit(0);
        }
        
        //Check if Java File compiles -- @@Does not work due to Greenfoot dependencies
        //passInspection &= doesCompile( pokemonFile );
        //if( !passInspection ) return false;
        
        //Get list of Methods and Constructors
        Method[] methodList = getMethods( pokemonFile );
        Constructor[] constructorList = getPokemonConstructors( pokemonFile );
        Method[] pokemonActorMethods = getMethods( pokemonActorFile );
        
        //1. Check if Pokemon has less than or equal to PokeWorld.STARTING_POINTS total points used to make the Pokemon
        inspectionTestNumber = 1;
        passInspection &= checkStartingPoints( constructorList );
        if( !passInspection ) return false;
        
        //2. Check if Pokemon has a default constructor and a non-default constructor
        inspectionTestNumber = 2;
        passInspection &= checkConstructors( constructorList );
        if( !passInspection ) return false;
        
        //3. Check if Pokemon extends the PokemonActor class and implements the Pokemon interface
        inspectionTestNumber = 3;
        passInspection &= checkClassHeader( pokemonFile );
        if( !passInspection ) return false;
        
        //4. Check if Pokemon has any illegal methods that increase their stats greatly
        inspectionTestNumber = 4;
        passInspection &= checkMethods( pokemonFile );
        if( !passInspection ) return false;
        passInspection &= checkMethods( pokemonActorFile );
        if( !passInspection ) return false;
        
        //5. Check if Pokemon is overriding any of the PokemonActor methods, which is not allowed
        inspectionTestNumber = 5;
        passInspection &= checkOverrides( methodList, pokemonActorMethods );
        if( !passInspection ) return false;
        
        //6. Check if Pokemon is illegally modifying any of their moves
        inspectionTestNumber = 6;
        passInspection &= checkMoves( pokemonFile );
        if( !passInspection ) return false;
        passInspection &= checkMoves( pokemonActorFile );
        if( !passInspection ) return false;
        
        //7. Checks if methods that Mr. Olson gave have been modified
        inspectionTestNumber = 7;
        passInspection &= checkModifiedMethods( pokemonActorFile );
        if( !passInspection ) return false;
        
        return passInspection;
    }
    
    /**
     * Check whether the Pokemon class has equal to or less than the total
     * PokeWorld.STARTING_POINTS value
     * 
     * @param pokemonList The list of constructors to run
     * @return boolean True if the PokemonActor has spent less than or equal to PokeWorld.STARTING_POINTS,
     *                 and has all values greater than PokeWorld.MIN_ATTRIBUTE, false otherwise
     * @see inspectPokemon( PokemonActor pokemon )
     * @see Constructor.newInstance()
     */
    private boolean checkStartingPoints( Constructor[] pokemonList ) {
        PokemonActor testPokemon = null;
        for( int i = 0; i < pokemonList.length; i++ ) {
            if( pokemonList[i].getParameterCount() == 1 ) {
                try {
                    testPokemon = (PokemonActor)pokemonList[i].newInstance( 0 );
                } catch( InstantiationException e ) {
                    e.printStackTrace();
                } catch( IllegalAccessException e ) {
                    e.printStackTrace();
                } catch( InvocationTargetException e ) {
                    e.printStackTrace();
                } catch( IllegalArgumentException e ) {
                    e.printStackTrace();
                } catch( ExceptionInInitializerError e ) {
                    e.printStackTrace();
                }
            }
        }
        
        try {
            testPokemon.checkPokemonInit();
        } catch( Pokemon.InvalidPokemonPointsException e ) {
            return false;
        } catch( Pokemon.InvalidPokemonValuesException e ) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Checks if the PokemonActor has a default constructor (actually, not a true default constructor,
     * but rather a default constructor with one parameter, the unique ID) and a non-default constructor
     * 
     * @param pokemonList The list of constructors of the PokemonActor (subclass)
     * @return boolean True if the Pokemon has a default constructor and a non-default constructor, false otherwise
     * @see inspectPokemon( PokemonActor pokemon )
     */
    private boolean checkConstructors( Constructor[] pokemonList ) {
        if( pokemonList.length < 2 ) return false;
        
        for( int i = 0; i < pokemonList.length; i++ ) {
            if( pokemonList[i].getParameterCount() == 1 ) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Checks to see if the Pokemon extends the PokemonActor class and implements the Pokemon interface
     * 
     * @param pokemonFile The Java file of the PokemonActor
     * @return boolean True if the Pokemon extends the PokemonActor class and implements the Pokemon interface,
     *                 false otherwise
     * @see inspectPokemon( PokemonActor pokemon )
     * @see getClassFromFile( File file )
     */
    private boolean checkClassHeader( File pokemonFile ) {
        Class className = getClassFromFile( pokemonFile );
        
        final String POKEMON_INTERFACE = "Pokemon";
        boolean headerIsGood = false;
        
        //Check that implements 'Pokemon' interface
        Type[] interfaceList = className.getGenericInterfaces();
        for( int i = 0; i < interfaceList.length; i++ ) {
            String nameOfInterfaceDebug = interfaceList[i].getTypeName();
            if( interfaceList[i].getTypeName().equals( POKEMON_INTERFACE ) ) headerIsGood = true;
        }
        
        //Check that extends PokemonActor
        
        
        return headerIsGood;
    }
    
    /**
     * Checks the methods of this Pokemon to see if there are any methods that
     * are illegally altering the Pokemon's stats to make it higher than the
     * total points allowed
     * 
     * This method assumes that there should be no parts of the program that is adding a field value plus
     * another value to that field value. This method may not be valid in the future as Pokemon abilities
     * and functionality is expanded
     * 
     * @param pokemonFile The file for this Pokemon
     * @return boolean True if this pokemon does not have any illegal methods, false otherwise
     * @see inspectPokemon( PokemonActor pokemon )
     */
    private boolean checkMethods( File pokemonFile ) {
        /*
         * Fields to watch out for: HP, attack, defense, specialAttack, specialDefense, speed, evasion,
         * accuracy, points, exp, level, wins, currentHP, currentAttack, currentDefense, currentSpecialAttack,
         * currentSpecialDefense, currentSpeed, currentEvasion, currentAccuracy
         */
        final String[] FIELD_LIST = {"HP","attack","defense","specialAttack","specialDefense","speed","evasion",
                                     "accuracy","points","exp","level","wins","criticalHitRatio"};
        final String[] LIST_OF_ILLEGAL_ACTIONS       = {" += ",  "+= ",  " +=",  " = ","= "," ="," *= ",  " *=",  "*= "  };
        final String[] LIST_OF_ILLEGAL_ACTIONS_REGEX = {" \\+= ","\\+= "," \\+="," = ","= "," ="," \\*= "," \\*=","\\*= "};
        final String[] OPERATIONS = {"+","*"};
        
        //Go through all fields and check for 'illegal actions' using that field
        for( int i = 0; i < FIELD_LIST.length; i++ ) {
            for( int j = 0; j < LIST_OF_ILLEGAL_ACTIONS.length; j++ ) {
                for( int k = 0; k < OPERATIONS.length; k++ ) {
                    
                    //Search for variables added
                    String illegalActionToken = FIELD_LIST[i] + LIST_OF_ILLEGAL_ACTIONS[j] + FIELD_LIST[i] +
                                                " " + OPERATIONS[k];
                    
                    //Search for numbers added
                    String regexSearch = FIELD_LIST[i] + LIST_OF_ILLEGAL_ACTIONS_REGEX[j] + "\\d+";
                    Pattern pattern = Pattern.compile( regexSearch );
                    
                    Scanner scanner = null;
                    try{
                        scanner = new Scanner( pokemonFile );
                    } catch( IOException e ) {
                        e.printStackTrace();
                    }
                    
                    //run through file
                    while( scanner.hasNextLine() ) {
                        String line = scanner.nextLine();
                        Matcher matcher = pattern.matcher( line );
                        
                        //@@DEBUG
                        boolean foundIllegalToken = matcher.find();
                        if( foundIllegalToken )
                            line += "";
                        
                        //@@DEBUG
                        boolean foundIllegalToken2 = line.contains( illegalActionToken );
                        if( foundIllegalToken2 )
                            line += "";
                            
                        if( foundIllegalToken2 || foundIllegalToken ) return false; //false that this method 'passes'... the English is backwards here a bit
                    }
                    
                    scanner.close();
                }
            }
        }
        
        return true;
    }
    
    /**
     * Checks if the Pokemon actor has an overridden methods from its superclass
     * PokemonActor.java
     * 
     * @param pokemonMethods The list of methods of the Pokemon
     * @param pokemonActorMethods The list of methods of the PokemonActor.java class
     * @return boolean True if the Pokemon has no methods that are being overridden, false otherwise
     * @see inspectPokemon( PokemonActor pokemon )
     */
    private boolean checkOverrides( Method[] pokemonMethods, Method[] pokemonActorMethods ) {
        for( int i = 0; i < pokemonMethods.length; i++ ) {
            for( int j = 0; j < pokemonActorMethods.length; j++ ) {
                String pokemonMethodName = pokemonMethods[i].getName();
                String pokemonActorMethodName = pokemonActorMethods[j].getName();
                if( pokemonMethodName.equals( pokemonActorMethodName ) && !pokemonMethodName.equals("act") )
                    return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks if the Pokemon is illegally modifying any of their moves using Move.java's
     * set methods, which should only be used by the World classes
     * 
     * @param pokemonFile The Java file to be searched
     * @return boolean True if the Java file does not contain any of the set methods within the Move class, false otherwise
     * @see inspectPokemon( PokemonActor pokemon )
     */
    private boolean checkMoves( File pokemonFile ) {
        String[] illegalMethods = {".setPower",".setAccuracy",".setPP",".addPP",".addPower",".addAccuracy",".addToCurrentPP",
                                   ".setCurrentPower",".setCurrentAccuracy",".setCurrentPP"};
        
        for( int i = 0; i < illegalMethods.length; i++ ) {
            Scanner scanner = null;
            try {
                scanner = new Scanner( pokemonFile );
            } catch( FileNotFoundException e ) {
                e.printStackTrace();
            }
            
            while( scanner.hasNextLine() ) {
                if( scanner.nextLine().contains( illegalMethods[i] ) ) return false;
            }
            
            scanner.close();
        }
        
        return true;
    }
    
    /**
     * Checks if any methods that were written by Mr. Olson have been modified
     * 
     * @param pokemonFile The file to be checked
     * @return boolean True if this file has no modified methods, false otherwise
     * @see inspectPokemon( File pokemonFile )
     */
    private boolean checkModifiedMethods( File pokemonFile ) {
        File pokemonActorMrOlsonMethodsFile = new File( POKEMON_ACTOR_MR_OLSON_METHODS_FILE_LOC );
        
        Scanner scanner = null;
        try {
            scanner = new Scanner( pokemonActorMrOlsonMethodsFile );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        /* Stores permanent functions from file in a map, where the key is the header and the
           value is the body of the function
        */
        HashMap<String, String> permanentMethodHeadersAndBody = new HashMap<String, String>();
        String nextLine = scanner.nextLine();
        String header = "";
        String body = "";
        while( scanner.hasNextLine() ) {
            if( nextLine.contains("@@@") && scanner.hasNextLine() ) {
                header = scanner.nextLine();
                body = scanner.nextLine();
                do {
                    nextLine = scanner.nextLine();
                    if( !nextLine.contains("@@@") )
                        body += nextLine;
                } while( !nextLine.contains("@@@") );
                
                permanentMethodHeadersAndBody.put( header, body );
            }
        }
        
        scanner.close();
        
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner( pokemonFile );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        String line = fileScanner.nextLine();
        int methodsMatched = 0;
        do {
            //Find the first permanent header
            if( permanentMethodHeadersAndBody.containsKey( line ) ) {
                ++methodsMatched;
                String value = permanentMethodHeadersAndBody.get( line ); //get body of header from permanent file
                body = "";
                //Add lines until the next header is found
                do {
                    if( !fileScanner.hasNextLine() ) break;
                    
                    line = fileScanner.nextLine();
                    body += line;
                } while( !permanentMethodHeadersAndBody.containsKey( line ) );
                
                //The section from the line after header to the next header should contain the permanent function
                if( !body.contains( value ) ) return false;
                
            } else {
                line = fileScanner.nextLine();
            }
        } while( fileScanner.hasNextLine() );
        
        //The total number of permanent methods in the permanent file should be the same as the number of methods found in the Java file
        if( permanentMethodHeadersAndBody.size() != methodsMatched ) return false;
        
        return true;
    }
    
    /**
     * Gets a list of the methods within this class
     * 
     * @param pokemonFile The Java file to be searched
     * @return Method[] The array of methods within the given class
     * @see Class.getDeclaredMethods()
     * @see getClassFromFile( File file )
     */
    private Method[] getMethods( File pokemonFile ) {
        Class className = getClassFromFile( pokemonFile );
          
        //Get list of methods in class
        return className.getDeclaredMethods();
    }
    
    /**
     * Gets a list of the constructors within this class
     * 
     * @param pokemonFile The Java file to be searched
     * @return Constructor[] The array of methods within the given class
     * @see Class.getConstructors()
     * @see getClassFromFile( File file )
     */
    private Constructor[] getPokemonConstructors( File pokemonFile ) {
        Class className = getClassFromFile( pokemonFile );
          
        //Get list of methods in class
        return className.getConstructors();
    }
    
    /**
     * Gets a Java Class object from the given Java file
     * 
     * @param file The file to get the class name from
     * @return Class The class of the Java file
     * @see getPokemonConstructors( File pokemonFile )
     * @see getMethods( File pokemonFile )
     * @see checkClassHeader( File pokemonFile )
     */
    private Class getClassFromFile( File file ) {
        Class className = null;
      
        //Get Class object from java file
        try {
            className = Class.forName( file.getName().replace(".java", "") );
        } catch( ClassNotFoundException e ) {
            e.printStackTrace();
        }
        
        return className;
    }
    
    /**
     * @@BROKEN: Does not work since compiler cannot find other
     * classes that this Java file depends on that are within a
     * project. Only works on standalone Java files
     * 
     * Determines whether the given Java file compiles or not
     * 
     * @param javaFile The Java File to check
     * @return boolean True if the Java File compiles, false otherwise
     * @see ToolProvider.getSystemJavaCompiler()
     * @see JavaCompiler.run( ... )
     */
    private boolean doesCompile( File javaFile ) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if( compiler.run( null, null, null, javaFile.getPath() ) != COMPILE_SUCCESSFUL ) return false;
        
        return true;
    }
    
    /**
     * Gets the corresponding Java file of this Pokemon
     * 
     * @param pokemon The pokemon whose java file is to be found
     * @return File The Java file of the pokemon
     * @see getFiles()
     */
    private File getJavaFile( PokemonActor pokemon ) throws FileNotFoundException {
        File[] listOfFiles = getFiles();
        String name = pokemon.getName();
        
        for( File file: listOfFiles ) {
            String valueTest = file.getName();
            if( file.getName().contains( name ) ) return file;
        }
        
        if( name.length() > 0 )
            throw new FileNotFoundException("File not found. Expected file name to have " + name + " in the title");
        
        return null;
    }
    
    /**
        Returns the list of java files in the current directory
      
        @return File[] The list of java files in the current directory
    */
    private static File[] getFiles() {
        File dir = new File(".");
        File[] filesList = dir.listFiles();
        ArrayList<File> newFileList = new ArrayList<File>();
      
        for( File file : filesList ) {
            if( file.isFile() ) {
                if( file.toString().contains("java")               &&
                    !file.toString().contains("PokeWorld.java")    &&
                    !file.toString().contains("PokeArena.java")    &&
                    !file.toString().contains("PokeBattle.java")   &&
                    !file.toString().contains("BattleImage.java")  &&
                    !file.toString().contains("OfficerJenny.java") &&
                    !file.toString().contains("Pokedex.java")      &&
                    !file.toString().contains("Pokemon.java")      &&
                    !file.toString().contains("Item.java")         &&
                    !file.toString().contains("TextImage.java")    &&
                    !file.toString().contains("Move.java") ) {
                    newFileList.add( file );
                }
            }
        }
      
        File[] newFileListArray = new File[ newFileList.size() ];
        //convert to array since toArray() does not preserve order
        for( int i = 0; i < newFileList.size(); i++ ) {
            newFileListArray[i] = newFileList.get(i);
        }
      
        return newFileListArray;
    }
    
    /**
        Exception class related to errors when trying to use a Pokemon object that was
        not recorded as being active within the world
    */
    public class ActivePokemonStateException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public ActivePokemonStateException( String errorMessage ) {
            super( errorMessage );
        }
      
    }
}
