
package angrybirds.java.scenes;

import angrybirds.java.Buttons.MainMenuButtons;
import angrybirds.java.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen {
    private Main game;
    private Texture bg;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;
    private MainMenuButtons btns;
    private static Music backgroundMusic;
    private static boolean flag = false;

    public MainMenu(Main game) {
        this.game = game;
        this.bg = new Texture("Background/MainMenu.jpg");

        // Set up the camera and viewport
        this.mainCamera = new OrthographicCamera();
        this.mainCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.mainCamera.position.set(Gdx.graphics.getWidth() / 2.0F, Gdx.graphics.getHeight() / 2.0F, 0.0F);
        this.gameViewport = new StretchViewport(1919.0F, 1079.0F, this.mainCamera);

        // Set up main menu buttons
        this.btns = new MainMenuButtons(game, gameViewport);

        // Only load and play the background music the first time an object is created
        if (!flag) {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/Main Theme.mp3"));
            backgroundMusic.setLooping(true);  // Set to loop continuously
            backgroundMusic.setVolume(0.5f);   // Set volume (adjust to your preference)
            backgroundMusic.play();            // Play the music
            flag = true;                       // Set the flag so it doesn't play again
        }
    }

    @Override
    public void show() {
        // No need to play music here as it is handled in the constructor
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a black color
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        Gdx.gl.glClear(16384);

        // Get the screen width and height to resize the background
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Begin batch and draw the background, scaling it to the current screen size
        game.getBatch().begin();
        game.getBatch().draw(bg, 0.0F, 0.0F, screenWidth, screenHeight);
        game.getBatch().end();

        // Set the batch to use the camera's projection matrix
        game.getBatch().setProjectionMatrix(mainCamera.combined);

        // Draw and act on the menu buttons
        this.btns.getStage().draw();
        this.btns.getStage().act();
    }

    @Override
    public void resize(int width, int height) {
        // Update the camera's viewport to match the new screen dimensions
        this.gameViewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        // Optionally, you can stop or pause the music if needed when hiding
        // backgroundMusic.pause();
    }

    @Override
    public void dispose() {
        // Dispose of all resources when no longer needed
        bg.dispose();

        // Only dispose of the music if this is the last screen, otherwise it remains active
        if (flag && backgroundMusic != null) {
            backgroundMusic.dispose();
        }
    }
}
