package angrybirds.java.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

public class LevelGen {
    private World world;
    private Stage stage;
    private float scaleFactor = 1.0f;
    private int[][] levelGrid = new int[5][5];
    private final int GRID_CELL_SIZE = 64; // Size of each cell in pixels
    private Texture woodTexture;
    private Texture stoneTexture;
    private Texture glassTexture;
    private Texture pigTexture;

    public LevelGen(World world, Stage stage) {
        this.world = world;
        this.stage = stage;

        // Load textures
        woodTexture = new Texture(Gdx.files.internal("Background/Wood Block.png"));
        stoneTexture = new Texture(Gdx.files.internal("Background/Stone Block.png"));
        glassTexture = new Texture(Gdx.files.internal("Background/Glass Block.png"));
        pigTexture = new Texture(Gdx.files.internal("Background/enemy.png"));

        generateRandomLevel();
    }

    public void generateRandomLevel() {
        Random random = new Random();
        int numRows = 6;
        int numCols = 6;
        levelGrid = new int[numRows][numCols];

        // Initialize the grid with -1 (empty)
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                levelGrid[i][j] = -1;

            }
        }

        // Define probabilities
        double woodProbability = 0.09;
        double stoneProbability = 0.09;
        double glassProbability = 0.09;
        double pigProbability = 0.1;

        
        for (int row = 0; row < numRows - 1; row++) {
            int emptyIndex = random.nextInt(numCols);
            for (int col = 0; col < numCols; col++) {
                if (col == emptyIndex) {
                    continue;
                }
                double randValue = random.nextDouble();
                if (randValue < woodProbability) {
                    levelGrid[row][col] = 0; // Wood block
                } else if (randValue < woodProbability + stoneProbability) {
                    levelGrid[row][col] = 1; // Stone block
                } else if (randValue < woodProbability + stoneProbability + glassProbability) {
                    levelGrid[row][col] = 2; // Glass block
                } else if (randValue < woodProbability + stoneProbability + glassProbability + pigProbability) {
                    levelGrid[row][col] = 3; // Piggy
                }
            }
        }

        // Fill the bottom-most row with stone blocks
        for (int j = 0; j < numCols; j++) {
            levelGrid[0][j] = 1; // Stone block
        }

        // Ensure that blocks do not float by adding support-checking logic
        ensureStability(levelGrid);

        // Place pigs based on specified conditions
        placePigs(levelGrid);
    }

    private void ensureStability(int[][] levelGrid) {
        int numRows = levelGrid.length;
        int numCols = levelGrid[0].length;
        for (int row = numRows - 2; row >= 0; row--) {
            for (int col = 0; col < numCols; col++) {
                if (levelGrid[row][col] != -1 && levelGrid[row + 1][col] == -1) {
                    levelGrid[row + 1][col] = 1;
                }
            }
        }
    }

    private void placePigs(int[][] levelGrid) {
        int numRows = levelGrid.length;
        int numCols = levelGrid[0].length;
        for (int row = 1; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (levelGrid[row][col] == -1 && (levelGrid[row - 1][col] == 1 || levelGrid[row - 1][col] == 2)) {
                    levelGrid[row][col] = 3; // Piggy
                }
            }
        }
    }

    private void createPhysicsBodies(int[][] levelGrid) {
        for (int row = 0; row < levelGrid.length; row++) {
            for (int col = 0; col < levelGrid[row].length; col++) {
                int blockType = levelGrid[row][col];
                if (blockType != -1) {
                    float x = col * GRID_CELL_SIZE * scaleFactor;
                    float y = row * GRID_CELL_SIZE * scaleFactor;

                    // Create physics body
                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set(x / 100f, y / 100f); // Convert to meters
                    Body body = world.createBody(bodyDef);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(GRID_CELL_SIZE * scaleFactor / 200f, GRID_CELL_SIZE * scaleFactor / 200f); // Half-width and half-height

                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = shape;
                    fixtureDef.density = 1.0f;
                    body.createFixture(fixtureDef);
                    shape.dispose();

                    // Create sprite for the visual representation
                    Sprite sprite;
                    if (blockType == 0) {
                        sprite = new Sprite(woodTexture);
                    } else if (blockType == 1) {
                        sprite = new Sprite(stoneTexture);
                    } else if (blockType == 2) {
                        sprite = new Sprite(glassTexture);
                    } else {
                        sprite = new Sprite(pigTexture);
                    }
                    sprite.setPosition(x, y);
                    sprite.setSize(GRID_CELL_SIZE * scaleFactor, GRID_CELL_SIZE * scaleFactor);
                    stage.getBatch().begin();
                    sprite.draw(stage.getBatch());
                    stage.getBatch().end();
                }
            }
        }
    }

    public void update() {
        // Scale the level up or down on custom input
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            scaleFactor += 0.1f;
            regenerateLevel();
        }
    }

    public void regenerateLevel() {
        stage.clear();
        createPhysicsBodies(levelGrid);
    }

    public void dispose() {
        woodTexture.dispose();
        stoneTexture.dispose();
        glassTexture.dispose();
        pigTexture.dispose();
        world.dispose();
    }
}
