package Main.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.World;

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

    // Generalized method to deal damage to either a pig or a structure
    public void dealDamage(Object target) {
        if (target instanceof Pig) {
            Pig pig = (Pig) target;
            // Check if the bird collides with the pig
            if (this.body.getPosition().dst(pig.getBody().getPosition()) < this.radius + pig.getRadius()) {
                pig.takeDamage(20); // You can change the damage value as needed
            }
        }
        else if (target instanceof Structure) {
            Structure structure = (Structure) target;
            // Get the position and dimensions of the structure
            Vector2 structurePosition = structure.getBody().getPosition();
            float structureWidth = structure.getWidth();
            float structureHeight = structure.getHeight();
            
            // Calculate the closest point on the rectangle to the bird's position
            float closestX = Math.max(structurePosition.x - structureWidth / 2, 
                                      Math.min(this.body.getPosition().x, structurePosition.x + structureWidth / 2));
            float closestY = Math.max(structurePosition.y - structureHeight / 2, 
                                      Math.min(this.body.getPosition().y, structurePosition.y + structureHeight / 2));
            
            // Calculate the distance between the bird's center and the closest point on the structure
            float distanceX = this.body.getPosition().x - closestX;
            float distanceY = this.body.getPosition().y - closestY;
            float distanceSquared = distanceX * distanceX + distanceY * distanceY;
            
            // Check if the distance is less than the bird's radius (collision detected)
            if (distanceSquared < this.radius * this.radius) {
                structure.takeDamage(20); // You can change the damage value as needed
            }
        }
        
    }
}
