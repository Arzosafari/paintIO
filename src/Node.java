
import java.awt.*;

class Node {
    public static final int EMPTY_COLOR = -1;
    private Player completeOwner;
    private Player contestedOwner;

    private Color ownColor;
    private int x,y;
    Node(int x, int y){
        this.x = x;
        this.y = y;
    }
    int getX() {

        return x;
    }

    int getY() {

        return y;
    }
    Player getContestedOwner() {

        return contestedOwner;
    }
    void setContestedOwner(Player contestedOwner) {

        this.contestedOwner = contestedOwner;
    }
    Player getOwner() {
        return completeOwner;
    }

    void setOwner(Player completeOwner) {
        if(this.completeOwner != null){
            this.completeOwner.removeOwnedTile(this);
        }
        this.completeOwner = completeOwner;
    }

    Color getColor() {
        if (completeOwner != null && contestedOwner == null) {
            return completeOwner.getColor().darker();

        } else if (completeOwner == null && contestedOwner != null) {
            int colorRed=contestedOwner.getColor().getRed();
            int colorGreen=contestedOwner.getColor().getGreen();
            int colorBlue= contestedOwner.getColor().getBlue();
            return new Color(colorRed, colorGreen,colorBlue).brighter();

        } else if (completeOwner != null && contestedOwner != completeOwner) {
            float[] completeHSB = Color.RGBtoHSB(completeOwner.getColor().getRed(),
                    completeOwner.getColor().getGreen(), completeOwner.getColor().getBlue(), null);
            float[] contestedHSB = Color.RGBtoHSB(contestedOwner.getColor().getRed(),
                    contestedOwner.getColor().getGreen(), contestedOwner.getColor().getBlue(), null);
            float blendedHue = (completeHSB[0] + contestedHSB[0]) / 2.0f;
            float blendedSaturation = (completeHSB[1] + contestedHSB[1]) / 2.0f;
            float blendedBrightness = (completeHSB[2] + contestedHSB[2]) / 2.0f;
            return Color.getHSBColor(blendedHue, blendedSaturation, blendedBrightness);
        } else {
            return Color.pink;//((x + y) % 2 == 0) ? Color.PINK : Color.BLACK;

        }
    }
    public Color setColor(Color color){
        return color;

    }


}