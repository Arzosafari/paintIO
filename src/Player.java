import java.awt.*;
import java.util.ArrayList;

public abstract class Player implements Comparable<Player> {//
    int x,y,dx,dy;
    String nameOfPlayer;
    int height,width;
    private Color color;
    private Boolean isAlive = true;
    private Tile currentTile;
    private ArrayList<Tile> ownedTiles = new ArrayList<>();
    private ArrayList<Tile> contestedTiles = new ArrayList<>();
    Boolean getAlive() {
        return isAlive;
    }
    int getX(){
        return x;
    }
    int getY(){
        return y;
    }
    int getDx() {

        return dx;
    }
    int getDy() {

        return dy;
    }

    Color getColor(){

        return color;
    }
    void setOwnedTiles(Tile t){
        ownedTiles.add(t);
        t.setOwner(this);
        t.setContestedOwner(null);
    }
    ArrayList<Tile> getOwnedTiles(){
        return ownedTiles;
    }
    double getPercentOfOwned(){

        return 100 * getOwnedTiles().size() / (double)(height*width);
    }

    void setContestedTiles(Tile t){
        contestedTiles.add(t);
        t.setContestedOwner(this);
    }
    ArrayList<Tile> getContestedTiles(){
        return contestedTiles;
    }
    void setCurrentTile(Tile currentTile) {

        this.currentTile = currentTile;
    }
    String getNameOfPlayer() {
        return nameOfPlayer;
    }
    public void setAlive(Boolean alive) {
        isAlive = alive;
    }
    public int compareTo(Player player){
        return Integer.compare(player.getOwnedTiles().size(), ownedTiles.size());
    }

    Player(int height, int width, Color color){
        this.height = height;
        this.width = width;
        this.color = color;

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
    }


    void die() {
        isAlive = false;
        ArrayList<Tile> ownedTilesCopy = (ArrayList<Tile>)ownedTiles.clone();
        ArrayList<Tile> contestedTilesCopy = (ArrayList<Tile>)contestedTiles.clone();
        for(int i = 0; i < ownedTilesCopy.size(); i++){
            ownedTilesCopy.get(i).setOwner(null);
        }

        for(int i = 0; i < contestedTilesCopy.size(); i++){
            contestedTilesCopy.get(i).setContestedOwner(null);
        }

        currentTile = null;
        ownedTiles.clear();
        contestedTiles.clear();

    }
    void removeOwnedTile(Tile t){
        ownedTiles.remove(t);
    }
    abstract void move();

    void contestToOwned(){
        for (Tile t : contestedTiles) {
            setOwnedTiles(t);
        }
        contestedTiles.clear();
    }

    void checkAttack(Tile t){
        if(t.getContestedOwner() != null) {
            t.getContestedOwner().die();
        }
    }


}