package Main.AngryBirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LoadScreen implements Screen {
    private Game game;
    private SpriteBatch spriteBatch;
    private Texture BackGround;
    private Texture BackTexture;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Stage stage;

    public LoadScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        // Load textures
        BackGround = new Texture("BackGround/Background.jpg");
        BackTexture = new Texture("Extras/Back.png");

        // Load button textures
        Texture loadATexture = new Texture("LoadGame_Buttons/Load_A.png");
        Texture loadBTexture = new Texture("LoadGame_Buttons/Load_B.png");
        Texture loadCTexture = new Texture("LoadGame_Buttons/Load_C.png");
        Texture loadDTexture = new Texture("LoadGame_Buttons/Load_D.png");

        // Create buttons
        ImageButton loadAButton = new ImageButton(new TextureRegionDrawable(loadATexture));
        ImageButton loadBButton = new ImageButton(new TextureRegionDrawable(loadBTexture));
        ImageButton loadCButton = new ImageButton(new TextureRegionDrawable(loadCTexture));
        ImageButton loadDButton = new ImageButton(new TextureRegionDrawable(loadDTexture));
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(BackTexture));

        // Set button sizes and positions
        float buttonWidth = 200;
        float buttonHeight = 75;
        loadAButton.setSize(buttonWidth, buttonHeight);
        loadBButton.setSize(buttonWidth, buttonHeight);
        loadCButton.setSize(buttonWidth, buttonHeight);
        loadDButton.setSize(buttonWidth, buttonHeight);

        loadAButton.setPosition(365, 445);
        loadBButton.setPosition(715, 445);
        loadCButton.setPosition(365, 245);
        loadDButton.setPosition(715, 245);

        // Set back button position and size
        float circleRadius = 35;
        backButton.setSize(circleRadius * 2, circleRadius * 2);
        backButton.setPosition(camera.viewportWidth - circleRadius * 2 - 20, camera.viewportHeight - circleRadius * 2 - 20);

        // Add listeners for buttons
        loadAButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        loadBButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        loadCButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        loadDButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        // Add buttons to the stage
        stage.addActor(loadAButton);
        stage.addActor(loadBButton);
        stage.addActor(loadCButton);
        stage.addActor(loadDButton);
        stage.addActor(backButton);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(1f, 1f, 1f, 1f);

        // Draw the background
        spriteBatch.begin();
        spriteBatch.draw(BackGround, 0, 0, camera.viewportWidth, camera.viewportHeight);
        spriteBatch.end();

        // Draw the stage with buttons
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        spriteBatch.dispose();
        BackGround.dispose();
        stage.dispose();
        BackTexture.dispose();
    }
}
