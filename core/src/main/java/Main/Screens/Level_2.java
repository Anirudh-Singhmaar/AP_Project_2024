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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level_2 implements Screen {
    private static final float PIXELS_PER_METER = 100f; // Scaling factor
    private Game game;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;

    private World world;
    private Body[] birdBodies;
    private Body leftWoodBody, rightWoodBody, topWoodBody, catapultBody, groundBody, pigBody;
    private Box2DDebugRenderer debugRenderer;

    
    // Textures
    private Texture birdTexture, woodTexture, pigTexture, groundTexture, catapultTexture,backButtonTexture,BackGround;

    private float backButtonX, backButtonY, backButtonRadius;
    private int currentBirdIndex = 0;
    private boolean isBirdLaunched = false;

    public Level_2(Game game) {
        this.game = game;
        this.backButtonRadius = 35;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        viewport.apply();
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Load textures
        birdTexture = new Texture("Birds/RED_Bird.png");
        woodTexture = new Texture("Blocks/Wood.png");
        pigTexture = new Texture("Pigs/Golden_pigs.png");
        groundTexture = new Texture("Extras/Ground.jpg");
        catapultTexture = new Texture("Extras/Catapult.png");
        backButtonTexture = new Texture("Extras/Back.png");
        
        BackGround = new Texture("BackGround/LevelBackground.jpg");

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
        ScreenUtils.clear(1f, 1f, 1f, 1f); // Clear the screen
        world.step(1 / 60f, 6, 2); // Simulate physics
    
        if (isBirdLaunched) {
            if (currentBirdIndex < birdBodies.length) {
                Body activeBird = birdBodies[currentBirdIndex];
                float velocity = activeBird.getLinearVelocity().len(); // Velocity magnitude
    
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
    
        debugRenderer.render(world, camera.combined); // Render Box2D debug information
    
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(BackGround, 0, 0, camera.viewportWidth, camera.viewportHeight);
        drawSprites(); // Render the textures
        drawBackButtonTexture(); // Replace the back button's circle with a texture if needed
    
        spriteBatch.end();
    }
    
    private void drawBackButtonTexture() {
        spriteBatch.draw(
            backButtonTexture,
            backButtonX * PIXELS_PER_METER - backButtonRadius, 
            backButtonY * PIXELS_PER_METER - backButtonRadius, 
            backButtonRadius * 2, 
            backButtonRadius * 2
        );
    }

    private void goBackToPreviousScreen() {
        game.setScreen(new LevelScreen(game)); // Switch to the previous screen
    }

    
    private void drawSprites() {
        // Ground
        spriteBatch.draw(groundTexture, groundBody.getPosition().x * PIXELS_PER_METER - 640, groundBody.getPosition().y * PIXELS_PER_METER - 10, 1280, 20);

        // Birds
        for (int i = 0; i < birdBodies.length; i++) {
            if (i >= currentBirdIndex) {
                spriteBatch.draw(birdTexture, birdBodies[i].getPosition().x * PIXELS_PER_METER - 37.5F, birdBodies[i].getPosition().y * PIXELS_PER_METER - 37.5F, 75, 75);
            }
        }

        // Wood blocks
        spriteBatch.draw(woodTexture, leftWoodBody.getPosition().x * PIXELS_PER_METER - 17.5F, leftWoodBody.getPosition().y * PIXELS_PER_METER - 87.5F, 35, 175);
        spriteBatch.draw(woodTexture, rightWoodBody.getPosition().x * PIXELS_PER_METER - 17.5F, rightWoodBody.getPosition().y * PIXELS_PER_METER - 87.5F, 35, 175);
        spriteBatch.draw(woodTexture, topWoodBody.getPosition().x * PIXELS_PER_METER - 100, topWoodBody.getPosition().y * PIXELS_PER_METER - 15, 200, 30);

        // Pig
        spriteBatch.draw(pigTexture, pigBody.getPosition().x * PIXELS_PER_METER - 37.5F, pigBody.getPosition().y * PIXELS_PER_METER - 37.5F, 75, 75);

        // Catapult
        spriteBatch.draw(catapultTexture, catapultBody.getPosition().x * PIXELS_PER_METER - 26.25F, catapultBody.getPosition().y * PIXELS_PER_METER - 62.5F, 52.5F, 125);
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