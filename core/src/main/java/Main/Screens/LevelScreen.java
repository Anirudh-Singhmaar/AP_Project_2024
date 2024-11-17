package Main.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelScreen implements Screen {
    private final Game game;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;
    private Texture BackGround, BackTexture, Level_1,Level_2,Level_3;
    private Stage stage;

    public LevelScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Initialize camera, viewport, and sprite batch
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();

        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        // Load textures
        BackGround = new Texture("BackGround/Background.jpg");
        BackTexture = new Texture("Extras/Back.png");
        Level_1 = new Texture("Level_Buttons/Level_1.png");
        Level_2 = new Texture("Level_Buttons/Level_2.png");
        Level_3 = new Texture("Level_Buttons/Level_3.png");

        // Setup buttons
        setupLevelButtons();
        setupBackButton();
    }
    private void setupLevelButtons() {
        // Button height and width (assuming buttons are all the same size)
        float buttonWidth = Level_1.getWidth();
        float buttonHeight = Level_1.getHeight();
    
        // Calculate center positions for each button
        float centerX = viewport.getWorldWidth() / 2 - buttonWidth / 2;
        float centerY = viewport.getWorldHeight() / 2;
    
        // Level 1 Button
        ImageButton Level_1Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Level_1)));
        Level_1Button.setSize(buttonWidth, buttonHeight);
        Level_1Button.setPosition(centerX - 300, centerY - buttonHeight / 2); // Offset for spacing
        Level_1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level_1(game));
            }
        });
        stage.addActor(Level_1Button);
    
        // Level 2 Button
        ImageButton Level_2Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Level_2)));
        Level_2Button.setSize(buttonWidth, buttonHeight);
        Level_2Button.setPosition(centerX, centerY - buttonHeight / 2); // Centered button
        Level_2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level_2(game));
            }
        });
        stage.addActor(Level_2Button);
    
        // Level 3 Button
        ImageButton Level_3Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Level_3)));
        Level_3Button.setSize(buttonWidth, buttonHeight);
        Level_3Button.setPosition(centerX + 300, centerY - buttonHeight / 2); // Offset for spacing
        Level_3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level_3(game));
            }
        });
        stage.addActor(Level_3Button);
    }
    

    private void setupBackButton() {
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(BackTexture)));
        backButton.setSize(70, 70); // Circle radius * 2
        backButton.setPosition(viewport.getWorldWidth() - 90, viewport.getWorldHeight() - 90);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);

        // Draw the background
        spriteBatch.begin();
        spriteBatch.draw(BackGround, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        spriteBatch.end();

        // Draw buttons and stage elements
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
        BackTexture.dispose();
        stage.dispose();
    }
}
