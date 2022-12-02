import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * PokemonActor.java
 * 
 * This class adds additional functionality to the Actor class and contains methods that all Pokemon have
 * 
 * --------------------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIELDS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC FIELDS
 * --------------------------------------------------------------------
 * - none -
 * 
 * PRIVATE FIELDS
 * --------------------------------------------------------------------
 * - none -
 * 
 * PROTECTED FIELDS
 * --------------------------------------------------------------------
 * - PokeWorld.ERROR          : The value returned when an error is encountered
 * 
 * - NAME           : The name of this Pokemon
 * - IMAGE_NAME     : The path and name of the image of this Pokemon
 * - BATTLE_IMAGE   : The path and name of the image used in battle
 * - TYPE           : The type of this Pokemon. See Pokedex.getPokemonTypes() to see a list
 *                    of the Pokemon types
 * - HP             : The maximum HP (hit points) of this Pokemon
 * - attack         : The attack value of this Pokemon
 * - defense        : The defense value of this Pokemon
 * - specialAttack  : The special attack value of this Pokemon. Special attack is the attack
 *                    value used for special attack moves
 * - specialDefense : The special defense value of this Pokemon. Special defense is the
 *                    defense value used for defense against special attacks
 * - speed          : The speed value of this Pokemon
 * - evasion        : The evasion value of this Pokemon. Evasion is used to help evade attacks
 * - accuracy       : The accuracy value of this Pokemon. Accuracy is used to help land attacks
 * - points         : The total points that this Pokemon has. Points are used to upgrade a
 *                    Pokemon's skills and abilities
 * - exp            : The total experience of this Pokemon. Experience is gained by defeating
 *                    other Pokemon in battle. Gaining enough experience will level up your
 *                    Pokemon. Leveling up awards the Pokemon with more points. See
 *                    Pokedex.getExperiencePerLevel( int level ) for details on experience
 * - level          : The level of this Pokemon. New Pokemon usually start at level 1
 * - wins           : The total wins that this Pokemon has. The more wins a Pokemon has, the
 *                    greater the benefits to the Pokemon and the Pokemon trainer
 * 
 * - currentHP             : The current HP of this Pokemon. The current HP of a Pokemon
 *                           cannot be greater than the maximum HP of the Pokemon
 * - currentAttack         : The current attack of this Pokemon. Pokemon attributes can
 *                           raise or lower, depending on status-type moves used from Pokemon
 *                           in battle, or from items
 * - currentDefense        : The current defense of this Pokemon
 * - currentSpecialAttack  : The current special attack of this Pokemon
 * - currentSpecialDefense : The current special defense of this Pokemon
 * - currentSpeed          : The current speed of this Pokemon
 * - currentEvasion        : The current evasion of this Pokemon
 * - currentAccuracy       : The current accuracy of this Pokemon
 * 
 * - addedExp    : The total experience that this Pokemon has gained so far in this battle series
 * 
 * - moves       : The list of Pokemon moves that this Pokemon knows. A Pokemon is limited to
 *                 knowing a maximum of PokeBattle.MAX_NUMBER_OF_MOVES (usually 4), and cannot
 *                 know less than 1 move
 * - currentMove : The current Move being used by this PokemonActor in battle
 * 
 * - items       : The list of items that this Pokemon has. Items are gained through battle victories,
 *                 or by generous onlookers, such as Officer Jenny
 * - currentItem : The item that this Pokemon is currently using or holding
 * 
 * - pokedex     : Every Pokemon has their own Pokedex, which can be used to access information
 *                 on Pokemon moves, Pokemon types, Pokemon experience, and other information.
 *                 In a later version, Poke trainers will have a Pokedex, not the Pokemon
 * 
 * - status      : The status of this Pokemon. A healthy Pokemon has a Status.NORMAL status.
 *                 The other statuses include Status.BURN, Status.FREEZE, Status.PARALYSIS,
 *                 Status.POISON, Status.SLEEP, Status.BOUND, and Status.FAINTED
 * 
 * - totalEvolutions  : The total number of times this Pokemon has evolved
 * 
 * - height           : The height of this Pokemon, in feet and inches
 * - weight           : The weight of this Pokemon, in pounds
 * - description      : The description of this Pokemon
 * - pokemonNumber    : The number of this Pokemon
 * 
 * - criticalHitRatio : The percent chance that a Pokemon's move will achieve a critical hit
 * 
 * - uniqueID         : The unique ID of this Pokemon
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ENUMS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * - Status   : NORMAL, BURN, FREEZE, PARALYSIS, POISON, SLEEP, BOUND, FAINTED
 *              [from the Pokemon.java interface]
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CONSTRUCTORS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * - PokemonActor( int uniqueID ) : Create a default PokemonActor object
 * - PokemonActor( String name, String imageName, String battleImageName, String type,
                         int HP, int attack, int defense, int specialAttack, int specialDefense,
                         int speed, int evasion, int accuracy, int exp, int level, int wins,
                         ArrayList<Move> moves, ArrayList<Item> items,
                         double height, double weight, String description, int uniqueID )
 *             : Create a PokemonActor with the given attributes. This Pokemon still must abide
 *               by the limits on starting Pokemon based on PokeWorld.STARTING_POINTS
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ METHODS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC METHODS
 * ---------------------------------------------------------------------
 * - act()              : The method called repeatedly by the world PokeBattle.java
 * - checkPokemonInit() : A method that checks whether the Pokemon created using a constructor
 *                        satisfies the requirements and limits for starting Pokemon
 * 
 * - getOneIntersectingPokemon( Class cls ) : Gets the first found intersecting PokemonActor to this Pokemon
 * - isTouchingPokemon( Class cls )         : Determines if this Pokemon is touching another PokemonActor
 * 
 * - spendPoints( int points )     : Spend points to advance an ability or stat or skill
 * 
 * - getStatus()                   : Gets the status of this Pokemon
 * - getStatusText()               : Gets the text to be displayed for that given Status change
 * - setStatus( Status status )    : Sets the status of this Pokemon
 * 
 * - getName()                              : Gets the name of this Pokemon
 * - getImageName()                         : Gets the image name of this Pokemon
 * - setImageName( String imageLoc )        : Sets the image of this Pokemon
 * - getBattleImageName()                   : Gets the battle image name for this Pokemon
 * - setBattleImageName( String image Loc ) : Sets the battle image name for this Pokemon
 * - getType()                              : Gets this Pokemon's types
 * - matchesType( String type )             : Determines whether this Pokemon's types matches the given type
 * 
 * - getHP()                       : Gets the HP of this Pokemon
 * - setHP( int HP )               : Sets the HP of this Pokemon
 * - getCurrentHP()                : Gets the current HP of this Pokemon
 * - setCurrentHP( int currentHP ) : Sets the current HP of this Pokemon
 * - addHP( int HP )               : Adds HP to current HP for this Pokemon
 * 
 * - getAttack()                             : Gets the attack of this Pokemon
 * - setAttack( int attack )                 : Sets the attack of this Pokemon
 * - getCurrentAttack()                      : Gets the current attack of this Pokemon
 * - setCurrentAttack( int currentAttack )   : Sets the current attack of this Pokemon
 * 
 * - getDefense()                            : Gets the defense of this Pokemon
 * - setDefense( int defense )               : Sets the defense of this Pokemon
 * - getCurrentDefense()                     : Gets the current defense of this Pokemon
 * - setCurrentDefense( int currentDefense ) : Sets the current defense of this Pokemon
 * 
 * - getSpecialAttack()                      : Gets the special attack of this Pokemon
 * - setSpecialAttack( int specialAttack )   : Sets the special attack of this Pokemon
 * - getCurrentSpecialAttack()               : Gets the current special attack of this Pokemon
 * - setCurrentSpecialAttack( int currentSpecialAttack ) : Sets the current special attack of
 *                                                         this Pokemon
 *                                                         
 * - getSpecialDefense()                     : Gets the special defense of this Pokemon
 * - setSpecialDefense( int specialDefense ) : Sets the special defense of this Pokemon
 * - getCurrentSpecialDefense()              : Gets the current special defense of this Pokemon
 * - setCurrentSpecialDefense( int currentSpecialDefense ) : Sets the current special defense
 *                                                           of this Pokemon
 *                                                           
 * - getSpeed()                              : Gets the speed of this Pokemon
 * - setSpeed( int speed )                   : Sets the speed of this Pokemon
 * - getCurrentSpeed()                       : Gets the current speed of this Pokemon
 * - setCurrentSpeed( int currentSpeed )     : Sets the current speed of this Pokemon
 * 
 * - getEvasion()                            : Gets the evasion of this Pokemon
 * - setEvasion( int evasion )               : Sets the evasion of this Pokemon
 * - getCurrentEvasion()                     : Gets the current evasion of this Pokemon
 * - setCurrentEvasion( int currentEvasion ) : Sets the current evasion of this Pokemon
 * 
 * - getAccuracy()                             : Gets the accuracy of this Pokemon
 * - setAccuracy( int accuracy )               : Sets the accuracy of this Pokemon
 * - getCurrentAccuracy()                      : Gets the current accuracy of this Pokemon
 * - setCurrentAccuracy( int currentAccuracy ) : Sets the current accuracy of this Pokemon
 * 
 * - getCurrentPoints()             : Gets the current number of points that this Pokemon has
 * - setCurrentPoints( int points ) : Sets the current number of points that this Pokemon has
 * 
 * - getTotalExp()                  : Gets the total experience this Pokemon has
 * - getRemainingExp()              : Gets the remaining experience needed before this Pokemon
 *                                    levels up
 * - getTotalAddedExp()             : Gets the total added experience this Pokemon has gotten
 *                                    so far in this battle series
 * - setTotalExp( int exp )         : Sets the total experience this Pokemon has
 * - addExp( int exp )              : Adds experience to this Pokemon's total experience
 * 
 * - getCurrentLevel()              : Gets the level of this Pokemon
 * 
 * - getItems()                     : Gets a list of this Pokemon's items
 * 
 * - getMoves()                     : Gets a list of this Pokemon's learned moves
 * - getCurrentMove()               : Gets the current Move that is selected during Battle or
 *                                    the Move that is currently being used
 * - setCurrentMove()               : Sets the current Move that is being selected or being used
 * - getMoveIndex( String moveName ): Gets the index of the name of the Move entered from the Move list
 * - printMoves()                   : Prints the list of moves that this Pokemon knows
 * - addMove( Move move )           : Add a move to the list of moves that this Pokemon knows
 * - deleteMove( String moveName )  : Delete a move off the list of moves that this Pokemon
 *                                    knows
 * - deleteMove( int moveNumber )   : Delete a move off the list of moves that this Pokemon
 *                                    knows
 * - addItem( Item item )           : Add an item to this Pokemon's list of Items
 * - getMovePP( String moveName )   : Gets the PP (Power Points, aka the number of times this
 *                                    move can be used in one battle series) of the given move
 * - getMovePP( int moveNumber )    : Gets the PP of the given move
 * - getCurrentMovePP( String moveName ) : Gets the current PP of the given move
 * - getCurrentMovePP( int moveNumber )  : Gets the current PP of the given move
 * 
 * - getTotalWins()                 : Gets the total wins that this Pokemon has
 * - addWin()                       : Adds 1 win to this Pokemon's total wins
 * 
 * - getPokedex()                   : Gets this Pokemon's Pokedex
 * 
 * - getTotalEvolutions()           : Gets the number of times this Pokemon has evolved
 * 
 * - getHeight()                    : Gets the height of this Pokemon, in feet and inches
 * - getWeight()                    : Gets the weight of this Pokemon, in pounds
 * - getDescription()               : Gets the description of this Pokemon
 * - getPokemonNumber()             : Gets the number of this Pokemon
 * 
 * - setHeight( double height )           : Sets the height of this Pokemon, in feet and inches
 * - setWeight( double weight )           : Sets the weight of this Pokemon, in pounds
 * - setDescription( String description ) : Sets the description of this Pokemon
 * 
 * - getUniqueID()                        : Gets the unique ID of this Pokemon
 * 
 * - hasItem( Item item )                 : Determines whether this Pokemon already has the given item or not
 * - getCurrentItem()                     : Gets the current Item being held or used by the Pokemon
 * - setCurrentItem( Item currentItem )   : Sets the current Item being held or used by the Pokemon
 * 
 * PROTECTED METHODS
 * ---------------------------------------------------------------------
 * - evaluateExp( int exp )                  : Used to determine whether a Pokemon has leveled
 *                                             up or not. If so, adds points, exp, and level(s)
 * - hasLeveled( int exp, int currentLevel ) : Used to determine whether a Pokemon has leveled
 *                                             up or not
 * 
 * - printMessage( String message )          : @Deprecated Prints a message to the screen
 * - printError( String message )            : @Deprecated Prints an error to the screen
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PRIVATE CLASSES @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * - none
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * --------------------------------------------------------------------------------------------
 * 
 * @author Peter Olson
 * @version 1/18/22
 * @see Pokemon.java
 * @see Pokedex.java
 * @see PokeWorld.java
 * @see PokeArena.java
 * @see PokeBattle.java
 * @see Move.java
 * @see OfficerJenny.java
 * @see Pikachu.java
 */
public class PokemonActor extends Actor implements Pokemon {

    protected String NAME;
    protected String IMAGE_NAME;
    protected String BATTLE_IMAGE;
    protected String TYPE;
    
    protected int HP, attack, defense, specialAttack, specialDefense, speed, evasion,
                  accuracy, points, exp, level, wins;
    
    protected int currentHP, currentAttack, currentDefense, currentSpecialAttack,
                  currentSpecialDefense, currentSpeed, currentEvasion,
                  currentAccuracy;
    
    protected int addedExp = 0;
    
    protected ArrayList<Move> moves;
    protected Move currentMove = null;
    protected ArrayList<Item> items;
    protected Item currentItem = null;
    
    protected Pokedex pokedex;
    
    protected Status status = Status.NORMAL;
    
    protected int totalEvolutions = 0;
    
    protected double height, weight;
    protected String description;
    protected int pokemonNumber;
    
    protected double criticalHitRatio, currentCriticalHitRatio;
    
    //Unique ID of this object
    private final int UNIQUE_ID;
    
    /**
     * The act method for this PokemonActor. This method should not be called directly by subclasses
     */
    public void act() {
        // Add your action code here.
    }
    
    /**
     * Default constructor -- This constructor should not be called directly
     * from a world object
     * 
     * @param uniqueID The unique ID of this PokemonActor
     * @see Pokedex.java
     */
    public PokemonActor( int uniqueID ) {
        super();
        
        NAME = "PokemonActor";
        
        points = PokeWorld.STARTING_POINTS;
        
        exp      = 0;
        level    = 1; //This costs no points
        wins     = 0;
        
        pokedex = new Pokedex();
        
        this.UNIQUE_ID = uniqueID;
    }

    /**
     * Create a PokemonActor object with the given fields. This constructor should
     * not be directly called from a world object
     * 
     * Checks for valid Pokemon creation (based on PokeWorld.STARTING_POINTS) by calling checkPokemonInit()
     * 
     * @param HP The maximum HP of this PokemonActor
     * @param attack The attack value of this PokemonActor
     * @param defense The defense value of this PokemonActor
     * @param specialAttack The special attack value of this PokemonActor
     * @param specialDefense The special defense value of this PokemonActor
     * @param speed The speed value of this PokemonActor
     * @param evasion The evasion value of this PokemonActor
     * @param accuracy The accuracy value of this PokemonActor
     * @param exp This PokemonActor's starting exp
     * @param level This PokemonActor's starting level
     * @param wins This PokemonActor's starting number of wins
     * @param moves This PokemonActor's list of moves
     * @param items This PokemonActor's list of items
     * @param height The height of this PokemonActor, in feet and inches
     * @param weight The weight of this PokemonActor, in pounds
     * @param description The description of this PokemonActor
     * @param uniqueID The unique ID of this Pokemon
     * @see World.getWorld()
     * @see World.showText()
     */
    public PokemonActor( String name, String imageName, String battleImageName, String type,
                         int HP, int attack, int defense, int specialAttack, int specialDefense,
                         int speed, int evasion, int accuracy, int exp, int level, int wins,
                         ArrayList<Move> moves, ArrayList<Item> items,
                         double height, double weight, String description,
                         int uniqueID ) throws InvalidMoveTotalException {
        points       = PokeWorld.STARTING_POINTS;
        
        NAME         = name;
        IMAGE_NAME   = imageName;
        BATTLE_IMAGE = battleImageName;
        TYPE         = type;
        
        this.HP             = currentHP             = spendPoints( HP ); //Multiplier (x10)
        this.attack         = currentAttack         = spendPoints( attack );
        this.defense        = currentDefense        = spendPoints( defense );
        this.specialAttack  = currentSpecialAttack  = spendPoints( specialAttack );
        this.specialDefense = currentSpecialDefense = spendPoints( specialDefense );
        this.speed          = currentSpeed          = spendPoints( speed );
        this.evasion        = currentEvasion        = spendPoints( evasion );
        this.accuracy       = currentAccuracy       = spendPoints( accuracy );
        this.exp      = exp;
        this.level    = level; //This costs no points
        this.wins     = wins;
        
        this.moves = moves;
        this.items = items;
        if( items.size() > 0 )
            this.currentItem = items.get(0);
        
        this.height = height;
        this.weight = weight;
        this.description = description;
        
        this.criticalHitRatio = currentCriticalHitRatio = (this.speed / 2.0) / PokeWorld.CRITICAL_HIT_RATIO;
        
        pokedex = new Pokedex();
        
        this.UNIQUE_ID = uniqueID;
        
        if( moves.size() < PokeWorld.MIN_NUMBER_OF_MOVES )
            throw new InvalidMoveTotalException("A Pokemon may have no fewer than " + PokeWorld.MIN_NUMBER_OF_MOVES +
                " move, but this Pokemon has " + moves.size() + " moves.");
        if( moves.size() > PokeWorld.MAX_NUMBER_OF_MOVES )
            throw new InvalidMoveTotalException("A Pokemon may have no more than " + PokeWorld.MAX_NUMBER_OF_MOVES +
                " moves, but this Pokemon has " + moves.size() + " moves.");
    }
    
    /**
     * Determines whether this actor is touching another actor
     * 
     * @param cls The class to check if the Pokemon is touching
     * @return boolean True if this Pokemon is touching another Pokemon, false otherwise
     * @see Actor.isTouching( Class cls )
     */
    public boolean isTouchingPokemon( Class cls ) {
        return isTouching( cls );
    }
    
    /**
     * Gets one PokemonActor that this Pokemon is intersecting
     * 
     * @param actor The class of which to find an intersecting PokemonActor
     * @return Actor The Actor that this Pokemon is intersecting
     */
    public Actor getOneIntersectingPokemon( Class cls ) {
        return getOneIntersectingObject( cls );
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Checks the initial values of the Pokemon to make sure that the total
     * points spent on this Pokemon does not exceed the initial value of PokeWorld.STARTING_POINTS
     * 
     * @see PokemonActor(...)
     */
    public void checkPokemonInit() throws InvalidPokemonPointsException, InvalidPokemonValuesException {
        int tempPokePoints = HP + attack + defense + specialAttack + specialDefense + speed +
                             evasion + accuracy + (level - 1)*PokeWorld.POINTS_GIVEN_ON_LEVEL_UP +
                             moves.size()*PokeWorld.NEW_MOVE_COST;
        
        if( tempPokePoints > PokeWorld.STARTING_POINTS )
            throw new InvalidPokemonPointsException("Expected Pokepoints of: " + PokeWorld.STARTING_POINTS +
                                                    ", but found " + tempPokePoints + " spent.");
        
        if( HP < PokeWorld.MIN_ATTRIBUTE || attack < PokeWorld.MIN_ATTRIBUTE ||
            defense < PokeWorld.MIN_ATTRIBUTE || specialAttack < PokeWorld.MIN_ATTRIBUTE ||
            specialDefense < PokeWorld.MIN_ATTRIBUTE || speed < PokeWorld.MIN_ATTRIBUTE ||
            evasion < PokeWorld.MIN_ATTRIBUTE || accuracy < PokeWorld.MIN_ATTRIBUTE )
            throw new InvalidPokemonValuesException("One or more values of this Pokemon is less than the required" +
                " minimum value for Pokemon attributes. The minimum value for all attributes is: " + PokeWorld.MIN_ATTRIBUTE +
                ", but this Pokemon has the following values:/n/n" +
                "HP: " + HP + "/nAttack: " + attack + "/nDefense: " + defense + "/nSpecial Attack: " + specialAttack +
                "/nSpecial Defense: " + specialDefense + "/nSpeed: " + speed + "/nEvasion: " + evasion +
                "/nAccuracy: " + accuracy);
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Returns the number of points remaining after spending.
     * 
     * Returns PokeWorld.ERROR if the value entered is greater than the
     * total points remaining
     * 
     * @param points The points to be spent
     * @return int The total points spent
     * @see PokemonActor(...)
     * @see PokeWorld.ERROR global final int variable
     */
    public int spendPoints( int points ) {
        if( this.points >= points ) this.points -= points;
        else                        return PokeWorld.ERROR;
        
        return points;
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Gets the current status of this PokemonActor
     * 
     * @return Status The current status of this PokemonActor
     * @see Status enum
     */
    public Status getStatus() {
        return this.status;
    }
    
    /**
     * Gets the text to be displayed for this current PokemonActor's Status
     * 
     * @return String The text to be displayed for this current PokemonActor's Status
     */
    public String getStatusText() {
        if(      this.status == Status.NORMAL )    return this.NAME + " is feeling normal.";
        else if( this.status == Status.BURN )      return this.NAME + " is hurt by its burn!.";
        else if( this.status == Status.FREEZE )    return this.NAME + " is frozen solid!";
        else if( this.status == Status.PARALYSIS ) return this.NAME + " is paralyzed! It can't move!";
        else if( this.status == Status.POISON )    return this.NAME + " is hurt by poison!";
        else if( this.status == Status.SLEEP )     return this.NAME + " is fast asleep.";
        else if( this.status == Status.BOUND )     return this.NAME + " is bound! It hurt itself trying to escape!";
        else                  /*Status.FAINTED*/   return this.NAME + " has fainted!";
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Sets the current status of this PokemonActor
     * 
     * @param status The new status of this PokemonActor
     * @see Status enum
     */
    public void setStatus( Status status ) {
        this.status = status;
    }
    
    /**
     * Get the name of this PokemonActor
     * 
     * @return String The name of this PokemonActor
     */
    public String getName() {
        return NAME;
    }
    
    /**
     * Get the image name for this PokemonActor
     * 
     * @return The image name of this PokemonActor
     */
    public String getImageName() {
        return IMAGE_NAME;
    }
    
    /**
     * Sets a new image for this PokemonActor
     * 
     * @param imageLoc The image path/location to be set. This should be in the 'images'
     *                 folder in Greenfoot
     */
    public void setImageName( String imageLoc ) {
        IMAGE_NAME = imageLoc;
    }
    
    /**
     * Gets the battle image of this PokemonActor
     * 
     * @return String The path and name of this battle image
     */
    public String getBattleImageName() {
        return this.BATTLE_IMAGE;
    }
    
    /**
     * Sets the battle image of this PokemonActor
     * 
     * @param String The path and name of the new battle image
     */
    public void setBattleImageName( String imageLoc ) {
        this.BATTLE_IMAGE = imageLoc;
    }
    
    /**
     * Get the Pokemon type for this PokemonActor
     * 
     * @return String The PokemonActor type of this PokemonActor
     */
    public String getType() {
        return TYPE;
    }
    
    /**
     * Determines whether one of this Pokemon's types matches the given type
     * 
     * @param type The type being compared to this Pokemon's types
     * @return boolean True if this type matches one of this Pokemon's types, false otherwise
     */
    public boolean matchesType( String type ) throws InvalidTypeException {
        String[] types = this.TYPE.split("/");
        if( types.length > 2 )
            throw new InvalidTypeException(
                "Invalid number of Pokemon types. Found " + types.length + " types, but the maximum number of types that " +
                "a Pokemon can have is " + PokeWorld.MAX_POKEMON_TYPES + "." );
        
        for( int i = 0; i < types.length; i++ )
            if( types[i].toUpperCase().equals( type.toUpperCase() ) ) return true;
        
        return false;
    }
    
    /**
     * Get the HP of this PokemonActor at full health
     * 
     * @return int The HP of this PokemonActor AT FULL HEALTH
     */
    public int getHP() {
        return HP;
    }
    
    /**
     * Set the maximum HP of this PokemonActor. Increasing the maximum HP also sets the
     * currentHP to the new maximum
     * 
     * @param HP The HP to set this PokemonActor's HP to
     */
    public void setHP( int HP ) {
        this.HP = HP;
        currentHP = HP;
    }
    
    /**
     * Get the current HP of this PokemonActor
     * 
     * @return int The current HP of this PokemonActor
     */
    public int getCurrentHP() {
        return currentHP;
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Set the current HP of this PokemonActor. Cannot set current HP greater than maximum HP
     * 
     * @param currentHP The currentHP to set this PokemonActor's current HP to
     * @see setStatus( String status )
     */
    public void setCurrentHP( int currentHP ) {
        if( currentHP > this.HP )
            printError("Cannot set current HP greater than maximum HP");
        else
            this.currentHP = currentHP;
        
        if( this.currentHP <= 0 ) setStatus( Status.FAINTED );
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Adds HP to the current HP of PokemonActor. Negative values can be added
     * 
     * @param HP The HP to be added to the current HP of this Pokemon
     * @see setStatus( String status )
     */
    public void addHP( int HP ) {
        this.currentHP += HP;
        if( this.currentHP > this.HP ) this.currentHP = this.HP;
        if( this.currentHP <= 0 )      setStatus( Status.FAINTED );
    }
    
    /**
     * Gets the attack value of this PokemonActor
     * 
     * @return int The attack value of this PokemonActor
     */
    public int getAttack() {
        return attack;
    }
    
    /**
     * Sets the attack value of this PokemonActor. Increasing the maximum attack also
     * sets the currentAttack to the new maximum
     * 
     * @param attack The attack value to set this PokemonActor's attack value to
     */
    public void setAttack( int attack ) {
        this.attack = attack;
        currentAttack = attack;
    }
    
    /**
     * Gets the current attack value of this PokemonActor
     * 
     * @return int The current attack value of this PokemonActor
     */
    public int getCurrentAttack() {
        return currentAttack;
    }
    
    /**
     * Sets the current attack value of this PokemonActor
     * 
     * @param currentAttack The current attack value to set this PokemonActor's current attack
     *                      value to
     */
    public void setCurrentAttack( int currentAttack ) {
        this.currentAttack = currentAttack;
    }
   
    /**
     * Gets the defense value of this PokemonActor
     * 
     * @return int The defense value of this PokemonActor
     */
    public int getDefense() {
        return defense;
    }
    
    /**
     * Sets the defense value of this PokemonActor. Increasing the maximum defense
     * also sets the currentDefense to the new maximum
     * 
     * @param defense The defense value to set this PokemonActor's defense value to
     */
    public void setDefense( int defense ) {
        this.defense = defense;
        currentDefense = defense;
    }
    
    /**
     * Gets the current defense value of this PokemonActor
     * 
     * @return int The current defense value of this PokemonActor
     */
    public int getCurrentDefense() {
        return currentDefense;
    }
    
    /**
     * Sets the current defense value of this PokemonActor
     * 
     * @param currentDefense The current defense value to set this PokemonActor's current
     *                       defense value to
     */
    public void setCurrentDefense( int currentDefense ) {
        this.currentDefense = currentDefense;
    }
   
    /**
     * Gets the special attack value of this PokemonActor
     * 
     * @return int The special attack value of this PokemonActor
     */
    public int getSpecialAttack() {
        return specialAttack;
    }
    
    /**
     * Sets the special attack value of this PokemonActor. Increasing the maximum
     * special attack value also sets the currentSpecialAttack to the new maximum
     * 
     * @param specialAttack The special attack value to set this PokemonActor's special attack value to
     */
    public void setSpecialAttack( int specialAttack ) {
        this.specialAttack = specialAttack;
        currentSpecialAttack = specialAttack;
    }
    
    /**
     * Gets the current special attack value of this PokemonActor
     * 
     * @return int The current special attack value of this PokemonActor
     */
    public int getCurrentSpecialAttack() {
        return currentSpecialAttack;
    }
    
    /**
     * Sets the current special attack value of this PokemonActor
     * 
     * @param currentSpecialAttack The current special attack value to set this PokemonActor's
     *                             current special attack value to
     */
    public void setCurrentSpecialAttack( int currentSpecialAttack ) {
        this.currentSpecialAttack = currentSpecialAttack;
    }
   
    /**
     * Gets the special defense value of this PokemonActor
     * 
     * @return int The special defense value of this PokemonActor
     */
    public int getSpecialDefense() {
        return specialDefense;
    }
    
    /**
     * Sets the special defense value of this PokemonActor. Increasing the maximum
     * special defense value also sets the currentSpecialDefense to the new maximum
     * 
     * @param specialDefense The special defense value to set this PokemonActor's special defense value to
     */
    public void setSpecialDefense( int specialDefense ) {
        this.specialDefense = specialDefense;
        currentSpecialDefense = specialDefense;
    }
   
    /**
     * Gets the current special defense value of this PokemonActor
     * 
     * @return int The current special defense value of this PokemonActor
     */
    public int getCurrentSpecialDefense() {
        return currentSpecialDefense;
    }
    
    /**
     * Sets the current special defense value of this PokemonActor
     * 
     * @param currentSpecialDefense The current special defense value to set this PokemonActor's
     *                              current special defense value to
     */
    public void setCurrentSpecialDefense( int currentSpecialDefense ) {
        this.currentSpecialDefense = currentSpecialDefense;
    }
    
    /**
     * Gets the speed value of this PokemonActor
     * 
     * @return int The speed value of this PokemonActor
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * Sets the speed value of this PokemonActor. Increasing the maximum speed value
     * also sets the currentSpeed to the new maximum
     * 
     * @param speed The speed value to set this PokemonActor's speed value to
     */
    public void setSpeed( int speed ) {
        this.speed = speed;
        currentSpeed = speed;
    }
    
    /**
     * Gets the current speed value of this PokemonActor
     * 
     * @return int The current speed value of this PokemonActor
     */
    public int getCurrentSpeed() {
        return currentSpeed;
    }
    
    /**
     * Sets the current speed value of this PokemonActor
     * 
     * @param currentSpeed The current speed value to set this PokemonActor's current speed value to
     */
    public void setCurrentSpeed( int currentSpeed ) {
        this.currentSpeed = currentSpeed;
    }
   
    /**
     * Gets the evasion value of this PokemonActor
     * 
     * @return int The evasion value of this PokemonActor
     */
    public int getEvasion() {
        return evasion;
    }
    
    /**
     * Sets the evasion value of this PokemonActor. Increasing the maximum evasion
     * value also sets the currentEvasion to the new maximum
     * 
     * @param evasion The evasion value to set this PokemonActor's evasion value to
     */
    public void setEvasion( int evasion ) {
        this.evasion = evasion;
        currentEvasion = evasion;
    }
    
    /**
     * Gets the current evasion value of this PokemonActor
     * 
     * @return int The current evasion value of this PokemonActor
     */
    public int getCurrentEvasion() {
        return currentEvasion;
    }
    
    /**
     * Sets the current evasion value of this PokemonActor
     * 
     * @param currentEvasion The current evasion value to set this PokemonActor's current
     *                       evasion value to
     */
    public void setCurrentEvasion( int currentEvasion ) {
        this.currentEvasion = currentEvasion;
    }
   
    /**
     * Gets the accuracy value of this PokemonActor
     * 
     * @return int The accuracy value of this PokemonActor
     */
    public int getAccuracy() {
        return accuracy;
    }
    
    /**
     * Sets the accuracy value of this PokemonActor. Increasing the maximum accuracy
     * value also sets the currentAccuracy to the new maximum
     * 
     * @param accuracy The accuracy value to set this PokemonActor's accuracy value to
     */
    public void setAccuracy( int accuracy ) {
        this.accuracy = accuracy;
        currentAccuracy = accuracy;
    }
   
    /**
     * Gets the current accuracy value of this PokemonActor
     * 
     * @return int The current accuracy value of this PokemonActor
     */
    public int getCurrentAccuracy() {
        return currentAccuracy;
    }
    
    /**
     * Sets the current accuracy value of this PokemonActor
     * 
     * @param currentAccuracy The current accuracy value to set this PokemonActor's current
     *                        accuracy value to
     */
    public void setCurrentAccuracy( int currentAccuracy ) {
        this.currentAccuracy = currentAccuracy;
    }
    
    /**
     * Gets the total points that this PokemonActor still has left to spend
     * 
     * @return int The total points that are unspent
     */
    public int getCurrentPoints() {
        return points;
    }
    
    /**
     * Sets the total points of this PokemonActor
     * 
     * @param points The total points to set this PokemonActor's points to. These points are used to
     *               level up PokemonActor's skills and abilities
     * @see spendPoints( int points )
     */
    public void setCurrentPoints( int points ) {
        this.points = points;
    }
   
    /**
     * Gets the total experience of this PokemonActor
     * 
     * @return int The total experience of this PokemonActor
     */
    public int getTotalExp() {
        return exp;
    }
    
    /**
     * Gets the remaining experience needed before this PokemonActor levels up
     * 
     * @return int The remaining experience before this PokemonActor levels up
     * @see Pokedex.getExperiencePerLevel()
     */
    public int getRemainingExp() {
        int currentLevel = getCurrentLevel();
        if( currentLevel > 1 )
            return pokedex.getExperiencePerLevel( currentLevel ) - (addedExp - pokedex.getExperiencePerLevel( currentLevel - 1 ) );
        else
            return pokedex.getExperiencePerLevel( currentLevel ) - addedExp;
    }
    
    /**
     * Gets the total gained experience of this PokemonActor
     * 
     * @return int The total gained experience of this PokemonActor
     */
    public int getTotalAddedExp() {
        return addedExp;
    }
    
    /**
     * Sets the total experience of this PokemonActor. The experience set cannot be
     * lower than the original experience of this Pokemon
     * 
     * @param exp The new total experience to set to this PokemonActor's total experience
     * @return boolean True if this Pokemon has leveled up, false otherwise
     * @see evaluateExp( int exp )
     */
    public boolean setTotalExp( int exp ) /*throws InvalidExpException*/ {
        /*if( exp < this.exp )
            throw new InvalidExpException( "Cannot decrease a Pokemon's experience. Tried setting Pokemon's experience to " +
                                          exp + " experience, but current experience is: " + this.exp );*/
        
        return evaluateExp( exp );
    }
       
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Evaluates whether the new exp will level up the PokemonActor or not.
     * If so, adjust points and level and exp. If not, still adjust the
     * total experience
     * 
     * @param exp The new total experience to set this PokemonActor's total experience
     * @return True if this Pokemon has leveled up, false otherwise
     * @see Pokedex.getLevelByExp( int exp )
     * @see PokeWorld.POINTS_GIVEN_ON_LEVEL_UP
     * @see PokemonActor.getCurrentLevel()
     * @see setTotalExp( int exp )
     * @see addExp( int exp )
     */
    protected boolean evaluateExp( int exp ) {
        if( hasLeveled( exp, getCurrentLevel() ) ) {
            int newLevel = pokedex.getLevelByExp( exp );
            points += (newLevel - this.level)*PokeWorld.POINTS_GIVEN_ON_LEVEL_UP;
            this.level = newLevel;
            this.exp = exp;
            this.addedExp += exp;
            return true;
        }
        this.exp = exp;
        return false;
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Adds experience to previous experience of this PokemonActor
     * 
     * @param exp The experience that has been added
     * @return boolean True if this Pokemon has leveled up, false otherwise
     * @see evaluate( int exp )
     * @see setTotalExp( int exp )
     */
    public boolean addExp( int exp ) {
        return evaluateExp( this.exp + exp );
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Determines whether the given exp has resulted in a level up
     * 
     * @param exp The current exp of this PokemonActor
     * @param currentLevel The current level of this PokemonActor
     * @return boolean True if PokemonActor has leveled up, false otherwise
     * @see Pokedex.getExperiencePerLevel( int currentLevel )
     * @see setTotalExp( int exp )
     */
    protected boolean hasLeveled( int exp, int currentLevel ) {
        if( pokedex.getExperiencePerLevel( currentLevel ) <= exp ) return true;
        else                                                       return false;
    }
    
    /**
     * Gets the current level of this PokemonActor
     * 
     * @return int The current level of this PokemonActor
     * @see evaluateExp( int exp )
     * @see getRemainingExp()
     */
    public int getCurrentLevel() {
        return level;
    }
   
    /**
     * Gets the list of this PokemonActor's items
     * 
     * @return ArrayList<Item> The list of items
     */
    public ArrayList<Item> getItems() {
        return items;
    }
    
    /**
     * Gets the list of this PokemonActor's moves
     * 
     * @return ArrayList<Move> The list of moves
     */
    public ArrayList<Move> getMoves() {
        return moves;
    }
    
    /**
     * Gets the current Move being used by this PokemonActor in battle
     * 
     * @return currentMove The current Move being used by this PokemonActor in battle
     */
    public Move getCurrentMove() {
        return currentMove;
    }
    
    /**
     * Sets the current Move being used by this PokemonActor in battle
     * 
     * @param currentMove The current Move that is being used by this PokemonActor in battle
     */
    public void setCurrentMove( Move currentMove ) {
        this.currentMove = currentMove;
    }
    
    /**
     * Gets the index of the Move known, or returns PokeWorld.ERROR if this Pokemon does
     * not know the name of the Move entered
     * 
     * @param moveName The name of the Move to check
     * @return int The index of the Move in the list of Moves, or PokeWorld.ERROR if this
     *             Pokemon does not know the Move entered
     */
    public int getMoveIndex( String moveName ) {
        int totalMoves = moves.size();
        for( int i = 0; i < totalMoves; i++ )
            if( moves.get(i).getName().toUpperCase().equals( moveName.toUpperCase() ) ) return i;
        
        return PokeWorld.ERROR;
    }
    
    /**
     * Prints the list of all the Pokemon moves using the Pokedex
     * 
     * @see Pokedex.printMoves()
     * @see printMessage( String message )
     */
    public void printMoves() {
        String moveList = pokedex.printMoves();
        printMessage( moveList );
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Adds a move to this PokemonActor's list of known moves
     * 
     * @param move The move to add to the list
     * @see PokeWorld.NEW_MOVE_COST
     * @see PokeWorld.MAX_NUMBER_OF_MOVES
     */
    public void addMove( Move move ) throws InvalidPokemonPointsException, InvalidTypeException {
        if( points < PokeWorld.NEW_MOVE_COST )
            throw new InvalidPokemonPointsException(
                "Not enough points to add this move. Requires " + PokeWorld.NEW_MOVE_COST +
                " points to add a new move." +
                "\nCurrently, " + NAME + " has " + points + " points." );
                
        else if( moves.size() == PokeWorld.MAX_NUMBER_OF_MOVES )
            throw new InvalidPokemonPointsException(
                NAME + " has too many moves learned. First delete a move, then add a new move." +
                "\nCurrently, " + NAME + " knows " + moves.size() + " moves, and the max allowed " +
                " number of moves is " + PokeWorld.MAX_NUMBER_OF_MOVES );
        
        else if( !matchesType( move.getType().name() ) && !move.getType().equals("NORMAL") ) {
            throw new InvalidTypeException(
                NAME + " tried to learn a Move that " + NAME + " can't learn!\nPokemon can only learn Moves that " +
                "share the same type as this Pokemon. All Pokemon can learn Normal Move types. This Pokemon's type " +
                "is " + TYPE + ", but the Move trying to be learned is of type " + move.getType() + "." );
        } else {
            points -= PokeWorld.NEW_MOVE_COST;
            moves.add( move );
        }
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Unlearn a move that PokemonActor currently knows. PokemonActor cannot unlearn a move that it
     * does not know, and cannot unlearn a move if it only knows one move
     * 
     * @param moveName The name of the move to unlearn
     * @return boolean True if PokemonActor knew this move and successful unlearned it.
     *                 False if PokemonActor did not know this move. Thus, it could not be unlearned
     */
    public boolean deleteMove( String moveName ) {
        int totalKnownMoves = moves.size();
        if( totalKnownMoves == 1 )
            return false;
        
        for( int i = 0; i < moves.size(); i++ ) {
            Move move = moves.get(i);
            if( move.getName().equals( moveName ) ) {
                moves.remove(i);
                return true;
            }
        }
        
        return false;
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Unlearn a move that PokemonActor currently knows. PokemonActor cannot unlearn a move that it
     * does not know, and cannot unlearn a move if it only knows one move
     * 
     * @param moveName The name of the move to unlearn
     * @return boolean True if PokemonActor knew this move and successful unlearned it.
     *                 False if PokemonActor did not know this move. Thus, it could not be unlearned
     * @see deleteMove( String moveName )
     */
    public boolean deleteMove( int moveNumber ) {
        return deleteMove( moves.get( moveNumber ).getName() );
    }
    
    /**
     * Add an Item to the list of Items. If the Item does not exist in the list,
     * update the quantity of the Item added instead of having two separate indices that
     * have that same item
     * 
     * @param item The item to add to the list
     * @see Item.setQuantity( int quantity )
     * @see Item.getQuantity()
     */
    public void addItem( Item item ) {
        if( hasItem( item ) ) {
            int itemSize = items.size();
            for( int i = 0; i < itemSize; i++ ) {
                if( item.equals( items.get(i) ) ) {
                    items.get(i).setQuantity( items.get(i).getQuantity() + 1 );
                    break;
                }
            }
        } else {
            item.setQuantity( 1 );
            items.add( item );
        }
    }
    
    /**
     * Remove the item from the list of Items or reduce the Item's quantity
     * 
     * @param item The item to remove from the list or reduce the quantity
     */
    public void removeItem( Item item ) {
        if( hasItem( item ) ) {
            int itemSize = items.size();
            for( int i = 0; i < itemSize; i++ ) {
                if( item.equals( items.get(i) ) ) {
                    if( item.getQuantity() > 1 ) items.get(i).setQuantity( items.get(i).getQuantity() - 1 );
                    else                         items.remove(i);
                    break;
                }
            }
        }
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Get the PP of the move. Returns PokeWorld.ERROR if the Pokemon move name does not exist
     * or if the Pokemon does not know this move
     * 
     * @param moveName The name of the move
     * @return int The number of PP this move has
     * @see PokeWorld.ERROR global final int variable
     */
    public int getMovePP( String moveName ) {
        for( int i = 0; i < moves.size(); i++ ) {
            Move move = moves.get(i);
            if( move.getName().equals( moveName ) )
                return moves.get(i).getPP();
        }
        
        return PokeWorld.ERROR;
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Get the PP of the move
     * 
     * @param moveName The name of the move
     * @return int The number of PP this move has
     * @see getMovePP( String moveName )
     */
    public int getMovePP( int moveNumber ) {
        return getMovePP( moves.get( moveNumber ).getName() );
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Get the current PP of the move. Returns PokeWorld.ERROR if the Pokemon move name does not exist
     * or if the Pokemon does not know this move
     * 
     * @param moveName The name of the move
     * @return int The number of PP remaining for this move
     * @see PokeWorld.ERROR global final int variable
     */
    public int getCurrentMovePP( String moveName ) {
        for( int i = 0; i < moves.size(); i++ ) {
            Move move = moves.get(i);
            if( move.getName().equals( moveName ) )
                return moves.get(i).getCurrentPP();
        }
        
        return PokeWorld.ERROR;
    }
    
    /** @@PERMANENT -- DO NOT CHANGE OR OFFICER JENNY WILL CATCH YOU
     * Get the current PP of the move
     * 
     * @param moveName The name of the move
     * @return int The number of PP remaining for this move
     * @see getCurrentMovePP( String moveName )
     */
    public int getCurrentMovePP( int moveNumber ) {
        return getCurrentMovePP( moves.get( moveNumber ).getName() );
    }
    
    /**
     * Gets the total wins that this PokemonActor has
     * 
     * @return int The total wins of this PokemonActor
     */
    public int getTotalWins() {
        return wins;
    }
    
    /**
     * Sets the total wins to a new value
     * 
     * @param wins The new total of wins (should be 1 higher than before)
     */
    public void setTotalWins( int wins ) {
        this.wins = wins;
    }
    
    /**
     * Gets this PokemonActor's Pokedex
     * 
     * @return Pokedex The Pokedex of this PokemonActor
     */
    public Pokedex getPokedex() {
        return pokedex;
    }
    
    /**
     * Gets the total number of times this PokemonActor has evolved
     * 
     * @return int The number of times this PokemonActor has evolved
     */
    public int getTotalEvolutions() {
        return totalEvolutions;
    }
    
    /**
     * Gets the height of this PokemonActor, in feet and inches
     * 
     * @return double The height of this PokemonActor, in feet and inches
     */
    public double getHeight() {
        return height;
    }
    
    /**
     * Sets the height of this PokemonActor, in feet and inches
     * 
     * @param height The height of the PokemonActor
     */
    public void setHeight( double height ) {
        this.height = height;
    }
    
    /**
     * Gets the weight of this PokemonActor, in pounds
     * 
     * @return double The weight of this PokemonActor, in pounds
     */
    public double getWeight() {
        return weight;
    }
    
    /**
     * Sets the weight of this PokemonActor, in pounds
     * 
     * @param weight The weight of the PokemonActor
     */
    public void setWeight( double weight ) {
        this.weight = weight;
    }
    
    /**
     * Gets the description of this PokemonActor
     * 
     * @return String The description of this PokemonActor
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the description of this PokemonActor
     * 
     * @return description The description of the PokemonActor
     */
    public void setDescription( String description ) {
        this.description = description;
    }
    
    /**
     * Gets the current critical hit ratio of this Pokemon
     * 
     * @return double The current critical hit ratio of this Pokemon
     */
    public double getCurrentCriticalHitRatio() {
        return currentCriticalHitRatio;
    }
    
    /**
     * Sets the current critical hit ratio of this Pokemon
     * 
     * @param currentCriticalHitRatio The new current critical hit ratio of this Pokemon
     */
    public void setCurrentCriticalHitRatio( double currentCriticalHitRatio ) {
        this.currentCriticalHitRatio = currentCriticalHitRatio;
    }
    
    /**
     * Gets the critical hit ratio of this Pokemon
     * 
     * @return double The critical hit ratio of this Pokemon
     */
    public double getCriticalHitRatio() {
        return criticalHitRatio;
    }
    
    /**
     * Sets the critical hit ratio of this Pokemon
     * 
     * @param criticalHitRatio The new critical hit ratio of this Pokemon
     */
    public void setCriticalHitRatio( double criticalHitRatio ) {
        this.criticalHitRatio = criticalHitRatio;
    }
    
    /**
     * Gets the number of this Pokemon
     * 
     * @return int The number of this Pokemon
     */
    public int getPokemonNumber() {
        return pokemonNumber;
    }
    
    /**
     * Gets the unique ID of this PokemonActor
     * 
     * @return int The unique ID of this PokemonActor
     */
    public int getUniqueID() {
        return UNIQUE_ID;
    }
    
    /**
     * Determines whether this Pokemon has the Item selected or not
     * 
     * @param item The item to check and see if this Pokemon has already
     * @return boolean True if this Pokemon has this item, false otherwise
     */
    public boolean hasItem( Item item ) {
        for( int i = 0; i < items.size(); i++ )
            if( items.get(i).equals( item ) ) return true;
        
        return false;
    }
    
    /**
     * Gets the Item that the Pokemon is currently holding or is currently using
     * 
     * @return Item The Item that the Pokemon is currently holding or is currently using.
     *              If the Pokemon is not currently holding or using any items, return null
     */
    public Item getCurrentItem() {
        return currentItem;
    }
    
    /**
     * Sets the Item that the Pokemon is currently holding or is currently using
     * 
     * @param item The Item that the Pokemon is holding or using. This can be null if
     *             the Pokemon is not holding or using any items
     */
    public void setCurrentItem( Item currentItem ) {
        this.currentItem = currentItem;
    }
    
    /**
     * Prints a message to the screen
     * 
     * @param message The message to print
     * @see printError( String message )
     */
    @Deprecated
    protected void printMessage( String message ) {
        printError( message );
    }
    
    /**
     * Print an error message to the screen
     * 
     * @param message The error message to print
     * @see World.showText( String message, int x, int y )
     * @see PokemonActor(...)
     * @see printMessage( String message )
     */
    @Deprecated
    protected void printError( String message ) {
        World w = getWorld();
        w.showText( message, w.getWidth()/2, w.getHeight()/2 );
    }
}
