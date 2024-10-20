package Main.AngryBirds;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;

// Screen 2: Main Page

public class LoadScreen implements Screen {
    private Game game;  // Changed 'Game' to 'game' for clarity
    private ShapeRenderer shapeRenderer;

    final float squareSize = 100;
    final float paddingTop = 225;
    final float paddingBottom = 200;

    private OrthographicCamera camera;
    private FitViewport viewport;

    public LoadScreen(Game game) {
        this.game = game;  // Changed 'Game' to 'game' for clarity
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);  // Set camera position
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a white background
        ScreenUtils.clear(1f, 1f, 1f, 1f);  // Set background to white

        // Begin ShapeRenderer only once
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Calculate the Load A Position with padding
        float rect1X = camera.position.x - (camera.viewportWidth / 2) + (camera.viewportWidth / 2 - squareSize / 2) - 275;
        float rect1Y = camera.position.y + (camera.viewportHeight / 2) - paddingTop - squareSize / 2;
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(rect1X, rect1Y, squareSize, squareSize);

        // Calculate the Load B Position with padding
        float rect2X = camera.position.x - (camera.viewportWidth / 2) + (camera.viewportWidth / 2 - squareSize / 2) + 75;
        float rect2Y = camera.position.y + (camera.viewportHeight / 2) - paddingTop - squareSize / 2;
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(rect2X, rect2Y, squareSize, squareSize);

        // Calculate the Load C Position with padding
        float rect3X = camera.position.x - (camera.viewportWidth / 2) + (camera.viewportWidth / 2 - squareSize/ 2) - 275;
        float rect3Y = camera.position.y - (camera.viewportHeight / 2) + paddingBottom - squareSize/ 2;
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(rect3X, rect3Y, squareSize, squareSize);

        // Calculate the Load D Position with padding
        float rect4X = camera.position.x - (camera.viewportWidth / 2) + (camera.viewportWidth / 2 - squareSize / 2) + 75;
        float rect4Y = camera.position.y - (camera.viewportHeight / 2) + paddingBottom - squareSize/ 2;
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(rect4X, rect4Y, squareSize, squareSize);

        // End ShapeRenderer once all shapes are drawn
        shapeRenderer.end();
    }

    @Override
    public void pause() {
        // Handle pause logic
    }

    @Override
    public void resume() {
        // Handle resume logic
    }

    @Override
    public void hide() {
        // Clean up when the screen is hidden
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();  // Dispose of ShapeRenderer to avoid memory leaks
    }
}
