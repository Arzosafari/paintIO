import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * PaperIO is the main class used to start window and keep track of current state and switch between states.
 */
class PaintIo extends JFrame implements ActionListener{



    private JPanel card;
    private Board board;
    private Menu menu;

    /**
     * Initializes a new occurrence of game
     */
    private PaintIo(){
        initializeUI();
    }

    /**
     * Specifies size, title and layout etc for game
     */
    private void initializeUI(){
        setSize(1000, 1000);
        setResizable(true);
        setVisible(true);
        setTitle("pant.io");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        menu = new Menu(this);
        card = new JPanel(new CardLayout());
        card.add(menu, "menu");

        add(card);
    }

    /**
     * Enum with all possible game states
     */
    private enum STATES{
        GAME,
        MENU
    }

    /**
     * Sets game state to specified state
     * @param s STATE game should be set to
     */


    /**
     * Reacts to game actions such as game start and game paused
     * @param e event to react to
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "play single":
                board = new Board(this, menu.getP1Name(), menu.getAreaHeight(), menu.getAreaWidth(), menu.getGameSpeed(), menu.getBotNumber());
                card.add(board, "board");
                setState(STATES.GAME);
                break;
            case "play Multi":
                board = new Board(this, menu.getP1Name(), menu.getP2Name(), menu.getAreaHeight(), menu.getAreaWidth(), menu.getGameSpeed(), menu.getBotNumber());
                card.add(board, "board");
                setState(STATES.GAME);
                break;
            case "End Game":
                setState(STATES.MENU);
                break;
        }
    }
    private void setState(STATES s){
        CardLayout cardLayout = (CardLayout) card.getLayout();
        if(s == STATES.GAME){
            cardLayout.show(card, "board");
            board.setPaused(false);
        }else if(s == STATES.MENU){
            cardLayout.show(card, "menu");
            board.setPaused(true);
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(PaintIo::new);
    }

}