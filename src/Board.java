import javax.swing.*;
import java.awt.event.ActionListener;

public class Board extends JPanel {
    private final int areaHeight;
    private final int areaWidth;
    private int botNumber;
    private ActionListener actionListener;
    private boolean paused = true;
    private final int tickReset;
    Board(ActionListener actionListener,String p1name,int areaHeight,int areaWidth,int gameSpeed,int botNumber){
        this.actionListener=actionListener;
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;
        this.botNumber=botNumber;
        int[]speeds={12,10,8,6,4};
        tickReset=speeds[gameSpeed-1];


    }
    Board(ActionListener actionListener,String p1name,String p2name,int areaHeight,int areaWidth,int gameSpeed,int botNumber){
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;

    }
    void setPaused(Boolean b){
        paused = b;
    }

}
