package angrybirds.java.scenes;

import angrybirds.java.Buttons.MainMenuButtons;
import angrybirds.java.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Loose implements Screen {
    private Main game;
    private Texture bg;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;
//    private MainMenuButtons btns;
    private Music backgroundMusic;  // New Music object for background sound


    private Sprite tablet;
    public Loose(Main game) {
        this.game = game;
        this.bg = new Texture("Background/loose.jpg");

        // Set up the camera and viewport
        this.mainCamera = new OrthographicCamera();
        this.mainCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.mainCamera.position.set(Gdx.graphics.getWidth() / 2.0F, Gdx.graphics.getHeight() / 2.0F, 0.0F);
        this.gameViewport = new StretchViewport(1919.0F, 1079.0F, this.mainCamera);


        // Set up main menu buttons
//        this.btns = new MainMenuButtons(game, gameViewport);
        this.tablet=new Sprite(new Texture("Background/wood.png"));
        // Load the background music
        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/Main Theme.mp3"));
        this.backgroundMusic.setLooping(true);  // Set to loop continuously
        this.backgroundMusic.setVolume(0.5f);   // Set volume (adjust to your preference)
    }

    @Override
    public void show() {
        // Play the background music when the menu screen is shown
        backgroundMusic.play();
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
        tablet.draw(game.getBatch());

        game.getBatch().end();

        // Set the batch to use the camera's projection matrix
        game.getBatch().setProjectionMatrix(mainCamera.combined);
        // Draw and act on the menu buttons
//        this.btns.getStage().draw();
//        this.btns.getStage().act();
    }

    @Override
    public void resize(int width, int height) {
        // Update the camera's viewport to match the new screen dimensions
        this.gameViewport.update(width, height);
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Define the size for the level tablets
        float tabletWidth = screenWidth * 0.25f;
        float tabletHeight = screenHeight * 0.11f;

        tablet.setPosition(
            (screenWidth - tabletWidth) / 2 + 600,  // Adjust horizontal offset
            (screenHeight - tabletHeight) / 2 + 200 // Adjust vertical offset
        );
    }


    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    private void checkForClicks() {
        // Get mouse position
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        // Convert to world coordinates
        mousePos.y = Gdx.graphics.getHeight() - mousePos.y; // Invert Y-axis

        if(tablet.getBoundingRectangle().contains(mousePos.x, mousePos.y) && Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
        }
    }
    @Override
    public void hide() {
        // Stop the background music when the screen is hidden or changed
        backgroundMusic.stop();
    }

    @Override
    public void dispose() {
        // Dispose of all resources when no longer needed
        bg.dispose();
        backgroundMusic.dispose();  // Dispose of the music to avoid memory leaks
    }
}
