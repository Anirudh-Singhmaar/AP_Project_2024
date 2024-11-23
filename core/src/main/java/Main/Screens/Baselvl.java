// package Main.Screens;

// import com.badlogic.gdx.Game;
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Screen;
// import com.badlogic.gdx.graphics.OrthographicCamera;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
// import com.badlogic.gdx.math.Vector2;
// import com.badlogic.gdx.physics.box2d.Body;
// import com.badlogic.gdx.physics.box2d.BodyDef;
// import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
// import com.badlogic.gdx.physics.box2d.CircleShape;
// import com.badlogic.gdx.physics.box2d.FixtureDef;
// import com.badlogic.gdx.physics.box2d.World;
// import com.badlogic.gdx.utils.viewport.FitViewport;


// public class Baselvl implements Screen{
//     private Game game;
//     private ShapeRenderer shapeRenderer;
//     private OrthographicCamera camera;
//     private FitViewport viewport;

//     private SpriteBatch spriteBatch;
//     private Texture BackGround, RedbirdTexture, PinkbirdTexture, GlassTexture, CatapultTexture, BackTexture, PigTexture;

//     private World world;
//     private Body[] birdBodies;
//     private Body leftGlassBody, rightGlassBody, topGlassBody, catapultBody, groundBody, pigBody;

//     private float backButtonX, backButtonY, backButtonRadius;
//     private int currentBirdIndex = 0; // Tracks the active bird

//     @Override
//     public void show() {
//         world = new World(new Vector2(0, -9.8f), true);
//         Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
//         camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        
//         BodyDef balldef = new BodyDef();
//         balldef.type = BodyDef.BodyType.DynamicBody;
//         balldef.position.set(35,12);
//         //ball shape

//         CircleShape shape = new CircleShape();
//         shape.setRadius(50f);

//         FixtureDef fixtureDef = new FixtureDef();
//         fixtureDef.shape = shape;
//         fixtureDef.density = 25f;
//         fixtureDef.friction = 3/5f;
//         fixtureDef.restitution = 0.75f;

//         world.createBody(balldef).createFixture(fixtureDef);
//         shape.dispose();
//     }

//     @Override
//     public void render(float delta) {
        
//     }

//     @Override
//     public void resize(int width, int height) {
        
//     }

//     @Override
//     public void pause() {
        
//     }

//     @Override
//     public void resume() {
        
//     }

//     @Override
//     public void hide() {
       
//     }

//     @Override
//     public void dispose() {
       
//     }

// }


