/* *****************************************************************************
 *  Name:    Kiran Biddinger
 *  NetID:   kb5465
 *  Precept: P02
 *
 *  Description:  Represents one pile/stack of the Tableau in Solitaire.
 *                Implements a linked list of cards contained in the pile,
 *                which can be added to or taken from throughout the game.
 *
 *  Compilation:  javac-introcs Pile.java
 *
 **************************************************************************** */

public class Pile {
    private Node first; // first card
    private int location; // x location of stack

    // constructor
    public Pile(int location) {
        this.location = 120 + (125 * location);
    }

    // draw the stack
    public void draw() {
        int y = 600; // starting y position

        int width = 100; // card width
        int height = 150; // card height

        // decrease size per card if stack size gets too large
        if (size() > 10) {
            height = 100;
            y = 625;
        }
        else if (size() > 12) {
            height = 50;
            y = 650;
        }

        // draw each node
        if (first != null) {
            Node node = first;
            node.c.draw(location, y, width, height);
            while (node.next != null) {
                node = node.next;
                y -= 50;
                node.c.draw(location, y, width, height);
            }
        }
    }

    // flip the last card of the stack
    public void flipLastCard() {
        if (first != null) {
            Node node = first;
            while (node.next != null) {
                node = node.next; // iterate to find last card
            }
            node.c.flip(); // flip last card
        }
    }

    // get number of last card of the stack
    public int getLastNumber() {
        if (first != null) {
            Node node = first;
            while (node.next != null) {
                node = node.next; // iterate to find last card
            }
            return node.c.getNumber(); // return number of that card
        }
        return 0; // return 0 if stack is empty
    }

    // get color of last card of stack
    public int getLastColor() {
        if (first != null) {
            Node node = first;
            while (node.next != null) {
                node = node.next; // iterate to find last card
            }
            int suit = node.c.getSuit();
            int color = 0;
            if (suit > 1) color = 1;
            return color; // color is 1 if red, 0 if black
        }
        return -1;
    }

    // get x location of stack
    public int getLocation() {
        return location;
    }

    // is the stack empty
    public boolean isEmpty() {
        return (first == null);
    }

    // add card to list
    public void addCard(Card c) {
        Node newNode = new Node();
        newNode.c = c;

        // degenerate case for no cards
        if (first == null) {
            first = newNode;
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

    // return the card in a stack
    public Card[] cards() {
        // initialize variables
        Card[] returnCards = new Card[size()];
        Node current = first;
        int counter = 0;
        returnCards[counter] = current.c;

        while (current.next != null) {
            counter++; // add to counter
            current = current.next; // traverse list
            returnCards[counter] = current.c;
        }

        return returnCards;
    }

    // get number of cards in stack
    public int size() {
        if (first == null) // special case
            return 0;

        Node current = first;
        int counter = 1;
        while (current.next != null) {
            current = current.next; // traverse list
            counter++; // add to counter
        }

        return counter;
    }

    // remove last node
    public void removeCard() {
        // special cases
        if (first == null) {
            throw new IllegalArgumentException("Empty stack");
        }
        else if (first.next == null) {
            first = null;
            return;
        }
        else if (first.next.next == null) {
            first.next = null;
            return;
        }

        Node node = first;
        do {
            node = node.next; // find second to last node
        } while (node.next.next != null);

        node.next = null; // remove last node
    }

    // node class
    private class Node {
        private Card c; // card for node
        private Node next; // next node
    }
}
