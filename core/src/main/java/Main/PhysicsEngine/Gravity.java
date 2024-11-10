package Main.PhysicsEngine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Gravity {
    private final World world;

    public Gravity() {
        // Initialize the world with gravity pointing down (-9.8 m/s^2 on the Y-axis)
        world = new World(new Vector2(0, -9.8f), true);
    }

    public World getWorld() {
        return world;
    }

    public void update(float deltaTime) {
        // Step the physics simulation forward
        world.step(deltaTime, 6, 2);
    }

    // Method to set gravity dynamically if needed
    public void setGravity(float x, float y) {
        world.setGravity(new Vector2(x, y));
    }
}

