package angrybirds.java.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Birds extends Sprite {
    private World world;
    private Body body;

    private float scaleFactor = 0.7f;
    private final int LEVEL_COUNT = 100;
    private final int GRID_CELL_SIZE = 64;
    // Constructor
    public Birds(World world, int x, int y,int bird) {
        // Store the world reference
        this.world = world;

        // Declare the texture that will be used
        Texture birdTexture;
//       this.stage = stage;
        // Choose the texture based on the bird type

        if (bird == 1) {
            birdTexture = new Texture("Background/red-bird.png");
            System.out.println("red");
        } else if (bird == 2) {
            birdTexture = new Texture("Background/silver.png");
            System.out.println("silver");
        } else if (bird == 3) {
            birdTexture = new Texture("Background/terence.png");
            System.out.println("terence");
        } else{
            birdTexture = new Texture("Background/red-bird.png");
        }

        // Call the Sprite constructor with the selected texture
        System.out.println("------------------------------------------------------------------------------------");
        super.setTexture(birdTexture);
        this.world=world;
        setPosition(x,y);
        setSize(GRID_CELL_SIZE * scaleFactor + 100030, GRID_CELL_SIZE * scaleFactor + 1000030);
        createBody(x,y);

//        stage.getBatch().begin();
//        this.draw(stage.getBatch());
//        stage.getBatch().end();

    }
    void createBody(int x,int y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x / 100f, y / 100f);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GRID_CELL_SIZE * scaleFactor / 200f, GRID_CELL_SIZE * scaleFactor / 180f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void dispose() {
    }
}
