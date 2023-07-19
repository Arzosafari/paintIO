import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;

public class Menu extends JPanel {
    private JTextField player1Name;
    private JTextField player2Name;
    private JSpinner areaHSpinner;
    private JSpinner areaWSpinner;
    private JSpinner gameSpeedSpinner;
    private JSpinner botNumberSpinner;
    private ActionListener actionListener;
    public String getP1Name() {

        return player1Name.getText();
    }
    public String getP2Name(){

        return player2Name.getText();
    }
    public int getAreaHeight() {
        return Integer.valueOf(((JTextField)areaHSpinner.getEditor().getComponent(0)).getText());
    }
    public int getAreaWidth() {
        return Integer.valueOf(((JTextField)areaHSpinner.getEditor().getComponent(0)).getText());
    }
    public int getGameSpeed(){
        return Integer.valueOf(((JTextField)gameSpeedSpinner.getEditor().getComponent(0)).getText());
    }
    public int getBotNumber(){
        return Integer.valueOf(((JTextField)botNumberSpinner.getEditor().getComponent(0)).getText());
    }

    private String[] name = {"Emma", "Olivia", "Ava", "Sophia", "Isabella", "Mia", "Charlotte", "Amelia", "Harper", "Evelyn", "Abigail", "Emily", "Elizabeth", "Mila", "Ella", "Avery", "Scarlett", "Sofia", "Chloe", "Victoria", "Layla", "Madison", "Grace", "Zoey", "Penelope", "Nora", "Riley", "Lily", "Eleanor", "Hannah", "Lila", "Aria", "Aubrey", "Ellie", "Stella", "Hazel", "Maya", "Aurora", "Natalie", "Emilia", "Addison", "Brooklyn", "Samantha", "Leah", "Audrey", "Caroline", "Savannah", "Genesis", "Piper", "Arianna", "Valentina", "Naomi", "Bella", "Paisley", "Clara", "Violet", "Nova", "Kennedy", "Madelyn", "Skylar", "Alice", "Cora", "Ruby", "Ivy", "Ariana", "Isabelle", "Jasmine", "Eva", "Everly", "Autumn", "Sadie", "Gianna", "Willow", "Quinn", "Serenity", "Peyton", "Madeline", "Gabriella", "Lydia", "Adelaide", "Madilyn", "Adeline", "Makenzie", "Jade", "Nevaeh", "Adalyn", "Aurora", "Melody", "Julia", "Isla", "Aubree", "Kinsley", "Kaylee", "Raelynn", "Ariel", "Jordyn", "Aylin", "Mariah", "Giselle", "Amara", "Elaina", "Everleigh", "Phoebe", "Harley", "Brielle", "Angelina", "Genesis", "Gia", "Alexandria", "Dahlia", "Laylah", "Zara", "Athena", "Kaitlyn", "Harmony", "Ximena", "Kali", "Valeria", "Arielle", "Hope", "Hayley", "Emery", "Leilani", "Aspen", "Jade", "Lilith", "Poppy", "Amaya", "Mckenna", "Rosalind", "Leila", "Saylor", "Brynlee", "Analia", "Alondra", "Aniyah", "Tiana", "Milana", "Kinslee", "Celeste", "Mabel", "Alanna", "Briar", "Leighton", "Elsie", "Magnolia", "Ainsley"};
    Menu(ActionListener actionListener){
        this.actionListener=actionListener;
        setBackground(Color.PINK);
        GridLayout gridLayout=new GridLayout(8,2);
        setLayout(gridLayout);
        addButton();
        addLabel();
        addTextField();
        addSpinner();
    }


    private void addLabel() {

        JLabel player1 = new JLabel("PLAYER 1 NAME:");
        JLabel player2 = new JLabel("PLAYER 2 NAME:");
        JLabel[] labels = {player1, player2};
        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(Color.BLACK);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            add(label);
        }
    }

    private void addButton() {
        JButton playBtn = new JButton("play single");
        JButton playMultiBtn = new JButton("play Multi");
        JButton[] button = {playBtn, playMultiBtn};
        for (JButton button1 : button) {
            button1.setSize(90, 25);
            button1.addActionListener(actionListener);
            button1.setFont(new Font("Arial", Font.BOLD, 30));
            button1.setBackground(Color.MAGENTA);
            button1.setBackground(Color.BLACK);
            button1.setFocusPainted(false);
            button1.setBorderPainted(true);
            add(button1);
        }
    }





    private void addSpinner(){
        JLabel areaHLabel=new JLabel("ENTER GAME AREA HEIGHT: " );
        areaHSpinner=new JSpinner(new SpinnerNumberModel(300,25,500,50));
        JLabel areaWLabel=new JLabel("ENTER GAME AREA WIDTH: ");
        areaWSpinner=new JSpinner(new SpinnerNumberModel(300,25,500,50));
        JLabel speedLabel=new JLabel("ENTER GAME SPEED(1,2,3,4,5): ");
        gameSpeedSpinner=new JSpinner(new SpinnerNumberModel(2,1,5,1));
        JLabel botNumberLabel=new JLabel("ENTER NUMBER OF BOTS: ");
        botNumberSpinner=new JSpinner(new SpinnerNumberModel(15,0,25,1));
        JLabel[] setLabels={areaHLabel,areaWLabel,speedLabel,botNumberLabel};
        JSpinner[] setSpinners={areaHSpinner,areaWSpinner,gameSpeedSpinner,botNumberSpinner};
        for(JLabel label:setLabels){
            label.setFont(new Font("Arial",Font.BOLD,16));
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.RIGHT);
        }
        for(JSpinner spinner:setSpinners){
            JTextField textField= (JTextField) spinner.getEditor().getComponent(0);
            spinner.setFont(new Font("Arial",Font.BOLD,16));
            textField.setBackground(Color.MAGENTA);
            textField.setForeground(Color.WHITE);
            textField.setHorizontalAlignment(JTextField.CENTER);}
        JComponent[]setComponent={areaHLabel,areaHSpinner,areaWLabel,areaWSpinner,speedLabel,gameSpeedSpinner,botNumberLabel,botNumberSpinner};
        for(JComponent component:setComponent){
            add(component);
        }
    }





    private void addTextField() {
        player1Name = new JTextField(name[new Random().nextInt(name.length)]);
        player2Name = new JTextField(name[new Random().nextInt(name.length)]);
        JTextField[] textFields = {player1Name, player2Name};
        for (JTextField textField : textFields) {
            textField.setFont(new Font("Arial", Font.BOLD, 30));
            textField.setBackground(Color.MAGENTA);
            textField.setForeground(Color.BLACK.brighter());
            textField.setHorizontalAlignment(JTextField.CENTER);
            add(textField);

        }
    }

    }