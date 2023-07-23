import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class Game extends JPanel {
    private Node[][] gameArea;
    private ActionListener actionListener;
    private  final int height=500;
    private final int width=500;
    private final int measure=28;
    private int smooth=0;
    private final int FinalSpeed;
    private int enemyNumber;
    private ArrayList<Player> players=new ArrayList<>();
    private Player mainPlayer;
    private HashMap<Node,Player> NodePlayer=new HashMap<>();
    private ArrayList<Player> looserEnemies=new ArrayList<>();
    private ArrayList<Draw> draw=new ArrayList<>();
    private HashMap<Player,Draw> playerDraw=new HashMap<>();
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
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new Task(),0,1000/60);
    }
    private void addNormalEnemy(){


        for(int i=0;i<enemyNumber;i++){
            int randR=(int)Math.random()*100;
            int randG=(int)Math.random()*100;
            int randB=(int)Math.random()*100;
            players.add(new normalEnemy());

        }
    }



}
