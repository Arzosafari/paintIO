import java.awt.*;
import java.util.List;

public class Painter {
    private int width,height;
    private final int scale;
    private List<Player> players;
    private Player humanPlayer;
    private Board board;
    private boolean draw=true;
    Painter(int scale,Board board,Player humanPlayer,List<Player> players){
        this.scale=scale;
        this.board=board;
        this.players=players;
        this.humanPlayer=humanPlayer;
    }
    public void setDraw(boolean draw){
        this.draw=draw;
    }
    void draw(Graphics g){
        if(draw){
            height=g.getClipBounds().height;
            width=g.getClipBounds().width;
            drawGameArea(g);
            drawPlayers(g);
        }

    }
    private void drawPlayers(Graphics g){
        int drawX;
        int drawY;

        g.setFont(new Font("Arial",Font.BOLD,12));
        FontMetrics fontMetrics= g.getFontMetrics();

        for(Player player:players){
            drawX= (player.getX()-humanPlayer.getX())*scale+((width-scale)/2);
            drawY=(player.getY()-humanPlayer.getY())*scale+((height-scale)/2);
            if(player!=humanPlayer){
                drawX+=((player.getDx()-humanPlayer.getDx())*scale*((board.getTickCounter()+1)/(double) board.getTickReset()));
                drawY+=((player.getDy()-humanPlayer.getDy())*scale*((board.getTickCounter()+1)/(double) board.getTickReset()));
            }
            g.setColor(Color.BLUE);
            g.drawString(player.getName(),drawX+(scale-fontMetrics.stringWidth(player.getName()))/2,drawY+scale+16);
            if(!(drawX+ scale<0||drawX>width||drawY+scale<0||drawY>height)){
                g.setColor(player.getColor());
                g.fillRect(drawX,drawY,scale,scale);
            }
        }

    }
    private void drawGameArea(Graphics g) {
        int drawX;
        int drawY;

        for (int y = 0; y < board.getAreaHeight(); y++) {
            for (int x = 0; x < board.getAreaWidth(); x++) {
                // x and y position relative to humanPlayer at which tile should be drawn
                drawX = (x - humanPlayer.getX()) * scale + ((width - scale) / 2)
                        + (int) ((-humanPlayer.getDx()) * scale *
                        ((board.getTickCounter() + 1) / (double) board.getTickReset()));
                drawY = (y - humanPlayer.getY()) * scale + ((height - scale) / 2)
                        + (int) ((-humanPlayer.getDy()) * scale *
                        ((board.getTickCounter() + 1) / (double) board.getTickReset()));

                // If visible, draw first white background and then draw color on top
                if (!(drawX + scale < 0 || drawX > width || drawY + scale < 0 || drawY > height)) {
                    g.setColor(Color.white);
                    g.fillRect(drawX, drawY, scale, scale);

                    g.setColor(board.getTile(x,y).getColor());
                    g.fillRect(drawX, drawY, scale, scale);
                }
            }
        }
    }


}
