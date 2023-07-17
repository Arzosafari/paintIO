import java.awt.*;
import java.awt.event.KeyEvent;

public class HumanPlayer extends Player{
    private int nextKey;
    public HumanPlayer(int height, int width, Color color,String name) {
        super(height, width, color);
        this.name=name;
    }
    void setNextKey(int nextKey){
        this.nextKey=nextKey;
    }

    @Override
    void move() {
        x+=dx;
        y+=dy;

    }
    void updateD() {
        //Left
        if((nextKey == KeyEvent.VK_LEFT || nextKey == KeyEvent.VK_A) && dx != 1) {
            dx = -1;
            dy = 0;
        }

        //Right
        if((nextKey == KeyEvent.VK_RIGHT || nextKey == KeyEvent.VK_D) && dx != -1) {
            dx = 1;
            dy = 0;
        }

        //Up
        if((nextKey == KeyEvent.VK_UP || nextKey == KeyEvent.VK_W) && dy != 1) {
            dx = 0;
            dy = -1;
        }

        //Down
        if((nextKey == KeyEvent.VK_DOWN || nextKey == KeyEvent.VK_S) && dy != -1) {
            dx = 0;
            dy = 1;
        }
    }
}
