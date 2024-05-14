/**
 * A target class to represent the yellow circles that you have
 * to press the button to land on at the right time
 *
 */

import java.awt.*;

public class Target extends GameObject {
    private static final int RADIUS = 50;
    private Overlay border;
    public Target(int x, int y) {
        super(x, y);
        border = new Overlay(x, y, 2 * RADIUS, 2 * RADIUS);
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.green);
        g.drawOval(x, y, 2 * RADIUS, 2 * RADIUS);
        g.fillOval(x, y, 2 * RADIUS, 2 * RADIUS);
    }

    public Overlay getBorder() {
        return border;
    }

    public static int getRadius() {
        return RADIUS;
    }
}
