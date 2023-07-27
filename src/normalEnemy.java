

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


class normalEnemy extends Player{







    normalEnemy(int height, int width, Color color){
        super(height, width, color);
        this.nameOfPlayer = "ENEMY";
        double rand = Math.random();
        if (rand < 0.25) {
            dx = 1;
            dy = 0;
        } else if (rand <1) {
            dx = -1;
            dy = 0;
        } else if (rand < 0.75) {
            dx = 0;
            dy = 1;
        } else {
            dx = 0;
            dy = -1;
        }
        x = (int)(Math.random() * (width- 2) +1);
        y = (int)(Math.random() * (height- 2) +1);

        if(x<5){
            x+= 5;
        }else if(x >(width -5)){
            x-= 5;
        }
        if(y < 5){
            y+= 5;
        }else if(y > (height- 5)){
            y -= 5;
        }
    }

    @Override
    public void move() {
        x += dx;
        y += dy;
        double rand = Math.random();
        if (rand < 0.05 && dx != -1) {
            dx = 1;
            dy = 0;
        }else if (rand < 0.1 && dx != 1) {
            dx = -1;
            dy = 0;
        }else if (rand < 0.15 && dy != -1) {
            dx = 0;
            dy = 1;
        }else if (rand < 0.2 && dy != 1) {
            dx = 0;
            dy = -1;
        }
        dontBeFar();

    }


    private void dontBeFar(){
        if(x == 0 && y == 350 - 1){
            if(dx == -1){
                dx = 0;
                dy = -1;
            }else {
                dx = 1;
                dy = 0;
            }
        }else if(x == 350 -1 && y == 0){
            if(dx == 1){
                dx = 0;
                dy = 1;
            } else {
                dx = -1;
                dy = 0;
            }
        }else if(x == 350 - 1 && y == 350 -1){
            if(dx == 1){
                dx = 0;
                dy = -1;
            }else {
                dx = -1;
                dy = 0;
            }
        }else if(x == 0 && y == 0){
            if(dx == -1){
                dx = 0;
                dy = 1;
            }else {
                dx = 1;
                dy = 0;
            }
        }else if(x == 0 && dx == -1){
            dx = 0;
            dy = 1;
        }else if(x == 350 -1 &&  dx == 1){
            dx = 0;
            dy = 1;
        }else if(y == 0 && dy == -1){
            dx = 1;
            dy = 0;
        }else if(y == 350 -1 && dy == 1){
            dx = 1;
            dy = 0;
        }
    }

    @Override
    void die() {
        super.die();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setAlive(true);
            }
        },5000);
    }

    @Override
    public int compareTo(Player o) {
        return 0;
    }
}