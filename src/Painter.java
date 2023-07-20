
import java.awt.*;
import java.util.List;

class Painter {

    private int height,width;
    private final int scale;
    private boolean draw = true;
    private List<Player> players;
    private Board board;
    private Player humanPlayer;

    Painter(int scale, Board board, Player humanPlayer, List<Player> players){
        this.scale = scale;
        this.board = board;
        this.players = players;
        this.humanPlayer = humanPlayer;
    }



    void render(Graphics g){
        if(draw){
            height = g.getClipBounds().height;
            width = g.getClipBounds().width;
            drawGameArea(g);
            drawPlayers(g);
        }
    }
    public void setDraw(boolean draw) {

        this.draw = draw;
    }

    private void drawPlayers(Graphics g){
        int drawX;
        int drawY;

        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
        FontMetrics fontMetrics = g.getFontMetrics();

        for (Player player : players) {
            drawX = (player.getX() - humanPlayer.getX()) * scale + ((width - scale) / 2);
            drawY = (player.getY() - humanPlayer.getY()) * scale + ((height - scale) / 2);
            if (player != humanPlayer) {
                drawX += ((player.getDx() - humanPlayer.getDx()) * scale
                        * ((board.getTickCounter() + 1) / (double) board.getTickReset()));
                drawY += ((player.getDy() - humanPlayer.getDy()) * scale
                        * ((board.getTickCounter() + 1) / (double) board.getTickReset()));
            }
            g.setColor(Color.BLUE);
            g.drawString(player.getNameOfPlayer(),
                    drawX + (scale - fontMetrics.stringWidth(player.getNameOfPlayer()))/2, drawY+scale+16);

            if (!(drawX + scale < 0 || drawX > width || drawY + scale < 0 || drawY > height)) {
                g.setColor(player.getColor());
                g.fillRect(drawX, drawY, scale, scale);
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
