package angrybirds.java;

import angrybirds.java.scenes.MainMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class Main extends Game {
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
//        GameManager.getInstance().initializeGameData();
        this.setScreen(new MainMenu(this));

    }


    @Override
    public void render() {
        super.render(); // important!

    }

    public void resize(int width, int height) {
        // Update the viewport dimensions when the screen is resized
        batch.getProjectionMatrix().setToOrtho2D(111, 111, width, height);
    }

    public SpriteBatch getBatch() {
        return this.batch;
}


    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }


}
