/**
 * The main class of the game; contains all main processes
 */

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Go extends Canvas implements Runnable {
    public static int WIDTH = 1920;
    public static int HEIGHT = 1080;
    private Thread thread;
    private boolean running = false;
    private static Handler handler;

    // When new game is constructed, it calls window class which
    // starts the game
    public Go() {
        handler = new Handler();
        new Window(WIDTH, HEIGHT, "PopNLock", this);

        handler.addObject(new Bar(WIDTH/2, HEIGHT/2 - 8, 480, 16));
        handler.addObject(new Target(WIDTH/2 - Target.getRadius(),
                HEIGHT/2 - 480 - Target.getRadius()));
    }

    // Starts the game
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    // Ends thread and closes game
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    // A system to keep track of frames passing during the game in
    // which a tick is counted and rendered
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    // Have the handler make everything do something during the tick
    private void tick() {
        handler.tick();
    }

    // // Have the handler render everything during the tick
    private void render() {
        BufferStrategy strat = this.getBufferStrategy();
        if (strat == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) strat.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        handler.render(g);
        g.dispose();
        strat.show();
    }

    public static Handler getHandler() {
        return handler;
    }

    public static void main(String[] args) {
        new Go();
    }
}
