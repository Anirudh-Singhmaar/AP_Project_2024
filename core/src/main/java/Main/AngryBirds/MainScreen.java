package Main.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;

public class MainScreen implements Screen {
    private Game game;
    private SpriteBatch spriteBatch;
    private Texture BackGround;
    private Texture Title;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Stage stage;

    public MainScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();

        // Load the textures
        BackGround = new Texture(Gdx.files.internal("BackGround/Background.jpg"));
        Title = new Texture(Gdx.files.internal("BackGround/Title.png"));

        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        viewport.apply();

        // Create the stage and set it as the input processor
        stage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        // Load button textures
        Texture playTexture = new Texture(Gdx.files.internal("MainScreen_Buttons/Play.png"));
        Texture loadTexture = new Texture(Gdx.files.internal("MainScreen_Buttons/LoadGame.png"));
        Texture exitTexture = new Texture(Gdx.files.internal("MainScreen_Buttons/Exit.png"));

        // Create buttons with texture regions
        ImageButton playButton = new ImageButton(new TextureRegionDrawable(playTexture));
        ImageButton loadButton = new ImageButton(new TextureRegionDrawable(loadTexture));
        ImageButton exitButton = new ImageButton(new TextureRegionDrawable(exitTexture));

        // Set the same size for each button
        float buttonWidth = 200;
        float buttonHeight = 75;
        playButton.setSize(buttonWidth, buttonHeight);
        loadButton.setSize(buttonWidth, buttonHeight);
        exitButton.setSize(buttonWidth, buttonHeight);

        // Set button positions
        playButton.setPosition(490, 200);     // Adjust position based on viewport size
        loadButton.setPosition(240, 200);     // Adjust position based on viewport size
        exitButton.setPosition(740, 200);     // Adjust position based on viewport size

        // Add listeners for buttons
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoadScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add buttons to stage
        stage.addActor(playButton);
        stage.addActor(loadButton);
        stage.addActor(exitButton);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a white background
        ScreenUtils.clear(1f, 1f, 1f, 1f);

        spriteBatch.begin();
        spriteBatch.draw(BackGround, 0, 0, camera.viewportWidth, camera.viewportHeight);  // Draw the background
        spriteBatch.draw(Title, camera.viewportWidth / 2 - 100, 450, 200, 75); // Draw title centered
        spriteBatch.end();

        // Draw the stage containing buttons
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        BackGround.dispose();
        Title.dispose();
        stage.dispose();
    }
}
