import java.awt.*;
import java.util.ArrayList;

public abstract class Player {
    int x, y;
    int dX, dY;
    String nameOfPlayer;
    private Color color;
    private Boolean dead = false;
    private Node thisNode;
    private ArrayList<Node> darkNodes = new ArrayList<>();
    private ArrayList<Node> brightNodes = new ArrayList<>();

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    Boolean getDead() {
        return dead;
    }

    int getDx() {
        return dX;
    }

    int getDy() {
        return dY;
    }

    Color getColor() {
        return color;
    }

    protected void setDarkNodes(Node node) {
        darkNodes.add(node);
        node.setOwner(this);
        node.setNotCompleteOwner(null);
    }

    ArrayList<Node> getDarkNodes() {
        return darkNodes;
    }

    void setBrightNodes(Node node) {
        brightNodes.add(node);
        node.setNotCompleteOwner(this);
    }

    ArrayList<Node> getBrightNodes() {
        return brightNodes;
    }

    void setThisNode(Node thisNode) {
        this.thisNode = thisNode;
    }

    String getNameOfPlayer() {
        return nameOfPlayer;
    }

    void setDead(Boolean dead) {
       this. dead = dead;
    }
    void setY(int y){
        this.y=y;
    }
    void setX(int x){
        this.x=x;
    }
    Player(Color color){
        this.color=color;
        int randX=(int)(Math.random()*(498)+1);
        int randY=(int)(Math.random()*(498)+1);
        double randZ=Math.random();
        x=randX;
        y=randY;
        if(randX<10){
            x+=10;
        } else if (randX>490){
            x-=10;

        }
        if(randY<10){
            y+=10;
        }else if(y>490){
            y-=10;
        }
        if(randZ<=0.25){
            dX=1;
            dY=0;
        }else if(randZ<=0.5&&randZ>0.25){
            dX=-1;
            dY=0;
        } else if (randZ<=0.75&&randZ>0.5) {
            dX=0;
            dY=1;
            
        }else{
            dX=0;
            dY=-1;
        }

    }
    void killed(){
        dead=true;
        ArrayList<Node>darkNodes2;
        ArrayList<Node>brightNode2;
        darkNodes2= (ArrayList<Node>) darkNodes.clone();
        brightNode2= (ArrayList<Node>) brightNodes.clone();
        for(int i=0;i<darkNodes2.size();i++)
            darkNodes2.get(i).setOwner(null);
        for(int j=0;j<brightNode2.size();j++)
            brightNode2.get(j).setNotCompleteOwner(null);

        brightNodes.clear();
        darkNodes.clear();
        thisNode=null;


    }
    abstract void move();
    protected void removeDarkNode(Node node){
        darkNodes.remove(node);
    }
    protected void changeBrightToDark(){
        for (Node node:brightNodes)
            setDarkNodes(node);
        brightNodes.clear();


    }
    protected void fight(Node node){
        if(node.getNotCompleteOwner()!=null)
            node.getNotCompleteOwner().killed();

    }


    }

