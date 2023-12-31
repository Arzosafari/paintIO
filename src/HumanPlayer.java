
import java.awt.*;
import java.awt.event.KeyEvent;


public class HumanPlayer extends Player {

    private int nextKey;
    void setNextKey(int nextKey) {
        this.nextKey = nextKey;
    }


    @Override
    public void move(){
        x += dx;
        y += dy;
    }
    HumanPlayer(int height, int width, Color color, String nameOfPlayer) {
        super(height, width, color);
        this.nameOfPlayer = nameOfPlayer;

    }


    void updateDirection() {
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

    @Override
    public int compareTo(Player o) {
        return 0;
    }
}
