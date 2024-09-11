/* *****************************************************************************
 *  Name:    Kiran Biddinger
 *  NetID:   kb5465
 *  Precept: P02
 *
 *  Description:  Represents the stock for Solitaire. Allows for a user to
 *                iterate through the cards of the Stock, and remove them when
 *                allowed by the rules.
 *
 *  Compilation:  javac-introcs Stock.java
 *  Execution: java-introcs Stock suit number
 *
 *  % java-introcs Stock 1 12
 *  After adding 1 card:
 *  Suit: 1
 *  Card: 12
 *  After adding 2 cards:
 *  Suit 1: 1
 *  Card 1: 12
 *  Suit 2: 0
 *  Card 2: 13
 *
 **************************************************************************** */

import java.awt.Color;

public class Stock {
    private Node first; // first card
    private Node currentNode; // current card for iterating
    private Card[] displayCards; // the displayed cards of the stock
    private final int x; // x location of stock
    private final int y; // y location of stock
    private boolean end; // has the stock reached the end

    // constructor
    public Stock() {
        // initialize variables
        x = 935;
        y = 875;
        end = false;
        displayCards = new Card[3];
    }

    // get x location of stock
    public int getX() {
        return x;
    }

    // add node to list
    public void addCard(Card c) {
        Node newNode = new Node();
        newNode.c = c;

        // degenerate case for no cards
        if (first == null) {
            first = newNode;
            currentNode = first;
        }

        // otherwise
        else {
            // find the current last node
            Node node = first;
            while (node.next != null) {
                node = node.next;
            }

            // insert new node
            node.next = newNode;
        }
    }

    // is the stock empty
    public boolean isEmpty() {
        return (first == null);
    }

    // remove specific node
    public void removeCard(Card c) {
        if (c == first.c) {
            if (first.next != null) first = first.next;
            else first = null;
        }
        else {
            Node itNode = first;
            while (itNode.next != null) {
                if (itNode.next.c == c) {
                    if (itNode.next.next == null) {
                        itNode.next = null;
                        break;
                    }
                    else itNode.next = itNode.next.next;
                }
                itNode = itNode.next;
            }
        }
    }

    // draw the stock
    public void draw() {
        final int WIDTH = 50;
        final int HEIGHT = 75;
        String backpicture = "cardpictures/backcard.png";

        if (isEmpty()) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.rectangle(x, y, WIDTH, HEIGHT);
        }
        else {
            StdDraw.picture(x, y, backpicture, WIDTH * 2, HEIGHT * 2);
            int counter = 0;
            for (int i = 2; i >= 0; i--) {
                if (displayCards[i] != null) {
                    displayCards[i].draw(830 - 105 * counter, y, 100, 150);
                    counter++;
                }
            }
        }
    }

    public void click() {
        if (!isEmpty()) {
            if (end) {
                for (int i = 0; i < 3; i++) displayCards[i] = null;
                end = false;
                return;
            }
            // if there are no display cards, add new ones
            displayCards = new Card[3]; // reset
            for (int i = 0; i < 3; i++) {
                displayCards[i] = currentNode.c;
                if (currentNode.next != null) {
                    currentNode = currentNode.next;
                }
                else {
                    if (first != null) currentNode = first;
                    end = true;
                    break;
                }
            }
        }
    }

    // get x location of front card
    public int getCardX() {
        int counter = 0;
        int cardX = 0;
        for (int i = 2; i >= 0; i--) {
            if (displayCards[i] != null) {
                cardX = 830 - 105 * counter;
                counter++;
            }
        }
        return cardX;
    }

    // get top card of stock
    public Card getTopCard() {
        return displayCards[0];
    }

    // remove top card of stock
    public void popTopCard() {
        removeCard(displayCards[0]);
        Card[] copyCard = new Card[3];

        for (int i = 1; i < 3; i++) {
            if (displayCards[i] != null) {
                copyCard[i - 1] = displayCards[i];
            }
        }
        displayCards = copyCard;
    }

    // node class
    private class Node {
        private Card c; // card for node
        private Node next; // next node
    }

    // allows us to test the addCard/removeCard methods
    // args will input suit, number
    public static void main(String[] args) {
        // read in from command line
        int suit = Integer.parseInt(args[0]);
        int number = Integer.parseInt(args[1]);

        // check valid arguments
        if (suit < 0 || suit > 3) {
            throw new IllegalArgumentException("Invalid Suit");
        }
        if (number < 1 || number > 13) {
            throw new IllegalArgumentException("Invalid number");
        }

        // add new card to stock
        Card card = new Card(number, suit);
        Stock stock = new Stock();
        stock.addCard(card);

        // addCard TEST 1: print out top card
        // should print out the inputted card
        StdOut.println("After adding 1 card: ");
        StdOut.println("Suit: " + stock.first.c.getSuit());
        StdOut.println("Card: " + stock.first.c.getNumber());

        // addCard TEST 2: add a second card, check preserved order
        // should print the inputted card as card 1, and the king of clubs
        // (suit 0, card 13) as card 2
        Card secondCard = new Card(13, 0);
        stock.addCard(secondCard);
        StdOut.println("After adding 2 cards: ");
        StdOut.println("Suit 1: " + stock.first.c.getSuit());
        StdOut.println("Card 1: " + stock.first.c.getNumber());
        StdOut.println("Suit 2: " + stock.first.next.c.getSuit());
        StdOut.println("Card 2: " + stock.first.next.c.getNumber());
    }
}
