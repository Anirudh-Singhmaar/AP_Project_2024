package Main.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Catapult {

    private World world;
    private Body catapultBody;
    private Texture texture;
    private float width, height;

    public Catapult(World world, float x, float y, float width, float height, Texture texture) {
        this.world = world;
        this.texture = texture;
        this.width = width;
        this.height = height;

        createCatapultBody(x, y);
    }

    private void createCatapultBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        catapultBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;

        catapultBody.createFixture(fixtureDef);
        shape.dispose();
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture,
                catapultBody.getPosition().x - width / 2,
                catapultBody.getPosition().y - height / 2,
                width, height);
    }

    public Body getBody() {
        return catapultBody;
    }

    public void dispose() {
        texture.dispose();
    }
}
