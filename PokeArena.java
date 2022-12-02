import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The arena where Pokemon run around. When they run into each other, they engage in battle
 * 
 * PokeWorld control the flow state of the game. The PokeBattle arena checks Pokemon using
 * OfficerJenny, runs animations, images, and sounds, and begins battles
 * 
 * -----------------------------------------------------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIELDS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC FIELDS
 * ------------------------------
 * - none
 * 
 * PRIVATE FIELDS
 * ------------------------------
 * - pokemonActive              : The list of Pokemon that are active on the screen
 * 
 * - WIDTH                      : The width of this World
 * - HEIGHT                     : The height of this World
 * 
 * - startMusic                 : Determines whether to start playing music or not
 * - PALETTE, CELADON, CERULEAN, CINNABAR, LAVENDAR, OCEAN, VERMILLION : The different background music mp3 paths
 * - SONG_LIST                  : The list of background music mp3s
 * - bgMusic                    : The background music GreenfootSound
 * - random                     : A Random object for randomizing
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ METHODS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC METHODS
 * ------------------------------
 * - act()                      : Checks if any Pokemon have intersected. If they have, begin a PokeBattle betwee
 *                                the two Pokemon, which sets a new world for the PokeBattle.java class
 * 
 * - getActivePokemon()         : Gets a list of the active Pokemon in this world
 * 
 * - addPokemon( PokemonActor pokemon )          : Add this Pokemon to the activePokemon list
 * 
 * - removeActivePokemon( PokemonActor pokemon ) : Remove this Pokemon from the activePokemon list
 * 
 * PRIVATE METHODS
 * ------------------------------
 * - getRandomBGMusic()         : Gets a random background song from the SONG_LIST
 * 
 * - addOfficerJenny()          : Adds Officer Jenny to the arena, who checks to make sure that active Pokemon are
 *                                following all rules, standards, and regulations for the Pokemon's code and
 *                                the Pokemon's fields. Pokemon that fail these rules, standards, or regulations
 *                                will be disqualified
 * 
 * - beginBattle( Pair pokemonPair ) : Begins a battle between the two Pokemon and sets the world to a new PokeBattle world
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CONSTRUCTORS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * - PokeArena()                       : Create a new PokeArena with two starting Pokemon in the arena
 * - PokeArena( PokemonActor pokemon ) : Create a new PokeArena with the given Pokemon starting in the arena
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * -----------------------------------------------------------------------------------------------------------------------------
 * 
 * @author Peter Olson
 * @version 1/17/22
 * @see Pokedex.java
 * @see Pokemon.java
 * @see OfficerJenny.java
 * @see Pikachu.java
 * @see PokeBattle.java
 * @see PokeWorld.java
 */
public class PokeArena extends PokeWorld {

    private ArrayList<Actor> pokemonActive;
    
    private final int WIDTH = 970;
    private final int HEIGHT = 545;
    
    private boolean startMusic = false;
    private final String PALETTE    = "./sounds/palette_town_theme.mp3";
    private final String CELADON    = "./sounds/celadon_city_theme.mp3";
    private final String CERULEAN   = "./sounds/cerulean_city_theme.mp3";
    private final String CINNABAR   = "./sounds/cinnabar_islands_theme.mp3";
    private final String LAVENDAR   = "./sounds/lavendar_town_theme.mp3";
    private final String OCEAN      = "./sounds/ocean_theme.mp3";
    private final String VERMILLION = "./sounds/vermillion_city_theme.mp3";
    private final String[] SONG_LIST = { PALETTE, CELADON, CERULEAN, CINNABAR, LAVENDAR, OCEAN, VERMILLION };
    private GreenfootSound bgMusic;
    private Random random = new Random();
    
    /**
     * Creates a new PokeArena with two Pokemon added to the world. These Pokemon are
     * also added to the active Pokemon list
     * 
     * @see addObject( Actor actor, int x, int y )
     */
    public PokeArena() {    
        // Create a new world with 970x545 cells with a cell size of 1x1 pixels.
        super(970, 545, 1);
        Pikachu pika = null;
        try {
            pika = new Pikachu( 1 );
        } catch( Pikachu.InvalidMoveTotalException e ) {
            e.printStackTrace();
        }
        addObject( pika, WIDTH/2, HEIGHT/2 );

        Pikachu pika2 = null;
        try {
            pika2 = new Pikachu( 2 );
        } catch( Pikachu.InvalidMoveTotalException e ) {
            e.printStackTrace();
        }
        addObject( pika2, 200, 200 );
        
        pokemonActive = new ArrayList<Actor>();
        pokemonActive.add( pika );
        pokemonActive.add( pika2 );
        
        //start playing background music. Comment this line out if you do not want to play any music!
        startMusic = true;
    }
    
    /**
     * Create a new PokeArena with one Pokemon spawned in, the victor of the last battle
     * 
     * Note: Later versions should allow for the next combatent to enter through keystroke or
     *       simultaneously with the victorious Pokemon
     * 
     * @see addObject( Actor actor, int x, int y )
     */
    public PokeArena( PokemonActor pokemon ) {
        super( 970, 545, 1 );
        
        addObject( pokemon, WIDTH/2, HEIGHT/2 );
        
        pokemonActive = new ArrayList<Actor>();
        pokemonActive.add( pokemon );
        
        //start playing background music. Comment this line out if you do not want to play any music!
        startMusic = true;
    }
    
    /**
     * Runs repeatedly, checking if Pokemon have intersected each other, which starts a battle, and if
     * any Pokemon have run off the edge of the screen, which removes that Pokemon
     * 
     * @see getRandomBGMusic()
     * @see GreenfootSound.play()
     * @see GreenfootSound.isPlaying()
     * @see PokemonActor.isTouchingPokemon( Class cls )
     * @see PokemonActor.getOneIntersectingPokemon( Class cls )
     * @see beginBattle( Pair battlePair )
     * @see removeObject( Actor actor )
     */
    public void act() {
        if( startMusic ) {
            bgMusic = new GreenfootSound( getRandomBGMusic() );
            bgMusic.play();
            startMusic = false;
        }
        
        if( bgMusic != null && !bgMusic.isPlaying() ) {
            bgMusic = new GreenfootSound( getRandomBGMusic() );
            bgMusic.play();
        }
        
        if( Greenfoot.isKeyDown("space") )
            addOfficerJenny();
        
        for( Actor actor: pokemonActive ) {
            if( ((PokemonActor)actor).isTouchingPokemon( PokemonActor.class ) ) {
                Actor battlePokemon = ((PokemonActor)actor).getOneIntersectingPokemon( PokemonActor.class );
                if( battlePokemon != null ) {
                    pokemonActive.clear();
                    beginBattle( new Pair( (PokemonActor)actor, (PokemonActor)battlePokemon ) );
                    break;
                }
            }
            
            int actorX = actor.getX();
            int actorY = actor.getY();
            if( actorX > WIDTH || actorX < 0 || actorY > HEIGHT || actorY < 0 ) removeObject( actor );
        }
    }
    
    /**
     * Play a random background song from the SONG_LIST
     * 
     * @return String The String path for this bg mp3 song
     */
    private String getRandomBGMusic() {
        return SONG_LIST[ random.nextInt( SONG_LIST.length ) ];
    }
    
    /**
     * Adds an OfficerJenny to the Arena at a fixed coordinate
     * 
     * This needs to be done not in the PokeArena constructor because the world needs to be
     * instantiated before OfficerJenny starts inspecting Pokemon
     * 
     * @see act()
     */
    private void addOfficerJenny() {
        OfficerJenny oj = new OfficerJenny();
        addObject( oj, WIDTH/2 + 50, HEIGHT/2 );
    }
    
    /**
     * Start a Pokemon battle using the pair of intersecting Pokemon
     * 
     * A new battle creates new entities using the images of the Pokemon and a new world
     * to create the battle scene. From there, sprites are handled by the PokeBattle
     * 
     * @param pokemonPair This is the Pair of Pokemon that are entering into the battle
     * @see act()
     * @see removeObjects( List<Actor> actorList )
     * @see Greenfoot.setWorld( World w )
     */
    private void beginBattle( Pair pokemonPair ) {
        bgMusic.stop();
        removeObjects( pokemonActive );
        Greenfoot.setWorld( new PokeBattle( pokemonPair, this ) );
    }
    
    /**
     * Gets the list of active pokemon and returns them
     * 
     * @return ArrayList<Actor> The list of active pokemon
     */
    public ArrayList<Actor> getActivePokemon() {
        return this.pokemonActive;
    }
    
    /**
     * Adds a Pokemon to the active pokemon list
     * 
     * @param pokemon The pokemon to be added to the list
     */
    public void addPokemon( PokemonActor pokemon ) {
        this.pokemonActive.add( pokemon );
    }
    
    /**
     * Removes the given Pokemon from the list of active pokemon in this world
     * 
     * @param pokemon The pokemon to be removed from the list
     * @see OfficerJenny.disqualifyPokemon( PokemonActor pokemon, World world )
     */
    public boolean removeActivePokemon( PokemonActor pokemon ) {
        return this.pokemonActive.remove( pokemon );
    }
}
