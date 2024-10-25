package Main.AngryBirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

// Screen 3: Levels Page

public class LevelScreen implements Screen{
    private Game game;  // Changed 'Game' to 'game' for clarity
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;
    
    private SpriteBatch spriteBatch;
    private Texture BackGround;
    // private Texture Level_1;
    // private Texture Level_2;
    // private Texture Level_3;
    private Texture BackTexture;
    
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
        
        spriteBatch = new SpriteBatch();
        BackGround = new Texture("BackGround/Background.jpg");
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
        spriteBatch.begin();
        spriteBatch.draw(BackGround, 0, 0, camera.viewportWidth, camera.viewportHeight);
        spriteBatch.end();
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
        float square1XY = -squareSize / 2;
        shapeRenderer.rect(square1XY, square1XY, squareSize, squareSize);  // Draw red square (centered at origin)
        shapeRenderer.identity();  // Reset transformations

        // 2. Draw Level 2's Square (Center)
        shapeRenderer.identity();  // Reset any existing transformations
        shapeRenderer.translate(centerX, centerY, 0);  // Move to the position of the blue square
        shapeRenderer.rotate(0, 0, 1, 45);  // Rotate by 45 degrees around the Z-axis
        shapeRenderer.setColor(Color.BLUE);
        float square2XY = -squareSize / 2;
        shapeRenderer.rect(square2XY, square2XY, squareSize, squareSize);  // Draw blue square (centered at origin)
        shapeRenderer.identity();   // Reset transformations

        // 3. Draw Level 3's Square (Right of center)
        shapeRenderer.identity();  // Reset any existing transformations
        shapeRenderer.translate(centerX + offset, centerY, 0);  // Move to the position of the green square
        shapeRenderer.rotate(0, 0, 1, 45);  // Rotate by 45 degrees around the Z-axis
        shapeRenderer.setColor(Color.GREEN);
        float square3XY = -squareSize / 2;
        shapeRenderer.rect(square3XY, square3XY, squareSize, squareSize);  // Draw green square (centered at origin)
        shapeRenderer.identity();   // Reset transformations
        
        spriteBatch.begin();
        spriteBatch.end();

        spriteBatch.begin();

        // Draw the back button texture
        float circleRadius = 35;
        float circleX = camera.viewportWidth - circleRadius - 20;
        float circleY = camera.viewportHeight - circleRadius - 20;
        float textureWidth = 2 * circleRadius;
        float textureHeight = 2 * circleRadius;

        spriteBatch.draw(BackTexture, circleX - circleRadius, circleY - circleRadius, textureWidth, textureHeight);
        spriteBatch.end();

        // End ShapeRenderer once all shapes are drawn
        shapeRenderer.end();

        // Handle Input: Detect Mouse Click and switch to load screen when squares are clicked
        if (Gdx.input.isButtonJustPressed(0)) {  // Left mouse button
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

            // Example for checking Level 1's square:
            if (mousePos.x >= centerX - offset - squareSize / 2 && mousePos.x <= centerX - offset + squareSize / 2
                && mousePos.y >= centerY - squareSize / 2 && mousePos.y <= centerY + squareSize / 2) {
                // Switch to the load screen (assuming Level_1 exists)
                game.setScreen(new Level_1(game));
            }

            // Example for checking Level 2's square:
            if (mousePos.x >= centerX - squareSize / 2 && mousePos.x <= centerX + squareSize / 2
                && mousePos.y >= centerY - squareSize / 2 && mousePos.y <= centerY + squareSize / 2) {
                // Switch to the load screen (assuming Level_2 exists)
                game.setScreen(new Level_2(game));
            }

            // Example for checking Level 3's square:
            if (mousePos.x >= centerX + squareSize / 2 && mousePos.x <= centerX + offset + squareSize / 2
                && mousePos.y >= centerY - squareSize / 2 && mousePos.y <= centerY + squareSize / 2) {
                // Switch to the load screen (assuming Level_3 exists)
                game.setScreen(new Level_3(game));
            }
        }
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
