package Main.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;

public class Structure {
    private Body body;
    private Texture texture;
    private float width, height;
    private int health;

    public Structure(World world, float x, float y, float width, float height, int health, Texture texture) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.health = health;
        this.body = createRectangleBody(world, x, y, width, height);
    }

    private Body createRectangleBody(World world, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Set to DynamicBody
        bodyDef.position.set(x, y);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Box2D uses half-width and half-height

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f; // Adjust density for realistic physics
        fixtureDef.friction = 0.5f; // Friction between objects
        fixtureDef.restitution = 0.1f; // Slight bounciness

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    // Method to apply damage to the structure
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            // The structure is destroyed when health reaches 0
            health = 0;
            body.setActive(false); // Disable the body if it's destroyed
        }
    }

    public void draw(Batch batch) {
        batch.draw(texture, 
            body.getPosition().x - width / 2, 
            body.getPosition().y - height / 2, 
            width, 
            height);
    }

    public Body getBody() {
        return body;
    }

    // Getters for the dimensions of the structure
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
