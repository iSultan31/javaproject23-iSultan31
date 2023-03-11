package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.UserView;
import city.cs.engine.World;
public class GameView extends UserView{
    private int numLives;
    private int coins;
    private World world;
    private LinkedList<StaticBody> hearts = new LinkedList<>();
    private Image gameOverImage;
    private Image gameWonImage;
    private boolean gamewon;

    public GameView(World world, int width, int height) {
        super(world, width, height);
        numLives = 3;
        this.world = world;

        try {
            gameOverImage = ImageIO.read(new File("data/gameover.png"));
            gameWonImage = ImageIO.read(new File("data/winner.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void setNumLives(int numLives) {
        this.numLives = numLives;
        if (numLives < hearts.size()) {
            StaticBody heart = hearts.pop();
            heart.removeAllImages();
            heart.addImage(new BodyImage("data/deadHeart.png", 2));
        }
    }

    public int getNumLives() {
        return numLives;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }

    public void gameWon() {
        this.gamewon = true;
    }

    @Override
    protected void paintForeground(Graphics2D g) {
        super.paintForeground(g);


        if (numLives <= 0 && gameOverImage != null) {
            g.drawImage(gameOverImage, 0, 0, getWidth(), getHeight(), null);
        }

        if(gamewon) {
            g.drawImage(gameWonImage, 0, 0, getWidth(), getHeight(), null);
        }
        Shape heartShape = new BoxShape(0.5f, 0.5f);
        for (int i = 0; i < numLives; i++) {
            StaticBody heart = new StaticBody(world, heartShape);
            heart.setPosition(new Vec2(-13f + -i * 2.5f, 9));
            heart.addImage(new BodyImage("data/hearts.png", 2));
            hearts.add(heart);
        }

        Shape coinCounterShape = new BoxShape(1f, 1f);
        StaticBody coinCounter = new StaticBody(world, coinCounterShape);
        coinCounter.setPosition(new Vec2(15, 9));
        coinCounter.addImage(new BodyImage("data/coins.png", 3));

        Font font = new Font("Arial", Font.PLAIN, 25);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(" = " + coins, 725, 87);

    }
}



