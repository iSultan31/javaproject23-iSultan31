package game;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import city.cs.engine.World;

public class GameObject extends DynamicBody{
    private BodyImage image;

    public GameObject(World world, Shape shape, Vec2 position, String imagePath) {
        super(world, shape);
        setPosition(position);
        image = new BodyImage(imagePath, 3);
        addImage(image);
    }

    public BodyImage getImage() {
        return image;
    }
}
