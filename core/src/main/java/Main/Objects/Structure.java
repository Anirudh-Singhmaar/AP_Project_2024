package Main.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class Structure {
    private Body body;
    private Texture texture;

    public Structure(World world, float x, float y, float width, float height, Texture texture) {
        this.texture = texture;
        this.body = createStructureBody(world, x, y, width, height);
    }

    private Body createStructureBody(World world, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);  // Half-width and height for the center

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

    public Texture getTexture() {
        return texture;
    }
}
