package Main.AngryBirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

// Screen 3: Levels Page

public class LevelScreen implements Screen{
    private Game game;  // Changed 'Game' to 'game' for clarity
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;
    final float squareSize = 100;

    public LevelScreen(Game game) {  // Changed 'Game' to 'game' for clarity
        this.game = game;  // Renamed variable to match convention
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
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

        // Begin ShapeRenderer
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Common square size
        float squareSize = 100;
        float offset = 300; // Symmetrical offset between the squares

        // Get the center of the camera
        float centerX = camera.position.x;
        float centerY = camera.position.y;

        // 1. Draw Level 1's Square (Left of center)
        shapeRenderer.identity();  // Reset any existing transformations
        shapeRenderer.translate(centerX - offset, centerY, 0);  // Move to the position of the red square
        shapeRenderer.rotate(0, 0, 1, 45);  // Rotate by 45 degrees around the Z-axis
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(-squareSize / 2, -squareSize / 2, squareSize, squareSize);  // Draw red square (centered at origin)
        shapeRenderer.identity();  // Reset transformations

        // 2. Draw Level 2's Square (Center)
        shapeRenderer.identity();  // Reset any existing transformations
        shapeRenderer.translate(centerX, centerY, 0);  // Move to the position of the blue square
        shapeRenderer.rotate(0, 0, 1, 45);  // Rotate by 45 degrees around the Z-axis
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(-squareSize / 2, -squareSize / 2, squareSize, squareSize);  // Draw blue square (centered at origin)
        shapeRenderer.identity();  // Reset transformations

        // 3. Draw Level 3's Square (Right of center)
        shapeRenderer.identity();  // Reset any existing transformations
        shapeRenderer.translate(centerX + offset, centerY, 0);  // Move to the position of the green square
        shapeRenderer.rotate(0, 0, 1, 45);  // Rotate by 45 degrees around the Z-axis
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(-squareSize / 2, -squareSize / 2, squareSize, squareSize);  // Draw green square (centered at origin)
        shapeRenderer.identity();  // Reset transformations

        // End ShapeRenderer once all shapes are drawn
        shapeRenderer.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
