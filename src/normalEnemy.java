import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class normalEnemy extends Player{
    private String[] nameOfEnemy={"Emma","Olivia", "Ava", "Sophia", "Isabella", "Mia","Charlotte", "Amelia", "Harper", "Evelyn", "Abigail", "Emily","Elizabeth", "Mila", "Ella", "Avery", "Scarlett", "Sofia", "Chloe"};
    normalEnemy(Color color) {
        super(color);
        int rand=new Random().nextInt(nameOfEnemy.length);
        this.nameOfPlayer=nameOfEnemy[rand];
    }

    @Override
    void move() {
        x+=dX;
        y+=dY;
        randomDirection();
        notTooFar();


    }
    private void randomDirection(){
        double rand=Math.random();
        if(rand<0.1&&dY!=-1){
            dY=1;
            dX=0;
        } else if (rand<0.2&&dY!=1) {
            dY=-1;
            dX=0;

        } else if (rand<0.3&&dX!=-1) {
            dY=0;
            dX=1;

        } else if (rand<0.4&&dX!=1) {
            dY=0;
            dX=-1;

        }
    }
    private void notTooFar(){
        if(x==0&&y==0){
            if(dX==-1){
                dY=1;
                dX=0;
            }else{
                dX=1;
                dY=0;
            }
        } else if (x==499&&y==0) {
            if(dX==1){
                dY=1;
                dX=0;
            }else{
                dX=-1;
                dY=0;
            }

        } else if (x==0&&y==499) {
            if(dX==-1){
                dY=-1;
                dX=0;
            } else  {
                dX=1;
                dY=0;

            }
        } else if (x==499&&y==499) {
            if(dX==1){
                dX=0;
                dY=-1;
            }else{
                dX=-1;
                dY=0;
            }

        } else if (x==499&&dX==1) {
            dX=0;
            dY=1;

        } else if (x==0&&dX==-1) {
            dX=0;
            dY=1;

        }else if(y==0&&dY==-1){
            dY=0;
            dX=1;
        } else if (y==499&&dY==1) {
            dX=1;
            dY=0;

        }
    }
    @Override
    void killed(){
        super.killed();
        makeAlive();
    }
    private void makeAlive(){
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                setDead(false);
            }
        };
        timer.schedule(timerTask,4000);
}
}
