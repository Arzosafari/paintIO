

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//lll
class PaintIo extends JFrame implements ActionListener{



    private JPanel panel;
    private Game gameScene;
    private MenuScene menuScene;
    private setting settingScene;
    ActionListener actionListener;



    private PaintIo(){
        setSize(500,500);
        setTitle("PAINT.IO");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
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

        switch (e.getActionCommand()) {
            case "LEVEL 1":
                if(menuScene.getPlayerName()==null){

                    JPanel panel = new JPanel();
                    panel.setBackground(Color.PINK);

                    JOptionPane.showMessageDialog(this, panel, "please write you name!!!", JOptionPane.PLAIN_MESSAGE);
                    actionListener.actionPerformed(new ActionEvent(this, 0, "GAME OVER"));
                }
                else {
                gameScene = new Game(this, menuScene.getPlayerName(),   menuScene.getGameSpeed(), menuScene.getEnemyNumber());
                panel.add(gameScene,"gameScene");
                cardLayout.show(panel,"gameScene");
                gameScene.setPaused(false);}
                break;
            case "LEVEL 2":
                gameScene = new Game(this,menuScene.getPlayerName(),   menuScene.getGameSpeed(), menuScene.getEnemyNumber(),true);
                panel.add(gameScene,"gameScene");
                cardLayout.show(panel,"gameScene");
                gameScene.setPaused(false);
                break;
            case "setting"  :
                settingScene=new setting(this);
                panel.add(settingScene,"settingScene");
                cardLayout.show(panel,"settingScene");

                break;
            case "menu":
                cardLayout.show(panel,"menuScene");
                break;

            case "GAME OVER":
                cardLayout.show(panel,"menuScene");
                gameScene.setPaused(true);
                break;
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(PaintIo::new);
    }

}