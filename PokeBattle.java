import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * PokeBattle.java
 * 
 * This world controls the battle for two Pokemon that are fighting.
 * 
 * -----------------------------------------------------------------------------------------------------------------------------
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIELDS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC FIELDS
 * ------------------------------
 * - none
 * 
 * PRIVATE FIELDS
 * ------------------------------
 * - DELAY_CYCLES               : Used to delay the act cycle in order to not skip screens when the user
 *                                presses the enter button
 *                                
 * - BG_IMAGE_LOC               : The location of the background image for this world
 * 
 * - HEALTH_BAR                 : The location of the health bar images for this world (need to add the number and .png)
 * - EXP_BAR                    : The location of the exp bar images for this world (need to add the number and .png)
 * 
 * - BATTLE_TEXTBOX             : The location of the image for the battle text box
 * 
 * - MENU                       : The location of the image for the menu text box, which has no selection arrow
 * - MENU_TOP_LEFT              : The location of the image for the menu text box with
 *                                the selection arrow in the top left corner
 * - MENU_BOTTOM_LEFT           : The location of the image for the menu text box with
 *                                the selection arrow in the bottom left corner
 * - MENU_TOP_RIGHT             : The location of the image for the menu text box with
 *                                the selection arrow in the top right corner
 * - MENU_BOTTOM_RIGHT          : The location of the image for the menu text box with
 *                                the selection arrow in the bottom right corner
 * 
 * - LARGE_MENU_TOP_LEFT        : The location of the image for the large menu text box with
 *                                the selection arrow in the top left corner
 * - LARGE_MENU_BOTTOM_LEFT     : The location of the image for the menu text box with
 *                                the selection arrow in the bottom left corner
 * - LARGE_MENU_TOP_RIGHT       : The location of the image for the menu text box with
 *                                the selection arrow in the top right corner
 * - LARGE_MENU_BOTTOM_RIGHT    : The location of the image for the menu text box with
 *                                the selection arrow in the bottom right corner
 * 
 * - world                      : The world object from the PokeArena.java world
 * - WIDTH, HEIGHT              : The width and height of the PokeBattle world
 * 
 * - TEXT_BOX_X                 : The x location of the text box
 * - TEXT_BOX_Y                 : The y location of the text box
 * - LARGE_TEXT_BOX_X           : The x location of the large text box
 * - LARGE_TEXT_BOX_Y           : The y location of the large text box
 * - TEXT_BOX_WIDTH             : The width of the text box
 * - TEXT_BOX_HEIGHT            : The height of the text box
 * - TEXT_BOX_OFFSET_X          : The x offset for the text box
 * - TEXT_BOX_OFFSET_Y          : The y offset for the text box
 * 
 * - first                      : The first PokemonActor of the PokemonActor pair to battle. This Pokemon
 *                                begins as the attacking Pokemon
 * - second                     : The second PokemonActor of the PokemonActor pair to battle. This Pokemon
 *                                begins as the defending Pokemon
 * 
 * - battleImages               : The pair of BattleImages that are used for the PokeBattle
 * 
 * - activeActors               : The list of the current Actors within this world... currently restricted to PokemonActors
 * 
 * - TURN_ORDER                 : @DEPRECATED - The list of BattleTurn turns in order for the main sequence
 * - currentTurn                : The current BattleTurn of the PokeBattle
 * - currentPlayer              : The current PokemonTurn of the PokeBattle
 * - currentSelection           : The current location of the SelectionArrow for menu selection
 * 
 * - totalSelectionOptions      : The number of menu selection options
 * - pageNumber                 : The current page number of the current menu
 * 
 * - MEDIUM_FONT_SIZE           : The size for TextImages for text within the text box
 * 
 * - textList                   : The list of TextImages that are on the screen
 * 
 * - currentEffectiveness       : The current effectiveness multiplier for the most recently used Move
 * - moveMissed                 : Keeps track of whether the most recent Move missed or hit
 * 
 * - startMusic                 : Determines whether to start playing music or not
 * - POKEMON_GYM, BATTLE_TRAINER, BATTLE_LEADER : The different background music mp3 paths
 * - SONG_LIST                  : The list of background music mp3s
 * - bgMusic                    : The background music GreenfootSound
 * - random                     : A Random object for randomizing
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ENUMS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * - BattleTurn     : INTRO, MENU, SELECT_MOVE, USE_MOVE, MOVE_EFFECT, MOVE_RESULT, POKEDEX_MENU, POKEDEX_EFFECTIVITY,
                      POKEDEX_EFFECTIVITY_SELECT, POKEDEX_EXPERIENCE, POKEDEX_MOVE, POKEDEX_STATUS, POKEDEX_POKEMON,
                      AWARD_EXP, OUTRO, ITEM, USE_ITEM, CHOOSE_MOVE_FOR_ITEM, RUN
 * 
 * - PokemonTurn    : PLAYER_1, PLAYER_2
 * 
 * - SelectionArrow : TOP_LEFT, BOTTOM_LEFT, TOP_RIGHT, BOTTOM_RIGHT
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Constructors @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PokeBattle( Pair battlePair, World world ) : Create a PokeBattle world, given the pair of PokemonActors and a reference
 *                                              to the previous world object
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Methods @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * PUBLIC METHODS
 * ------------------------------
 * - act()          : Processes the ordering of the battle, using the BattleTurn enum to dictate the stages of Battle.
 *                    Both PLAYER_1 (the 'first' Pokemon) and PLAYER_2 (the 'second' Pokemon) use the same ordering of
 *                    battle. Animations, field processing, and interactions are handled through the act() method.
 *                    Currently v 1.0 does not implement animation... this will be implemented within Part 3) of the
 *                    Pokemon project. Much of the act() method focuses on navigation using the text box menus, which
 *                    are navigated using a system of arrow keys and the 'enter' button. The conclusion of the act()
 *                    methods results in the winning Pokemon being returned to the original world (PokeArena) with its
 *                    new current field state, and setting the world back to the PokeArena.
 * 
 * PRIVATE METHODS
 * ------------------------------
 * - addPokemonImages()         : Adds the BATTLE_IMAGEs to the PokeBattle
 * 
 * - changeSelectionImage()     : Changes the text and selection image for menu options
 * 
 * - hasPP()                    : Determines whether the current Move has PP or not
 * - printNoPP()                : Prints that there is no PP left for this Move. The Move 'STRUGGLE' will be used instead
 * 
 * - attack()                   : Launches the attack sequence. The attack sequence includes the following:
 *                                1) Display text of Pokemon using attack: "<Pokemon.NAME> used <Move.NAME>!"
 *                                2) Subtract move PP
 *                                3) Get move power and accuracy. Get Attacking Pokemon current (for all) attack
 *                                   or special attack, accuracy, and level. Get Defending Pokemon current (for all)
 *                                   defense or special defense, and evasion. Get effectiveness based on Pokemon types
 *                                4) Calculate damage / determine if hit
 *                                5) Calculate Status effects and other side-effects - @@TO DO
 *                                6) Set damage inflicted by adjusting defender Pokemon HP
 *                                7) Adjust on-screen images, such health bars and health totals - @@TO DO
 * 
 * - determineIfHit( double chanceToHit )   : Determines whether the Move will hit based on the calculated chanceToHit input
 * 
 * - getCurrentPokemon()                    : Gets the current active Pokemon (the one that is attacking)
 * - getOtherPokemon()                      : Gets the non-active Pokemon (the one that is defending)
 * - getCurrentMove( PokemonActor pokemon ) : Gets the current Move that is being used by the active (attacking) Pokemon
 * 
 * - displayMoveEffect()                    : Displays any unsual effects following an effect, including Status effects and
 *                                            the irregular effectiveness of the attack
 * 
 * - showResults()                          : Displays the results of the attack, including whether the attack missed and
 *                                            whether the attack caused the opponent to faint
 * 
 * - awardExp()                             : Awards the winning Pokemon exp after the Battle is over, and levels up the Pokemon
 *                                            if necessary
 * 
 * - changeWorlds()                         : Changes the world back to the PokeArena, adding the winner of the Battle to the
 *                                            arena
 * 
 * - setSelection()                         : Sets the menu based on the current BattleTurn
 * - selectMenu()                           : Selects the correct menu based on the current selection
 *
 * - clearText()                            : Clears all TextImages off the screen
 * 
 * - setMenu()                              : Sets the main menu options
 * - setMenu( String imageLoc )             : Sets the main menu images and text
 * - setFightMenu()                         : Sets the fight menu images and text, including the Moves, Move PP, and Move types
 * - setPokedexMenu()                       : Sets the Pokedex menu images and text
 * - setItemMenu()                          : Sets the Item menu images and text
 * - setRunMenu()                           : @@TO DO - Sets the Run menu images and text
 * 
 * - addPokeInfo()                          : Adds the Pokemons' info to the Battle interface
 * - addHealthBar()                         : Adds the Pokemons' healthbar to the screen
 * - addExpBar()                            : Adds the Pokemons' exp bar to the screen
 * - addNames()                             : Adds the Pokemons' names to the screen
 * 
 * - setPokedexMenu( String imageLoc, String option1, String option2, String option3, String option4 )
 *      : Sets the Pokedex menu with the given image and text
 * - setPokedex()                 : Sets the Pokedex image based on the menu selection
 * 
 * - pokedexSearch()              : Sets the Pokedex image to the search menu using a TextField
 * - pokedexSearch( String text ) : Sets the search result using the text input. If the search is valid, displays the
 *                                  corresponding Pokemon and its information. If not valid, display a 'not found' template
 * - addToPokedex( PokemonActor pokemon, PokemonActor[] pokemonList )
 *      : Adds the Pokemon in the list to the given Pokemon's Pokedex
 * - createNewPokedexTextFile( PokemonActor pokemon ) : Create a new Pokedex text file for the given Pokemon and store the
 *                                                      information of this Pokemon within the file
 * - getPokedexPokemonInfo( PokemonActor pokemon )    : Gets the Pokemon's info as a String
 * - getPokedexTextFiles()                            : Gets a list of the Pokedex text files in this folder
 * 
 * - pokedexMove()                : Sets the Pokedex image to the search menu using a TextField
 * - pokedexMove( String text )   : Sets the search result using the text input. If the search is valid, displays the
 *                                  corresponding Move and its information. If not valid, display a 'not found' template
 * 
 * - pokedexEffectivity()         : Sets the Pokedex image to the pokedex effectivity search, which uses button inputs
 *                                  to tell what the effectiveness of different types is equal to
 * - addHighlights()              : Adds highlights to the type buttons clicked
 * 
 * - pokedexStatus()              : Sets the Pokedex image to the pokedex status search, which allows the user to search
 *                                  for a status and to view their current Pokemon's current status
 * - pokedexStatus()              : Sets the search result using the text input. If the search is valid, displays the
 *                                  corresponding Status and its information, as well as displaying the current Pokemon's
 *                                  current Status. If not valid, display a 'not found' template
 * 
 * - runSelection()               : Sets the menu to confirm the use of an item
 * - chooseMove()                 : Allow the the user to choose the Move that this item will affect
 * - useItemMenu()                : Uses the item and displays a message of the relevant changes
 * 
 * - getAllMoveNames( PokemonActor pokemon ): Gets a list of all the Move names of the current Pokemon
 * - getAllMovePP( PokemonActor pokemon )   : Gets a list of all the Move PP of the current Pokemon
 * - getAllMoveTypes( PokemonActor pokemon ): Gets a list of all the Move types of the current Pokemon
 * 
 * - setText( String text )                                 : Sets the text to the screen
 * - setText( String text, int addXOffset, int addYOffset ) : Sets the text to the screen with the x and y added offsets
 * - setText( int width, int height, String text,
 *            int xOffset, int yOffset,
 *            Color color, boolean isBold, int x, int y )   : Sets the text to the screen with the given inputs
 * - setTextImage( String imageLoc, String text,
 *                 int xOffset, int yOffset, int x, int y ) : Sets the image to the screen with text on top
 * - setTextImage( GreenfootImage image, String text,
 *                 int xOffset, int yOffset, int x, int y ) : Sets the image to the screen with text on top
 * 
 * - getTextImage( int width, int height, String text,
 *                 int xOffset, int yOffset,
 *                 Color color, boolean isBold )            : Gets a TextImage with a transparent background and text on top
 * - getTextImage( String imageLoc, String text,
 *                 int xOffset, int yOffset )               : Gets a TextImage using the given image and text on top
 * - getTextImage( GreenfootImage image, String text,
 *                 int xOffset, int yOffset )               : Gets a TextImage using the given image and text on top
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Private/Protected Classes @@@@@@@@@@@@@@@@@
 * - none
 * 
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * -----------------------------------------------------------------------------------------------------------------------------
 * 
 * @author Peter Olson
 * @version 2/8/22
 * @see PokemonActor.java
 * @see PokeArena.java
 * @see PokeWorld.java
 * @see Move.java
 * @see Pokedex.java
 */
public class PokeBattle extends PokeWorld {

    /* Used to delay the act cycles. This is needed since the 'isKeyDown'
     * method can pick up a second keystroke if it is pressed for too long */
    private final int DELAY_CYCLES         = 10;
    
    private final String BG_IMAGE_LOC      = "./images/battlescene.png";
    
    private final String HEALTH_BAR        = "./images/healthbar_";
    private final String EXP_BAR           = "./images/expbar_";
    
    private final String BATTLE_TEXTBOX    = "./images/battle_textbox.png";
    
    private final String MENU              = "./images/menu.png";
    private final String MENU_TOP_LEFT     = "./images/menu_top_left.png";
    private final String MENU_BOTTOM_LEFT  = "./images/menu_bottom_left.png";
    private final String MENU_TOP_RIGHT    = "./images/menu_top_right.png";
    private final String MENU_BOTTOM_RIGHT = "./images/menu_bottom_right.png";
    
    private final String LARGE_MENU_TOP_LEFT     = "./images/large_menu_top_left.png";
    private final String LARGE_MENU_BOTTOM_LEFT  = "./images/large_menu_bottom_left.png";
    private final String LARGE_MENU_TOP_RIGHT    = "./images/large_menu_top_right.png";
    private final String LARGE_MENU_BOTTOM_RIGHT = "./images/large_menu_bottom_right.png";
    
    private final String POKEDEX_POKEMON_SEARCH        = "./images/pokedex.png";
    private final String POKEDEX_POKEMON_INFO          = "./images/pokedex_pokemon_details.png";
    private final String UNKNOWN_POKEMON               = "./images/unknown_pokemon.png";
    private final int POKEDEX_POKEMON_INFO_WIDTH       = 435;
    private final int POKEDEX_POKEMON_INFO_HEIGHT      = 122;
    
    private final String POKEDEX_MOVE_INFO             = "./images/pokedex_move_details.png";
    private final int POKEDEX_MOVE_INFO_WIDTH          = 371;
    private final int POKEDEX_MOVE_INFO_HEIGHT         = 68;
    
    private String TYPE_LOC_GENERIC              = "./images/type_@.png";
    private String CATEGORY_LOC_GENERIC          = "./images/category_@.png";
    
    private final String POKEBAG                 = "./images/pokebag.png";
    private final String POKEBAG_ARROW           = "./images/pokebag_arrow.png";
    private final String POKEBAG_X               = "./images/pokebag_x.png";
    private int selectionArrowListPosition       = 0;
    private final int MAX_ITEMS_PER_WINDOW       = 5;
    private Item currentItem                     = null;
    
    private String remainingText = "";
    
    private World world;
    private final int WIDTH, HEIGHT;
    
    private final int TEXT_BOX_X        = 408;
    private final int TEXT_BOX_Y        = 459;
    private final int LARGE_TEXT_BOX_X  = 252;
    private final int LARGE_TEXT_BOX_Y  = 460;
    private final int TEXT_BOX_WIDTH    = 757;
    private final int TEXT_BOX_HEIGHT   = 121;
    private final int TEXT_BOX_OFFSET_X = 20;
    private final int TEXT_BOX_OFFSET_Y = 28;
    
    private PokemonActor first, second;
    
    private Pair battleImages;
    
    private ArrayList<Actor> activeActors = new ArrayList<Actor>();
    private TextField activeTextField;
    private String lastSearch = "";
    
    private boolean isHighlighted  = false;
    private boolean isRedHighlight = true;
    private boolean isYes          = true;
    private String lastTypeImageLoc;
    
    protected enum BattleTurn {
        INTRO, MENU, SELECT_MOVE, USE_MOVE, MOVE_EFFECT, MOVE_RESULT, POKEDEX_MENU, POKEDEX_EFFECTIVITY,
        POKEDEX_EFFECTIVITY_SELECT, POKEDEX_EXPERIENCE, POKEDEX_MOVE, POKEDEX_STATUS, POKEDEX_POKEMON,
        AWARD_EXP, OUTRO, ITEM, USE_ITEM, CHOOSE_MOVE_FOR_ITEM, RUN;
    }
    
    protected enum PokemonTurn {
        PLAYER_1, PLAYER_2;
    }
    
    protected enum SelectionArrow {
        TOP_LEFT, BOTTOM_LEFT, TOP_RIGHT, BOTTOM_RIGHT;
    }
    
    private BattleTurn     currentTurn      = BattleTurn.INTRO;
    private PokemonTurn    currentPlayer    = PokemonTurn.PLAYER_1;
    private SelectionArrow currentSelection = SelectionArrow.TOP_LEFT;
    
    private int totalSelectionOptions   = 4;
    private int pageNumber              = 1;
    
    private final int MEDIUM_FONT_SIZE  = 18;
    private final int LARGE_FONT_SIZE   = 28;
    private int currentFontSize         = MEDIUM_FONT_SIZE;
    private double pixelToFontSizeRatio = 2.0;
    
    private ArrayList<TextImage> textList = new ArrayList<TextImage>();
    
    private double currentEffectiveness = 1.0;
    private boolean moveMissed          = false;
    private boolean hasCriticalHit      = false;
    
    //Variables to keep track of attacking special effects
    private double lastDamage           = (double)PokeWorld.ERROR;
    private Move lastAttack             = null;
    private boolean isPhasedOut1        = false; //These should be Pokemon fields. I'm leaving this as is right now so that students don't have to change everything once again @@FIX
    private boolean isPhasedOut2        = false;
    
    private boolean startMusic          = false;
    private final String POKEMON_GYM    = "./sounds/pokemon_gym.mp3";
    private final String BATTLE_TRAINER = "./sounds/battle_verses_trainer.mp3";
    private final String BATTLE_LEADER  = "./sounds/battle_verses_gym_leader.mp3";
    private final String[] SONG_LIST    = { POKEMON_GYM, BATTLE_TRAINER, BATTLE_LEADER };
    private GreenfootSound bgMusic;
    private Random random = new Random();
    
    /**
     * Constructor for objects of class PokeBattle.
     * 
     * @param battlePair The Pair of PokemonActors that are battling
     * @param world The reference to the PokemonArena world
     * @see Pair.getFirst(), Pair.getSecond()
     * @see addPokemonImages()
     * @see setText( String text )
     * @see addToPokedex( PokemonActor pokemon, PokemonActor[] pokemonList )
     * @see PokeWorld.capFirstLetter( String token )
     */
    public PokeBattle( Pair battlePair, World world ) {
        // Create a new world with 817x545 cells with a cell size of 1x1 pixels.
        super( 817, 545, 1 );
        
        this.world = world;
        GreenfootImage bgImage = new GreenfootImage( BG_IMAGE_LOC );
        this.WIDTH = bgImage.getWidth();
        this.HEIGHT = bgImage.getHeight();
        
        this.first = (PokemonActor)battlePair.getFirst();
        this.second = (PokemonActor)battlePair.getSecond();
        this.battleImages = addPokemonImages();
        
        String text = PokeWorld.capFirstLetter( first.getName() ) + " wants to fight " +
                      PokeWorld.capFirstLetter( second.getName() ) + "!\n\n<Press ENTER to continue>";
        setText( text );
        
        //Add Pokemon to Pokedex, for both Pokemon
        addToPokedex( first, new PokemonActor[]{ second } );
        addToPokedex( second, new PokemonActor[]{ first } );
        
        //start playing background music. Comment this line out if you do not want to play any music!
        startMusic = true;
    }
    
    /**
     * Add the Pokemon images to the battle arena
     * 
     * @return Pair The pair of BattleImage Pokemon
     */
    private Pair addPokemonImages() {
        String firstImageLoc = first.getBattleImageName();
        String secondImageLoc = second.getBattleImageName();
        
        BattleImage firstImage = new BattleImage( firstImageLoc );
        BattleImage secondImage = new BattleImage( secondImageLoc );
        
        addObject( firstImage, (int)(WIDTH/3.4), (int)(HEIGHT*0.85) - firstImage.getHeight() );
        addObject( secondImage, (int)(WIDTH*0.75), (int)(HEIGHT*0.03) + secondImage.getHeight() );
        
        activeActors.add( firstImage );
        activeActors.add( secondImage );
        
        return new Pair( firstImage, secondImage );
    }
    
    /**
     * Runs the battle until there is a winner
     * 
     * @see setMenu()
     * @see changeSelection()
     * @see selectMenu()
     * @see attack()
     * @see displayMoveEffect()
     * @see showResults()
     * @see awardExp()
     * @see changeWorlds()
     * @see setPokedex()
     * @see pokedexSearch( String text )
     * @see pokedexMove( String text )
     * @see addHighlights()
     * @see pokedexStatus( String text )
     * @see setItemMenu()
     * @see runSelection()
     * 
     * @see clearText()
     * @see Greenfoot.delay( int numCycles )
     * @see TextField.getText()
     * @see removeObject( Actor actor )
     * 
     * @see getRandomBGMusic()
     * @see GreenfootSound.play()
     * @see GreenfootSound.isPlaying()
     */
    public void act() {
        if(        currentTurn == BattleTurn.INTRO && Greenfoot.isKeyDown("enter") ) {
            clearText();
            setMenu();
            isHighlighted = false; //Reset for the pokedexEffectivity() function
            addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES ); //delay cycles so that the next listen for 'enter' does not pick up this one
        } else if( ( currentTurn == BattleTurn.MENU || currentTurn == BattleTurn.SELECT_MOVE ||
                     currentTurn == BattleTurn.POKEDEX_MENU || currentTurn == BattleTurn.CHOOSE_MOVE_FOR_ITEM ) &&
                   ( Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right") ||
                     Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down") ) ) {
            changeSelection();
            addPokeInfo();
        } else if( currentTurn == BattleTurn.MENU && Greenfoot.isKeyDown("enter") ) {
            clearText();
            selectMenu();
            addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.SELECT_MOVE && Greenfoot.isKeyDown("enter") ) {
            clearText();
            if( !hasPP() )
                printNoPP(); //Sets current Move to STRUGGLE
            attack();
            addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.MOVE_EFFECT && Greenfoot.isKeyDown("enter") ) {
            clearText();
            displayMoveEffect();
            addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.MOVE_RESULT && Greenfoot.isKeyDown("enter") ) {
            clearText();
            showResults();
            addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.AWARD_EXP && Greenfoot.isKeyDown("enter") ) {
            clearText();
            awardExp();
            addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.OUTRO && Greenfoot.isKeyDown("enter") ) {
            clearText();
            changeWorlds();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.POKEDEX_MENU && Greenfoot.isKeyDown("enter") ) {
            clearText();
            setPokedex();
            addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.POKEDEX_POKEMON && Greenfoot.isKeyDown("enter") && activeTextField != null ) {
            clearText();
            lastSearch = activeTextField.getText();
            pokedexSearch( lastSearch );
            removeObject( activeTextField );
            activeTextField = null;
            //addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.POKEDEX_POKEMON && Greenfoot.isKeyDown("enter") && !remainingText.equals("") ) {
            clearText();
            pokedexSearch( lastSearch );
            //addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.POKEDEX_MOVE && Greenfoot.isKeyDown("enter") && activeTextField != null ) {
            clearText();
            lastSearch = activeTextField.getText();
            pokedexMove( lastSearch );
            removeObject( activeTextField );
            activeTextField = null;
            //addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.POKEDEX_MOVE && Greenfoot.isKeyDown("enter") && !remainingText.equals("") ) {
            clearText();
            pokedexMove( lastSearch );
            //addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.POKEDEX_EFFECTIVITY_SELECT && Greenfoot.mouseClicked( null ) && !Greenfoot.isKeyDown("enter") ) {
            addHighlights();
            //addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.POKEDEX_STATUS && Greenfoot.isKeyDown("enter") && activeTextField != null ) {
            clearText();
            lastSearch = activeTextField.getText();
            pokedexStatus( lastSearch );
            removeObject( activeTextField );
            activeTextField = null;
            addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.POKEDEX_STATUS && Greenfoot.isKeyDown("enter") && !remainingText.equals("") ) {
            clearText();
            pokedexStatus( lastSearch );
            addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.ITEM && ( (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down")) ||
                                                       (!remainingText.equals("") && Greenfoot.isKeyDown("enter")) ) ) {
            clearText();
            setItemMenu();
            //addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.ITEM &&
                   (Greenfoot.isKeyDown("enter") || (currentItem != null &&
                                                     (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right")) ) ) ) {
            runSelection();
            //addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        } else if( currentTurn == BattleTurn.CHOOSE_MOVE_FOR_ITEM && Greenfoot.isKeyDown("enter") ) {
            clearText();
            useItemMenu();
            addPokeInfo();
            Greenfoot.delay( DELAY_CYCLES );
        }
        
        //Begin playing music
        if( startMusic ) {
            bgMusic = new GreenfootSound( getRandomBGMusic() );
            bgMusic.play();
            startMusic = false;
        }
        
        //Play a new song once the current song finishes playing
        if( bgMusic != null && !bgMusic.isPlaying() ) {
            bgMusic = new GreenfootSound( getRandomBGMusic() );
            bgMusic.play();
        }
    }
    
    /**
     * Play a random background song from the SONG_LIST
     * 
     * @return The String path for this bg mp3 song
     */
    private String getRandomBGMusic() {
        return SONG_LIST[ random.nextInt( SONG_LIST.length ) ];
    }
    
    /**
     * Change the selection image and text of the current menu
     * 
     * @see act()
     * @see clearText()
     * @see setSelection()
     */
    private void changeSelection() {
        if( Greenfoot.isKeyDown("left") && currentSelection == SelectionArrow.TOP_RIGHT ) {
            currentSelection = SelectionArrow.TOP_LEFT;
            setSelection();
        } else if( Greenfoot.isKeyDown("left") && currentSelection == SelectionArrow.BOTTOM_RIGHT ) {
            currentSelection = SelectionArrow.BOTTOM_LEFT;
            setSelection();
        } else if( Greenfoot.isKeyDown("right") && currentSelection == SelectionArrow.TOP_LEFT && totalSelectionOptions >= 3 ) {
            currentSelection = SelectionArrow.TOP_RIGHT;
            setSelection();
        } else if( Greenfoot.isKeyDown("right") && currentSelection == SelectionArrow.BOTTOM_LEFT && totalSelectionOptions >= 4 ) {
            currentSelection = SelectionArrow.BOTTOM_RIGHT;
            setSelection();
        } else if( Greenfoot.isKeyDown("up") && currentSelection == SelectionArrow.BOTTOM_LEFT ) {
            currentSelection = SelectionArrow.TOP_LEFT;
            setSelection();
        } else if( Greenfoot.isKeyDown("up") && currentSelection == SelectionArrow.BOTTOM_RIGHT ) {
            currentSelection = SelectionArrow.TOP_RIGHT;
            setSelection();
        } else if( Greenfoot.isKeyDown("down") && currentSelection == SelectionArrow.TOP_LEFT && totalSelectionOptions >= 2 ) {
            currentSelection = SelectionArrow.BOTTOM_LEFT;
            setSelection();
        } else if( Greenfoot.isKeyDown("down") && currentSelection == SelectionArrow.TOP_RIGHT && totalSelectionOptions >= 4 ) {
            currentSelection = SelectionArrow.BOTTOM_RIGHT;
            setSelection();
        }
    }
    
    /**
     * Determines whether there is enough PP to use the current Move
     * 
     * @return boolean True if there is enough PP, false otherwise
     * @see act()
     * @see getCurrentMove( PokemonActor pokemon )
     * @see getCurrentPokemon()
     * @see Move.getCurrentPP()
     */
    private boolean hasPP() {
        return getCurrentMove( getCurrentPokemon() ).getCurrentPP() > 0;
    }
    
    /**
     * Print to the screen that the current move has no
     * 
     * "______ has no moves left! Blank used STRUGGLE in its confusion!"
     * 
     * @see act()
     * @see getCurrentPokemon()
     * @see PokeWorld.capFirstLetter( String token )
     * @see PokemonActor.getName()
     * @see PokemonActor.getPokedex()
     * @see Pokedex.getMoveFromName( String name )
     * @see PokemonActor.setCurrentMove( Move currentMove )
     * @see setText( String text )
     * @see setText( String text, int addXOffset, int addYOffset )
     */
    private void printNoPP() {
        PokemonActor pokemon = getCurrentPokemon();
        String name = PokeWorld.capFirstLetter( pokemon.getName() );
        String text = name + " has no moves left!";
        String text2 = name + " used STRUGGLE in its confusion!";
        
        //Set current move of PokemonActor to STRUGGLE
        Move struggle = null;
        try {
            struggle = pokemon.getPokedex().getMoveFromName("STRUGGLE");
        } catch( Pokedex.InvalidMovesFileException e ) {
            e.printStackTrace();
        }
        pokemon.setCurrentMove( struggle );
        
        setText( text );
        setText( text2, 0, 30 );
    }
    
    /**
     * Launch the attack sequence. The attack sequence includes the following:
     * 
     * 1) Display text of Pokemon using attack: "<Pokemon.NAME> used <Move.NAME>!"
     * 2) Subtract move PP
     * 3) Get move power and accuracy. Get Attacking Pokemon current (for all) attack
     *    or special attack, accuracy, and level. Get Defending Pokemon current (for all)
     *    defense or special defense, and evasion. Get effectiveness based on Pokemon types
     * 4) Calculate damage / determine if hit
     * 5) Calculate Status effects and other side-effects
     * 6) Set damage inflicted by adjusting defender Pokemon HP
     * 7) Adjust on-screen images, such health bars and health totals
     * 
     * @see act()
     * @see getCurrentPokemon()
     * @see getOtherPokemon()
     * @see PokemonActor.getName()
     * @see setText( String text )
     * @see setText( String text, int addXOffset, int addYOffset )
     * @see Move.setCurrentPP( int PP )
     * @see Move.getCurrentPower()
     * @see Move.getCurrentAccuracy()
     * @see Move.getType()
     * @see Move.getCategory()
     * @see PokemonActor.getCurrentMove()
     * @see PokemonActor.getCurrentAttack()
     * @see PokemonActor.getCurrentDefense()
     * @see PokemonActor.getCurrentSpecialAttack()
     * @see PokemonActor.getCurrentSpecialDefense()
     * @see PokemonActor.getCurrentAccuracy()
     * @see PokemonActor.getCurrentLevel()
     * @see PokemonActor.getCurrentEvasion()
     * @see PokemonActor.getPokedex()
     * @see Pokedex.getEffectiveness( String attackType, String defendingPokemonType )
     * @see determineIfHit( double chanceToHit )
     */
    private void attack() {
        currentTurn = BattleTurn.USE_MOVE;
        
        PokemonActor pokemon = getCurrentPokemon();       //Attacking Pokemon
        String pokemonName = pokemon.getName();
        PokemonActor otherPokemon = getOtherPokemon();
        String otherPokemonName = otherPokemon.getName(); //Defending Pokemon
        
        boolean endAttackSequence = false;
        
        Move move = pokemon.getCurrentMove();
        String moveName = move.getName();
        
        /*Reset selection arrow. Must be done AFTER the getCurrentMove(...) function,
          which depends on the previous arrow location */
        currentSelection = SelectionArrow.TOP_LEFT;
        
        //1) Display text of Pokemon using attack: "<Pokemon.NAME> used <Move.NAME>!"
        String text = pokemonName + " used " + moveName + "!";
        setText( text );
        
        //2) Subtract move PP
        move.setCurrentPP( move.getCurrentPP() - 1 );
        
        /* 3) Get move power and accuracy. Get Attacking Pokemon current (for all) attack
         *    or special attack, accuracy, and level. Get Defending Pokemon current (for all)
         *    defense or special defense, and evasion. Get effectiveness based on Pokemon types */
        int movePower = move.getCurrentPower();
        int moveAccuracy = move.getCurrentAccuracy();
        Move.MoveType moveType = move.getType();
        Move.MoveCategory moveCategory = move.getCategory();
        int currentAttack;
        int otherCurrentDefense;
        if( moveCategory == Move.MoveCategory.PHYSICAL || moveCategory == Move.MoveCategory.STATUS ) {
            currentAttack = pokemon.getCurrentAttack();
            otherCurrentDefense = otherPokemon.getCurrentDefense();
        } else { //MoveCategory.SPECIAL
            currentAttack = pokemon.getSpecialAttack();
            otherCurrentDefense = otherPokemon.getCurrentSpecialDefense();
        }
        int currentAccuracy = pokemon.getCurrentAccuracy();
        int currentLevel = pokemon.getCurrentLevel();
        int otherCurrentEvasion = otherPokemon.getCurrentEvasion();
        double effectiveness = 1.0; //to be overwritten
        try {
            effectiveness = pokemon.getPokedex().getEffectiveness( moveType.name(), otherPokemon.getType() );
        } catch( Pokedex.InvalidMoveException e ) {
            e.printStackTrace();
        }
        this.currentEffectiveness = effectiveness; //used in the next turn of the battle sequence
        
        //4) Calculate damage / determine if hit
        double chanceToHit = (moveAccuracy * ((double)currentAccuracy)/((double)otherCurrentEvasion))/100.0;
        boolean moveHit = determineIfHit( chanceToHit );

        double damage = ( ( ((((2.0)*currentLevel)/5.0) + 2.0) * (double)movePower *
                         (((double)currentAttack)/((double)otherCurrentDefense)) )/50.0) + 2.0;
        
        double criticalHitRatio = pokemon.getCurrentCriticalHitRatio();
        hasCriticalHit = new Random().nextDouble() < criticalHitRatio;
        if( hasCriticalHit )
            damage *= (2*pokemon.getCurrentLevel() + 5)/(pokemon.getCurrentLevel() + 5);
                         
        //5) Calculate Status effects and other side-effects
        //@@TO DO
        
        if( effectiveness != Pokedex.REG_EFFECTIVE ||
            otherPokemon.getStatus() != PokemonActor.Status.NORMAL ||
            hasCriticalHit )                                         currentTurn = BattleTurn.MOVE_EFFECT;
        else                                                         currentTurn = BattleTurn.MOVE_RESULT;
        
        if( !moveHit ) {
            this.moveMissed = true;
            this.lastDamage = 0.0;
            currentTurn = BattleTurn.MOVE_RESULT;
            return;
        }
        
        //6) Set damage inflicted by adjusting defender Pokemon HP
        int totalDamage = (int)(damage * effectiveness);
        this.lastDamage = (double)totalDamage;
        otherPokemon.setCurrentHP( otherPokemon.getCurrentHP() - totalDamage );
        
        /* ------------------------------------------------------------------------------------- */
        
        this.lastAttack = move;
        
    }
    
    /**
     * Adds all the on-screen informational pictures to the screen
     * 
     * @see act()
     * @see addHealthBar()
     * @see addExpBar()
     * @see addNames()
     */
    private void addPokeInfo() {
        addHealthBar();
        addExpBar();
        addNames();
    }
    
    /**
     * Shows the damaged state of the health of both Pokemon in the battle by
     * updating the health bar overlay image and showing the health as number X / X
     * 
     * @see addPokeInfo()
     * @see getCurrentPokemon()
     * @see PokemonActor.getCurrentHP()
     * @see PokemonActor.getHP()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset,
     *               Color color, boolean isBold, int x, int y )
     * @see getOtherPokemon()
     */
    private void addHealthBar() {
        PokemonActor pokemon = getCurrentPokemon();
        int currentHealth = pokemon.getCurrentHP();
        int maxHealth = pokemon.getHP();
        double healthRatio = (double)currentHealth / (double)maxHealth;
        double healthPercent = healthRatio * 10.0;
        int healthStage = (int)(Math.floor( healthPercent ) * 10.0);
        if( healthStage < 0 ) healthStage = 0;
        
        int xPos, yPos;
        String spaces;
        if(      currentHealth >= 100 ) spaces = "   "; 
        else if( currentHealth >= 10 )  spaces = "    ";
        else                            spaces = "    ";
        int xBuffer;
        if(      currentHealth >= 100 ) xBuffer = 0;
        else if( currentHealth >= 10 )  xBuffer = 10;
        else                            xBuffer = 27;
        
        //Display the current health / max health as text
        setText( 150, 50, currentHealth + spaces + maxHealth, 20, 20,
                     Color.BLACK, false, 695 + xBuffer, 353, LARGE_FONT_SIZE );

             if( healthStage >= 90 ) { xPos = 748; yPos = 312; }
        else if( healthStage == 80 ) { xPos = 740; yPos = 312; }
        else if( healthStage == 70 ) { xPos = 732; yPos = 312; }
        else if( healthStage == 60 ) { xPos = 724; yPos = 312; }
        else if( healthStage == 50 ) { xPos = 716; yPos = 312; }
        else if( healthStage == 40 ) { xPos = 708; yPos = 312; }
        else if( healthStage == 30 ) { xPos = 700; yPos = 312; }
        else if( healthStage == 20 ) { xPos = 692; yPos = 312; }
        else if( healthStage == 10 ) { xPos = 684; yPos = 312; }
        else                         { xPos = 676; yPos = 312; }
        
        if( healthStage < 100 )
            setTextImage( HEALTH_BAR + healthStage + ".png", "", 200, 50, xPos, yPos );
        
        PokemonActor otherPokemon = getOtherPokemon();
        healthRatio = (double)otherPokemon.getCurrentHP() / (double)otherPokemon.getHP();
        healthPercent = healthRatio * 10.0;
        healthStage = (int)(Math.floor( healthPercent ) * 10.0);
        if( healthStage < 0 ) healthStage = 0;
        
        if(      currentHealth >= 100 ) spaces = "   "; 
        else if( currentHealth >= 10 )  spaces = "    ";
        else                            spaces = "    ";
        
        if(      currentHealth >= 100 ) xBuffer = 0;
        else if( currentHealth >= 10 )  xBuffer = 10;
        else                            xBuffer = 27;
        
             if( healthStage >= 90 ) { xPos = 332; yPos = 118; }
        else if( healthStage == 80 ) { xPos = 324; yPos = 118; }
        else if( healthStage == 70 ) { xPos = 316; yPos = 118; }
        else if( healthStage == 60 ) { xPos = 308; yPos = 118; }
        else if( healthStage == 50 ) { xPos = 300; yPos = 118; }
        else if( healthStage == 40 ) { xPos = 292; yPos = 118; }
        else if( healthStage == 30 ) { xPos = 284; yPos = 118; }
        else if( healthStage == 20 ) { xPos = 276; yPos = 118; }
        else if( healthStage == 10 ) { xPos = 268; yPos = 118; }
        else                         { xPos = 260; yPos = 118; }
        
        if( healthStage < 100 )
            setTextImage( HEALTH_BAR + healthStage + ".png", "", 200, 50, xPos, yPos );
        
    }
    
    /**
     * Adds the exp bar to the screen for both Pokemon
     * 
     * @see addPokeInfo()
     * @see getCurrentPokemon()
     * @see getOtherPokemon()
     * @see PokemonActor.getTotalAddedExp()
     * @see PokemonActor.getRemainingExp()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset,
     *               Color color, boolean isBold, int x, int y )
     * @see PokemonActor.getCurrentLevel()
     */
    private void addExpBar() {
        PokemonActor pokemon = currentPlayer == PokemonTurn.PLAYER_1 ? getCurrentPokemon() : getOtherPokemon();
        int addedExp = pokemon.getTotalAddedExp();
        int expToLevel = pokemon.getRemainingExp() + addedExp;
        double expRatio = (double)addedExp / (double)expToLevel;
        double expPercent = expRatio * 10.0;
        int expStage = (int)(Math.floor( expPercent ) * 10.0);
        if( expStage < 0 ) expStage = 0;
        
        expStage = 100 - expStage; //flip stages because images are opposite of the way the hp images work
        
        int xPos, yPos;
             if( expStage >= 90 ) { xPos = 550; yPos = 366; }
        else if( expStage == 80 ) { xPos = 561; yPos = 366; }
        else if( expStage == 70 ) { xPos = 572; yPos = 366; }
        else if( expStage == 60 ) { xPos = 583; yPos = 366; }
        else if( expStage == 50 ) { xPos = 594; yPos = 366; }
        else if( expStage == 40 ) { xPos = 605; yPos = 366; }
        else if( expStage == 30 ) { xPos = 616; yPos = 366; }
        else if( expStage == 20 ) { xPos = 627; yPos = 366; }
        else if( expStage == 10 ) { xPos = 638; yPos = 366; }
        else                      { xPos = 649; yPos = 366; }
        
        if( expStage < 100 )
            setTextImage( EXP_BAR + expStage + ".png", "", 200, 50, xPos, yPos );
        
        PokemonActor attackingPokemon = getCurrentPokemon();
        PokemonActor defendingPokemon = getOtherPokemon();
        int attackingPokemonLevel = attackingPokemon.getCurrentLevel();
        int defendingPokemonLevel = defendingPokemon.getCurrentLevel();
        
        setText( 150, 50, attackingPokemonLevel + "", 20, 20,
                     Color.BLACK, false, 775, 297, LARGE_FONT_SIZE );
        setText( 150, 50, defendingPokemonLevel + "", 20, 20,
                     Color.BLACK, false, 360, 103, LARGE_FONT_SIZE ); 
    }
    
    /**
     * Add the names of the Pokemon to the display
     * 
     * @see addPokeInfo()
     */
    private void addNames() {
        PokemonActor attackingPokemon = getCurrentPokemon();
        PokemonActor defendingPokemon = getOtherPokemon();
        String attackingName = attackingPokemon.getName();
        String defendingName = defendingPokemon.getName();
        
        setText( 150, 50, attackingName, 20, 20,
                     Color.BLACK, false, 537, 297, LARGE_FONT_SIZE );
        setText( 150, 50, defendingName, 20, 20,
                     Color.BLACK, false, 120, 103, LARGE_FONT_SIZE ); 
    }
    
    /**
     * Determines whether a Move will hit based on the chance to hit. Any chance
     * to hit that is greater than 1.0 will hit
     * 
     * @param chanceToHit The calculated chance of the Move to hit
     * @return boolean True of the Move will hit, false otherwise
     */
    private boolean determineIfHit( double chanceToHit ) {
        Random random = new Random();
        
        if( chanceToHit >= random.nextDouble() ) return true;
        else                                     return false;
    }
    
    /**
     * Gets the current Pokemon
     * 
     * @return PokemonActor The current PokemonActor that is attacking
     */
    private PokemonActor getCurrentPokemon() {
        PokemonActor pokemon = null;
        if( currentPlayer == PokemonTurn.PLAYER_1 ) pokemon = this.first;
        else                                        pokemon = this.second;
        
        return pokemon;
    }
    
    /**
     * Gets the non-active (defending) Pokemon
     * 
     * @return PokemonActor The non-active (defending Pokemon
     */
    private PokemonActor getOtherPokemon() {
        return currentPlayer == PokemonTurn.PLAYER_1 ? this.second : this.first;
    }
    
    /**
     * Get the current Move being used in the battle
     * 
     * @param PokemonActor pokemon
     * @return Move The move that is currently being used in this battle
     * @see attack()
     * @see Pokemon.getMoves()
     */
    private Move getCurrentMove( PokemonActor pokemon ) {
        int moveNumber = 0;
        if(      currentSelection == SelectionArrow.TOP_LEFT )    moveNumber = 0;
        else if( currentSelection == SelectionArrow.BOTTOM_LEFT ) moveNumber = 1;
        else if( currentSelection == SelectionArrow.TOP_RIGHT )   moveNumber = 2;
        else                                                      moveNumber = 3;
        
        return pokemon.getMoves().get( moveNumber );
    }
    
    /**
     * Display the effects of the Move used against the Pokemon, including Status changes
     * and the effectivity of the Move if it had any kind of irregular effectiveness
     * 
     * @see act()
     * @see getCurrentPokemon()
     * @see getOtherPokemon()
     * @see Pokemon.getStatusText()
     * @see setText( String text )
     * @see setText( String text, int addXOffset, int addYOffset )
     */
    private void displayMoveEffect() {
        PokemonActor pokemon = getCurrentPokemon();
        PokemonActor otherPokemon = getOtherPokemon();
        
        String text = "";
        if( hasCriticalHit ) text = "A critical hit! It's ";
        else                 text = "It's ";
        
        if( currentEffectiveness != Pokedex.REG_EFFECTIVE ) {
            if(      currentEffectiveness == Pokedex.SUPER_EFFECTIVE )    text += "super effective!";
            else if( currentEffectiveness == Pokedex.NOT_VERY_EFFECTIVE ) text += "not very effective...";
            else                           /*Pokedex.NO_EFFECT*/          text = "It had no effect!";
            setText( text );
            
            PokemonActor.Status otherStatus = otherPokemon.getStatus();
            if( otherStatus != PokemonActor.Status.NORMAL && otherStatus != PokemonActor.Status.FAINTED ) {
                text = otherPokemon.getStatusText();
                setText( text, 0, 30 );
            }
        } else {
            PokemonActor.Status otherStatus = otherPokemon.getStatus();
            if( otherStatus != PokemonActor.Status.NORMAL && otherStatus != PokemonActor.Status.FAINTED ) {
                if( hasCriticalHit ) text = "A critical hit! " + otherPokemon.getStatusText();
                else                 text = otherPokemon.getStatusText();
                setText( text );
            }
        }
        currentTurn = BattleTurn.MOVE_RESULT;
    }
    
    /**
     * Show the results of the Battle
     * 
     * @see act()
     * @see getCurrentPokemon()
     * @see getOtherPokemon()
     * @see PokemonActor.getName()
     * @see setText( String text )
     * @see setText( String text, int addXOffset, int addYOffset )
     * @see PokemonActor.getStatus()
     */
    private void showResults() {
        PokemonActor pokemon = getCurrentPokemon();
        PokemonActor otherPokemon = getOtherPokemon();
        if( this.moveMissed ) {
            this.moveMissed = false;
            String text = pokemon.getName() + "'s attack missed!";
            setText( text );
            text = "It's " + otherPokemon.getName() + "'s turn.";
            setText( text, 0, 30 );
            currentTurn = BattleTurn.INTRO;
            currentPlayer = currentPlayer == PokemonTurn.PLAYER_1 ? PokemonTurn.PLAYER_2 : PokemonTurn.PLAYER_1;
        } else if( otherPokemon.getStatus() == PokemonActor.Status.FAINTED ) {
            String text = otherPokemon.getName() + " fainted!";
            setText( text );
            currentTurn = BattleTurn.AWARD_EXP;
        } else {
            String text = "It's " + otherPokemon.getName() + "'s turn.";
            setText( text );
            currentTurn = BattleTurn.INTRO;
            currentPlayer = currentPlayer == PokemonTurn.PLAYER_1 ? PokemonTurn.PLAYER_2 : PokemonTurn.PLAYER_1;
        }
    }
    
    /**
     * Awards exp to the winner, assesses levels, and congratulates the Player
     * 
     * @see act()
     * @see getCurrentPokemon()
     * @see getOtherPokemon()
     * @see PokemonActor.getPokedex()
     * @see Pokedex.getExperienceFromBattle( PokemonActor winner, PokemonActor loser )
     * @see PokemonActor.getName()
     * @see setText( String text )
     * @see PokemonActor.addExp( int exp )
     * @see setText( String text, int addXOffset, int addYOffset )
     * @see PokemonActor.setTotalWins( int wins )
     * @see PokemonActor.getTotalWins()
     */
    private void awardExp() {
        PokemonActor pokemon = getCurrentPokemon();
        PokemonActor otherPokemon = getOtherPokemon();
        int expGained = pokemon.getPokedex().getExperienceFromBattle( pokemon, otherPokemon );
        String text = pokemon.getName() + " won! " + pokemon.getName() + " gained " + expGained + " experience.";
        setText( text );
        
        boolean leveledUp = pokemon.addExp( expGained );
        if( leveledUp ) {
            text = pokemon.getName() + " has leveled up!";
            setText( text, 0, 30 );
        }
        
        pokemon.setTotalWins( pokemon.getTotalWins() + 1 );
        
        currentTurn = BattleTurn.OUTRO;
    }
    
    /**
     * Change the world back to the PokeArena world
     * 
     * @see act()
     * @see removeObjects( ArrayList<Actor> list )
     * @see Greenfoot.setWorld( World world )
     * @see getCurrentPokemon()
     */
    private void changeWorlds() {
        bgMusic.stop();
        bgMusic = null;
        removeObjects( activeActors );
        PokemonActor pokemon = getCurrentPokemon();
        PokeArena world = new PokeArena( pokemon );
        Greenfoot.setWorld( world );
    }
    
    /**
     * Sets the direction of the TextImage change based on the new SelectionArrow location
     * 
     * @see changeSelection()
     * @see clearText()
     * @see setMenu()
     * @see setFightMenu()
     * @see setPokedexMenu()
     */
    private void setSelection() {
        clearText();
        if(      currentTurn == BattleTurn.MENU ) 
            setMenu();
        else if( currentTurn == BattleTurn.SELECT_MOVE )
            setFightMenu();
        else if( currentTurn == BattleTurn.POKEDEX_MENU )
            setPokedexMenu();
        else if( currentTurn == BattleTurn.CHOOSE_MOVE_FOR_ITEM )
            chooseMove();
    }
    
    /**
     * Change to the selected main menu option. Options include
     * the FIGHT menu, POKEDEX menu, ITEM menu, and RUN menu
     * 
     * @see act()
     * @see setFightMenu()
     * @see setPokedexMenu()
     * @see setItemMenu()
     * @see setRunMenu()
     */
    private void selectMenu() {
        if(        currentSelection == SelectionArrow.TOP_LEFT ) {
            setFightMenu();
        } else if( currentSelection == SelectionArrow.BOTTOM_LEFT ) {
            currentSelection = SelectionArrow.TOP_LEFT;
            setPokedexMenu();
        } else if( currentSelection == SelectionArrow.TOP_RIGHT ) {
            currentSelection = SelectionArrow.TOP_LEFT;
            setItemMenu();
        } else {
            currentSelection = SelectionArrow.TOP_LEFT;
            setRunMenu();
        }
    }
    
    /**
     * Remove the TextImages from the screen and clear the images from the ArrayList
     * 
     * @see textList global variable
     * @see World.removeObjects( List<Actor> list )
     */
    private void clearText() {
        removeObjects( textList );
        textList.clear();
    }
    
    /**
     * Set the text area to the main menu text
     * 
     * @see PokemonActor.getName()
     * @see setText( String text )
     * @see setMenu( String imageLoc )
     */
    private void setMenu() {
        currentTurn = BattleTurn.MENU;
        totalSelectionOptions = 4;
        
        String pokemonName;
        if( currentPlayer == PokemonTurn.PLAYER_1 ) pokemonName = first.getName();
        else                                        pokemonName = second.getName();
        
        String text = "What will " + pokemonName.toUpperCase() + " do?";
        setText( text );
        
        if(      currentSelection == SelectionArrow.TOP_LEFT )    setMenu( MENU_TOP_LEFT );
        else if( currentSelection == SelectionArrow.BOTTOM_LEFT ) setMenu( MENU_BOTTOM_LEFT );
        else if( currentSelection == SelectionArrow.TOP_RIGHT )   setMenu( MENU_TOP_RIGHT );
        else                                                      setMenu( MENU_BOTTOM_RIGHT );
    }
    
    /**
     * Sets the menu image and adds the objects to the textList
     * and to the world
     * 
     * @param imageLoc The image path of the menu image
     * @see setMenu()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset,
     *               Color color, boolean isBold, int x, int y )
     * @see World.addObject( Actor actor, int x, int y )
     * @see TEXT_BOX_X, TEXT_BOX_Y, MENU_TEXT_BOTTOM
     */
    private void setMenu( String imageLoc ) {
        //Add menu image and "FIGHT" text
        setTextImage( imageLoc, "FIGHT", 41, 46, TEXT_BOX_X + 225, TEXT_BOX_Y );
        
        //Add "POKEDEX" text
        setText( 277, 100, "POKEDEX", 27, 76,
                 Color.BLACK, false /* non-bold text */, TEXT_BOX_X + 225, TEXT_BOX_Y );
        
        //Add "ITEM" text
        setText( 277, 100, "ITEM", 152, 36,
                 Color.BLACK, false /* non-bold text */, TEXT_BOX_X + 225, TEXT_BOX_Y );
        
        //Add "RUN" text
        setText( 277, 100, "RUN", 152, 75,
                 Color.BLACK, false /* non-bold text */, TEXT_BOX_X + 225, TEXT_BOX_Y );
    }
    
    /**
     * Sets the menu when FIGHT is selected within the main menu
     * 
     * @see selectMenu()
     * @see getAllMoveNames( PokemonActor pokemon )
     * @see getAllMovePP( PokemonActor pokemon )
     * @see getAllMoveTypes( PokemonActor pokemon )
     * @see PokemonActor.getMoves()
     * @see Move.getName()
     * @see PokemonActor.setCurrentMove( Move currentMove )
     * @see PokemonActor.getCurrentMovePP( String moveName )
     * @see setText( int width, int height, String text, int xOffset, int yOffset,
     *                    Color color, boolean isBold, int x, int y )
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see LARGE_MENU_TOP_LEFT, LARGE_MENU_BOTTOM_LEFT, LARGE_MENU_TOP_RIGHT, LARGE_MENU_BOTTOM_RIGHT, MENU
     */
    private void setFightMenu() {
        currentTurn = BattleTurn.SELECT_MOVE;
        
        PokemonActor currentPokemon = currentPlayer == PokemonTurn.PLAYER_1 ? this.first : this.second;
        
        //Get list of move names
        String[] moveNames = getAllMoveNames( currentPokemon );
        
        //Get list of move pps
        int[] movePP = getAllMovePP( currentPokemon );
        
        //Get list of move types
        String[] moveTypes = getAllMoveTypes( currentPokemon );
        
        /*Set Strings for TextImages
         * 
         * Move 1 -- Top left
         * Move 2 -- Bottom left
         * Move 3 -- Top right
         * Move 4 -- Bottom right
         * 
         * A Pokemon that has less than four moves shows no text in that area
         */
        ArrayList<Move> moves = currentPokemon.getMoves();
        String move1Name = moves.get(0).getName();
        String move2Name, move3Name, move4Name;
        move2Name = move3Name = move4Name = "";
        
        totalSelectionOptions = moveNames.length;
        String moveInfo1 = "PP: " + movePP[0] + "\nType: " + moveTypes[0];
        String moveInfo2, moveInfo3, moveInfo4;
        moveInfo2 = moveInfo3 = moveInfo4 = "";
        if( totalSelectionOptions >= 2 ) {
            move2Name = moves.get(1).getName();
            moveInfo2 = "PP: " + movePP[1] + "\nType: " + moveTypes[1];
        }
        if( totalSelectionOptions >= 3 ) {
            move3Name = moves.get(2).getName();
            moveInfo3 = "PP: " + movePP[2] + "\nType: " + moveTypes[2];
        }
        if( totalSelectionOptions >= 4 ) {
            move4Name = moves.get(3).getName();
            moveInfo4 = "PP: " + movePP[3] + "\nType: " + moveTypes[3];
        }
        
        //Set move TextImage
        int xOffset = 46;
        int yOffset = 44;
        if(      currentSelection == SelectionArrow.TOP_LEFT ) {
            currentPokemon.setCurrentMove( moves.get( 0 ) );
            setTextImage( LARGE_MENU_TOP_LEFT, move1Name, xOffset, yOffset, LARGE_TEXT_BOX_X, LARGE_TEXT_BOX_Y );
        } else if( currentSelection == SelectionArrow.BOTTOM_LEFT ) {
            currentPokemon.setCurrentMove( moves.get( 1 ) );
            setTextImage( LARGE_MENU_BOTTOM_LEFT, move1Name, xOffset, yOffset, LARGE_TEXT_BOX_X, LARGE_TEXT_BOX_Y );
        } else if( currentSelection == SelectionArrow.TOP_RIGHT ) {
            currentPokemon.setCurrentMove( moves.get( 2 ) );
            setTextImage( LARGE_MENU_TOP_RIGHT, move1Name, xOffset, yOffset, LARGE_TEXT_BOX_X, LARGE_TEXT_BOX_Y );
        } else {
            currentPokemon.setCurrentMove( moves.get( 3 ) );
            setTextImage( LARGE_MENU_BOTTOM_RIGHT, move1Name, xOffset, yOffset, LARGE_TEXT_BOX_X, LARGE_TEXT_BOX_Y );
        }
            
        if( totalSelectionOptions >= 2 )
            setText( 433, 121, move2Name, 41, 88,
                     Color.BLACK, false /* bold text */, LARGE_TEXT_BOX_X, LARGE_TEXT_BOX_Y );
            
        if( totalSelectionOptions >= 3 )
            setText( 433, 121, move3Name, 239, 44,
                     Color.BLACK, false /* bold text */, LARGE_TEXT_BOX_X, LARGE_TEXT_BOX_Y );
        
        if( totalSelectionOptions >= 4 )
            setText( 433, 121, move4Name, 80, 88,
                     Color.BLACK, false /* bold text */, LARGE_TEXT_BOX_X, LARGE_TEXT_BOX_Y );
        
        //Set PP / Move Type TextImage
        xOffset = 75;
        yOffset = 56;
        if(      currentSelection == SelectionArrow.TOP_LEFT )
            setTextImage( MENU, moveInfo1, xOffset, yOffset, TEXT_BOX_X + 225, TEXT_BOX_Y + 1 );
        else if( currentSelection == SelectionArrow.BOTTOM_LEFT )
            setTextImage( MENU, moveInfo2, xOffset, yOffset, TEXT_BOX_X + 225, TEXT_BOX_Y + 1 );
        else if( currentSelection == SelectionArrow.TOP_RIGHT )
            setTextImage( MENU, moveInfo3, xOffset, yOffset, TEXT_BOX_X + 225, TEXT_BOX_Y + 1 );
        else
            setTextImage( MENU, moveInfo4, xOffset, yOffset, TEXT_BOX_X + 225, TEXT_BOX_Y + 1 );
    }
    
    /**
     * Gets a list of the names of the moves that this Pokemon knows
     * 
     * @param pokemon The Pokemon to get move names from
     * @return String[] The list of Pokemon move names
     * @see setFightMenu()
     * @see PokemonActor.getMoves()
     * @see Move.getName()
     */
    private String[] getAllMoveNames( PokemonActor pokemon ) {
        ArrayList<String> moveNames = new ArrayList<String>();
        ArrayList<Move> moves = pokemon.getMoves();
        int totalMoves = moves.size();
        for( int i = 0; i < totalMoves; i++ ) {
            moveNames.add( moves.get(i).getName() );
        }

        String[] temp = new String[ totalMoves ];
        return (String[])moveNames.toArray( temp );
    }
    
    /**
     * Gets a list of the current PP of the moves that this Pokemon knows
     * 
     * @param The Pokemon to get move current PP values from
     * @return int[] The list of move current PP
     * @see setFightMenu()
     * @see PokemonActor.getMoves()
     * @see PokemonActor.getCurrentMovePP( int moveNumber )
     */
    private int[] getAllMovePP( PokemonActor pokemon ) {
        int totalMoves = pokemon.getMoves().size();
        int[] movePP = new int[ totalMoves ];
        for( int i = 0; i < totalMoves; i++ ) {
            movePP[i] = pokemon.getCurrentMovePP( i );
        }
        
        return movePP;
    }
    
    /**
     * Gets a list of the types of the moves that this Pokemon knows
     * 
     * @param pokemon The Pokemon to get the list of move types from
     * @return String[] The list of this Pokemon's move types
     * @see setFightMenu()
     * @see PokemonActor.getMoves()
     * @see Enum.name()
     */
    private String[] getAllMoveTypes( PokemonActor pokemon ) {
        ArrayList<Move> moves = pokemon.getMoves();
        int totalMoves = moves.size();
        String[] moveTypes = new String[ totalMoves ];
        for( int i = 0; i < totalMoves; i++ ) {
            moveTypes[i] = moves.get(i).getType().name();
        }
        
        return moveTypes;
    }
    
    /**
     * Sets the menu when POKEDEX is selected within the main menu
     * 
     * @see selectMenu()
     * @see setText( String text )
     * @see setPokedexMenu( String imageLoc, String option1, String option2, String option3, String option4 )
     */
    private void setPokedexMenu() {
        currentTurn = BattleTurn.POKEDEX_MENU;
        if( pageNumber == 1 ) totalSelectionOptions = 4;
        else                  totalSelectionOptions = 3;

        String text = "Pick a Pokedex Option:";
        setText( text );
        if( pageNumber == 1 ) {
            if(      currentSelection == SelectionArrow.TOP_LEFT )    setPokedexMenu( MENU_TOP_LEFT, "POKEMON", "MOVE", "EFFECTIVITY", "NEXT PAGE" );
            else if( currentSelection == SelectionArrow.BOTTOM_LEFT ) setPokedexMenu( MENU_BOTTOM_LEFT, "POKEMON", "MOVE", "EFFECTIVITY", "NEXT PAGE" );
            else if( currentSelection == SelectionArrow.TOP_RIGHT )   setPokedexMenu( MENU_TOP_RIGHT, "POKEMON", "MOVE", "EFFECTIVITY", "NEXT PAGE" );
            else                                                      setPokedexMenu( MENU_BOTTOM_RIGHT, "POKEMON", "MOVE", "EFFECTIVITY", "NEXT PAGE" );
        } else { //pageNumber == 2
            if(      currentSelection == SelectionArrow.TOP_LEFT )    setPokedexMenu( MENU_TOP_LEFT, "EXP", "STATUS", "MENU", "" );
            else if( currentSelection == SelectionArrow.BOTTOM_LEFT ) setPokedexMenu( MENU_BOTTOM_LEFT, "EXP", "STATUS", "MENU", "" );
            else if( currentSelection == SelectionArrow.TOP_RIGHT )   setPokedexMenu( MENU_TOP_RIGHT, "EXP", "STATUS", "MENU", "" );
            else                                                      setPokedexMenu( MENU_BOTTOM_RIGHT, "EXP", "STATUS", "MENU", "" );
        }
    }
    
    /**
     * Sets the menu image and adds the objects to the textList
     * and to the world
     * 
     * @param imageLoc The image path of the menu image
     * @param option1, option2, option3, option4 The String titles for the menu options (a max of four)
     * @see setPokedexMenu()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset,
     *               Color color, boolean isBold, int x, int y )
     * @see World.addObject( Actor actor, int x, int y )
     * @see TEXT_BOX_X, TEXT_BOX_Y, MENU_TEXT_BOTTOM
     */
    private void setPokedexMenu( String imageLoc, String option1, String option2, String option3, String option4 ) {
        //Add menu image and "Pokemon" text
        setTextImage( imageLoc, option1, 41, 46, TEXT_BOX_X + 225, TEXT_BOX_Y );
        
        //Add "Move" text
        if( totalSelectionOptions >= 2 )
        setText( 277, 100, option2, 27, 76,
                 Color.BLACK, false /* non-bold text */, TEXT_BOX_X + 225, TEXT_BOX_Y );
        
        //Add "Effectivity" text
        if( totalSelectionOptions >= 3 )
        setText( 277, 100, option3, 152, 36,
                 Color.BLACK, false /* non-bold text */, TEXT_BOX_X + 225, TEXT_BOX_Y );
        
        //Add "Next Page" text
        if( totalSelectionOptions >= 4 )
        setText( 277, 100, option4, 152, 75,
                 Color.BLACK, false /* non-bold text */, TEXT_BOX_X + 225, TEXT_BOX_Y );
    }
    
    /**
     * Sets the Pokedex display based on the object chosen
     * 
     * @see act()
     * @see pokedexSearch()
     * @see pokedexMove()
     * @see pokedexEffectivity()
     * @see pokedexExperience()
     * @see pokedexStatus()
     * @see setPokedexMenu()
     * @see setMenu()
     */
    private void setPokedex() {
        if(        currentSelection == SelectionArrow.TOP_LEFT && pageNumber == 1 ) {
            currentTurn = BattleTurn.POKEDEX_POKEMON;
            pokedexSearch();
        } else if( currentSelection == SelectionArrow.BOTTOM_LEFT && pageNumber == 1 ) {
            currentTurn = BattleTurn.POKEDEX_MOVE;
            pokedexMove();
        } else if( currentSelection == SelectionArrow.TOP_RIGHT && pageNumber == 1 ) {
            currentTurn = BattleTurn.POKEDEX_EFFECTIVITY;
            pokedexEffectivity();
        } else if(              /*SelectionArrow.BOTTOM_RIGHT*/    pageNumber == 1 ) {
            pageNumber = 2;
            currentSelection = SelectionArrow.TOP_LEFT;
            setPokedexMenu();
        } else if( currentSelection == SelectionArrow.TOP_LEFT && pageNumber == 2 ) {
            currentTurn = BattleTurn.POKEDEX_EXPERIENCE;
            pageNumber = 1;
            totalSelectionOptions = 4;
            pokedexExperience();
        } else if( currentSelection == SelectionArrow.BOTTOM_LEFT && pageNumber == 2 ) {
            currentTurn = BattleTurn.POKEDEX_STATUS;
            pageNumber = 1;
            totalSelectionOptions = 4;
            pokedexStatus();
        } else {
            pageNumber = 1;
            totalSelectionOptions = 4;
            currentTurn = BattleTurn.MENU;
            currentSelection = SelectionArrow.TOP_LEFT;
            setMenu();
        }
    }
    
    /**
     * Adds a Pokedex to the screen and opens the Pokemon search interface
     * 
     * @see setPokedex()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset, Color color, boolean isBold, int x, int y )
     * @see World.addObject( Actor actor, int x, int y )
     */
    private void pokedexSearch() {
        setTextImage( POKEDEX_POKEMON_SEARCH, "Enter a", 63, 103, 230, 350 );
        setText( 200, 100, "Pokemon name:", 20, 20, Color.BLACK, false, 145, 360 );
        TextField pokedexTextField = new TextField( 135, 30, true );
        addObject( pokedexTextField, 134, 367 );
        activeTextField = pokedexTextField;
    }
    
    /**
     * Given the text entered into the Pokedex Pokemon search, display Pokemon data or
     * show the user that this Pokemon was not found
     * 
     * @param text The text entered into the Pokedex Pokemon search
     * @see pokedexSearch()
     * @see act()
     * @see PokemonActor.getName()
     * @see PokemonActor.getPokemonNumber()
     * @see PokemonActor.getWeight()
     * @see PokemonActor.getHeight()
     * @see PokemonActor.getDescription()
     * @see PokemonActor.getBattleImageName()
     * @see PokemonActor.getType()
     * @see getCurrentPokemon()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setTextImage( GreenfootImage image, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset, Color color, boolean isBold, int x, int y )
     * @see addTextToRegion( String text, int regionWidth, int regionHeight, int xOffset, int yOffset, int x, int y,
     *                       double leadingRatio )
     * @see PokeWorld.scaleImageToMaxLimit( GreenfootImage image, int limitWidth, int limitHeight )
     * @see PokeWorld.capFirstLetter( String token )
     */
    private void pokedexSearch( String text ) {
        PokemonActor pokemon = getCurrentPokemon();
        Pokedex pokedex = pokemon.getPokedex();
        String pokedexFileName = pokedex.getPokedexFileName();
        
        //If the search has an incorrect query, or if the Pokemon being searched for has not been found yet
        if( !pokedex.hasPokemonInfo( pokedexFileName, text ) ) {
            setTextImage( POKEDEX_POKEMON_INFO, "", 40, 190, 410, 220 );
            setText( POKEDEX_POKEMON_INFO_WIDTH, POKEDEX_POKEMON_INFO_HEIGHT, "Cannot find the Pokemon \"" + text + "\"!",
                     20, 20, Color.BLACK, false, 400, 300 );
            setTextImage( UNKNOWN_POKEMON, "", 0, 0, 280, 160 );
            setText( 200, 100, "?    ?", 20, 20, Color.BLACK, false, 467, 152 );
            setText( 200, 100, "?", 20, 20, Color.BLACK, false, 455, 182 );
            setText( 200, 100, "?", 20, 20, Color.BLACK, false, 473, 212 );
            setText( 200, 100, "?", 20, 20, Color.BLACK, false, 473, 242 );
            
            currentTurn = BattleTurn.INTRO;
            currentSelection = SelectionArrow.TOP_LEFT;
            remainingText = "";
            
            return;
        }
        
        //Get Pokemon information from pokedexFileName
        Scanner scanner = null;
        try {
            scanner = new Scanner( new File( pokedexFileName ) );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        String name, battleImage, type, description;
        name = battleImage = type = description = "";
        int number = 0;
        double height, weight;
        height = weight = 0.0;
        while( scanner.hasNextLine() ) {
            String line = scanner.nextLine();
            if( line.equals("@@@") && scanner.nextLine().toLowerCase().equals( text ) ) {
                name        = text;
                number      = Integer.parseInt( scanner.nextLine() );
                battleImage = scanner.nextLine();
                type        = scanner.nextLine();
                height      = Double.parseDouble( scanner.nextLine() );
                weight      = Double.parseDouble( scanner.nextLine() );
                description = scanner.nextLine();
            }
        }
        
        //Set description and main image
        setTextImage( POKEDEX_POKEMON_INFO, "", 40, 190, 410, 220 );
        String remainingDescription = remainingText.equals("") ? description : remainingText;
        int yOffset = 0;
        String excessText = addTextToRegion( remainingDescription, POKEDEX_POKEMON_INFO_WIDTH, POKEDEX_POKEMON_INFO_HEIGHT,
                                             20, 20, 400, 300, 2.0 );
        if( excessText.equals("") ) {
            currentTurn = BattleTurn.INTRO;
            currentSelection = SelectionArrow.TOP_LEFT;
            remainingText = "";
        } else {
            remainingText = excessText;
        }
        
        //Set Pokemon image
        GreenfootImage scaledImage = new GreenfootImage( battleImage );
        PokeWorld.scaleImageToMaxLimit( scaledImage, 114, 105 );
        setTextImage( scaledImage, "", 0, 0, 280, 160 );
        
        //Set Pokemon name, number, type, height, and weight
        String revisedName = PokeWorld.capFirstLetter( name );
        setText( 200, 100, number + "    " + revisedName, 20, 20, Color.BLACK, false, 467, 152 );
        setText( 200, 100, type, 20, 20, Color.YELLOW, true, 415, 182 );
        
        String formattedHeight = (int)height + "'" + (int)((height - (double)((int)height))*10.0) + "\"";
        setText( 200, 100, formattedHeight, 20, 20, Color.BLACK, false, 473, 212 );
        setText( 200, 100, weight + "", 20, 20, Color.BLACK, false, 473, 242 );
    }
    
    /**
     * Add and print text to a region such that all lines fit within the given region.
     * If not all the text fits within the region, return the text that does not fit
     * 
     * @param text The text to add to the region
     * @param regionWidth The width of the region to fit the text
     * @param regionHeight The height of the region to fit the text
     * @param xOffset The x offset of the text from the top left corner
     * @param yOffset The y offset of the text from the top left corner
     * @param x The x coordinate of the top left corner of the region to place the text
     * @param y The y coordinate of the top left corner of the region to place the text
     * @param leadingRatio The ratio used to determine the space between lines. The larger the leadingRatio, the smaller
     *                     the space inbetween text
     * @return String The text that does not fit within the specified region. If all the text fits, returns ""
     * @see pokedexSearch( String text )
     */
    private String addTextToRegion( String text, int regionWidth, int regionHeight, int xOffset, int yOffset, int x, int y,
                                    double leadingRatio ) {
        int charWidth = (int)((double)currentFontSize * ( 1.0 / pixelToFontSizeRatio ) );
        int maxCharsPerLine = regionWidth / charWidth - 1;
        int leading = (int)(currentFontSize / leadingRatio);
        int maxLines = regionHeight / (currentFontSize + leading);
        
        //Parse text into correctly sized lines. Only parse at spaces
        ArrayList<String> linesOfText = new ArrayList<String>();
        while( text.length() > maxCharsPerLine ) {
            int indexOfLastSpace = text.lastIndexOf( " ", maxCharsPerLine );
            if( indexOfLastSpace == PokeWorld.ERROR ) indexOfLastSpace = maxCharsPerLine;
            String line = text.substring( 0, indexOfLastSpace );
            linesOfText.add( line );
            text = text.substring( indexOfLastSpace + 1, text.length() );
        }
        linesOfText.add( text );
        
        //Set text to region
        int linesOfTextSize = linesOfText.size();
        for( int i = 0; i < linesOfTextSize && i < maxLines; i++ ) {
            setText( regionWidth, regionHeight, linesOfText.get(i), xOffset, yOffset,
                     Color.BLACK, false, x, y + i * ( currentFontSize + leading ) );
        }
        
        //If there are more lines then the region can hold, return the excess text
        String excessText = "";
        if( linesOfTextSize > maxLines ) {
            for( int i = maxLines; i < linesOfTextSize; i++ ) {
                excessText += linesOfText.get(i) + " ";
            }
        }
        
        return excessText;
    }
    
    /**
     * Adds the given Pokemon to each Pokemon's pokedex
     * 
     * @param pokemon The Pokemon whose Pokedex is being added to
     * @param pokemonList The list of PokemonActors to add to given Pokemon's Pokedex
     * @see PokeBattle(...)
     * @see getPokedexTextFiles()
     * @see PokemonActor.getName()
     * @see PokemonActor.getUniqueID()
     * @see PokemonActor.getPokedex()
     * @see Pokedex.setPokedexFileName( String pokedexFileName );
     * @see File.getName()
     * @see createNewPokedexTextFile( PokemonActor pokemon )
     * @see PokeWorld.addToFile( String fileName, String text )
     * @see getPokedexPokemonInfo( PokemonActor pokemon )
     */
    private void addToPokedex( PokemonActor pokemon, PokemonActor[] pokemonList ) {
        String name = pokemon.getName();
        final String POKEDEX_TEXT_FILE_SUFFIX = "_pokedex.txt";
        String fileName = name + pokemon.getUniqueID() + POKEDEX_TEXT_FILE_SUFFIX;
        pokemon.getPokedex().setPokedexFileName( fileName );
        
        //Get Pokedex text files
        File[] fileList = getPokedexTextFiles();
        boolean fileExists = false;
        File pokedexFile = null;
        for( File file: fileList ) {
            if( file.getName().equals( fileName ) ) {
                fileExists = true;
                pokedexFile = file;
                break;
            }
        }
        
        String previousFileText = "";
        //If there is no pokedex text file for this Pokemon, create a new one.
        if( !fileExists ) pokedexFile = createNewPokedexTextFile( pokemon );
        
        //Add all Pokemon information to the Pokedex Pokemon information file
        for( PokemonActor pokemonFound: pokemonList ) {
            if( !pokemon.getPokedex().hasPokemonInfo( fileName, pokemonFound.getName() ) )
                PokeWorld.addToFile( fileName, getPokedexPokemonInfo( pokemonFound ) );
        }
    }
    
    /**
     * Create a new Pokedex text file for the given Pokemon and
     * fill the new File with the information for the given Pokemon (the
     * information for itself)
     * 
     * Format:
     * Pokemon Name
     * Pokemon Number
     * Pokemon Image Path
     * Pokemon Type
     * Pokemon Height
     * Pokemon Weight
     * Pokemon Description
     * @@@
     * 
     * @param pokemon The Pokemon to add the new Pokedex file to and to write that Pokemon's info into
     * @return File The newly written Pokedex text file
     * @see addToPokedex( PokemonActor pokemon, PokemonActor[] pokemonList )
     * @see PokemonActor.getName()
     * @see File.createNewFile()
     * @see getPokedexPokemonInfo( PokemonActor pokemon )
     * @see PokeWorld.writeToFile( String fileName, String text )
     * @see File.getName()
     */
    private File createNewPokedexTextFile( PokemonActor pokemon ) {
        String name = pokemon.getName();
        File pokedexTextFile = new File( name + pokemon.getUniqueID() + "_pokedex.txt");
        try {
            pokedexTextFile.createNewFile();
        } catch( IOException e ) {
            e.printStackTrace();
        }
        
        String text = getPokedexPokemonInfo( pokemon );
        
        PokeWorld.writeToFile( pokedexTextFile.getName(), text );
                      
        return pokedexTextFile;
    }
    
    /**
     * Gets the list of information about a Pokemon that is used for storing
     * information within the Pokedex using the <POKEMON_NAME>_pokedex.txt file
     * 
     * @param pokemon The Pokemon to get information from
     * @return String The list of information that will be stored within the text file
     * @see PokemonActor.getName()
     * @see PokemonActor.getHeight()
     * @see PokemonActor.getWeight()
     * @see PokemonActor.getDescription()
     * @see PokemonActor.getType()
     * @see PokemonActor.getImageName()
     * @see PokemonActor.getPokemonNumber()
     * @see System.lineSeparator()
     */
    private String getPokedexPokemonInfo( PokemonActor pokemon ) {
        String name        = pokemon.getName();
        double height      = pokemon.getHeight();
        double weight      = pokemon.getWeight();
        String description = pokemon.getDescription();
        String type        = pokemon.getType();
        String imagePath   = pokemon.getImageName();
        int pokemonNumber  = pokemon.getPokemonNumber();
        
        String s = System.lineSeparator();
        
        String text = "@@@" + s + name + s + pokemonNumber + s + imagePath + s + type + s +
                      height + s + weight + s + description + s;
        
        return text;
    }
    
    /**
     *  Returns the list of Pokemon pokedex files in the current directory
     *
     *  @return File[] The list of Pokemon pokedex files in the current directory
     *  @see addToPokedex( PokemonActor pokemon, PokemonActor[] pokemonList )
     */
    private static File[] getPokedexTextFiles() {
        File dir = new File(".");
        File[] filesList = dir.listFiles();
        ArrayList<File> newFileList = new ArrayList<File>();
      
        for( File file : filesList ) {
            if( file.isFile() ) {
                if( !file.toString().contains(".java") &&
                    !file.toString().contains(".class") &&
                    !file.toString().contains(".ctxt") &&
                    !file.toString().contains("pokemonActorUnmodifiedMethods") &&
                    !file.toString().contains("pokemonMoves") &&
                    !file.toString().contains("README") ) {
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
     * Displays the Pokedex to allow the user to search for a given Move
     * 
     * @see setPokedex()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset, Color color, boolean isBold, int x, int y )
     * @see World.addObject( Actor actor, int x, int y )
     */
    private void pokedexMove() {
        setTextImage( POKEDEX_POKEMON_SEARCH, "Enter a", 63, 103, 230, 350 );
        setText( 200, 100, "Move name:", 20, 20, Color.BLACK, false, 145, 360 );
        TextField pokedexTextField = new TextField( 135, 30, true );
        addObject( pokedexTextField, 134, 367 );
        activeTextField = pokedexTextField;
    }
    
    /**
     * Displays the information for the Move that was entered into the Pokedex search.
     * If the Move does not exist, the UNKNOWN_MOVE template is displayed instead
     * 
     * @param text The name of the Move being search for
     * @see act()
     * @see getCurrentPokemon()
     * @see PokemonActor.getPokedex()
     * @see Pokedex.getPokedexMoveFileName()
     * @see Pokedex.moveExists( String moveName )
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset, Color color, boolean isBold, int x, int y )
     * @see addTextToRegion( String text, int regionWidth, int regionHeight, int xOffset, int yOffset, int x, int y,
     *                       double leadingRatio )
     * @see PokeWorld.getTextWidth( String text, int fontSize, double pixelToFontRatio )
     */
    private void pokedexMove( String text ) {
        PokemonActor pokemon = getCurrentPokemon();
        Pokedex pokedex = pokemon.getPokedex();
        String moveFileName = pokedex.getPokedexMoveFileName();
        
        //If the search has an incorrect query, or if the Move being searched for does not exist
        int xLoc = 410;
        try {
            if( !pokedex.moveExists( text ) ) {
                setTextImage( POKEDEX_MOVE_INFO, "", 40, 190, xLoc, 220 );
                setText( 200, 100, "Cannot find Move!", 20, 20, Color.BLACK, true, 317, 250 );
                int textWidth = PokeWorld.getTextWidth( "?", MEDIUM_FONT_SIZE, 1.38 );
                setText( 200, 100, "?", 20, 20, Color.WHITE, true, xLoc - 20 + 100 - (textWidth / 2), 162 );
                setText( 200, 100, "?", 20, 20, Color.WHITE, false, 450, 337 );
                setText( 200, 100, "?", 20, 20, Color.WHITE, false, 450, 362 );
                setText( 200, 100, "?", 20, 20, Color.WHITE, false, 640, 337 );
                setText( 200, 100, "?", 20, 20, Color.WHITE, false, 640, 362 );
                setText( 200, 100, "?", 20, 20, Color.WHITE, false, 640, 387 );
                
                currentTurn = BattleTurn.INTRO;
                currentSelection = SelectionArrow.TOP_LEFT;
                remainingText = "";
                
                return;
            }
        } catch( Pokedex.InvalidMovesFileException e ) {
            e.printStackTrace();
        }
        
        //Get Pokemon information from pokedexFileName
        Scanner scanner = null;
        try {
            scanner = new Scanner( new File( moveFileName ) );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        
        String name, type, category, description, power, accuracy, pp;
        name = type = category = power = accuracy = pp = description = "";
        while( scanner.hasNextLine() ) {
            String line = scanner.nextLine();
            String[] tokens = line.split("@");
            if( tokens[0].equals( text.toUpperCase() ) ) {
                name        = tokens[0];
                type        = tokens[1];
                category    = tokens[2];
                power       = tokens[3];
                accuracy    = tokens[4];
                pp          = tokens[5];
                description = tokens[6];
            }
        }
        
        //Set description and main image
        setTextImage( POKEDEX_MOVE_INFO, "", 40, 190, xLoc, 220 );
        String remainingDescription = remainingText.equals("") ? description : remainingText;
        int yOffset = 0;
        String excessText = addTextToRegion( remainingDescription, POKEDEX_MOVE_INFO_WIDTH, POKEDEX_MOVE_INFO_HEIGHT,
                                             20, 20, 393, 235, 2.0 );
        if( excessText.equals("") ) {
            currentTurn = BattleTurn.INTRO;
            currentSelection = SelectionArrow.TOP_LEFT;
            remainingText = "";
        } else {
            remainingText = excessText;
        }
        
        //Set Move name, type, category, power, accuracy, and pp. Type and category are displayed as images
        int textWidth = PokeWorld.getTextWidth( name, MEDIUM_FONT_SIZE, 1.38 );
        setText( 200, 100, name, 20, 20, Color.WHITE, true, xLoc - 20 + 100 - (textWidth / 2), 162 );
        setTextImage( TYPE_LOC_GENERIC.replace( "@", type.toLowerCase() ), "", 0, 0, 373, 300 );
        setTextImage( CATEGORY_LOC_GENERIC.replace( "@", category.toLowerCase() ), "", 0, 0, 373, 326 );
        setText( 200, 100, power, 20, 20, Color.WHITE, false, 640, 337 );
        setText( 200, 100, accuracy, 20, 20, Color.WHITE, false, 640, 362 );
        setText( 200, 100, pp, 20, 20, Color.WHITE, false, 640, 387 );
    }
    
    /**
     * Displays the Pokedex to allow the user to select two types and get the effectivity multiplier as a result
     * 
     * @see setPokedex()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset, Color color, boolean isBold, int x, int y )
     * @see World.addObject( Actor actor, int x, int y )
     */
    private void pokedexEffectivity() {
        setTextImage( POKEDEX_POKEMON_SEARCH, "Select two types", 36, 103, 230, 350 );
        setText( 200, 100, "(order matters):", 20, 20, Color.BLACK, false, 145, 360 );
        
        double scaleRatio = 0.7;
        String[] typeFileNameList = { "./images/type_normal.png", "./images/type_fire.png", "./images/type_water.png",
                                      "./images/type_electric.png", "./images/type_grass.png", "./images/type_ice.png",
                                      "./images/type_fighting.png", "./images/type_poison.png", "./images/type_ground.png",
                                      "./images/type_flying.png", "./images/type_psychic.png", "./images/type_bug.png",
                                      "./images/type_rock.png", "./images/type_ghost.png", "./images/type_dragon.png",
                                      "./images/type_dark.png", "./images/type_self.png" };
        GreenfootImage[] images = { new GreenfootImage( typeFileNameList[0] ), new GreenfootImage( typeFileNameList[1] ),
                                    new GreenfootImage( typeFileNameList[2] ), new GreenfootImage( typeFileNameList[3] ),
                                    new GreenfootImage( typeFileNameList[4] ), new GreenfootImage( typeFileNameList[5] ),
                                    new GreenfootImage( typeFileNameList[6] ), new GreenfootImage( typeFileNameList[7] ),
                                    new GreenfootImage( typeFileNameList[8] ), new GreenfootImage( typeFileNameList[9] ),
                                    new GreenfootImage( typeFileNameList[10] ), new GreenfootImage( typeFileNameList[11] ),
                                    new GreenfootImage( typeFileNameList[12] ), new GreenfootImage( typeFileNameList[13] ),
                                    new GreenfootImage( typeFileNameList[14] ), new GreenfootImage( typeFileNameList[15] ),
                                    new GreenfootImage( typeFileNameList[16] ) };
        for( int i = 0, j = 0, columnMargin = 0; i < typeFileNameList.length; i++, j++ ) {
            images[i].scale( (int)(images[i].getWidth() * scaleRatio), (int)(images[i].getHeight() * scaleRatio ) );
            setTextImage( images[i], "", 0, 0, 283 + columnMargin, 300 + (j * 20) );
            if( j == 5 ) {
                j = -1;
                columnMargin += 50;
            }
        }
        currentTurn = BattleTurn.POKEDEX_EFFECTIVITY_SELECT;
    }
    
    /**
     * Checks if the mouse is above one of the Types. If it is, replace the TextImage with the correct
     * highlighted version, either being red (attacking selection) or blue (defending selection)
     * 
     * @see act()
     * @see Greenfoot.getMouseInfo()
     * @see MouseInfo.getActor()
     * @see Actor.getImage()
     * @see GreenfootImage.toString()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see getCurrentPokemon()
     * @see PokemonActor.getPokedex()
     * @see Pokedex.getEffectivenessText( String attackType, String defendingPokemonType )
     * @see addTextToRegion( String text, int regionWidth, int regionHeight, int xOffset, int yOffset, int x, int y,
     *                       double leadingRatio )
     */
    private void addHighlights() {
        //Check the image name of the actor beneath the location of the mouse
        MouseInfo info = null;
        //The try catch is needed if the mouse is ever off-screen
        try {
            info = Greenfoot.getMouseInfo();
            if( info == null) return;
        } catch( NullPointerException e ) {
            return;
        }
        
        Actor actor = null;
        //The try catch is needed if there is no actor beneath the mouse location
        try {
            actor = info.getActor();
        } catch( NullPointerException e ) {
            return;
        }
        
        //Add highlights to the type buttons clicked. Once the second type is selected, display the effectiveness result
        if( actor != null && actor.getImage().toString().contains("type") ) {
            String toStringText = actor.getImage().toString();
            if( !toStringText.contains("_red") && !toStringText.contains("_blue") && !isHighlighted ) {
                //Do not allow the first type clicked to be self
                if( isRedHighlight && toStringText.contains("self") ) return;
                
                //Paste highlighted type button over original button (this also enlargens the selection)
                String fileName = toStringText.split(" ")[3];
                String highlight = isRedHighlight ? "_red.png" : "_blue.png";
                String fileNameColor = fileName.replace(".png", highlight);
                setTextImage( fileNameColor, "", 0, 0, actor.getX(), actor.getY() );
                isRedHighlight = !isRedHighlight;
                
                //Remember the first type String
                if( highlight.contains("_red") ) {
                    lastSearch = fileNameColor.replace("_red.png","").replace("./images/type_","");
                }
                
                //Find effectiveness based on the two types selected, and print this to the screen
                if( highlight.contains("_blue") ) {
                    isHighlighted = true;
                    String defendingPokemonType = fileNameColor.replace("_blue.png","").replace("./images/type_","");
                    //Defending pokemon type is equal to the attacking type if the second type selection is 'self'
                    if( defendingPokemonType.equals("self") ) defendingPokemonType = lastSearch;
                    
                    String effectivenessText = getCurrentPokemon().getPokedex().getEffectivenessText( lastSearch, defendingPokemonType );
                    addTextToRegion( effectivenessText, 150, 100, 20, 20, 120, 385, 4.0 );
                    currentTurn = BattleTurn.INTRO;
                    currentSelection = SelectionArrow.TOP_LEFT;
                }
            }
        }
    }
    
    /**
     * Displays the Pokedex to show the experience needed to level and the current experience of this Pokemon
     * 
     * @see setPokedex()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setTextImage( GreenfootImage image, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset, Color color, boolean isBold, int x, int y )
     * @see getCurrentPokemon()
     * @see PokemonActor.getRemainingExp()
     * @see PokemonActor.getTotalExp()
     * @see PokemonActor.getBattleImageName()
     * @see PokeWorld.scaleImageToMaxLimit( GreenfootImage image, int limitWidth, int limitHeight )
     * @see PokemonActor.getName()
     * @see PokeWorld.capFirstLetter( String token )
     */
    private void pokedexExperience() {
        PokemonActor pokemon = getCurrentPokemon();
        int expUntilLevel = pokemon.getRemainingExp();
        int currentExp = pokemon.getTotalExp();
        String pokemonImageName = pokemon.getBattleImageName();
        
        GreenfootImage pokemonImage = new GreenfootImage( pokemonImageName );
        PokeWorld.scaleImageToMaxLimit( pokemonImage, 137, 90 );
        
        setTextImage( POKEDEX_POKEMON_SEARCH, "Exp to level: " + expUntilLevel, 235, 170, 230, 350 );
        setText( 200, 100, "Current exp: " + currentExp, 20, 20, Color.BLACK, false, 344, 430 );
        setTextImage( pokemonImage, "", 0, 0, 130, 335 );
        setText( 200, 100, PokeWorld.capFirstLetter( pokemon.getName() ), 20, 20, Color.BLACK, true, 350, 360 );
        
        currentTurn = BattleTurn.INTRO;
        currentSelection = SelectionArrow.TOP_LEFT;
    } 

    /**
     * Displays the Pokedex to allow the user to look up a Status effect
     * 
     * @see setPokedex()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset, Color color, boolean isBold, int x, int y )
     * @see World.addObject( Actor actor, int x, int y )
     */
    private void pokedexStatus() { //292x201 green window
        setTextImage( POKEDEX_POKEMON_SEARCH, "Enter a Status:", 37, 103, 230, 350 );
        TextField pokedexTextField = new TextField( 135, 30, true );
        addObject( pokedexTextField, 134, 367 );
        activeTextField = pokedexTextField;
    }
    
    /**
     * Displays the Status in the Pokedex window, which has been searched for.
     * If the Status entered does not exist, display a message that the Status is
     * unknown, and list the known Statuses
     * 
     * @param text The text that was entered into the text field
     * @see act()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset, Color color, boolean isBold, int x, int y )
     * @see getCurrentPokemon()
     * @see PokemonActor.getBattleImageName()
     * @see PokemonActor.getStatus()
     * @see PokeWorld.scaleImageToMaxLimit( GreenfootImage image, int limitWidth, int limitHeight )
     * @see Enum.name()
     * @see addTextToRegion( String text, int regionWidth, int regionHeight, int xOffset, int yOffset, int x, int y,
     *                       double leadingRatio )
     */
    private void pokedexStatus( String text ) {
        setTextImage( POKEDEX_POKEMON_SEARCH, "", 0, 0, 230, 350 );
        
        //Get this Pokemon's image and status
        PokemonActor pokemon = getCurrentPokemon();
        String pokemonImageName = pokemon.getBattleImageName();
        PokemonActor.Status pokeStatus = pokemon.getStatus();
        
        //Set this Pokemon's image to the screen
        GreenfootImage pokemonImage = new GreenfootImage( pokemonImageName );
        PokeWorld.scaleImageToMaxLimit( pokemonImage, 137, 90 );
        setTextImage( pokemonImage, "", 0, 0, 130, 335 );
        
        //Set the current status image location
        String statusImage = "./images/status";
        if(      pokeStatus.name().equals( "NORMAL" ) )    statusImage += "_normal.png";
        else if( pokeStatus.name().equals( "BURN" ) )      statusImage += "_burn.png";
        else if( pokeStatus.name().equals( "FREEZE" ) )    statusImage += "_freeze.png";
        else if( pokeStatus.name().equals( "PARALYSIS" ) ) statusImage += "_paralysis.png";
        else if( pokeStatus.name().equals( "POISON" ) )    statusImage += "_poison.png";
        else if( pokeStatus.name().equals( "SLEEP" ) )     statusImage += "_sleep.png";
        else if( pokeStatus.name().equals( "BOUND" ) )     statusImage += "_bound.png";
        else if( pokeStatus.name().equals( "CONFUSION" ) ) statusImage += "_confusion.png";
        else if( pokeStatus.name().equals( "FAINTED" ) )   statusImage += "_paralysis.png";
        else                                               statusImage = PokeWorld.ERROR + "";
        
        //Set the current Status image
        if( !statusImage.equals( PokeWorld.ERROR + "" ) ) {
            GreenfootImage statusImageResize = new GreenfootImage( statusImage );
            PokeWorld.scaleImageToMaxLimit( statusImageResize, 50, 40 );
            setTextImage( statusImageResize, "", 0, 0, 178, 384 );
        }
        
        //Set the text for the current Status
        setText( 200, 100, "Status", 20, 20, Color.BLACK, false, 165, 496 );
        
        //Set the image name of the Status being searched for
        statusImage = "./images/status";
        text = text.toUpperCase();
        int statusIndex = 0;
        if(      text.equals( "NORMAL"    ) ) { statusImage += "_normal.png";                         }
        else if( text.equals( "BURN"      ) ) { statusImage += "_burn.png";         statusIndex = 1;  }
        else if( text.equals( "FREEZE"    ) ) { statusImage += "_freeze.png";       statusIndex = 2;  }
        else if( text.equals( "PARALYSIS" ) ) { statusImage += "_paralysis.png";    statusIndex = 3;  }
        else if( text.equals( "POISON"    ) ) { statusImage += "_poison.png";       statusIndex = 4;  }
        else if( text.equals( "SLEEP"     ) ) { statusImage += "_sleep.png";        statusIndex = 5;  }
        else if( text.equals( "BOUND"     ) ) { statusImage += "_bound.png";        statusIndex = 6;  }
        else if( text.equals( "CONFUSION" ) ) { statusImage += "_confusion.png";    statusIndex = 7;  }
        else if( text.equals( "FAINTED"   ) ) { statusImage += "_fainted.png";      statusIndex = 8;  }
        else                                  { statusImage = PokeWorld.ERROR + ""; statusIndex = -1; }
        
        //If the text entered is not a valid Status
        if( statusImage.equals( PokeWorld.ERROR + "" ) ) {
            currentTurn = BattleTurn.INTRO;
            currentSelection = SelectionArrow.TOP_LEFT;
            remainingText = "";
            
            addTextToRegion( "This Status is not recognized", 165, 100, 20, 20, 325, 377, 4.8 );
            
            return;
        }
        
        //Set lookup Status image
        GreenfootImage statusImageResize = new GreenfootImage( statusImage );
        PokeWorld.scaleImageToMaxLimit( statusImageResize, 50, 40 );
        setTextImage( statusImageResize, "", 0, 0, 287, 315 );
        statusImage = statusImage.replace(".png","");
        statusImage = statusImage.substring( 16, statusImage.length() );
        setText( 200, 100, statusImage.toUpperCase(), 20, 20, Color.BLACK, false, 397, 350 );
        
        //Status descriptions
        String[] statusDescriptions = { "No adverse Status conditions",
                                        "The burn condition (BRN) inflicts damage every turn and halves damage dealt" +
                                            " by a Pokemon's physical moves. Burn inflicts damage equal to 1/16 of" +
                                            " its maximum HP every turn.",
                                        "The freeze condition (FRZ) causes a Pokemon to be unable to use moves",
                                        "The paralysis condition (PAR) reduces the Pokemon's Speed stat and causes" +
                                            " it to have a 25% chance of being unable to use a move when trying to" +
                                            " use one. Its Speed is reduced to 25% of its normal value.",
                                        "The poison condition (PSN) inflicts damage every turn. Poison inflicts" +
                                            " damage equal to 1/16 of its maximum HP every turn.",
                                        "The sleep condition (SLP) causes a Pokemon to be unable to use moves." +
                                            " Sleep lasts for a randomly chosen duration of 1 to 7 turns",
                                        "When a Pokemon is hit by a binding move, it becomes bound. While it is" +
                                            " bound, a Pokemon takes damage at the end of each turn and cannot switch" +
                                            " out or flee. This lasts 2-5 turns. There is a 37.5% chance that the move" +
                                            " will last 2 turns, a 37.5% chance that it will last 3 turns, a 12.5% chance" +
                                            " that it will last 4 turns, and a 12.5% chance that it will last 5 turns." +
                                            " While a Pokémon is bound, it cannot use moves, including on the turn it" +
                                            " is hit if it would move second.",
                                        "The confused condition causes a Pokemon to sometimes hurt itself in its" +
                                            " confusion instead of executing a selected move. The chance to hurt" +
                                            " itself is 50%. Confusion wears off after 2-5 attacking turns.",
                                        "Fainting is a status condition in which a Pokemon is no longer able to battle."
                                      };
        
        //Set text, allowing for excess text to roll-over next time the user presses enter
        String description = statusDescriptions[ statusIndex ];
        String remainingDescription = remainingText.equals("") ? description : remainingText;
        String excessText = addTextToRegion( remainingDescription, 165, 100,
                                             20, 20, 325, 377, 4.8 );
        if( excessText.equals("") ) {
            currentTurn = BattleTurn.INTRO;
            currentSelection = SelectionArrow.TOP_LEFT;
            remainingText = "";
        } else {
            remainingText = excessText;
        }
    }
    
    /**
     * Sets the menu when ITEM is selected within the main menu
     * 
     * @see act()
     * @see selectMenu()
     * @see getCurrentPokemon()
     * @see PokemonActor.getItems()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset, Color color, boolean isBold, int x, int y )
     * @see Item.getQuantity()
     * @see PokeWorld.scaleImageToMaxLimit( GreenfootImage image, int limitWidth, int limitHeight )
     * @see addTextToRegion( String text, int regionWidth, int regionHeight, int xOffset, int yOffset, int x, int y,
     *                       double leadingRatio )
     */
    private void setItemMenu() {
        final int LEADING = 29;
        
        currentTurn = BattleTurn.ITEM;
        currentItem = null;
        
        PokemonActor pokemon = getCurrentPokemon();
        ArrayList<Item> itemList = pokemon.getItems();
        //Number of items displayed
        int itemIndexAdjustment = (pageNumber - 1) * MAX_ITEMS_PER_WINDOW;
        int totalItemsLeft = itemList.size() - itemIndexAdjustment;
        
        //Adjust variable used for arrow position
        if(      Greenfoot.isKeyDown("up") && selectionArrowListPosition != 0 )
            --selectionArrowListPosition;
        else if( Greenfoot.isKeyDown("down") && (( ( totalItemsLeft != 0 && (selectionArrowListPosition != totalItemsLeft &&
                                                                         selectionArrowListPosition != MAX_ITEMS_PER_WINDOW) ) ||
                                                   ( totalItemsLeft == 0 && selectionArrowListPosition != totalItemsLeft + 1 )) ) )
            ++selectionArrowListPosition;
        
        //Display the Pokebag if the Pokemon / player has no items
        if( itemList.isEmpty() ) {
            setTextImage( POKEBAG, "You have no items.", 250, 50, 282, 285 );
            setTextImage( POKEBAG_ARROW, "", 0, 0, 285, 178 + selectionArrowListPosition * LEADING );
            setText( 200, 100, "Exit bag", 20, 20, Color.BLACK, false, 376, 243 );
            
            return;
        }
        
        //Display the Pokebag and up to the first MAX_ITEMS_PER_WINDOW in the bag
        setTextImage( POKEBAG, "", 0, 0, 282, 285 );
        setTextImage( POKEBAG_ARROW, "", 0, 0, 285, 178 + selectionArrowListPosition * LEADING );
        int itemsOnPage = totalItemsLeft > MAX_ITEMS_PER_WINDOW ? MAX_ITEMS_PER_WINDOW : totalItemsLeft;
        for( int i = itemIndexAdjustment, j = 0; i < itemsOnPage + itemIndexAdjustment; i++, j++ ) {
            setText( 200, 100, itemList.get(i).getName(), 20, 20, Color.BLACK, false, 376, 213 + j * LEADING );
            setTextImage( POKEBAG_X, "", 0, 0, 470, 178 + j * LEADING );
            setText( 200, 100, itemList.get(i).getQuantity() + "", 20, 20, Color.BLACK, false, 562, 213 + j * LEADING );
        }
        if( totalItemsLeft <= MAX_ITEMS_PER_WINDOW )
            setText( 200, 100, "Exit bag", 20, 20, Color.BLACK, false, 376, 213 + itemsOnPage * LEADING );
        else
            setText( 200, 100, "Next page", 20, 20, Color.BLACK, false, 376, 213 + itemsOnPage * LEADING );
        
        //Display the Item image and description
        if( Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down") ) remainingText = "";
        
        int currentItemSelectionIndex = itemIndexAdjustment + selectionArrowListPosition;
        if( selectionArrowListPosition != MAX_ITEMS_PER_WINDOW && totalItemsLeft > 0 &&
            selectionArrowListPosition != itemsOnPage ) {
            GreenfootImage itemImage = new GreenfootImage( itemList.get( currentItemSelectionIndex ).getImageName() );
            PokeWorld.scaleImageToMaxLimit( itemImage, 56, 48 );
            setTextImage( itemImage, "", 0, 0, 86, 293 );
            
            String remainingDescription = remainingText.equals("") ? itemList.get( currentItemSelectionIndex ).getDescription() : remainingText;
            String excessText = addTextToRegion( remainingDescription, 205, 96,
                                             20, 20, 135, 380, 3.0 );
            if( excessText.equals("") ) remainingText = "";
            else                        remainingText = excessText;
        }
    }
    
    /**
     * Displays the response to the user selecting the current option when enter is pressed.
     * If the option selected is an item, ask whether the user really wants to use that item.
     * If the option selected is to go to the next page, print the next page of items.
     * If the option selected is to exit the Pokebag, exit to the main menu
     * 
     * @see act()
     * @see getCurrentPokemon()
     * @see PokemonActor.getItems()
     * @see chooseMove()
     * @see useItemMenu()
     * @see addPokeInfo()
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     * @see setText( int width, int height, String text, int xOffset, int yOffset, Color color, boolean isBold, int x, int y )
     */
    private void runSelection() {
        PokemonActor pokemon = getCurrentPokemon();
        ArrayList<Item> itemList = pokemon.getItems();
        
        //If enter is clicked on the selection that says 'You have no items.', then do nothing
        if( itemList.isEmpty() && selectionArrowListPosition == 0 ) return;
        
        int currentPosition = (pageNumber - 1) * MAX_ITEMS_PER_WINDOW + selectionArrowListPosition;
        int currentItemNumber = itemList.isEmpty() ? currentPosition - 1 : currentPosition ;
        if( selectionArrowListPosition < MAX_ITEMS_PER_WINDOW && currentItemNumber != itemList.size() ) {
            /* Item will be used. Reset values and change turn enum. Don't allow for last 'enter' to carry into
               initiating the use of this item (note that currentItem is set AFTER this if statement */   
            if( Greenfoot.isKeyDown("enter") && isYes && currentItem != null ) {
                currentTurn = BattleTurn.USE_ITEM;
                
                pageNumber = 1;
                selectionArrowListPosition = 0;
                
                Item item = itemList.get( currentItemNumber );
                this.currentItem = item;
                pokemon.setCurrentItem( item );
                
                /*Normally this would be in the act() method, but this is done here in order to not require another enter
                  to run the useItemMenu() function, since this function is first run to display the yes/no question, then
                  enter is pressed again in order to run this function again and this time carry through with using the item
                */
                clearText();
                if( item.getChooseMove() ) chooseMove();
                else                       useItemMenu();
                
                addPokeInfo();
                
                Greenfoot.delay( DELAY_CYCLES );
                return;
            }
            
            //Save the current item
            Item item = itemList.get( currentItemNumber );
            this.currentItem = item;
            pokemon.setCurrentItem( item );
            
            //Update arrow menu option marker
            if( (Greenfoot.isKeyDown("left") && !isYes) || (Greenfoot.isKeyDown("right") && isYes) )
                isYes = !isYes;
            
            //Change menu arrows
            if( isYes )
            setTextImage( MENU_BOTTOM_LEFT, "Use " + itemList.get( currentItemNumber ).getName() + "?",
                          85, 46, TEXT_BOX_X + 225, TEXT_BOX_Y );
            else if( !isYes )
            setTextImage( MENU_BOTTOM_RIGHT, "Use " + itemList.get( currentItemNumber ).getName() + "?",
                          85, 46, TEXT_BOX_X + 225, TEXT_BOX_Y );
            
            //Set Yes and No options
            setText( 200, 100, "YES", 20, 20, Color.BLACK, false, 604, 516 );
            setText( 200, 100, "NO", 20, 20, Color.BLACK, false, 731, 516 );
            
            //If the 'Next page' selection is entered
        } else if( selectionArrowListPosition == MAX_ITEMS_PER_WINDOW && currentItemNumber != itemList.size() ) {
            pageNumber += 1;
            selectionArrowListPosition = 0;
            setItemMenu();
            
            //If the 'Exit Pokebag' selection is entered
        } else {
            pageNumber = 1;
            selectionArrowListPosition = 0;
            isYes = true;
            currentTurn = BattleTurn.INTRO;
            
            /* Run the same code in the act() method sequence since 'enter' has already been pressed, and won't carry over
               into the next cycle*/
            clearText();
            setMenu();
            isHighlighted = false; //Reset for the pokedexEffectivity() function
            
            addPokeInfo();
            
            Greenfoot.delay( DELAY_CYCLES ); //delay cycles so that the next listen for 'enter' does not pick up this one
        }
        
    }
    
    /**
     * Choose the Move to use this Item on
     * 
     * @see runSelection()
     * @see useItemMenu()
     * @see setSelection()
     * @see setFightMenu()
     */
    public void chooseMove() {
        setFightMenu();
        
        currentTurn = BattleTurn.CHOOSE_MOVE_FOR_ITEM;
    }
    
    /**
     * Uses the currentItem and displays the results in the menu
     * 
     * @see act()
     * @see itemAddCurrent( String propertyName, double propertyValue, Item item )
     * @see itemAddMax( String propertyName, double propertyValue, Item item )
     * @see itemMultCurrent( String propertyName, double propertyValue, Item item )
     * @see itemMultMax( String propertyName, double propertyValue, Item item )
     * @see setText( int width, int height, String text, int xOffset, int yOffset, Color color, boolean isBold, int x, int y )
     * @see ...
     */
    public void useItemMenu() {
        PokemonActor currentPokemon = getCurrentPokemon();
        currentSelection = SelectionArrow.TOP_LEFT;
        
        ArrayList<String> propertyNamesChanged = new ArrayList<String>();
        ArrayList<Double> parallelValuesChanged = new ArrayList<Double>();
        Item item = currentPokemon.getCurrentItem();
        double[] properties = item.getProperties();
        String[] propertyNames = Item.PROPERTY_NAMES;
        
        /*Check boolean requirements. If not met, do not use item, and display message showing that the item
          could not be used */
        boolean affectThis = item.getAffectThis();
        String reqXType = item.getReqXType();
        String reqXCat = item.getReqXCat();
        int reqHPX = item.getReqHPX();
        PokemonActor affectedPokemon = affectThis ? getCurrentPokemon() : getOtherPokemon();
        
        //Save old values before using item
        int oldHP                  = affectedPokemon.getHP(); int oldCurrentHP = affectedPokemon.getCurrentHP();
        int oldAttack              = affectedPokemon.getAttack(); int oldCurrentAttack = affectedPokemon.getCurrentAttack();
        int oldDefense             = affectedPokemon.getDefense(); int oldCurrentDefense = affectedPokemon.getCurrentDefense();
        int oldSpecialAttack       = affectedPokemon.getSpecialAttack(); int oldCurrentSpecialAttack = affectedPokemon.getCurrentSpecialAttack();
        int oldSpecialDefense      = affectedPokemon.getSpecialDefense(); int oldCurrentSpecialDefense = affectedPokemon.getCurrentSpecialDefense();
        int oldSpeed               = affectedPokemon.getSpeed(); int oldCurrentSpeed = affectedPokemon.getCurrentSpeed();
        int oldEvasion             = affectedPokemon.getEvasion(); int oldCurrentEvasion = affectedPokemon.getCurrentEvasion();
        int oldAccuracy            = affectedPokemon.getAccuracy(); int oldCurrentAccuracy = affectedPokemon.getCurrentAccuracy();
        int oldExp                 = affectedPokemon.getTotalExp(); int oldCurrentExp = affectedPokemon.getTotalAddedExp();
        double oldCriticalHitRatio = affectedPokemon.getCriticalHitRatio(); double oldCurrentCriticalHitRatio = affectedPokemon.getCurrentCriticalHitRatio();
        
        int oldLevel = affectedPokemon.getCurrentLevel();
        
        Pokemon.Status oldStatus = affectedPokemon.getStatus();
        
        int totalMoves = affectedPokemon.getMoves().size();
        int[][] oldMoveStats = new int[ totalMoves ][3]; //3 -> movePower, moveAccuracy, movePP
        int[][] oldCurrentMoveStats = new int[ totalMoves ][3];
        for( int i = 0; i < totalMoves; i++ ) {
            int oldMovePower           = affectedPokemon.getMoves().get(i).getPower();
            int oldCurrentMovePower    = affectedPokemon.getMoves().get(i).getCurrentPower();
            int oldMoveAccuracy        = affectedPokemon.getMoves().get(i).getAccuracy();
            int oldCurrentMoveAccuracy = affectedPokemon.getMoves().get(i).getCurrentAccuracy();
            int oldMovePP              = affectedPokemon.getMoves().get(i).getPP();
            int oldCurrentMovePP       = affectedPokemon.getMoves().get(i).getCurrentPP();
            
            oldMoveStats[i][0] = oldMovePower; oldMoveStats[i][1] = oldMoveAccuracy; oldMoveStats[i][2] = oldMovePP;
            oldCurrentMoveStats[i][0] = oldCurrentMovePower;
            oldCurrentMoveStats[i][1] = oldCurrentMoveAccuracy;
            oldCurrentMoveStats[i][2] = oldCurrentMovePP;
        }
        
        //See if Item doesn't meet requirements
        boolean affectsAllMoves = item.getAffectsAllMoves();
        if( !affectsAllMoves && (!reqXType.equals("na") || !reqXCat.equals("na")) &&
            (( !reqXType.replace("req","").replace("Type","").toUpperCase()
              .equals( affectedPokemon.getCurrentMove().getType().name() ) ||
              !reqXCat.replace("req","").replace("Cat","").toUpperCase()
              .equals( affectedPokemon.getCurrentMove().getCategory().name() ) ) ||
            ( (reqHPX != 0 && ( reqHPX == 1 && ((double)affectedPokemon.getCurrentHP() / (double)affectedPokemon.getHP() ) > 0.33) ||
                              ( reqHPX == 2 && ((double)affectedPokemon.getCurrentHP() / (double)affectedPokemon.getHP() ) >= 0.5) ||
                              ( reqHPX == 3 && ((double)affectedPokemon.getCurrentHP() / (double)affectedPokemon.getHP() ) <= 0.5) ||
                              ( reqHPX == 4 && ((double)affectedPokemon.getCurrentHP() / (double)affectedPokemon.getHP() ) < 0.66) ||
                              ( reqHPX == 5 && ((double)affectedPokemon.getCurrentHP() / (double)affectedPokemon.getHP() ) <= 0.99) ) ) ) ) {
            setText( 500, 100, affectedPokemon.getName() + " cannot use " + item.getName() + "!",
                     20, 20, Color.BLACK, false, 270, 450 );
            
            //Change turns when item is used
            currentTurn = BattleTurn.INTRO;
            //@@QUESTION - Don't switch turn if Item doesn't meet requirements for use?
            //currentPlayer = currentPlayer == PokemonTurn.PLAYER_1 ? PokemonTurn.PLAYER_2 : PokemonTurn.PLAYER_1;
            
            return;
        }
        
        //Get the Strings and corresponding values
        for( int i = 0; i < properties.length; i++ ) {
            if( properties[i] != 0.0 ) {
                propertyNamesChanged.add( propertyNames[i] );
                parallelValuesChanged.add( properties[i] );
            }
        }
        
        //Change stats of Pokemon / opponent
        int size = propertyNamesChanged.size();
        ArrayList<Double> valueResults = new ArrayList<Double>();
        for( int i = 0; i < size; i++ ) {
            boolean addValues     = item.getAddValues();
            boolean affectCurrent = item.getAffectCurrent();
            if(      addValues && affectCurrent )
                valueResults.add(
                    itemAddCurrent( propertyNamesChanged.get(i), parallelValuesChanged.get(i), item ) );
            else if( addValues && !affectCurrent )
                valueResults.add(
                    itemAddMax( propertyNamesChanged.get(i), parallelValuesChanged.get(i), item ) );
            else if( !addValues && affectCurrent )
                valueResults.add(
                    itemMultCurrent( propertyNamesChanged.get(i), parallelValuesChanged.get(i), item ) );
            else   /*!addValues && !affectCurrent*/
                valueResults.add(
                    itemMultMax( propertyNamesChanged.get(i), parallelValuesChanged.get(i), item ) );
        }
        
        //Change Status of Pokemon, if Item heals Status
        boolean healStatus = item.getHealStatus();
        if( healStatus ) currentPokemon.setStatus( Pokemon.Status.NORMAL );
        String healX = item.getHealX();
        if( !healX.equals("na") ) {
            if(      healX.equals("healBurn") && currentPokemon.getStatus() == Pokemon.Status.BURN )
                currentPokemon.setStatus( Pokemon.Status.NORMAL );
            else if( healX.equals("healFreeze") && currentPokemon.getStatus() == Pokemon.Status.FREEZE )
                currentPokemon.setStatus( Pokemon.Status.NORMAL );
            else if( healX.equals("healParalysis") && currentPokemon.getStatus() == Pokemon.Status.PARALYSIS )
                currentPokemon.setStatus( Pokemon.Status.NORMAL );
            else if( healX.equals("healPoison") && currentPokemon.getStatus() == Pokemon.Status.POISON )
                currentPokemon.setStatus( Pokemon.Status.NORMAL );
            else if( healX.equals("healSleep") && currentPokemon.getStatus() == Pokemon.Status.SLEEP )
                currentPokemon.setStatus( Pokemon.Status.NORMAL );
            else if( healX.equals("healBound") && currentPokemon.getStatus() == Pokemon.Status.BOUND )
                currentPokemon.setStatus( Pokemon.Status.NORMAL );
            else if( healX.equals("healConfusion") && currentPokemon.getStatus() == Pokemon.Status.CONFUSION )
                currentPokemon.setStatus( Pokemon.Status.NORMAL );
            else if( healX.equals("healFainted") && currentPokemon.getStatus() == Pokemon.Status.FAINTED )
                currentPokemon.setStatus( Pokemon.Status.NORMAL );
            
            healStatus = true;
        }
        
        //Change quantities if Item has been used, or remove item
        boolean oneTimeUse = item.getOneTimeUse();
        if( oneTimeUse )
            currentPokemon.removeItem( item );
        
        //Get the text to write to the screen
        String intro = currentPokemon.getName() + " used " + item.getName() + "!";
        setText( 500, 100, intro, 20, 20, Color.WHITE, true, 270, 450 );
        final int LEADING = 25;
        
        //If any of these are true, do not run the next for loop, run a special one instead
        boolean affectsAllStats       = item.getAffectsAllStats();
        //affectsAllMoves is instantiated on a line earlier in this method
        boolean restoresLoweredStats  = item.getRestoresLoweredStats();
        boolean affectValuesOnLevelUp = item.getAffectValuesOnLevelUp();
        
        int resultsSize = valueResults.size();
        if( !affectsAllStats && !affectsAllMoves && !restoresLoweredStats && !affectValuesOnLevelUp ) {
        for( int i = 0; i < resultsSize; i++ ) {
            String itemDetails = currentPokemon.getName() + " ";
            boolean affectCurrent = item.getAffectCurrent();
            if( propertyNamesChanged.get(i).equals("HP") ) {
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)(valueResults.get(i) - oldCurrentHP) + " HP!";
                    else
                        itemDetails += "lost " + (int)(oldCurrentHP - valueResults.get(i)) + " HP!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)(valueResults.get(i) - oldHP) + " HP!";
                    else
                        itemDetails += "permanently lost " + (int)(oldHP - valueResults.get(i)) + " HP!";
                }
            } else if( propertyNamesChanged.get(i).equals("attack") ) {
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)Math.abs(valueResults.get(i) - oldCurrentAttack) + " Attack!";
                    else
                        itemDetails += "lost " + (int)Math.abs(oldCurrentAttack - valueResults.get(i)) + " Attack!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)Math.abs(valueResults.get(i) - oldAttack) + " Attack!";
                    else
                        itemDetails += "permanently lost " + (int)Math.abs(oldAttack - valueResults.get(i)) + " Attack!";
                }
            } else if( propertyNamesChanged.get(i).equals("defense") ) {
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)Math.abs(valueResults.get(i) - oldCurrentDefense) + " Defense!";
                    else
                        itemDetails += "lost " + (int)Math.abs(oldCurrentDefense - valueResults.get(i)) + " Defense!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)Math.abs(valueResults.get(i) - oldDefense) + " Defense!";
                    else
                        itemDetails += "permanently lost " + (int)Math.abs(oldDefense - valueResults.get(i)) + " Defense!";
                }
            } else if( propertyNamesChanged.get(i).equals("specialAttack") ) {
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)Math.abs(valueResults.get(i) - oldCurrentSpecialAttack) + " Special Attack!";
                    else
                        itemDetails += "lost " + (int)Math.abs(oldCurrentSpecialAttack - valueResults.get(i)) + " Special Attack!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)Math.abs(valueResults.get(i) - oldSpecialAttack) + " Special Attack!";
                    else
                        itemDetails += "permanently lost " + (int)Math.abs(oldSpecialAttack - valueResults.get(i)) + " Special Attack!";
                }
            } else if( propertyNamesChanged.get(i).equals("specialDefense") ) {
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)Math.abs(valueResults.get(i) - oldCurrentSpecialDefense) + " Special Defense!";
                    else
                        itemDetails += "lost " + (int)Math.abs(oldCurrentSpecialDefense - valueResults.get(i)) + " Special Defense!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)Math.abs(valueResults.get(i) - oldSpecialDefense) + " Special Defense!";
                    else
                        itemDetails += "permanently lost " + (int)Math.abs(oldSpecialDefense - valueResults.get(i)) + " Special Defense!";
                }
            } else if( propertyNamesChanged.get(i).equals("speed") ) {
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)Math.abs(valueResults.get(i) - oldCurrentSpeed) + " Speed!";
                    else
                        itemDetails += "lost " + (int)Math.abs(oldCurrentSpeed - valueResults.get(i)) + " Speed!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)Math.abs(valueResults.get(i) - oldSpeed) + " Speed!";
                    else
                        itemDetails += "permanently lost " + (int)Math.abs(oldSpeed - valueResults.get(i)) + " Speed!";
                }
            } else if( propertyNamesChanged.get(i).equals("evasion") ) {
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)Math.abs(valueResults.get(i) - oldCurrentEvasion) + " Evasion!";
                    else
                        itemDetails += "lost " + (int)Math.abs(oldCurrentEvasion - valueResults.get(i)) + " Evasion!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)Math.abs(valueResults.get(i) - oldEvasion) + " Evasion!";
                    else
                        itemDetails += "permanently lost " + (int)Math.abs(oldEvasion - valueResults.get(i)) + " Evasion!";
                }
            } else if( propertyNamesChanged.get(i).equals("accuracy") ) {
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)Math.abs(valueResults.get(i) - oldCurrentAccuracy) + " Accuracy!";
                    else
                        itemDetails += "lost " + (int)Math.abs(oldCurrentAccuracy - valueResults.get(i)) + " Accuracy!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)Math.abs(valueResults.get(i) - oldAccuracy) + " Accuracy!";
                    else
                        itemDetails += "permanently lost " + (int)Math.abs(oldAccuracy - valueResults.get(i)) + " Accuracy!";
                }
            } else if( propertyNamesChanged.get(i).equals("exp") ) {
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)Math.abs(valueResults.get(i) - oldCurrentExp) + " exp!";
                    else
                        itemDetails += "lost " + (int)Math.abs(oldCurrentExp - valueResults.get(i)) + " exp!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)Math.abs(valueResults.get(i) - oldExp) + " exp!";
                    else
                        itemDetails += "permanently lost " + (int)Math.abs(oldExp - valueResults.get(i)) + " exp!";
                }
                
                //If Level Up
                int newLevel = currentPokemon.getCurrentLevel();
                if( newLevel != oldLevel ) {
                    itemDetails += " " + currentPokemon.getName() + " leveled up!";
                    if( newLevel - oldLevel > 1 )
                        itemDetails.substring( 0, itemDetails.length() - 1).concat( (newLevel - oldLevel) + " times!" );
                }
            } else if( propertyNamesChanged.get(i).equals("criticalHitRatio") ) {
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + Math.abs(valueResults.get(i) - oldCurrentCriticalHitRatio) +
                            "% to their critical hit ratio!";
                    else
                        itemDetails += "lost " + Math.abs(oldCurrentCriticalHitRatio - valueResults.get(i)) +
                            "% to their critical hit ratio!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + Math.abs(valueResults.get(i) - oldCriticalHitRatio) +
                            "% to their critical hit ratio!";
                    else
                        itemDetails += "permanently lost " + Math.abs(oldCriticalHitRatio - valueResults.get(i)) +
                            "% to their critical hit ratio!";
                }
            } else if( propertyNamesChanged.get(i).equals("movePower") ) {
                Move move = currentPokemon.getCurrentMove();
                itemDetails = currentPokemon.getName() + "'s Move " + move.getName() + " ";
                int moveIndex = currentPokemon.getMoveIndex( move.getName() );
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)Math.abs(move.getCurrentPower() - oldCurrentMoveStats[moveIndex][0]) + " Power!";
                    else
                        itemDetails += "lost " + (int)Math.abs(oldCurrentMoveStats[moveIndex][0] - move.getCurrentPower()) + " Power!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)Math.abs(move.getPower() - oldMoveStats[moveIndex][0]) + " Power!";
                    else
                        itemDetails += "permanently lost " + (int)Math.abs(oldMoveStats[moveIndex][0] - move.getPower()) + " Power!";
                }
            } else if( propertyNamesChanged.get(i).equals("moveAccuracy") ) {
                Move move = currentPokemon.getCurrentMove();
                itemDetails = currentPokemon.getName() + "'s Move " + move.getName() + " ";
                int moveIndex = currentPokemon.getMoveIndex( move.getName() );
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)Math.abs(move.getCurrentAccuracy() - oldCurrentMoveStats[moveIndex][1]) + " Accuracy!";
                    else
                        itemDetails += "lost " + (int)Math.abs(oldCurrentMoveStats[moveIndex][1] - move.getCurrentAccuracy()) + " Accuracy!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)Math.abs(move.getAccuracy() - oldMoveStats[moveIndex][1]) + " Accuracy!";
                    else
                        itemDetails += "permanently lost " + (int)Math.abs(oldMoveStats[moveIndex][1] - move.getAccuracy()) + " Accuracy!";
                }
            } else if( propertyNamesChanged.get(i).equals("movePP") ) {
                Move move = currentPokemon.getCurrentMove();
                itemDetails = currentPokemon.getName() + "'s Move " + move.getName() + " ";
                int moveIndex = currentPokemon.getMoveIndex( move.getName() );
                if( affectCurrent ) {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "gained " + (int)Math.abs(move.getCurrentPP() - oldCurrentMoveStats[moveIndex][2]) + " PP!";
                    else
                        itemDetails += "lost " + (int)Math.abs(oldCurrentMoveStats[moveIndex][2] - move.getCurrentPower()) + " PP!";
                } else {
                    if( parallelValuesChanged.get(i) > 0.0 || parallelValuesChanged.get(i) == (double)PokeWorld.ERROR )
                        itemDetails += "permanently gained " + (int)Math.abs(move.getPP() - oldMoveStats[moveIndex][2]) + " PP!";
                    else
                        itemDetails += "permanently lost " + (int)Math.abs(oldMoveStats[moveIndex][2] - move.getPP()) + " PP!";
                }
            }
            
            //Write details to the screen
            setText( 500, 100, itemDetails, 20, 20, Color.WHITE, true, 270, 475 + i * LEADING );
        }
        }
        
        int i = 0;
        if( affectsAllStats )      setText( 500, 100, currentPokemon.getName() + "'s stats went up!",
                                            20, 20, Color.WHITE, true, 270, 475 + i++ * LEADING );
        if( affectsAllMoves )      setText( 500, 100, currentPokemon.getName() + "'s Moves were replenished!",
                                            20, 20, Color.WHITE, true, 270, 475 + i++ * LEADING );
        if( restoresLoweredStats ) setText( 500, 100, currentPokemon.getName() + "'s lowered stats went up!",
                                            20, 20, Color.WHITE, true, 270, 475 + i++ * LEADING );
        if( healStatus )           setText( 500, 100, currentPokemon.getName() + " was healed of its " + oldStatus.name() + "!",
                                            20, 20, Color.WHITE, true, 270, 475 + i++ * LEADING );
                                            
        //Change turns when item is used
        currentTurn = BattleTurn.INTRO;
        currentPlayer = currentPlayer == PokemonTurn.PLAYER_1 ? PokemonTurn.PLAYER_2 : PokemonTurn.PLAYER_1;
    }
    
    /**
     * Adds the value to the given current property for the selected Pokemon, which is dependent on the 'affectThis' variable
     * 
     * @param propertyName The name of the property that is being affected by this Item
     * @param propertyValue The value of the property that is going to be added to the corresponding current property
     *                      of the selected Pokemon
     * @param item The item whose properties are affecting the selected Pokemon
     * @see useItemMenu()
     * @see Item.getAffectThis()
     * @see getCurrentPokemon()
     * @see getOtherPokemon()
     * @see PokemonActor.addHP( int HP )
     * @see PokemonActor.getCurrentHP()
     * @see PokemonActor.setCurrentAttack( int currentAttack )
     * @see PokemonActor.getCurrentAttack()
     * @see ...
     */
    private double itemAddCurrent( String propertyName, double propertyValue, Item item ) {
        boolean affectThis = item.getAffectThis();
        PokemonActor affectedPokemon = affectThis ? getCurrentPokemon() : getOtherPokemon();
        if( item.getRandomStat() )
            propertyName = Item.PROPERTY_NAMES[ new Random().nextInt( Item.PROPERTY_NAMES.length ) ];
        
        //Adjustment properties
        boolean affectsAllStats      = item.getAffectsAllStats();
        boolean affectsAllMoves      = item.getAffectsAllMoves();
        boolean restoreCurrentToFull = item.getRestoreCurrentToFull();
        boolean restoresLoweredStats = item.getRestoresLoweredStats();
        boolean maximizeValues       = propertyValue == (double)PokeWorld.ERROR ? true : false;
        
        switch( propertyName ) {
            case "HP":
                if( !restoresLoweredStats || propertyName.equals( "HP" ) )
                affectedPokemon.addHP( (int)propertyValue );
                tryRestoreCurrentHP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentHP();
            case "attack":
                if( !restoresLoweredStats || propertyName.equals( "attack" ) )
                affectedPokemon.setCurrentAttack( affectedPokemon.getCurrentAttack() + (int)propertyValue );
                tryRestoreCurrentAttack( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentAttack();
            case "defense":
                if( !restoresLoweredStats || propertyName.equals( "defense" ) )
                affectedPokemon.setCurrentDefense( affectedPokemon.getCurrentDefense() + (int)propertyValue );
                tryRestoreCurrentDefense( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentDefense();
            case "specialAttack":
                if( !restoresLoweredStats || propertyName.equals( "specialAttack" ) )
                affectedPokemon.setCurrentSpecialAttack( affectedPokemon.getCurrentSpecialAttack() + (int)propertyValue );
                tryRestoreCurrentSpecialAttack( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentSpecialAttack();
            case "specialDefense":
                if( !restoresLoweredStats || propertyName.equals( "specialDefense" ) )
                affectedPokemon.setCurrentSpecialDefense( affectedPokemon.getCurrentSpecialDefense() + (int)propertyValue );
                tryRestoreCurrentSpecialDefense( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentSpecialDefense();
            case "speed":
                if( !restoresLoweredStats || propertyName.equals( "speed" ) )
                affectedPokemon.setCurrentSpeed( affectedPokemon.getCurrentSpeed() + (int)propertyValue );
                tryRestoreCurrentSpeed( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentSpeed();
            case "evasion":
                if( !restoresLoweredStats || propertyName.equals( "evasion" ) )
                affectedPokemon.setCurrentEvasion( affectedPokemon.getCurrentEvasion() + (int)propertyValue );
                tryRestoreCurrentEvasion( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentEvasion();
            case "accuracy":
                if( !restoresLoweredStats || propertyName.equals( "accuracy" ) )
                affectedPokemon.setCurrentAccuracy( affectedPokemon.getCurrentAccuracy() + (int)propertyValue );
                tryRestoreCurrentAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentAccuracy();
            case "points":
                /* no item should add points */
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)PokeWorld.ERROR;
            case "exp":
                if( !restoresLoweredStats || propertyName.equals( "exp" ) )
                affectedPokemon.addExp( (int)propertyValue );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getTotalAddedExp();
            case "movePower":
                if( !affectsAllStats && !affectsAllMoves ) {
                    if( !restoresLoweredStats || propertyName.equals( "movePower" ) )
                    affectedPokemon.getCurrentMove().addPower( (int)propertyValue );
                    tryRestoreCurrentMovePower( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getCurrentPower();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            move.addPower( (int)propertyValue );
                            tryRestoreCurrentMovePower( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getCurrentPower();
                }
            case "moveAccuracy":
                if( !affectsAllStats && !affectsAllMoves ) {
                    if( !restoresLoweredStats || propertyName.equals( "moveAccuracy" ) )
                    affectedPokemon.getCurrentMove().addAccuracy( (int)propertyValue );
                    tryRestoreCurrentMoveAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getCurrentAccuracy();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            move.addAccuracy( (int)propertyValue );
                            tryRestoreCurrentMoveAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getCurrentAccuracy();
                }
            case "movePP":
                if( !affectsAllStats && !affectsAllMoves ) {
                    if( !restoresLoweredStats && propertyName.equals( "movePP" ) )
                    affectedPokemon.getCurrentMove().addToCurrentPP( (int)propertyValue );
                    tryRestoreCurrentMovePP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getCurrentPP();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            move.addToCurrentPP( (int)propertyValue );
                            tryRestoreCurrentMovePP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getCurrentPP();
                }
            case "criticalHitRatio":
                if( !restoresLoweredStats || propertyName.equals( "criticalHitRatio" ) )
                affectedPokemon.setCurrentCriticalHitRatio( affectedPokemon.getCurrentCriticalHitRatio() + propertyValue );
                tryRestoreCurrentCriticalHitRatio( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                return affectedPokemon.getCurrentCriticalHitRatio();
            default: break;
        }
        
        return (double)PokeWorld.ERROR;
    }
    
    /**
     * Adds the value to the given max property for the selected Pokemon, which is dependent on the 'affectThis' variable
     * 
     * @param propertyName The name of the property that is being affected by this Item
     * @param propertyValue The value of the property that is going to be added to the corresponding max property
     *                      of the selected Pokemon
     * @param item The item whose properties are affecting the selected Pokemon
     * @see useItemMenu()
     */
    private double itemAddMax( String propertyName, double propertyValue, Item item ) {
        boolean affectThis = item.getAffectThis();
        PokemonActor affectedPokemon = affectThis ? getCurrentPokemon() : getOtherPokemon();
        if( item.getRandomStat() )
            propertyName = Item.PROPERTY_NAMES[ new Random().nextInt( Item.PROPERTY_NAMES.length ) ];
            
        //Adjustment properties
        boolean affectsAllStats      = item.getAffectsAllStats();
        boolean affectsAllMoves      = item.getAffectsAllMoves();
        boolean restoreCurrentToFull = item.getRestoreCurrentToFull();
        boolean restoresLoweredStats = item.getRestoresLoweredStats();
        boolean maximizeValues       = propertyValue == (double)PokeWorld.ERROR ? true : false;
            
        switch( propertyName ) {
            case "HP":
                if( !restoresLoweredStats || propertyName.equals( "HP" ) )
                affectedPokemon.setHP( affectedPokemon.getHP() + (int)propertyValue );
                tryRestoreCurrentHP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getHP();
            case "attack":
                if( !restoresLoweredStats || propertyName.equals( "attack" ) )
                affectedPokemon.setAttack( affectedPokemon.getAttack() + (int)propertyValue );
                tryRestoreCurrentAttack( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getAttack();
            case "defense":
                if( !restoresLoweredStats || propertyName.equals( "defense" ) )
                affectedPokemon.setDefense( affectedPokemon.getDefense() + (int)propertyValue );
                tryRestoreCurrentDefense( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getDefense();
            case "specialAttack":
                if( !restoresLoweredStats || propertyName.equals( "specialAttack" ) )
                affectedPokemon.setSpecialAttack( affectedPokemon.getSpecialAttack() + (int)propertyValue );
                tryRestoreCurrentSpecialAttack( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getSpecialAttack();
            case "specialDefense":
                if( !restoresLoweredStats || propertyName.equals( "specialDefense" ) )
                affectedPokemon.setSpecialDefense( affectedPokemon.getSpecialDefense() + (int)propertyValue );
                tryRestoreCurrentSpecialDefense( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getSpecialDefense();
            case "speed":
                if( !restoresLoweredStats || propertyName.equals( "speed" ) )
                affectedPokemon.setSpeed( affectedPokemon.getSpeed() + (int)propertyValue );
                tryRestoreCurrentSpeed( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getSpeed();
            case "evasion":
                if( !restoresLoweredStats || propertyName.equals( "evasion" ) )
                affectedPokemon.setEvasion( affectedPokemon.getEvasion() + (int)propertyValue );
                tryRestoreCurrentEvasion( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getEvasion();
            case "accuracy":
                if( !restoresLoweredStats || propertyName.equals( "accuracy" ) )
                affectedPokemon.setAccuracy( affectedPokemon.getAccuracy() + (int)propertyValue );
                tryRestoreCurrentAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getAccuracy();
            case "points":
                /* no item should add points */
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)PokeWorld.ERROR;
            case "exp":
                if( !restoresLoweredStats || propertyName.equals( "exp" ) )
                affectedPokemon.addExp( (int)propertyValue );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getTotalAddedExp();
            case "movePower":
                if( !affectsAllStats && !affectsAllMoves ) {
                    if( !restoresLoweredStats || propertyName.equals( "movePower" ) ) {
                        Move move = affectedPokemon.getCurrentMove();
                        move.setPower( move.getPower() + (int)propertyValue );
                    }
                    tryRestoreCurrentMovePower( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getPower();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            if( !restoresLoweredStats || propertyName.equals( "movePower" ) )
                            move.setPower( move.getPower() + (int)propertyValue );
                            tryRestoreCurrentMovePower( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getPower();
                }
            case "moveAccuracy":
                if( !affectsAllStats && !affectsAllMoves ) {
                    Move move = affectedPokemon.getCurrentMove();
                    if( !restoresLoweredStats || propertyName.equals( "moveAccuracy" ) )
                    move.setAccuracy( move.getAccuracy() + (int)propertyValue );
                    if( maximizeValues ) {
                        if( move.getCategory() == Move.MoveCategory.SPECIAL &&
                            move.getAccuracy() < Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() )
                            move.setAccuracy( Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() );
                        else if( move.getAccuracy() < Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() )
                            move.setAccuracy( Move.getMaximumAccuracy() );
                    }
                    tryRestoreCurrentMoveAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getAccuracy();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            if( !restoresLoweredStats || propertyName.equals( "moveAccuracy" ) )
                            move.setAccuracy( move.getAccuracy() + (int)propertyValue );
                            if( move.getCategory() == Move.MoveCategory.SPECIAL &&
                                move.getAccuracy() < Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() )
                                move.setAccuracy( Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() );
                            else if( move.getAccuracy() < Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() )
                                move.setAccuracy( Move.getMaximumAccuracy() );
                            tryRestoreCurrentMoveAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getAccuracy();
                }
            case "movePP":
                if( !affectsAllStats && !affectsAllMoves ) {
                    Move move = affectedPokemon.getCurrentMove();
                    if( !restoresLoweredStats || propertyName.equals( "movePP" ) )
                    move.setPP( move.getPP() + (int)propertyValue );
                    if( maximizeValues && move.getPP() < Move.getMaximumPP() )
                        move.setPP( Move.getMaximumPP() );
                    tryRestoreCurrentMovePP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getPP();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            if( !restoresLoweredStats || propertyName.equals( "movePP" ) )
                            move.setPP( move.getPP() + (int)propertyValue );
                            if( maximizeValues && move.getPP() < Move.getMaximumPP() )
                                move.setPP( Move.getMaximumPP() );
                            tryRestoreCurrentMovePP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getPP();
                }
            case "criticalHitRatio":
                if( !restoresLoweredStats || propertyName.equals( "criticalHitRatio" ) )
                affectedPokemon.setCriticalHitRatio( affectedPokemon.getCriticalHitRatio() + propertyValue );
                tryRestoreCurrentCriticalHitRatio( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                return affectedPokemon.getCriticalHitRatio();
            default: break;
        }
        
        return (double)PokeWorld.ERROR;
    }
    
    /**
     * Adds the value to the given current property for the selected Pokemon, which is dependent on the 'affectThis' variable
     * 
     * @param propertyName The name of the property that is being affected by this Item
     * @param propertyValue The value of the property that is going to be added to the corresponding current property
     *                      of the selected Pokemon
     * @param item The item whose properties are affecting the selected Pokemon
     * @see useItemMenu()
     * @see Item.getAffectThis()
     * @see getCurrentPokemon()
     * @see getOtherPokemon()
     * @see PokemonActor.addHP( int HP )
     * @see PokemonActor.getCurrentHP()
     * @see PokemonActor.setCurrentAttack( int currentAttack )
     * @see PokemonActor.getCurrentAttack()
     * @see ...
     */
    private double itemMultCurrent( String propertyName, double propertyValue, Item item ) {
        boolean affectThis = item.getAffectThis();
        PokemonActor affectedPokemon = affectThis ? getCurrentPokemon() : getOtherPokemon();
        if( item.getRandomStat() )
            propertyName = Item.PROPERTY_NAMES[ new Random().nextInt( Item.PROPERTY_NAMES.length ) ];
        
        //Adjustment properties
        boolean affectsAllStats      = item.getAffectsAllStats();
        boolean affectsAllMoves      = item.getAffectsAllMoves();
        boolean restoreCurrentToFull = item.getRestoreCurrentToFull();
        boolean restoresLoweredStats = item.getRestoresLoweredStats();
        boolean maximizeValues       = propertyValue == (double)PokeWorld.ERROR ? true : false;
            
        switch( propertyName ) {
            case "HP":
                if( !restoresLoweredStats || propertyName.equals( "HP" ) )
                affectedPokemon.addHP( (int)(affectedPokemon.getCurrentHP() * propertyValue) );
                tryRestoreCurrentHP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentHP();
            case "attack":
                if( !restoresLoweredStats || propertyName.equals( "attack" ) )
                affectedPokemon.setCurrentAttack(
                    affectedPokemon.getCurrentAttack() + (int)(affectedPokemon.getCurrentAttack() * propertyValue) );
                tryRestoreCurrentAttack( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentAttack();
            case "defense":
                if( !restoresLoweredStats || propertyName.equals( "defense" ) )
                affectedPokemon.setCurrentDefense(
                    affectedPokemon.getCurrentDefense() + (int)(affectedPokemon.getCurrentDefense() * propertyValue) );
                tryRestoreCurrentDefense( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentDefense();
            case "specialAttack":
                if( !restoresLoweredStats || propertyName.equals( "specialAttack" ) )
                affectedPokemon.setCurrentSpecialAttack(
                    affectedPokemon.getCurrentSpecialAttack() + (int)(affectedPokemon.getCurrentSpecialAttack() * propertyValue) );
                tryRestoreCurrentSpecialAttack( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentSpecialAttack();
            case "specialDefense":
                if( !restoresLoweredStats || propertyName.equals( "specialDefense" ) )
                affectedPokemon.setCurrentSpecialDefense(
                    affectedPokemon.getCurrentSpecialDefense() + (int)(affectedPokemon.getCurrentSpecialDefense() * propertyValue) );
                tryRestoreCurrentSpecialDefense( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentSpecialDefense();
            case "speed":
                if( !restoresLoweredStats || propertyName.equals( "speed" ) )
                affectedPokemon.setCurrentSpeed( 
                    affectedPokemon.getCurrentSpeed() + (int)(affectedPokemon.getCurrentSpeed() * propertyValue) );
                tryRestoreCurrentSpeed( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentSpeed();
            case "evasion":
                if( !restoresLoweredStats || propertyName.equals( "evasion" ) )
                affectedPokemon.setCurrentEvasion(
                    affectedPokemon.getCurrentEvasion() + (int)(affectedPokemon.getCurrentEvasion() * propertyValue) );
                tryRestoreCurrentEvasion( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentEvasion();
            case "accuracy":
                if( !restoresLoweredStats || propertyName.equals( "accuracy" ) )
                affectedPokemon.setCurrentAccuracy(
                    affectedPokemon.getCurrentAccuracy() + (int)(affectedPokemon.getCurrentAccuracy() * propertyValue) );
                tryRestoreCurrentAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getCurrentAccuracy();
            case "points":
                /* no item should add points */
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)PokeWorld.ERROR;
            case "exp":
                if( !restoresLoweredStats || propertyName.equals( "exp" ) )
                affectedPokemon.addExp( (int)(affectedPokemon.getTotalExp() * propertyValue) );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getTotalAddedExp();
            case "movePower":
                if( !affectsAllStats && !affectsAllMoves ) {
                    if( !restoresLoweredStats || propertyName.equals( "movePower" ) )
                    affectedPokemon.getCurrentMove().addPower(
                        (int)(affectedPokemon.getCurrentMove().getCurrentPower() * propertyValue) );
                    tryRestoreCurrentMovePower( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getCurrentPower();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            if( !restoresLoweredStats || propertyName.equals( "movePower" ) )
                            move.addPower( (int)(move.getCurrentPower() * propertyValue) );
                            tryRestoreCurrentMovePower( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getCurrentPower();
                }
            case "moveAccuracy":
                if( !affectsAllStats && !affectsAllMoves ) {
                    if( !restoresLoweredStats || propertyName.equals( "moveAccuracy" ) )
                    affectedPokemon.getCurrentMove().addAccuracy(
                        (int)(affectedPokemon.getCurrentMove().getCurrentAccuracy() * propertyValue) );
                    tryRestoreCurrentMoveAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getCurrentAccuracy();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            if( !restoresLoweredStats || propertyName.equals( "moveAccuracy" ) )
                            move.addAccuracy( (int)(move.getCurrentAccuracy() * propertyValue) );
                            tryRestoreCurrentMoveAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getCurrentAccuracy();
                }
            case "movePP":
                if( !affectsAllStats && !affectsAllMoves ) {
                    if( !restoresLoweredStats || propertyName.equals( "movePP" ) )
                    affectedPokemon.getCurrentMove().addPP(
                        (int)(affectedPokemon.getCurrentMove().getCurrentPP() * propertyValue) );
                    tryRestoreCurrentMovePP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getCurrentPP();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            if( !restoresLoweredStats || propertyName.equals( "movePP" ) )
                            move.addPP( (int)(move.getCurrentPP() * propertyValue) );
                            tryRestoreCurrentMovePP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getCurrentPP();
                }
            case "criticalHitRatio":
                if( !restoresLoweredStats || propertyName.equals( "criticalHitRatio" ) )
                affectedPokemon.setCurrentCriticalHitRatio( affectedPokemon.getCurrentCriticalHitRatio() +
                    (affectedPokemon.getCurrentCriticalHitRatio() * propertyValue) );
                tryRestoreCurrentCriticalHitRatio( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                return affectedPokemon.getCurrentCriticalHitRatio();
            default: break;
        }
        
        return (double)PokeWorld.ERROR;
    }
    
    /**
     * Adds the value to the given max property for the selected Pokemon, which is dependent on the 'affectThis' variable
     * 
     * @param propertyName The name of the property that is being affected by this Item
     * @param propertyValue The value of the property that is going to be added to the corresponding max property
     *                      of the selected Pokemon
     * @param item The item whose properties are affecting the selected Pokemon
     * @see useItemMenu()
     */
    private double itemMultMax( String propertyName, double propertyValue, Item item ) {
        boolean affectThis = item.getAffectThis();
        PokemonActor affectedPokemon = affectThis ? getCurrentPokemon() : getOtherPokemon();
        if( item.getRandomStat() )
            propertyName = Item.PROPERTY_NAMES[ new Random().nextInt( Item.PROPERTY_NAMES.length ) ];
        
        //Adjustment properties
        boolean affectsAllStats      = item.getAffectsAllStats();
        boolean affectsAllMoves      = item.getAffectsAllMoves();
        boolean restoreCurrentToFull = item.getRestoreCurrentToFull();
        boolean restoresLoweredStats = item.getRestoresLoweredStats();
        boolean maximizeValues       = propertyValue == (double)PokeWorld.ERROR ? true : false;
            
        switch( propertyName ) {
            case "HP":
                if( !restoresLoweredStats || propertyName.equals( "HP" ) )
                affectedPokemon.setHP(
                    affectedPokemon.getHP() + (int)(affectedPokemon.getHP() * propertyValue) );
                tryRestoreCurrentHP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getHP();
            case "attack":
                if( !restoresLoweredStats || propertyName.equals( "attack" ) )
                affectedPokemon.setAttack(
                    affectedPokemon.getAttack() + (int)(affectedPokemon.getAttack() * propertyValue) );
                tryRestoreCurrentAttack( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getAttack();
            case "defense":
                if( !restoresLoweredStats || propertyName.equals( "defense" ) )
                affectedPokemon.setDefense(
                    affectedPokemon.getDefense() + (int)(affectedPokemon.getDefense() * propertyValue) );
                tryRestoreCurrentDefense( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getDefense();
            case "specialAttack":
                if( !restoresLoweredStats || propertyName.equals( "specialAttack" ) )
                affectedPokemon.setSpecialAttack(
                    affectedPokemon.getSpecialAttack() + (int)(affectedPokemon.getSpecialAttack() * propertyValue) );
                tryRestoreCurrentSpecialAttack( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getSpecialAttack();
            case "specialDefense":
                if( !restoresLoweredStats || propertyName.equals( "specialDefense" ) )
                affectedPokemon.setSpecialDefense(
                    affectedPokemon.getSpecialDefense() + (int)(affectedPokemon.getSpecialDefense() * propertyValue) );
                tryRestoreCurrentSpecialDefense( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getSpecialDefense();
            case "speed":
                if( !restoresLoweredStats || propertyName.equals( "speed" ) )
                affectedPokemon.setSpeed(
                    affectedPokemon.getSpeed() + (int)(affectedPokemon.getSpeed() * propertyValue) );
                tryRestoreCurrentSpeed( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getSpeed();
            case "evasion":
                if( !restoresLoweredStats || propertyName.equals( "evasion" ) )
                affectedPokemon.setEvasion(
                    affectedPokemon.getEvasion() + (int)(affectedPokemon.getEvasion() * propertyValue) );
                tryRestoreCurrentEvasion( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getEvasion();
            case "accuracy":
                if( !restoresLoweredStats || propertyName.equals( "accuracy" ) )
                affectedPokemon.setAccuracy(
                    affectedPokemon.getAccuracy() + (int)(affectedPokemon.getAccuracy() * propertyValue) );
                tryRestoreCurrentAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getAccuracy();
            case "points":
                /* no item should add points */
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)PokeWorld.ERROR;
            case "exp":
                if( !restoresLoweredStats || propertyName.equals( "exp" ) )
                affectedPokemon.addExp( (int)(affectedPokemon.getTotalExp() * propertyValue) );
                if( !affectsAllStats && !restoresLoweredStats )
                return (double)affectedPokemon.getTotalAddedExp();
            case "movePower":
                if( !affectsAllStats && !affectsAllMoves ) {
                    Move move = affectedPokemon.getCurrentMove();
                    if( !restoresLoweredStats || propertyName.equals( "movePower" ) )
                    move.setPower( move.getPower() + (int)(move.getPower() * propertyValue) );
                    tryRestoreCurrentMovePower( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getPower();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            if( !restoresLoweredStats || propertyName.equals( "movePower" ) )
                            move.setPower( move.getPower() + (int)(move.getPower() * propertyValue) );
                            tryRestoreCurrentMovePower( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getPower();
                }
            case "moveAccuracy":
                if( !affectsAllStats && !affectsAllMoves ) {
                    Move move = affectedPokemon.getCurrentMove();
                    if( !restoresLoweredStats || propertyName.equals( "moveAccuracy" ) )
                    move.setAccuracy( move.getAccuracy() + (int)(move.getAccuracy() * propertyValue) );
                    if( maximizeValues ) {
                        if( move.getCategory() == Move.MoveCategory.SPECIAL &&
                            move.getAccuracy() < Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() )
                            move.setAccuracy( Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() );
                        else if( move.getAccuracy() < Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() )
                            move.setAccuracy( Move.getMaximumAccuracy() );
                    }
                    tryRestoreCurrentMoveAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getAccuracy();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            if( !restoresLoweredStats || propertyName.equals( "moveAccuracy" ) )
                            move.setAccuracy( move.getAccuracy() + (int)(move.getAccuracy() * propertyValue) );
                            if( maximizeValues ) {
                                if( move.getCategory() == Move.MoveCategory.SPECIAL &&
                                    move.getAccuracy() < Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() )
                                    move.setAccuracy( Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() );
                                else if( move.getAccuracy() < Move.getMaximumAccuracy() - Move.getSpecialAccuracyLimit() )
                                    move.setAccuracy( Move.getMaximumAccuracy() );
                            }
                            tryRestoreCurrentMoveAccuracy( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getAccuracy();
                }
            case "movePP":
                if( !affectsAllStats && !affectsAllMoves ) {
                    Move move = affectedPokemon.getCurrentMove();
                    if( !restoresLoweredStats || propertyName.equals( "movePP" ) )
                    move.setPP( move.getPP() + (int)( move.getPP() * propertyValue) );
                    if( maximizeValues && move.getPP() < Move.getMaximumPP() )
                        move.setPP( Move.getMaximumPP() );
                    tryRestoreCurrentMovePP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                    if( !restoresLoweredStats )
                    return (double)affectedPokemon.getCurrentMove().getPP();
                } else if( !affectsAllStats && affectsAllMoves ) {
                    ArrayList<Move> moves = affectedPokemon.getMoves();
                    for( Move move : moves ) {
                        String reqXType = item.getReqXType();
                        String reqXCat  = item.getReqXCat();
                        boolean matchesType = reqXType.equals("na") ||
                                              reqXType.replace("req","").replace("Type","").toUpperCase()
                                              .equals( move.getType().name() ) ? true : false;
                        boolean matchesCat  = reqXCat.equals("na") ||
                                              reqXCat.replace("req","").replace("Cat","").toUpperCase()
                                              .equals( move.getCategory().name() ) ? true : false;
                        if( matchesType && matchesCat ) {
                            if( !restoresLoweredStats || propertyName.equals( "movePP" ) )
                            move.setPP( move.getPP() + (int)(move.getPP() * propertyValue) );
                            if( maximizeValues && move.getPP() < Move.getMaximumPP() )
                                move.setPP( Move.getMaximumPP() );
                            tryRestoreCurrentMovePP( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                        }
                    }
                    if( !restoresLoweredStats )
                    return (double)moves.get(0).getPP();
                }
            case "criticalHitRatio":
                if( !restoresLoweredStats || propertyName.equals( "criticalHitRatio" ) )
                affectedPokemon.setCriticalHitRatio(
                    affectedPokemon.getCriticalHitRatio() + (affectedPokemon.getCriticalHitRatio() * propertyValue) );
                tryRestoreCurrentCriticalHitRatio( restoreCurrentToFull, maximizeValues, restoresLoweredStats, affectedPokemon );
                return affectedPokemon.getCriticalHitRatio();
            default: break;
        }
        
        return (double)PokeWorld.ERROR;
    }
    
    /**
     * The tryRestoreCurrent____ helper functions restore current values to their maximums, making use of the
     * maximizeValues and restoreCurrentToFull properties of that item
     * 
     * @param restoreCurrentToFull True if the item restores the current property value to its max value, false otherwise
     * @param maximizeValues True if the item restores the current property value to its max value, false otherwise
     * @param restoresLoweredStats True if the item restores all lowered current property values to their max values,
     *                             false otherwise
     * @param affectedPokemon The Pokemon that is affected by these effects
     * @see ...
     */
    private void tryRestoreCurrentHP( boolean restoreCurrentToFull, boolean maximizeValues, boolean restoresLoweredStats,
                                      PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentHP() < affectedPokemon.getHP() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.setCurrentHP( affectedPokemon.getHP() );
    }
    private void tryRestoreCurrentAttack( boolean restoreCurrentToFull, boolean maximizeValues, boolean restoresLoweredStats,
                                          PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentAttack() < affectedPokemon.getAttack() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.setCurrentAttack( affectedPokemon.getAttack() ); 
    }
    private void tryRestoreCurrentDefense( boolean restoreCurrentToFull, boolean maximizeValues, boolean restoresLoweredStats,
                                           PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentDefense() < affectedPokemon.getDefense() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.setCurrentDefense( affectedPokemon.getDefense() ); 
    }
    private void tryRestoreCurrentSpecialAttack( boolean restoreCurrentToFull, boolean maximizeValues,
                                                 boolean restoresLoweredStats, PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentSpecialAttack() < affectedPokemon.getSpecialAttack() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.setCurrentSpecialAttack( affectedPokemon.getSpecialAttack() ); 
    }
    private void tryRestoreCurrentSpecialDefense( boolean restoreCurrentToFull, boolean maximizeValues,
                                                  boolean restoresLoweredStats, PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentSpecialDefense() < affectedPokemon.getSpecialDefense() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.setCurrentSpecialDefense( affectedPokemon.getSpecialDefense() ); 
    }
    private void tryRestoreCurrentSpeed( boolean restoreCurrentToFull, boolean maximizeValues,
                                         boolean restoresLoweredStats, PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentSpeed() < affectedPokemon.getSpeed() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.setCurrentSpeed( affectedPokemon.getSpeed() ); 
    }
    private void tryRestoreCurrentEvasion( boolean restoreCurrentToFull, boolean maximizeValues,
                                           boolean restoresLoweredStats, PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentEvasion() < affectedPokemon.getEvasion() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.setCurrentEvasion( affectedPokemon.getEvasion() ); 
    }
    private void tryRestoreCurrentAccuracy( boolean restoreCurrentToFull, boolean maximizeValues,
                                            boolean restoresLoweredStats, PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentAccuracy() < affectedPokemon.getAccuracy() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.setCurrentAccuracy( affectedPokemon.getAccuracy() );
    }
    private void tryRestoreCurrentMovePower( boolean restoreCurrentToFull, boolean maximizeValues,
                                             boolean restoresLoweredStats, PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentMove().getCurrentPower() < affectedPokemon.getCurrentMove().getPower() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.getCurrentMove().setCurrentPower( affectedPokemon.getCurrentMove().getPower() );
    }
    private void tryRestoreCurrentMoveAccuracy( boolean restoreCurrentToFull, boolean maximizeValues,
                                                boolean restoresLoweredStats, PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentMove().getCurrentAccuracy() < affectedPokemon.getCurrentMove().getAccuracy() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.getCurrentMove().setCurrentAccuracy( affectedPokemon.getCurrentMove().getAccuracy() );
    }
    private void tryRestoreCurrentMovePP( boolean restoreCurrentToFull, boolean maximizeValues,
                                          boolean restoresLoweredStats, PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentMove().getCurrentPP() < affectedPokemon.getCurrentMove().getPP() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.getCurrentMove().setCurrentPP( affectedPokemon.getCurrentMove().getPP() );
    }
    private void tryRestoreCurrentCriticalHitRatio( boolean restoreCurrentToFull, boolean maximizeValues,
                                                    boolean restoresLoweredStats, PokemonActor affectedPokemon ) {
        if( affectedPokemon.getCurrentCriticalHitRatio() < affectedPokemon.getCriticalHitRatio() &&
            (restoreCurrentToFull || maximizeValues || restoresLoweredStats ) )
            affectedPokemon.setCurrentCriticalHitRatio( affectedPokemon.getCriticalHitRatio() ); 
    }
    
    /**
     * Sets the menu when RUN is selected within the main menu
     * 
     * @see selectMenu()
     * @see SelectionArrow enum
     */
    private void setRunMenu() {
        setText( 500, 100, getCurrentPokemon().getName() + " can't run away from this battle!",
                 20, 20, Color.WHITE, true, 270, 450 );
        
        currentTurn = BattleTurn.INTRO;
    }
    
    /* @@@@@@ TextImage Object Getter and Setter Methods @@@@@@ */
    /**
     * Sets a large text box's text with default values
     * 
     * @see setText( int width, int height, String text, int xOffset, int yOffset,
     *               Color color, boolean isBold, int x, int y )
     */
    private void setText( String text ) {
        setText( TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT, text, TEXT_BOX_OFFSET_X, TEXT_BOX_OFFSET_Y,
                 Color.WHITE, true /*bold text*/, TEXT_BOX_X, TEXT_BOX_Y );
    }
    
    /**
     * Sets a large text box's text with default values
     * 
     * @see setText( int width, int height, String text, int xOffset, int yOffset,
     *               Color color, boolean isBold, int x, int y )
     */
    private void setText( String text, int addXOffset, int addYOffset ) {
        setText( TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT, text, TEXT_BOX_OFFSET_X + addXOffset, TEXT_BOX_OFFSET_Y + addYOffset,
                 Color.WHITE, true /*bold text*/, TEXT_BOX_X, TEXT_BOX_Y );
    }
    
    /**
     * Sets a large text box's text
     * 
     * @param width The width of the box that the text is placed in
     * @param height The height of the box that the text is placed in
     * @param text The text to be placed on the TextImage
     * @param xOffset The offset from the top left corner of the TextImage in the x direction (right)
     * @param yOffset The offset from the top left corner of the TextImage in the y direction (down)
     * @param x The x coordinate of the TextImage
     * @param y The y coordinate of the TextImage
     * @see setFightMenu()
     * @see getTextImage( int width, int height, String text, int xOffset, int yOffset,
     *                    Color color, boolean isBold )
     */
    private void setText( int width, int height, String text, int xOffset, int yOffset,
                               Color color, boolean isBold, int x, int y ) {
        TextImage menuImage = getTextImage( width, height, text, xOffset, yOffset, color, isBold );
        textList.add( menuImage );
        addObject( menuImage, x, y );
    }
    
    /**
     * Sets a large text box's text
     * 
     * @param width The width of the box that the text is placed in
     * @param height The height of the box that the text is placed in
     * @param text The text to be placed on the TextImage
     * @param xOffset The offset from the top left corner of the TextImage in the x direction (right)
     * @param yOffset The offset from the top left corner of the TextImage in the y direction (down)
     * @param x The x coordinate of the TextImage
     * @param y The y coordinate of the TextImage
     * @param fontSize The size of the font
     * @see setFightMenu()
     * @see getTextImage( int width, int height, String text, int xOffset, int yOffset,
     *                    Color color, boolean isBold )
     */
    private void setText( int width, int height, String text, int xOffset, int yOffset,
                               Color color, boolean isBold, int x, int y, int fontSize ) {
        TextImage menuImage = getTextImage( width, height, text, xOffset, yOffset, color, isBold, fontSize );
        textList.add( menuImage );
        addObject( menuImage, x, y );
    }
    
    /**
     * Sets a small text box image from the given image file path and the rows of text
     * 
     * @param imageLoc The image path for the TextImage
     * @param text The text to be placed on the TextImage
     * @param xOffset The offset from the top left corner of the image to place the text in the x direction (right)
     * @param yOffset The offset from the top left corner of the image to place the text in the y direction (down)
     * @param x The x coordinate of this TextImage
     * @param y The y coordinate of this TextImage
     * @see setFightMenu()
     */
    private void setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y ) {
        TextImage menuImage = getTextImage( imageLoc, text, xOffset, yOffset );
        textList.add( menuImage );
        addObject( menuImage, x, y );
    }
    
    /**
     * Sets a small text box image from the given image file path and the rows of text
     * 
     * @param imageLoc The image path for the TextImage
     * @param text The text to be placed on the TextImage
     * @param xOffset The offset from the top left corner of the image to place the text in the x direction (right)
     * @param yOffset The offset from the top left corner of the image to place the text in the y direction (down)
     * @param x The x coordinate of this TextImage
     * @param y The y coordinate of this TextImage
     * @see setFightMenu()
     */
    private void setTextImage( GreenfootImage image, String text, int xOffset, int yOffset, int x, int y ) {
        TextImage menuImage = getTextImage( image, text, xOffset, yOffset );
        textList.add( menuImage );
        addObject( menuImage, x, y );
    }
    
    /**
     * Creates the bottom row of text for the menu as a TextImage object
     * using MENU_TEXT_BOTTOM
     * 
     * @return TextImage The bottom row of text for the menu TextImage object
     */
    private TextImage getTextImage( int width, int height, String text, int xOffset, int yOffset,
                                    Color color, boolean isBold ) {
        TextImage menuText = new TextImage(
            width,
            height,
            text,
            color,
            MEDIUM_FONT_SIZE,
            false, /* doesn't expire */
            xOffset,
            yOffset,
            isBold /* no bold text */
        );
        
        return menuText;
    }
    
    /**
     * Creates the bottom row of text for the menu as a TextImage object
     * using MENU_TEXT_BOTTOM
     * 
     * @return TextImage The bottom row of text for the menu TextImage object
     */
    private TextImage getTextImage( int width, int height, String text, int xOffset, int yOffset,
                                    Color color, boolean isBold, int fontSize ) {
        TextImage menuText = new TextImage(
            width,
            height,
            text,
            color,
            fontSize,
            false, /* doesn't expire */
            xOffset,
            yOffset,
            isBold /* no bold text */
        );
        
        return menuText;
    }
    
    /**
     * Creates a small TextImage with the given text
     * 
     * @param imageLoc The image path for this TextImage
     * @param text The text to place in the TextImage
     * @param xOffset The offset from the top left corner of the image to place the text in the x direction (right)
     * @param yOffset The offset from the top left corner of the image to place the text in the y direction (down)
     * @return TextImage The small TextImage with the given text
     * @see setMenu( String imageLoc )
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     */
    private TextImage getTextImage( String imageLoc, String text, int xOffset, int yOffset ) {
        TextImage menuImage = new TextImage(
            imageLoc,
            text,
            Color.BLACK,
            MEDIUM_FONT_SIZE,
            false, /* doesn't expire */
            xOffset,
            yOffset
        );
        
        return menuImage;
    }
    
    /**
     * Creates a small TextImage with the given text
     * 
     * @param imageLoc The image path for this TextImage
     * @param text The text to place in the TextImage
     * @param xOffset The offset from the top left corner of the image to place the text in the x direction (right)
     * @param yOffset The offset from the top left corner of the image to place the text in the y direction (down)
     * @return TextImage The small TextImage with the given text
     * @see setMenu( String imageLoc )
     * @see setTextImage( String imageLoc, String text, int xOffset, int yOffset, int x, int y )
     */
    private TextImage getTextImage( GreenfootImage image, String text, int xOffset, int yOffset ) {
        TextImage menuImage = new TextImage(
            image,
            text,
            Color.BLACK,
            MEDIUM_FONT_SIZE,
            false, /* doesn't expire */
            xOffset,
            yOffset
        );
        
        return menuImage;
    }
    
    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
}
