package Main.Objects;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    private Body body;
    private Texture texture;
    private float radius;
    private boolean isLaunched;

    public Bird(World world, float x, float y, float radius, Texture texture) {
        this.radius = radius;
        this.texture = texture;
        this.isLaunched = false;
        this.body = createBirdBody(world, x, y);
    }

    private Body createBirdBody(World world, float x, float y) {
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

    public void launch(Vector2 velocity) {
        if (!isLaunched) {
            body.setLinearVelocity(velocity);
            isLaunched = true;
        }
    }

    public Body getBody() {
        return body;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getRadius() {
        return radius;
    }
}