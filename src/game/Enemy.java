package game;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.CollisionListener;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.World;

public class Enemy extends GameObject {
    private float minX;
    private float maxX;
    private float xVelocity = 5.0f;
    public Enemy(World world, Vec2 position, String imagePath) {
        super(world, new BoxShape(1, 1), position, imagePath);
        minX = position.x - 5.0f;
        maxX = position.x + 5.0f;

        StepListener stepListener = new StepListener() {
            @Override
            public void preStep(StepEvent e) {
                float x = Enemy.this.getPosition().x;
                if (x >= 15) {
                    Enemy.this.removeAllImages();
                    Enemy.this.addImage(new BodyImage("data/enemy.png", 3));
                    Enemy.this.setLinearVelocity(new Vec2(-5, 0));
                } else if (x <= 9) {
                    Enemy.this.removeAllImages();
                    Enemy.this.addImage(new BodyImage("data/enemyRight.png", 3));
                    Enemy.this.setLinearVelocity(new Vec2(5, 0));
                }
            }

            @Override
            public void postStep(StepEvent e) {
            }
        };
        world.addStepListener(stepListener);


    }

    @Override
    public void addCollisionListener(CollisionListener listener) {
        super.addCollisionListener(listener);
    }
}
