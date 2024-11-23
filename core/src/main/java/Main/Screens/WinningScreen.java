package Main.Screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WinningScreen implements Screen {
    private final Stage stage;

    public WinningScreen(Game game) {
        this.stage = new Stage(new ScreenViewport());

        // Create a winning image
        Image winImage = new Image(new Texture("BackGround/Win.jpg")); // Add this image to your assets
        winImage.setPosition(Gdx.graphics.getWidth() / 2f - winImage.getWidth() / 2f, 
                             Gdx.graphics.getHeight() / 2f);

        // Create a TextButtonStyle
        BitmapFont font = new BitmapFont(); // Use the default font or load a custom one
        TextureRegionDrawable upDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("LoadGame_Buttons/Up.jpg")));
        TextureRegionDrawable downDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("LoadGame_Buttons/Down.jpg")));
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(upDrawable, downDrawable, null, font);

        // Create buttons
        TextButton nextLevelButton = new TextButton("Next Level", buttonStyle);
        TextButton quitButton = new TextButton("Quit", buttonStyle);

        // Position buttons
        nextLevelButton.setPosition(Gdx.graphics.getWidth() / 2f - nextLevelButton.getWidth() / 2f, 150);
        quitButton.setPosition(Gdx.graphics.getWidth() / 2f - quitButton.getWidth() / 2f, 100);

        // Add button listeners
        nextLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game)); // Load the next level
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Gdx.app.exit(); // Exit the game
            }
        });

        // Add actors to the stage
        stage.addActor(winImage);
        stage.addActor(nextLevelButton);
        stage.addActor(quitButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
