/* *****************************************************************************
 *  Name:    Kiran Biddinger
 *  NetID:   kb5465
 *  Precept: P02
 *
 *  Description:  Deck class for the game of Solitaire. Implements most of the
 *                gameplay -- allows for shuffling and dealing of the deck,
 *                and then allows the user to move cards around and play the
 *                game of solitaire.
 *
 *  Compilation:  javac-introcs Deck.java
 *  Execution: java-introcs Deck x1 y1 x2 y2
 *             This should print the shuffled deck of cards, allowing
 *             verification that the cards are randomly shuffled. Will then
 *             print the tally of number of individual cards in each suit,
 *             which should be 13 for each.
 *             Using (x1, y1) and (x2, y2), the program will then print whether
 *             both pairs of coordinates are near given piles. Pair 1 should
 *             be within the frame (0 - 1000, 0 - 1000), and if near a pile
 *             (as described in the main) should yield a number 0 - 6 for a
 *             pile. The out of frame coordinates should yield -1.
 *
 **************************************************************************** */

public class Deck {
    private Pile[] piles; // piles for the tableau
    private Card[] cards; // array for playing cards
    private Stock stock; // the stock of cards for the game
    private boolean movingCard; // are cards being moved?
    private Card[] highlightedCard; // cards involved in move
    private Foundation[] foundations; // foundations for each suit
    private int movedFrom; // where a card moved from
    private int suitsCompleted; // number of suits fully removed from board

    // constructor
    public Deck() {
        // initialize piles
        piles = new Pile[7];
        for (int i = 0; i < piles.length; i++) piles[i] = new Pile(i);

        // initialize cards and stock
        cards = new Card[52];
        stock = new Stock();

        // initialize foundations
        foundations = new Foundation[4];
        for (int i = 0; i < foundations.length; i++) {
            foundations[i] = new Foundation(i);
        }
        suitsCompleted = 0;
    }

    // initialize cards
    public void initialize() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                // create new card
                cards[i + 13 * j] = new Card(i + 1, j);
            }
        }

        this.shuffle(); // shuffle deck
    }

    // shuffle the deck
    public void shuffle() {
        int n = cards.length;
        // randomly exchange cards to shuffle
        for (int i = 0; i < n; i++) {
            int r = i + (int) (StdRandom.uniform() * (n - i));
            Card temp = cards[i];
            cards[i] = cards[r];
            cards[r] = temp;
        }
    }

    // deal the cards
    public void deal() {
        int counter = 0; // counter for top card

        // fill the tableau piles
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < i + 1; j++) {
                piles[i].addCard(cards[counter]);
                counter++;
            }
            piles[i].flipLastCard();
        }

        // fill stock with remaining cards
        for (int i = 0; i < 24; i++) {
            stock.addCard(cards[counter]);
            cards[counter].flip();
            counter++;
        }
    }

    // draw the screen
    public void draw() {
        // draw background
        StdDraw.setPenColor(0, 100, 0);
        StdDraw.filledRectangle(500, 500, 500, 500);

        for (int i = 0; i < piles.length; i++) {
            piles[i].draw(); // draw piles of cards
        }

        for (int i = 0; i < foundations.length; i++) {
            foundations[i].draw(); // draw foundations
        }

        stock.draw(); // draw stock

        StdDraw.show();
        StdDraw.pause(20);
    }

    // if mouse has been pressed, allow for moves
    public boolean checkMouse() {
        boolean won = false; // boolean for has the game been won

        if (StdDraw.isMousePressed()) {
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();

            // if iterating through stock
            if (nearStock(mouseX, mouseY) == 0) {
                stock.click();
            }

            // if a card is being moved
            if (movingCard) {
                if (nearPile(mouseX, mouseY) >= 0) {
                    movePile(mouseX, mouseY); // move it to a pile
                }
                else if (nearFoundation(mouseX, mouseY) >= 0) {
                    moveFoundation(mouseX, mouseY);
                }
                else {
                    movingCard = false;
                    highlightedCard[0].setSelected(false);
                }
            }
            // if trying to select a card
            else {
                if (nearPile(mouseX, mouseY) >= 0) {
                    cardFromPile(mouseX, mouseY, nearPile(mouseX, mouseY));
                }
                else if (nearStock(mouseX, mouseY) == 1) {
                    cardFromStock();
                }
            }
            StdDraw.pause(500); // pause to allow mouse release
        }
        if (suitsCompleted == 4) won = true; // win if all 4 suits are complete
        return won;
    }

    // move a card to a pile
    private void movePile(double x, double y) {
        // get pile that user is near and last number of that pile
        int pileNumber = nearPile(x, y);
        int bottomNumber = piles[pileNumber].getLastNumber();

        // if moving to a non-empty pile
        if (bottomNumber > 0) {
            int color = piles[pileNumber].getLastColor();
            if (color != highlightedCard[0].getColor() &&
                    bottomNumber - 1 == highlightedCard[0].getNumber()) {
                // if moved from stock, add from stock
                if (movedFrom > 6) {
                    stock.popTopCard();
                    piles[pileNumber].addCard(highlightedCard[0]);
                }
                // if moved from pile, add from that pile
                else {
                    for (int i = 0; i < highlightedCard.length; i++) {
                        piles[movedFrom].removeCard();
                        piles[pileNumber].addCard(highlightedCard[i]);
                    }
                    piles[movedFrom].flipLastCard();
                }
            }
        }
        // if moving king to empty pile
        else if (bottomNumber == 0) {
            if (highlightedCard[0].getNumber() == 13) {
                // if moved from stock, add from stock
                if (movedFrom > 6) {
                    stock.popTopCard();
                    piles[pileNumber].addCard(highlightedCard[0]);
                }
                // if moved from pile, add from that pile
                else {
                    for (int i = 0; i < highlightedCard.length; i++) {
                        piles[movedFrom].removeCard();
                        piles[pileNumber].addCard(highlightedCard[i]);
                    }
                    piles[movedFrom].flipLastCard();
                }
            }
        }

        // deselect card
        movingCard = false;
        highlightedCard[0].setSelected(false);
    }

    // move card to a foundation
    private void moveFoundation(double x, double y) {
        int foundNumber = nearFoundation(x, y);

        // if moving one card and card is compatible, move card
        if (highlightedCard.length == 1) {
            if (highlightedCard[0].getSuit() == foundNumber &&
                    highlightedCard[0].getNumber() - 1 ==
                            foundations[foundNumber].getNumCards()) {
                // if moving from stock, pop from stock
                if (movedFrom > 6) {
                    stock.popTopCard();
                }
                // else remove from pile
                else {
                    piles[movedFrom].removeCard();
                    piles[movedFrom].flipLastCard();
                }
                // stack card
                foundations[foundNumber].stackCard(highlightedCard[0]);

                // iterate num suits completed if king is stacked
                if (highlightedCard[0].getNumber() == 13) suitsCompleted++;
            }
        }

        // deselect card
        movingCard = false;
        highlightedCard[0].setSelected(false);
    }

    // nearest pile – return -1 if not near a pile
    private int nearPile(double x, double y) {
        if (y < 700 && y > 10) {
            for (int i = 0; i < piles.length; i++) {
                if (Math.abs(piles[i].getLocation() - x) < 30) {
                    return i; // return number of pile if near that pile
                }
            }
        }
        return -1; // return -1 if not near pile
    }

    // nearest foundation
    private int nearFoundation(double x, double y) {
        if (y < 950 && y > 800) {
            for (int i = 0; i < foundations.length; i++) {
                if (Math.abs(foundations[i].getLocation() - x) < 30) {
                    return i; // return suit number if near that foundation
                }
            }
        }
        return -1; // return -1 if not near the foundation
    }

    // returns -1 if not near stock, 0 if iterating through cards, 1 if
    // selecting a card
    private int nearStock(double x, double y) {
        // if near stock, return 0 if iterating or 1 if selecting top card
        if (y < 950 && y > 800) {
            if (Math.abs(x - stock.getX()) < 30) return 0;
            else if (Math.abs(x - stock.getCardX()) < 30) return 1;
        }
        return -1; // return -1 if not near stock
    }

    // select a card from a pile
    private void cardFromPile(double x, double y, int pile) {
        // get all cards from pile
        Card[] inputCards = piles[pile].cards();

        // iterate through all cards in pile
        for (int i = 0; i < inputCards.length; i++) {
            if (inputCards[i].getFlipped()) {
                double dist = distance(x, y, inputCards[i].getCoords()[0],
                                       inputCards[i].getCoords()[1]);

                // if mouse is near card i, select that card
                if (dist < 30) {
                    // selecting card parameters
                    inputCards[i].setSelected(true);
                    movingCard = true;
                    int length = inputCards.length - i;
                    highlightedCard = new Card[length];
                    movedFrom = pile;

                    // select cards below a given card if they exist
                    for (int j = 0; j < length; j++) {
                        highlightedCard[j] = inputCards[j + i];
                    }
                    break;
                }
            }
        }
    }

    // get a card from the stock
    private void cardFromStock() {
        movingCard = true;
        highlightedCard = new Card[1];
        highlightedCard[0] = stock.getTopCard();
        highlightedCard[0].setSelected(true);
        movedFrom = 7;
    }

    // calculate distance between two points
    private double distance(double x1, double y1, double x2, double y2) {
        double dist = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        return dist;
    }

    // allows test of (1) shuffling of the cards
    // also tests (2) the ability to assess whether the mouse is near a pile
    public static void main(String[] args) {
        // tests the shuffling
        Deck deck = new Deck();
        deck.initialize();

        // initialize TEST 1: print order of cards in deck after shuffling
        // should print a random order of cards
        int[] suits = new int[4];
        StdOut.println("Individual Cards: ");
        for (int i = 0; i < deck.cards.length; i++) {
            StdOut.println("Suit: " + deck.cards[i].getSuit() + "; Number: " +
                                   deck.cards[i].getNumber());
            suits[deck.cards[i].getSuit()]++;
        }

        // initialize TEST 2: print number of cards in each suit
        // should print 13 for each
        StdOut.println("# cards in ecach suit: ");
        for (int i = 0; i < suits.length; i++) {
            StdOut.println("Suit: " + i + ", Number of cards: " + suits[i]);
        }

        // tests the ability to check whether card is near a pile
        // args 0, 2 = x of mouse, args 1, 3 = y of mouse
        // first x, y pair should be in frame, second out of frame
        // coords are 1000 x 1000)
        double xCoord1 = Double.parseDouble(args[0]);
        double yCoord1 = Double.parseDouble(args[1]);
        double xCoord2 = Double.parseDouble(args[2]);
        double yCoord2 = Double.parseDouble(args[3]);

        // check valid arguments for each test
        if (xCoord1 < 0 || xCoord1 > 1000) {
            throw new IllegalArgumentException("Invalid xCoord1");
        }
        if (xCoord2 < 1000 && xCoord2 > 0) {
            throw new IllegalArgumentException("Invalid xCoord2");
        }
        if (yCoord1 < 0 || yCoord1 > 1000) {
            throw new IllegalArgumentException("Invalid yCoord1");
        }
        if (yCoord2 < 1000 && yCoord2 > 0) {
            throw new IllegalArgumentException("Invalid yCoord2");
        }

        // set up playing field
        deck.deal();

        // nearPile TEST 1: see if inputted x,y (1) is near a pile
        // if within given y bounds y < 700 && y > 10 and x is near a pile x –
        // where x is within 30 of (120 + (125 * n)) and n is a number 0-6 –
        // should return various numbers from 0 to 6 (n)
        StdOut.println("NearPile 1 : " + deck.nearPile(xCoord1, yCoord1));

        // nearPile TEST 2: see if inputted x,y (2) is near a pile
        // if outside of bounds, should return -1
        StdOut.println("NearPile 2 : " + deck.nearPile(xCoord2, yCoord2));
    }
}
