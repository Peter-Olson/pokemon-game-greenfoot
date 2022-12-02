import greenfoot.*;
import java.util.ArrayList;

/**
   Pokemon.java
   
   This interface includes all of the required methods that a Pokemon must have
   
   Each of these methods must be found in your Pokemon's class. Most of these
   methods are 'getters' and 'setters'. Getters get field properties of your
   Pokemon, while setters set field properties of your Pokemon.
   
   Each Pokemon should at minimum have the following fields defined as
   global private variables within their class:
   
   * Name of Pokemon
   * Image Location
   * Battle Image Location
   * Pokemon Type (Normal, Fire, Water, Electric, Grass, Ice, Fighting, Poison,
                   Ground, Flying, Psychic, Bug, Rock, Ghost, Dragon, or Dark
                   types available)
   * Hit Points (HP)
   * Attack
   * Defense
   * Special Attack
   * Special Defense
   * Speed
   * Evasion
   * Accuracy
   * 
   * And all current stats of these fields
   * 
   * Level Up Points
   * Experience (EXP)
   * Level #
   * ArrayList<Move> moves ( The moves that are available to learn depend on
                               Pokemon type. See Pokedex.java for details )
   * ArrayList<Item> items
   * Total Battle Wins
   * Status
   * Number of evolutions
   * 
   * Height (of Pokemon)
   * Weight (of Pokemon)
   * Description
   * 
   * Critical hit ratio (and current)
   
   @author Peter Olson
   @version 3/17/22
   @see PokemonActor.java
*/
public interface Pokemon {

    public enum Status {
        NORMAL, BURN, FREEZE, PARALYSIS, POISON, SLEEP, BOUND, CONFUSION, FAINTED;
    }
    
    public boolean isTouchingPokemon( Class cls );
    public Actor getOneIntersectingPokemon( Class cls );
    
    /* @@@@@@@@@@@@@@@@@@@@@ HW 1 -- Getters and Setters for Fields -- @@@@@@@@@@@@@@@@@@@@@ */
    public String getName();
    public String getImageName();
    public void setImageName( String imageLoc );
    public String getBattleImageName();
    public void setBattleImageName( String imageLoc );
    
    public Status getStatus();
    public void setStatus( Status status );
    
    public String getType();
   
    public int getHP();
    public void setHP( int HP );
    public int getCurrentHP();
    public void setCurrentHP( int currentHP );
    public void addHP( int HP );
    
    public int getAttack();
    public void setAttack( int attack );
    public int getCurrentAttack();
    public void setCurrentAttack( int currentAttack );
    
    public int getDefense();
    public void setDefense( int defense );
    public int getCurrentDefense();
    public void setCurrentDefense( int currentDefense );
    
    public int getSpecialAttack();
    public void setSpecialAttack( int specialAttack );
    public int getCurrentSpecialAttack();
    public void setCurrentSpecialAttack( int currentSpecialAttack );
    
    public int getSpecialDefense();
    public void setSpecialDefense( int specialDefense );
    public int getCurrentSpecialDefense();
    public void setCurrentSpecialDefense( int currentSpecialDefense );
    
    public int getSpeed();
    public void setSpeed( int speed );
    public int getCurrentSpeed();
    public void setCurrentSpeed( int currentSpeed );
    
    public int getEvasion();
    public void setEvasion( int evasion );
    public int getCurrentEvasion();
    public void setCurrentEvasion( int currentEvasion );
    
    public int getAccuracy();
    public void setAccuracy( int accuracy );
    public int getCurrentAccuracy();
    public void setCurrentAccuracy( int currentAccuracy );
    
    public int getCurrentPoints();
    public void setCurrentPoints( int points );
   
    public int getTotalExp();
    public int getRemainingExp();
    public boolean setTotalExp( int exp ) /*throws InvalidExpException*/;
    public boolean addExp( int exp );
    
    public int getCurrentLevel();
   
    public ArrayList<Item> getItems();
    public Item getCurrentItem();
    public void setCurrentItem( Item currentItem );
    
    public ArrayList<Move> getMoves();
    public Move getCurrentMove();
    public void setCurrentMove( Move currentMove );
    public void addMove( Move move ) throws InvalidPokemonPointsException, InvalidTypeException;
    public boolean deleteMove( String moveName );
    public boolean deleteMove( int moveNumber );
    
    public int getMovePP( String moveName );
    public int getMovePP( int moveNumber );
    public int getCurrentMovePP( String moveName );
    public int getCurrentMovePP( int moveNumber );
  
    public int getTotalAddedExp();
    public int getTotalWins();
    public void setTotalWins( int wins );
    public int getTotalEvolutions();
    
    public Pokedex getPokedex();
    
    public double getHeight();
    public double getWeight();
    public void setHeight( double height );
    public void setWeight( double weight );
    public String getDescription();
    public void setDescription( String description );
    public int getPokemonNumber();
    
    public double getCurrentCriticalHitRatio();
    public void setCurrentCriticalHitRatio( double currentCriticalHitRatio );
    public double getCriticalHitRatio();
    public void setCriticalHitRatio( double criticalHitRatio );
    
    /**
        Exception class related to errors with the allocation of experience
    *//* @@FIX: This Exception had to be commented out both in this interface and in PokemonActor due to an error with
    *           OneDrive not syncing files correctly on Windows 10/11.... this could be fixed by moving this project
    *           off OneDrive
    public class InvalidExpException extends Exception {

        /**
            Throw new exception

            @param errorMessage The message to print
        *//*
        public InvalidExpException( String errorMessage ) {
            super( errorMessage );
        }

    }*/

    /**
        Exception class related to errors with the allocation of Pokepoints for
        the creation of a new Pikachu object
    */
    public class InvalidPokemonPointsException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidPokemonPointsException( String errorMessage ) {
            super( errorMessage );
        }
      
    }    
   
    /**
        Exception class related to a Pokemon having less than PokeWorld.MIN_NUMBER_OF_MOVES
        known move or more than PokeWorld.MAX_NUMBER_OF_MOVES moves
    */
    public class InvalidMoveTotalException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidMoveTotalException( String errorMessage ) {
            super( errorMessage );
        }
      
    }
    
    /**
        Exception class related to Pokemon values being too low or too high
    */
    public class InvalidPokemonValuesException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidPokemonValuesException( String errorMessage ) {
            super( errorMessage );
        }
      
    }
    
    /**
        Exception class related to a Pokemon trying to learn a Move that has a different type than this Pokemon's type.
        Or related to an invalid type entry, if the type does not exist or if the type formatting is incorrect
    */
    public class InvalidTypeException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidTypeException( String errorMessage ) {
            super( errorMessage );
        }
      
    }
    
    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ END OF HW 1 TASKS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
}


