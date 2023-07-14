import java.awt.*;

public class Tile {
    private Player owner;
    private Color color=Color.WHITE;
    private Player contestedOwner;
    private int x;
    private int y;
    Tile(int x,int y){
        this.x=x;
        this.y=y;
    }
    Color getColor(){
        if(owner!=null&&contestedOwner==null){
            return owner.getColor().darker();
        } else if (owner==null&&contestedOwner!=null) {
            return (new Color(contestedOwner.getColor().getRed(),contestedOwner.getColor().getGreen(),contestedOwner.getColor().getBlue(),100));

        } else if (owner!=null&&contestedOwner!=owner) {
            return MixColors();

        }else{
            return color;
        }
    }
    private Color MixColors(){
        float mixRed=((owner.getColor().getRed()/255f)*(contestedOwner.getColor().getRed()/255f));
        float mixGreen=((owner.getColor().getGreen()/255f)*(contestedOwner.getColor().getGreen()/255f));
        float mixBlue=((owner.getColor().getBlue()/255f)*(contestedOwner.getColor().getBlue()/255f));
        return (new Color(((mixRed+1)/2),((mixGreen+1)/2),((mixBlue+1)/2)));
    }


    public Player getContestedOwner() {
        return contestedOwner;
    }
    void setContestedOwner(Player contestedOwner){
        this.contestedOwner=contestedOwner;

    }
    Player getOwner(){
        return owner;
    }
    void setOwner(Player owner){
        if(this.owner!=null){
            this.owner.removeTileOwned(this);
        }
        this.owner=owner;
    }
    int getX(){
        return x;
    }
    int getY(){
        return y;
    }

}
