package Main.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;

public class LoadScreen implements Screen {
    private Game game;
    private SpriteBatch spriteBatch;
    private Texture BackGround;
    private Texture Load_A;
    private Texture Load_B;
    private Texture Load_C;
    private Texture Load_D;
    private Texture BackTexture;

    final float rectWidth = 200;
    final float rectHeight = 75;
    final float paddingTop = 225;
    final float paddingBottom = 200;

    private OrthographicCamera camera;
    private FitViewport viewport;

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
        BackTexture = new Texture("Extras/Back.png");
        BackGround = new Texture("BackGround/Background.jpg");
        Load_A = new Texture("LoadGame_Buttons/Load_A.png");
        Load_B = new Texture("LoadGame_Buttons/Load_B.png");
        Load_C = new Texture("LoadGame_Buttons/Load_C.png");
        Load_D = new Texture("LoadGame_Buttons/Load_D.png");
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

        // Rectangle positions
        float rect1X = camera.position.x - (camera.viewportWidth / 2) + (camera.viewportWidth / 2 - rectWidth / 2) - 275;
        float rect1Y = camera.position.y + (camera.viewportHeight / 2) - paddingTop - rectHeight / 2;
        float rect2X = camera.position.x - (camera.viewportWidth / 2) + (camera.viewportWidth / 2 - rectWidth / 2) + 75;
        float rect2Y = camera.position.y + (camera.viewportHeight / 2) - paddingTop - rectHeight / 2;
        float rect3X = camera.position.x - (camera.viewportWidth / 2) + (camera.viewportWidth / 2 - rectWidth / 2) - 275;
        float rect3Y = camera.position.y - (camera.viewportHeight / 2) + paddingBottom - rectHeight / 2;
        float rect4X = camera.position.x - (camera.viewportWidth / 2) + (camera.viewportWidth / 2 - rectWidth / 2) + 75;
        float rect4Y = camera.position.y - (camera.viewportHeight / 2) + paddingBottom - rectHeight / 2;

        // Draw the textures wrapping the rectangles
        spriteBatch.begin();
        spriteBatch.draw(Load_A, rect1X, rect1Y, rectWidth, rectHeight);
        spriteBatch.draw(Load_B, rect2X, rect2Y, rectWidth, rectHeight);
        spriteBatch.draw(Load_C, rect3X, rect3Y, rectWidth, rectHeight);
        spriteBatch.draw(Load_D, rect4X, rect4Y, rectWidth, rectHeight);

        // Draw the back button texture
        float circleRadius = 35;
        float circleX = camera.viewportWidth - circleRadius - 20;
        float circleY = camera.viewportHeight - circleRadius - 20;
        float textureWidth = 2 * circleRadius;
        float textureHeight = 2 * circleRadius;

        spriteBatch.draw(BackTexture, circleX - circleRadius, circleY - circleRadius, textureWidth, textureHeight);
        spriteBatch.end();

        // Handle mouse input for button interaction
        if (Gdx.input.isButtonJustPressed(0)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();

            Vector3 mousePos = new Vector3(mouseX, mouseY, 0);
            camera.unproject(mousePos);

            // Check if the back button was clicked
            float dx = mousePos.x - circleX;
            float dy = mousePos.y - circleY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance <= circleRadius) {
                game.setScreen(new MainScreen(game));
            }

            // Check if any of the rectangles (buttons) was clicked
            if (mousePos.x >= rect1X && mousePos.x <= rect1X + rectWidth && mousePos.y >= rect1Y && mousePos.y <= rect1Y + rectHeight) {
                game.setScreen(new LevelScreen(game));
            }

            if (mousePos.x >= rect2X && mousePos.x <= rect2X + rectWidth && mousePos.y >= rect2Y && mousePos.y <= rect2Y + rectHeight) {
                game.setScreen(new LevelScreen(game));
            }

            if (mousePos.x >= rect3X && mousePos.x <= rect3X + rectWidth && mousePos.y >= rect3Y && mousePos.y <= rect3Y + rectHeight) {
                game.setScreen(new LevelScreen(game));
            }

            if (mousePos.x >= rect4X && mousePos.x <= rect4X + rectWidth && mousePos.y >= rect4Y && mousePos.y <= rect4Y + rectHeight) {
                game.setScreen(new LevelScreen(game));
            }
        }
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
        Load_A.dispose();
        Load_B.dispose();
        Load_C.dispose();
        Load_D.dispose();
        BackTexture.dispose();
    }
}
