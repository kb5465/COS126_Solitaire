/* *****************************************************************************
 *  Name:    Kiran Biddinger
 *  NetID:   kb5465
 *  Precept: P02
 *
 *  Description:  Represents a card object for Solitaire, with a specific suit
 *                and number. Can be moved around and contains its own draw
 *                method.
 *
 *  Compilation:  javac-introcs Card.java
 *
 **************************************************************************** */

public class Card {
    private final int number; // number on card
    private final int suit; // suit of card
    private boolean stacked; // whether the card has been stacked
    private boolean flipped; // whether the card has been flipped
    private boolean inPlay; // whether the card can be moved
    private String picture; // string for picture
    private boolean selected; // is the card selected
    private int x; // x coord
    private int y; // y coord

    // constructor
    public Card(int number, int suit) {
        // initialize variables
        this.number = number;
        this.suit = suit;
        stacked = false;
        flipped = false;
        picture = "cardpictures/" + this.number + "-" + suit + ".png";
        selected = false;
    }

    // return number of card
    public int getNumber() {
        return number;
    }

    // return suit of card
    public int getSuit() {
        return suit;
    }

    // return color of card
    public int getColor() {
        int color = 0;
        if (suit > 1) color = 1; // color is 1 if red, 0 if black
        return color;
    }

    // return stacked status
    public boolean getStacked() {
        return stacked;
    }

    // return flipped status
    public boolean getFlipped() {
        return flipped;
    }

    // return inPlay status
    public boolean getInPlay() {
        return inPlay;
    }

    // return picture
    public String getPicture() {
        return picture;
    }

    // changed stacked status
    public void setStacked(boolean stack) {
        stacked = stack;
    }

    // changed stacked status
    public void setInPlay(boolean inPlay) {
        this.inPlay = inPlay;
    }

    // flip card
    public void flip() {
        flipped = true;
    }

    public void draw(int inputX, int inputY, int width, int height) {
        // parameters for draw
        String backpicture = "cardpictures/backcard.png";
        this.setCoords(inputX, inputY);

        // draw card if flipped
        if (flipped) {
            StdDraw.setPenColor(StdDraw.RED);
            if (selected) {
                // draw outline around card if selected by user
                StdDraw.filledRectangle(inputX, inputY, (double) width / 2 + 5,
                                        (double) height / 2 + 5);
            }
            StdDraw.picture(inputX, inputY, picture, width, height);
        }
        // draw back of card if not flipped
        else StdDraw.picture(inputX, inputY, backpicture, width, height);
    }

    // set coords of card
    private void setCoords(int inputX, int inputY) {
        x = inputX;
        y = inputY;
    }

    // get coords of card
    public int[] getCoords() {
        int[] coords = { x, y };
        return coords;
    }

    // get selected status
    public boolean getSelected() {
        return selected;
    }

    // set selected status
    public void setSelected(boolean inputSelected) {
        selected = inputSelected;
    }
}
