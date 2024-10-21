package Main.AngryBirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Level_2 implements Screen {

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    public Level_2(Game game) {
    }

    @Override
    public void show() {
        // World and Renderer initialization
        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Camera initialization: Focus on the area where the objects will be
        camera = new OrthographicCamera(10, 10 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(0, 0, 0);  // Center camera on the origin
        camera.update();

        // Ground Body Definition
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(0, -1.8f);  // Set ground slightly lower

        // Create the ground body in the world
        Body groundBody = world.createBody(groundBodyDef);

        // Create a ground shape using ChainShape
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-10, 0), new Vector2(10, 0)});

        // Define fixture for ground
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f;

        // Attach the shape to the ground body
        groundBody.createFixture(groundFixtureDef);

        // Dispose of the shape after use
        groundShape.dispose();

        // Circles positions
        Vector2[] positions = new Vector2[]{
            new Vector2(-4.2f, 1.8f),  // First circle (bird) position
            new Vector2(-3.7f, -1.3f),  // Second circle position
            new Vector2(-4.7f, -1.8f)   // Third circle position
        };

        // Fixture definition for the circles
        FixtureDef fix = new FixtureDef();
        fix.density = 1f;
        fix.friction = 0.3f;
        fix.restitution = 0.5f;

        // Creating a circle shape for the birds
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.125f);
        fix.shape = circleShape;

        // Create the circles
        for (Vector2 position : positions) {
            BodyDef circleBodyDef = new BodyDef();
            circleBodyDef.type = BodyDef.BodyType.DynamicBody;
            circleBodyDef.position.set(position);

            world.createBody(circleBodyDef).createFixture(fix);
        }

        // Dispose of the circle shape after use
        circleShape.dispose();

        // Now let's create the rectangle
        PolygonShape rectangleShape = new PolygonShape();
        rectangleShape.setAsBox(0.0625f, 0.251f);  // Half-width = 0.0625, Half-height = 0.251

        fix.shape = rectangleShape;

        // Define the body for the rectangle
        BodyDef rectangleBodyDef = new BodyDef();
        rectangleBodyDef.type = BodyDef.BodyType.StaticBody;
        rectangleBodyDef.position.set(new Vector2(-3.7f, -1.55f));  // Place the rectangle just above the ground

        // Create the rectangle in the world
        world.createBody(rectangleBodyDef).createFixture(fix);

        // Dispose of the rectangle shape after use
        rectangleShape.dispose();
    }

    @Override
    public void render(float delta) {
        // Clear the screen with white color
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Step the physics world
        world.step(1 / 60f, 6, 2);

        // Update the camera
        camera.update();

        // Render the world using debug renderer
        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        // Update the camera when resizing
        camera.viewportWidth = 10;
        camera.viewportHeight = 10 * height / (float) width;
        camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
