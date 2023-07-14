import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {
    private Board board;
    private Menu menu;
    private JPanel card;
    private Main(){
        initUi();
    }
    private void initUi(){
        setSize(1000,1000);
        setResizable(true);
        setVisible(true);
        setTitle("Paint_io");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//center the frame
        menu=new Menu(this);
        card=new JPanel(new CardLayout());
        card.add(menu,"menu");
        add(card);
    }
    private enum STATES{
        GAME,MENU

    }
    private void setState(STATES s){
        CardLayout cardLayout=(CardLayout) card.getLayout();
        if(s==STATES.GAME){
            cardLayout.show(card,"board");
            board.setPaused(false);
        }else if(s==STATES.MENU){
            cardLayout.show(card,"menu");
            board.setPaused(true);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "play single":
                board=new Board(this,menu.getP1Name(),menu.getAreaHeight(),menu.getAreaWidth(),menu.getGameSpeed(),menu.getBotNumber());
                card.add(board,"board");
                setState(STATES.GAME);
                break;
            case "play Multi":
                board=new Board(this,menu.getP1Name(),menu.getP2Name(),menu.getAreaHeight(),menu.getAreaWidth(),menu.getGameSpeed(),menu.getBotNumber());
                card.add(board,"board");
                setState(STATES.GAME);
                break;
            case" End Game":
                setState(STATES.MENU);
                break;
        }

    }
    public static void main(String[]args){
        EventQueue.invokeLater(Main::new);
    }
}
