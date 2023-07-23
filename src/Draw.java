import java.awt.*;
import java.util.ArrayList;

public class Draw {
    private int height,width;
    private boolean draw=true;
    private ArrayList<Player> players;
    private Game game;
    private Player MainPlayer;
    public void setDraw(boolean draw){
        this.draw=draw;
    }

    Draw(Game game,Player mainPlayer,ArrayList<Player> players){
        this.game=game;
        this.players=players;
        this.MainPlayer=mainPlayer;
    }
    private void plantPlayers(Graphics g){
        int paintX;
        int paintY;
        g.setFont(new Font("Arial",Font.BOLD,12));
        FontMetrics fontMetrics= g.getFontMetrics();

        for(int i=0;i<players.size();i++){
            paintX=(players.get(i).getX()-MainPlayer.getX())*28+((width-28)/2);
            paintY=(players.get(i).getY()-MainPlayer.getY())*28+((height-28)/2);
            if(players.get(i)!=MainPlayer){
                paintX+=((players.get(i).getDx()-MainPlayer.getDx())*28*((game.getSmooth()+1)/game.getFinalSpeed()));
                paintY+=((players.get(i).getDy()-MainPlayer.getDx())*28*((game.getSmooth()+1)/game.getFinalSpeed()));
            }
            g.drawString(players.get(i).getNameOfPlayer(),paintX+(28-fontMetrics.stringWidth(players.get(i).getNameOfPlayer()))/2,paintY+40);
            g.setColor(Color.cyan);
            if(paintY<=height&&paintY+28>=0&&paintX+28>=0&&paintX<=width){
                g.fillRect(paintX,paintY,28,28);
                g.setColor(players.get(i).getColor());
            }


        }
    }
    private void GamesArea(Graphics g){
        int x;
        int y;
        for(int i=0;i<500;i++){
            for(int j=0;j<500;j++){
                x=(j-MainPlayer.getX())*28+((width-28)/2)+(int)(((MainPlayer.getDx())*-1)*28*((game.getSmooth()+1)/(double)game.getFinalSpeed()));
                y=(i-MainPlayer.getY())*28+((height-28)/2)+(int)(((MainPlayer.getDy())*-1)*28+((game.getSmooth()+1)/(double)game.getFinalSpeed()));
                if(x+28>=0&&x<=width&&y+28>=0&&y<=height){
                    g.fillRect(x,y,28,28);
                    g.setColor(Color.PINK);
                    g.setColor(game.getNode(x,y).getColor());
                    g.fillRect(x,y,28,28);


                }
            }
        }

    }

    void GraphicRender(Graphics g){
        if(draw){
            height=g.getClipBounds().height;
            width=g.getClipBounds().width;
            GamesArea(g);
            plantPlayers(g);
        }

    }

}
