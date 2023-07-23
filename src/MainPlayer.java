import java.awt.*;
import java.awt.event.KeyEvent;

public class MainPlayer extends Player{

    private int id;
     void setId(int id){
        this.id=id;
    }
    MainPlayer(Color color,String nameOfPlayer) {
        super(color);
        this.nameOfPlayer=nameOfPlayer;

    }

    @Override
    void move() {
        x+=dX;
        y+=dY;

    }
    void lastDForKeyBoard(){
        if (id == KeyEvent.VK_UP && dY != 1) {
            dX=0;
            dY=-1;

        } if(id==KeyEvent.VK_DOWN&&dY!=-1) {
            dX=0;
            dY=1;

        }if(id==KeyEvent.VK_LEFT&&dX!=1){
            dY=0;
            dX=-1;
        }if(id==KeyEvent.VK_RIGHT&&dX!=-1){
            dX=1;
            dY=0;
        }
    }

}
