/**
 * The class to represent the spinning bar that touches the target
 * Bar will spin and increase in speed with successful hits (button presses)
 */

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Bar extends GameObject {
    private int length;
    private int width;
    private static double spin = -1;
    private double degrees = 0;
    private int hits = 0;

    public Bar(int x, int y, int length, int width) {
        super(x, y);
        this.length = length;
        this.width = width;
    }

    // Keep degree measurements to within 360 degrees
    @Override
    public void tick() {
        degrees -= spin;
        if (degrees >= 360) {
            degrees = 0;
        } else if (degrees < 0) {
            degrees += 360;
        }
    }

    // The bar is rotated at a certain speed based on degrees
    @Override
    public void render(Graphics2D g) {
        Graphics2D gBar = (Graphics2D) g.create();
        gBar.setColor(Color.BLUE);
        gBar.rotate(Math.toRadians(-degrees),x, y + 8);
        gBar.fillRect(x, y, length, width);
        gBar.dispose();
    }

    // Utility rotation matrix functions
    public static int rotationX(int xPos, int yPos, double radians) {
        return (int) ((xPos * Math.cos(radians)) -
                (yPos * Math.sin(radians)));
    }

    public static int rotationY(int xPos, int yPos, double radians) {
        return (int) ((xPos * Math.sin(radians)) +
                (yPos * Math.cos(radians)));
    }

    // Check if the bar is touching the target using an overlay
    // (border rectangle) to represent the zone of contact
    public boolean overlaps(Target t, long degrees) {
        double rad = (Math.toRadians(degrees));
        int rotatedX = rotationX(length - x, 0, rad) + x;
        int rotatedY = rotationY(length - x, 0, rad) + Go.HEIGHT/2;
        if (hits > 0) {
            rotatedX = Go.WIDTH - rotatedX;
        }

        Overlay barBorder = new Overlay(rotatedX - 12, rotatedY - 12, 24, 24);

        return barBorder.intersects(t.getBorder());
    }

    // When spacebar is pressed, check if bar is touching target;
    // then if it is, adjust bar speed based on number of hits and
    // randomly place a new target a set distance away from the
    // current point
    public static class KeyPresses extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                Handler objects = Go.getHandler();
                Bar bar = (Bar) objects.everything.getFirst();
                long d = Math.round(bar.degrees);
                Target circle = (Target) objects.everything.getLast();
                if (bar.overlaps(circle, d)) {
                    bar.hits++;
                    if (bar.hits % 5 == 0 && bar.hits <= 50) {
                        if (spin < 0) {
                            spin -= 1;
                        } else {
                            spin += 1;
                        }
                    }
                    spin *= -1;

                    double r = Math.toRadians((int) (Math.random() * 261) + 50);
                    int localX = circle.x - Go.WIDTH / 2 + Target.getRadius();
                    int localY = circle.y - Go.HEIGHT / 2 + Target.getRadius();
                    int rotatedX = rotationX(localX, localY, r) + Go.WIDTH/2;
                    int rotatedY = rotationY(localX, localY, r) + Go.HEIGHT/2;

                    objects.removeObject(circle);
                    objects.addObject(new Target(rotatedX -
                            Target.getRadius(), rotatedY -
                            Target.getRadius()));
                }
            }
        }
    }
}
