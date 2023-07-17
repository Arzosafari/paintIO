import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Board extends JPanel {
    private final int areaHeight;
    private final int areaWidth;
    private int botNumber;
    private ActionListener actionListener;
    private Tile[][] gameArea;
    private final int scale=20;
    private ArrayList<Player> players=new ArrayList<>();
    private ArrayList <HumanPlayer> humanPlayers=new ArrayList<>();
    private HashMap<Tile,Player> tilePlayerHashMap=new HashMap<>();
    private ArrayList<Player> deadBots=new ArrayList<>();
    private ArrayList<Painter> painters=new ArrayList<>();
    private HashMap<Player,Painter>playerPainterHashMap=new HashMap<>();
    private int tickCounter=0;
    private boolean paused = true;
    private final int tickReset;
    private List<Color> colorList = new ArrayList<>(Arrays.asList(Color.magenta, Color.green, Color.red,
            Color.blue, Color.orange, Color.yellow, Color.pink, new Color(142,12,255),
            new Color(100,43,119), new Color(150,90,100)));
    Board(ActionListener actionListener,String p1name,int areaHeight,int areaWidth,int gameSpeed,int botNumber){
        this.actionListener=actionListener;
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;
        this.botNumber=botNumber;
        int[]speeds={12,10,8,6,4};
        tickReset=speeds[gameSpeed-1];
        players.add(new HumanPlayer(areaHeight,areaWidth,new Color((int)(Math.random()*0x1000000)),p1name));
        humanPlayers.add((HumanPlayer)players.get(0) );

        initBoard();

        painters.add(new Painter(scale,this,humanPlayers.get(0),players));
        playerPainterHashMap.put(humanPlayers.get(0),painters.get(0));


    }
    Board(ActionListener actionListener,String p1name,String p2name,int areaHeight,int areaWidth,int gameSpeed,int botNumber){
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;
        this.actionListener=actionListener;
        this.botNumber=botNumber;
        int[] speeds={12,10,8,6,4};
        tickReset=speeds[gameSpeed-1];

        players.add(new HumanPlayer(areaHeight, areaWidth, new Color((int)(Math.random() * 0x1000000)), p1name));
        players.add(new HumanPlayer(areaHeight, areaWidth, new Color((int)(Math.random() * 0x1000000)), p2name));
        humanPlayers.add((HumanPlayer)players.get(0));
        humanPlayers.add((HumanPlayer)players.get(1));

        initBoard();

        painters.add(new Painter(scale, this, humanPlayers.get(0), players));
        painters.add(new Painter(scale, this, humanPlayers.get(1), players));
        playerPainterHashMap.put(humanPlayers.get(0), painters.get(0));
        playerPainterHashMap.put(humanPlayers.get(1), painters.get(1));



    }

    private void initBoard(){
        this.gameArea=new Tile[areaHeight][areaWidth];
        for(int i=0;i<gameArea.length;i++){
            for(int j=0;j<gameArea[i].length;j++){
                gameArea[i][j]=new Tile(i,j);
            }
        }
        specifyKeyActions();
        setBackground(Color.BLACK);
        for(int i = 0; i < botNumber; i++){
            if(i > 9){
                players.add(new BotPlayer(gameArea.length,gameArea[0].length,
                        new Color((int)(Math.random() * 0x1000000))));
            }else {
                players.add(new BotPlayer(gameArea.length, gameArea[0].length, colorList.get(i)));
            }
        }


        for(int i=0;i<players.size();i++){
            if(!checkSpawn(players.get(i))){
                players.remove(players.get(i));
                i--;
                if(botNumber > 9){
                    players.add(new BotPlayer(gameArea.length,gameArea[0].length,
                            new Color((int)(Math.random() * 0x1000000))));
                }else {
                    players.add(new BotPlayer(gameArea.length,gameArea[0].length, colorList.get(i)));
                }

            }else {
                startingArea(players.get(i));
            }
        }
        final int INITIAL_DELAY = 0;
        final int PERIOD_INTERVAL = 1000/60;
        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);


    }

    private void specifyKeyActions() {
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
    }
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pause");
        am.put("pause", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                ActionEvent action = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "pause");
                actionListener.actionPerformed(action);
            }
        });

}
   private void startingArea(Player player){
        int x=player.getX();
        int y=player.getY();
       if(!checkSpawn(player)){
           Player playerCopy = new BotPlayer(gameArea.length,gameArea[0].length, player.getColor());
           startingArea(playerCopy);
       }
        for(int i=x-1;i<=x+1;i++){
            for(int j=y-1;j<=y+1;j++){
                player.setTileOwned(getTile(i,j));
            }
        }

   }
   private boolean checkSpawn(Player player){
        int x=player.getX();
        int y=player.getY();
        for(int i=x-3;i<=x+3;i++){
            for(int j=y-3;j<y+3;j++){
                if(getTile(i,j).getOwner()!=null||getTile(i,j).getContestedOwner()!=null){
                    return false;
                }
            }
        }
        return true;
   }
   public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i=0;i<painters.size();i++) {
            g.setClip(getWidth() / painters.size() * i, 0, getWidth() / painters.size(), getHeight());
            g.translate(getWidth() / painters.size() * i, 0);
            painters.get(i).draw(g);
            g.translate(-getWidth() / painters.size() * i, 0);
        }
            try {
                drawScoreboard(g);

            } catch(IndexOutOfBoundsException ignored){
            }
            Toolkit.getDefaultToolkit().sync();


   }
   private void drawScoreboard(Graphics g){
        g.setFont(new Font("Arial",Font.BOLD,12));
        FontMetrics fontMetrics=g.getFontMetrics();
        int fontHeight=fontMetrics.getHeight();
        int barWidth;
        int barHeight=fontHeight+4;

        Player player;
        String string;
        Color color;

        double highestPercentOwned=players.get(0).getPercentageOfOwned();
        Collections.sort(players);
        for(int i=0;i<Integer.min(5,players.size());i++){
            player=players.get(i);
            string=String.format("%.2f%% - " + player.getName(), player.getPercentageOfOwned());
            color=player.getColor();

            barWidth=(int)((player.getPercentageOfOwned()/highestPercentOwned)*(getWidth()/4));
            g.setColor(player.getColor());
            g.fillRect(getWidth()-barWidth,barHeight*i,barWidth,barHeight);

            if(0.299*color.getRed() + 0.587*color.getGreen() + 0.114*color.getBlue() < 127){
                g.setColor(Color.WHITE);
            }else{
                g.setColor(Color.BLACK);
            }
            g.drawString(string, 2+getWidth() -  barWidth,  barHeight*i + fontHeight);
        }


   }
   private void tick(){
        Player player;
        tilePlayerHashMap.clear();
       for (int i = 0; i < players.size(); i++) {
           player = players.get(i);
           player.move();
           if(player.getX() < 0 || player.getX() >= areaWidth || player.getY() < 0 || player.getY() >= areaHeight) {
               player.die();
           }else{
               Tile tile = getTile(player.getX(), player.getY());
               player.checkAttack(tile);
               player.setCurrentTile(tile);
               findCollision(player, tile);
               if (tile.getOwner() != player && player.getAlive()) {
                   player.setTileContested(tile);
                   // If player arrives back to an owned tile
               } else if (player.getTilesContested().size() > 0) {
                   player.contestToOwn();
                   fillEnclosure(player);
               }
           }
           if(player instanceof BotPlayer && !player.getAlive()){
               deadBots.add(player);
           }

           }
       respawnBots();
       boolean allKilled = true;
       for(HumanPlayer humanPlayer : humanPlayers){
           humanPlayer.updateD();
           // Sets painter to stop drawing if humanPlayer is dead
           playerPainterHashMap.get(humanPlayer).setDraw(humanPlayer.getAlive());
           allKilled = allKilled && !humanPlayer.getAlive();
       }
       if(allKilled){
           endGame();
       }
       players.removeIf(p -> !p.getAlive());



   }
   private void endGame(){
       JOptionPane.showMessageDialog(this, "YOU LOST!, GAME OVER...", "GAME OVER", JOptionPane.PLAIN_MESSAGE);
       actionListener.actionPerformed(new ActionEvent(this, 0, "End Game"));
   }
   private void respawnBots(){
       for(int i = 0; i < deadBots.size(); i++){
           if(deadBots.get(i).getAlive()){
               Player player = new BotPlayer(gameArea.length,gameArea[0].length,
                       new Color((int)(Math.random() * 0x1000000)));
               startingArea(player);
               players.add(player);
               deadBots.remove(deadBots.get(i));
           }
       }

   }
   private void findCollision(Player player,Tile tile){
        if(tilePlayerHashMap.containsKey(tile)){
            for(Map.Entry<Tile, Player> entry : tilePlayerHashMap.entrySet()) {
                if (entry.getKey() == tile) {
                    if (entry.getValue().getTilesContested().size() > player.getTilesContested().size()) {
                        entry.getValue().die();
                    } else if (entry.getValue().getTilesContested().size() < player.getTilesContested().size()) {
                        player.die();
                    } else if (entry.getValue().getTilesContested().size() == player.getTilesContested().size()) {
                        if (entry.getValue().getTilesOwned().size() > player.getTilesOwned().size()) {
                            entry.getValue().die();
                        } else {
                            player.die();
                        }
                    }
                }
            }

        }else{
            tilePlayerHashMap.put(tile, player);
        }
       players.removeIf(p -> !p.getAlive());

   }
    private void updateTick(){
        tickCounter++;
        tickCounter %= tickReset;
    }
    private void fillEnclosure(Player player){
        int maxX = 0;
        int minX = gameArea[0].length;
        int maxY = 0;
        int minY = gameArea.length;
        for (Tile t : player.getTilesOwned()) {
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
        for(Tile t : player.getTilesOwned()){
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

                // DFS algorithm
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
                if(cont){ // If DFS don't find boundary
                    inside.addAll(visited);
                }else{
                    outside.addAll(visited);
                }
            }
        }
        for(Tile t : inside){
            player.setTileOwned(t);
        }



    }
    void setPaused(boolean b){
        paused=b;
    }
    int getAreaHeight(){
        return areaHeight;
    }

    public int getAreaWidth() {
        return areaWidth;
    }
    int getTickCounter(){
        return tickCounter;
    }
    int getTickReset(){
        return tickReset;
    }
    Tile getTile(int x, int y){
        return gameArea[y][x];
    }
    private class ScheduleTask extends TimerTask{

        @Override
        public void run() {
            if(!paused) {
                updateTick();
                if (tickCounter == 0) {
                    tick();
                }
                repaint();
            }
        }

        }
    }

