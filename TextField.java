import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Create an editable text field object
 * 
 * @author Gevater_Tod4711; updated by Peter Olson
 * @version 2013
 */
public class TextField extends Actor {
    private boolean active = false;
    private boolean cursorActive = false;
    private boolean displayKeyNames;
    private boolean enabled = true;
    
    private static int activeTextField = 1;
    
    private int textFieldNumber;
    private int textFontSize;
    private int cursorPosition = 0;
    
    private long cursorTime = System.currentTimeMillis();
    
    private String input = "";
    private String text = "";
    private String temp = "";
    
    private Color bgColor = Color.WHITE;
    private Color textColor = Color.BLACK;
    
    /**
     * Creates a new TextField of a default size of 250 x 50 with black letters on white background.
     */
    public TextField() {
        this(250, 50, false, true, Color.WHITE, Color.BLACK, "");
    }
    
    /**
     * Creates a new TextField with a default size of 250 x 50 with black letters on white background and a starting text.
     * 
     * @param text
     *      The text that is shown directly.
     */
    public TextField(String text) {
        this(250, 50, false, true, Color.WHITE, Color.BLACK, text);
    }
    
    /**
     * Creates a new TextField with a default size of 250 x 50 with black letters on white background and a starting text.
     * 
     * @param text
     *      The text that is shown directly.
     * 
     * @param enabled
     *      If you want the textfield to be enabled (able to write in it) this boolean has to be true.
     */
    public TextField(String text, boolean enabled) {
        this(250, 50, false, enabled, Color.WHITE, Color.BLACK, text);
    }
    
    /**
     * Creates a new TextField of the given size with black letters on white background.
     * 
     * @param width
     *      The TextFields width (which has to be greater than 50).
     * 
     * @param height
     *      The TextFields height (which has to be greater than 20).
     */
    public TextField(int width, int height) throws IllegalArgumentException {
        this(width, height, false, true, Color.WHITE, Color.BLACK, "");
    }
    
    /**
     * Creates a new TextField of the given size with black letters on white background.
     * 
     * @param width
     *      The TextFields width (which has to be greater than 50).
     * 
     * @param height
     *      The TextFields height (which has to be greater than 20).
     * 
     * @param enabled
     *      If you want the textfield to be enabled (able to write in it) this boolean has to be true.
     */
    public TextField(int width, int height, boolean enabled) throws IllegalArgumentException {
        this(width, height, false, enabled, Color.WHITE, Color.BLACK, "");
    }
    
    /**
     * Creates a new TextField of the given size with black letters on white background and a starting text.
     * 
     * @param width
     *      The TextFields width (which has to be greater than 50).
     * 
     * @param height
     *      The TextFields height (which has to be greater than 20).
     * 
     * @param text
     *      The text that is shown directly.
     */
    public TextField(int width, int height, String text) throws IllegalArgumentException {
        this(width, height, false, true, Color.WHITE, Color.BLACK, text);
    }
    
    /**
     * Creates a new TextField of the given size with black letters on white background and a starting text.
     * 
     * @param width
     *      The TextFields width (which has to be greater than 50).
     * 
     * @param height
     *      The TextFields height (which has to be greater than 20).
     * 
     * @param displayKeyNames
     *      If you want to use the textfield for only one key this parameter has to be true.
     * 
     * @param text
     *      The text that is shown directly.
     */
    public TextField(int width, int height, boolean displayKeyNames, String text) throws IllegalArgumentException {
        this(width, height, displayKeyNames, true, Color.WHITE, Color.BLACK, text);
    }
    
    /**
     * Creates a new TextField of the given size and the given colors.
     * 
     * @param width
     *      The TextFields width (which has to be greater than 50).
     * 
     * @param height
     *      The TextFields height (which has to be greater than 20).
     * 
     * @param bgColor
     *      The color of the TextFields background.
     * 
     * @param textColor
     *      The color of the text.
     */
    public TextField(int width, int height, Color bgColor, Color textColor) throws IllegalArgumentException {
        this(width, height, false, true, bgColor, textColor, "");
    }
    
    /**
     * Creates a new TextField of the given size and the given colors.
     * 
     * @param width
     *      The TextFields width (which has to be greater than 50).
     * 
     * @param height
     *      The TextFields height (which has to be greater than 20).
     * 
     * @param displayKeyNames
     *      If you want to use the textfield for only one key this parameter has to be true.
     * 
     * @param bgColor
     *      The color of the TextFields background.
     * 
     * @param textColor
     *      The color of the text.
     */
    public TextField(int width, int height, boolean displayKeyNames, Color bgColor, Color textColor) throws IllegalArgumentException {
        this(width, height, false, true, bgColor, textColor, "");
    }
    
    /**
     * Creates a new TextField of the given size, the given colors and a starting text.
     * 
     * @param width
     *      The TextFields width (which has to be greater than 50).
     * 
     * @param height
     *      The TextFields height (which has to be greater than 20).
     * 
     * @param bgColor
     *      The color of the TextFields background.
     * 
     * @param textColor
     *      The color of the text.
     */
    public TextField(int width, int height, Color bgColor, Color textColor, String text) throws IllegalArgumentException {
        this(width, height, false, true, bgColor, textColor, text);
    }
    
    /**
     * Creates a new TextField of the given size, the given colors and a starting text.
     * 
     * @param width
     *      The TextFields width (which has to be greater than 50).
     * 
     * @param height
     *      The TextFields height (which has to be greater than 20).
     * 
     * @param displayKeyNames
     *      If you want to use the textfield for only one key this parameter has to be true.
     * 
     * @param bgColor
     *      The color of the TextFields background.
     * 
     * @param textColor
     *      The color of the text.
     */
    public TextField(int width, int height, boolean displayKeyNames, Color bgColor, Color textColor, String text) throws IllegalArgumentException {
        this(width, height, displayKeyNames, true, bgColor, textColor, text);
    }
    
    /**
     * Creates a new TextField of the given size, the given colors and a starting text.
     * 
     * @param width
     *      The TextFields width (which has to be greater than 50).
     * 
     * @param height
     *      The TextFields height (which has to be greater than 20).
     * 
     * @param displayKeyNames
     *      If you want to use the textfield for only one key this parameter has to be true.
     * 
     * @param enabled
     *      If you want the textfield to be enabled (able to write in it) this boolean has to be true.
     * 
     * @param bgColor
     *      The color of the TextFields background.
     * 
     * @param textColor
     *      The color of the text.
     */
    public TextField(int width, int height, boolean displayKeyNames, boolean enabled, Color bgColor, Color textColor, String text) throws IllegalArgumentException {
        if (width < 50) {
            throw new IllegalArgumentException("The width of the text field has to be greater or equal 50");
        }
        else if (height < 20) {
            throw new IllegalArgumentException("The height of the text field has to be greater or equal 20");
        }
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.text = text;
        this.displayKeyNames = displayKeyNames;
        this.enabled = enabled;
        cursorPosition = text.length();
        getImage().clear();
        getImage().scale(width, height);
        textFontSize = height - 10;
        resetImage();
        displayText();
    }
    
    /**
     * Assigns this field a number.
     * This method is called when the object is added to the world.
     * 
     * @param world The world of which this TextField object is added
     */
    public void addedToWorld( World world ) {
        textFieldNumber = getWorld().getObjects( TextField.class ).size();
    }
    
    /**
     * Control the TextFields action.
     */
    public void act() {
        if( Greenfoot.mouseClicked( this ) ) {
            activeTextField = textFieldNumber;
            active = true;
        }
        if( active ) {
            if( activeTextField != textFieldNumber ) {
                active = false;
                cursorActive = false;
                displayText();
                return;
            }
            if( System.currentTimeMillis() - cursorTime > 750 ) {
                cursorTime = System.currentTimeMillis();
                cursorActive = !cursorActive;
            }
            input = Greenfoot.getKey();
            if( enabled ) {
                if( displayKeyNames ) {
                    if( input != null && input != "" ) {
                        text = input;
                    }
                } else {
                    if( input == "backspace" && text.length() != 0 && cursorPosition != 0 ) {
                        if( cursorPosition == text.length() ) {
                            text = text.substring( 0, text.length() - 1 );
                        } else {
                            temp = text.substring( 0, cursorPosition - 1 );
                            temp += text.substring( cursorPosition, text.length() - 1 );
                            text = temp;
                            temp = "";
                        }
                        cursorPosition--;
                    } else if( input == "enter" ) {
                        setActiveTextField();
                    } else if( input == "space" ) {
                        if( cursorPosition == text.length() ) {
                            text += " ";
                        } else {
                            temp = text.substring( 0, cursorPosition );
                            temp += " ";
                            temp += text.substring( cursorPosition, text.length() - 1 );
                            text = temp;
                            temp = "";
                        }
                        cursorPosition++;
                    } else if( input == "left" ) {
                        if( cursorPosition > 0 ) {
                            cursorPosition--;
                            cursorTime = System.currentTimeMillis();
                            cursorActive = true;
                        }
                    } else if( input == "right" ) {
                        if( cursorPosition < text.length() ) {
                            cursorPosition++;
                            cursorTime = System.currentTimeMillis();
                            cursorActive = true;
                        }
                    } else if( input != null && input.length() == 1 ) {
                        if( cursorPosition == text.length() ) {
                            text += input;
                        } else {
                            temp = text.substring( 0, cursorPosition );
                            temp += input;
                            temp += text.substring( cursorPosition, text.length() - 1 );
                            text = temp;
                            temp = "";
                        }
                        cursorPosition++;
                    }
                }
            }
            else if( input == "enter" ) {
                setActiveTextField();
            }
            displayText();
        } else {
            if( activeTextField == textFieldNumber ) {
                active = true;
            }
        }
    }
    
    /**
     * Display the text onto the TextField.
     */
    private void displayText() {
        GreenfootImage textImage = new GreenfootImage( text, textFontSize, textColor, new Color(0, 0, 0, 0) );
        GreenfootImage textBeforeCursor = new GreenfootImage( text.substring( 0, cursorPosition ), textFontSize, textColor, new Color(0, 0, 0, 0) );
        resetImage();
        getImage().drawImage( textImage, ( textImage.getWidth() > getImage().getWidth() - 10 ? -( textImage.getWidth() - getImage().getWidth() ) - 10 : 5 ), ( getImage().getHeight() / 2 - textImage.getHeight() / 2 ) );
        getImage().setColor( textColor );
        if( cursorActive ) {
            getImage().fillRect( ( textBeforeCursor.getWidth() > getImage().getWidth() - 10 ? getImage().getWidth() - 8 : textBeforeCursor.getWidth() + 7 ), getImage().getHeight() / 2 - textFontSize / 2, 2, textFontSize );
        }
        getImage().setColor( bgColor );
        getImage().fillRect( 0, 0, 3, getImage().getHeight() - 2 );
    }
    
    /**
     * Clears the image of the TextField so that it is empty again.
     */
    private void resetImage() {
        getImage().setColor( bgColor );
        getImage().fill();
        getImage().setColor( Color.BLACK );
        getImage().fillRect( 0, getImage().getHeight() - 2, getImage().getWidth(), 3 );
        getImage().fillRect( getImage().getWidth() - 2, 0, 3, getImage().getHeight() );
    }
    
    /**
     * Set the active TextField to the given number.
     * 
     * @param activeTextField
     *      The number of the TextField that is activated.
     */
    public void setActiveTextField( int activeTextField ) {
        this.activeTextField = activeTextField;
    }
    /**
     * Set the active TextField to the next textfield.
     */
    public void setActiveTextField() {
        activeTextField++;
        if( activeTextField > getWorld().getObjects( TextField.class ).size() ) {
            activeTextField = 1;
        }
    }
    
    /**
     * Check whether this textfield is enabled (if you can change the text in it).
     * 
     * @return
     *      Returns true if the text field is enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }
    /**
     * Enable or disenable the textfield.
     * 
     * @param enabled
     *      If you want the textfield to be enabled (able to write in it) this boolean has to be true.
     */
    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }
    
    /**
     * Returns the text of this textfield.
     * 
     * @return
     *      The text that is currently displayed in the textfield.
     */
    public String getText() {
        return text;
    }
    /**
     * Set the text of this textfield.
     * 
     * @param text
     *      The text that should be displayed in the textfield.
     */
    public void setText( String text ) {
        this.text = text;
        cursorPosition = text.length();
    }
    
    /**
     * Returns the backgroundcolor of this TextField.
     * 
     * @return
     *      The backgroundcolor of this TextField.
     */
    public Color getBackgroundColor() {
        return bgColor;
    }
    /**
     * Set the backgroundcolor to the given color.
     * 
     * @param bgColor
     *      The new backgroundcolor.
     */
    public void setBackgroundColor( Color bgColor ) {
        this.bgColor = bgColor;
    }
    
    /**
     * Returns the textcolor of this TextField.
     * 
     * @return
     *      The textcolor of this TextField.
     */
    public Color getTextColor() {
        return textColor;
    }
    /**
     * Set the textcolor to the given color.
     * 
     * @param textColor
     *      The new textcolor.
     */
    public void setTextColor( Color textColor ) {
        this.textColor = textColor;
    }
}