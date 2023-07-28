import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.*;


public class Game extends JPanel {
    private Node[][] gameArea;
    private int offsetX, offsetY;
    private boolean isDragging;
    private boolean isMoving;
    private int bullet=4;
    private int destinationX, destinationY;
    private boolean hard;

    private ActionListener actionListener;
    MouseEvent e;
    private Point lastDragPoint;
    private final int areaHeight=500;
    private final int areaWidth=500;

    private int smooth = 0;
    private final int finalSpeed;
    private int enemyNumber;
    private ArrayList<Player> players = new ArrayList<>();
    private MainPlayer mainPlayer;
    private HashMap<Node, Player> tilePlayerHashMap = new HashMap<>();

    private ArrayList<Player> looserEnemy = new ArrayList<>();
    private boolean paused = true;

    private ArrayList<Painter> painters = new ArrayList<>();
    private HashMap<Player, Painter> playerPainterHashMap = new HashMap<>();
    Game(ActionListener actionListener, String p1name,int gameSpeed, int enemyNumber,boolean hard){
        setSize(1000,1000);
        this.actionListener=actionListener;
        this.hard=true;
        this.enemyNumber=enemyNumber;
        int[] speeds = {10, 8, 6,4,2};
        finalSpeed=speeds[gameSpeed-1];
        players.add(mainPlayer=new MainPlayer(areaHeight, areaWidth, new Color(102,60,90), p1name));
        this.gameArea =  new Node[areaWidth][areaHeight];
        for(int i = 0; i < gameArea.length; i++) {
            for (int j = 0; j < gameArea[i].length; j++) {
                gameArea[i][j] = new Node(j, i);

            }
        }


        registerArrowKeyInputs();
        addSmartEnemy();
        setBackground(Color.BLACK);
        timer();
        painters.add(new Painter( this, mainPlayer, players));
        playerPainterHashMap.put(mainPlayer, painters.get(0));




    }



    Game(ActionListener actionListener, String p1name,int gameSpeed, int enemyNumber){
        setSize(1000,1000);
        this.actionListener = actionListener;
        this.enemyNumber = enemyNumber;
        int[] speeds = {12, 10, 8, 6, 4};
        finalSpeed = speeds[gameSpeed - 1];
        this.hard=false;

        players.add(mainPlayer=new MainPlayer(areaHeight, areaWidth, new Color(102,40,75), p1name));




        this.gameArea =  new Node[areaWidth][areaHeight];
        for(int i = 0; i < gameArea.length; i++) {
            for (int j = 0; j < gameArea[i].length; j++) {
                gameArea[i][j] = new Node(j, i);

            }



        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isMoving) {
                    isMoving = true;
                    destinationX = e.getX();
                    destinationY = e.getY();

                } else {
                    isMoving = false;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (mainPlayer.contains(e.getX(), e.getY())) {
                    offsetX = e.getX() - mainPlayer.getX(); // Calculate offset for smooth dragging
                    offsetY = e.getY() - mainPlayer.getY();
                    isDragging = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    mainPlayer.setPosition(e.getX() - offsetX, e.getY() - offsetY);
                    repaint(); // Trigger repaint to update the player position visually
                }
            }
        });

        timer();
        movePlayerByMouse(mainPlayer);
        registerArrowKeyInputs();
        addNormalEnemy();
        setBackground(Color.BLACK);


        painters.add(new Painter( this, mainPlayer, players));
        playerPainterHashMap.put(mainPlayer, painters.get(0));
    }



    private void weaponA(MainPlayer currentPlayer, Node[][] nodes) {
        int x = currentPlayer.getX();
        int y = currentPlayer.getY();
        int dx = currentPlayer.getDx();
        int dy = currentPlayer.getDy();
        if(bullet>0) {

            if (currentPlayer.getDirection() == Player.SOUTH) {
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y + 5; j <= y + 7; j++) {
                        currentPlayer.setOwnedTiles(nodes[j][i]);
                        if(nodes[j][i].getContestedOwner() instanceof normalEnemy||nodes[j][i].getContestedOwner() instanceof SmartEnemy)
                            nodes[j][i].getContestedOwner().die();

                    }
                }
            } else if (currentPlayer.getDirection() == Player.EAST) {
                for (int i = x + 5; i <= x + 7; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        currentPlayer.setOwnedTiles(nodes[j][i]);
                        if(nodes[j][i].getContestedOwner() instanceof normalEnemy||nodes[j][i].getContestedOwner() instanceof SmartEnemy)
                            nodes[j][i].getContestedOwner().die();
                    }
                }
            } else if (currentPlayer.getDirection() == Player.WEST) {
                for (int i = x - 7; i <= x - 5; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        currentPlayer.setOwnedTiles(nodes[j][i]);
                        if(nodes[j][i].getContestedOwner() instanceof normalEnemy||nodes[j][i].getContestedOwner() instanceof SmartEnemy)
                            nodes[j][i].getContestedOwner().die();
                    }
                }

            } else if (currentPlayer.getDirection() == Player.NORTH) {
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 7; j <= y - 5; j++) {
                        currentPlayer.setOwnedTiles(nodes[j][i]);
                        if(nodes[j][i].getContestedOwner() instanceof normalEnemy||nodes[j][i].getContestedOwner() instanceof SmartEnemy)
                            nodes[j][i].getContestedOwner().die();
                    }
                }
            }
            bullet--;
        }
        else {
            JPanel panel = new JPanel();
            panel.setBackground(Color.PINK);

            JOptionPane.showMessageDialog(this, panel, "you cant use weapon A anymore!!! ", JOptionPane.PLAIN_MESSAGE);

        }
    }

    private void timer(){
         Timer timer = new Timer();
         TimerTask timerTask=new TimerTask() {
             @Override
             public void run() {
                 if(!paused) {
                     smooth();
                     if (smooth == 0) {
                         logic();
                     }
                     repaint();
                 }
             }
         };
        timer.scheduleAtFixedRate(timerTask,
                0, 1000/60);

    }
    private void addNormalEnemy(){
        for( int i = 0; i < enemyNumber; i++){
            int randR= (int) (Math.random() * 200);
            int randG= (int) (Math.random() * 200);
            int randB= (int) (Math.random() * 200);

                players.add(new normalEnemy(gameArea.length,gameArea[0].length,
                        new Color(randR,randG,randB)));

        }

        for( int i = 0; i < players.size(); i++){
            if(!notClose(players.get(i))){
                players.remove(players.get(i));
                i--;
                int randR= (int) (Math.random() * 200);
                int randG= (int) (Math.random() * 200);
                int randB= (int) (Math.random() * 200);

                players.add(new normalEnemy(gameArea.length,gameArea[0].length,
                        new Color(randR,randG,randB)));

            }else {
                firstPlace(players.get(i));
            }
        }

    }
    private void addSmartEnemy(){
        for( int i = 0; i < enemyNumber; i++){
            int randR= (int) (Math.random() * 200);
            int randG= (int) (Math.random() * 200);
            int randB= (int) (Math.random() * 200);

            players.add(new SmartEnemy(gameArea.length,gameArea[0].length,
                    new Color(randR,randG,randB),mainPlayer));

        }
        for( int i = 0; i < players.size(); i++){
            if(!notClose(players.get(i))){
                players.remove(players.get(i));
                i--;
                int randR= (int) (Math.random() * 200);
                int randG= (int) (Math.random() * 200);
                int randB= (int) (Math.random() * 200);

                players.add(new SmartEnemy(gameArea.length,gameArea[0].length,
                        new Color(randR,randG,randB),mainPlayer));

            }else {
                firstPlace(players.get(i));
            }
        }

    }


    private void registerArrowKeyInputs() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUP");
        getActionMap().put("moveUP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.setNextKey(KeyEvent.VK_UP);
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public void putValue(String key, Object value) {
                // Do nothing
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDOWN");
        getActionMap().put("moveDOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.setNextKey(KeyEvent.VK_DOWN);
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public void putValue(String key, Object value) {
                // Do nothing
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLEFT");
        getActionMap().put("moveLEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.setNextKey(KeyEvent.VK_LEFT);
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public void putValue(String key, Object value) {
                // Do nothing
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRIGHT");
        getActionMap().put("moveRIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.setNextKey(KeyEvent.VK_RIGHT);
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public void putValue(String key, Object value) {
                // Do nothing
            }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "weaponA");
        getActionMap().put("weaponA", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                weaponA(mainPlayer,gameArea);
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public void putValue(String key, Object value) {
                // Do nothing
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
                        ((MainPlayer) player).setNextKey(KeyEvent.VK_LEFT);
                        player.setX(player.getX() - finalSpeed);
                    } else {
                        ((MainPlayer) player).setNextKey(KeyEvent.VK_RIGHT);
                        player.setX(player.getX() + finalSpeed);
                    }
                } else {
                    // Move up or down
                    if (distY < 0) {
                        ((MainPlayer) player).setNextKey(KeyEvent.VK_UP);
                        player.setY(player.getY() - finalSpeed);
                    } else {
                        ((MainPlayer) player).setNextKey(KeyEvent.VK_DOWN);
                        player.setY(player.getY() + finalSpeed);
                    }
                }


            }
        });
    }


    private void firstPlace(Player player){
        int x = player.getX();
        int y = player.getY();
        if(!notClose(player)){
            if(hard){
                Player player2 = new SmartEnemy(gameArea.length,gameArea[0].length, player.getColor(),mainPlayer);
                firstPlace(player2);

            }else{
            Player player2 = new normalEnemy(gameArea.length,gameArea[0].length, player.getColor());
            firstPlace(player2);}
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
        for(int i = x-4; i <= x+4; i++) {
            for (int j = y - 4; j <= y + 4; j++) {
                if (getTile(i, j).getOwner() != null || getTile(i, j).getContestedOwner() != null ) {
                    return false;
                }
            }
        }
        return true;}


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < painters.size(); i++){
            g.setClip(getWidth()/painters.size() * i,0,getWidth()/painters.size(),getHeight());
            g.translate(getWidth()/painters.size() * i,0);
            painters.get(i).rendering(g);

            g.translate(-getWidth()/painters.size() * i,0);
        }

        Toolkit.getDefaultToolkit().sync();
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
            if (x < 0) x = areaWidth -1 ;
            if (x >= areaWidth)x= areaWidth-1;
            if (y < 0) y = areaHeight -1 ;
            if (y >= areaHeight) y = areaHeight-1;
            player.setAlive(true);
            Node node = getTile(x, y);
            player.checkAttack(node);
            player.setCurrentTile(node);



           if (node.getOwner() != player && player.getAlive()) {
               player.setContestedTiles(node);

            } else if (player.getContestedTiles().size() > 0) {
                player.contestToOwned();
                dfs(player);
            }

            // If BotPlayer is killed, add it to deadBots list
            if (player instanceof normalEnemy && !player.getAlive()) {
                looserEnemy.add(player);
            }
        }
        renewEnemy();

        boolean allKilled = true;

            mainPlayer.updateDirection();
            // Sets painter to stop drawing if humanPlayer is dead
            playerPainterHashMap.get(mainPlayer).setDraw(mainPlayer.getAlive());
            allKilled = allKilled && !mainPlayer.getAlive();

        if (allKilled) {
            endGame();
        }

// Remove dead players
        players.removeIf(p -> !p.getAlive());
    }


    private void endGame()
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.PINK);

        JOptionPane.showMessageDialog(this, panel, "YOU LOST!, GAME OVER...", JOptionPane.PLAIN_MESSAGE);
         actionListener.actionPerformed(new ActionEvent(this, 0, "GAME OVER"));
    }


    private void renewEnemy(){
        for(int i = 0; i < looserEnemy.size(); i++){
            if(looserEnemy.get(i).getAlive()){
                Player player = new normalEnemy(gameArea.length,gameArea[0].length,
                        new Color((int)(Math.random() * 0x1000000)));
                firstPlace(player);
                players.add(player);
                looserEnemy.remove(looserEnemy.get(i));
            }
        }
    }





    private void smooth(){
        smooth++;
        smooth %= finalSpeed;
    }


    private void dfs(Player player) {

        int maxX = 0;
        int minX = gameArea[0].length;
        int maxY = 0;
        int minY = gameArea.length;
        for (Node t : player.getOwnedTiles()) {
            if(t.getX() > maxX) maxX = t.getX();
            if(t.getX() < minX) minX = t.getX();
            if(t.getY() > maxY) maxY = t.getY();
            if(t.getY() < minY) minY = t.getY();
        }


        ArrayList<Node> outside = new ArrayList<>();
        ArrayList<Node> inside  = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();
        HashSet<Node> toCheck = new HashSet<>();


        int y;
        int x;
        for(Node t : player.getOwnedTiles()){
            y = t.getY();
            x = t.getX();
            if(y -1 >= 0) toCheck.add(gameArea[y-1][x]);
            if(y + 1 < gameArea.length) toCheck.add(gameArea[y+1][x]);
            if(x - 1 >= 0) toCheck.add(gameArea[y][x-1]);
            if(x + 1 < gameArea[y].length) toCheck.add(gameArea[y][x+1]);
        }



        for(Node t : toCheck){
            if(!inside.contains(t)){
                Stack<Node> stack = new Stack<>();
                boolean cont = true;
                Node v;
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


        for(Node t : inside){
            player.setOwnedTiles(t);
        }
    }


    void setPaused(Boolean b){
        paused = b;
    }


    int getAreaHeight() {
        return areaHeight;
    }

    int getAreaWidth() {
        return areaWidth;
    }


    int getSmooth() {
        return smooth;
    }

    int getTickReset() {
        return finalSpeed;
    }


    Node getTile(int x, int y){
        return gameArea[y][x];
    }


}