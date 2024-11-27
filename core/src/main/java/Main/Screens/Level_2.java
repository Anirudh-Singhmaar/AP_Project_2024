package Main.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level_2 implements Screen {
    private static final float PIXELS_PER_METER = 100f;
    private Game game;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;

    private World world;
    private Body[] birdBodies;
    private Body leftVerticalWoodBody, rightVerticalWoodBody, topHorizontalWoodBody, catapultBody, groundBody, pigBody;
    private Box2DDebugRenderer debugRenderer;

    private Sprite birdSprite, verticalWoodSprite, horizontalWoodSprite, pigSprite;
    private Sprite groundSprite, catapultSprite, backButtonSprite, backgroundSprite;

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

        birdSprite = new Sprite(new Texture("Birds/RED_Bird.png"));
        verticalWoodSprite= new Sprite(new Texture("Blocks/Wood.png"));
        horizontalWoodSprite = new Sprite(new Texture("Blocks/Wood.png"));
        pigSprite = new Sprite(new Texture("Pigs/Golden_pigs.png"));
        groundSprite = new Sprite(new Texture("Extras/Ground.jpg"));
        catapultSprite = new Sprite(new Texture("Extras/Catapult.png"));
        backButtonSprite = new Sprite(new Texture("Extras/Back.png"));
        backgroundSprite = new Sprite(new Texture("BackGround/LevelBackground.jpg"));
        
        // Adjust the background to fit the screen
        backgroundSprite.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        backgroundSprite.setPosition(0, 0);
    
        createGroundBody();
        createBirdBodies();
        createWoodBodiesWithGravity();
        createCatapultBody();

        backButtonX = 100 / PIXELS_PER_METER;
        backButtonY = 680 / PIXELS_PER_METER;

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
        leftVerticalWoodBody = createRectangleBody(8.8f, 1.5f, 0.35f, 1.75f, BodyDef.BodyType.DynamicBody);
        rightVerticalWoodBody = createRectangleBody(10.8f, 1.5f, 0.35f, 1.75f, BodyDef.BodyType.DynamicBody);
        topHorizontalWoodBody = createRectangleBody(9.8f, 2.6f, 2f, 0.3f, BodyDef.BodyType.DynamicBody);
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
        fixtureDef.density = 0.5f;
        body.createFixture(fixtureDef);

        shape.dispose();
        return body;
    }

    private void launchBird(float screenX, float screenY) {
        Body bird = birdBodies[currentBirdIndex];
        Vector2 launchDirection = new Vector2(screenX - bird.getPosition().x, screenY - bird.getPosition().y).nor().scl(10);
        bird.setLinearVelocity(launchDirection);
        isBirdLaunched = true;
    }
    
    private void updateBirdStatus(float delta) {
        if (isBirdLaunched) {
            Body currentBird = birdBodies[currentBirdIndex];
    
            // Check if the bird is off-screen or stationary
            if (currentBird.getPosition().y < 0 || currentBird.getLinearVelocity().len() < 0.1f) {
                loadNextBird();
            }
        }
    }

    private void loadNextBird() {
        // Check if there are more birds left to launch
        if (currentBirdIndex < birdBodies.length - 1) {
            currentBirdIndex++; // Increment to load the next bird
    
            Body nextBird = birdBodies[currentBirdIndex];
            // Reset the bird's position near the catapult
            nextBird.setTransform(3.5f, 1.25f, 0); // Set position to the catapult's start point
            nextBird.setLinearVelocity(0, 0);      // Stop any motion
            nextBird.setAngularVelocity(0);        // Reset rotation
            
            // Update sprite position to match the body (if needed)
            birdSprite.setPosition(nextBird.getPosition().x * PIXELS_PER_METER - birdSprite.getWidth() / 2, 
                                   nextBird.getPosition().y * PIXELS_PER_METER - birdSprite.getHeight() / 2);
    
            isBirdLaunched = false; // Set the bird as not launched
        } else {
            // If there are no birds left to load, we might want to reset the game or show a message
            System.out.println("All birds used!");
        }
    }
    

    private void goBackToPreviousScreen() {
        game.setScreen(new LevelScreen(game));
    }
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        drawSprites(); // Drawing the current sprites
        spriteBatch.end();

        debugRenderer.render(world, camera.combined);
        world.step(1 / 60f, 6, 2);

        updateBirdStatus(delta); // Check if it's time to load the next bird
    }

    private void drawSprites() {
        backgroundSprite.draw(spriteBatch);
    
        // Draw the ground
        groundSprite.setPosition(0, 0); // Assuming ground is at the bottom left of the screen
        groundSprite.setSize(1280f, 50f); // Set ground size based on screen width and desired height
        groundSprite.draw(spriteBatch);
        
        // Draw the wood blocks for the inverted "U"
        drawWoodSprite(verticalWoodSprite, leftVerticalWoodBody);
        drawWoodSprite(verticalWoodSprite, rightVerticalWoodBody);
        drawWoodSprite(horizontalWoodSprite, topHorizontalWoodBody);
    
        // Draw the pig in the middle of the "U"
        drawPigSprite(pigSprite, pigBody);
    
        // Draw the birds
        for (Body birdBody : birdBodies) {
            if (birdBody != null) {
                drawBirdSprite(birdSprite, birdBody);
            }
        }
    
        // Draw the catapult
        drawCatapultSprite(catapultSprite, catapultBody);
    
        // Draw the back button
        backButtonSprite.setSize(backButtonRadius * 2, backButtonRadius * 2);
        backButtonSprite.setPosition(
            backButtonX * PIXELS_PER_METER - backButtonSprite.getWidth() / 2,
            backButtonY * PIXELS_PER_METER - backButtonSprite.getHeight() / 2
        );
        backButtonSprite.draw(spriteBatch);
    }
    

    private void drawBirdSprite(Sprite sprite, Body body) {
        // Get the position of the bird body
        Vector2 position = body.getPosition();
    
        // Get the radius of the circular fixture
        CircleShape shape = (CircleShape) body.getFixtureList().first().getShape();
        float radius = shape.getRadius() * PIXELS_PER_METER;
    
        // Set the sprite size to match the body diameter
        sprite.setSize(radius * 2, radius * 2);
    
        // Set the sprite's position (no rotation needed for circular birds)
        sprite.setPosition(
            position.x * PIXELS_PER_METER - sprite.getWidth() / 2,
            position.y * PIXELS_PER_METER - sprite.getHeight() / 2
        );
    
        // Draw the sprite
        sprite.draw(spriteBatch);
    }
    
    private void drawCatapultSprite(Sprite sprite, Body body) {
        // Get the position and rotation of the catapult body
        Vector2 position = body.getPosition();
        float angle = body.getAngle() * MathUtils.radiansToDegrees;
    
        // Calculate the size of the body (rectangular fixture)
        PolygonShape shape = (PolygonShape) body.getFixtureList().first().getShape();
        Vector2 size = new Vector2();
        shape.getVertex(0, size); // Get one vertex to determine the size
        size.scl(2 * PIXELS_PER_METER); // Scale to pixels and account for full width/height
    
        // Set the sprite size to match the body dimensions
        sprite.setSize(size.x, size.y);
    
        // Set the sprite's position, origin, and rotation
        sprite.setPosition(
            position.x * PIXELS_PER_METER - sprite.getWidth() / 2,
            position.y * PIXELS_PER_METER - sprite.getHeight() / 2
        );
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setRotation(180+angle);
    
        // Draw the sprite
        sprite.draw(spriteBatch);
    }
    

    private void drawWoodSprite(Sprite sprite, Body body) {
        // Get the position and rotation of the body
        Vector2 position = body.getPosition();
        float angle = body.getAngle() * MathUtils.radiansToDegrees;
    
        // Calculate the size of the body (rectangular fixtures)
        PolygonShape shape = (PolygonShape) body.getFixtureList().first().getShape();
        Vector2 size = new Vector2();
        shape.getVertex(0, size); // Get one vertex to determine the size
        size.scl(2 * PIXELS_PER_METER); // Scale to pixels and account for full width/height
    
        // Set the sprite size to match the body dimensions
        sprite.setSize(size.x, size.y);
    
        // Set the sprite's position, origin, and rotation
        sprite.setPosition(
            position.x * PIXELS_PER_METER - sprite.getWidth() / 2,
            position.y * PIXELS_PER_METER - sprite.getHeight() / 2
        );
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setRotation(angle);
    
        // Draw the sprite
        sprite.draw(spriteBatch);
    }
    
    private void drawPigSprite(Sprite sprite, Body body) {
        // Get the position of the pig body
        Vector2 position = body.getPosition();
    
        // Get the radius of the circular fixture
        CircleShape shape = (CircleShape) body.getFixtureList().first().getShape();
        float radius = shape.getRadius() * PIXELS_PER_METER;
    
        // Set the sprite size to match the body diameter
        sprite.setSize(radius * 2, radius * 2);
    
        // Set the sprite's position (no rotation needed for circular pigs)
        sprite.setPosition(
            position.x * PIXELS_PER_METER - sprite.getWidth() / 2,
            position.y * PIXELS_PER_METER - sprite.getHeight() / 2
        );
    
        // Draw the sprite
        sprite.draw(spriteBatch);
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
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        world.dispose();
    }

    @Override
    public void pause() {
        // Implemented pause
    }

    @Override
    public void resume() {
        // Implemented resume
    }
}