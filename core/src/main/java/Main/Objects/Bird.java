package Main.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bird extends Actor {
    private Body body;         // Physics body
    private Sprite sprite;     // Sprite for rendering
    private float radius,x,y;      // Bird's radius
    private World world;       // Box2D world

    public Bird(World world,float x,float y, Sprite sprite, float radius) {
        this.world = world;
        this.sprite = sprite;
        this.radius = radius;
        this.x = x;
        this.y = y;
        
        createBody();
        
        setSize(radius*2,radius*2);
    }

    private void createBody() {
        // Define the body definition (static, dynamic, or kinematic)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Dynamic body for physics
        bodyDef.position.set(this.x, this.y); // Initial position (can be set dynamically)

        // Create the body in the world
        body = world.createBody(bodyDef);

        // Define a circular shape for the bird's collision
        CircleShape shape = new CircleShape();
        shape.setRadius(radius); // Set the radius of the bird

        // Define a fixture for the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Set density (affects movement, gravity)
        fixtureDef.friction = 0.3f; // Set friction (affects sliding)
        fixtureDef.restitution = 0.5f; // Set restitution (bounciness)

        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Dispose of the shape after creating the fixture
        shape.dispose();
    }

    public void dealDamage(Pig pig) {
        pig.takeDamage(10);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Update the actor's position to match the body
        setPosition(body.getPosition().x - sprite.getWidth() / 2, 
                    body.getPosition().y - sprite.getHeight() / 2);
        setRotation((float) Math.toDegrees(body.getAngle()));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Update sprite position and rotation
        sprite.setPosition(getX(), getY());
        sprite.setRotation(getRotation());
        sprite.draw(batch);
    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getRadius() {
        return radius;
    }
}