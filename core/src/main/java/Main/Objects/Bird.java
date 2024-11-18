package Main.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Bird {
    private Body body;
    private Texture texture;
    private float radius;

    public Bird(World world, float x, float y, Texture texture) {
        this.texture = texture;
        this.radius = 30f; // Set the bird's radius here
        this.body = createCircleBody(world, x, y, radius);
    }

    private Body createCircleBody(World world, float x, float y, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    public Body getBody() {
        return body;
    }

    public void draw(com.badlogic.gdx.graphics.g2d.Batch batch) {
        Vector2 position = body.getPosition();
        // Scale texture to match the body's size (2 * radius)
        float diameter = radius * 2;
        batch.draw(texture, position.x - radius, position.y - radius, diameter, diameter);
    }
}
