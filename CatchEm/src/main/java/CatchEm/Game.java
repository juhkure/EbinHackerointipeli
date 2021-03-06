/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CatchEm;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

/**
 *
 * @author Johannes
 */
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 4552703084559473965L;

    /**
     *  Ikkunan leveys, jota käytetään useammassa luokassa
     */
    public static final int Width = 640;

    /**
     *  Ikkunan korkeus, jota käytetään useammassa luokassa
     */
    public static final int Height = Width / 12 * 9;

    private Thread thread;
    private boolean running = false;
    private final Handler handler;
    private final HUD hud;
    private Spawner ts;

    private Random random;
    
    /**
     *  Luodaan peli, jossa alustetaan muuttujia
     */
    public Game() {
        handler = new Handler();

        hud = new HUD(handler);
        ts = new Spawner(handler, hud);

        this.addKeyListener(new KeyInput(handler, hud, this));

        Window window = new Window(Width, Height, "CatchEm", this);

        random = new Random();
    }

    /**
     *  Käynnistetään peli ja threadi
     */
    public synchronized void start() {

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     *  Pysäytetään thread
     */
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {

        }
    }
    
    /**
     *  Pelin looppi. Täältä viitataan tick() metodiin.
     */

    public final void run() {
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                this.render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    
    /**
     * Tick metodi johon viitataan loopista aina kerran yhden peliloopin aikana. Tick metodista 
     * viitataan toisten luokkin tick -metodeihin.
     */

    private void tick() {
        handler.tick();
        hud.tick();
        ts.tick();
    }
    
    /**
     * Pelin graafiset elementit renderoidaan ikkunalle.
     */

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
        }

        if (bs != null) {
            Graphics g = bs.getDrawGraphics();

            g.setColor(Color.black);
            g.fillRect(0, 0, Width, Height);

            handler.render(g);

            hud.render(g);

            g.dispose();

            bs.show();
        }
    }

    /**
     * Clamp metodi on yleisesti käytetty eri luokissa, jonka avulla voidaan esimerkiksi rajoittaa
     * peliobjektin kulkemista ikkunan ulkopuolelle.
     *
     * @param variable Nykyinen arvo
     * @param min Pienin mahdollinen arvo
     * @param max Suurin mahdollinen arvo
     * @return Uusi arvo, mahdollisesti muokattuna
     */
    public static int clamp(int variable, int min, int max) {
        if (variable >= max) {
            return variable = max;
        } else if (variable <= min) {
            return variable = min;
        } else {
            return variable;
        }
    }

}
