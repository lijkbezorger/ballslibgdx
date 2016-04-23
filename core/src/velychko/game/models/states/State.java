package velychko.game.models.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import velychko.game.Game;

/**
 * @author Yaroslav Velychko
 */

public abstract class State {

    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStateManager gameStateManager;

    public State(GameStateManager gameStateManager) {
        camera = new OrthographicCamera(Game.getInstance().width, Game.getInstance().height);
        mouse = new Vector3();
        this.gameStateManager = gameStateManager;
    }

    /**
     * Handle input (touch or push on input elements)
     */
    protected abstract void handleInput();

    /**
     * Update image
     *
     * @param deltaTime float
     */
    public abstract void update(float deltaTime);

    /**
     * Render screen
     *
     * @param spriteBatch SpriteBatch
     */
    public abstract void render(SpriteBatch spriteBatch);

    /**
     * Clean resources
     */
    public abstract void dispose();

}

