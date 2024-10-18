package angrybirds.java.ground;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;

public class Ground1 extends Sprite {
    private World world;
    private Body body;
    private String name;

    private static  float GROUND_WIDTH = 1000.0f;
    private static float GROUND_HEIGHT = 130.0f;

    public Ground1(World world, String name) {
        super(new Texture("Background/ground.png"));
        this.world = world;
        this.name = name;
        createGround(); // Initialize the ground in the constructor
    }


    private void createGround() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
//        bodyDef.size.set(0, 0);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(getWidth()*2,getHeight()*2 - 75);
        Fixture fixture = body.createFixture(shape, 1.0f);
        fixture.setUserData(name);
         shape.dispose();
    }

    public void setSpritePosition(float x, float y) {
        setPosition(x, y);
        setSpriteSize(GROUND_WIDTH * 2, GROUND_HEIGHT * 2);
    }

    public void setSpriteSize(float width, float height) {
        setSize(width, height);
    }

    public String getName() {
        return name;
    }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        getTexture().dispose();
        world.destroyBody(body); // Properly clean up the Box2D body
    }
}
