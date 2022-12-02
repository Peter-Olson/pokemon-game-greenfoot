import greenfoot.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Pokedex.java
 * 
 * Pokedex is a class that is used to quickly reference information within the Pokemon world.
 * 
 * A pokedex can be used in battle, or outside of battle, and is useful in finding information
 * about other Pokemon, about your Pokemon, about Pokemon types, about Pokemon moves, about
 * Pokemon leveling information, and more.
 * 
 * A pokedex can also be used to catalog the different Pokemon encountered during Pokebattles
 * 
 * --------------------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIELDS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC FIELDS
 * --------------------------------------------------------------------
 * - TOTAL_FIELDS_FOR_MOVE : Has the total fields per move found in the POKEMON_MOVES_FILE
 * - INVALID_MOVE          : Used to label a Move that is not valid
 * 
 * - SUPER_EFFECTIVE       : The multiplier for move/type combinations that are super-effective
 * - REG_EFFECTIVE         : The multiplier for move/type combos that have regular effectivity
 * - NOT_VERY_EFFECTIVE    : The multiplier for move/type combos that are not very effective
 * - NO_EFFECT             : The multiplier for move/type combos that have no effect
 * 
 * - POKEMON_POKEDEX_LIST_LOC : The file path for the pokedex pokemon number list
 * 
 * PRIVATE FIELDS
 * --------------------------------------------------------------------
 * - experiencePerLevel : A list that holds that experience required to level up at each level
 * - MAX_LEVEL          : The maximum level a Pokemon can reach
 * - FIRST_LEVEL_UP_EXP : The initial experience needed to reach level 2
 * 
 * - POKEMON_MOVES_FILE : The file path and name where the list of pokemon moves are stored
 * - MOVE_FIELD_SIZE    : The number of properties that each move has in the POKEMON_MOVES_FILE
 * - moveList           : The list of moves available within this world
 * - EMPTY              : Holds the token used for entries with no data
 * 
 * - pokemonTypes       : The list of Pokemon types available within this world
 * 
 * - effectivenessChart : The chart of effectivity for cross-combinations of Pokemon / Move types
 * 
 * - pokemonDiscovered  : The list of Pokemon that have been discovered by this Pokemon
 * 
 * - pokedexFileName    : The file path for this Pokemon's pokedex's discovered pokemon information
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CONSTRUCTORS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * - Pokedex()          : Create a basic Pokedex
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ METHODS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC METHODS
 * ---------------------------------------------------------------------
 * - getPokedexFileName()                         : Gets the name of this Pokedex's pokemon information text file
 * - setPokedexFileName( String pokedexFileName ) : Sets the name of this Pokedex's pokemon information text file 
 * 
 * - getExperiencePerLevel( int level ) : Gets the experience required to level up at
 *                                        this level
 *                                        
 * - getExperienceFromBattle( PokemonActor winner, PokemonActor loser ) : Gets the total experience gained from this battle
 *                                                                        between the two Pokemon
 * 
 * - getLevelByExp( int exp )           : Gets the current level based on the experience
 * 
 * - getMoves()                         : Gets a list of the moves in this world
 * - printMoves()                       : "Prints" the list of moves in this world (by returning a String)
 * - getMoveFromName( String name )     : Gets a Move object from the given name
 * - getMovesFromType( String type )    : Gets a list of Moves given the type of Move entered
 * - moveExists( String name )          : Determines whether the given Move name exists within the standard
 *                                        collection of Moves in the POKEMON_MOVES_FILE
 * 
 * - getEffectiveness( String attackType, String defendingPokemonType ) : Gets the attack multipler based on the type of
 *                        the attacking move and the type of the defending Pokemon
 * - isPokemonType( String type )       : Tells whether the given type is a valid Pokemon type or not
 * - getPokemonTypes()                  : Returns a list of the valid Pokemon types
 * 
 * - getPokemonNumber( String pokemonName )    : Gets the number of this Pokemon, or makes a new number if it is not in the
 *                                               Pokedex
 * - addPokemon( String pokemonName )          : Adds a Pokemon to the Pokedex list
 * - isPokemonDiscovered( String pokemonName ) : Determines whether the given Pokemon has been seen yet (in the Pokedex)
 * 
 * PRIVATE METHODS
 * ---------------------------------------------------------------------
 * - setPokemonDiscovered()  : Sets the original 150 Pokemon into the Pokedex as being undiscovered
 * 
 * - setExperiencePerLevel() : Sets the experience required to level up per level, from
 *                             level 1 all the way to level MAX_LEVEL
 * 
 * - initMoves()             : Creates the list of moves using the data in POKEMON_MOVES_FILE
 * 
 * - SOPln( String str )     : Prints a String to the console
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PUBLIC CLASSES @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * InvalidMovesFileException : Used for errors relating to the POKEMON_MOVES_FILE
 * InvalidMoveException      : Used for errors relating to invalid move entry
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PRIVATE CLASSES @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PropertyEffectData        : Used for storing data for Moves with special propertyies/effects
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * --------------------------------------------------------------------------------------------
 * 
 * @author Peter Olson
 * @version 4/8/22
 */
public class Pokedex {
    //Holds the amounts of experience per level, from 1 to MAX_LEVEL
    private int[] experiencePerLevel;
    
    private final int MAX_LEVEL = 100;
    //starting amount of exp to get from level 1 to 2
    private final int FIRST_LEVEL_UP_EXP = 10;
    
    private final String POKEMON_MOVES_FILE = PokeWorld.POKEMON_MOVES_FILE;
    //vv The total number of attributes per line in the POKEMON_MOVES_FILE vv
    public final int TOTAL_FIELDS_FOR_MOVE = PokeWorld.TOTAL_FIELDS_FOR_MOVE;
    private ArrayList<Move> moveList;
    private final String EMPTY = "NA";
    public static final String INVALID_MOVE = "MOVE_NOT_FOUND"; //see getMoveFromName( String name )
    
    private final String[] pokemonTypes = {"NORMAL","FIRE","WATER","ELECTRIC","GRASS","ICE",
                                           "FIGHTING","POISON","GROUND","FLYING","PSYCHIC",
                                           "BUG","ROCK","GHOST","DRAGON","DARK"};
    /*
     * This chart runs parallel with the pokemonTypes type list
     * 
     * 0 = no change in attack effectiveness (100% damage)
     * 1 = not very effective (50% damage)
     * 2 = super effective (200% damage)
     * 3 = no effect (0% damage )
     */
    private final int[][] effectivenessChart = {
    /*Def> N F W E G I F P G F P B R G D D */
     /*N*/{0,0,0,0,0,0,0,0,0,0,0,0,1,3,0,0},
     /*F*/{0,1,1,0,2,2,0,0,0,0,0,2,1,0,1,0},
     /*W*/{0,2,1,0,1,0,0,0,2,0,0,0,2,0,1,0},
     /*E*/{0,0,2,1,1,0,0,0,3,2,0,0,0,0,1,0},
     /*G*/{0,1,2,0,1,0,0,1,2,1,0,1,2,0,1,0},
     /*I*/{0,1,1,0,2,1,0,0,2,2,0,0,0,0,2,0},
     /*F*/{2,0,0,0,0,2,0,1,0,1,1,1,2,3,0,2},
     /*P*/{0,0,0,0,2,0,0,1,1,0,0,0,1,1,0,0},
     /*G*/{0,2,0,2,1,0,0,2,0,3,0,1,2,0,0,0},
     /*F*/{0,0,0,1,2,0,2,0,0,0,0,2,1,0,0,0},
     /*P*/{0,0,0,0,0,0,2,2,0,0,1,0,0,0,0,3},
     /*B*/{0,1,0,0,2,0,1,1,0,1,2,0,0,1,0,2},
     /*R*/{0,2,0,0,0,2,1,0,1,2,0,2,0,0,0,0},
     /*G*/{3,0,0,0,0,0,0,0,0,0,2,0,0,2,0,1},
     /*D*/{0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0},
     /*D*/{0,0,0,0,0,0,1,0,0,0,2,0,0,2,0,1}
    /* ^ Atk */
        };
    
    //Constants for effectivity
    public static final double SUPER_EFFECTIVE    = 2.0;
    public static final double REG_EFFECTIVE      = 1.0;
    public static final double NOT_VERY_EFFECTIVE = 0.5;
    public static final double NO_EFFECT          = 0.0;
                                   
    //Contains all the pokemon that have been seen or not seen by this Pokemon, categorized by number and name
    private LinkedHashMap<String, Boolean> pokemonDiscovered;
    public static String POKEMON_POKEDEX_LIST_LOC = "pokemonNumberList.txt";
    private String pokedexFileName;
    
    /**
     * Create an empty Pokedex and initialize basic information
     * 
     * @see setExperiencePerLeve()
     * @see setPokemonDiscovered()
     * @see initMoves()
     */
    public Pokedex() {
        experiencePerLevel = new int[ MAX_LEVEL ];
        setExperiencePerLevel();
        
        pokemonDiscovered = new LinkedHashMap<String, Boolean>();
        setPokemonDiscovered();
        
        try {
            initMoves();
        } catch( InvalidMovesFileException e ) {
            SOPln( e.getMessage() );
        }
    }
    
    /**
     * Set the list of pokemon that have been discovered. Initially, all Pokemon
     * from Generation 1 have not been discovered
     * 
     * @see Pokedex()
     * @see HashMap.put( Key key, Value value )
     */
    private void setPokemonDiscovered() {
        Scanner scanner = null;
        try {
            scanner = new Scanner( new File( POKEMON_POKEDEX_LIST_LOC ) );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        while( scanner.hasNextLine() ) {
            String line = scanner.nextLine();
            String name = line.split("@")[1];
            pokemonDiscovered.put( name, false );
        }
        
        scanner.close();
    }
    
    /**
     * Gets the file name for this Pokedex's discovered pokemon information text file
     * 
     * @return String The file name for this Pokedex's discovered pokemon information text file
     */
    public String getPokedexFileName() {
        return this.pokedexFileName;
    }
    
    /**
     * Sets the file name for this Pokedex's discovered pokemon information text file
     * 
     * @param pokedexFileName The file name where this Pokemon's Pokedex's pokemon information text file is located
     */
    public void setPokedexFileName( String pokedexFileName ) {
        this.pokedexFileName = pokedexFileName;
    }
    
    /**
     * Gets the file name for the Move text file list
     * 
     * This probably shouldn't be in this class, but whatever
     * 
     * @return String The name of the Move text file list
     */
    public String getPokedexMoveFileName() {
        return POKEMON_MOVES_FILE;
    }
    
    /**
     * Determines whether this Pokemon is one of the original 150 Pokemon
     * from Generation 1 or not. If it is, return the number. If it exists
     * in the list, but is not from the original 150, return the number. If
     * it does not exist, return the number after the last number in this list
     * 
     * @param pokemonName The name of the Pokemon being searched for
     * @return int The number of this Pokemon, or the number after the last number
     *             if this Pokemon is not found within the list
     */
    public int getPokemonNumber( String pokemonName ) {
        Scanner scanner = null;
        try {
            scanner = new Scanner( new File( POKEMON_POKEDEX_LIST_LOC ) );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        String lastNumber = "";
        while( scanner.hasNextLine() ) {
            String line = scanner.nextLine();
            String[] tokens = line.split("@");
            lastNumber = tokens[0];
            if( tokens[1].equals( pokemonName ) ) return Integer.parseInt( lastNumber );
        }
        
        int lastNumberValue = Integer.parseInt( lastNumber ) + 1;
        
        scanner.close();
        
        return lastNumberValue;
    }
    
    /**
     * Updates the list of discovered Pokemon based on the Pokemon added
     * 
     * @param pokemonName The name of the added Pokemon
     * @see LinkedHashMap.get( Object key )
     * @see LinkedHashMap.replace( Key key, Value oldValue, Value newValue )
     * @see LinkedHashMap.put( Key key, Value value )
     */
    public void addPokemon( String pokemonName ) {
        if( pokemonDiscovered.get( pokemonName ) != null ) {
            pokemonDiscovered.replace( pokemonName, false, true );
        } else {
            pokemonDiscovered.put( pokemonName, true );
        }
    }
    
    /**
     * Determines whether this pokedex contains the given Pokemon (if it is
     * registered within the original Gen 1 Pokemon, or if it has been seen before)
     * 
     * @param pokemonName The name of the Pokemon that is being searched for
     * @return boolean True if the Pokedex has seen this Pokemon, false otherwise
     * @see LinkedHashMap.get( Object key )
     */
    public boolean isPokemonDiscovered( String pokemonName ) {
        if(      pokemonDiscovered.get( pokemonName ) == null ) return false;
        else if( !pokemonDiscovered.get( pokemonName ) )        return false;
        else                                                    return true;
    }
    
    /**
     * Determines whether this Pokedex has the information for the given Pokemon
     * 
     * @param fileName The file location of the Pokedex's information
     * @param pokemonName The name of the Pokemon in question
     * @return True if the file has this Pokemon's information already, false otherwise
     * @see PokeBattle.addToPokedex( PokemonActor pokemon, PokemonActor[] pokemonList )
     */
    public boolean hasPokemonInfo( String fileName, String name ) {
        Scanner scanner = null;
        try {
            scanner = new Scanner( new File( fileName ) );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        while( scanner.hasNextLine() ) {
            String line = scanner.nextLine();
            if( line.equals("@@@") && scanner.hasNextLine() ) {
                if( name.toLowerCase().equals( scanner.nextLine().toLowerCase() ) ) return true;
            }
        }
        
        scanner.close();
        
        return false;
    }
    
    /**
     * Determines whether the POKEMON_POKEDEX_LIST_LOC has a Pokemon listed
     * at the given Pokemon number
     * 
     * @param pokemonNumber The number of the Pokemon
     * @return True if the Pokemon number exists, false otherwise
     */
    public boolean hasPokemonNumber( int pokemonNumber ) {
        Scanner scanner = null;
        try {
            scanner = new Scanner( new File( POKEMON_POKEDEX_LIST_LOC ) );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        boolean hasNumber = false;
        while( scanner.hasNextLine() ) {
            String line = scanner.nextLine();
            String[] tokens = line.split("@");
            if( tokens[0].equals( pokemonNumber + "" ) ) return true;
        }
        
        return false;
    }
    
    /**
     * Get the list of Pokemon types
     * 
     * @return String[] The list of Pokemon types
     */
    public String[] getPokemonTypes() {
        return pokemonTypes;
    }
    
    /**
     * Determines whether the String entered is a Pokemon type
     * 
     * @param String The token in question
     * @return boolea True if the token is a Pokemon type, false otherwise
     */
    public boolean isPokemonType( String type ) {
        for( int i = 0; i < this.pokemonTypes.length; i++ ) {
            if( this.pokemonTypes[i].equals( type ) ) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Finds the attacking move types effectiveness against the defending
     * Pokemon type
     * 
     * @param String Attacking move type
     * @param String Defending Pokemon type
     * @return double The effectiveness of the attack.
     * 
     *                REG_EFFECTIVE = no multiplier
     *                SUPER_EFFECTIVE = 200% damage
     *                NOT_VERY_EFFECTIVE = 50% damage
     *                NO_EFFECT = no damage
     */
    public double getEffectiveness( String attackType, String defendingPokemonType ) throws InvalidMoveException {
        attackType = attackType.toUpperCase();
        defendingPokemonType = defendingPokemonType.toUpperCase();
        int attackIndex, defendIndex;
        attackIndex = defendIndex = PokeWorld.ERROR;
        for( int i = 0; i < pokemonTypes.length; i++ ) {
            if( pokemonTypes[i].equals( attackType ) )           attackIndex = i;
            if( pokemonTypes[i].equals( defendingPokemonType ) ) defendIndex = i;
        }
        
        if( attackIndex == PokeWorld.ERROR )
            throw new InvalidMoveException("Invalid move entered. Found invalid entry called '" + attackType + "'");
        if( defendIndex == PokeWorld.ERROR )
            throw new InvalidMoveException("Invalid move entered. Found invalid entry called '" + defendingPokemonType + "'");
        
        int effectiveness = effectivenessChart[ attackIndex ][ defendIndex ];
        
        double convertedEffectiveness = REG_EFFECTIVE;
        if(      effectiveness == (int)SUPER_EFFECTIVE )          convertedEffectiveness = SUPER_EFFECTIVE;
        else if( effectiveness == (int)(NOT_VERY_EFFECTIVE * 2) ) convertedEffectiveness = NOT_VERY_EFFECTIVE;
        else if( effectiveness == (int)(NO_EFFECT + 3) )          convertedEffectiveness = NO_EFFECT;
        
        return convertedEffectiveness;
    }
    
    /**
     * Gets the text interpretation of the effectiveness of the attacking move type
     * against the Pokemon Move type
     * 
     * @param attackType
     * @param defendingPokemonType
     * @return String The text interpretation of the multiplier. The possible returns include the following:
     *                1. "It has a regular effect."
     *                2. "It's super-effective!"
     *                3. "It's not very effective..."
     *                4. "There is no effect."
     * @see getEffectiveness( String attackType, String defendingPokemonType )
     */
    public String getEffectivenessText( String attackType, String defendingPokemonType ) {
        double effectiveness = 1.0;
        try {
            effectiveness = getEffectiveness( attackType, defendingPokemonType );
        } catch( InvalidMoveException e ) {
            e.printStackTrace();
        }
        
        if(      effectiveness == REG_EFFECTIVE )      return "It has a regular effect.";
        else if( effectiveness == SUPER_EFFECTIVE )    return "It's super-effective!";
        else if( effectiveness == NOT_VERY_EFFECTIVE ) return "It's not very effective...";
        else                                           return "There is not effect.";
    }
    
    /**
     * Set the linear-scale model for leveling up
     * 
     * (From Level 2): 10, 20, 40, 70, 110, 160, 220, 290, 370, 460,
     *                 560, 670, 790, 920, 1060, 1210, 1370, 1540, 1720, 1910...
     * 
     * @see FIRST_LEVEL_UP_EXP
     * @see Pokedex(...)
     */
    private void setExperiencePerLevel() {
        experiencePerLevel[0] = FIRST_LEVEL_UP_EXP;
        for( int i = 1; i < experiencePerLevel.length; i++ ) {
            experiencePerLevel[i] = experiencePerLevel[ i-1 ] + FIRST_LEVEL_UP_EXP * i;
        }
    }
    
    /**
     * Gets the experience required to level up for this level
     * 
     * @param level The level to get the total experience required to level up
     * @return int The total experience required to level up at this level
     * @see Pokemon.getRemainingExp()
     */
    public int getExperiencePerLevel( int level ) {
        return experiencePerLevel[ level - 1 ];
    }
    
    /**
     * Gets the current level based on the experience input
     * 
     * @param exp The current experience of this Pokemon
     * @return int The corresponding level to this experience
     * @see Pokemon.setTotalExp( int exp )
     */
    public int getLevelByExp( int exp ) {
        for( int i = experiencePerLevel.length - 1; i >= 0; i-- ) {
            if( experiencePerLevel[i] < exp ) return i+1;
        }
        
        return 1;
    }
    
    /**
     * Get experience from the PokemonActor that has been defeated
     * 
     * @param winner The victorious PokemonActor
     * @param loser The PokemonActor that has lost
     * @return int The amount of experience gained
     * @see PokemonActor.getImageName()
     * @see GreenfootImage.getWidth(), GreenfootImage.getHeight()
     * @see PokemonActor.getCurrentLevel()
     * @see PokemonActor.getTotalEvolutions()
     */
    public int getExperienceFromBattle( PokemonActor winner, PokemonActor loser ) {
        GreenfootImage img = null;
        try {
            img = new GreenfootImage( loser.getImageName() );
        } catch( IllegalArgumentException e ) {
            e.printStackTrace();
        }
        int w = img.getWidth();
        int h = img.getHeight();
        int b = w > h ? w : h;
        
        int otherLevel = loser.getCurrentLevel();
        b += otherLevel * (loser.getTotalEvolutions() + 1);
        
        double partOne = (double)(b * otherLevel)/5.0;
        double num = ((2.0 * (double)(otherLevel)) + 10.0)/((double)otherLevel + (double)(winner.getCurrentLevel()) + 10.0 );
        double partTwo = Math.pow( num, 2.5 );
        
        return (int)(partOne * partTwo);
    }
    
    /**
     * Creates the list of moves for this world
     * 
     * @see Pokedex(...)
     * @see InvalidMovesFileException()
     */
    private void initMoves() throws InvalidMovesFileException {
        File moveListFile = new File( POKEMON_MOVES_FILE );
        Scanner movesFileScanner = null;
        try {
            movesFileScanner = new Scanner( moveListFile );
        } catch( IOException e ) {
            e.printStackTrace();
        }
        
        int lineNumber = 1;
        moveList = new ArrayList<Move>();
        while( movesFileScanner.hasNextLine() ) {
            String line = movesFileScanner.nextLine();
            String[] moveInfoList = line.split("@");
            if( moveInfoList.length < PokeWorld.MIN_FIELDS_FOR_MOVE )
                throw new InvalidMovesFileException(
                    "Found an incorrect line on line #" + lineNumber + ". Expected at least " +
                    PokeWorld.MIN_FIELDS_FOR_MOVE + " total values, but found " + moveInfoList.length +
                    " total values.\n\nLine: " + line );
            
            //parse values
            String name = moveInfoList[0];
            String type = moveInfoList[1];
            String moveType = moveInfoList[2];
            int power, accuracy, pp;
            power = accuracy = pp = 0;
            try {
                power = Integer.parseInt( moveInfoList[3] );
            } catch( NumberFormatException e ) {
                if( moveInfoList[3].equals( EMPTY ) ) power = 0;
                else e.printStackTrace();
            }
            try {
                accuracy = Integer.parseInt( moveInfoList[4] );
            } catch( NumberFormatException e ) {
                if( moveInfoList[4].equals( EMPTY ) ) accuracy = 0;
                else e.printStackTrace();
            }
            try {
                pp = Integer.parseInt( moveInfoList[5] );
            } catch( NumberFormatException e ) {
                if( moveInfoList[5].equals( EMPTY ) ) pp = 0;
                else e.printStackTrace();
            }
            String description = moveInfoList[6];
            
            //Finding and setting properties
            int index = 7;

            PropertyEffectData dataObject = getPropertyData( index, moveInfoList );
            
            this.moveList.add(
                new Move( name, type, moveType, power, accuracy, pp,
                          description, true, dataObject.propertyList, dataObject.metaData, dataObject.location )
                );
            
            ++lineNumber;
        }
    }
    
    /**
     * Gets a PropertyEffectData object that parses the property data from a String. The String being parsed has the following
     * format:
     * 
     * <propertyNumber>:<acronym>:<metadata>
     * 
     * @param index The property index
     * @param moveInfoList The list of properties for the Move
     * @return PropertyEffectData An object holding the properties for this Move object
     * @see initMoves()
     * @see PropertyEffectData()
     */
    private PropertyEffectData getPropertyData( int index, String[] moveInfoList ) {
        boolean[] propertyList = new boolean[ TOTAL_FIELDS_FOR_MOVE ];
        double[] metaData = new double[ TOTAL_FIELDS_FOR_MOVE ];
        String location = "";
        
        for( ; index < moveInfoList.length; index++ ) {
            String[] moveMetaData = moveInfoList[index].split(":");
            int propertyNumber = Integer.parseInt( moveMetaData[0] );
            propertyList[ propertyNumber ] = true;
            if( propertyNumber != 30 )
                metaData[ propertyNumber ] = Double.parseDouble( moveMetaData[2] );
            
            if( propertyNumber == 30 ) location = moveMetaData[2];
        }
        
        return new PropertyEffectData( propertyList, metaData, location );
    }
    
    /**
     * Get the list of Pokemon moves
     * 
     * @return ArrayList<Move>
     */
    public ArrayList<Move> getMoves() {
        return moveList;
    }
    
    /**
     * Print the list of Pokemon moves
     * 
     * @return The detailed list of moves
     */
    public String printMoves() {
        String list = "";
        for( Move move: moveList ) {
            list += "Name:        " + move.getName()        + "\n";
            list += "Type:        " + move.getType()        + "\n";
            list += "Move Type:   " + move.getCategory()    + "\n";
            list += "Power:       " + move.getPower()       + "\n";
            list += "Accuracy:    " + move.getAccuracy()    + "\n";
            list += "PP:          " + move.getPP()          + "\n";
            list += "Description: " + move.getDescription() + "\n";
        }
        
        return list;
    }
    
    /**
     * Finds and creates a Move from the Move.pokemonMoves text file given the name
     * of the move
     * 
     * @param name The name of the move
     * @return Move The Move object that is found from the Move.pokemonMoves
     */
    public Move getMoveFromName( String name ) throws InvalidMovesFileException {
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
            
            if( tokens[0].equals( name ) ) {
                String type                    = tokens[1];
                String moveType                = tokens[2];
                int power    = tokens[3].equals("NA") ? -1 : Integer.parseInt( tokens[3] );
                int accuracy = tokens[4].equals("NA") ? -1 : Integer.parseInt( tokens[4] );
                int pp       = tokens[5].equals("NA") ? -1 : Integer.parseInt( tokens[5] );
                String description             = tokens[6];
                
                int index = 7;
                PropertyEffectData dataObject = getPropertyData( index, tokens );
                
                return new Move( name, type, moveType, power, accuracy, pp, description, true,
                                 dataObject.propertyList, dataObject.metaData, dataObject.location );
                
            }
            
            ++lineNumber;
        }
        
        return new Move( INVALID_MOVE, "", "", 0, 0, 0, "" );
    }
    
    /**
     * Determines whether the Move exists based on the name entered
     * 
     * @param name The name of the move
     * @return boolean True if the Move exists within the POKEMON_MOVES_FILE, false otherwise
     * @see Move.checkPoints()
     * @see getMoveFromName( String name )
     * @see InvalidMovesFileException
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
     * Get a list of Moves that share the given type of Move
     * 
     * @param type The type of Move to get
     * @return ArrayList<Move> The list of Moves that are of the given type
     * @see getPropertyData( int index, String[] stringData )
     */
    public ArrayList<Move> getMovesFromType( String type ) throws InvalidMovesFileException {
        type = type.toUpperCase();
        
        ArrayList<Move> listOfMoves = new ArrayList<Move>();
        
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
            
            if( tokens[1].equals( type ) ) {
                String name                    = tokens[0];
                String moveType                = tokens[2];
                int power    = Integer.parseInt( tokens[3] );
                int accuracy = Integer.parseInt( tokens[4] );
                int pp       = Integer.parseInt( tokens[5] );
                String description             = tokens[6];
                
                int index = 7;
                PropertyEffectData dataObject = getPropertyData( index, tokens );
                
                listOfMoves.add( new Move( name, type, moveType, power, accuracy, pp, description, true,
                                           dataObject.propertyList, dataObject.metaData, dataObject.location ) );
                
            }
            
            ++lineNumber;
        }
        
        return listOfMoves;
    }
    
    /**
     * A structure to hold the data from a File for a Move that has special properties or effects
     */
    private class PropertyEffectData {
        public boolean[] propertyList;
        public double[] metaData;
        public String location;
        
        /**
         * Create a PropertyEffectData object for holding property effect information for a Move
         * 
         * @param propertyList The list of properties showing whether the property is active or not
         * @param metaData The list of metadata that is associated with each property
         * @param location The location of the Move. By default, a Move's location is an empty String
         */
        public PropertyEffectData( boolean[] propertyList, double[] metaData, String location ) {
            this.propertyList = propertyList;
            this.metaData = metaData;
            this.location = location;
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
        Exception class related to an invalid move entry
    */
    public class InvalidMoveException extends Exception {
      
        /**
            Throw new exception
         
            @param errorMessage The message to print
        */
        public InvalidMoveException( String errorMessage ) {
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
