package Main.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {
    private Body body;
    private Texture texture;
    private int health;

    public Pig(World world, float x, float y, int health, Texture texture) {
        this.health = health;
        this.texture = texture;
        this.body = createPigBody(world, x, y);
    }

    private Body createPigBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(25f);  // Radius for the pig's body

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.3f;
        body.createFixture(fixtureDef);

        shape.dispose();
        return body;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            // Handle pig destruction (e.g., remove pig from world, play sound, etc.)
        }
    }

    public Body getBody() {
        return body;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getHealth() {
        return health;
    }
}