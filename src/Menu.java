import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Menu extends JPanel {
    private JTextField player1Name;
    private JTextField player2Name;
    private JSpinner areaHSpinner;
    private JSpinner areaWSpinner;
    private JSpinner gameSpeedSpinner;
    private JSpinner botNumberSpinner;
    private ActionListener actionListener;
    private String[] name = {"Emma", "Olivia", "Ava", "Sophia", "Isabella", "Mia", "Charlotte", "Amelia", "Harper", "Evelyn", "Abigail", "Emily", "Elizabeth", "Mila", "Ella", "Avery", "Scarlett", "Sofia", "Chloe", "Victoria", "Layla", "Madison", "Grace", "Zoey", "Penelope", "Nora", "Riley", "Lily", "Eleanor", "Hannah", "Lila", "Aria", "Aubrey", "Ellie", "Stella", "Hazel", "Maya", "Aurora", "Natalie", "Emilia", "Addison", "Brooklyn", "Samantha", "Leah", "Audrey", "Caroline", "Savannah", "Genesis", "Piper", "Arianna", "Valentina", "Naomi", "Bella", "Paisley", "Clara", "Violet", "Nova", "Kennedy", "Madelyn", "Skylar", "Alice", "Cora", "Ruby", "Ivy", "Ariana", "Isabelle", "Jasmine", "Eva", "Everly", "Autumn", "Sadie", "Gianna", "Willow", "Quinn", "Serenity", "Peyton", "Madeline", "Gabriella", "Lydia", "Adelaide", "Madilyn", "Adeline", "Makenzie", "Jade", "Nevaeh", "Adalyn", "Aurora", "Melody", "Julia", "Isla", "Aubree", "Kinsley", "Kaylee", "Raelynn", "Ariel", "Jordyn", "Aylin", "Mariah", "Giselle", "Amara", "Elaina", "Everleigh", "Phoebe", "Harley", "Brielle", "Angelina", "Genesis", "Gia", "Alexandria", "Dahlia", "Laylah", "Zara", "Athena", "Kaitlyn", "Harmony", "Ximena", "Kali", "Valeria", "Arielle", "Hope", "Hayley", "Emery", "Leilani", "Aspen", "Jade", "Lilith", "Poppy", "Amaya", "Mckenna", "Rosalind", "Leila", "Saylor", "Brynlee", "Analia", "Alondra", "Aniyah", "Tiana", "Milana", "Kinslee", "Celeste", "Mabel", "Alanna", "Briar", "Leighton", "Elsie", "Magnolia", "Ainsley"};
    Menu(ActionListener actionListener){
        this.actionListener=actionListener;
        setBackground(Color.PINK);
        GridLayout gridLayout=new GridLayout(8,2);
        setLayout(gridLayout);
        addComponents();
    }

    private void addComponents() {
        add(new JLabel(" "));
        add(new JLabel(" "));
        JButton playBtn=new JButton("play single");
        JButton playMultiBtn=new JButton("play Multi");
        JButton[] button={playBtn,playMultiBtn};
        for(JButton button1:button){
            button1.setSize(90,25);
            button1.addActionListener(actionListener);
            button1.setFont(new Font("Arial", Font.BOLD, 30));
            button1.setBackground(Color.MAGENTA);
            button1.setBackground(Color.BLACK);
            button1.setFocusPainted(false);
            button1.setBorderPainted(true);
            add(button1);
        }
        JLabel player1=new JLabel("PLAYER 1 NAME:");
        JLabel player2=new JLabel("PLAYER 2 NAME:");
        JLabel[] labels={player1,player2};
        for(JLabel label:labels){
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(Color.BLACK);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            add(label);
        }
        player1Name=new JTextField(name[new Random().nextInt(name.length)]);
        player2Name=new JTextField(name[new Random().nextInt(name.length)]);
        JTextField[] textFields={player1Name,player2Name};
        for(JTextField textField:textFields){
            textField.setFont(new Font("Arial", Font.BOLD, 30));
            textField.setBackground(Color.MAGENTA);
            textField.setForeground(Color.BLACK.brighter());
            textField.addMouseListener(new FieldMouseListener(textField));
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.setCaretColor(Color.WHITE);
            add(textField);

        }
        JLabel areaHLabel=new JLabel("ENTER GAME AREA HEIGHT: " );
        areaHSpinner=new JSpinner(new SpinnerNumberModel(300,25,500,5));
        JLabel areaWLabel=new JLabel("ENTER GAME AREA WIDTH: ");
        areaWSpinner=new JSpinner(new SpinnerNumberModel(300,25,500,50));
        JLabel speedLabel=new JLabel("ENTER GAME SPEED(1,2,3,4,5): ");
        gameSpeedSpinner=new JSpinner(new SpinnerNumberModel(3,1,5,1));
        JLabel botNumberLabel=new JLabel("ENTER NUMBER OF BOTS: ");
        botNumberSpinner=new JSpinner(new SpinnerNumberModel(10,0,25,1));
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
      class FieldMouseListener implements MouseListener {
        private boolean changed=false;
        private JTextField textField;
        public FieldMouseListener(JTextField textField){
            this.textField=textField;
        }
          @Override
          public void mouseClicked(MouseEvent e) {

          }

          @Override
          public void mousePressed(MouseEvent e) {
            if(!changed){
                textField.setText("");
                textField.setForeground(Color.WHITE);
            }
            changed=true;

          }

          @Override
          public void mouseReleased(MouseEvent e) {

          }

          @Override
          public void mouseEntered(MouseEvent e) {

          }

          @Override
          public void mouseExited(MouseEvent e) {

          }
      }

    }

