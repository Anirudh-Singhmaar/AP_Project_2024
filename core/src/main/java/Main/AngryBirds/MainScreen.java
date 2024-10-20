package Main.AngryBirds;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;

public class MainScreen implements Screen {
    private Game Game;
    private ShapeRenderer shapeRenderer;

    final float rectWidth = 200;
    final float rectHeight = 100;
    final float paddingTop = 125;

    private OrthographicCamera camera;
    private FitViewport viewport;

    public MainScreen(Game Game) {
        this.Game = Game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        viewport.apply();
    }

    @Override
    public void resize(int width, int height) {
        // Adjust the viewport when the window is resized
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a white background
        ScreenUtils.clear(1f, 1f, 1f, 1f);  // Set background to white

        // Calculate the rectangle's position
        float rectX = camera.position.x - (camera.viewportWidth / 2) + (camera.viewportWidth / 2 - rectWidth / 2);  // Horizontal center
        float rectY = camera.position.y + (camera.viewportHeight / 2) - paddingTop - rectHeight / 2;  // Top-center with padding

        // Use the camera to begin drawing with the correct aspect ratio
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Draw the rectangle using ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);  // Set the rectangle color
        shapeRenderer.rect(rectX, rectY, rectWidth, rectHeight);  // Draw the rectangle
        shapeRenderer.end();
    }

    @Override
    public void hide() {
        // Clean up when the screen is hidden
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
    public void dispose() {
        shapeRenderer.dispose();  // Dispose of ShapeRenderer to avoid memory leaks
    }
}
