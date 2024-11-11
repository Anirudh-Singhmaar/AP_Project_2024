package Main.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.physics.box2d.*;

public class Level_1 implements Screen {

    private Game game;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private SpriteBatch spriteBatch;
    private Texture BackGround, RedbirdTexture, PinkbirdTexture, GlassTexture, CatapultTexture, BackTexture;

    private World world;
    private Body redBirdBody1, pinkBirdBody, redBirdBody2, leftGlassBody, rightGlassBody, topGlassBody, catapultBody, groundBody;

    private float backButtonX, backButtonY, backButtonRadius;  // Store the position and radius of the back button

    public Level_1(Game game) {
        this.game = game;
        this.backButtonRadius = 35;  // Set the radius of the back button to 35
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
        CatapultTexture = new Texture("Extras/Catapult.png");
        BackTexture = new Texture("Extras/Back.png");

        world = new World(new Vector2(0, -9.8f), true);  // Gravity

        createGroundBody();  // Create the ground
        createBirdBodies();
        createGlassBodies();
        createCatapultBody();

        // Calculate the back button position (top-right corner)
        backButtonX = camera.viewportWidth - 100; // Adjust the X position for the button
        backButtonY = camera.viewportHeight - 50; // Adjust the Y position for the button

        // Set the input processor for back button
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // Adjust screenY because libGDX's Y-axis increases downwards, while the camera Y-axis increases upwards
                screenY = Gdx.graphics.getHeight() - screenY;  // Flip the Y coordinate

                // Check if the touch is inside the circular back button
                float distance = Vector2.dst(screenX, screenY, backButtonX, backButtonY);
                if (distance <= backButtonRadius) {
                    goBackToPreviousScreen(); // Go back to the previous screen
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
        redBirdBody1 = createCircleBody(125, 80, 37.5f, BodyDef.BodyType.DynamicBody);
        pinkBirdBody = createCircleBody(200, 80, 37.5f, BodyDef.BodyType.DynamicBody);
        redBirdBody2 = createCircleBody(275, 80, 37.5f, BodyDef.BodyType.DynamicBody);
    }

    private void createGlassBodies() {
        leftGlassBody = createRectangleBody(880, 90, 35, 175, BodyDef.BodyType.StaticBody);
        rightGlassBody = createRectangleBody(1080, 90, 35, 175, BodyDef.BodyType.StaticBody);
        topGlassBody = createRectangleBody(980, 260, 200, 30, BodyDef.BodyType.StaticBody);
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

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        world.step(1 / 60f, 6, 2);  // Update Box2D world

        spriteBatch.begin();
        spriteBatch.draw(BackGround, 0, 0, camera.viewportWidth, camera.viewportHeight);

        // Draw birds at their body positions
        float firstBirdX = redBirdBody1.getPosition().x - 37.5f;
        float firstBirdY = redBirdBody1.getPosition().y - 37.5f;
        spriteBatch.draw(RedbirdTexture, firstBirdX, firstBirdY, 75, 75);
        
        float secondBirdX = pinkBirdBody.getPosition().x - 37.5f;
        float secondBirdY = pinkBirdBody.getPosition().y - 37.5f;
        spriteBatch.draw(PinkbirdTexture, secondBirdX, secondBirdY, 75, 75);

        float thirdBirdX = redBirdBody2.getPosition().x - 37.5f;
        float thirdBirdY = redBirdBody2.getPosition().y - 37.5f;
        spriteBatch.draw(RedbirdTexture, thirdBirdX, thirdBirdY, 75, 75);

        // Draw glass blocks at their body positions
        float leftGlassX = leftGlassBody.getPosition().x - 17.5f;
        float leftGlassY = leftGlassBody.getPosition().y - 87.5f;
        spriteBatch.draw(GlassTexture, leftGlassX, leftGlassY, 35, 175);
        
        float rightGlassX = rightGlassBody.getPosition().x - 17.5f;
        float rightGlassY = rightGlassBody.getPosition().y - 87.5f;
        spriteBatch.draw(GlassTexture, rightGlassX, rightGlassY, 35, 175);
        
        float topGlassX = topGlassBody.getPosition().x - 100f;
        float topGlassY = topGlassBody.getPosition().y - 0.5f;
        spriteBatch.draw(GlassTexture, topGlassX, topGlassY, 200, 30);

        // Draw catapult at its body position
        float catapultX = catapultBody.getPosition().x - 26.25f;
        float catapultY = catapultBody.getPosition().y - 62.5f;
        spriteBatch.draw(CatapultTexture, catapultX, catapultY, 52.5f, 125);

        // Draw circular back button at the top-right corner
        spriteBatch.draw(BackTexture, backButtonX - backButtonRadius, backButtonY - backButtonRadius, backButtonRadius * 2, backButtonRadius * 2);

        spriteBatch.end();
    }

    private void goBackToPreviousScreen() {
        game.setScreen(new LevelScreen(game));  // Assuming you have a MainMenu screen
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
        spriteBatch.dispose();
        BackGround.dispose();
        RedbirdTexture.dispose();
        PinkbirdTexture.dispose();
        GlassTexture.dispose();
        CatapultTexture.dispose();
        BackTexture.dispose();
    }
}
