package Main.Screens;

import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private Game game;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;
    private Texture BackGround, Level_1, Level_2, Level_3, BackTexture;
    private Stage stage;

    final float rectWidth = 200;
    final float rectHeight = 75;
    final float circleRadius = 35;

    public LevelScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        SpriteBatch batch = new SpriteBatch();
        stage = new Stage();

        spriteBatch = new SpriteBatch();
        BackGround = new Texture("BackGround/Background.jpg");
        Level_1 = new Texture("Level_Buttons/Level_1.png");
        Level_2 = new Texture("Level_Buttons/Level_2.png");
        Level_3 = new Texture("Level_Buttons/Level_3.png");
        BackTexture = new Texture("Extras/Back.png");

        stage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(stage);
        List<LevelGenerator.GameObject> levelObjects = LevelGenerator.loadLevel(1);
        for (LevelGenerator.GameObject obj : levelObjects) {
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

        // Setting up the buttons
        setupLevelButtons();
        setupBackButton();
    }

    private void setupLevelButtons() {
        // Level 1 Button
        ImageButton Level_1Button = new ImageButton(new TextureRegionDrawable(new Texture("Level_Buttons/Level_1.png")));
        Level_1Button.setPosition(camera.position.x - 300 - rectWidth / 2, camera.position.y - rectHeight / 2);
        Level_1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level_1(game));
            }
        });
        stage.addActor(Level_1Button);

        // Level 2 Button
        ImageButton Level_2Button = new ImageButton(new TextureRegionDrawable(new Texture("Level_Buttons/Level_2.png")));
        Level_2Button.setPosition(camera.position.x - rectWidth / 2, camera.position.y - rectHeight / 2);
        Level_2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level_2(game));
            }
        });
        stage.addActor(Level_2Button);

        // Level 3 Button
        ImageButton Level_3Button = new ImageButton(new TextureRegionDrawable(new Texture("Level_Buttons/Level_3.png")));
        Level_3Button.setPosition(camera.position.x + 300 - rectWidth / 2, camera.position.y - rectHeight / 2);
        Level_3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level_3(game));
            }
        });
        stage.addActor(Level_3Button);
    }

    private void setupBackButton() {
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(BackTexture));
        backButton.setSize(circleRadius * 2, circleRadius * 2);
        backButton.setPosition(camera.viewportWidth - circleRadius * 2 - 20, camera.viewportHeight - circleRadius * 2 - 20);
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
        spriteBatch.draw(BackGround, 0, 0, camera.viewportWidth, camera.viewportHeight);
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
        shapeRenderer.dispose();
        BackGround.dispose();
        Level_1.dispose();
        Level_2.dispose();
        Level_3.dispose();
        BackTexture.dispose();
        stage.dispose();
        batch.dispose();
    }
}
