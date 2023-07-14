import java.awt.*;
import java.util.ArrayList;

public abstract class Player implements Comparable<Player> {
    int x,y,dx,dy;
    private Color color;
    private ArrayList<Tile> ownedTiles=new ArrayList<>();
    private ArrayList<Tile> contestedTile=new ArrayList<>();
    int height,width;
    String name;
    private Boolean isAlive=true;
    private Tile currentTile;



    public Player(int height,int width,Color color){
        x=(int)(Math.random()*(width-2)+1);
        y=(int)(Math.random()*(height-2)+1);

        if(x<5){
            x+=5;
        }else if(x>(width-5)){
            x-=5;
        }
        if(y<5){
            y+=5;
        }else if(y>(height)-5){
            y-=5;
        }
        this.color=color;
        this.height=height;
        this.width=width;

        double rand=Math.random();
        if(rand<0.25){
            dx=1;
            dy=0;
        }else if(rand<5){
            dx=-1;
            dy=0;
        }else if(rand<0.75){
            dx=0;
            dy=1;
        }else{
            dx=0;
            dy=-1;
        }
    }
    int getX(){return x;}
    int getY(){return y;}
    Color getColor(){
        return color;
    }
    abstract void move();

    public void die(){
        isAlive=false;
        ArrayList<Tile> ownedTilesCopy=(ArrayList<Tile>)ownedTiles.clone() ;
        ArrayList<Tile> contestedTilesCopy=(ArrayList<Tile>)contestedTile.clone();
        for(int i=0;i<ownedTilesCopy.size();i++){
            ownedTilesCopy.get(i).setOwner(null);
        }
        for(int i=0;i<contestedTilesCopy.size();i++){
            contestedTilesCopy.get(i).setOwner(null);
        }
        ownedTiles.clear();
        contestedTile.clear();
        currentTile=null;
    }
    void setTileOwned(Tile t){
        ownedTiles.add(t);
        t.setOwner(this);
        t.setContestedOwner(null);
    }
    void removeTileOwned(Tile t){
        ownedTiles.remove(t);
    }
    ArrayList<Tile> getTilesOwned(){
        return ownedTiles;
    }
    double getPercentageOfOwned(){
        return 100*getTilesOwned().size()/(double)(height*width);
    }
    void setTileContested(Tile t){
        contestedTile.add(t);
        t.setContestedOwner(this);
    }
    ArrayList<Tile> getTilesContested(){
        return contestedTile;
    }
    void contestToOwn(){
        for(Tile t: contestedTile){
            setTileOwned(t);
        }
        contestedTile.clear();
    }
    void checkAttack(Tile t){
        if(t.getContestedOwner()!=null){
            t.getContestedOwner().die();
        }

    }
    void setCurrentTile(Tile currentTile){
        this.currentTile=currentTile;
    }
    int getDx(){
        return dx;
    }
    int getDy(){
        return dy;
    }
    String getName(){
        return name;
    }
    boolean getAlive(){
        return isAlive;
    }
    public void setAlive(boolean alive){
        isAlive=alive;
    }
    public int compareTo(Player player){
        return Integer.compare(player.getTilesOwned().size(),ownedTiles.size());
    }

}
