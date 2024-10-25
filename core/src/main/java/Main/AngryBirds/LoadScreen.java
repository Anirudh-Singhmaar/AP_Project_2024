package Main.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Game;

// Screen 2: Main Page

public class LoadScreen implements Screen {
    private Game game;  // Changed 'Game' to 'game' for clarity
    private ShapeRenderer shapeRenderer;

    private SpriteBatch spriteBatch;
    private Texture BackGround;
    private Texture Load_A;
    private Texture Load_B;
    private Texture Load_C;
    private Texture Load_D;
    private Texture BackTexture;

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

        spriteBatch = new SpriteBatch();
        BackTexture = new Texture("Extras/Back.png");
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

        // Draw Circle in the Top Right Corner
        shapeRenderer.setColor(Color.YELLOW); // Set color for the circle
        float circleRadius = 50; // Define the radius of the circle
        float circleX = camera.viewportWidth - circleRadius - 20; // Position 20 pixels from the right edge
        float circleY = camera.viewportHeight - circleRadius - 20; // Position 20 pixels from the top edge
        shapeRenderer.circle(circleX, circleY, circleRadius); // Draw the circle
        shapeRenderer.end();

        // Begin sprite batch to draw the texture within the circle
        spriteBatch.begin();

        // Adjust the texture size to match the circle diameter (2 * circleRadius)
        float textureWidth = 2 * circleRadius;
        float textureHeight = 2 * circleRadius;

        // Draw the texture at the same position as the circle, but adjusting the origin for texture drawing
        spriteBatch.draw(BackTexture, circleX - circleRadius, circleY - circleRadius, textureWidth, textureHeight);

        spriteBatch.end();

        // End ShapeRenderer once all shapes are drawn
        shapeRenderer.end();

        // Handle Input: Detect Mouse Click and switch to load screen when rect2, rect3, or rect4 is clicked
        if (Gdx.input.isButtonJustPressed(0)) {  // 0 is the left mouse button
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();

            // Convert mouse coordinates to world coordinates
            Vector3 mousePos = new Vector3(mouseX, mouseY, 0);
            camera.unproject(mousePos);  // Convert the screen coordinates to world coordinates

            // Check if the mouse is within the bounds of Circle
            float dx = mousePos.x - circleX; // Distance in x direction
            float dy = mousePos.y - circleY; // Distance in y direction
            float distance = (float) Math.sqrt(dx * dx + dy * dy); // Calculate distance

            if (distance <= circleRadius) {
                // Switch to the load screen
                game.setScreen(new MainScreen(game));  // Assuming you want to go back to LevelScreen
            }

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
