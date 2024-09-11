/* *****************************************************************************
 *  Name:    Kiran Biddinger
 *  NetID:   kb5465
 *  Precept: P02
 *
 *  Description:  Represents the win screen for the game of Solitaire. Displays
 *                a title for "WINNER!" and allows the user to replay the game
 *                after winning.
 *
 *  Compilation:  javac-introcs WinScreen.java
 *
 **************************************************************************** */

import java.awt.Font;

public class WinScreen {

    // empty constructor
    public WinScreen() {
    }

    public void draw() {
        // draw background
        StdDraw.setPenColor(0, 100, 0);
        StdDraw.filledRectangle(500, 500, 500, 500);

        // draw text
        StdDraw.setPenColor(StdDraw.RED);
        Font font = new Font("Arial", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(500, 650, "WINNER!");
        Font font2 = new Font("Sans Serif", Font.PLAIN, 20);
        StdDraw.setFont(font2);
        StdDraw.text(500, 400, "Click anywhere to replay");


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
