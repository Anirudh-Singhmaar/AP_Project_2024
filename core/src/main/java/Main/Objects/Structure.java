package Main.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Structure extends Actor {
    private Body body;         // Physics body
    private Sprite sprite;     // Sprite for rendering the structure
    private float width;       // Structure width
    private float height;      // Structure height
    private int durability;    // Structure durability

    public Structure(Body body, Sprite sprite, float width, float height, int durability) {
        this.body = body;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.durability = durability;

        setSize(width, height);
        setPosition(body.getPosition().x - width / 2, 
                    body.getPosition().y - height / 2);
    }

    public void takeDamage(int damage) {
        durability -= damage;
        if (durability <= 0) {
            onDestroyed(); // Handle destruction when durability reaches zero
        }
    }

    private void onDestroyed() {
        this.remove(); // Remove from the scene
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Update the actor's position to match the body's position
        setPosition(body.getPosition().x - width / 2, 
                    body.getPosition().y - height / 2);
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getDurability() {
        return durability;
    }
}
