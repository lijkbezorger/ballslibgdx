package velychko.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import velychko.game.models.states.GameStateManager;
import velychko.game.models.states.MenuState;

public class Game extends ApplicationAdapter {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 1240;

    public static final String TITLE = "Plush balls";

    OrthographicCamera camera;

    private GameStateManager gameStateManager;
    private SpriteBatch batch;


    @Override
    public void create() {
        Gdx.gl.glClearColor(1, 1, 1, 0);

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.setToOrtho(true, WIDTH, HEIGHT);

        batch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        gameStateManager.push(new MenuState(gameStateManager));

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
