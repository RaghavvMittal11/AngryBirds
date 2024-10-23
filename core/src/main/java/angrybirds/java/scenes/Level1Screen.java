package angrybirds.java.scenes;

import angrybirds.java.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Level1Screen implements Screen {
    private final Music backgroundMusic;
    private Main game;
    private Texture bg;
    private OrthographicCamera mainCamera;
    private Sprite newLevelTablet;
    private Sprite loadLevelTablet;
    private Sprite newLevel;
    private Sprite loadLevel;
//    private Sprite level3;
    private Viewport gameViewport;
    private float fadeAlpha = 0.5F;
    private Sprite back;

    public Level1Screen(Main game) {
        this.game = game;
        this.bg = new Texture("Background/levelScreen.jpg");
        // Load the level tablet image and create the sprite
        this.newLevelTablet = new Sprite(new Texture("Background/wood.png"));
        this.loadLevelTablet = new Sprite(new Texture("Background/wood.png"));
        this.newLevel=new Sprite(new Texture("Buttons/MainMenu/NewGame.png"));
        this.loadLevel =new Sprite(new Texture("Buttons/MainMenu/LoadLevel.png"));
        this.back=new Sprite(new Texture("Buttons/MainMenu/back.png"));



        // Set up the camera and viewport
        this.mainCamera = new OrthographicCamera();
        this.mainCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.gameViewport = new StretchViewport(1919.0F, 1079.0F, this.mainCamera);

        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/SoundLevelScreen.mp3"));
        this.backgroundMusic.setLooping(true);  // Set to loop continuously
        this.backgroundMusic.setVolume(1.0f);
        // Center the level tablet on the screen and scale it
        centerAndScaleLevelTablet();
    }


    // Method to center and scale the level tablet image
//    private void centerAndScaleLevelTablet() {
//        // Get the screen width and height
//        float screenWidth = Gdx.graphics.getWidth();
//        float screenHeight = Gdx.graphics.getHeight();
//
//        // Define a percentage of the screen to use for the level tablet's size (adjust as necessary)
//        float tabletWidth = screenWidth * 0.275f;  // 50% of screen width
//        float tabletHeight = screenHeight * 0.11f; // 50% of screen height
//
//        // Set the size of the level tablet
//        newLevelTablet.setSize(tabletWidth, tabletHeight);
//        loadLevelTablet.setSize(tabletWidth, tabletHeight);
//        levelTablet3.setSize(tabletWidth, tabletHeight);
//
//        // Center the level tablet on the screen
//        newLevelTablet.setPosition(
//            (screenWidth - tabletWidth) / 2 + 600,  // Center horizontally
//            (screenHeight - tabletHeight) / 2 + 200 // Center vertically
//        );
//        loadLevelTablet.setPosition(
//            (screenWidth - tabletWidth) / 2 + 600,  // Center horizontally
//            (screenHeight - tabletHeight) / 2  // Center vertically
//        );
//        levelTablet3.setPosition(
//            (screenWidth - tabletWidth) / 2 + 600,  // Center horizontally
//            (screenHeight - tabletHeight) / 2 -200 // Center vertically
//        );
//    }

    // Method to draw the background while maintaining its aspect ratio
    private void drawBackground() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Get the aspect ratio of the background image
        float bgAspectRatio = (float) bg.getWidth() / bg.getHeight();

        // Calculate the appropriate width and height to maintain aspect ratio
        float bgWidth = screenWidth;
        float bgHeight = screenWidth / bgAspectRatio;

        // If the calculated height is smaller than the screen height, adjust the width and height
        if (bgHeight < screenHeight) {
            bgHeight = screenHeight;
            bgWidth = screenHeight * bgAspectRatio;
        }

        // Center the background on the screen
        float bgX = (screenWidth - bgWidth) / 2;
        float bgY = (screenHeight - bgHeight) / 2  + 250;

        // Draw the background
        game.getBatch().setColor(1.0F, 1.0F, 1.0F, fadeAlpha);
        game.getBatch().draw(bg, bgX, bgY, bgWidth, bgHeight);
        game.getBatch().setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void updateFade(float delta) {
        if (fadeAlpha < 1.0f) {
            fadeAlpha += delta / 2.0f;  // Adjust the fade-in speed
        }
    }


    // Method to center and scale the level tablet image
//    private void centerAndScaleLevelTablet() {
//        float screenWidth = Gdx.graphics.getWidth();
//        float screenHeight = Gdx.graphics.getHeight();
//
//        // Define a percentage of the screen to use for the level tablet's size (adjust as necessary)
//        float tabletWidth = screenWidth * 0.25f;
//        float tabletHeight = screenHeight * 0.11f;
//
//        // Set the size of the level tablets
//        newLevelTablet.setSize(tabletWidth, tabletHeight);
//        loadLevelTablet.setSize(tabletWidth, tabletHeight);
//        levelTablet3.setSize(tabletWidth, tabletHeight);
//        bird.setSize(tabletWidth, tabletHeight);
//
//        // Position the level tablets with better relative placement
//        newLevelTablet.setPosition(
//            (screenWidth - tabletWidth) / 2 + 600,  // Center horizontally
//            (screenHeight - tabletHeight) / 2 + 200 // Adjust vertical offset
//        );
//        loadLevelTablet.setPosition(
//            (screenWidth - tabletWidth) / 2 + 600,  // Center horizontally
//            (screenHeight - tabletHeight) / 2  // Adjust vertical offset
//        );
//        levelTablet3.setPosition(
//            (screenWidth - tabletWidth) / 2 + 600,  // Center horizontally
//            (screenHeight - tabletHeight) / 2 - 200 // Adjust vertical offset
//        );
//
//        bird.setPosition(
//            (screenWidth - tabletWidth) / 2 - 600,  // Center horizontally
//            (screenHeight - tabletHeight) / 2 + 300 // Adjust vertical offset
//        );
//    }

    private void centerAndScaleLevelTablet() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Define the size for the level tablets
        float tabletWidth = screenWidth * 0.25f;
        float tabletHeight = screenHeight * 0.11f;





        // Set the size of the level tablets
        newLevelTablet.setSize(tabletWidth, tabletHeight);
        loadLevelTablet.setSize(tabletWidth, tabletHeight);

        float levelWidth=screenWidth*0.20f;
        float levelHeight=screenHeight * 0.09f;
        newLevel.setSize(levelWidth, levelHeight );
        loadLevel.setSize(levelWidth, levelHeight);
//        level3.setSize(levelWidth, levelHeight);
        // back.setSize(backWidth,backHeight);
        // Set the size of the bird

        float backWidth = screenWidth * 0.070f;
        float backHeight = screenHeight * 0.15f;
        // Set the size of back
        back.setSize(backWidth,backHeight);

        // Position the level tablets
        newLevelTablet.setPosition(
            (screenWidth - tabletWidth) / 2 + 600,  // Adjust horizontal offset
            (screenHeight - tabletHeight) / 2 + 200 // Adjust vertical offset
        );
        loadLevelTablet.setPosition(
            (screenWidth - tabletWidth) / 2 + 600,  // Adjust horizontal offset
            (screenHeight - tabletHeight) / 2  // Center vertically
        );
//        levelTablet3.setPosition(
//            (screenWidth - tabletWidth) / 2 + 600,  // Adjust horizontal offset
//            (screenHeight - tabletHeight) / 2 - 200 // Adjust vertical offset
//        );

        newLevel.setPosition(
            (screenWidth - levelWidth) / 2 + 600,  // Adjust horizontal offset
            (screenHeight - levelHeight) / 2 + 200 // Adjust vertical offset
        );
        loadLevel.setPosition(
            (screenWidth - levelWidth) / 2 + 600,  // Adjust horizontal offset
            (screenHeight - levelHeight) / 2  // Center vertically
        );
//        level3.setPosition(
//            (screenWidth - levelWidth) / 2 + 600,  // Adjust horizontal offset
//            (screenHeight - levelHeight) / 2 - 200 // Adjust vertical offset
//        );

        back.setPosition(screenWidth + backWidth / 2 - 1800,
            (screenHeight - backHeight) / 2  + 400);

    }



    @Override
    public void show() {
        // Play the background music when the menu screen is shown
        backgroundMusic.play();
    }

//    @Override
//    public void render(float delta) {
//        // Clear the screen with a black color
//        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        // Begin batch and draw the background, scaling it to the current screen size
//        game.getBatch().begin();
//        game.getBatch().draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        // Draw the level tablet sprite centered on the screen
//        newLevelTablet.draw(game.getBatch());
//        loadLevelTablet.draw(game.getBatch());
//        levelTablet3.draw(game.getBatch());
//
//        game.getBatch().end();
//        game.getBatch().setProjectionMatrix(mainCamera.combined);
//    }

    @Override
    public void render(float delta) {
        // Clear the screen with a black color
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateFade(delta);  // Update the fade effect

        // Begin batch
        game.getBatch().begin();

        // Draw the background while maintaining aspect ratio
        drawBackground();

        // Draw the level tablet sprite centered on the screen
        game.getBatch().setColor(1.0F, 1.0F, 1.0F, fadeAlpha);
        newLevelTablet.draw(game.getBatch());
        loadLevelTablet.draw(game.getBatch());
//        levelTablet3.draw(game.getBatch());

        newLevel.draw(game.getBatch());
        loadLevel.draw(game.getBatch());
//        level3.draw(game.getBatch());

        back.draw(game.getBatch());


        game.getBatch().end();
        game.getBatch().setProjectionMatrix(mainCamera.combined);

       checkForClicks();
    }


    private void checkForClicks() {
        // Get mouse position
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        // Convert to world coordinates
        mousePos.y = Gdx.graphics.getHeight() - mousePos.y; // Invert Y-axis

        // Check if level tablets are clicked
         if (back.getBoundingRectangle().contains(mousePos.x, mousePos.y) && Gdx.input.isTouched()) {
             System.out.println("Back button clicked");
            game.setScreen(new GameScreen(game));  // Change to MainMenu
        }
         if(newLevel.getBoundingRectangle().contains(mousePos.x, mousePos.y) && Gdx.input.isTouched()){
             System.out.println("Level 1 clicked");
             game.setScreen(new Play1(game));
         }
         if(loadLevel.getBoundingRectangle().contains(mousePos.x, mousePos.y) && Gdx.input.isTouched()){
              //  System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
                game.setScreen(new Pause(game));
            }
    }



    @Override
    public void resize(int width, int height) {
        // Update the camera's viewport to match the new screen dimensions
        this.gameViewport.update(width, height);
        centerAndScaleLevelTablet();  // Re-center and resize the tablet on resize
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
        bg.dispose();
        newLevelTablet.getTexture().dispose();
        loadLevelTablet.getTexture().dispose();
//        levelTablet3.getTexture().dispose();
        newLevel.getTexture().dispose();
        loadLevel.getTexture().dispose();
//        level3.getTexture().dispose();
        back.getTexture().dispose();
        backgroundMusic.dispose();
    }
}
