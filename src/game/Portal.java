package game;

import org.jbox2d.common.Vec2;
import city.cs.engine.*;

public class Portal extends StaticBody {

    public Portal(World world, Vec2 position,  String imagePath) {
        super(world, new BoxShape(2, 4));
        this.setPosition(position);
        this.addImage(new BodyImage(imagePath, 8));

    }

    @Override
    public void addCollisionListener(CollisionListener listener) {
        super.addCollisionListener(listener);

    }
}