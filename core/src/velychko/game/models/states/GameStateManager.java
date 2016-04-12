package velychko.game.models.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * @author Yaroslav Velychko
 */
public class GameStateManager {

    private Stack<State> states;

    /**
     * Constructor
     */
    public GameStateManager() {
        states = new Stack<State>();
    }

    /**
     * Push element to top in stack
     *
     * @param state State
     */
    public void push(State state) {
        states.push(state);
    }

    /**
     * Pop element from top of the stack
     */
    public void pop() {
        states.pop();
    }

    /**
     * Remove top element with dispose
     * Push new state(screen) to stack
     *
     * @param state State
     */
    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Update current state(screen)
     *
     * @param deltaTime float
     */
    public void update(float deltaTime) {
        states.peek().update(deltaTime);
    }


    /**
     * Get current state(screen)
     * update it and render
     *
     * @param spriteBatch SpriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        states.peek().render(spriteBatch);
    }

}
