/**
 * A window class to open the game in a new tab; Starts the game from here
 */

import java.awt.*;
import javax.swing.JFrame;
public class Window extends Canvas {
    public Window(int width, int height, String title, Go go) {
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(go);

        frame.addKeyListener(new Bar.KeyPresses());
        go.start();
    }
}
