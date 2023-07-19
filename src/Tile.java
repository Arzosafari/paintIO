
import java.awt.*;

class Tile {

    private Player completeOwner;
    private Player contestedOwner;
    private Color color = Color.BLACK;

    private int x,y;
    Tile(int x, int y){
        this.x = x;
        this.y = y;
    }
    int getX() {

        return x;
    }

    int getY() {

        return y;
    }
    Player getContestedOwner() {

        return contestedOwner;
    }
    void setContestedOwner(Player contestedOwner) {

        this.contestedOwner = contestedOwner;
    }
    Player getOwner() {
        return completeOwner;
    }

    void setOwner(Player completeOwner) {
        if(this.completeOwner != null){
            this.completeOwner.removeOwnedTile(this);
        }
        this.completeOwner = completeOwner;
    }

    Color getColor(){

        if(completeOwner != null && contestedOwner == null) {
            return completeOwner.getColor().darker();
        }

        else if (completeOwner == null && contestedOwner != null) {
            return(new Color(contestedOwner.getColor().getRed(), contestedOwner.getColor().getGreen(),
                    contestedOwner.getColor().getBlue(), 110));
        }

        else if (completeOwner != null && contestedOwner != completeOwner){
            float blendedGreen = ((completeOwner.getColor().getGreen() / 255f) * (contestedOwner.getColor().getGreen() / 255f));
            float blendedBlue = ((completeOwner.getColor().getBlue() / 255f) * (contestedOwner.getColor().getBlue() / 255f));
            float blendedRed = ((completeOwner.getColor().getRed() / 255f) * (contestedOwner.getColor().getRed() / 255f));
            return(new Color(((blendedRed + 1 ) / 2),((blendedGreen + 1) / 2),((blendedBlue +1 )/ 2)));

        }else{
            return color;
        }
    }

}