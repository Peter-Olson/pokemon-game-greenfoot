import java.io.File;
import java.io.FileNotFoundException;
//import java.util.Scanner;
import java.util.stream.*;
//import java.util.EnumSet;
//import java.util.ArrayList;
import java.util.*;

/**
 * Move.java
 * 
 * Has the abilities, qualities, data, and descriptions for a Pokemon move
 * 
 * --------------------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIELDS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC FIELDS
 * --------------------------------------------------------------------
 * - none -
 * 
 * PRIVATE FIELDS
 * --------------------------------------------------------------------
 * - NAME        : The name of this Pokemon move
 * - TYPE        : The attack type for this move. See MOVE_TYPE_LIST for examples
 * - CATEGORY    : The category of this move. See MOVE_CATEGORY_LIST for examples
 * - POWER       : The power of this move
 * - ACCURACY    : The accuracy of this move
 * - PP          : The Power Points of this move. This is the number of times that the move can
 *                 be used within a battle series
 * - DESCRIPTION : The description of this move
 * 
 * - currentPower    : The current power of this move
 * - currentAccuracy : The current accuracy of this move
 * - currentPP       : The current Power Points of this move (the number of times this can be
 *                     used before the power is exhausted)
 * 
 * - MOVE_TYPE_LIST     : The list of move attack types
 * - MOVE_CATEGORY_LIST : The list of move category types
 * 
 * - moveType           : The type for this move (as an enum)
 * - moveCategory       : The category for this move (as an enum)
 * 
 * - POKEMON_MOVES_FILE    : The file path for the list of standards Moves that a Pokemon can learn
 * - TOTAL_FIELDS_FOR_MOVE : The total number of fields per line in the POKEMON_MOVES_FILE
 * 
 * - MIN_PP         : The minimum amount of PP a move can have
 * - MAX_PP         : The maximum amount of PP a move can have
 * - MIN_ACC        : The minimum amount of accuracy a move can have
 * - MAX_ACC        : The maximum amount of accuracy a move can have
 * - MIN_POWER      : THe minimum amount of power a move can have
 * 
 * - MAX_POINTS         : The maximum amount of points that a move can use
 * - MAX_POINTS_STATUS  : The maximum amount of points that a STATUS-category Move can have
 * 
 * - POWER_MULT         : The multiplier for spending points on power for a Move
 * - ACC_MULT           : The multiplier for spending points on accuracy for a Move
 * - PP_MULT            : The multiplier for spending points on PP for a Move
 * - SPECIAL_ACC_LIMIT  : A SPECIAL-category Move can have a maximum accuracy of 100 - this value
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ENUMS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * MoveType         : NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND,
 *                    FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK
 *
 * MoveCategory:    : PHYSICAL, STATUS, SPECIAL
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CONSTRUCTORS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * Move( String NAME, String TYPE, String MOVE_TYPE, int POWER, int ACCURACY, int PP,
 *       String DESCRIPTION )
 *      : Create a move from the given values
 * 
 * Move( String NAME, String TYPE, String CATEGORY, int POWER, int ACCURACY, int PP,
 *       String DESCRIPTION, boolean fromFile )
 *      : Create a move with the option of creating a Move from a file
 *      
 * Move( String NAME, String TYPE, String CATEGORY, int POWER, int ACCURACY, int PP,
 *       String DESCRIPTION, boolean fromFile, boolean[] propertyList, double[] metaData, String location )
 *      : Create a move with the option of creating a Move from a file. This move has special properties/effects
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ METHODS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC METHODS
 * ---------------------------------------------------------------------
 * - getName()                          : Gets the name of this move
 * - getType()                          : Gets this move's attack type (as an enum)
 * - getCategory()                      : Gets the category of this move's category (as an enum)
 * - getPower()                         : Gets the power of this move
 * - getAccuracy()                      : Gets the accuracy of this move
 * - getPP()                            : Gets the PP of this move
 * - getDescription()                   : Gets this move's description
 * 
 * - setPower( int POWER )              : Sets the power of this move
 * - setAccuracy( int ACCURACY )        : Sets the accuracy of this move
 * - setPP( int PP )                    : Sets the PP of this move
 * 
 * - addPP( int PP )                    : Adds PP to the total PP of this move
 * 
 * - addPower( int power )              : Adds power to the current power of this move
 * - addAccuracy( int accuracy )        : Adds accuracy to the current accuracy of this move
 * - addToCurrentPP( int PP )           : Adds PP to the current PP of this move
 * 
 * - getCurrentPower()                  : Gets the current power of this move
 * - getCurrentAccuracy()               : Gets the current accuracy of this move
 * - getCurrentPP()                     : Gets the current PP of this move
 * 
 * - setCurrentPower( int power )       : Sets the current power of this move
 * - setCurrentAccuracy( int accuracy ) : Sets the current accuracy of this move
 * - setCurrentPP( int PP )             : Sets the current PP of this move
 * 
 * - moveExists( String name )          : Determines whether this move exists as a standard
 *                                        Move that is stored within the POKEMON_MOVES_FILE
 * 
 * - getMoveFileName()                  : Gets the name of the POKEMON_MOVES_FILE
 * - addMoveToFile( String name, String type, String category, int power, int accuracy, int pp, String description )
 *      : Adds the Move information to the POKEMON_MOVES_FILE. This Move has no special properties/effects
 * - addMoveToFile( String name, String type, String category, int power, int accuracy, int pp, String description,
                    boolean[] propertyList, double[] metaData, String location )
        : Adds the Move information to the POKEMON_MOVES_FILE. This Move has special properties/effects
 *      
 * - getMinimumPP()            : Gets the minimum PP requirement
 * - getMaximumPP()            : Gets the maximum PP limit
 * - getMinimumAccuracy()      : Gets the minimum accuracy requirement
 * - getMaximumAccuracy()      : Gets the maximum accuracy limit
 * - getMinimumPower()         : Gets the minimum power requirement
 * - getMaximumPoints()        : Gets the maximum points limit
 * - getMaximumPointsStatus()  : Gets the maxmimum points limit for Status category moves
 * - getPowerMultiplier()      : Gets the power multiplier for spending points on power for custom moves
 * - getAccuracyMultiplier()   : Gets the accuracy multiplier for spending points on accuracy for custom moves
 * - getPPMultiplier()         : Gets the PP multiplier for spending points on PP for custom moves
 * - getSpecialAccuracyLimit() : Gets the special accuracy limit, which limits the accuracy for special category custom moves
 * 
 * PRIVATE METHODS
 * ---------------------------------------------------------------------
 * - checkType()          : Checks to make sure this move has a valid type
 * - checkCategory()      : Checks to make sure this move has a valid category
 * - checkPoints( boolean fromFile ) : Checks if the points used to make this Move
 *                                     is valid
 * 
 * - setEnums()           : Sets the enum fields from the String fields related to the
 *                          TYPE and CATEGORY variables
 * 
 * - SOPln( String str )  : Prints a message to the console
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PRIVATE CLASSES @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * InvalidTypeException   : Used for errors related to types
 * 
 * InvalidPointsException : Used for errors related to points
 * 
 * InvalidMovesFileException : Used for errors related to the POKEMON_MOVES_FILE
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * --------------------------------------------------------------------------------------------
 * 
 * @author Peter Olson
 * @version 4/8/22
 */
public class Move {

    private final String NAME, TYPE, CATEGORY;
    private int POWER, ACCURACY, PP;
    private final String DESCRIPTION;
    
    private int currentPower, currentAccuracy, currentPP;
    
    private final String[] MOVE_TYPE_LIST = {"NORMAL","FIRE","WATER","ELECTRIC","GRASS","ICE",
                                             "FIGHTING","POISON","GROUND","FLYING","PSYCHIC",
                                             "BUG","ROCK","GHOST","DRAGON","DARK"};
    
    public static enum MoveType {
        NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND,
        FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK;
    }
    private MoveType moveType;
                                             
    private final String[] MOVE_CATEGORY_LIST = {"PHYSICAL","STATUS","SPECIAL"};
    
    public static enum MoveCategory {
        PHYSICAL, STATUS, SPECIAL;
    }
    private MoveCategory moveCategory;
    
    private final String POKEMON_MOVES_FILE = PokeWorld.POKEMON_MOVES_FILE;
    //vv The total number of attributes per line in the POKEMON_MOVES_FILE vv
    public final int TOTAL_FIELDS_FOR_MOVE = PokeWorld.TOTAL_FIELDS_FOR_MOVE;
    
    /* @@@@@@@@@@@@@@@@@@@@@@@@@ MOVE BOOLEANS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
    //Booleans affecting application [2 total]
    public final boolean AFFECTS_THIS/*0:AT*/, PERCENT_CHANCE/*1:PC*/;
    //Booleans required to use the Move [7 total]
    public final boolean IF_HIT_BY_ATTACK/*2:IHBA*/, IF_HIT_BY_PHYSICAL_ATTACK/*3:IHBPA*/, IF_HIT_BY_SPECIAL_ATTACK/*4:IHBSA*/,
                         IF_HIT_BY_STATUS_ATTACK/*5:IHBSA*/,
                         REQUIRES_OPPONENT_TO_BE_SLEEPING/*6:ROTBS*/, REQUIRES_OPPONENT_TO_BE_UNDERGROUND/*7:ROTBU*/,
                         REQUIRES_OPPONENT_TO_BE_FLYING/*68:ROTBF*/;
    //Properties related to damage [12 total]
    public final int DAMAGE_X_TURNS/*8:DXT*/, HIT_X_TIMES/*9:HXT*/, HIT_X_TIMES_IN_ONE_TURN/*10:HXTIOT*/, DO_X_DAMAGE/*11:DXD*/;
    public final double CAUSES_RECOIL_DAMAGE/*12:CRD*/, RECOVER_HP_BASED_ON_DAMAGE/*13:RHBOD*/, REDUCES_DAMAGE/*14:RD*/,
                        INFLICTS_DAMAGE_EQUAL_TO_PERCENT_OF_LEVEL/*15:IDETPOL*/, DAMAGES_USER/*16:DU*/,
                        HIT_PERCENT_MAX_HP/*17:HPMH*/, RECOVER_PERCENT_MAX_HP/*71:RPMH*/;
    public final boolean INFLICTS_DAMAGE_EQUAL_TO_LEVEL/*18:IDETL*/;
    //Properties related to status effects [13 total]
    public final boolean CAUSES_FLINCHING/*19:CF*/, CAUSES_BURN/*20:CB*/, CAUSES_FREEEZE/*21:CF*/, CAUSES_PARALYSIS/*22:CP*/,
                         CAUSES_POISON/*23:CP*/, CAUSES_SLEEP/*24:CS*/, CAUSES_BOUND/*25:CB*/, CAUSES_CONFUSION/*26:CC*/,
                         CAUSES_FAINTING/*27:CF*/, CAUSES_TRAP/*28:CT*/, PHASE_OUT_ATTACK_SECOND/*29:POAS*/;
    public final String PHASE_OUT_LOCATION/*30:POL*/;
    public final int SLEEPS_FOR_X_TURNS_THEN_FULLY_HEALS/*31:SFXTTFH*/;
    //Properties related to stats [25 total]
    public final double MULTIPLY_POWER/*32:MP*/, RAISE_HP/*33:RH*/, LOWER_HP/*34:LH*/, RAISE_ATTACK/*35:RA*/,
                        LOWER_ATTACK/*36:LA*/, RAISE_DEFENSE/*37:RD*/, LOWER_DEFENSE/*38:LD*/,
                        RAISE_SPECIAL_ATTACK/*39:RSA*/, LOWER_SPECIAL_ATTACK/*40:LSA*/, RAISE_SPECIAL_DEFENSE/*41:RSD*/,
                        LOWER_SPECIAL_DEFENSE/*42:LSD*/, RAISE_EVASIVENESS/*43:RE*/, LOWER_EVASIVENESS/*44:LE*/,
                        RAISE_ACCURACY/*45:RA*/, LOWER_ACCURACY/*46:LA*/, RAISE_SPEED/*47:RS*/, LOWER_SPEED/*48:LS*/,
                        RAISE_CRITICAL_HIT_RATIO/*49:RCHR*/, LOWER_CRITICAL_HIT_RATIO/*50:LCHR*/,
                        TEMP_RAISE_CRITICAL_HIT_RATIO/*51:TRCHR*/, RAISE_POWER_DEPENDS_ON_WEIGHT/*52:RPDOW*/, DRAIN_HP/*53:DH*/;
    public final boolean RESETS_STATS_CHANGES/*54:RSC*/, STATS_CANNOT_BE_CHANGED/*55:SCBC*/,
                         RAISE_DEFENSE_THEN_ATTACK/*56:RDTA*/;
    //Properties related to post-attack [9 total]
    public final boolean CHANGE_TYPE_TO_TYPE_OF_FIRST_MOVE/*57:CTTTOFM*/, OPPONENT_CANT_USE_LAST_ATTACK/*58:OCULA*/,
                         IF_MISSES/*59:IM*/, IF_SPECIAL_ATTACK/*60:ISA*/, GETS_FORM_AND_ATTACKS/*61:GFAA*/,
                         STATUS_CHANGES_AFTER/*70:SCA*/, IF_PHYSICAL_ATTACK/*72:IPA*/;
    public final double GAIN_MONEY_AFTER_BATTLE/*62:GMAB*/;
    public final int CREATE_DECOY/*63:CD*/;
    //Properties related to multiple turns [3 total]
    /*[Note that several of the properties above also fall into this category, including PHASE_OUT_ATTACK_SECOND,
     * SLEEPS_FOR_X_TURNS_THEN_FULLY_HEALS, RAISE_DEFENSE_THEN_ATTACK, DAMAGE_X_TURNS, HIT_X_TIMES, and
     * HIT_X_TIMES_IN_ONE_TURN] */
    public final int FOR_X_TURNS/*64:FXT*/, LOSE_NEXT_X_TURNS/*69:LNXT*/;
    public final boolean CHARGE_FIRST_ATTACK_SECOND/*65:CFAS*/;
    //Properties related to using different Moves [2 total]
    public final boolean PERFORM_RANDOM_MOVE/*66:PRM*/, COPY_OPPONENT_LAST_MOVE/*67:COLM*/;
    /* --------------------------END MOVE BOOLEANS------------------------------------------- */
    /* ------------ RELATED MOVE PROPERTY DATA ------------- */
    public double metaData = 0.0;
    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
    
    private static final int MIN_PP       = 5;
    private static final int MAX_PP       = 40;
    
    private static final int MIN_ACC      = 5;
    private static final int MAX_ACC      = 100;
    
    private static final int MIN_POWER    = 5;
    
    private static final int MAX_POINTS          = 250;
    private static final int MAX_POINTS_STATUS   = 175;
    private static final double POWER_MULT       = 1.5;
    private static final double ACC_MULT         = 0.75;
    private static final double PP_MULT          = 5.0;
    private static final int SPECIAL_ACC_LIMIT   = 25;
    
    /**
     *  Create a move
     *  
     *  @param NAME The name of the move
     *  @param TYPE The move's type. See MOVE_TYPE_LIST for examples
     *  @param CATEGORY The move's category
     *  @param POWER The move's power
     *  @param ACCURACY The move's accuracy
     *  @param PP The move's total PP
     *  @param DESCRIPTION This move's description
     *  @see setEnums()
     *  @see checkType()
     *  @see checkCategory()
     *  @see checkPoints( boolean fromFile )
     *  @see moveExists( String name )
     *  @see addMoveToFile( String name, String type, String category, int power, int accuracy, int pp, String description )
     */
    public Move( String NAME, String TYPE, String CATEGORY,
                 int POWER, int ACCURACY, int PP,
                 String DESCRIPTION ) {
        this.NAME                          = NAME;
        this.TYPE                          = TYPE;
        this.CATEGORY                      = CATEGORY;
        this.POWER       = currentPower    = POWER;
        this.ACCURACY    = currentAccuracy = ACCURACY;
        this.PP          = currentPP       = PP;
        this.DESCRIPTION                   = DESCRIPTION;
        
        //Set the MoveType and MoveCategory enum values
        setEnums();
        
        //Set Move Effect Characteristics
        /* @@@@@@@@@@@@@@@@@@@@@@@@@ MOVE BOOLEANS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
        //Booleans affecting application [2 total]
        AFFECTS_THIS = PERCENT_CHANCE = false;
        //Booleans required to use the Move [7 total]
        IF_HIT_BY_ATTACK = IF_HIT_BY_PHYSICAL_ATTACK = IF_HIT_BY_SPECIAL_ATTACK = IF_HIT_BY_STATUS_ATTACK =
            REQUIRES_OPPONENT_TO_BE_SLEEPING = REQUIRES_OPPONENT_TO_BE_UNDERGROUND =
            /*num 68*/REQUIRES_OPPONENT_TO_BE_FLYING = false;
        //Properties related to damage [12 total]
        DAMAGE_X_TURNS = HIT_X_TIMES = HIT_X_TIMES_IN_ONE_TURN = DO_X_DAMAGE = 0;
        CAUSES_RECOIL_DAMAGE = RECOVER_HP_BASED_ON_DAMAGE = REDUCES_DAMAGE =
            INFLICTS_DAMAGE_EQUAL_TO_PERCENT_OF_LEVEL = DAMAGES_USER = HIT_PERCENT_MAX_HP =
            /* 71 */ RECOVER_PERCENT_MAX_HP = 0.0;
        INFLICTS_DAMAGE_EQUAL_TO_LEVEL = false;
        //Properties related to status effects [13 total]
        CAUSES_FLINCHING = CAUSES_BURN = CAUSES_FREEEZE = CAUSES_PARALYSIS = CAUSES_POISON = CAUSES_SLEEP = 
            CAUSES_BOUND = CAUSES_CONFUSION = CAUSES_FAINTING = CAUSES_TRAP = PHASE_OUT_ATTACK_SECOND = false;
        PHASE_OUT_LOCATION = "";
        SLEEPS_FOR_X_TURNS_THEN_FULLY_HEALS = 0;
        //Properties related to stats [25 total]
        MULTIPLY_POWER = RAISE_HP = LOWER_HP = RAISE_ATTACK = LOWER_ATTACK = RAISE_DEFENSE = LOWER_DEFENSE = 
            RAISE_SPECIAL_ATTACK = LOWER_SPECIAL_ATTACK = RAISE_SPECIAL_DEFENSE = LOWER_SPECIAL_DEFENSE =
            RAISE_EVASIVENESS = LOWER_EVASIVENESS = RAISE_ACCURACY = LOWER_ACCURACY = RAISE_SPEED = LOWER_SPEED =
            RAISE_CRITICAL_HIT_RATIO = LOWER_CRITICAL_HIT_RATIO = TEMP_RAISE_CRITICAL_HIT_RATIO =
            RAISE_POWER_DEPENDS_ON_WEIGHT = DRAIN_HP = 0.0;
        RESETS_STATS_CHANGES = STATS_CANNOT_BE_CHANGED = RAISE_DEFENSE_THEN_ATTACK = false;
        //Properties related to post-attack [9 total]
        CHANGE_TYPE_TO_TYPE_OF_FIRST_MOVE = OPPONENT_CANT_USE_LAST_ATTACK = IF_MISSES = IF_SPECIAL_ATTACK =
            GETS_FORM_AND_ATTACKS = /*num 70*/ STATUS_CHANGES_AFTER = /*72*/ IF_PHYSICAL_ATTACK = false;
        GAIN_MONEY_AFTER_BATTLE = 0.0;
        CREATE_DECOY = 0;
        //Properties related to multiple turns [3 total]
        /*[Note that several of the properties above also fall into this category, including PHASE_OUT_ATTACK_SECOND,
         * SLEEPS_FOR_X_TURNS_THEN_FULLY_HEALS, RAISE_DEFENSE_THEN_ATTACK, DAMAGE_X_TURNS, HIT_X_TIMES, and
         * HIT_X_TIMES_IN_ONE_TURN] */
        FOR_X_TURNS = /* num 69 */ LOSE_NEXT_X_TURNS = 0;
        CHARGE_FIRST_ATTACK_SECOND = false;
        //Properties related to using different Moves [2 total]
        PERFORM_RANDOM_MOVE = COPY_OPPONENT_LAST_MOVE = false;
        /* --------------------------END MOVE BOOLEANS------------------------------------------- */
        
        //Adjust accuracy limit if this Move is of the SPECIAL type
        if( this.ACCURACY >= MIN_ACC + SPECIAL_ACC_LIMIT && this.TYPE.equals("SPECIAL") )
            this.ACCURACY -= SPECIAL_ACC_LIMIT;
        
        //Check to make sure this is a valid Move
        try {
            checkType();
            checkCategory();
            checkPoints( false );
        } catch( InvalidTypeException e ) {
            e.printStackTrace();
        } catch( InvalidPointsException e ) {
            e.printStackTrace();
        }
        
        //Add the Move information to the Move file if it is a new Move
        try {
            if( !moveExists( NAME ) ) {
                addMoveToFile( NAME, TYPE, CATEGORY, POWER, ACCURACY, PP, DESCRIPTION );
            }
        } catch( InvalidMovesFileException e ) {
            e.printStackTrace();
        }
    }
    
    /**
     *  Create a move
     *  
     *  @param NAME The name of the move
     *  @param TYPE The move's type. See MOVE_TYPE_LIST for examples
     *  @param CATEGORY The move's category
     *  @param POWER The move's power
     *  @param ACCURACY The move's accuracy
     *  @param PP The move's total PP
     *  @param DESCRIPTION This move's description
     *  @param fromFile True if this Move is from the POKEMON_MOVES_FILE file, false otherwise
     *  @see setEnums()
     *  @see checkType()
     *  @see checkCategory()
     *  @see checkPoints( boolean fromFile )
     *  @see moveExists( String name )
     *  @see addMoveToFile( String name, String type, String category, int power, int accuracy, int pp, String description )
     */
    public Move( String NAME, String TYPE, String CATEGORY,
                 int POWER, int ACCURACY, int PP,
                 String DESCRIPTION, boolean fromFile ) {
        this.NAME                          = NAME;
        this.TYPE                          = TYPE;
        this.CATEGORY                      = CATEGORY;
        this.POWER       = currentPower    = POWER;
        this.ACCURACY    = currentAccuracy = ACCURACY;
        this.PP          = currentPP       = PP;
        this.DESCRIPTION                   = DESCRIPTION;
        
        //Set the MoveType and MoveCategory enum values
        setEnums();
        
        //Set Move Effect Characteristics
        /* @@@@@@@@@@@@@@@@@@@@@@@@@ MOVE BOOLEANS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
        //Booleans affecting application [2 total]
        AFFECTS_THIS = PERCENT_CHANCE = false;
        //Booleans required to use the Move [7 total]
        IF_HIT_BY_ATTACK = IF_HIT_BY_PHYSICAL_ATTACK = IF_HIT_BY_SPECIAL_ATTACK = IF_HIT_BY_STATUS_ATTACK =
            REQUIRES_OPPONENT_TO_BE_SLEEPING = REQUIRES_OPPONENT_TO_BE_UNDERGROUND =
            /*num 68*/REQUIRES_OPPONENT_TO_BE_FLYING = false;
        //Properties related to damage [12 total]
        DAMAGE_X_TURNS = HIT_X_TIMES = HIT_X_TIMES_IN_ONE_TURN = DO_X_DAMAGE = 0;
        CAUSES_RECOIL_DAMAGE = RECOVER_HP_BASED_ON_DAMAGE = REDUCES_DAMAGE =
            INFLICTS_DAMAGE_EQUAL_TO_PERCENT_OF_LEVEL = DAMAGES_USER = HIT_PERCENT_MAX_HP =
            /* 71 */ RECOVER_PERCENT_MAX_HP = 0.0;
        INFLICTS_DAMAGE_EQUAL_TO_LEVEL = false;
        //Properties related to status effects [13 total]
        CAUSES_FLINCHING = CAUSES_BURN = CAUSES_FREEEZE = CAUSES_PARALYSIS = CAUSES_POISON = CAUSES_SLEEP = 
            CAUSES_BOUND = CAUSES_CONFUSION = CAUSES_FAINTING = CAUSES_TRAP = PHASE_OUT_ATTACK_SECOND = false;
        PHASE_OUT_LOCATION = "";
        SLEEPS_FOR_X_TURNS_THEN_FULLY_HEALS = 0;
        //Properties related to stats [25 total]
        MULTIPLY_POWER = RAISE_HP = LOWER_HP = RAISE_ATTACK = LOWER_ATTACK = RAISE_DEFENSE = LOWER_DEFENSE = 
            RAISE_SPECIAL_ATTACK = LOWER_SPECIAL_ATTACK = RAISE_SPECIAL_DEFENSE = LOWER_SPECIAL_DEFENSE =
            RAISE_EVASIVENESS = LOWER_EVASIVENESS = RAISE_ACCURACY = LOWER_ACCURACY = RAISE_SPEED = LOWER_SPEED =
            RAISE_CRITICAL_HIT_RATIO = LOWER_CRITICAL_HIT_RATIO = TEMP_RAISE_CRITICAL_HIT_RATIO =
            RAISE_POWER_DEPENDS_ON_WEIGHT = DRAIN_HP = 0.0;
        RESETS_STATS_CHANGES = STATS_CANNOT_BE_CHANGED = RAISE_DEFENSE_THEN_ATTACK = false;
        //Properties related to post-attack [9 total]
        CHANGE_TYPE_TO_TYPE_OF_FIRST_MOVE = OPPONENT_CANT_USE_LAST_ATTACK = IF_MISSES = IF_SPECIAL_ATTACK =
            GETS_FORM_AND_ATTACKS = /*num 70*/ STATUS_CHANGES_AFTER = /*72*/ IF_PHYSICAL_ATTACK = false;
        GAIN_MONEY_AFTER_BATTLE = 0.0;
        CREATE_DECOY = 0;
        //Properties related to multiple turns [3 total]
        /*[Note that several of the properties above also fall into this category, including PHASE_OUT_ATTACK_SECOND,
         * SLEEPS_FOR_X_TURNS_THEN_FULLY_HEALS, RAISE_DEFENSE_THEN_ATTACK, DAMAGE_X_TURNS, HIT_X_TIMES, and
         * HIT_X_TIMES_IN_ONE_TURN] */
        FOR_X_TURNS = /* num 69 */ LOSE_NEXT_X_TURNS = 0;
        CHARGE_FIRST_ATTACK_SECOND = false;
        //Properties related to using different Moves [2 total]
        PERFORM_RANDOM_MOVE = COPY_OPPONENT_LAST_MOVE = false;
        /* --------------------------END MOVE BOOLEANS------------------------------------------- */
        
        //Adjust accuracy limit if this Move is of the SPECIAL type
        if( this.ACCURACY >= MIN_ACC + SPECIAL_ACC_LIMIT && this.TYPE.equals("SPECIAL") )
            this.ACCURACY -= SPECIAL_ACC_LIMIT;
        
        //Check to make sure this is a valid Move
        try {
            checkType();
            checkCategory();
            if( !fromFile )
                checkPoints( fromFile );
        } catch( InvalidTypeException e ) {
            e.printStackTrace();
        } catch( InvalidPointsException e ) {
            e.printStackTrace();
        }
        
        //Add the Move information to the Move file if it is a new Move
        try {
            if( !moveExists( NAME ) ) {
                addMoveToFile( NAME, TYPE, CATEGORY, POWER, ACCURACY, PP, DESCRIPTION );
            }
        } catch( InvalidMovesFileException e ) {
            e.printStackTrace();
        }
    }
    
    /**
     *  Create a move
     *  
     *  @param NAME The name of the move
     *  @param TYPE The move's type. See MOVE_TYPE_LIST for examples
     *  @param CATEGORY The move's category
     *  @param POWER The move's power
     *  @param ACCURACY The move's accuracy
     *  @param PP The move's total PP
     *  @param DESCRIPTION This move's description
     *  @param fromFile True if this Move is from the POKEMON_MOVES_FILE file, false otherwise
     *  @param propertyList The list of active properties
     *  @param metaData The metadata attached to each property
     *  @param location The location of where this Move takes place. By default, location is an empty String
     *  @see setEnums()
     *  @see checkType()
     *  @see checkCategory()
     *  @see checkPoints( boolean fromFile )
     *  @see moveExists( String name )
     *  @see addMoveToFile( String name, String type, String category, int power, int accuracy, int pp, String description )
     */
    public Move( String NAME, String TYPE, String CATEGORY,
                 int POWER, int ACCURACY, int PP,
                 String DESCRIPTION, boolean fromFile, boolean[] propertyList, double[] metaData, String location ) {
        this.NAME                          = NAME;
        this.TYPE                          = TYPE;
        this.CATEGORY                      = CATEGORY;
        this.POWER       = currentPower    = POWER;
        this.ACCURACY    = currentAccuracy = ACCURACY;
        this.PP          = currentPP       = PP;
        this.DESCRIPTION                   = DESCRIPTION;
        
        //Set the MoveType and MoveCategory enum values
        setEnums();

        //Set Move Effect Characteristics
        /* @@@@@@@@@@@@@@@@@@@@@@@@@ MOVE BOOLEANS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
        //Booleans affecting application [2 total]
        AFFECTS_THIS                        = propertyList[0];
        PERCENT_CHANCE                      = propertyList[1];
        //Booleans required to use the Move [7 total]
        IF_HIT_BY_ATTACK                    = propertyList[2];
        IF_HIT_BY_PHYSICAL_ATTACK           = propertyList[3];
        IF_HIT_BY_SPECIAL_ATTACK            = propertyList[4];
        IF_HIT_BY_STATUS_ATTACK             = propertyList[5];
        REQUIRES_OPPONENT_TO_BE_SLEEPING    = propertyList[6];
        REQUIRES_OPPONENT_TO_BE_UNDERGROUND = propertyList[7];
        REQUIRES_OPPONENT_TO_BE_FLYING      = propertyList[68];
        //Properties related to damage [12 total]
        DAMAGE_X_TURNS                            = propertyList[8]  ? (int)metaData[8]  : 0;
        HIT_X_TIMES                               = propertyList[9]  ? (int)metaData[9]  : 0;
        HIT_X_TIMES_IN_ONE_TURN                   = propertyList[10] ? (int)metaData[10] : 0;
        DO_X_DAMAGE                               = propertyList[11] ? (int)metaData[11] : 0;
        CAUSES_RECOIL_DAMAGE                      = propertyList[12] ? metaData[12] : 0.0;
        RECOVER_HP_BASED_ON_DAMAGE                = propertyList[13] ? metaData[13] : 0.0;
        REDUCES_DAMAGE                            = propertyList[14] ? metaData[14] : 0.0;
        INFLICTS_DAMAGE_EQUAL_TO_PERCENT_OF_LEVEL = propertyList[15] ? metaData[15] : 0.0;
        DAMAGES_USER                              = propertyList[16] ? metaData[16] : 0.0;
        HIT_PERCENT_MAX_HP                        = propertyList[17] ? metaData[17] : 0.0;
        RECOVER_PERCENT_MAX_HP                    = propertyList[71] ? metaData[71] : 0.0;
        INFLICTS_DAMAGE_EQUAL_TO_LEVEL            = propertyList[18];
        //Properties related to status effects [13 total]
        CAUSES_FLINCHING                          = propertyList[19];
        CAUSES_BURN                               = propertyList[20];
        CAUSES_FREEEZE                            = propertyList[21];
        CAUSES_PARALYSIS                          = propertyList[22];
        CAUSES_POISON                             = propertyList[23];
        CAUSES_SLEEP                              = propertyList[24];
        CAUSES_BOUND                              = propertyList[25];
        CAUSES_CONFUSION                          = propertyList[26];
        CAUSES_FAINTING                           = propertyList[27];
        CAUSES_TRAP                               = propertyList[28];
        PHASE_OUT_ATTACK_SECOND                   = propertyList[29];
        PHASE_OUT_LOCATION                        = propertyList[30] ? location : "";
        SLEEPS_FOR_X_TURNS_THEN_FULLY_HEALS       = propertyList[31] ? (int)metaData[31] : 0;
        //Properties related to stats [25 total]
        MULTIPLY_POWER                  = propertyList[32] ? metaData[32] : 0.0;
        RAISE_HP                        = propertyList[33] ? metaData[33] : 0.0;
        LOWER_HP                        = propertyList[34] ? metaData[34] : 0.0;
        RAISE_ATTACK                    = propertyList[35] ? metaData[35] : 0.0;
        LOWER_ATTACK                    = propertyList[36] ? metaData[36] : 0.0;
        RAISE_DEFENSE                   = propertyList[37] ? metaData[37] : 0.0;
        LOWER_DEFENSE                   = propertyList[38] ? metaData[38] : 0.0;
        RAISE_SPECIAL_ATTACK            = propertyList[39] ? metaData[39] : 0.0;
        LOWER_SPECIAL_ATTACK            = propertyList[40] ? metaData[40] : 0.0;
        RAISE_SPECIAL_DEFENSE           = propertyList[41] ? metaData[41] : 0.0;
        LOWER_SPECIAL_DEFENSE           = propertyList[42] ? metaData[42] : 0.0;
        RAISE_EVASIVENESS               = propertyList[43] ? metaData[43] : 0.0;
        LOWER_EVASIVENESS               = propertyList[44] ? metaData[44] : 0.0;
        RAISE_ACCURACY                  = propertyList[45] ? metaData[45] : 0.0;
        LOWER_ACCURACY                  = propertyList[46] ? metaData[46] : 0.0;
        RAISE_SPEED                     = propertyList[47] ? metaData[47] : 0.0;
        LOWER_SPEED                     = propertyList[48] ? metaData[48] : 0.0;
        RAISE_CRITICAL_HIT_RATIO        = propertyList[49] ? metaData[49] : 0.0;
        LOWER_CRITICAL_HIT_RATIO        = propertyList[50] ? metaData[50] : 0.0;
        TEMP_RAISE_CRITICAL_HIT_RATIO   = propertyList[51] ? metaData[51] : 0.0;
        RAISE_POWER_DEPENDS_ON_WEIGHT   = propertyList[52] ? metaData[52] : 0.0;
        DRAIN_HP                        = propertyList[53] ? metaData[53] : 0.0;
        RESETS_STATS_CHANGES            = propertyList[54];
        STATS_CANNOT_BE_CHANGED         = propertyList[55];
        RAISE_DEFENSE_THEN_ATTACK       = propertyList[56];
        //Properties related to post-attack [9 total]
        CHANGE_TYPE_TO_TYPE_OF_FIRST_MOVE = propertyList[57];
        OPPONENT_CANT_USE_LAST_ATTACK     = propertyList[58];
        IF_MISSES                         = propertyList[59];
        IF_SPECIAL_ATTACK                 = propertyList[60];
        GETS_FORM_AND_ATTACKS             = propertyList[61];
        STATUS_CHANGES_AFTER              = propertyList[70];
        IF_PHYSICAL_ATTACK                = propertyList[72];
        GAIN_MONEY_AFTER_BATTLE           = propertyList[62] ? metaData[62] : 0.0;
        CREATE_DECOY                      = propertyList[63] ? (int)metaData[63] : 0;
        //Properties related to multiple turns [3 total]
        /*[Note that several of the properties above also fall into this category, including PHASE_OUT_ATTACK_SECOND,
         * SLEEPS_FOR_X_TURNS_THEN_FULLY_HEALS, RAISE_DEFENSE_THEN_ATTACK, DAMAGE_X_TURNS, HIT_X_TIMES, and
         * HIT_X_TIMES_IN_ONE_TURN] */
        FOR_X_TURNS                = propertyList[64] ? (int)metaData[64] : 0;
        LOSE_NEXT_X_TURNS          = propertyList[69] ? (int)metaData[69] : 0;
        CHARGE_FIRST_ATTACK_SECOND = propertyList[65];
        //Properties related to using different Moves [2 total]
        PERFORM_RANDOM_MOVE        = propertyList[66];
        COPY_OPPONENT_LAST_MOVE    = propertyList[67];
        /* --------------------------END MOVE BOOLEANS------------------------------------------- */
        
        //Adjust accuracy limit if this Move is of the SPECIAL type
        if( this.ACCURACY >= MIN_ACC + SPECIAL_ACC_LIMIT && this.TYPE.equals("SPECIAL") )
            this.ACCURACY -= SPECIAL_ACC_LIMIT;
        
        //Check to make sure this is a valid Move
        try {
            checkType();
            checkCategory();
            if( !fromFile )
                checkPoints( fromFile );
        } catch( InvalidTypeException e ) {
            e.printStackTrace();
        } catch( InvalidPointsException e ) {
            e.printStackTrace();
        }
        
        //Add the Move information to the Move file if it is a new Move
        try {
            if( !moveExists( NAME ) ) {
                addMoveToFile( NAME, TYPE, CATEGORY, POWER, ACCURACY, PP, DESCRIPTION, propertyList, metaData, location );
            }
        } catch( InvalidMovesFileException e ) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the file name of the text file containing all the Moves within the game
     * 
     * @return String The file name of the text file with all the Moves
     */
    public String getMoveFileName() {
        return this.POKEMON_MOVES_FILE;
    }
    
    /**
     * Adds the Move information to the Move file. If the Move already exists, throw an exception
     * 
     * @param name The name of the Move
     * @param type The type of the Move
     * @param category The category of the Move
     * @param power The Move's power
     * @param accuracy The Move's accuracy
     * @param pp The Move's pp
     * @param description The Move's description
     * @see Move(...)
     * @see PokeWorld.addToFile( String fileLoc, String text )
     */
    public void addMoveToFile( String name, String type, String category, int power, int accuracy, int pp, String description ) {
        String line = name + "@" + type + "@" + category + "@" + power + "@" + accuracy + "@" + pp + "@" + description;
        PokeWorld.addToFile( POKEMON_MOVES_FILE, line );
    }
    
    /**
     * Adds the Move information to the Move file. If the Move already exists, throw an exception
     * 
     * @param name The name of the Move
     * @param type The type of the Move
     * @param category The category of the Move
     * @param power The Move's power
     * @param accuracy The Move's accuracy
     * @param pp The Move's pp
     * @param description The Move's description
     * @see Move(...)
     * @see PokeWorld.addToFile( String fileLoc, String text )
     */
    public void addMoveToFile( String name, String type, String category, int power, int accuracy, int pp, String description,
                               boolean[] propertyList, double[] metaData, String location ) {
        String line = name + "@" + type + "@" + category + "@" + power + "@" + accuracy + "@" + pp + "@" + description;
        
        String[] acronyms = {"AT","PC","IHBA","IHBPA","IHBSA","IHBSA","ROTBS","ROTBU","DXT","HXT","HXTIOT","DXD","CRD","RHBOD",
                             "RD","IDETPOL","DU","HPMH","IDETL","CF","CB","CF","CP","CP","CS","CB","CC","CF","CT","POAS","POL",
                             "SFXTTFH","MP","RH","LH","RA","LA","RD","LD","RSA","LSA","RSD","LSD","RE","LE","RA","LA","RS",
                             "LS","RCHR","LCHR","TRCHR","RPDOW","DH","RSC","SCBC","RDTA","CTTTOFM","OCULA","IM","ISA","GFAA",
                             "GMAB","CD","FXT","CFAS","PRM","COLM","ROTBF","LNXT","SCA","RPMH","IPA"};
        for( int i = 0; i < propertyList.length; i++ ) {
            if( propertyList[i] && i != 30 )
                line += "@" + i + ":" + acronyms[i] + ":" + metaData[i];
            else
                line += "@" + i + ":" + acronyms[i] + ":" + location;
        }
        PokeWorld.addToFile( POKEMON_MOVES_FILE, line );
    }
    
    /**
     * Sets the MoveType and MoveCategory enum values
     * 
     * @see Move(...)
     * @see Stream.of(...).map(...).collect(...)
     * @see Enum.values()
     * @see Collectors.toList()
     * @see EnumSet.allOf( Class cls )
     */
    private void setEnums() {
        List<String> moveTypeList = Stream.of( MoveType.values() )
                               .map( MoveType::name )
                               .collect( Collectors.toList() );
        List<MoveType> moveTypeValues = new ArrayList<MoveType>( EnumSet.allOf( MoveType.class ) );
        this.moveType = moveTypeValues.get( moveTypeList.indexOf( this.TYPE ) );
        
        List<String> moveCategoryList = Stream.of( MoveCategory.values() )
                               .map( MoveCategory::name )
                               .collect( Collectors.toList() );
        List<MoveCategory> moveCategoryValues = new ArrayList<MoveCategory>( EnumSet.allOf( MoveCategory.class ) );
        this.moveCategory = moveCategoryValues.get( moveCategoryList.indexOf( this.CATEGORY ) );
    }
    
    /**
     * Checks to see if this Move has a valid type
     * 
     * @see InvalidTypeException()
     * @see Move(...)
     */
    private void checkType() throws InvalidTypeException {
        boolean validType = false;
        for( int i = 0; i < MOVE_TYPE_LIST.length; i++ ) {
            if( MOVE_TYPE_LIST[i].equals( this.TYPE ) ) {
                validType = true;
                break;
            }
        }
        if(!validType)
            throw new InvalidTypeException( "Invalid type used. Found " + this.TYPE +
                                           ", which is not a valid move type for PokeBattles" );
    }
    
    /**
     * Checks to see if this Move has a valid type category
     * 
     * @see InvalidTypeException()
     * @see Move(...)
     */
    private void checkCategory() throws InvalidTypeException {
        boolean validType = false;
        for( int i = 0; i < MOVE_CATEGORY_LIST.length; i++ ) {
            if( MOVE_CATEGORY_LIST[i].equals( this.CATEGORY ) ) {
                validType = true;
                break;
            }
        }
        if(!validType)
            throw new InvalidTypeException( "Invalid type category used. Found " +
                                            this.CATEGORY + ", which is not a valid move" +
                                            " type for PokeBattles" );
    }
    
    /**
     * Checks to see if this Move is valid based on points constraints for custom moves
     * 
     * For Custom Moves:
     * - MAX_POINTS for Physical or Special category moves
     * - MAX_POINTS_STATUS for Status category moves
     * - Power costs x POWER_MULT its value
     * - Accuracy costs x ACC_MULT its value
     * - PP costs x PP_MULT its value
     * - Special category moves are constrained to minus SPECIAL_ACC_LIMIT their final accuracy result
     * 
     * Minimums and Maximums
     * - MAX_PP is the highest allowed PP
     * - MIN_PP is the lowest allowed PP
     * - MAX_ACC is the highest allowed accuracy
     * - MIN_ACC is the lowest allowed accuracy
     * - MIN_POWER is the lowest allowed power
     * - There is no limit for max power, but this is contrained based upon the other limits
     * 
     * Note that non-custom moves are not constrained by these limits
     * (eg. 'Explosion' has power 250 and acc 100 and pp 5, which would cost 250*POWER_MULT + 100*ACC_MULT +
     *  5*PP_MULT = [as of 1/31/22 9:37am] 475 points)
     *  
     *  Eg. A physical category move with 80 power, 100 accuracy, and 10 PP would cost 80*POWER_MULT + 100*ACC_MULT + 10*PP_MULT
     *      points = [as of 1/31/22 9:40am] 245 points
     * 
     * @param fromFile Determines whether this move should be searched for or not
     * @see Move(...)
     * @see InvalidPointsException
     * @see moveExists( String name )
     * @see InvalidMovesFileException
     * @see MAX_POINTS, MAX_POINTS_STATUS, POWER_MULT, ACC_MULT, PP_MULT, SPECIAL_ACC_LIMIT, MAX_PP, MIN_PP,
     *      MAX_ACC, MIN_ACC, MIN_POWER
     */
    private void checkPoints( boolean fromFile ) throws InvalidPointsException {
        //Check to see if this move is a custom move or not
        if( !fromFile ) {
            boolean isCustomMove = true;
            try {
                isCustomMove = moveExists( this.NAME );
            } catch( InvalidMovesFileException e ) {
                e.printStackTrace();
            }
            if( !isCustomMove ) return; //If this move exists and is not a custom move, do not check against limitations
        }
        
        //Checks maximum and minimums
        if( this.PP < MIN_PP )
            throw new InvalidPointsException("Invalid PP for this move. The minimum required amount of PP is " + MIN_PP +
                ", but found " + this.PP + " PP.");
        if( this.PP > MAX_PP )
            throw new InvalidPointsException("Invalid PP for this move. The maximum amount of PP allowed is " + MAX_PP +
                ", but found " + this.PP + " PP.");
        if( this.ACCURACY < MIN_ACC )
            throw new InvalidPointsException("Invalid accuracy for this move. The minimum required amount of accuracy is " + MIN_ACC +
                ", but found " + this.ACCURACY + " accuracy.");
        if( this.ACCURACY > MAX_ACC )
            throw new InvalidPointsException("Invalid accuracy for this move. The maximum accuracy allowed is " + MAX_ACC +
                ", but found " + this.ACCURACY + " accuracy.");
        if( this.TYPE.equals("SPECIAL") && this.ACCURACY > MAX_ACC - SPECIAL_ACC_LIMIT )
            throw new InvalidPointsException("Invalid accuracy for this move. The maximum accuracy allowed for special " +
                "category moves is " + (MAX_ACC - SPECIAL_ACC_LIMIT) + ", but found " + this.ACCURACY + " accuracy.");
        if( this.POWER < MIN_POWER )
            throw new InvalidPointsException("Invalid power for this move. The minimum required amount of power is " + MIN_POWER +
                ", but found " + this.POWER + " power.");
        
        int accuracyPointsSpentWithoutMult = this.TYPE.equals("SPECIAL") ? (this.ACCURACY + SPECIAL_ACC_LIMIT) : this.ACCURACY;
        double totalPointsSpent = this.POWER*POWER_MULT + accuracyPointsSpentWithoutMult*ACC_MULT + this.PP*PP_MULT;
        
        if( this.TYPE.equals("STATUS") ) {
            if( (int)totalPointsSpent > MAX_POINTS_STATUS )
                throw new InvalidPointsException("Invalid number of points spent for this custom move. Found " +
                    totalPointsSpent + ", but only " + MAX_POINTS_STATUS + " points can be spent for a status category move.");
        } else {
            if( (int)totalPointsSpent > MAX_POINTS )
                throw new InvalidPointsException("Invalid number of points spent for this custom move. Found " +
                    totalPointsSpent + ", but only " + MAX_POINTS + " points can be spent for physical or special category moves.");
        }
    }
    
    /**
     * Determines whether the Move exists based on the name entered
     * 
     * Note: Could not use Pokedex's moveExists method because Pokedex's constructor creates Move objects, which creates
     * an infinite loop of constructor calls = stack overflow error
     * 
     * @param name The name of the move
     * @return boolean True if the Move exists within the POKEMON_MOVES_FILE, false otherwise
     * @see checkPoints()
     * @see Pokedex.getMoveFromName( String name )
     * @see InvalidMovesFileException
     * @see Pokedex.moveExists( String name ) -- identical
     * @see POKEMON_MOVES_FILE
     */
    public boolean moveExists( String name ) throws InvalidMovesFileException {
        name = name.toUpperCase();
        
        File movesFile = new File( POKEMON_MOVES_FILE );
        Scanner scanner = null;
        try {
            scanner = new Scanner( movesFile );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        int lineNumber = 1;
        while( scanner.hasNextLine() ) {
            String line = scanner.nextLine();
            String[] tokens = line.split("@");
            
            if( tokens.length < PokeWorld.MIN_FIELDS_FOR_MOVE )
                throw new InvalidMovesFileException(
                    "Invalid number of moves on line number " + lineNumber + ". Found " + tokens.length +
                    " properties, but expected at least " + PokeWorld.MIN_FIELDS_FOR_MOVE + " properties.");
            
            if( tokens[0].equals( name ) )
                return true;
            
            ++lineNumber;
        }
        
        scanner.close();
        
        return false;
    }
    
    /**
     * Get the name of this move
     * 
     * @return String The name of this move
     */
    public String getName() {
        return NAME;
    }
    
    /**
     * Get this move's type
     * 
     * @return MoveType This move's type
     */
    public MoveType getType() {
        return this.moveType;
    }
    
    /**
     * Get this move's category
     * 
     * @return MoveCategory This move's category
     */
    public MoveCategory getCategory() {
        return this.moveCategory;
    }
    
    /**
     * Gets this move's power
     * 
     * @return int This move's power
     */
    public int getPower() {
        return POWER;
    }
    
    /**
     * Sets this move's power
     * 
     * @param POWER This move's new power
     */
    public void setPower( int POWER ) {
        this.POWER = POWER;
        
        if( this.POWER < 0 ) this.POWER = 0;
    }
    
    /**
     * Gets this move's current power
     * 
     * @return int This move's current power
     */
    public int getCurrentPower() {
        return currentPower;
    }
    
    /**
     * Sets this move's current power
     * 
     * @param currentPower The new current power of this move
     */
    public void setCurrentPower( int currentPower ) {
        this.currentPower = currentPower;
        
        if( this.currentPower < 0 ) this.currentPower = 0;
    }
    
    /**
     * Adds power to this move's current power. Negative values can be added
     * 
     * @param power The power to be added to this move's current power
     */
    public void addPower( int power ) {
        this.currentPower += power;
        
        if( this.currentPower < 0 ) this.currentPower = 0;
    }
    
    /**
     * Get this move's accuracy
     * 
     * @return int This move's accurary
     */
    public int getAccuracy() {
        return ACCURACY;
    }
    
    /**
     * Set this move's accuracy
     * 
     * @param ACCURACY The new accuracy of this move
     */
    public void setAccuracy( int ACCURACY ) {
        this.ACCURACY = ACCURACY;
        
        if( this.ACCURACY < 0 ) this.ACCURACY = 0;
    }
    
    /**
     * Get this move's current accuracy
     * 
     * @return int This move's current accurary
     */
    public int getCurrentAccuracy() {
        return currentAccuracy;
    }
    
    /**
     * Sets this move's current accuracy
     * 
     * @param accuracy This moves new current accuracy
     */
    public void setCurrentAccuracy( int accuracy ) {
        this.currentAccuracy = accuracy;
        
        if( this.currentAccuracy < 0 ) this.currentAccuracy = 0;
    }
    
    /**
     * Add to this move's current accuracy. Negative values can be added
     * 
     * @param accuracy The accuracy to be added to this move's accuracy
     */
    public void addAccuracy( int accuracy ) {
        this.currentAccuracy += accuracy;
        
        if( this.currentAccuracy < 0 ) this.currentAccuracy = 0;
    }
    
    /**
     * Get this move's PP
     * 
     * @return int This move's PP
     */
    public int getPP() {
        return PP;
    }
    
    /**
     * Sets this move's PP
     * 
     * @param PP The new PP of this move
     */
    public void setPP( int PP ) {
        this.PP = PP;
        
        if( this.PP < 0 ) this.PP = 0;
    }
    
    /**
     * Get this move's current PP
     * 
     * @return int This move's current PP
     */
    public int getCurrentPP() {
        return currentPP;
    }
    
    /**
     * Sets this move's current PP to a new value
     * 
     * @param PP
     */
    public void setCurrentPP( int PP ) {
        this.currentPP = PP;
        
        if( this.currentPP < 0 ) this.currentPP = 0;
    }
    
    /**
     * Adds to this move's current PP. Negative values can be added
     * 
     * @param PP The value to add to this move's current PP
     */
    public void addToCurrentPP( int PP ) {
        this.currentPP += PP;
        
        if( this.currentPP < 0 ) this.currentPP = 0;
    }
    
    /**
     * Adds to this move's maximum PP. Negative values can be added
     * 
     * @param PP The value to add to this move's maximum PP
     * @see MIN_PP global final int variable
     */
    public void addPP( int PP ) {
        this.PP += PP;
        
        if( this.PP < MIN_PP ) this.PP = MIN_PP;
    }
    
    /**
     * Get this move's description
     * 
     * @return String This move's description
     */
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * Get the minimum amount of PP required for a Move
     * 
     * @return int The minimum amount of PP required for a Move
     */
    public static int getMinimumPP() {
        return MIN_PP;
    }
    
    /**
     * Get the maximum amount of PP allowed for a Move
     * 
     * @return int The maximum amount of PP allowed for a Move
     */
    public static int getMaximumPP() {
        return MAX_PP;
    }
    
    /**
     * Gets the minimum amount of accuracy required for a Move
     * 
     * @return int The minimum amount of accuracy required for a Move
     */
    public static int getMinimumAccuracy() {
        return MIN_ACC;
    }
    
    /**
     * Gets the maximum amount of accuracy allowed for a Move
     * 
     * @return int The maximum amount of accuracy allowed for a Move
     */
    public static int getMaximumAccuracy() {
        return MAX_ACC;
    }
    
    /**
     * Gets the minimum amount of power required for a Move
     * 
     * @return int The minimum amount of power required for a Move
     */
    public static int getMinimumPower() {
        return MIN_POWER;
    }
    
    /**
     * Gets the maximum amount of points that a non-Status category Move can have
     * 
     * @return int The maximum amount of points that a non-Status category Move can have
     */
    public static int getMaximumPoints() {
        return MAX_POINTS;
    }
    
    /**
     * Gets the maximum amount of points that a Status category Move can have
     * 
     * @return int The maximum amount of points that a Status category Move can have
     */
    public static int getMaxmimumPointsStatus() {
        return MAX_POINTS_STATUS;
    }
    
    /**
     * Gets the power multiplier used when points are spent on a custom Move's power
     * 
     * @return double The power multiplier used for custom Move power
     */
    public static double getPowerMultiplier() {
        return POWER_MULT;
    }
    
    /**
     * Gets the accuracy multiplier used when points are spent on a custom Move's accuracy
     * 
     * @return double The accuracy multiplier used for custom Move accuracy
     */
    public static double getAccuracyMultiplier() {
        return ACC_MULT;
    }
    
    /**
     * Gets the PP multiplier used when points are spent on a custom Move's PP
     * 
     * @return double The PP multiplier used for custom Move PP
     */
    public static double getPPMultiplier() {
        return PP_MULT;
    }
    
    /**
     * Gets the handicap that is imposed on special category Move types for their accuracy. The accuracy
     * of special category Move types will be equal to the Accuracy of the Move minus SPECIAL_ACC_LIMIT,
     * if the Accuracy of the Move is greater than the MIN_ACC + SPECIAL_ACC_LIMIT
     * 
     * @see Move(...)
     */
    public static int getSpecialAccuracyLimit() {
        return SPECIAL_ACC_LIMIT;
    }
    
    /**
        Exception class related to move types
    */
    private class InvalidTypeException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidTypeException( String errorMessage ) {
            super( errorMessage );
        }
      
    }
    
    /**
        Exception class related to the allocation of points used to make a custom move
        
        @see checkPoints()
    */
    private class InvalidPointsException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidPointsException( String errorMessage ) {
            super( errorMessage );
        }
      
    }
    
    /**
        Exception class related to an invalid POKEMON_MOVES_FILE line
    */
    public class InvalidMovesFileException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidMovesFileException( String errorMessage ) {
            super( errorMessage );
        }
      
    }
    
    /**
     * Overrides the System.out.println(...) method
     * 
     * @param str The String to print
     */
    private void SOPln( String str ) {
        System.out.println( str );
    }
}