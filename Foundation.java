/* *****************************************************************************
 *  Name:    Kiran Biddinger
 *  NetID:   kb5465
 *  Precept: P02
 *
 *  Description:  Represents a foundation for the game of Solitaire, with a
 *                specific suit and top card. This is where cards get placed
 *                after being removed from a stack or from the stock, and the
 *                game is won when all four foundations are full (King on top).
 *
 *  Compilation:  javac-introcs Foundation.java
 *
 **************************************************************************** */

import java.awt.Color;
import java.awt.Font;

public class Foundation {
    private int numCards; // number of cards in foundation
    private Card topCard; // top card of the foundation
    private int suit; // suit of foundation
    private String suitName; // name of suit
    private int x; // x location of foundation
    private int y; // y location of foundation

    // constructor
    public Foundation(int suit) {
        // initialize suit
        this.suit = suit;
        if (suit == 0) suitName = "Clubs";
        else if (suit == 1) suitName = "Spades";
        else if (suit == 2) suitName = "Hearts";
        else suitName = "Diamonds";

        // set coords
        y = 875;
        x = 65 + 140 * suit;

        numCards = 0; // initialize starting number of cards to 0
    }

    // is the foundation empty
    public boolean isEmpty() {
        return (numCards == 0);
    }

    // draw the foundation
    public void draw() {
        final int WIDTH = 50;
        final int HEIGHT = 75;

        if (isEmpty()) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.rectangle(x, y, WIDTH, HEIGHT); // outline the foundation
        }
        else {
            topCard.draw(x, y, WIDTH * 2, HEIGHT * 2); // draw the top card
        }
        Font font = new Font("Sans Serif", Font.PLAIN, 16);
        StdDraw.setFont(font);
        StdDraw.text(x, y - 100, suitName); // label the suit below card
    }

    // stack a card on top of the foundation
    public void stackCard(Card card) {
        topCard = card;
        numCards += 1;
    }

    // get suit of foundation
    public int getSuit() {
        return suit;
    }

    // get number of cards in foundation
    public int getNumCards() {
        return numCards;
    }

    // get x location of foundation
    public int getLocation() {
        return x;
    }
}
