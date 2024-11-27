package Main.Objects;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Catapult {
    private Body body;
    private Sprite sprite;

    public Catapult(World world, Sprite sprite, float x, float y, float width, float height) {
        this.sprite = sprite;
        this.body = createRectangleBody(world, x, y, width, height);
    }

    private Body createRectangleBody(World world, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    public void draw(SpriteBatch spriteBatch, float pixelsPerMeter) {
        Vector2 position = body.getPosition();
        float angle = body.getAngle();

        sprite.setSize(2 * pixelsPerMeter, 2 * pixelsPerMeter);
        sprite.setPosition(position.x * pixelsPerMeter - sprite.getWidth() / 2,
                           position.y * pixelsPerMeter - sprite.getHeight() / 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setRotation(angle * MathUtils.radiansToDegrees);
        sprite.draw(spriteBatch);
    }

    public Body getBody() {
        return body;
    }
}
