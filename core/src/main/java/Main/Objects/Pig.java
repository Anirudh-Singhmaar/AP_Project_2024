package Main.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {
    private Body body;
    private Texture texture;
    private float width, height;

    public Pig(World world, float x, float y, float width, float height, Texture texture) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.body = createRectangleBody(world, x, y, width, height);
    }

    private Body createRectangleBody(World world, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Pigs can move when hit
        bodyDef.position.set(x, y);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.3f;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    public Body getBody() {
        return body;
    }

    public void draw(Batch batch) {
        Vector2 position = body.getPosition();
        batch.draw(texture, 
            position.x - width / 2, 
            position.y - height / 2, 
            width, 
            height);
    }
}
