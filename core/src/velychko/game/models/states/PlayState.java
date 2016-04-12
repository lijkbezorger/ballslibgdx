package velychko.game.models.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import velychko.game.Game;
import velychko.game.controllers.PlayController;
import velychko.game.models.ball.Ball;
import velychko.game.models.ball.BallManager;

/**
 * @author Yaroslav Velychko
 */
public class PlayState extends State {

    private Texture background;

    private List<Ball> balls;

    Ball[] ballsOnField;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);

        background = new Texture("field.png");

        BallManager ballManager = new BallManager();

        ballManager.setCurrentLevel(1);
        ballManager.getLevelName();
        this.balls = ballManager.getBalls();
        ballsOnField = new Ball[this.balls.size()];

        PlayController playController = new PlayController(this.balls, this);
        Gdx.input.setInputProcessor(playController);

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    public Ball[] getBallsOnField() {
        return ballsOnField;
    }

    public void setBallsOnField(Ball[] ballsOnField) {
        this.ballsOnField = ballsOnField;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 150, Game.WIDTH, Game.WIDTH);
        int index = 0;
        for (Ball ball : this.balls) {
            ball.getRotation();
            ballsOnField[index++] = ball;
            spriteBatch.draw(ball, ball.postionOnScreen.x, ball.postionOnScreen.y, Ball.SIZE/2, Ball.SIZE/2, Ball.SIZE, Ball.SIZE, 1, 1, ball.getRotation(), true);
        }
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
