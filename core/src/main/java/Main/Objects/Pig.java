package Main.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pig extends Actor {
    private Body body;         // Physics body
    private Sprite sprite;     // Sprite for rendering
    private int health;        // Pig's health

    public Pig(Body body, Sprite sprite) {
        this.body = body;
        this.sprite = sprite;
        this.health = 20;

        // Set the initial size and position based on the sprite
        setSize(sprite.getWidth(), sprite.getHeight());
        setPosition(body.getPosition().x - sprite.getWidth() / 2, 
                    body.getPosition().y - sprite.getHeight() / 2);
    }

    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            onDestroyed(); // Handle destruction logic if health drops to zero
        }
    }

    private void onDestroyed() {
        System.out.println("Pig destroyed!");
        this.remove(); // Remove from the scene
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

    public int getHealth() {
        return health;
    }
}
