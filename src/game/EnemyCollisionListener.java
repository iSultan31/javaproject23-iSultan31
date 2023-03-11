package game;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.DynamicBody;

public class EnemyCollisionListener implements CollisionListener{
    private DynamicBody character;
    private ArrayList<DynamicBody> enemies;
    private GameView view;

    public EnemyCollisionListener(DynamicBody character, ArrayList<DynamicBody> enemies, GameView view) {
        this.character = character;
        this.enemies = enemies;
        this.view = view;
    }

    @Override
    public void collide(CollisionEvent e) {
        for (DynamicBody enemy : enemies) {
            if (e.getReportingBody() == character && e.getOtherBody() == enemy) {
                if(view.getNumLives()>1) {
                    view.setNumLives(view.getNumLives()-1);
                    System.out.println("Lives: " + view.getNumLives());
                }
                else {
                    view.setNumLives(view.getNumLives()-1);
                    System.out.println("Lives: " + view.getNumLives());
                    e.getReportingBody().destroy();
                    System.out.println("Game Over");
                }
            }
        }
    }
}
