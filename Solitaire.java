/* *****************************************************************************
 *  Name:    Kiran Biddinger
 *  NetID:   kb5465
 *  Precept: P02
 *
 *  Description:  Allows the user to play the game of Solitaire with a
 *                graphical user interface. The user will first see a start
 *                screen, where they can click anywhere to begin. The user will
 *                then see a screen with the game of solitaire, and will be able
 *                to select cards by clicking on a card, and then move that card
 *                by clicking on the spot where they would like to move that
 *                card. If the user wins the game, they will see a winner's
 *                screen and be able to replay the game by clicking anywhere.
 *
 *  Compilation:  javac-introcs Solitaire.java
 *  Execution: java-introcs Solitaire
 *
 *
 **************************************************************************** */

public class Solitaire {
    public static void main(String[] args) {
        // what screen is being played (0 = start, 1 = game, 2 = winners)
        int screen = 0;

        // initialize start, end screen
        StartScreen startScreen = new StartScreen();
        WinScreen winScreen = new WinScreen();

        // initialize deck
        Deck deck = new Deck();
        deck.initialize();
        deck.deal();

        // set up StdDraw
        StdDraw.setXscale(0, 1000);
        StdDraw.setYscale(0, 1000);
        StdDraw.enableDoubleBuffering();

        // loop parameters for advancing game
        boolean start;
        boolean won;
        boolean reset;

        // infinite loop
        while (true) {
            if (screen == 0) { // start screen
                startScreen.draw();
                start = startScreen.checkMouse();
                if (start) screen = 1; // if start, move to game screen
            }
            else if (screen == 1) { // game screen
                deck.draw();
                won = deck.checkMouse(); // allows playing game
                if (won) screen = 2; // if won, move to winners screen
            }
            else { // winners screen
                winScreen.draw();
                reset = winScreen.checkMouse();

                // reset game if click
                if (reset) {
                    screen = 1;
                    deck = new Deck();
                    deck.initialize();
                    deck.deal();
                }
            }
        }
    }
}
