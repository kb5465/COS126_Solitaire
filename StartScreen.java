/* *****************************************************************************
 *  Name:    Kiran Biddinger
 *  NetID:   kb5465
 *  Precept: P02
 *
 *  Description:  Represents the start screen for the game of Solitaire.
 *                Introduces the user to the game, and allows them to start the
 *                game by clicking anywhere on the screen.
 *
 *  Compilation:  javac-introcs StartScreen.java
 *
 **************************************************************************** */

import java.awt.Font;

public class StartScreen {

    // empty constructor
    public StartScreen() {
    }

    public void draw() {
        // draw background
        StdDraw.setPenColor(0, 100, 0);
        StdDraw.filledRectangle(500, 500, 500, 500);

        // draw text
        StdDraw.setPenColor(StdDraw.RED);
        Font font = new Font("Arial", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(500, 650, "Solitaire");
        Font font2 = new Font("Sans Serif", Font.PLAIN, 20);
        StdDraw.setFont(font2);
        StdDraw.text(500, 400, "Click anywhere to start");


        StdDraw.show();
        StdDraw.pause(20);
    }

    // start game if mouse is clicked
    public boolean checkMouse() {
        boolean clicked = false;
        if (StdDraw.isMousePressed()) {
            clicked = true;
        }
        return clicked;
    }
}
