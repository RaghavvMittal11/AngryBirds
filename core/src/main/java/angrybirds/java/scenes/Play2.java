package angrybirds.java.scenes;

import angrybirds.java.Main;
import angrybirds.java.ground.Ground1;
import angrybirds.java.ground.Platform;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Play2 implements Screen{
    private Main game;
    private Texture bg;
    private OrthographicCamera mainCamera;
    private World world;
    private Viewport gameViewport;
    private float fadeAlpha = 0.5F;
    private Sprite pauseButton;
    private Ground1 ground1;
    private Platform platform;
    private Box2DDebugRenderer debugRenderer;


    Play2(Main game) {
        this.game = game;
        this.bg = new Texture("Background/background.png");
        // Load the level tablet image and create the sprite
        this.pauseButton = new Sprite(new Texture("Buttons/MainMenu/Pause.png"));
        this.world = new World(new Vector2(0.0F, -9.8F), true);

        // Set up the camera and viewport
        this.mainCamera = new OrthographicCamera();
        this.mainCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.gameViewport = new StretchViewport(1919.0F, 1079.0F, this.mainCamera);
        this.ground1 = new Ground1(world, "Ground1");
        this.platform = new Platform(world, "Platform");
        platform.setSpritePosition(100, 200);
        ground1.setSpritePosition(0, 0);
        debugRenderer = new Box2DDebugRenderer();


        // Center the level tablet on the screen and scale it
        centerAndScaleLevelTablet();
    }
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
        float bgY = (screenHeight - bgHeight) / 2;

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
    public void show() {
    }
    public void render(float delta) {
        // Clear the screen with a black color
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateFade(delta);  // Update the fade effect

        // Begin batch
        game.getBatch().begin();

        // Draw the background while maintaining aspect ratio
        drawBackground();

        ground1.draw(game.getBatch());
        platform.draw(game.getBatch());


        // Draw the level tablet sprite centered on the screen
        game.getBatch().setColor(1.0F, 1.0F, 1.0F, fadeAlpha);
        pauseButton.draw(game.getBatch());
        game.getBatch().end();
        game.getBatch().setProjectionMatrix(mainCamera.combined);

        debugRenderer.render(world, mainCamera.combined);


        checkForClicks();
    }
    private void centerAndScaleLevelTablet() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Define the size for the level tablets
        float tabletWidth = screenWidth * 0.25f;
        float tabletHeight = screenHeight * 0.11f;




        // back.setSize(backWidth,backHeight);
        // Set the size of the bird

        float pauseWidth = screenWidth * 0.070f;
        float pauseHeight = screenHeight * 0.15f;
        pauseButton.setSize(pauseWidth,pauseHeight);

        // Position the level tablets

        pauseButton.setPosition(screenWidth + pauseWidth / 2 - 1900,
            (screenHeight - pauseHeight) / 2  + 400);

    }
    private void checkForClicks() {
        // Get mouse position
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        // Convert to world coordinates
        mousePos.y = Gdx.graphics.getHeight() - mousePos.y; // Invert Y-axis

        // Check if level tablets are clicked
        if (pauseButton.getBoundingRectangle().contains(mousePos.x, mousePos.y) && Gdx.input.isTouched()) {
            System.out.println("Back button clicked");
            game.setScreen(new Level1Screen(game));  // Change to MainMenu
        }
    }
    public void resize(int width, int height) {
        // Update the camera's viewport to match the new screen dimensions
        this.gameViewport.update(width, height);
        centerAndScaleLevelTablet();  // Re-center and resize the tablet on resize
    }

    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
    public void dispose() {
        bg.dispose();
        pauseButton.getTexture().dispose();
        ground1.dispose();
        world.dispose();
        platform.dispose();
    }
}

