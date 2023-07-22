import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {
    private JPanel panel;
    private Game gameScene;
    private MenuScene menuScene;

    private SCENE s;
    private Main(){
        setSize(500,500);
        setTitle("PAINT.IO");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        addMenu();

    }

    private void addMenu(){
        menuScene=new MenuScene(this);
        CardLayout cardLayout=new CardLayout();
        panel=new JPanel(cardLayout);
        panel.add(menuScene,"menuScene");
        add(panel);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) panel.getLayout();
        switch (e.getActionCommand()){
            case "LEVEL 1":
                gameScene=new Game();
                panel.add(gameScene,"gameScene");
                cardLayout.show(panel,"gameScene");
                break;
            case "LEVEL 2":
                gameScene=new Game();
                panel.add(gameScene,"gameScene");
                cardLayout.show(panel,"gameScene");
                break;
            case "GAME OVER":
                cardLayout.show(panel,"menuScene");
                break;

        }
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(Main::new);
    }
}
