import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SmartEnemy extends Player {

    private MainPlayer mainPlayer;

    SmartEnemy(int height, int width, Color color, MainPlayer mainPlayer) {
        super(height, width, color);
        this.nameOfPlayer = "ENEMY";
        this.mainPlayer = mainPlayer;
        randomizeDirection();
        randomizePosition();
    }

    @Override
    public void move() {
        lastX = x;
        lastY = y;
        int dx = 0;
        int dy = 0;

        if (mainPlayer.getX() < x) {
            dx = -1;
        } else if (mainPlayer.getX() > x) {
            dx = 1;
        }

        if (mainPlayer.getY() < y) {
            dy = -1;
        } else if (mainPlayer.getY() > y) {
            dy = 1;
        }

        x += dx;
        y += dy;

       // checkCollision();
       // checkAttack(currentNode);
    }

    private void randomizeDirection() {
        double rand = Math.random();
        if (rand < 0.25) {
            dx = 1;
            dy = 0;
        } else if (rand < 0.5) {
            dx = -1;
            dy = 0;
        } else if (rand < 0.75) {
            dx = 0;
            dy = 1;
        } else {
            dx = 0;
            dy = -1;
        }
    }

    private void randomizePosition() {
        x = (int) (Math.random() * (width - 2) + 1);
        y = (int) (Math.random() * (height - 2) + 1);

        if (x < 5) {
            x += 5;
        } else if (x > (width - 5)) {
            x -= 5;
        }

        if (y < 5) {
            y += 5;
        } else if (y > (height - 5)) {
            y -= 5;
        }
    }

    private void checkCollision() {
        if (currentNode != null && currentNode.getContestedOwner() != null && currentNode.getContestedOwner() != this) {
            currentNode.getContestedOwner().die();
        }
    }

    @Override
    void die() {
        super.die();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                randomizeDirection();
                randomizePosition();
                setAlive(true);
            }
        }, 5000);
    }


}