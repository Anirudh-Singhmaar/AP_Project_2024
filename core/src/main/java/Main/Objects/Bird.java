package Main.Objects;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bird {
    private Body body;
    private Sprite sprite;

    public Bird(World world, Sprite sprite, float x, float y, float radius) {
        this.sprite = sprite;
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
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    public void draw(SpriteBatch spriteBatch, float pixelsPerMeter) {
        Vector2 position = body.getPosition();
        sprite.setSize(2 * body.getFixtureList().first().getShape().getRadius() * pixelsPerMeter, 
                       2 * body.getFixtureList().first().getShape().getRadius() * pixelsPerMeter);
        sprite.setPosition(position.x * pixelsPerMeter - sprite.getWidth() / 2,
                           position.y * pixelsPerMeter - sprite.getHeight() / 2);
        sprite.draw(spriteBatch);
    }

    public void launch(Vector2 direction) {
        body.setLinearVelocity(direction);
    }

    public void reset(float x, float y) {
        body.setTransform(x, y, 0);
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
    }

    public Body getBody() {
        return body;
    }
}
