package Main.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Baselvl implements Screen {
    private static final float PIXELS_PER_METER = 100f; // Scaling factor
    private Game game;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private World world;
    private Body[] birdBodies;
    private Body leftWoodBody, rightWoodBody, topWoodBody, catapultBody, groundBody, pigBody;
    private Box2DDebugRenderer debugRenderer;

    private float backButtonX, backButtonY, backButtonRadius;
    private int currentBirdIndex = 0;
    private boolean isBirdLaunched = false;

    public Baselvl(Game game) {
        this.game = game;
        this.backButtonRadius = 35;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        viewport.apply();
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        createGroundBody();
        createBirdBodies();
        createWoodBodiesWithGravity();
        createCatapultBody();
        
        loadNextBirdOntoCatapult();

        backButtonX = (camera.viewportWidth - 100) / PIXELS_PER_METER;
        backButtonY = (camera.viewportHeight - 50) / PIXELS_PER_METER;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = Gdx.graphics.getHeight() - screenY;
                float distance = Vector2.dst(screenX, screenY, backButtonX * PIXELS_PER_METER, backButtonY * PIXELS_PER_METER);
                if (distance <= backButtonRadius) {
                    goBackToPreviousScreen();
                    return true;
                }

                if (!isBirdLaunched && currentBirdIndex < birdBodies.length) {
                    launchBird(screenX / PIXELS_PER_METER, screenY / PIXELS_PER_METER);
                    return true;
                }
                return false;
            }
        });
    }

    private void createGroundBody() {
        groundBody = createRectangleBody(6.5f, 0.1f, 12.8f, 0.2f, BodyDef.BodyType.StaticBody);
    }

    private void createBirdBodies() {
        birdBodies = new Body[3];
        birdBodies[0] = createCircleBody(3.5f, 1.25f, 0.375f, BodyDef.BodyType.DynamicBody);
        birdBodies[1] = createCircleBody(2f, 0.8f, 0.375f, BodyDef.BodyType.DynamicBody);
        birdBodies[2] = createCircleBody(2.75f, 0.8f, 0.375f, BodyDef.BodyType.DynamicBody);
    }

    private void createWoodBodiesWithGravity() {
        leftWoodBody = createRectangleBody(8.8f, 1.5f, 0.35f, 1.75f, BodyDef.BodyType.DynamicBody);
        rightWoodBody = createRectangleBody(10.8f, 1.5f, 0.35f, 1.75f, BodyDef.BodyType.DynamicBody);
        topWoodBody = createRectangleBody(9.8f, 2.6f, 2f, 0.3f, BodyDef.BodyType.DynamicBody);
        pigBody = createCircleBody(9.8f, 1.5f, 0.35f, BodyDef.BodyType.DynamicBody);
    }

    private void createCatapultBody() {
        catapultBody = createRectangleBody(3.5f, 0.8f, 0.525f, 1.25f, BodyDef.BodyType.StaticBody);
    }

    private Body createCircleBody(float x, float y, float radius, BodyDef.BodyType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.5f; // Bounciness
        fixtureDef.friction = 1.75f; // High friction for better sliding control
        body.createFixture(fixtureDef);

        shape.dispose();
        return body;
    }

    private Body createRectangleBody(float x, float y, float width, float height, BodyDef.BodyType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(x, y);
    
        Body body = world.createBody(bodyDef);
    
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
    
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f; // Lightweight for wood
        fixtureDef.friction = 0.4f; // Low friction
        fixtureDef.restitution = 0.2f; // Low bounciness
        body.createFixture(fixtureDef);
    
        shape.dispose();
        return body;
    }    

    private void launchBird(float screenX, float screenY) {
        Body bird = birdBodies[currentBirdIndex];
        Vector2 launchDirection = new Vector2(screenX - bird.getPosition().x, screenY - bird.getPosition().y).nor().scl(-5);
        bird.applyLinearImpulse(launchDirection, bird.getWorldCenter(), true);
        isBirdLaunched = true;
    }

    private void loadNextBirdOntoCatapult() {
        if (currentBirdIndex < birdBodies.length) {
            Body nextBird = birdBodies[currentBirdIndex];
            nextBird.setTransform(3.5f, 1.25f, 0);
            nextBird.setLinearVelocity(0, 0);
            nextBird.setAngularVelocity(0);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        world.step(1 / 60f, 6, 2);


        if (isBirdLaunched) {
            if (currentBirdIndex < birdBodies.length) {
                Body activeBird = birdBodies[currentBirdIndex];
                float velocity = activeBird.getLinearVelocity().len(); // Calculate the velocity magnitude
    
                // Check if the bird is off-screen horizontally or its velocity is near zero
                if (activeBird.getPosition().x < -2 || activeBird.getPosition().x > 15 || velocity < 0.1f) {
                    activeBird.setLinearVelocity(0, 0); // Stop any remaining velocity
                    world.destroyBody(activeBird); // Destroy the current bird body
    
                    currentBirdIndex++;
    
                    if (currentBirdIndex < birdBodies.length) {
                        isBirdLaunched = false;
                        loadNextBirdOntoCatapult();
                    } else {
                        // All birds used, game over logic
                        System.out.println("Game over");
                    }
                }
            }
        }
    
        debugRenderer.render(world, camera.combined);
    
        // Render shapes and other game elements
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawGround();
        drawBirds();
        drawWoodBlocks();
        drawPig();
        drawCatapult();
        drawBackButton();
        shapeRenderer.end();
    
        // Draw outlines
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1); // Black outlines
        drawGround();
        drawBirds();
        drawWoodBlocks();
        drawPig();
        drawCatapult();
        drawBackButton();
        shapeRenderer.end();
    }
    

    private void drawGround() {
        drawRectangle(groundBody, 12.8f * PIXELS_PER_METER, 0.2f * PIXELS_PER_METER);
    }

    private void drawBirds() {
        for (int i = 0; i < birdBodies.length; i++) {
            if (i >= currentBirdIndex) {
                drawCircle(birdBodies[i], 0.375f * PIXELS_PER_METER);
            }
        }
    }

    private void drawWoodBlocks() {
        drawRectangle(leftWoodBody, 0.35f * PIXELS_PER_METER, 1.75f * PIXELS_PER_METER);
        drawRectangle(rightWoodBody, 0.35f * PIXELS_PER_METER, 1.75f * PIXELS_PER_METER);
        drawRectangle(topWoodBody, 2f * PIXELS_PER_METER, 0.3f * PIXELS_PER_METER);
    }

    private void drawPig() {
        drawCircle(pigBody, 0.375f * PIXELS_PER_METER);
    }

    private void drawCatapult() {
        drawRectangle(catapultBody, 0.525f * PIXELS_PER_METER, 1.25f * PIXELS_PER_METER);
    }

    private void drawRectangle(Body body, float width, float height) {
        shapeRenderer.rect(
            body.getPosition().x * PIXELS_PER_METER - width / 2,
            body.getPosition().y * PIXELS_PER_METER - height / 2,
            width / 2, // Origin X (center for rotation)
            height / 2, // Origin Y (center for rotation)
            width,
            height,
            1, // Scale X
            1, // Scale Y
            (float) Math.toDegrees(body.getAngle()) // Convert radians to degrees for rotation
        );
    }
    
    private void drawCircle(Body body, float radius) {
        shapeRenderer.circle(body.getPosition().x * PIXELS_PER_METER,
                             body.getPosition().y * PIXELS_PER_METER,
                             radius);
    }

    private void drawBackButton() {
        shapeRenderer.circle(backButtonX * PIXELS_PER_METER, backButtonY * PIXELS_PER_METER, backButtonRadius);
    }

    private void goBackToPreviousScreen() {
        game.setScreen(new LevelScreen(game)); // Switch to the previous screen
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        world.dispose();
        shapeRenderer.dispose();
        debugRenderer.dispose();
    }
}