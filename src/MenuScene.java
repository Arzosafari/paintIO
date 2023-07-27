import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;
//llll
public class MenuScene extends JPanel {
    private JTextField playerName;
    private JSpinner Speed;
    private JSpinner enemyNum;
    private ActionListener actionListener;

    public String getPlayerName(){
        return playerName.getText();
    }

    public int getAreaHeight() {
        return 500;
    }
    public int getAreaWidth() {
        return 500;
    }
    public int getGameSpeed(){
        return Integer.valueOf(((JTextField)Speed.getEditor().getComponent(0)).getText());
    }
    public int getEnemyNumber(){
        return Integer.valueOf(((JTextField)enemyNum.getEditor().getComponent(0)).getText());
    }

    MenuScene(ActionListener actionListener){
        this.actionListener=actionListener;
        GridLayout gridLayout=new GridLayout(9,1);
        setLayout(gridLayout);
        setBackground(new Color(100,20,70));
        addWelcome();
        addButton();
        addTextField();
        addSpinner();
    }
    private void addWelcome(){
        JLabel welcome=new JLabel("WELCOME TO PAINT.IO");
        welcome.setFont(new Font("Arial",Font.ITALIC,20));
        welcome.setForeground(Color.WHITE);
        welcome.setHorizontalAlignment(JLabel.CENTER);
        welcome.setVerticalAlignment(JLabel.CENTER);
        add(welcome);

    }


    private void addButton() {
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





    private void addSpinner(){
        JLabel speedLabel=new JLabel("ENTER GAME SPEED(1,2,3,4,5): ");
        Speed=new JSpinner(new SpinnerNumberModel(2,1,5,1));
        JLabel botNumberLabel=new JLabel("ENTER NUMBER OF Enemies: ");
        enemyNum=new JSpinner(new SpinnerNumberModel(15,0,250,1));
        JLabel[] labels={speedLabel,botNumberLabel};
        JSpinner[] setSpinners={Speed,enemyNum};
        for(int i=0;i<labels.length;i++){
            labels[i].setFont(new Font("Arial",Font.BOLD,16));
            labels[i].setHorizontalAlignment(JLabel.CENTER);
            labels[i].setForeground(Color.BLACK);
            add(labels[i]);
        }
        for(JSpinner spinner:setSpinners){
            JTextField textField= (JTextField) spinner.getEditor().getComponent(0);
            spinner.setFont(new Font("Arial",Font.BOLD,16));
            textField.setBackground(Color.PINK);
            textField.setForeground(Color.BLACK);
            textField.setHorizontalAlignment(JTextField.CENTER);
            add(spinner);
        }

        }






    private void addTextField() {
        JLabel playerNameLabel=new JLabel(" ENTER YOUR NAME: ");
        playerName=new JTextField();
        playerNameLabel.setFont(new Font("Arial",Font.BOLD,16));
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playerNameLabel.setVerticalAlignment(JLabel.CENTER);
        playerNameLabel.setForeground(Color.WHITE);
        add(playerNameLabel);
        playerName.setBackground(new Color(100,30,50));
        playerName.setForeground(Color.BLACK);
        playerName.setFont(new Font("Arial",Font.BOLD,28));
        playerName.setHorizontalAlignment(JTextField.CENTER);
        add(playerName);

    }

    }