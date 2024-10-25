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

public class LevelScreen implements Screen {
    private Game game;  
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private FitViewport viewport;
    
    private SpriteBatch spriteBatch;
    private Texture BackGround;
    private Texture Level_1;
    private Texture Level_2;
    private Texture Level_3;
    private Texture BackTexture;
    
    final float rectWidth = 200;
    final float rectHeight = 75;

    public LevelScreen(Game game) {  
        this.game = game;  
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);  
        camera.update();
        
        spriteBatch = new SpriteBatch();
        BackGround = new Texture("BackGround/Background.jpg");
        Level_1 = new Texture("Level_Buttons/Level_1.png");
        Level_2 = new Texture("Level_Buttons/Level_2.png");
        Level_3 = new Texture("Level_Buttons/Level_3.png");
        BackTexture = new Texture("Extras/Back.png");
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);  
        
        // Draw the background
        spriteBatch.begin();
        spriteBatch.draw(BackGround, 0, 0, camera.viewportWidth, camera.viewportHeight);
        spriteBatch.end();
    
        // Begin ShapeRenderer for rectangles
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        float offset = 300; // Symmetrical offset between the rectangles
        float centerX = camera.position.x;
        float centerY = camera.position.y;
    
        // Draw Level 1's Rectangle (Left of center) - transparent red
        shapeRenderer.setColor(new Color(1, 0, 0, 0.5f)); // Red with 50% transparency
        shapeRenderer.rect(centerX - offset - rectWidth / 2, centerY - rectHeight / 2, rectWidth, rectHeight);
        
        // Draw Level 2's Rectangle (Center) - transparent blue
        shapeRenderer.setColor(new Color(0, 0, 1, 0.5f)); // Blue with 50% transparency
        shapeRenderer.rect(centerX - rectWidth / 2, centerY - rectHeight / 2, rectWidth, rectHeight);
        
        // Draw Level 3's Rectangle (Right of center) - transparent green
        shapeRenderer.setColor(new Color(0, 1, 0, 0.5f)); // Green with 50% transparency
        shapeRenderer.rect(centerX + offset - rectWidth / 2, centerY - rectHeight / 2, rectWidth, rectHeight);
        
        shapeRenderer.end();
    
        // Begin drawing level sprites
        spriteBatch.begin();
    
        // Draw level sprites over the rectangles with exact matching dimensions
        spriteBatch.draw(Level_1, centerX - offset - rectWidth / 2, centerY - rectHeight / 2, rectWidth, rectHeight); // Level 1 texture
        spriteBatch.draw(Level_2, centerX - rectWidth / 2, centerY - rectHeight / 2, rectWidth, rectHeight); // Level 2 texture
        spriteBatch.draw(Level_3, centerX + offset - rectWidth / 2, centerY - rectHeight / 2, rectWidth, rectHeight); // Level 3 texture
        
        // Draw the back button texture
        float circleRadius = 35;
        float circleX = camera.viewportWidth - circleRadius - 20;
        float circleY = camera.viewportHeight - circleRadius - 20;
        spriteBatch.draw(BackTexture, circleX - circleRadius, circleY - circleRadius, 2 * circleRadius, 2 * circleRadius);
        spriteBatch.end();
    
        // Handle Input: Detect Mouse Click
        if (Gdx.input.isButtonJustPressed(0)) {  
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();
            Vector3 mousePos = new Vector3(mouseX, mouseY, 0);
            camera.unproject(mousePos);  
    
            // Check if the mouse is within the bounds of Circle
            float dx = mousePos.x - circleX; 
            float dy = mousePos.y - circleY; 
            float distance = (float) Math.sqrt(dx * dx + dy * dy); 
    
            if (distance <= circleRadius) {
                game.setScreen(new MainScreen(game));  
            }
    
            // Check if the user clicked Level 1's rectangle
            if (mousePos.x >= centerX - offset - rectWidth / 2 && mousePos.x <= centerX - offset + rectWidth / 2 
                && mousePos.y >= centerY - rectHeight / 2 && mousePos.y <= centerY + rectHeight / 2) {
                game.setScreen(new Level_1(game));
            }
    
            // Check if the user clicked Level 2's rectangle
            if (mousePos.x >= centerX - rectWidth / 2 && mousePos.x <= centerX + rectWidth / 2 
                && mousePos.y >= centerY - rectHeight / 2 && mousePos.y <= centerY + rectHeight / 2) {
                game.setScreen(new Level_2(game));
            }
    
            // Check if the user clicked Level 3's rectangle
            if (mousePos.x >= centerX + offset - rectWidth / 2 && mousePos.x <= centerX + offset + rectWidth / 2 
                && mousePos.y >= centerY - rectHeight / 2 && mousePos.y <= centerY + rectHeight / 2) {
                game.setScreen(new Level_3(game));
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
        // Dispose of assets
        spriteBatch.dispose();
        shapeRenderer.dispose();
        BackGround.dispose();
        Level_1.dispose();
        Level_2.dispose();
        Level_3.dispose();
        BackTexture.dispose();
    }
}
