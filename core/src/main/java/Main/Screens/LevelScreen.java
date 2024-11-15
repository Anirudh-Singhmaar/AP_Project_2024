package Main.Screens;

import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelScreen implements Screen {
    private final Game game;
    private ShapeRenderer shapeRenderer;
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

        // Set up level objects (assuming LevelGenerator works correctly)
        List<LevelGenerator.GameObject> levelObjects = LevelGenerator.loadLevel(2);
        for (LevelGenerator.GameObject obj : LevelGenerator.loadLevel(2)) {
            if (!obj.type.equals("catapult")) { // Skip catapult in level selector
                Image image = new Image(obj.texture);
                image.setPosition(obj.x, obj.y);
                if (obj.type.equals("block")) {
                    image.setSize(obj.width, obj.height);
                } else if (obj.type.equals("target")) {
                    image.setSize(obj.radius * 2, obj.radius * 2);
                }
                stage.addActor(image);
            }
        }
        

        // Setup buttons
        setupLevelButtons();
        setupBackButton();
    }

    private void setupLevelButtons() {
        // Level 1 Button
        ImageButton Level_1Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Level_1)));
        Level_1Button.setPosition(camera.position.x - 300, camera.position.y - 75 / 2);
        Level_1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level_1(game));
            }
        });
        stage.addActor(Level_1Button);

        // Level 2 Button
        ImageButton Level_2Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Level_2)));
        Level_2Button.setPosition(camera.position.x, camera.position.y - 75 / 2);
        Level_2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level_2(game));
            }
        });
        stage.addActor(Level_2Button);

        // Level 3 Button
        ImageButton Level_3Button = new ImageButton(new TextureRegionDrawable(new TextureRegion(Level_3)));
        Level_3Button.setPosition(camera.position.x + 300, camera.position.y - 75 / 2);
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
