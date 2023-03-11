package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class GameWorld {
    DynamicBody character;
    EnemyCollisionListener enemyCollisionListener;
    public GameWorld() {


        World world = new World();

        GameView view = new GameView(world, 800, 600);
        view.setOpaque(false);
        view.addKeyListener(new MyKeyListener());
        view.setFocusable(true);


        Shape shape = new BoxShape(20, 0.5f);
        StaticBody ground = new StaticBody(world, shape);
        ground.setPosition(new Vec2(0f, -11.1f));
        ground.setFillColor(new Color(0, 0, 0, 0));
        ground.setLineColor(new Color(0, 0, 0, 0));

        Shape platformShape = new BoxShape(3.5f, 0.5f);
        StaticBody platform = new StaticBody(world, platformShape);
        platform.setPosition(new Vec2(8, -3));

        Shape platformShape2 = new BoxShape(3.5f, 0.5f);
        StaticBody platform2 = new StaticBody(world, platformShape2);
        platform2.setPosition(new Vec2(-2, -7));


        Shape characterShape = new BoxShape(1,1);
        character = new DynamicBody(world, characterShape);
        character.setPosition(new Vec2(-10,-10));
        character.addImage(new BodyImage("data/character.png", 3));
        character.setGravityScale(1f);

        ArrayList<DynamicBody> enemies = new ArrayList<>();

        Enemy enemy2 = new Enemy(world, new Vec2(8,-10), "data/enemy.png");
        enemies.add(enemy2);

        enemyCollisionListener = new EnemyCollisionListener(character, enemies,view);
        character.addCollisionListener(enemyCollisionListener);

        ArrayList<StaticBody> coins = new ArrayList<>();

        for(int i = 0;i < 6;i+=2) {
            Coin coin = new Coin(world, new Vec2(-4+i, -5), "data/coins.png");
            coins.add(coin);
        }

        for(StaticBody coin: coins) {
            coin.addCollisionListener(new CollisionListener() {
                @Override
                public void collide(CollisionEvent e) {
                    if (e.getReportingBody() == coin && e.getOtherBody() == character) {
                        e.getReportingBody().destroy();
                        view.setCoins(view.getCoins()+1);
                    }
                }
            });
        }

        Portal portal = new Portal(world, new Vec2(19, -8), "data/portal.png");
        portal.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent e) {

                if (e.getReportingBody() == portal && e.getOtherBody() == character) {

                    e.getReportingBody().destroy();
                    e.getOtherBody().destroy();
                    view.gameWon();
                    System.out.println("Winner!!!");
                }
            }
        });


        final JFrame  frame = new JFrame ();
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        ImageIcon backgroundImageIcon = new ImageIcon("data/background.png");


        JLabel backgroundLabel = new JLabel(backgroundImageIcon);
        backgroundLabel.setBounds(0, 0, 800, 550);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);


        view.setBounds(0, 0, 800, 500);
        layeredPane.add(view, JLayeredPane.PALETTE_LAYER);



        frame.add(layeredPane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setSize(700,457);

        frame.setResizable(false);

        frame.pack();

        frame.setVisible(true);


        world.start();
    }

    private class MyKeyListener implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {

            int code = e.getKeyCode();


            switch (code) {
                case KeyEvent.VK_LEFT:

                    character.removeAllImages();
                    character.addImage(new BodyImage("data/characterLeft.png", 3));
                    character.setLinearVelocity(new Vec2(-10, character.getLinearVelocity().y));
                    break;
                case KeyEvent.VK_RIGHT:

                    character.removeAllImages();
                    character.addImage(new BodyImage("data/character.png", 3));
                    character.setLinearVelocity(new Vec2(10, character.getLinearVelocity().y));
                    break;

                case KeyEvent.VK_DOWN:

                    Vec2 v = character.getLinearVelocity();
                    if (Math.abs(v.y) > 0.01f) {
                        character.setLinearVelocity(new Vec2(character.getLinearVelocity().x,-10 ));
                    }

                    break;
                case KeyEvent.VK_SPACE:

                    Vec2 v2 = character.getLinearVelocity();
                    if (Math.abs(v2.y) < 0.01f) {
                        character.setLinearVelocity(new Vec2(v2.x, 10));
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            character.setLinearVelocity(new Vec2(0, character.getLinearVelocity().y));
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }
    }
    public static void main(String[] args) {
        new GameWorld();
    }
}