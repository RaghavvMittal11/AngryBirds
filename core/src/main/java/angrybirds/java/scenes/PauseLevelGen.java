package angrybirds.java.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Random;

public class PauseLevelGen {
    private World world;
    private Stage stage;
    private float scaleFactor = 0.7f;
    private final int LEVEL_COUNT = 100;
    private final int GRID_CELL_SIZE = 64; // Size of each cell in pixels
    private Texture woodTexture;
    private Texture stoneTexture;
    private Texture glassTexture;
    private Texture[] pigTexture;
    private  Texture gullelTexture;
    private Texture backgroundTexture;

    // Array to hold multiple levels with different grid sizes
    private int[][][] levels;
    private int[][] gridSizes = {{10, 3}, {7, 5}, {11, 4}}; // Sizes for each grid

    public PauseLevelGen(World world, Stage stage) {
        this.world = world;
        this.stage = stage;

        // Load textures
        woodTexture = new Texture(Gdx.files.internal("Background/Wood Block.png"));
        stoneTexture = new Texture(Gdx.files.internal("Background/Stone Block.png"));
        glassTexture = new Texture(Gdx.files.internal("Background/Glass Block.png"));
        gullelTexture = new Texture(Gdx.files.internal("Background/gullel.png"));

        pigTexture = new Texture[]{
            new Texture(Gdx.files.internal("Background/enemy.png")),
            new Texture(Gdx.files.internal("Background/kingPig.png")),
            new Texture(Gdx.files.internal("Background/profPig.png"))
        };



        // Initialize levels array based on grid sizes
        levels = new int[gridSizes.length][][];

        generateRandomLevels();
    }

    public void generateRandomLevels() {
        Random random = new Random();

        for (int i = 0; i < gridSizes.length; i++) {
            int rows = gridSizes[i][0];
            int cols = gridSizes[i][1];
            int[][] levelGrid = new int[rows][cols];

            // Fill the bottom row with stone
            for (int col = 0; col < cols; col++) {
                levelGrid[rows - 1][col] = 2; // Stone
            }

            // Randomly place pigs and blocks
            for (int row = rows - 2; row >= 0; row--) {
                for (int col = 0; col < cols; col++) {
                    int blockType = random.nextInt(5) - 1; // Random block or empty
                    if (blockType == 3) {
                        if (row > 0 && levelGrid[row + 1][col] != -1) {
                            levelGrid[row][col] = 3; // Place pig
                            levelGrid[row + 1][col] = 0; // Place wood below pig
                        } else {
                            continue;
                        }
                    } else if (blockType >= 0 && blockType <= 2) {
                        levelGrid[row][col] = blockType;
                    }
                }
            }
            levels[i] = levelGrid;
        }
        createPhysicsBodiesForAllLevels();
    }

    private void createPhysicsBodiesForAllLevels() {
        int xOffset = 0;

        for (int i = 0; i < levels.length; i++) {
            int[][] levelGrid = levels[i];
            createPhysicsBodies(levelGrid, xOffset);
            xOffset += (levelGrid[0].length + 3) * GRID_CELL_SIZE * scaleFactor;
        }
    }

    private void createPhysicsBodies(int[][] levelGrid, int xOffset) {
        int index = 0;
        for (int row = 0; row < levelGrid.length; row++) {
            for (int col = 0; col < levelGrid[row].length; col++) {
                int blockType = levelGrid[row][col];
                if (blockType != -1) {
                    float x = (col * GRID_CELL_SIZE * scaleFactor) + xOffset;
                    float y = row * GRID_CELL_SIZE * scaleFactor;

                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set(x / 100f, y / 100f);
                    Body body = world.createBody(bodyDef);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(GRID_CELL_SIZE * scaleFactor / 200f, GRID_CELL_SIZE * scaleFactor / 200f);
                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = shape;
                    fixtureDef.density = 1.0f;
                    body.createFixture(fixtureDef);
                    shape.dispose();

                    // Create sprite with shadows and outlines
                    Sprite sprite;
                    if (blockType == 0) {
                        sprite = new Sprite(woodTexture);
                    } else if (blockType == 1) {
                        sprite = new Sprite(glassTexture);
                    } else if (blockType == 2) {
                        sprite = new Sprite(stoneTexture);
                    } else {
                        sprite = new Sprite(pigTexture[index]);
                        index = (index + 1) % 3;
                    }

                    sprite.setPosition(x+850, y+200);
                    sprite.setSize(GRID_CELL_SIZE * scaleFactor, GRID_CELL_SIZE * scaleFactor);
                    sprite.setAlpha(0.9f);

                    stage.getBatch().begin();
                    sprite.draw(stage.getBatch());
                    stage.getBatch().end();


                }
            }
        }


        Sprite sprite[] = {
            new Sprite(new Texture(Gdx.files.internal("Background/red-bird.png"))),
            new Sprite(new Texture(Gdx.files.internal("Background/silver.png"))),
            new Sprite(new Texture(Gdx.files.internal("Background/terence.png"))),
            new Sprite(new Texture(Gdx.files.internal("Background/BlackBomb.png")))
        };
        int x[]={100,210,330,600};
        int y[]={230,240,240,660};
        for(int i=0;i<4;i++){
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(x[i] / 100f, y[i] / 100f);
            Body body = world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(GRID_CELL_SIZE * scaleFactor / 200f, GRID_CELL_SIZE * scaleFactor / 180f);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1.0f;
            body.createFixture(fixtureDef);
            shape.dispose();

            sprite[i].setPosition(x[i], y[i]);
            sprite[i].setSize(GRID_CELL_SIZE * scaleFactor + 30, GRID_CELL_SIZE * scaleFactor + 30);
            stage.getBatch().begin();
            sprite[i].draw(stage.getBatch());
            stage.getBatch().end();
        }

        Sprite gullel = new Sprite(gullelTexture);
        gullel.setPosition(410, 230);
        gullel.setSize(GRID_CELL_SIZE * scaleFactor + 80, GRID_CELL_SIZE * scaleFactor + 80);
        stage.getBatch().begin();
        gullel.draw(stage.getBatch());
        stage.getBatch().end();

    }

    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            scaleFactor += 0.1f;
            regenerateLevel();
        }
    }

    public void regenerateLevel() {
        stage.clear();
        createPhysicsBodiesForAllLevels();
    }

    public void dispose() {
        woodTexture.dispose();
        stoneTexture.dispose();
        glassTexture.dispose();

        for (Texture texture : pigTexture) {
            texture.dispose();
        }

        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }

        world.dispose();
    }
}
