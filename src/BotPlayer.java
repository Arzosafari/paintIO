

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


class BotPlayer extends Player{



    private String[] nameOfBots = {"Emma", "Olivia", "Ava", "Sophia", "Isabella", "Mia", "Charlotte", "Amelia", "Harper", "Evelyn", "Abigail", "Emily", "Elizabeth", "Mila", "Ella", "Avery", "Scarlett", "Sofia", "Chloe", "Victoria", "Layla", "Madison", "Grace", "Zoey", "Penelope", "Nora", "Riley", "Lily", "Eleanor", "Hannah", "Lila", "Aria", "Aubrey", "Ellie", "Stella", "Hazel", "Maya", "Aurora", "Natalie", "Emilia", "Addison", "Brooklyn", "Samantha", "Leah", "Audrey", "Caroline", "Savannah", "Genesis", "Piper", "Arianna", "Valentina", "Naomi", "Bella", "Paisley", "Clara", "Violet", "Nova", "Kennedy", "Madelyn", "Skylar", "Alice", "Cora", "Ruby", "Ivy", "Ariana", "Isabelle", "Jasmine", "Eva", "Everly", "Autumn", "Sadie", "Gianna", "Willow", "Quinn", "Serenity", "Peyton", "Madeline", "Gabriella", "Lydia", "Adelaide", "Madilyn", "Adeline", "Makenzie", "Jade", "Nevaeh", "Adalyn", "Aurora", "Melody", "Julia", "Isla", "Aubree", "Kinsley", "Kaylee", "Raelynn", "Ariel", "Jordyn", "Aylin", "Mariah", "Giselle", "Amara", "Elaina", "Everleigh", "Phoebe", "Harley", "Brielle", "Angelina", "Genesis", "Gia", "Alexandria", "Dahlia", "Laylah", "Zara", "Athena", "Kaitlyn", "Harmony", "Ximena", "Kali", "Valeria", "Arielle", "Hope", "Hayley", "Emery", "Leilani", "Aspen", "Jade", "Lilith", "Poppy", "Amaya", "Mckenna", "Rosalind", "Leila", "Saylor", "Brynlee", "Analia", "Alondra", "Aniyah", "Tiana", "Milana", "Kinslee", "Celeste", "Mabel", "Alanna", "Briar", "Leighton", "Elsie", "Magnolia", "Ainsley"};



        /**
         * Constructs a new BotPLayer on a random spot on the game area with specified color with a randomized direction
         * @param height height of game area player is constructed in
         * @param width width of game area player is constructed in
         * @param color the color of the player
         */
    BotPlayer(int height, int width, Color color){
        super(height, width, color);
        this.nameOfPlayer = nameOfBots[new Random().nextInt(nameOfBots.length)];
    }

    @Override
    public void move() {
        x += dx;
        y += dy;
        double rand = Math.random();
        if (rand < 0.05 && dx != -1) {
            dx = 1;
            dy = 0;
        }else if (rand < 0.1 && dx != 1) {
            dx = -1;
            dy = 0;
        }else if (rand < 0.15 && dy != -1) {
            dx = 0;
            dy = 1;
        }else if (rand < 0.2 && dy != 1) {
            dx = 0;
            dy = -1;
        }
        dontBeFar();

    }


    private void dontBeFar(){
        if(x == 0 && y == 350 - 1){
            if(dx == -1){
                dx = 0;
                dy = -1;
            }else {
                dx = 1;
                dy = 0;
            }
        }else if(x == 350 -1 && y == 0){
            if(dx == 1){
                dx = 0;
                dy = 1;
            } else {
                dx = -1;
                dy = 0;
            }
        }else if(x == 350 - 1 && y == 350 -1){
            if(dx == 1){
                dx = 0;
                dy = -1;
            }else {
                dx = -1;
                dy = 0;
            }
        }else if(x == 0 && y == 0){
            if(dx == -1){
                dx = 0;
                dy = 1;
            }else {
                dx = 1;
                dy = 0;
            }
        }else if(x == 0 && dx == -1){
            dx = 0;
            dy = 1;
        }else if(x == 350 -1 &&  dx == 1){
            dx = 0;
            dy = 1;
        }else if(y == 0 && dy == -1){
            dx = 1;
            dy = 0;
        }else if(y == 350 -1 && dy == 1){
            dx = 1;
            dy = 0;
        }
    }

    @Override
    void die() {
        super.die();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setAlive(true);
            }
        },5000);
    }

    @Override
    public int compareTo(Player o) {
        return 0;
    }
}