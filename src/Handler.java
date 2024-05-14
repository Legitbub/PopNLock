/**
 * A handler class containing a linked list with every object in the game
 * All objects will be ticked from here to make them do stuff and get
 * rendered
 */

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    LinkedList<GameObject> everything = new LinkedList<GameObject>();

    public void tick() {
        for (int i = 0; i < everything.size(); i++) {
            GameObject current = everything.get(i);
            current.tick();
        }
    }
    public void render(Graphics g) {
        for (int i = 0; i < everything.size(); i++) {
            GameObject current = everything.get(i);
            current.render((Graphics2D) g);
        }
    }
    public void addObject(GameObject thing) {
        this.everything.add(thing);
    }
    public void removeObject(GameObject thing) {
        this.everything.remove(thing);
    }
}