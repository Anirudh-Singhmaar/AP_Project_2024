package Main.AngryBirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level_2 implements Screen {

    private Game game;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;

    // Declare SpriteBatch and Texture
    private SpriteBatch spriteBatch;
    private Texture RedbirdTexture;  // Texture for the bird
    private Texture PinkbirdTexture;  // Texture for the bird
    private Texture WoodTexture;  // New WoodTexture for the rectangles

    public Level_2(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);  // Setting the viewport dimensions
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);  // Center the camera
        camera.update();

        // Initialize SpriteBatch
        spriteBatch = new SpriteBatch();

        // Load the texture for the bird from the Birds folder
        RedbirdTexture = new Texture("Birds/RED_Bird.png");  // Correct path to the bird.png file
        PinkbirdTexture = new Texture("Birds/Pink_Bird.png");  // Correct path to the bird.png file
        WoodTexture = new Texture("Blocks/Wood.png");  // Correct path to the wood texture file
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

        // Set projection for shape renderer and sprite batch
        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);

        // Begin drawing shapes (rectangles for the box shape)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float paddingRight = 200;  // Padding from the right side of the screen
        float rectWidth = 50;  // Width of the rectangles (for sides and top)
        float rectHeight = 200;  // Height of the vertical rectangles
        float topRectHeight = 50;  // Height of the top rectangle
        float bottomRectHeight = 20;  // Height of the bottom rectangle (floor)

        // Positions for the box shape (relative to the viewport)
        float rightX = camera.viewportWidth - paddingRight;  // X position for the right rectangle
        float leftX = rightX - 200;  // X position for the left rectangle (200px to the left of the right rectangle)
        float bottomY = 50;  // Y position for the bottom rectangle (floor)
        float topY = bottomY + rectHeight;  // Y position for the top of the box
        float verticalY = bottomY + bottomRectHeight;  // Y position for the vertical rectangles (above the floor)

        // Draw the bottom rectangle (floor)
        shapeRenderer.setColor(Color.GRAY);  // Set color for the floor
        shapeRenderer.rect(0, bottomY, camera.viewportWidth, bottomRectHeight);  // Draw the floor
        shapeRenderer.end();

        // Begin drawing the wood rectangles with SpriteBatch
        spriteBatch.begin();
        // Draw the left rectangle (vertical) using WoodTexture
        spriteBatch.draw(WoodTexture, leftX, verticalY, rectWidth, rectHeight);

        // Draw the right rectangle (vertical) using WoodTexture
        spriteBatch.draw(WoodTexture, rightX, verticalY, rectWidth, rectHeight);

        // Draw the top rectangle (horizontal) using WoodTexture
        spriteBatch.draw(WoodTexture, leftX, topY, rightX - leftX + rectWidth, topRectHeight);

        // Draw the base rectangle (horizontal) using WoodTexture
        spriteBatch.draw(WoodTexture, leftX, bottomY, rightX - leftX + rectWidth, topRectHeight);

        // End SpriteBatch
        spriteBatch.end();

        // Begin drawing sprites with SpriteBatch
        spriteBatch.begin();

        // Bird parameters
        float paddingLeft = 125;
        float birdWidth = 50;  // Adjust the size of the bird sprite
        float horizontalSpacing = 75;  // Horizontal spacing between birds

        // Adjust Y position so that the birds are above the floor
        float birdY = 70;  // Adjust Y position for birds to touch the ground

        // X positions for the three birds (horizontally spaced)
        float firstBirdX = paddingLeft;
        float secondBirdX = firstBirdX + horizontalSpacing;
        float thirdBirdX = secondBirdX + horizontalSpacing;

        // Draw the first bird
        spriteBatch.draw(RedbirdTexture, firstBirdX, birdY, birdWidth, birdWidth);  // Adjust size as needed

        // Draw the second bird
        spriteBatch.draw(PinkbirdTexture, secondBirdX, birdY, birdWidth, birdWidth);

        // Draw the third bird
        spriteBatch.draw(RedbirdTexture, thirdBirdX, birdY, birdWidth, birdWidth);

        spriteBatch.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();  // Dispose of SpriteBatch
        RedbirdTexture.dispose();
        PinkbirdTexture.dispose();
        WoodTexture.dispose();  // Dispose of WoodTexture
    }
}
