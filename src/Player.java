import java.awt.*;
import java.util.ArrayList;

public abstract class Player implements Comparable<Player> {//
    int x,y,dx,dy;
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    public int lastX;
    public int lastY;
    String nameOfPlayer;
    int height,width;
    private Color color;
    private Boolean isAlive = true;
    private Node currentNode;
    private ArrayList<Node> ownedNodes = new ArrayList<>();
    private ArrayList<Node> contestedNodes = new ArrayList<>();
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
    void setOwnedTiles(Node t){
        ownedNodes.add(t);
        t.setOwner(this);
        t.setContestedOwner(null);
    }
    ArrayList<Node> getOwnedTiles(){
        return ownedNodes;
    }


    void setContestedTiles(Node t){
        contestedNodes.add(t);
        t.setContestedOwner(this);
    }
    ArrayList<Node> getContestedTiles(){

        return contestedNodes;
    }
    void setCurrentTile(Node currentNode) {

        this.currentNode = currentNode;
    }
    String getNameOfPlayer() {
        return nameOfPlayer;
    }
    public void setAlive(Boolean alive) {

        isAlive = alive;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }



    Player(int height, int width, Color color){
        this.height = height;
        this.width = width;
        this.color = color;

        this.lastX = x; // Initialize lastX to the same value as x
        this.lastY = y; // Initialize lastY to the same value as y
    }
    public int getDirection() {
        int dx = x - lastX;
        int dy = y - lastY;

        if (dx == 0 && dy == -1) {
            return Player.NORTH;
        } else if (dx == 1 && dy == 0) {
            return Player.EAST;
        } else if (dx == 0 && dy == 1) {
            return Player.SOUTH;
        } else if (dx == -1 && dy == 0) {
            return Player.WEST;
        } else {
            // This should never happen, but just in case
            throw new IllegalStateException("Invalid player direction");
        }
    }


    void die() {
        isAlive = false;
        ArrayList<Node> ownedTilesCopy = (ArrayList<Node>) ownedNodes.clone();
        ArrayList<Node> contestedTilesCopy = (ArrayList<Node>) contestedNodes.clone();
        for(int i = 0; i < ownedTilesCopy.size(); i++){
            ownedTilesCopy.get(i).setOwner(null);
        }

        for(int i = 0; i < contestedTilesCopy.size(); i++){
            contestedTilesCopy.get(i).setContestedOwner(null);
        }

        currentNode = null;
        ownedNodes.clear();
        contestedNodes.clear();

    }
    void removeOwnedTile(Node t){
        ownedNodes.remove(t);
    }
    abstract void move();

    void contestToOwned(){
        for (Node t : contestedNodes) {
            setOwnedTiles(t);
        }
        contestedNodes.clear();
    }

    void checkAttack(Node t){
        if(t.getContestedOwner() != null) {
            t.getContestedOwner().die();
        }
    }


}