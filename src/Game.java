import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.TimerTask;
import java.util.Timer;


public class Game extends JPanel {
    private Node[][] gameArea;
    static Color color=new Color(102,60,90);

    private boolean canCallMethod = true;
    private Timer timer;

    private int bullet=4;

    private boolean hard;

    private ActionListener actionListener;
    MouseEvent e;

    private int areaHeight=500;
    private int areaWidth=500;

    private int smooth = 0;
    private final int finalSpeed;
    private int enemyNumber;
    private ArrayList<Player> players = new ArrayList<>();
    private MainPlayer mainPlayer;
    private HashMap<Node, Player> tilePlayerHashMap = new HashMap<>();

    private ArrayList<Player> looserEnemy = new ArrayList<>();
    private ArrayList<normalEnemy> normalEnemies=new ArrayList<>();
    private boolean paused = true;

    private ArrayList<Painter> painters = new ArrayList<>();
    private HashMap<Player, Painter> playerPainterHashMap = new HashMap<>();
    void setColor(Color color){
        this.color=color;
    }
    private Color getColor(){
        return color;
    }
    Game(ActionListener actionListener, String p1name,int gameSpeed, int enemyNumber,boolean hard){
        setSize(1000,1000);
        this.actionListener=actionListener;
        this.hard=true;
        this.enemyNumber=enemyNumber;
        int[] speeds = {10, 8, 6,4,2};
        finalSpeed=speeds[gameSpeed-1];
        players.add(mainPlayer=new MainPlayer(areaHeight, areaWidth,color, p1name));
        gameBoard();
        addButton();
        registerArrowKeyInputs();
        addSmartEnemy();
        setBackground(Color.PINK);
        timer();
        addPausedButton();
        addunpausedButton();
        painters.add(new Painter( this, mainPlayer, players));
        playerPainterHashMap.put(mainPlayer, painters.get(0));


    }



    Game(ActionListener actionListener, String p1name,int gameSpeed, int enemyNumber){
        setSize(1000,1000);

        timer = new Timer();
        timer.schedule(new MyTask(), 0, 3000);
        this.actionListener = actionListener;
        this.enemyNumber = enemyNumber;
        int[] speeds = {12, 10, 8, 6, 4};
        finalSpeed = speeds[gameSpeed - 1];
        this.hard=false;
        players.add(mainPlayer=new MainPlayer(areaHeight, areaWidth,color, p1name));
        gameBoard();
        addButton();
        timer();
        movePlayerByMouse(mainPlayer);
        registerArrowKeyInputs();
        addNormalEnemy();
        setBackground(Color.PINK);
        addPausedButton();
        addunpausedButton();


        painters.add(new Painter( this, mainPlayer, players));
        playerPainterHashMap.put(mainPlayer, painters.get(0));
    }
    private void addPausedButton(){
        JButton button=new JButton("stop");
        button.setBackground(new Color(102,20,80));
        button.setFocusPainted(false);
        button.setSize(80,30);
        button.setForeground(Color.WHITE);
        button.addActionListener(actionListener);
        button.setFont(new Font("Arial",Font.BOLD,28));
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              setPaused(true);

            }
        });
    }
    private void addunpausedButton(){
        JButton button=new JButton("resume");
        button.setBackground(new Color(102,30,60));
        button.setFocusPainted(false);
        button.setSize(80,30);
        button.setForeground(Color.WHITE);
        button.addActionListener(actionListener);
        button.setFont(new Font("Arial",Font.BOLD,28));
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPaused(false);

            }
        });


    }
    private void gameBoard(){
        this.gameArea =  new Node[areaHeight][areaWidth];
        for(int i = 0; i < gameArea.length; i++) {
            for (int j = 0; j < gameArea[i].length; j++) {
                gameArea[i][j] = new Node(j, i);

            }

        }
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
    private void weaponB(MainPlayer currentPlayer,Node[][] nodes){
        int x = currentPlayer.getX();
        int y = currentPlayer.getY();
        if(canCallMethod){
            if(currentPlayer.getDirection()==Player.NORTH){
                for(int i=y+1;i<=areaHeight;i++){
                    if(!hard){
                        for(normalEnemy player:normalEnemies){
                            if(player.getX()==x&&player.getY()==i){
                                player.die();
                                JPanel panel = new JPanel();
                                panel.setBackground(Color.PINK);

                                JOptionPane.showMessageDialog(this, panel, "you killed one enemy by weapon B", JOptionPane.PLAIN_MESSAGE);

                            }

                        }
                    }
                }
                canCallMethod=false;
            }if(currentPlayer.getDirection()==Player.SOUTH){
                for(int i=y+1;i>0;i--){
                    if(!hard){
                        for(normalEnemy player:normalEnemies){
                            if(player.getX()==x&&player.getY()==i){
                                player.die();
                                JPanel panel = new JPanel();
                                panel.setBackground(Color.PINK);

                                JOptionPane.showMessageDialog(this, panel, "you killed one enemy by weapon B", JOptionPane.PLAIN_MESSAGE);

                            }
                        }
                    }

                }
                canCallMethod=false;
            }if(currentPlayer.getDirection()==Player.EAST){
                for(int i=x+1;i<areaWidth;i++){
                    if(!hard){
                        for(normalEnemy player:normalEnemies)
                            if(player.getX()==i&&player.getY()==y){
                                player.die();
                                JPanel panel = new JPanel();
                                panel.setBackground(Color.PINK);

                                JOptionPane.showMessageDialog(this, panel, "you killed one enemy by weapon B", JOptionPane.PLAIN_MESSAGE);

                            }
                    }
                }
                canCallMethod=false;
            }
            if(currentPlayer.getDirection()==Player.WEST){
                for(int i=x+1;i<areaWidth;i++){
                    if(!hard){
                        for (normalEnemy player:normalEnemies){
                            if (player.getX()==i&&player.getY()==y){

                                player.die();
                                JPanel panel = new JPanel();
                                panel.setBackground(Color.PINK);

                                JOptionPane.showMessageDialog(this, panel, "you killed one enemy by weapon B", JOptionPane.PLAIN_MESSAGE);

                            }
                        }
                    }

                }
            }
            canCallMethod=false;
        }
        else {
            JPanel panel = new JPanel();
            panel.setBackground(Color.PINK);

            JOptionPane.showMessageDialog(this, panel, "you cant use weapon B wait please!!! ", JOptionPane.PLAIN_MESSAGE);

        }

    }
    private void addButton(){
        JButton button=new JButton("EXIT");
        button.setBackground(Color.MAGENTA);
        button.setFocusPainted(false);
        button.setSize(80,30);
        button.setForeground(Color.WHITE);
        button.addActionListener(actionListener);
        button.setFont(new Font("Arial",Font.ITALIC,28));
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(new ActionEvent(this, 0, "menu"));

            }
        });

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
            normalEnemies.add(new normalEnemy(gameArea.length,gameArea[0].length,
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
                normalEnemies.add(new normalEnemy(gameArea.length,gameArea[0].length,
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
                mainPlayer.setDirection(KeyEvent.VK_UP);
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
                mainPlayer.setDirection(KeyEvent.VK_DOWN);
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
                mainPlayer.setDirection(KeyEvent.VK_LEFT);
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
                mainPlayer.setDirection(KeyEvent.VK_RIGHT);
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
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0), "weaponB");
        getActionMap().put("weaponB", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                weaponB(mainPlayer,gameArea);
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
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // Compute the distance and direction of the mouse drag
                int distX = e.getX() - player.getX();
                int distY = e.getY() - player.getY();

                // Determine the direction of movement based on the distance
                if (Math.abs(distX) > Math.abs(distY)) {
                    // Move left or right
                    if (distX < 0) {
                        ((MainPlayer) player).setDirection(KeyEvent.VK_LEFT);
                        player.setX(player.getX() - finalSpeed);
                    } else {
                        ((MainPlayer) player).setDirection(KeyEvent.VK_RIGHT);
                        player.setX(player.getX() + finalSpeed);
                    }
                } else {
                    // Move up or down
                    if (distY < 0) {
                        ((MainPlayer) player).setDirection(KeyEvent.VK_UP);
                        player.setY(player.getY() - finalSpeed);
                    } else {
                        ((MainPlayer) player).setDirection(KeyEvent.VK_DOWN);
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
        drawEnemyNum(g);
        drawScore(g,gameArea);
    }
    private void drawScore(Graphics g, Node[][] nodes) {
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        FontMetrics fontMetrics = g.getFontMetrics();
        int fontHeight = fontMetrics.getHeight();
        int barWidth;
        int barHeight = fontHeight + 4;

        Color color;

        int nodesOwned = 0;
        int nodesContested = 0;
        for (int row = 0; row < nodes.length; row++) {
            for (int col = 0; col < nodes[row].length; col++) {
                Node node = nodes[row][col];
                if (node.getOwner() == mainPlayer) {
                    nodesOwned++;}
                else if (node.getContestedOwner() ==mainPlayer) {
                    nodesContested++;
                }
            }
        }
        String StringOwnedNodes= String.valueOf(nodesOwned);
        checkNumber(nodesOwned);
        //saveToFile("scoreFile.txt",StringOwnedNodes);



        String string = String.format("Nodes Owned:  %d", nodesOwned);
        color = Color.MAGENTA;

        barWidth = getWidth() / 4;
        g.setColor(color);
        g.fillRect(getWidth() - barWidth, 0, barWidth, barHeight);
        if (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue() < 127) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawString(string, 2 + getWidth() - barWidth, fontHeight + 4);
    }

    private void checkNumber(int num) {
        String filePath = "scoreFile.txt";
        int newNumber = num; // Number to compare with the file content

        try {
            // Read the file contents
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String fileContent = reader.readLine();
            reader.close();

            // Parse the file content as a number
            int savedNumber = Integer.parseInt(fileContent);

            // Compare the numbers
            if (newNumber > savedNumber) {
                // Update the file with the new number
                FileWriter writer = new FileWriter(filePath);
                writer.write(String.valueOf(newNumber));
                writer.close();

                System.out.println("New number saved successfully!");
            } else {
                System.out.println("The new number is not larger than the saved number.");
            }

        } catch (IOException e) {
            System.err.println("An error occurred while reading or writing the file.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("The file content is not a valid number.");
            e.printStackTrace();
        }
    }

    private void drawEnemyNum(Graphics g) {
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        FontMetrics fontMetrics = g.getFontMetrics();
        int fontHeight = fontMetrics.getHeight();
        int barWidth;
        int barHeight = fontHeight + 4;

        Color color;

        int enemiesRemaining = 0;
        for (Player player : players) {
            if (player instanceof normalEnemy) {
                enemiesRemaining++;
            }
        }

        String string = String.format("Enemies Remaining: %d", enemiesRemaining);

        barWidth = getWidth() / 4;
        color = new Color(102, 40, 50);
        g.setColor(color);
        g.fillRect(getWidth() - barWidth, barHeight + 4, barWidth, barHeight);
        if (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue() < 127) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawString(string, getWidth() - barWidth + 2, fontHeight + barHeight + 6);
    }
    private void logic(){
        Player player;
        tilePlayerHashMap.clear();
        if(mainPlayer.getX()>=getAreaWidth()||mainPlayer.getY()>=getAreaHeight()){
            for(int i=getAreaWidth();i<=mainPlayer.getX();i++){
                for(int j=getAreaHeight();j<=mainPlayer.getY();j++){
                    gameArea[i][j]=new Node(i,j);

                }
            }
            areaHeight=Math.max(mainPlayer.getX(),mainPlayer.getY())+1;
            areaWidth=Math.max(mainPlayer.getX(),mainPlayer.getY())+1;

        }
        for (int i = 0; i < players.size(); i++) {
            player = players.get(i);
            player.move();
            int x = player.getX();
            int y = player.getY();
            // unlimited part
            if(player.getX() < 0 || player.getX() >= areaWidth || player.getY() < 0 || player.getY() >= areaHeight){
                for(int m=getAreaWidth();m<=mainPlayer.getX();m++){
                    for(int j=getAreaHeight();j<=mainPlayer.getY();j++){
                        gameArea[m][j]=new Node(m,j);
                        areaWidth+=25;
                        areaHeight+=25;


                    }
                }
                player.die();

            }

            else {
                Node node = getTile(x, y);
                player.checkAttack(node);
                player.setCurrentTile(node);
                findCollision(player, node);


                if (node.getOwner() != player && player.getAlive()) {
                    player.setContestedTiles(node);

                } else if (player.getContestedTiles().size() > 0) {
                    player.contestToOwned();
                    dfs(player);
                }
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
                normalEnemy player = new normalEnemy(gameArea.length,gameArea[0].length,
                        new Color((int)(Math.random() * 0x1000000)));

                firstPlace(player);
                players.add(player);
                normalEnemies.add(player);
                looserEnemy.remove(looserEnemy.get(i));
            }
        }
    }





    private void smooth(){
        smooth++;
        smooth %= finalSpeed;
    }
    private void findCollision(Player player,Node tile){
        if(tilePlayerHashMap.containsKey(tile)){
            for(Map.Entry<Node, Player> entry : tilePlayerHashMap.entrySet()) {
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

        }else{
            tilePlayerHashMap.put(tile, player);
        }
        players.removeIf(p -> !p.getAlive());

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
    private class MyTask extends TimerTask {
        public void run() {
            canCallMethod = true;
        }
    }


}