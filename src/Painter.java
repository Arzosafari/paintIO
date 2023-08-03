
import java.awt.*;
import java.util.List;

class Painter {

    private int height,width;
    private final int measure=40;
    private boolean draw = true;
    private List<Player> players;
    private Game game;
    private Player mainPlayer;

    Painter( Game game, Player mainPlayer, List<Player> players){
        this.game = game;
        this.players = players;
        this.mainPlayer=mainPlayer;
    }



    void rendering(Graphics g){
        if(!draw) {
            return;
        }

        height = g.getClipBounds().height;
        width = g.getClipBounds().width;

        drawGameArea(g);
        drawPlayers(g);
    }

    public void setDraw(boolean draw) {

        this.draw = draw;
    }

    private void drawPlayers(Graphics g){
        g.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fontMetrics = g.getFontMetrics();

        for (Player player : players) {
            if (player == mainPlayer) {
                continue;
            }

            int drawX = (int) ((player.getX() - mainPlayer.getX()) * measure + ((width - measure) / 2)
                                + ((player.getDx() - mainPlayer.getDx()) * measure
                                * ((game.getSmooth() + 1) / (double) game.getTickReset())));
            int drawY = (int) ((player.getY() - mainPlayer.getY()) * measure + ((height - measure) / 2)
                                + ((player.getDy() - mainPlayer.getDy()) * measure
                                * ((game.getSmooth() + 1) / (double) game.getTickReset())));

            if (drawX + measure < 0 || drawX > width || drawY + measure < 0 || drawY > height) {
                continue;
            }

            g.setColor(player.getColor());
            g.fillRect(drawX, drawY, measure, measure);

            g.setColor(player.getColor());
            String playerName = player.getNameOfPlayer();
            int stringWidth = fontMetrics.stringWidth(playerName);
            g.drawString(playerName, drawX + (measure - stringWidth) / 2, drawY + measure + 16);
        }

        int mainDrawX = (width - measure) / 2;
        int mainDrawY = (height - measure) / 2;
        g.setColor(mainPlayer.getColor());
        g.fillRect(mainDrawX, mainDrawY, measure, measure);

        g.setColor(Color.BLACK);
        String mainPlayerName = mainPlayer.getNameOfPlayer();
        int stringWidth = fontMetrics.stringWidth(mainPlayerName);
        g.drawString(mainPlayerName, mainDrawX + (measure - stringWidth) / 2, mainDrawY + measure + 16);
    }

    private void drawGameArea(Graphics g) {
        for (int y = 0; y < game.getAreaHeight(); y++) {
            for (int x = 0; x < game.getAreaWidth(); x++) {
                int tileX = x - mainPlayer.getX();
                int tileY = y - mainPlayer.getY();
                int offsetX = (int) (-mainPlayer.getDx() * measure
                        * ((game.getSmooth() + 1) / (double) game.getTickReset()));
                int offsetY = (int) (-mainPlayer.getDy() * measure
                        * ((game.getSmooth() + 1) / (double) game.getTickReset()));
                int drawX = tileX * measure + ((width - measure) / 2) + offsetX;
                int drawY = tileY * measure + ((height - measure) / 2) + offsetY;

                if (drawX + measure < 0 || drawX > width || drawY + measure < 0 || drawY > height) {
                   continue;

                }

                g.setColor(Color.WHITE);
                g.fillRect(drawX, drawY, measure, measure);

                g.setColor(game.getTile(x, y).getColor());
                g.fillRect(drawX, drawY, measure, measure);
            }
        }
    }}
