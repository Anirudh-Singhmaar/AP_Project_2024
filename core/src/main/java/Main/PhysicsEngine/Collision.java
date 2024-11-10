package Main.PhysicsEngine;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Collision implements ContactListener {
    
    @Override
    public void beginContact(Contact contact) {
        // Get the bodies involved in the collision
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        // Check if one of the bodies is a bird and the other an obstacle
        if (isBird(bodyA) && isObstacle(bodyB)) {
            handleBirdObstacleCollision(bodyA, bodyB);
        } else if (isObstacle(bodyA) && isBird(bodyB)) {
            handleBirdObstacleCollision(bodyB, bodyA);
        }
    }

    @Override
    public void endContact(Contact contact) {
        // Called when objects stop colliding
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Called before collision is resolved
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Called after collision resolution
    }

    // Custom methods to identify birds and obstacles
    private boolean isBird(Body body) {
        return body.getUserData() != null && body.getUserData().equals("bird");
    }

    private boolean isObstacle(Body body) {
        return body.getUserData() != null && body.getUserData().equals("obstacle");
    }

    private void handleBirdObstacleCollision(Body bird, Body obstacle) {
        // Code to handle the collision effect
        System.out.println("Bird hit an obstacle!");

        // Example: Destroy obstacle or reduce health
        obstacle.setUserData("destroyed");
    }
}
