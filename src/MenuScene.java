import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuScene extends JPanel {
    private JTextField playerName;
    private JTextField speed;
    private JTextField enemyNum;
    private ActionListener actionListener;
    public String getPlayerName(){
        return playerName.getText();
    }
    public int getHeight(){
        return 500;
    }
    public int getWidth(){
        return 500;
    }
    public int getSpeed() {
        try {
            return Integer.parseInt(speed.getText());
        } catch (NumberFormatException e) {
            return 3;
        }
    }
    public int getEnemyNym(){
        try {
            return Integer.parseInt(enemyNum.getText());
        } catch (NumberFormatException e) {
            return 200;
        }

    }
    MenuScene(ActionListener actionListener){
        this.actionListener=actionListener;
        GridLayout gridLayout=new GridLayout(9,1);
        setLayout(gridLayout);
        setBackground(new Color(100,20,70));
        addWelcome();
        addButton();
        addTextField();


    }
    private void addWelcome(){
        JLabel welcome=new JLabel("WELCOME TO PAINT.IO");
        welcome.setFont(new Font("Arial",Font.BOLD,20));
        welcome.setForeground(Color.WHITE);
        welcome.setHorizontalAlignment(JLabel.CENTER);
        welcome.setVerticalAlignment(JLabel.CENTER);
        add(welcome);

    }
    private void addButton(){
        Color color=new Color(102,20,60);
        Color color1=new Color(102,40,75);
        JButton level1=new JButton("LEVEL 1");
        JButton level2=new JButton("LEVEL 2");
        JButton[] button={level1,level2};
        button[0].setBackground(color);
        button[1].setBackground(color1);
        for(int i=0;i<=1;i++){
            button[i].setFocusPainted(false);
            button[i].setSize(80,30);
            button[i].setForeground(Color.WHITE);
            button[i].addActionListener(actionListener);
            button[i].setFont(new Font("Arial",Font.BOLD,28));
            add(button[i]);

        }


    }
    private void addTextField(){
        JLabel playerNameLabel=new JLabel(" ENTER YOUR NAME: ");
        playerName=new JTextField();
        JLabel speedLabel=new JLabel(" ENTER THE SPEED: ");
        speed=new JTextField();
        JLabel enemyNumLabel= new JLabel("ENTER THE NUMBER OF ENEMies:");
        enemyNum=new JTextField();
        JLabel[] Jlabel={ playerNameLabel,speedLabel,enemyNumLabel};
        for(int i=0;i<=2;i++){
            Jlabel[i].setFont(new Font("Arial",Font.BOLD,16));
            Jlabel[i].setHorizontalAlignment(JLabel.CENTER);
            Jlabel[i].setVerticalAlignment(JLabel.CENTER);
            Jlabel[i].setForeground(Color.WHITE);
            add(Jlabel[i]);}
            JTextField[] JtextField={playerName,speed,enemyNum};
            JtextField[0].setBackground(new Color(100,30,50));
            JtextField[1].setBackground(new Color(100,30,50));
            JtextField[2].setBackground(new Color(100,30,50));
            JtextField[0].setForeground(Color.BLACK);
            JtextField[1].setForeground(Color.WHITE);
            JtextField[2].setForeground(Color.PINK);
            for(int j=0;j<=2;j++){
                JtextField[j].setFont(new Font("Arial",Font.BOLD,28));
                JtextField[j].setHorizontalAlignment(JTextField.CENTER);
                add(JtextField[j]);}
        }
    }




