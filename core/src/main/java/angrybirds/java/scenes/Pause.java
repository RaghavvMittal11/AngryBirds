package angrybirds.java.scenes;

import angrybirds.java.Main;
import angrybirds.java.ground.Ground1;
import angrybirds.java.ground.Platform;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Pause implements Screen {
    private final Music backgroundMusic;
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
    private Stage stage;
    private PauseLevelGen pauselevelGen;

    public Pause(Main game) {
        this.game = game;
        this.bg = new Texture("Background/backg.jpg");
        this.pauseButton = new Sprite(new Texture("Buttons/MainMenu/Pause.png"));
        this.world = new World(new Vector2(0.0F, -9.8F), true);
        this.mainCamera = new OrthographicCamera();
        this.mainCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.gameViewport = new StretchViewport(1919.0F, 1079.0F, this.mainCamera);
        this.ground1 = new Ground1(world, "Ground1");
        this.platform = new Platform(world, "Platform");
        platform.setSpritePosition(100, 200);
        ground1.setSpritePosition(0, 0);
        debugRenderer = new Box2DDebugRenderer();
        this.stage = new Stage();
        this.pauselevelGen = new PauseLevelGen(world, stage);
        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/playSound.mp3"));
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.setVolume(1.0f);
        centerAndScaleLevelTablet();
    }

    private void drawBackground() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float bgAspectRatio = (float) bg.getWidth() / bg.getHeight();
        float bgWidth = screenWidth;
        float bgHeight = screenWidth / bgAspectRatio;
        if (bgHeight < screenHeight) {
            bgHeight = screenHeight;
            bgWidth = screenHeight * bgAspectRatio;
        }
        float bgX = (screenWidth - bgWidth) / 2;
        float bgY = (screenHeight - bgHeight) / 2;
        game.getBatch().setColor(1.0F, 1.0F, 1.0F, fadeAlpha);
        game.getBatch().draw(bg, bgX, bgY, bgWidth, bgHeight);
        game.getBatch().setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void updateFade(float delta) {
        if (fadeAlpha < 1.0f) {
            fadeAlpha += delta / 2.0f;
        }
    }

    public void show() {
        backgroundMusic.play();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateFade(delta);
        game.getBatch().begin();
        drawBackground();
        ground1.draw(game.getBatch());
        platform.draw(game.getBatch());
        game.getBatch().setColor(1.0F, 1.0F, 1.0F, fadeAlpha);
        pauseButton.draw(game.getBatch());
        game.getBatch().end();
        game.getBatch().setProjectionMatrix(mainCamera.combined);
        debugRenderer.render(world, mainCamera.combined);
        stage.act(delta);
        stage.draw();
        pauselevelGen.generateRandomLevels();
        checkForClicks();
    }

    private void centerAndScaleLevelTablet() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float pauseWidth = screenWidth * 0.070f;
        float pauseHeight = screenHeight * 0.15f;
        pauseButton.setSize(pauseWidth, pauseHeight);
        pauseButton.setPosition(screenWidth + pauseWidth / 2 - 1900, (screenHeight - pauseHeight) / 2 + 400);
    }

    private void checkForClicks() {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        mousePos.y = Gdx.graphics.getHeight() - mousePos.y;
        if (pauseButton.getBoundingRectangle().contains(mousePos.x, mousePos.y) && Gdx.input.isTouched()) {
            game.setScreen(new Level1Screen(game));
        }
    }

    public void resize(int width, int height) {
        this.gameViewport.update(width, height);
        centerAndScaleLevelTablet();
    }

    public void pause() {}

    public void resume() {}

    public void hide() {}

    public void dispose() {
        bg.dispose();
        pauseButton.getTexture().dispose();
        ground1.dispose();
        world.dispose();
        platform.dispose();
        stage.dispose();
        pauselevelGen.dispose();
        debugRenderer.dispose();
        backgroundMusic.dispose();
    }
}
