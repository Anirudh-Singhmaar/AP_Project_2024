package Main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import Main.Screens.MainScreen;

public class Main extends Game {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;

    @Override
    public void create() {
        // Initialize resources
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);

        // Set the first screen (e.g., MainMenuScreen)
        this.setScreen(new MainScreen(this));
    }

    @Override
    public void render() {
        super.render();  // Call the render method of the current screen
    }

    @Override
    public void dispose() {
        batch.dispose();
        // Dispose other resources as needed
    }
}
