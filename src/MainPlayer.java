
import java.awt.*;
import java.awt.event.KeyEvent;


public class MainPlayer extends Player {

    private int direction;
    void setDirection(int direction) {
        this.direction = direction;
    }


    @Override
    public void move(){
        lastX = x;
        lastY = y;
        x += dx;
        y += dy;
    }
    MainPlayer(int height, int width, Color color, String nameOfPlayer) {
        super(height, width, color);
        this.nameOfPlayer = nameOfPlayer;
        setPosition( width/2,height/2);

    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    void updateDirection() {
        if(direction == KeyEvent.VK_LEFT && dx != 1) {
            dx = -1;
            dy = 0;
        }

        if(direction == KeyEvent.VK_RIGHT && dx != -1) {
            dx = 1;
            dy = 0;
        }


        if(direction == KeyEvent.VK_UP  && dy != 1) {
            dx = 0;
            dy = -1;
        }


        if(direction == KeyEvent.VK_DOWN && dy != -1) {
            dx = 0;
            dy = 1;
        }
    }


}
