package Main.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Catapult extends Actor {
    private Body body;         // Physics body
    private Sprite baseSprite; // Sprite for the base of the catapult
    private Sprite armSprite;  // Sprite for the swinging arm of the catapult
    private float armAngle;    // Current angle of the arm
    private float maxPullBack; // Maximum pull-back angle
    private float releaseAngle; // Angle at which the bird is released

    public Catapult(Body body, Sprite baseSprite, Sprite armSprite, float maxPullBack, float releaseAngle) {
        this.body = body;
        this.baseSprite = baseSprite;
        this.armSprite = armSprite;
        this.maxPullBack = maxPullBack;
        this.releaseAngle = releaseAngle;
        this.armAngle = maxPullBack;

        // Set the size and position for the base sprite
        setSize(baseSprite.getWidth(), baseSprite.getHeight());
        setPosition(body.getPosition().x - baseSprite.getWidth() / 2,
                    body.getPosition().y - baseSprite.getHeight() / 2);
    }

    public void pullBack(float angle) {
        // Ensure the pull-back angle does not exceed the maximum
        if (angle < maxPullBack) {
            armAngle = maxPullBack;
        } else if (angle > 0) {
            armAngle = 0;
        } else {
            armAngle = angle;
        }
    }

    public boolean release(Bird bird) {
        if (armAngle == 0) {
            // Apply an impulse to the bird and return true for a successful release
            bird.getBody().applyLinearImpulse(
                (float) Math.cos(releaseAngle) * 10,  // X-component of impulse
                (float) Math.sin(releaseAngle) * 10,  // Y-component of impulse
                bird.getBody().getWorldCenter().x,
                bird.getBody().getWorldCenter().y,
                true
            );
            return true;
        }
        return false; // If the arm is not pulled back, release fails
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Sync the arm sprite with the catapult's body and arm angle
        armSprite.setOrigin(baseSprite.getWidth() / 2, baseSprite.getHeight() / 2);
        armSprite.setRotation(armAngle);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the base of the catapult
        baseSprite.setPosition(getX(), getY());
        baseSprite.draw(batch);

        // Draw the arm of the catapult
        armSprite.setPosition(getX(), getY());
        armSprite.setRotation(armAngle);
        armSprite.draw(batch);
    }

    public Body getBody() {
        return body;
    }

    public Sprite getBaseSprite() {
        return baseSprite;
    }

    public Sprite getArmSprite() {
        return armSprite;
    }

    public float getArmAngle() {
        return armAngle;
    }
}
