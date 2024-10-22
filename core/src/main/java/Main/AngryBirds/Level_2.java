package Main.AngryBirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
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
    private Texture GlassTexture;  // New GlassTexture for the rectangles
    private Texture CatapultTexture;
    private Texture BackTexture;

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
        PinkbirdTexture = new Texture("Birds/PINK_Bird.png");  // Correct path to the bird.png file
        GlassTexture = new Texture("Blocks/Glass.png");  // Correct path to the wood texture file
        CatapultTexture = new Texture("Extras/Catapult.png");
        BackTexture = new Texture("Extras/Back.png");

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
        float rectWidth = 35;  // Width of the rectangles (for sides and top)
        float rectHeight = 175;  // Height of the vertical rectangles
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

        // Begin drawing the wood rectangles with SpriteBatch
        spriteBatch.begin();
        // Draw the left rectangle (vertical) using GlassTexture
        spriteBatch.draw(GlassTexture, leftX, verticalY, rectWidth, rectHeight);

        // Draw the right rectangle (vertical) using GlassTexture
        spriteBatch.draw(GlassTexture, rightX, verticalY, rectWidth, rectHeight);

        // Draw the top rectangle (horizontal) using GlassTexture
        spriteBatch.draw(GlassTexture, leftX, topY, rightX - leftX + rectWidth, 2*topRectHeight/3);

        // End SpriteBatch
        spriteBatch.end();

        // Begin drawing sprites with SpriteBatch
        spriteBatch.begin();

        // Bird and Catapult parameters
        float paddingLeft = 125;
        float CircleWidth = 75;  // Adjust the size of the bird sprite
        float horizontalSpacing = 75;  // Horizontal spacing between birds

        float CatapultWidth = 35;  // Width of the rectangles (for sides and top)
        float CatapultHeight = 125;  // Height of the vertical rectangles

        // Adjust Y position so that the birds are above the floor
        float GroundY = 60;  // Adjust Y position for birds to touch the ground
        float offset = 60;

        // X positions for the three birds (horizontally spaced)
        float firstBirdX = paddingLeft;
        float secondBirdX = firstBirdX + horizontalSpacing;
        float thirdBirdX = secondBirdX + horizontalSpacing;
        float CatapX = thirdBirdX + horizontalSpacing;

        // Draw the first bird
        spriteBatch.draw(RedbirdTexture, firstBirdX, GroundY, CircleWidth, CircleWidth);  // Adjust size as needed

        // Draw the second bird
        spriteBatch.draw(PinkbirdTexture, secondBirdX, GroundY, CircleWidth, CircleWidth);

        // Draw the third bird
        spriteBatch.draw(RedbirdTexture, thirdBirdX, GroundY + offset, CircleWidth, CircleWidth);

        spriteBatch.draw(CatapultTexture, CatapX, GroundY, 3*CatapultWidth/2, CatapultHeight);

        spriteBatch.end();

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
                game.setScreen(new LevelScreen(game));  // Assuming you want to go back to LevelScreen
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
        dispose();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();  // Dispose of SpriteBatch
        RedbirdTexture.dispose();
        PinkbirdTexture.dispose();
        GlassTexture.dispose();  // Dispose of GlassTexture
        CatapultTexture.dispose();
    }
}
