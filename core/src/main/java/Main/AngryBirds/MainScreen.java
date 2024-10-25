package Main.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;

public class MainScreen implements Screen {
    private Game game;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch spriteBatch;
    private Texture BackGround;
    private Texture Play;
    private Texture Exit;
    private Texture LoadGame;
    private Texture Title;

    final float rectWidth = 200;
    final float rectHeight = 75;
    final float paddingTop = 225;
    final float paddingBottom = 200;

    private OrthographicCamera camera;
    private FitViewport viewport;

    public MainScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        // Load the textures
        BackGround = new Texture(Gdx.files.internal("BackGround/Background.jpg"));
        Play = new Texture(Gdx.files.internal("MainScreen_Buttons/Play.png"));
        Exit = new Texture(Gdx.files.internal("MainScreen_Buttons/Exit.png"));
        LoadGame = new Texture(Gdx.files.internal("MainScreen_Buttons/LoadGame.png"));
        Title = new Texture(Gdx.files.internal("BackGround/Title.png"));

        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        viewport.apply();
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

        // Calculate the positions for each button
        float rect1X = camera.position.x - (camera.viewportWidth / 2) + (camera.viewportWidth / 2 - rectWidth / 2);
        float rect1Y = camera.position.y + (camera.viewportHeight / 2) - paddingTop - rectHeight / 2;

        float rect2X = rect1X - 250;
        float rect2Y = camera.position.y - (camera.viewportHeight / 2) + paddingBottom - rectHeight / 2;

        float rect3X = rect1X;
        float rect3Y = rect2Y;

        float rect4X = rect1X + 250;
        float rect4Y = rect2Y;

        // Draw the title texture at the calculated position
        spriteBatch.draw(Title, rect1X, rect1Y, rectWidth, rectHeight);

        // Draw the Load Game texture
        spriteBatch.draw(LoadGame, rect2X, rect2Y, rectWidth, rectHeight);

        // Draw the Play Game texture
        spriteBatch.draw(Play, rect3X, rect3Y, rectWidth, rectHeight);

        // Draw the Exit texture
        spriteBatch.draw(Exit, rect4X, rect4Y, rectWidth, rectHeight);

        spriteBatch.end();

        // Handle Input: Detect Mouse Click and switch to load screen when rect2, rect3, or rect4 is clicked
        if (Gdx.input.isButtonJustPressed(0)) {  // 0 is the left mouse button
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();

            // Convert mouse coordinates to world coordinates
            Vector3 mousePos = new Vector3(mouseX, mouseY, 0);
            camera.unproject(mousePos);

            if (mousePos.x >= rect2X && mousePos.x <= rect2X + rectWidth && mousePos.y >= rect2Y && mousePos.y <= rect2Y + rectHeight) {
                game.setScreen(new LoadScreen(game));  // Load Screen
            }

            if (mousePos.x >= rect3X && mousePos.x <= rect3X + rectWidth && mousePos.y >= rect3Y && mousePos.y <= rect3Y + rectHeight) {
                game.setScreen(new LevelScreen(game));  // Level Screen
            }

            if (mousePos.x >= rect4X && mousePos.x <= rect4X + rectWidth && mousePos.y >= rect4Y && mousePos.y <= rect4Y + rectHeight) {
                Gdx.app.exit();  // Exit Application
            }
        }
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
        shapeRenderer.dispose();
        spriteBatch.dispose();
        BackGround.dispose();
        Play.dispose();
        Exit.dispose();
        LoadGame.dispose();
        Title.dispose();
    }
}
