import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Item.java
 * 
 * Defines an item that a Pokemon can use in battle. Items can be used to enhance stats,
 * affect certain moves, learn new moves, affect experience, or can be used to evolve a
 * Pokemon
 * 
 * -----------------------------------------------------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIELDS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC FIELDS
 * --------------------------------------------------------------------
 * - none
 * 
 * PRIVATE FIELDS
 * --------------------------------------------------------------------
 * - ITEMS_FILE                  : The text file that contains the regular item information
 * - TOTAL_REG_ITEMS             : The total number of items stored in the ITEMS_FILE (the number of lines)
 * 
 * - quantity                    : The number of items of this type
 * 
 * - NAME                        : The name of this Item
 * - IMAGE_NAME                  : The image name of this Item
 * - DESCRIPTION                 : The description of this item
 * 
 * - TOTAL_ADJUSTABLE_PROPERTIES : The total adjustable properties for the property fields
 * - properties                  : The list of properties. These values are addod or multiplied to the corresponding
 *                                 Pokemon fields when this Item is used
 * - MAX_ITEM_LIST_TOKEN_SIZE    : The maximum number of informational tokens that are found in the ITEMS_FILE text file
 * - PROPERTY_NAMES              : The String names of the property stats that Items can affect
 * 
 * - affectThis                  : Tells whether this Item affects this Pokemon or the opponent Pokemon
 * - addValues                   : Tells whether this Item adds property values or multiplies them when the Item effect
 *                                 is applied
 * - affectCurrent               : Tells whether this Item affects the current property value or the maximum value
 * 
 * - healStatus                  : Tells whether this Item heals Pokemon Status back to Status.NORMAL or not
 * - healX                       : Tells which Status is healed by this Item, if any
 * 
 * - oneTimeUse                  : Tells whether this Item is consumed upon use or not
 * - useEachTurn                 : Tells whether this Item is to be repeatedly used each turn or not
 * 
 * - reqXType                    : Tells whether this Item requires a certain Move type or not
 * - reqXCat                     : Tells whether this Item requires a certain Move category or not
 * 
 * - affectsAllStats             : Tells whether this Item affects all property stats or not
 * - affectsAllMoves             : Tells whether this Item affects all the Pokemon's Moves or not
 * - chooseMove                  : Tells whether this Item requires a Move to be chosen for the effect to be
 *                                 applied or not
 * 
 * - restoreCurrentToFull        : Tells whether this Item restores the current property value to its maximum value or not
 * 
 * - reqHPX                      : Describes the HP requirements for this Item to take effect
 * 
 * - randomStat                  : Tells whether this Item affects a random property stat or not
 * - restoresLoweredStats        : Tells whether this Item restores all current stats (besides HP) that are below
 *                                 their maximums, or not
 * 
 * - affectValuesOnLevelUp       : Tells whether this Item affects values when the Pokemon levels up, or not
 * 
 * - maximizeValues              : Tells whether this Item maximizes property values or not (not-current values)
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CONSTRUCTORS @@@@@@@@@@@@@@@@@@@@@@@@
 * - Item()         : Create a default item. Only used for debugging
 * - Item( String itemLineFromFile ) throws InvalidItemTextFileInputException, InvalidPropertiesNameException
 *      : Create an Item using a formatted line from the ITEMS_FILE text file
 * - Item( int quantity, String NAME, String IMAGE_NAME, String DESCRIPTION,
                 double[] properties,
                 boolean affectThis,
                 boolean addValues,
                 boolean affectCurrent,
                 boolean healStatus, String healX,
                 boolean oneTimeUse, boolean useEachTurn,
                 String reqXType, String reqXCat,
                 boolean affectsAllStats, boolean affectsAllMoves, boolean chooseMove,
                 boolean restoreCurrentToFull, int reqHPX,
                 boolean randomStat, boolean restoresLoweredStats,
                 boolean affectValuesOnLevelUp, boolean maximizeValues )
            throws InvalidTotalPropertiesException
        : Create an Item given the fields and properties above
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ METHODS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC METHODS
 * --------------------------------------------------------------------
 * - act()                            : Doesn't do anything
 * 
 * - equals( Object obj )             : Determines if this Item and the Item inputted are equal.
 *                                      Overrides Object's equals method
 * 
 * - getQuantity()                    : Gets the quantity of this Item
 * - setQuantity( int quantity )      : Sets the quantity of this Item
 * 
 * - getName()                        : Gets the name of this Item
 * - getImageName()                   : Gets the image name of this Item
 * - getDescription()                 : Gets the description of this Item
 * 
 * - getProperties()                  : Gets the list of properties of this Item
 * - setProperties( double[] values ) : Sets the properties of this Item to the given list of values
 * 
 * - getAffectThis()                  : Gets whether this Item affects the Pokemon holding this Item or the opponent Pokemon
 * - getAddValues()                   : Gets whether this Item's properties are added or multiplied when the Item effect
 *                                      is applied
 * - getAffectCurrent()               : Gets whether this Item affects the current properties or the maximum properties of
 *                                      the Pokemon
 * 
 * - getHealStatus()                  : Gets whether this Item heals all Status effects or not
 * - getHealX()                       : Gets which (if any) of the Status effects that this Item heals
 * 
 * - getOneTimeUse()                  : Gets whether this Item is consumed upon use or not
 * - getUseEachTurn()                 : Gets whether this Item is used repeatedly each turn or not
 * 
 * - getReqXType()                    : Gets whether this Item requires a certain Move type or not
 * - getReqXCat()                     : Gets whether this Item requires a certain Move category or not
 * 
 * - getAffectsAllStats()             : Gets whether this Item affects all stats or not
 * - getAffectsAllPP()                : Gets whether this Item affects all of the Pokemon's Move PPs or not
 * - getChooseMove()                  : Gets whether this Item requires a Move to be chosen or not
 * - getRestoreCurrentToFull()        : Gets whether this Item restores the selected property's current value to its
 *                                      maximum value or not
 * 
 * - getReqHPX()                      : Gets whether this Item requires a certain HP threshhold to be met or not
 * 
 * - getRandomStat()                  : Gets whether this Item affects a random property stat or not
 * - getRestoresLoweredStats()        : Gets whether this Item restores all property current stats that are below their
 *                                      maximum values, or not
 * - getAffectValuesOnLevelUp()       : Gets whether this Item takes an effect when the Pokemon levels up or not
 * 
 * - getMaximizeValues()              : Gets whether this Item maximizes the selected maximum property stat or not
 * 
 * - getRandomItemLine()                    : Gets a random Item line from the ITEMS_FILE text file
 * - getItemLineFromName( String itemName ) : Gets the item line the corresponds with the item name entered
 * 
 * PRIVATE METHODS
 * --------------------------------------------------------------------
 * - none
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PUBLIC CLASSES @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * InvalidTotalPropertiesException   : An exception thrown if the number of properties entered is not equal to
 *                                     TOTAL_ADJUSTABLE_PROPERTIES
 * InvalidItemTextFileInputException : An exception thrown if the number of tokens found in the ITEMS_FILE text file
 *                                     is not equal to MAX_ITEM_LIST_TOKEN_SIZE or MAX_ITEM_LIST_TOKEN_SIZE - 1
 * InvalidPropertiesNameException    : An exception thrown if the property name found in the ITEMS_FILE text file
 *                                     is not one of the #TOTAL_ADJUSTABLE_PROPERTIES property names
 * InvalidItemNameException          : An exception thrown if the item name being search for does not exist within the
 *                                     ITEMS_FILE text file
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * -----------------------------------------------------------------------------------------------------------------------------
 * 
 * @author Peter Olson
 * @version 3/9/22
 */
public class Item extends Actor {
    
    private static final String ITEMS_FILE = "items.txt";
    private static final int TOTAL_REG_ITEMS = 101;
    
    //Determined when the Item is added to the list of Items
    private int quantity;
    
    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
    /* Below are all of the fields that are found in the ITEMS_FILE, excluding the final
       variables */
    
    private final String NAME;
    private final String IMAGE_NAME; //56x48 max dimensions
    private final String DESCRIPTION; //205x96 max dimensions
    
    /* Properties include the list of adjustable fields that can be
     * affected through the use items. This array of values is synthesized
     * when an item is used, and will automatically adjust Pokemon field values
     * based on the data. Depending on the boolean variables, these values
     * will affect the current or max field values, this Pokemon or the opponent's
     * Pokemon, or will add or multiply these values
     * 
     * HP, attack, defense, specialAttack, specialDefense, speed, evasion,
     * accuracy, points, exp, movePower, moveAccuracy, movePP, criticalHitRatio [14 total]
     */
    private final int TOTAL_ADJUSTABLE_PROPERTIES = 14;
    private double[] properties = new double[ TOTAL_ADJUSTABLE_PROPERTIES ];
    private final int MAX_ITEM_LIST_TOKEN_SIZE = 21;
    public static final String[] PROPERTY_NAMES = { "HP", "attack", "defense", "specialAttack", "specialDefense", "speed",
                                                    "evasion", "accuracy", "points", "exp", "movePower", "moveAccuracy",
                                                    "movePP", "criticalHitRatio" };
    
    private boolean affectThis, addValues, affectCurrent;
    private boolean healStatus;
    private String healX;
    private boolean oneTimeUse, useEachTurn;
    private String reqXType, reqXCat;
    private boolean affectsAllStats, affectsAllMoves, chooseMove;
    private boolean restoreCurrentToFull;
    private int reqHPX;
    private boolean randomStat, restoresLoweredStats;
    private boolean affectValuesOnLevelUp;
    
    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ END @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
    
    //Deduced from the other variables above
    private boolean maximizeValues;
    
    /**
     * Create a default Item. This constructor should not be called except for
     * testing purposes
     * 
     * @see setProperties( double[] values )
     */
    public Item() {
        this.quantity = 1;
        
        this.NAME = "Item";
        this.IMAGE_NAME = "./images/item_unknown.png";
        this.DESCRIPTION = "There are no items in your Pokebag. @@DEBUG Now this text is longer in order to text the add" +
                           " text to region method. I want to see if it is compatible with the current sequencing that is" +
                           " controlled within the act method";
                           
        setProperties( null ); //set all entries to 0.0
        
        this.affectThis = this.addValues = this.affectCurrent = true;
        this.healStatus = false;
        this.healX = "";
        this.oneTimeUse = true;
        this.useEachTurn = false;
        this.reqXType = this.reqXCat = "";
        this.affectsAllStats = this.affectsAllMoves = this.chooseMove = false;
        this.restoreCurrentToFull = false;
        this.reqHPX = 0;
        this.randomStat = this.restoresLoweredStats = false;
        this.affectValuesOnLevelUp = false;
        
        this.maximizeValues = false;
    }
    
    /**
     * Create an Item based on the String input from a line in the ITEMS_FILE
     * 
     * @param itemLineFromFile
     */
    public Item( String itemLineFromFile ) throws InvalidItemTextFileInputException, InvalidPropertiesNameException {
        String[] lineTokens = itemLineFromFile.split("@");
        
        if( lineTokens.length > MAX_ITEM_LIST_TOKEN_SIZE || lineTokens.length < MAX_ITEM_LIST_TOKEN_SIZE - 1 )
            throw new InvalidItemTextFileInputException(
                "Invalid number of tokens found for item creation. Expected " + (MAX_ITEM_LIST_TOKEN_SIZE - 1) +
                " or " + MAX_ITEM_LIST_TOKEN_SIZE + " total tokens, but found " + lineTokens.length + " tokens " +
                " for Item " + lineTokens[0] );
            
        NAME        = lineTokens[0];
        IMAGE_NAME  = lineTokens[1];
        DESCRIPTION = lineTokens[2];
        
        /* @@NOTE: Properties are set after setting boolean values */
        /* @@NOTE: The variable quantity is set outside of these constructors */
        
        int index = lineTokens.length == MAX_ITEM_LIST_TOKEN_SIZE ? 4 : 3;
        
        affectThis            = lineTokens[ index++ ].equals("T") ? true : false;
        addValues             = lineTokens[ index++ ].equals("T") ? true : false;
        affectCurrent         = lineTokens[ index++ ].equals("T") ? true : false;
        healStatus            = lineTokens[ index++ ].equals("T") ? true : false;
        healX                 = lineTokens[ index++ ];
        oneTimeUse            = lineTokens[ index++ ].equals("T") ? true : false;
        useEachTurn           = lineTokens[ index++ ].equals("T") ? true : false;
        reqXType              = lineTokens[ index++ ];
        reqXCat               = lineTokens[ index++ ];
        affectsAllStats       = lineTokens[ index++ ].equals("T") ? true : false;
        affectsAllMoves       = lineTokens[ index++ ].equals("T") ? true : false;
        chooseMove            = lineTokens[ index++ ].equals("T") ? true : false;
        restoreCurrentToFull  = lineTokens[ index++ ].equals("T") ? true : false;
        reqHPX                = Integer.parseInt( lineTokens[ index++ ] );
        randomStat            = lineTokens[ index++ ].equals("T") ? true : false;
        restoresLoweredStats  = lineTokens[ index++ ].equals("T") ? true : false;
        affectValuesOnLevelUp = lineTokens[ index++ ].equals("T") ? true : false;
        
        maximizeValues = false; //determined when the Item is used
        
        setProperties( null );
        
        //Fill the properties array with any values mentioned in the fourth index of the String from the text file
        double propertyValue = (double)PokeWorld.ERROR;
        if( lineTokens.length == MAX_ITEM_LIST_TOKEN_SIZE ) {
            
            String[] propertyInstructionList = lineTokens[ 3 ].split(",");
            
            for( int i = 0; i < propertyInstructionList.length; i++ ) {
            
                String[] propertyInstructions = propertyInstructionList[ i ].split(":");
            
                String propertyName = propertyInstructions[0];
                double itemPropertyAddMultValue = 0.0;
                try {
                    itemPropertyAddMultValue = addValues ? (double)Integer.parseInt(   propertyInstructions[1] ) :
                                                                      Double.parseDouble( propertyInstructions[1] );
                } catch( NumberFormatException e ) {
                    //In case a double value needs to be added instead of multiplied, such as in the case of working with
                    //stats like critical hit ratio
                    itemPropertyAddMultValue = Double.parseDouble( propertyInstructions[1] );
                }
                //Convert zeros to be -1.0, so that changed properties can be recognized
                itemPropertyAddMultValue = itemPropertyAddMultValue == 0.0 ? (double)PokeWorld.ERROR : itemPropertyAddMultValue;
                switch( propertyName ) {
                    case "HP":               properties[0]  = itemPropertyAddMultValue; break;
                    case "attack":           properties[1]  = itemPropertyAddMultValue; break;
                    case "defense":          properties[2]  = itemPropertyAddMultValue; break;
                    case "specialAttack":    properties[3]  = itemPropertyAddMultValue; break;
                    case "specialDefense":   properties[4]  = itemPropertyAddMultValue; break;
                    case "speed":            properties[5]  = itemPropertyAddMultValue; break;
                    case "evasion":          properties[6]  = itemPropertyAddMultValue; break;
                    case "accuracy":         properties[7]  = itemPropertyAddMultValue; break;
                    case "points":           properties[8]  = itemPropertyAddMultValue; break;
                    case "exp":              properties[9]  = itemPropertyAddMultValue; break;
                    case "movePower":        properties[10] = itemPropertyAddMultValue; break;
                    case "moveAccuracy":     properties[11] = itemPropertyAddMultValue; break;
                    case "movePP":           properties[12] = itemPropertyAddMultValue; break;
                    case "criticalHitRatio": properties[13] = itemPropertyAddMultValue; break;
                    default:
                        throw new InvalidPropertiesNameException(
                            "Invalid properties name found in " + ITEMS_FILE + ". Found name " + propertyName + 
                            ", but this name is not one of the " + properties.length + " valid properies." );

                }
            }
        }
    }
    
    /**
     * Create an Item from the given fields
     * 
     * @param quantity The number of this item
     * @param NAME The name of this Item
     * @param IMAGE_NAME The image file name of this Item
     * @param DESCRIPTION The description of this item
     * @param properties The list of properties that are used to affect the Pokemon's stats
     * @param affectThis Determines whether this Item affects this Pokemon or the opponent Pokemon
     * @param addValues Determines whether this Item's properties are added or multiplier to Pokemon stats
     * @param affectCurrent Determines whether this Item affects the Pokemon's current stats or maximum stats
     * @param healStatus Determines whether this Item heals the Status of the Pokemon or not
     * @param healX Determines what Status condition this Item heals
     * @param oneTimeUse Determines whether this Item is a one-time use item or not
     * @param useEachTurn Determines whether this Item is used each turn or not
     * @param reqXType Determines what Move types are required
     * @param reqXCat Determines what Move categories are required
     * @param affectsAllStats Determines whether this Item affects all stats or not
     * @param affectsAllMoves Determines whether this Item affects all Moves or not
     * @param chooseMove Determines whether this Item requires the choice of a Move or not
     * @param restoreCurrentToFull Determines whether this Item restores the selected current stats to their maximum values or not
     * @param reqHPX Determines the HP requirement for whether this Item can take effect or not
     * @param randomStat Determines whether this Item affects a random stat or not
     * @param restoresLoweredStats Determines whether this Item restores all lowered stats or not (aside from HP)
     * @param affectValuesOnLevelUp Determines whether this Item affects stats when the Pokemon levels up
     * @param maximizeValues Determines whether this Item's properties adjustments should maximize values or not
     */
    public Item( int quantity, String NAME, String IMAGE_NAME, String DESCRIPTION,
                 double[] properties,
                 boolean affectThis,
                 boolean addValues,
                 boolean affectCurrent,
                 boolean healStatus, String healX,
                 boolean oneTimeUse, boolean useEachTurn,
                 String reqXType, String reqXCat,
                 boolean affectsAllStats, boolean affectsAllMoves, boolean chooseMove,
                 boolean restoreCurrentToFull, int reqHPX,
                 boolean randomStat, boolean restoresLoweredStats,
                 boolean affectValuesOnLevelUp, boolean maximizeValues )
            throws InvalidTotalPropertiesException {
        this.quantity = quantity;
        this.NAME = NAME;
        this.IMAGE_NAME = IMAGE_NAME;
        this.DESCRIPTION = DESCRIPTION;
        
        if( properties.length != TOTAL_ADJUSTABLE_PROPERTIES )
            throw new InvalidTotalPropertiesException(
                "Invalid number of additive properties for this Item. Expected " + TOTAL_ADJUSTABLE_PROPERTIES +
                ", but found " + properties.length + " total properties." );

        this.properties = properties;
        this.affectThis = affectThis;
        this.addValues = addValues;
        this.affectCurrent = affectCurrent;
        this.healStatus = healStatus; this.healX = healX;
        this.oneTimeUse = oneTimeUse; this.useEachTurn = useEachTurn;
        this.reqXType = reqXType; this.reqXCat = reqXCat;
        this.affectsAllStats = affectsAllStats; this.affectsAllMoves = affectsAllMoves; this.chooseMove = chooseMove;
        this.restoreCurrentToFull = restoreCurrentToFull; this.reqHPX = reqHPX;
        this.randomStat = randomStat; this.restoresLoweredStats = restoresLoweredStats;
        this.affectValuesOnLevelUp = affectValuesOnLevelUp;
        this.maximizeValues = maximizeValues;
    }
    
    /**
     * Act - do whatever the Item wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        // Add your action code here.
    }
    
    /**
     * Determines whether two Items are equal, by overriding the equals(...) method that is
     * inherited from the Object class
     * 
     * Note: This method should NOT make sure that the quantity global variables are equal
     * 
     * @param obj The Item to compare this Item to
     * @return boolean True if the Items are equal, false otherwise
     * @see All the getters
     */
    @Override
    public boolean equals( Object obj ) {
        if(      obj == null )                       return false;
        else if( this.getClass() != obj.getClass() ) return false;
        Item item = (Item)obj;
        if( !this.NAME.equals( item.getName() ) || !this.IMAGE_NAME.equals( item.getImageName() ) ||
            !this.DESCRIPTION.equals( item.getDescription() ) || this.affectThis != item.getAffectThis() ||
            this.addValues != item.getAddValues() || this.affectCurrent != item.getAffectCurrent() ||
            this.healStatus != item.getHealStatus() || !this.healX.equals( item.getHealX() ) ||
            this.oneTimeUse != item.getOneTimeUse() || this.useEachTurn != item.getUseEachTurn() ||
            !this.reqXType.equals( item.getReqXType() ) || !this.reqXCat.equals( item.getReqXCat() ) ||
            this.affectsAllStats != item.getAffectsAllStats() || this.affectsAllMoves != item.getAffectsAllMoves() ||
            this.chooseMove != item.getChooseMove() || this.restoreCurrentToFull != item.getRestoreCurrentToFull() ||
            this.reqHPX != item.getReqHPX() || this.randomStat != item.getRandomStat() ||
            this.restoresLoweredStats != item.getRestoresLoweredStats() ||
            this.affectValuesOnLevelUp != item.getAffectValuesOnLevelUp() ||
            this.maximizeValues != item.getMaximizeValues()
        )
            return false;
            
        for( int i = 0; i < properties.length; i++ )
            if( this.properties[i] != item.getProperties()[i] ) return false;
        
        return true;
    }
    
    /**
     * Gets the quantity of this Item
     * 
     * @return int The quantity of this Item
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Sets the quantity of this Item
     * 
     * @param quantity The quantity of this Item
     */
    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }
    
    /**
     * Gets the name of this Item
     * 
     * @return String The name of this Item
     */
    public String getName() {
        return NAME;
    }
    
    /**
     * Gets the image name of this Item
     * 
     * @return String The image file path of this Item
     */
    public String getImageName() {
        return IMAGE_NAME;
    }
    
    /**
     * Gets the description of this Item
     * 
     * @return String The description of this Item
     */
    public String getDescription() {
        return DESCRIPTION;
    }
    
    /**
     * Gets the properties of this Item
     * 
     * The indices of this array represents the following properties:
     * 
     * HP, attack, defense, specialAttack, specialDefense, speed, evasion,
     * accuracy, points, exp, movePower, moveAccuracy, movePP, criticalHitRatio
     * 
     * @return double[] The list of properties of this Item
     */
    public double[] getProperties() {
        return properties;
    }
    
    /**
     * Sets the properties array to have the given default values
     * 
     * @param values The list of values to set. If values is null, set all values to 0.0
     *               for adding, or 1.0 for multiplying
     */
    public void setProperties( double[] values ) {
        if( values != null ) {
            this.properties = values;
            return;
        }
        
        //Initialize properties with all zeros for adding or all ones for multiplying
        for( int i = 0; i < TOTAL_ADJUSTABLE_PROPERTIES; i++ )
            this.properties[i] = 0.0;
    }
    
    /**
     * Gets whether this Item affects the current Pokemon or the opponent Pokemon
     * 
     * @return boolean True if this Item affects the current Pokemon, false if this
     *                 Item affects the opponent Pokemon
     */
    public boolean getAffectThis() {
        return affectThis;
    }
    
    /**
     * Gets whether this Item adds values or multiplies them
     * 
     * @return boolean True if this Item adds values, false if it multiplies them
     */
    public boolean getAddValues() {
        return addValues;
    }
    
    /**
     * Gets whether this Item affects the current values or the maximum values
     * of the Pokemon
     * 
     * @return boolean True if this Item affects the current values, false if
     *                 it affects the maximum values
     */
    public boolean getAffectCurrent() {
        return affectCurrent;
    }
    
    /**
     * Gets the heal status of this Item. A heal status of 'true' means that this item
     * heals the Pokemon's Status back to Status.NORMAL when the item is used. If false,
     * this item does not affect the Pokemon's Status
     * 
     * @return boolean True if this Item heals the Pokemon's Status back to Status.NORMAL,
     *                 false otherwise
     */
    public boolean getHealStatus() {
        return healStatus;
    }
    
    /**
     * Gets whether this Item heals a specific Status condition or not. The value healX
     * has the name of the Status that can be healed, or has an empty String if this
     * Item does not heal any Status conditions
     * 
     * @return String The name of the Status condition that can be healed, or an empty
     *                String if this Item does not heal any Status conditions
     */
    public String getHealX() {
        return healX;
    }
    
    /**
     * Gets whether this Item can be used multiple times without being consumed or not.
     * 
     * @return boolean True if this Item is consumed upon use, false otherwise
     */
    public boolean getOneTimeUse() {
        return oneTimeUse;
    }
    
    /**
     * Gets whether this Item is used every turn or not
     * 
     * @return boolean True if this Item is used every turn, false otherwise
     */
    public boolean getUseEachTurn() {
        return useEachTurn;
    }
    
    /**
     * Gets whether this Item requires a specific Move type or not. The value reqXType
     * has the name of the Move type, or has an empty String if the Item does not require
     * a Move type
     * 
     * @return String The Move type required for this Item to be used, or an empty String
     *                if this Item does not require a Move Type
     */
    public String getReqXType() {
        return reqXType;
    }
    
    /**
     * Gets whether this Item requires a specific Move category or not. The value reqXCat
     * has the name of the Move category, or has an empty String if the Item does not require
     * a Move category
     * 
     * @return String The Move category required for this Item to be used, or an empty String
     *                if this Item does not require a Move category
     */
    public String getReqXCat() {
        return reqXCat;
    }
    
    /**
     * Gets whether this Item affects all the stats of a Pokemon or not
     * 
     * @return boolean True if this Item affects all stats, false otherwise
     */
    public boolean getAffectsAllStats() {
        return affectsAllStats;
    }
    
    /**
     * Gets whether this Item affects all the Moves of a Pokemon or not
     * 
     * @return boolean True if this Item affects all the Moves of a Pokemon, false otherwise
     */
    public boolean getAffectsAllMoves() {
        return affectsAllMoves;
    }
    
    /**
     * Gets whether this Item requires a Move to be selected or not. If a Move
     * must be selected, a separate pop-up window is required for the user
     * to select the Move that will be affected by this Item
     * 
     * @return boolean True if this Item requires a Move to be selected, false otherwise
     */
    public boolean getChooseMove() {
        return chooseMove;
    }
    
    /**
     * Gets whether this Item restores the selected current properties to their
     * maximum values or not
     * 
     * @return boolean True if this Item restores the selected current properties
     *                 to their maximum values, false otherwise
     */
    public boolean getRestoreCurrentToFull() {
        return restoreCurrentToFull;
    }
    
    /**
     * Gets whether this Item requires a certain HP threshhold or not. The following
     * values denote the HP threshhold requirements:
     * 
     * 0: No HP threshhold required
     * 1: HP < 33%
     * 2: HP < 50%
     * 3: HP >= 50%
     * 4: HP > 66%
     * 5: HP = 100%
     * 
     * @return int The value that denotes a threshhold requirement for the Pokemon's HP value.
     *             A value of zero means that their is no HP threshhold requirement
     */
    public int getReqHPX() {
        return reqHPX;
    }
    
    /**
     * Gets whether this Item affects a stat randomly or not
     * 
     * @return boolean True if this Item affects a stat randomly, false otherwise
     */
    public boolean getRandomStat() {
        return randomStat;
    }
    
    /**
     * Gets whether this Item restores lowered stats or not
     * 
     * @return boolean True if this Item restores lowered stats, false otherwise
     */
    public boolean getRestoresLoweredStats() {
        return restoresLoweredStats;
    }
    
    /**
     * Gets whether this Item affects stats when a Pokemon levels up or not
     * 
     * @return boolean True if this Item affects Pokemon stats on level up, false otherwise
     */
    public boolean getAffectValuesOnLevelUp() {
        return affectValuesOnLevelUp;
    }
    
    /**
     * Gets whether this Item maximizes the property value or not
     * 
     * @return boolean True if this Item maximizes the property value listed or not
     */
    public boolean getMaximizeValues() {
        return maximizeValues;
    }
    
    /**
     * Get a random line (the data for an Item) from the ITEMS_FILE file
     * 
     * @return String The line containing the data for an Item
     */
    public static String getRandomItemLine() {
        File file = new File( ITEMS_FILE );
        Scanner scanner = null;
        try {
            scanner = new Scanner( file );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        Random random = new Random();
        int lineNumber = random.nextInt( TOTAL_REG_ITEMS );
        int i = 0;
        while( scanner.hasNextLine() ) {
            String line = scanner.nextLine();
            if( i++ == lineNumber ) return line;
        }
        
        return PokeWorld.ERROR + "";
    }
    
    /**
     * Get an Item based on the name of the Item from the ITEMS_FILE
     * 
     * @param itemName The item name to find in the text file
     * @return String The line of text from the ITEMS_FILE text file that has the Item information
     */
    public static String getItemLineFromName( String itemName ) throws InvalidItemNameException {
        File file = new File( ITEMS_FILE );
        Scanner scanner = null;
        try {
            scanner = new Scanner( file );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        while( scanner.hasNextLine() ) {
            String line = scanner.nextLine();
            String[] tokens = line.split("@");
            if( tokens[0].toUpperCase().equals( itemName.toUpperCase() ) ) return line;
        }
        
        throw new InvalidItemNameException(
            "Invalid item name. The item " + itemName + " does not exist in the file " + ITEMS_FILE + "." );
    }
    
    /**
        Exception class related to the incorrect number of Item properties
    */
    public class InvalidTotalPropertiesException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidTotalPropertiesException( String errorMessage ) {
            super( errorMessage );
        }
      
    }
    
    /**
        Exception class related to the incorrect number of Item tokens from the ITEMS_FILE file
    */
    public class InvalidItemTextFileInputException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidItemTextFileInputException( String errorMessage ) {
            super( errorMessage );
        }
      
    }
    
    /**
        Exception class related to an invalid properties name found in the ITEMS_FILE.txt file
    */
    public class InvalidPropertiesNameException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidPropertiesNameException( String errorMessage ) {
            super( errorMessage );
        }
      
    }
    
    /**
        Exception class related to an invalid item name entered
    */
    public static class InvalidItemNameException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidItemNameException( String errorMessage ) {
            super( errorMessage );
        }
      
    }
}
