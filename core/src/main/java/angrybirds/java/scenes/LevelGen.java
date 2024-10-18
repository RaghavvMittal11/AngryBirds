//package angrybirds.java.scenes;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.*;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//
//import java.util.Random;
//
//public class LevelGen {
//    private World world;
//    private Stage stage;
//    private float scaleFactor = 1.0f;
//
//    private final int GRID_CELL_SIZE = 64; // Size of each cell in pixels
//
//    private Texture woodTexture;
//    private Texture stoneTexture;
//    private Texture glassTexture;
//    private Texture pigTexture;
//
//    public LevelGen(World world, Stage stage) {
//        this.world = world;
//        this.stage = stage;
//
//        // Load textures
//        woodTexture = new Texture(Gdx.files.internal("Background/Wood Block.png"));
//        stoneTexture = new Texture(Gdx.files.internal("Background/Stone Block.png"));
//        glassTexture = new Texture(Gdx.files.internal("Background/Glass Block.png"));
//        pigTexture = new Texture(Gdx.files.internal("Background/enemy.png"));
//    }
//
//    public void generateRandomLevel() {
//        Random random = new Random();
//
//        // Define a grid size for the level (rows and columns)
//        int numRows = 10;
//        int numCols = 20;
//
//        // Define a 2D grid to place blocks
//        int[][] levelGrid = new int[numRows][numCols];
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                levelGrid[i][j] = -1;
//            }
//        }
//
//        // Define probabilities for each block type
//        double woodProbability = 0.4;
//        double stoneProbability = 0.3;
//        double glassProbability = 0.3;
//
//        // Fill the grid with random blocks
//        for (int row = 0; row < numRows; row++) {
//            for (int col = 0; col < numCols; col++) {
//                double randValue = random.nextDouble();
//
//                if (randValue < woodProbability) {
//                    levelGrid[row][col] = 0;
//                } else if (randValue < woodProbability + stoneProbability) {
//                    levelGrid[row][col] = 1;
//                } else {
//                    levelGrid[row][col] = 2;
//                }
//            }
//        }
//
//        // Ensure that blocks do not float by adding support-checking logic
//        ensureStability(levelGrid);
//
//        // Place pigs
//        placePigs(levelGrid, 5); // Number of pigs to place
//
//        // Create physics bodies for the level
//        createPhysicsBodies(levelGrid);
//    }
//
//    private void ensureStability(int[][] levelGrid) {
//        for (int row = 1; row < levelGrid.length; row++) {
//            for (int col = 0; col < levelGrid[row].length; col++) {
//                if (levelGrid[row][col] != -1 && levelGrid[row - 1][col] == -1) {
//                    levelGrid[row][col] = -1; // Remove block if no support beneath
//                }
//            }
//        }
//    }
//
//    private void placePigs(int[][] levelGrid, int numPigs) {
//        Random random = new Random();
//        int count = 0;
//
//        while (count < numPigs) {
//            int row = random.nextInt(levelGrid.length);
//            int col = random.nextInt(levelGrid[0].length);
//
//            if (levelGrid[row][col] == -1) {
//                // Place a pig if the space is empty
//                levelGrid[row][col] = 3;
//                count++;
//            }
//        }
//    }
//
//    private void createPhysicsBodies(int[][] levelGrid) {
//        for (int row = 0; row < levelGrid.length; row++) {
//            for (int col = 0; col < levelGrid[0].length; col++) {
//                int blockType = levelGrid[row][col];
//                if (blockType != -1) {
//                    float x = col * GRID_CELL_SIZE * scaleFactor;
//                    float y = row * GRID_CELL_SIZE * scaleFactor;
//
//                    // Create physics body
//                    BodyDef bodyDef = new BodyDef();
//                    bodyDef.type = BodyDef.BodyType.StaticBody;
//                    bodyDef.position.set(x / 100f, y / 100f); // Convert to meters
//
//                    Body body = world.createBody(bodyDef);
//
//                    PolygonShape shape = new PolygonShape();
//                    shape.setAsBox(GRID_CELL_SIZE * scaleFactor / 200f, GRID_CELL_SIZE * scaleFactor / 200f); // Half-width and half-height
//
//                    FixtureDef fixtureDef = new FixtureDef();
//                    fixtureDef.shape = shape;
//                    fixtureDef.density = 1.0f;
//
//                    body.createFixture(fixtureDef);
//                    shape.dispose();
//
//                    // Add visual representation
//                    Image image;
//                    if (blockType == 0) {
//                        image = new Image(woodTexture);
//                    } else if (blockType == 1) {
//                        image = new Image(stoneTexture);
//                    } else if (blockType == 2) {
//                        image = new Image(glassTexture);
//                    } else {
//                        image = new Image(pigTexture);
//                    }
//
//                    image.setPosition(x, y);
//                    image.setSize(GRID_CELL_SIZE * scaleFactor, GRID_CELL_SIZE * scaleFactor);
//                    stage.addActor(image);
//                }
//            }
//        }
//    }
//
//    public void update() {
//        // Scale the level up or down on custom input
//        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
//            scaleFactor += 0.1f;
//            regenerateLevel();
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
//            scaleFactor = Math.max(0.1f, scaleFactor - 0.1f);
//            regenerateLevel();
//        }
//    }
//
//    private void regenerateLevel() {
//        stage.clear();
////        world.dispose();
////        world = new World(new Vector2(0, -9.8f), true);
//        generateRandomLevel();
//    }
//
//    public void dispose() {
//        woodTexture.dispose();
//        stoneTexture.dispose();
//        glassTexture.dispose();
//        pigTexture.dispose();
//        world.dispose();
//    }
//
//}
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
        // Define a grid size for the level (rows and columns)
        int numRows = 5;
        int numCols = 5;

        // Define a 2D grid to place blocks

        for (int i = 0; i < numRows-1; i++) {
            for (int j = 0; j < numCols; j++) {
                levelGrid[i][j] = -1;
            }
        }


        // Define probabilities for each block type
        double woodProbability = 0.1;
        double stoneProbability = 0.1;
        double glassProbability = 0.1;

        // Fill the grid with random blocks
//        for (int row = 0; row < numRows-1; row++) {
//            for (int col = 0; col < numCols; col++) {
//                double randValue = random.nextDouble();
//
//                if (randValue < woodProbability) {
//                    levelGrid[row][col] = 0;
//
//                } else if (randValue < woodProbability + stoneProbability) {
//                    levelGrid[row][col] = 1;
//                } else {
//                    levelGrid[row][col] = 2;
//                }
//            }
//        }

        int row=0;
        int col=0;
        while(row<numRows-1){
            while(col<numCols){
                double randValue = random.nextDouble();

                if (randValue < 0.25) {
                    levelGrid[row][col] = 0;
                    col+=2;
                } else if (randValue < 0.5) {
                    levelGrid[row][col] = 1;
                    col+=2;
                } else if(randValue<0.75) {
                    levelGrid[row][col] = 2;
                    col+=2;
                }
            }
            row++;
        }
        for(int j=0;j<numCols;j++){
            levelGrid[4][j] = 1;
        }

        // Ensure that blocks do not float by adding support-checking logic
        ensureStability(levelGrid);

        // Place pigs
        placePigs(levelGrid); // Number of pigs to place


    }

    private void ensureStability(int[][] levelGrid) {
        for (int row = 1; row < levelGrid.length; row++) {
            for (int col = 0; col < levelGrid[row].length; col++) {
                if (levelGrid[row][col] != -1 && levelGrid[row - 1][col] == -1) {
                    levelGrid[row][col] = -1; // Remove block if no support beneath
                }
            }
        }
    }


    private void placePigs(int[][] levelGrid) {
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(levelGrid[i][j] == -1){
                    levelGrid[i][j] = 1;
//   System.out.println("piggie");
//                    break;
                }
            }
        }
    }




    private void createPhysicsBodies(int[][] levelGrid) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
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
//                    System.out.println("Here");
                    sprite.setPosition(x, y);
                    sprite.setSize(GRID_CELL_SIZE * scaleFactor, GRID_CELL_SIZE * scaleFactor);
                    stage.getBatch().begin();
                    sprite.draw(stage.getBatch());
                    stage.getBatch().end();
                }
            }
        }
    }

    public void update() {// Scale the level up or down on custom input
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            scaleFactor += 0.1f;
            regenerateLevel();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            scaleFactor = Math.max(0.1f, scaleFactor - 0.1f);
            regenerateLevel();
        }
    }

    public void regenerateLevel() {
        stage.clear();
        createPhysicsBodies(levelGrid);

//        world.dispose();
//        world = new World(new Vector2(0, -9.8f), true);
//        generateRandomLevel();
    }

    public void dispose() {
        woodTexture.dispose();
        stoneTexture.dispose();
        glassTexture.dispose();
        pigTexture.dispose();
        world.dispose();
    }
}
