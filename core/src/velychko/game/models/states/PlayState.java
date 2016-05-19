package velychko.game.models.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.List;

import velychko.game.Game;
import velychko.game.controllers.PlayController;
import velychko.game.models.ball.Ball;
import velychko.game.models.ball.BallManager;
import velychko.game.models.uicomponent.FinishDialog;

/**
 * @author Yaroslav Velychko
 */
public class PlayState extends State {

    private Stage stage;

    public FinishDialog finishDialog;

    private Texture background;

    private List<Ball> balls;

    Ball[] ballsOnField;

    PlayController playController;

    public PlayState(GameStateManager gameStateManager, int level) {
        super(gameStateManager);

        background = new Texture("field.png");

        BallManager ballManager = new BallManager();

        ballManager.setCurrentLevel(level);
        ballManager.getLevelName();
        this.balls = ballManager.getBalls();
        ballsOnField = new Ball[this.balls.size()];

        playController = new PlayController(this.balls, this);
        Gdx.input.setInputProcessor(playController);

//        stage = new Stage();
//
//        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"), Game.getInstance().skin);
//        finishDialog = new FinishDialog("go next", skin, gameStateManager);
//        finishDialog.show(stage);
//        stage.addActor(finishDialog);
//
//        Gdx.input.setInputProcessor(stage);
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
        spriteBatch.draw(background, 0, Game.getInstance().verticalOffset, Game.getInstance().width, Game.getInstance().width);
        int index = 0;
        for (Ball ball : this.balls) {
            ball.getRotation();
            ballsOnField[index++] = ball;

            if (Game.getInstance().isBallInMove) {

                if (ball.checkIsOutside(balls)) {
                    Game.getInstance().isBallInMove = false;
                    break;
                }
                ball.checkIntersection();

                if (ball.isRamed) {
                    ball.changeSpeed(balls);
                }
            }

            if (ball.isPush || ball.isPunched) {
                ball.positionOnScreen.x += ball.getVelocity().x;
                ball.positionOnScreen.y += ball.getVelocity().y;
            }


            spriteBatch.draw(ball, ball.positionOnScreen.x, ball.positionOnScreen.y, Ball.SIZE / 2, Ball.SIZE / 2, Ball.SIZE, Ball.SIZE, 1, 1, ball.getRotation(), true);
        }
        if (balls.size() == 1) {
            stage.draw();
        }

        spriteBatch.end();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
