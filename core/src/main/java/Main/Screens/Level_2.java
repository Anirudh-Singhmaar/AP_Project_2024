package Main.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level_2 implements Screen {
    private static final float PIXELS_PER_METER = 100.0F;
    private Game game;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private World world;
    private SpriteBatch spriteBatch;

    // Textures
    private Texture birdTexture;
    private Texture woodTexture;
    private Texture pigTexture;
    private Texture groundTexture;
    private Texture catapultTexture;

    private Body[] birdBodies;
    private Body leftWoodBody;
    private Body rightWoodBody;
    private Body topWoodBody;
    private Body catapultBody;
    private Body groundBody;
    private Body pigBody;

    private Box2DDebugRenderer debugRenderer;
    private int currentBirdIndex = 0;
    private boolean isBirdLaunched = false;

    public Level_2(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280.0F, 720.0F, camera);
        viewport.apply();
        camera.position.set(viewport.getWorldWidth() / 2.0F, viewport.getWorldHeight() / 2.0F, 0.0F);
        camera.update();

        world = new World(new Vector2(0.0F, -9.8F), true);
        debugRenderer = new Box2DDebugRenderer();

        spriteBatch = new SpriteBatch();

        // Load textures
        birdTexture = new Texture("Birds/RED_Bird.png");
        woodTexture = new Texture("Blocks/Wood.png");
        pigTexture = new Texture("Pigs/Golden_pigs.png");
        groundTexture = new Texture("BackGround/Background.jpg");
        catapultTexture = new Texture("Extras/Catapult.png");

        createGroundBody();
        createBirdBodies();
        createWoodBodiesWithGravity();
        createCatapultBody();
        // loadNextBirdOntoCatapult();
    }

    private void createGroundBody() {
        groundBody = createRectangleBody(6.5F, 0.1F, 12.8F, 0.2F, BodyType.StaticBody);
    }

    private void createBirdBodies() {
        birdBodies = new Body[3];
        birdBodies[0] = createCircleBody(3.5F, 1.25F, 0.375F, BodyType.DynamicBody);
        birdBodies[1] = createCircleBody(2.0F, 0.8F, 0.375F, BodyType.DynamicBody);
        birdBodies[2] = createCircleBody(2.75F, 0.8F, 0.375F, BodyType.DynamicBody);
    }

    private void createWoodBodiesWithGravity() {
        leftWoodBody = createRectangleBody(8.8F, 1.5F, 0.35F, 1.75F, BodyType.DynamicBody);
        rightWoodBody = createRectangleBody(10.8F, 1.5F, 0.35F, 1.75F, BodyType.DynamicBody);
        topWoodBody = createRectangleBody(9.8F, 2.6F, 2.0F, 0.3F, BodyType.DynamicBody);
        pigBody = createCircleBody(9.8F, 1.5F, 0.35F, BodyType.DynamicBody);
    }

    private void createCatapultBody() {
        catapultBody = createRectangleBody(3.5F, 0.8F, 0.525F, 1.25F, BodyType.StaticBody);
    }

    private Body createCircleBody(float x, float y, float radius, BodyType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0F;
        fixtureDef.restitution = 0.5F;
        fixtureDef.friction = 1.75F;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    private Body createRectangleBody(float x, float y, float width, float height, BodyType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2.0F, height / 2.0F);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5F;
        fixtureDef.friction = 0.4F;
        fixtureDef.restitution = 0.2F;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1.0F, 1.0F, 1.0F, 1.0F);
        world.step(0.016666668F, 6, 2);

        // Render sprites
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        drawSprites();
        spriteBatch.end();

        debugRenderer.render(world, camera.combined);
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
       
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        world.dispose();
        spriteBatch.dispose();
        debugRenderer.dispose();

        birdTexture.dispose();
        woodTexture.dispose();
        pigTexture.dispose();
        groundTexture.dispose();
        catapultTexture.dispose();
    }
}
