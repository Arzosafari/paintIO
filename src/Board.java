import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Timer;
import java.util.*;


public class Board extends JPanel {
    private Tile[][] gameArea;
    private ActionListener actionListener;
    private final int areaHeight,areaWidth;
    private final int measure = 28;
    private int tickCounter = 0;
    private final int tickReset;
    private int enemyNumber;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<HumanPlayer> humanPlayers = new ArrayList<>();
    private HashMap<Tile, Player> tilePlayerHashMap = new HashMap<>();

    private ArrayList<Player> deadBots = new ArrayList<>();
    private boolean paused = true;

    private ArrayList<Painter> painters = new ArrayList<>();
    private HashMap<Player, Painter> playerPainterHashMap = new HashMap<>();

    private List<Color> colorList = new ArrayList<>(Arrays.asList(Color.magenta, Color.green, Color.red,
            Color.blue, Color.orange, Color.yellow, Color.pink, new Color(142,12,255),
            new Color(100,43,119), new Color(150,90,100)));


    Board(ActionListener actionListener, String p1name, int areaHeight, int areaWidth, int gameSpeed, int enemyNumber){
        this.actionListener = actionListener;
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;
        this.enemyNumber = enemyNumber;
        int[] speeds = {12, 10, 8, 6, 4};
        tickReset = speeds[gameSpeed - 1];

        players.add(new HumanPlayer(areaHeight, areaWidth, new Color((int)(Math.random() * 0x1000000)), p1name));
        humanPlayers.add((HumanPlayer)players.get(0));

        initBoard();

        painters.add(new Painter(measure, this, humanPlayers.get(0), players));
        playerPainterHashMap.put(humanPlayers.get(0), painters.get(0));
    }


    Board(ActionListener actionListener, String p1name, String p2name, int areaHeight, int areaWidth, int gameSpeed, int enemyNumber) {
        this.actionListener = actionListener;
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;
        this.enemyNumber = enemyNumber;
        int[] speeds = {12, 10, 8, 6, 4};
        tickReset = speeds[gameSpeed - 1];

        players.add(new HumanPlayer(areaHeight, areaWidth, new Color((int)(Math.random() * 0x1000000)), p1name));
        players.add(new HumanPlayer(areaHeight, areaWidth, new Color((int)(Math.random() * 0x1000000)), p2name));
        humanPlayers.add((HumanPlayer)players.get(0));
        humanPlayers.add((HumanPlayer)players.get(1));

        initBoard();

        painters.add(new Painter(measure, this, humanPlayers.get(0), players));
        painters.add(new Painter(measure, this, humanPlayers.get(1), players));
        playerPainterHashMap.put(humanPlayers.get(0), painters.get(0));
        playerPainterHashMap.put(humanPlayers.get(1), painters.get(1));
    }


    private void initBoard(){

         this.gameArea = new Tile[areaHeight][areaWidth];
            for(int i = 0; i < gameArea.length; i++) {
                for (int j = 0; j < gameArea[i].length; j++) {
                    gameArea[i][j] = new Tile(j, i);

                }
            }
        if (humanPlayers.size() == 1) {
            Player player = humanPlayers.get(0);
            movePlayerByMouse(player);
        }

        keyEvent();
        addBot();
        setBackground(Color.BLACK);
        timer();
    }
    private void timer(){
        final int INITIAL_DELAY = 0 ;//in time;
        final int PERIOD_INTERVAL = 1000/60;//60 bar dar saniye va ejraye har task hodode 16 milisecond
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);

    }
    private void addBot(){
        for( int i = 0; i < enemyNumber; i++){
            if(i > 9){
                players.add(new BotPlayer(gameArea.length,gameArea[0].length,
                        new Color((int)(Math.random() * 0x1000000))));
            }else {
                players.add(new BotPlayer(gameArea.length, gameArea[0].length, colorList.get(i)));
            }
        }

        for( int i = 0; i < players.size(); i++){
            if(!notClose(players.get(i))){
                players.remove(players.get(i));
                i--;
                if(enemyNumber > 9){
                    players.add(new BotPlayer(gameArea.length,gameArea[0].length,
                            new Color((int)(Math.random() * 0x1000000))));
                }else {
                    players.add(new BotPlayer(gameArea.length,gameArea[0].length, colorList.get(i)));
                }

            }else {
                firstPlace(players.get(i));
            }
        }

    }


    private void keyEvent(){
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        if(humanPlayers.size() == 1){
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUP");
            am.put("moveUP", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) { humanPlayers.get(0).setNextKey(KeyEvent.VK_UP);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDOWN");
            am.put("moveDOWN", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {humanPlayers.get(0).setNextKey(KeyEvent.VK_DOWN);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLEFT");
            am.put("moveLEFT", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) { humanPlayers.get(0).setNextKey(KeyEvent.VK_LEFT);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRIGHT");
            am.put("moveRIGHT", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) { humanPlayers.get(0).setNextKey(KeyEvent.VK_RIGHT);
                }
            });
        }else if(humanPlayers.size() == 2){
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveP1UP");
            am.put("moveP1UP", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) { humanPlayers.get(1).setNextKey(KeyEvent.VK_UP);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveP1DOWN");
            am.put("moveP1DOWN", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {humanPlayers.get(1).setNextKey(KeyEvent.VK_DOWN);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveP1LEFT");
            am.put("moveP1LEFT", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) { humanPlayers.get(1).setNextKey(KeyEvent.VK_LEFT);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveP1RIGHT");
            am.put("moveP1RIGHT", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) { humanPlayers.get(1).setNextKey(KeyEvent.VK_RIGHT);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "moveP2UP");
            am.put("moveP2UP", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) { humanPlayers.get(0).setNextKey(KeyEvent.VK_W);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "moveP2DOWN");
            am.put("moveP2DOWN", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {humanPlayers.get(0).setNextKey(KeyEvent.VK_S);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "moveP2LEFT");
            am.put("moveP2LEFT", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) { humanPlayers.get(0).setNextKey(KeyEvent.VK_A);
                }
            });
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "moveP2RIGHT");
            am.put("moveP2RIGHT", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) { humanPlayers.get(0).setNextKey(KeyEvent.VK_D);
                }
            });
        }


        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pause");
        am.put("pause", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                ActionEvent action = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "pause");
                actionListener.actionPerformed(action);
            }
        });
    }


    private void movePlayerByMouse(Player player) {
        // Add a mouse listener to the game panel
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // Compute the distance and direction of the mouse drag
                int distX = e.getX() - player.getX();
                int distY = e.getY() - player.getY();

                // Determine the direction of movement based on the distance
                if (Math.abs(distX) > Math.abs(distY)) {
                    // Move left or right
                    if (distX < 0) {
                        ((HumanPlayer) player).setNextKey(KeyEvent.VK_LEFT);
                        player.setX(player.getX() - tickReset);
                    } else {
                        ((HumanPlayer) player).setNextKey(KeyEvent.VK_RIGHT);
                        player.setX(player.getX() + tickReset);
                    }
                } else {
                    // Move up or down
                    if (distY < 0) {
                        ((HumanPlayer) player).setNextKey(KeyEvent.VK_UP);
                        player.setY(player.getY() - tickReset);
                    } else {
                        ((HumanPlayer) player).setNextKey(KeyEvent.VK_DOWN);
                        player.setY(player.getY() + tickReset);
                    }
                }


            }
        });
    }


    /**
     * Marks all tiles in the starting area of a player to owned by player
     * @param player player to generate starting area for
     */
    private void firstPlace(Player player){
        int x = player.getX();
        int y = player.getY();
        if(!notClose(player)){
            Player player2 = new BotPlayer(gameArea.length,gameArea[0].length, player.getColor());
            firstPlace(player2);
        }
        for(int i = x-1; i <= x+1; i++){
            for(int j = y-1; j <= y+1; j++){
                player.setOwnedTiles(getTile(i,j));
            }
        }
    }



    private boolean notClose(Player player){
        int x = player.getX();
        int y = player.getY();
        for(int i = x-3; i <= x+3; i++) {
            for (int j = y - 3; j <= y + 3; j++) {
                if (getTile(i, j).getOwner() != null || getTile(i, j).getContestedOwner() != null ) {
                    return false;
                }
            }
        }
        return true;//if no body is close
    }

    /**
     * Overrides paintComponent and is called whenever everything should be drawn on the screen
     * @param g Graphics element used to draw elements on screen
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < painters.size(); i++){
            g.setClip(getWidth()/painters.size() * i,0,getWidth()/painters.size(),getHeight());
            g.translate(getWidth()/painters.size() * i,0);
            painters.get(i).render(g);

            g.translate(-getWidth()/painters.size() * i,0);
        }
        try {
            renderScoreboard(g);

        } catch(IndexOutOfBoundsException ignored){
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void renderScoreboard(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fontMetrics = g.getFontMetrics();
        int fontHeight = fontMetrics.getHeight();
        int rectWidth;
        int rectHeight = fontHeight + 5;

        Player player;
        String string;
        Color color;

        double highestPercentOwned = players.get(0).getPercentOfOwned();
        Collections.sort(players);
        for(int i = 0; i < Integer.min(5, players.size()); i++){
            player = players.get(i);
            string = String.format("%.2f%% - " + player.getNameOfPlayer(), player.getPercentOfOwned());
            color = player.getColor();

            rectWidth = (int)((player.getPercentOfOwned() / highestPercentOwned)*(getWidth()/4));
            g.setColor(player.getColor());
            g.fillRect(getWidth() - rectWidth,  rectHeight*i, rectWidth,rectHeight);
            // If color is perceived as dark set the font color to white, else black
            if(0.299*color.getRed() + 0.587*color.getGreen() + 0.114*color.getBlue() < 127){
                g.setColor(Color.WHITE);
            }else{
                g.setColor(Color.BLACK);
            }
            g.drawString(string, 2+getWidth() -  rectWidth,  rectHeight*i + fontHeight);
        }
    }

    private void logic(){
        Player player;
        tilePlayerHashMap.clear();
        for (int i = 0; i < players.size(); i++) {
            player = players.get(i);
            player.move();
            int x = player.getX();
            int y = player.getY();
            // unlimited part
            if (x < 0) x = areaWidth - 1;
            if (x >= areaWidth) x = 0;
            if (y < 0) y = areaHeight - 1;
            if (y >= areaHeight) y = 0;
            Tile tile = getTile(x, y);
            player.checkAttack(tile);
            player.setCurrentTile(tile);
            findCollision(player, tile);

            // If player is outside their owned territory
            if (tile.getOwner() != player && player.getAlive()) {
                player.setContestedTiles(tile);
                // If player arrives back to an owned tile
            } else if (player.getContestedTiles().size() > 0) {
                player.contestToOwned();
                fillEnclosure(player);
            }

            // If BotPlayer is killed, add it to deadBots list
            if (player instanceof BotPlayer && !player.getAlive()) {
                deadBots.add(player);
            }
        }
        renewBots();

        boolean allKilled = true;
        for (HumanPlayer humanPlayer : humanPlayers) {
            humanPlayer.updateDirection();
            // Sets painter to stop drawing if humanPlayer is dead
            playerPainterHashMap.get(humanPlayer).setDraw(humanPlayer.getAlive());
            allKilled = allKilled && !humanPlayer.getAlive();
        }
        if (allKilled) {
            endGame();
        }

// Remove dead players
        players.removeIf(p -> !p.getAlive());
    }

    /**
     * Method to end game and tell this to PaperIO class
     */
    private void endGame(){
        JOptionPane.showMessageDialog(this, "YOU LOST!, GAME OVER...", "GAME OVER", JOptionPane.PLAIN_MESSAGE);
        actionListener.actionPerformed(new ActionEvent(this, 0, "End Game"));
    }

    /**
     * Method that respawns dead bots after a set interval
     */
    private void renewBots(){
        for(int i = 0; i < deadBots.size(); i++){
            if(deadBots.get(i).getAlive()){
                Player player = new BotPlayer(gameArea.length,gameArea[0].length,
                        new Color((int)(Math.random() * 0x1000000)));
                firstPlace(player);
                players.add(player);
                deadBots.remove(deadBots.get(i));
            }
        }
    }

    /**
     * Method that detects player-to-player head on collision
     * @param player Player you want to check collision for
     * @param tile   Tile that Player currently is on
     */
    private void findCollision(Player player, Tile tile) {
        // If corresponding tile is found in tilePlayerMap
        if(tilePlayerHashMap.containsKey(tile)) {

            // Iterate through all entries in tilePlayerMap, if the Tile in entry matches Tile in input,
            // compare sizes between players and destroy one of them. The player with the largest tiles contested
            // survives. If both players have the same amount of tiles contested, the player with the most tiles
            // owned survives. If both players have the same amount of tiles contested and tiles owned,
            // the first player added to Players list dies.
            for(Map.Entry<Tile, Player> entry : tilePlayerHashMap.entrySet()) {
                if (entry.getKey() == tile) {
                    if (entry.getValue().getContestedTiles().size() > player.getContestedTiles().size()) {
                        entry.getValue().die();
                    } else if (entry.getValue().getContestedTiles().size() < player.getContestedTiles().size()) {
                        player.die();
                    } else if (entry.getValue().getContestedTiles().size() == player.getContestedTiles().size()) {
                        if (entry.getValue().getOwnedTiles().size() > player.getOwnedTiles().size()) {
                            entry.getValue().die();
                        } else {
                            player.die();
                        }
                    }
                }
            }
        }else { // If no corresponding tile is found, add tile and player to tilePlayerMap
            tilePlayerHashMap.put(tile, player);
        }
        // Remove dead players(lambda)
        players.removeIf(p -> !p.getAlive());
    }

    /**
     * Controls tick counter of game which is needed to make game smooth.
     */
    private void smooth(){
        tickCounter++;
        tickCounter %= tickReset;
    }

    /**
     * After a player has traveled out to enclose an area the area needs to be filled. This method depends on that the
     * Player.contestedToOwned() method has been called. The method works by doing a depth first search from each tile
     * adjacent to a tile owned by the player sent as parameter. If the DFS algorithm finds a boundary we know it is not
     * enclosed and should not be filled. The boundary is the smallest rectangle surrounding all owned tiles by the
     * player to minimize cost of method. If the DFS can't find the boundary or if the one the DFS starts on we know it

     */
    private void fillEnclosure(Player player) {

        int maxX = 0;
        int minX = gameArea[0].length;
        int maxY = 0;
        int minY = gameArea.length;
        for (Tile t : player.getOwnedTiles()) {
            if(t.getX() > maxX) maxX = t.getX();
            if(t.getX() < minX) minX = t.getX();
            if(t.getY() > maxY) maxY = t.getY();
            if(t.getY() < minY) minY = t.getY();
        }


        ArrayList<Tile> outside = new ArrayList<>();
        ArrayList<Tile> inside  = new ArrayList<>();
        ArrayList<Tile> visited = new ArrayList<>();
        HashSet<Tile> toCheck = new HashSet<>();


        int y;
        int x;
        for(Tile t : player.getOwnedTiles()){
            y = t.getY();
            x = t.getX();
            if(y -1 >= 0) toCheck.add(gameArea[y-1][x]);
            if(y + 1 < gameArea.length) toCheck.add(gameArea[y+1][x]);
            if(x - 1 >= 0) toCheck.add(gameArea[y][x-1]);
            if(x + 1 < gameArea[y].length) toCheck.add(gameArea[y][x+1]);
        }



        for(Tile t : toCheck){
            if(!inside.contains(t)){
                Stack<Tile> stack = new Stack<>();
                boolean cont = true;
                Tile v;
                visited.clear();


                stack.push(t);
                while((!stack.empty()) && cont){
                    v = stack.pop();
                    if(!visited.contains(v) && (v.getOwner() != player)){
                        y = v.getY();
                        x = v.getX();
                        if(outside.contains(v) //If already declared as outside
                                || x < minX || x > maxX || y < minY || y > maxY //If outside of boundary
                                || x == gameArea[0].length -1 || x == 0 || y == 0 || y == gameArea.length -1){ // If it is a edge tile
                            cont = false;
                        }else{
                            visited.add(v);
                            if(y -1 >= 0) stack.push(gameArea[y-1][x]);
                            if(y + 1 < gameArea.length) stack.push(gameArea[y+1][x]);
                            if(x - 1 >= 0) stack.push(gameArea[y][x-1]);
                            if(x + 1 < gameArea[y].length) stack.push(gameArea[y][x+1]);
                        }
                    }
                }
                if(cont){
                    inside.addAll(visited);
                }else{
                    outside.addAll(visited);
                }
            }
        }


        for(Tile t : inside){
            player.setOwnedTiles(t);
        }
    }

    /**
     * Set board to paused mode, meaning logic and graphics are not updated
     * @param b True if game should be paused, false otherwise
     */
    void setPaused(Boolean b){
        paused = b;
    }

    /**
     * Get height of game area
     * @return height of game area
     */
    int getAreaHeight() {
        return areaHeight;
    }

    /**
     * Get width of game area
     * @return width of game area
     */
    int getAreaWidth() {
        return areaWidth;
    }

    /**
     * Get current tick counter
     * @return current tick counter
     */
    int getTickCounter() {
        return tickCounter;
    }

    /**
     * Get how often tick is reset, impacting speed of game
     * @return how often tick is reset
     */
    int getTickReset() {
        return tickReset;
    }

    /**
     * Get tile at position (x,y)
     * @param x x position of tile
     * @param y y position of tile
     * @return tile at position (x,y)
     */
    Tile getTile(int x, int y){
        return gameArea[y][x];
    }

    /**
     * ScheduleTask is responsible for receiving and responding to timer calls
     */
    private class ScheduleTask extends TimerTask {

        /**
         * Gets called by timer at specified interval. Calls tick at specified rate and repaint each time
         */
        @Override
        public void run() {
            if(!paused) {
                smooth();
                if (tickCounter == 0) {
                    logic();
                }
                repaint();
            }
        }
    }
}