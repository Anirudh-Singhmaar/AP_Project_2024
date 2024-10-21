package Main.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
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

        // Handle Input: Detect Mouse Click and switch to load screen when rect2, rect3, or rect4 is clicked
        if (Gdx.input.isButtonJustPressed(0)) {  // 0 is the left mouse button
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();

            // Convert mouse coordinates to world coordinates
            Vector3 mousePos = new Vector3(mouseX, mouseY, 0);
            camera.unproject(mousePos);  // Convert the screen coordinates to world coordinates

            // Check if the mouse is within the bounds of rect2
            if (mousePos.x >= rect1X && mousePos.x <= rect1X + squareSize && mousePos.y >= rect1Y && mousePos.y <= rect1Y + squareSize) {
                // Switch to the load screen
                game.setScreen(new LevelScreen(game));  // Assuming you have a LoadScreen class
            }

            // Check if the mouse is within the bounds of rect2
            if (mousePos.x >= rect2X && mousePos.x <= rect2X + squareSize && mousePos.y >= rect2Y && mousePos.y <= rect2Y + squareSize) {
                // Switch to the load screen
                game.setScreen(new LevelScreen(game));  // Assuming you have a LoadScreen class
            }

            // Check if the mouse is within the bounds of rect3
            if (mousePos.x >= rect3X && mousePos.x <= rect3X + squareSize && mousePos.y >= rect3Y && mousePos.y <= rect3Y + squareSize) {
                // Switch to the load screen
                game.setScreen(new LevelScreen(game));  // Assuming you have a LoadScreen class
            }

            // Check if the mouse is within the bounds of rect4
            if (mousePos.x >= rect4X && mousePos.x <= rect4X + squareSize && mousePos.y >= rect4Y && mousePos.y <= rect4Y + squareSize) {
                // Switch to the load screen
                game.setScreen(new LevelScreen(game));  // Assuming you have a LoadScreen class
            }
        }
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
