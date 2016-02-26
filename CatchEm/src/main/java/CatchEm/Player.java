/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CatchEm;

import static CatchEm.Game.height;
import static CatchEm.Game.width;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Johannes
 */
public class Player extends GameObject {

    Random random = new Random();
    Handler handler;
    HUD hud;

    public Player(int x, int y, ID id, Handler handler, HUD hud) {
        super(x, y, id);
        this.score = 0;
        this.handler = handler;
        this.hud = hud;
        this.stamina = 100;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        
        
        x = Game.clamp(x, 0, Game.width - 39);
        y = Game.clamp(y, 0, Game.height - 60);
        
        collision();
    }
    
    private void collision(){
        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.Target){
                if(getBounds().intersects(tempObject.getBounds())){
                    this.addScore();
                    handler.removeObject(tempObject);
                    handler.addObject(new Target(random.nextInt(width-39), random.nextInt(height-60), ID.Target));
                    hud.addTime();
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (getId() == ID.Player) {
            g.setColor(Color.blue);
            g.fillRect(x, y, 32, 32);
        }
        if (getId() == ID.Player2){
            g.setColor(Color.green);
            g.fillRect(x, y, 32, 32);
        }
    }
    

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

}