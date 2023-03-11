package game;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.CollisionListener;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

public class Coin extends StaticBody {

    public Coin(World world, Vec2 position, String imagePath) {
        super(world, new BoxShape(1, 1));
        this.addImage(new BodyImage(imagePath, 3));
        this.setPosition(position);
    }

    @Override
    public void addCollisionListener(CollisionListener listener) {
        super.addCollisionListener(listener);

    }
}
