package angrybirds.java.Buttons;

import angrybirds.java.Main;
import angrybirds.java.scenes.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuButtons {
    private Main game;
    private Stage stage;
    private Viewport gameViewport;
    private ImageButton playButton;
    private Music buttonClickMusic;  // To store the music for the button click

    public MainMenuButtons(Main game, Viewport gameViewport) {
        this.game = game;
        this.gameViewport = gameViewport;
        this.stage = new Stage(gameViewport, game.getBatch());

        // Load the music file for button click
        this.buttonClickMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/Click Sound.wav"));

        Gdx.input.setInputProcessor(this.stage);

        // Create the button with a border and set its position
        createAndPositionButtons();

        // Add the button to the stage
        this.stage.addActor(this.playButton);

        // Add the click listener to the button
        addClickListener();
    }

    void createAndPositionButtons() {
        // Load the PlayButton texture
        Texture playButtonTexture = new Texture("Buttons/MainMenu/PlayButton.jpg");

        // Create a SpriteDrawable using the texture
        this.playButton = new ImageButton(new SpriteDrawable(new Sprite(playButtonTexture)));

        // Get screen dimensions
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Define button size relative to screen size
        float buttonWidth = screenWidth * 0.2f;
        float buttonHeight = screenHeight * 0.2f;

        // Set button size
        this.playButton.setSize(buttonWidth, buttonHeight);

        // Center the button by positioning it relative to screen dimensions
        this.playButton.setPosition(
            (screenWidth - buttonWidth) / 2 - 50,  // X position: center horizontally
            (screenHeight - buttonHeight) / 2  + 300 // Y position: center vertically
        );
    }

    // Add a click listener to the play button
    private void addClickListener() {
        this.playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Play the button click sound
                buttonClickMusic.play();
                // Switch to the game screen
                game.setScreen(new GameScreen(game));
            }
        });
    }

    // Get the stage
    public Stage getStage() {
        return this.stage;
    }

    // Dispose the resources when no longer needed
    public void dispose() {
        stage.dispose();
        buttonClickMusic.dispose();  // Dispose of the music when it's no longer needed
    }
}
