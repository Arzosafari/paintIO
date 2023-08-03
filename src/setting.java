import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class setting extends JPanel {
    ActionListener actionListener;

    setting(ActionListener actionListener) {
        this.actionListener = actionListener;
        setBackground(Color.PINK);

        GridLayout gridLayout = new GridLayout(12,1);
        setLayout(gridLayout);
        setLabels();
        displayFileContents("scoreFile.txt");
        addColorLabel();
        chooseColor();
        addExitButton();

    }


    public void displayFileContents(String fileName) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.ITALIC, 16));
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            reader.close();
            System.out.println("File contents displayed: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        this.add(scrollPane);
    }

    private void setLabels() {
        JLabel label = new JLabel("your record:");
        label.setFont(new Font("Arial", Font.ITALIC, 40));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        add(label);
    }
    private void chooseColor(){

        Color color=new Color(102,20,60);
        Color color1=new Color(102,40,75);
        Color color2=new Color(102,50,100);
        Color color3=new Color(90,60,90);
        Color color4=new Color(86,45,80);
        Color color5=new Color(53,30,86);
        Color color6=new Color(98,50,60);
        Color color7=new Color(102,50,30);
        JButton button=new JButton();
        JButton button1=new JButton();
        JButton button2=new JButton();
        JButton button3=new JButton();
        JButton button4=new JButton();
        JButton button5=new JButton();
        JButton button6=new JButton();
        JButton button7=new JButton();
        Color []colors={color,color1,color2,color3,color4,color5,color6,color7};
        JButton[] buttons={button,button1,button2,button3,button4,button5,button6,button7};
        for(int i=0;i<buttons.length;i++){
            buttons[i].setBackground(colors[i]);
            buttons[i].setFocusPainted(false);
            buttons[i].setSize(80,30);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].addActionListener(actionListener);
            buttons[i].setFont(new Font("Arial",Font.BOLD,28));
            add(buttons[i]);
            int finalI = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Game.color=colors[finalI];
                    actionListener.actionPerformed(new ActionEvent(this, 0, "menu"));
                }
            });

        }

    }
    private void addColorLabel(){
        JLabel label=new JLabel("choose the color for your player");
        label.setFont(new Font("Arial",Font.ITALIC,20));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        add(label);

    }
    private void addExitButton(){
        JButton button=new JButton("EXIT");
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setSize(80,30);
        button.setForeground(Color.WHITE);
        button.addActionListener(actionListener);
        button.setFont(new Font("Arial",Font.BOLD,28));
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(new ActionEvent(this, 0, "menu"));

            }
        });

    }

}