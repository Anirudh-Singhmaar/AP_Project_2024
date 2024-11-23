package Main.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {
    private Body body;
    private Texture texture;
    private float radius;
    private int health;

    public Pig(World world, float x, float y, float radius, int health, Texture texture) {
        this.texture = texture;
        this.radius = radius;
        this.health = health;
        this.body = createCircleBody(world, x, y, radius);
    }

    private Body createCircleBody(World world, float x, float y, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Pigs can move when hit
        bodyDef.position.set(x, y);

        Body body = world.createBody(bodyDef);

        // Create a circle shape
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

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

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            // Optionally, you can make the pig "die" by removing it from the world or changing its state
            health = 0;
            body.setActive(false); // Disables the body (if needed)
        }
    }

    public void draw(Batch batch) {
        Vector2 position = body.getPosition();
        batch.draw(texture, 
            position.x - radius,  // x position adjusted for center alignment
            position.y - radius,  // y position adjusted for center alignment
            radius * 2,           // width of the sprite
            radius * 2            // height of the sprite
        );
    }

    public float getRadius() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRadius'");
    }
}
