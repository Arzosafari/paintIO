import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JPanel {
    private Node[][] gameArea;
    private ActionListener actionListener;
    private  final int height=500;
    private final int width=500;
    private final int measure=28;
    private static int smooth=0;

    private final int FinalSpeed;
    private int enemyNumber;
    private ArrayList<Player> players=new ArrayList<>();
    private MainPlayer mainPlayer;
    private HashMap<Node,Player> NodePlayer=new HashMap<Node,Player>();
    private ArrayList<Player> looserEnemies=new ArrayList<>();
    private ArrayList<Draw> draw=new ArrayList<>();
    private HashMap<Player,Draw> playerDraw=new HashMap<>();
     int getSmooth(){
        return smooth;
    }
    int getFinalSpeed(){
         return FinalSpeed;
    }
    Game(){

    }
    Game(ActionListener actionListener,String playerName,int speed1,int enemyNumber){
        this.actionListener=actionListener;
        int[] speed={14,12,10,8,6,4,2};
        this.FinalSpeed=speed[speed1-1];
        this.enemyNumber=enemyNumber;
        mainPlayer=new MainPlayer();
        players.add(mainPlayer);
        this.gameArea=new Node[500][500];
        for(int i=0;i<500;i++){
            for(int j=0;j<gameArea[i].length;j++){
                gameArea[i][j]=new Node(j,i);
            }
        }
        keyEvent();
        addNormalEnemy();
        setBackground(Color.PINK);
        FixedTime();





    }
    private void FixedTime(){
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
               smooth();
               if (smooth==0)
                   logic();
               reStart();
            }
        };
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(timerTask,0,1000/60);
    }
    private void addNormalEnemy(){

        for(int i=0;i<enemyNumber;i++){
            int randR=(int)Math.random()*100;
            int randG=(int)Math.random()*100;
            int randB=(int)Math.random()*100;
            Color color=new Color(randR,randG,randB);
            players.add(new normalEnemy(color));}
            for(int i=0;i<players.size();i++){
                int randR=(int)Math.random()*100;
                int randG=(int)Math.random()*100;
                int randB=(int)Math.random()*100;
                if(close(players.get(i))){
                    players.remove(players.get(i));
                    i--;
                    players.add(new normalEnemy(new Color(randR,randG,randB)));
                }else
                    firstPlaceForNormalLevel(players.get(i));

            }


    }
    private void keyEvent(){
        InputMap inputMap=getInputMap(2);
        ActionMap actionMap=getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0),"left");
        AbstractAction action=new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.setId(37);//left
            }
        };
        actionMap.put("left",action);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0),"right");
        AbstractAction action1=new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.setId(39);//right
            }
        };
        actionMap.put("right",action1);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0),"up");
        AbstractAction action2=new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.setId(40);
            }
        };
        actionMap.put("up",action2);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0),"down");
        AbstractAction action3=new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.setId(38);
            }
        };
        actionMap.put("down",action3);

    }
    static void smooth(){}
    static void reStart(){}
    private void logic(){
         Player player;
         NodePlayer.clear();
         int height=500;
         int width=500;
         for(int i=0;i<players.size();i++){
             players.get(i).move();
             int x=players.get(i).getX();
             int y=players.get(i).getY();
             //unlimited part
             if(player.getX()<0)
                 x=500

         }

    }
    private void firstPlaceForNormalLevel(Player player){
        if(close(player)){
            Player player1=new normalEnemy(player.getColor());
            firstPlaceForNormalLevel(player1);
        }
        for (int i=player.getX()-2;i<=player.getY()+2;i++){
            for(int j=player.getY()-2;i<=player.getY()+2;j++){
            player.setDarkNodes(getNode(i,j));
            }
        }


    }
    private boolean close(Player player){
        for(int i=player.getX()-4;i<=player.getX()+4;i++){
            for(int j=player.getY()-4;j<=player.getY()+4;j++){
                if(getNode(i,j).getNotCompleteOwner()!=null||getNode(i,j).getOwner()!=null){
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i=0;i<draw.size();i++){
            g.setClip(500/draw.size()*i,0,500/draw.size(),500);
            g.translate(500/draw.size()*i,0);
            draw.get(i).GraphicRender(g);
            g.translate(-500/draw.size()*i,0);
            Toolkit.getDefaultToolkit().sync();
        }
    }
    Node getNode(int x,int y){
        return gameArea[x][y];
    }



}
