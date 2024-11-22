package Main.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Level_1 implements Screen {

    private Game game;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private static final float PIXELS_PER_METER = 1f;
    private SpriteBatch spriteBatch;
    private Texture BackGround, RedbirdTexture, PinkbirdTexture, GlassTexture, CatapultTexture, BackTexture, PigTexture, WoodTexture;

    private World world;
    private Body[] birdBodies;
    private Body[] glassBodies;
    private Body[] woodBodies;
    private Body[] pigBodies;
    private Body catapultBody, groundBody;

    private float backButtonX, backButtonY, backButtonRadius;
    private int currentBirdIndex = 0; // Tracks the active bird
    private boolean isBirdLaunched = false;

    public Level_1(Game game) {
        this.game = game;
        this.backButtonRadius = 35;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        spriteBatch = new SpriteBatch();
        BackGround = new Texture("BackGround/LevelBackground.jpg");
        RedbirdTexture = new Texture("Birds/RED_Bird.png");
        PinkbirdTexture = new Texture("Birds/PINK_Bird.png");
        GlassTexture = new Texture("Blocks/Glass.png");
        WoodTexture = new Texture("Blocks/Wood.png");
        CatapultTexture = new Texture("Extras/Catapult.png");
        BackTexture = new Texture("Extras/Back.png");
        PigTexture = new Texture("Pigs/Normal_Pigs.png");

        world = new World(new Vector2(0, -9.8f), true);

        createGroundBody();
        createBirdBodies();
        createGlassBodies();
        createWoodBodies();
        createPigBodies();
        createCatapultBody();

        loadNextBirdOntoCatapult(); // Position the first bird on the catapult

        backButtonX = camera.viewportWidth - 100;
        backButtonY = camera.viewportHeight - 50;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = Gdx.graphics.getHeight() - screenY;
                float distance = Vector2.dst(screenX, screenY, backButtonX, backButtonY);
                if (distance <= backButtonRadius) {
                    goBackToPreviousScreen();
                    return true;
                }

                if (!isBirdLaunched && currentBirdIndex < birdBodies.length) {
                    launchBird(screenX, screenY);
                    return true;
                }
                return false;
            }
        });
    }

    private void createGroundBody() {
        groundBody = createRectangleBody(camera.viewportWidth / 2, 10, camera.viewportWidth, 20, BodyDef.BodyType.StaticBody);
    }

    private void createBirdBodies() {
        birdBodies = new Body[3];
        birdBodies[0] = createCircleBody(350, 125, 37.5f, BodyDef.BodyType.DynamicBody); // Initially loaded on the catapult
        birdBodies[1] = createCircleBody(200, 80, 37.5f, BodyDef.BodyType.DynamicBody);
        birdBodies[2] = createCircleBody(275, 80, 37.5f, BodyDef.BodyType.DynamicBody);
    }

    private void createGlassBodies() {
        glassBodies = new Body[5];
        float glassWidth = 17f / 2;  // Half width in meters
        float glassHeight = 90f / 2; // Half height in meters
        glassBodies[0] = createRectangleBody(900 / PIXELS_PER_METER, 200 / PIXELS_PER_METER, glassWidth, glassHeight, BodyDef.BodyType.DynamicBody);
        glassBodies[1] = createRectangleBody(940 / PIXELS_PER_METER, 200 / PIXELS_PER_METER, glassWidth, glassHeight, BodyDef.BodyType.DynamicBody);
        glassBodies[2] = createRectangleBody(980 / PIXELS_PER_METER, 200 / PIXELS_PER_METER, glassWidth, glassHeight, BodyDef.BodyType.DynamicBody);
        glassBodies[3] = createRectangleBody(1020 / PIXELS_PER_METER, 200 / PIXELS_PER_METER, glassWidth, glassHeight, BodyDef.BodyType.DynamicBody);
        glassBodies[4] = createRectangleBody(1060 / PIXELS_PER_METER, 200 / PIXELS_PER_METER, glassWidth, glassHeight, BodyDef.BodyType.DynamicBody);
    }

    private void createWoodBodies() {
        woodBodies = new Body[2];
        woodBodies[0] = createRectangleBody(950, 300, 200, 30, BodyDef.BodyType.DynamicBody);
        woodBodies[1] = createRectangleBody(1050, 400, 100, 30, BodyDef.BodyType.DynamicBody);
    }

    private void createPigBodies() {
        pigBodies = new Body[2];
        pigBodies[0] = createCircleBody(1000, 250, 40f, BodyDef.BodyType.DynamicBody);
        pigBodies[1] = createCircleBody(1100, 450, 40f, BodyDef.BodyType.DynamicBody);
    }

    private void createCatapultBody() {
        catapultBody = createRectangleBody(350, 80, 52.5f, 125, BodyDef.BodyType.StaticBody);
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
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 1;
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
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.3f;
        body.createFixture(fixtureDef);

        shape.dispose();
        return body;
    }

    private void launchBird(float screenX, float screenY) {
        Body bird = birdBodies[currentBirdIndex];
        Vector2 launchDirection = new Vector2(screenX - bird.getPosition().x, screenY - bird.getPosition().y).nor().scl(-500);
        bird.applyLinearImpulse(launchDirection, bird.getWorldCenter(), true);
        isBirdLaunched = true;
    }

    private void loadNextBirdOntoCatapult() {
        if (currentBirdIndex < birdBodies.length) {
            Body nextBird = birdBodies[currentBirdIndex];
            nextBird.setTransform(350, 125, 0); // Position the bird on the catapult
            nextBird.setLinearVelocity(0, 0); // Reset velocity
            nextBird.setAngularVelocity(0);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        world.step(1 / 60f, 6, 2);

        if (isBirdLaunched) {
            Body activeBird = birdBodies[currentBirdIndex];
            if (activeBird.getLinearVelocity().len() < 0.1 && activeBird.getPosition().y < 0.1) {
                currentBirdIndex++;
                if (currentBirdIndex < birdBodies.length) {
                    isBirdLaunched = false;
                    loadNextBirdOntoCatapult(); // Load the next bird
                }
            }
        }

        spriteBatch.begin();
        spriteBatch.draw(BackGround, 0, 0, camera.viewportWidth, camera.viewportHeight);

        drawBirds();
        drawGlassBlocks();
        drawWoodBlocks();
        drawPigs();
        drawCatapult();
        drawBackButton();

        spriteBatch.end();
    }

    private void drawBirds() {
        for (int i = 0; i < birdBodies.length; i++) {
            if (i >= currentBirdIndex) {
                drawSprite(i == 1 ? PinkbirdTexture : RedbirdTexture, birdBodies[i], 37.5f);
            }
        }
    }

    private void drawGlassBlocks() {
        for (Body glassBody : glassBodies) {
            drawSprite(GlassTexture, glassBody, 17.5f, 87.5f);
        }
    }

    private void drawWoodBlocks() {
        for (Body woodBody : woodBodies) {
            drawSprite(WoodTexture, woodBody, 100f, 15f);
        }
    }

    private void drawPigs() {
        for (Body pigBody : pigBodies) {
            drawSprite(PigTexture, pigBody, 37.5f);
        }
    }

    private void drawCatapult() {
        drawSprite(CatapultTexture, catapultBody, 26.25f, 62.5f);
    }

    private void drawBackButton() {
        spriteBatch.draw(BackTexture, backButtonX - backButtonRadius, backButtonY - backButtonRadius, backButtonRadius * 2, backButtonRadius * 2);
    }

    private void drawSprite(Texture texture, Body body, float radius) {
        drawSprite(texture, body, radius, radius);
    }

    private void drawSprite(Texture texture, Body body, float widthHalf, float heightHalf) {
        float x = body.getPosition().x - widthHalf;
        float y = body.getPosition().y - heightHalf;
    
        // Convert dimensions from meters to pixels
        spriteBatch.draw(texture,
            (x - widthHalf) * PIXELS_PER_METER, // X in pixels
            (y - heightHalf) * PIXELS_PER_METER, // Y in pixels
            (widthHalf * 2) * PIXELS_PER_METER, // Width in pixels
            (heightHalf * 2) * PIXELS_PER_METER  // Height in pixels
        );
    }
    

    private void goBackToPreviousScreen() {
        game.setScreen(new LevelScreen(game));
        dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        BackGround.dispose();
        RedbirdTexture.dispose();
        PinkbirdTexture.dispose();
        GlassTexture.dispose();
        WoodTexture.dispose();
        CatapultTexture.dispose();
        BackTexture.dispose();
        PigTexture.dispose();
        world.dispose();
    }
}
