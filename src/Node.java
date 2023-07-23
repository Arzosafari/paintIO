import java.awt.*;

public class Node {
    private Player owner;
    private Player notCompleteOwner;

    private int x,y;
    int getX(){
        return x;
    }
    int getY(){
        return y;
    }
    Player getNotCompleteOwner(){
        return notCompleteOwner;
    }
    void setNotCompleteOwner(Player notCompleteOwner){
        this.notCompleteOwner=notCompleteOwner;
    }
    Player getOwner(){
        return owner;
    }
    void setOwner(Player owner){
        if(this.owner!=null){
            this.owner.removeDarkNodes(this);
        }
        this.owner=owner;
    }
    Color getColor(){
        if(owner!=null&&notCompleteOwner==null)
            return owner.getColor().darker();
        else if((owner==null&&notCompleteOwner!=null)||owner!=null&&notCompleteOwner!=owner){
            int colorR=notCompleteOwner.getColor().getRed();
            int colorG=notCompleteOwner.getColor().getGreen();
            int colorB=notCompleteOwner.getColor().getBlue();
            return new Color(colorR,colorG,colorB,150);

        }
        else {
            return Color.PINK;
        }
    }
}
