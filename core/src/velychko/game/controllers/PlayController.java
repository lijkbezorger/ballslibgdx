package velychko.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.List;

import velychko.game.Game;
import velychko.game.models.ball.Ball;
import velychko.game.models.states.PlayState;

/**
 * @author Yaroslav Velychko
 */
public class PlayController implements InputProcessor {

    public int activeBallIndex = -1;
    public int doubleBallIndex = -1;

    public long firstClick = 0;
    public long secondClick = 0;

    public PlayState playState;
    public List<Ball> balls;

    public PlayController(List<Ball> balls, PlayState playState) {
        this.playState = playState;
        this.balls = balls;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!Game.getInstance().isBallInMove) {
            PlayState playState = this.playState;
            Ball[] ballsOnField = playState.getBallsOnField();

            int index = 0;
            for (Ball ball : ballsOnField) {
                int ballX1 = (int) ball.positionOnScreen.x;
                int ballY1 = (int) ball.positionOnScreen.y;

                int ballX2 = ball.getRegionWidth() + ballX1;
                int ballY2 = ball.getRegionHeight() + ballY1;

                if (touchBall(screenX, screenY, ballX1, ballY1, ballX2, ballY2)) {
                    if (this.doubleBallIndex == index) {
                        secondClick = System.currentTimeMillis();
                        if ((secondClick - firstClick) < 200) {
                            ball.isDoubleClicked = true;
                            ball.setSpeed();
                            ball.setBallLine(balls);
                        }
                    }

                    this.activeBallIndex = index;
                    this.doubleBallIndex = index;
                    ball.isTouched = true;
                    Gdx.input.vibrate(35);
                    break;
                } else {
                    this.activeBallIndex = -1;
                    ball.isTouched = false;
                }
                index++;
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!Game.getInstance().isBallInMove) {
            if (this.activeBallIndex != -1) {
                firstClick = System.currentTimeMillis();
                Ball ball = playState.getBallsOnField()[this.activeBallIndex];
                defineDirection(screenX, screenY, ball);
            }
        }
        return true;
    }

    public boolean touchBall(int clickX, int clickY, int ballX1, int ballY1, int ballX2, int ballY2) {
        if (((ballX1 <= clickX) && (clickX <= ballX2)) && ((ballY1 <= clickY) && (clickY <= ballY2))) {
            return true;
        } else {
            return false;
        }
    }

    public void defineDirection(int screenX, int screenY, Ball ball) {
        int centerX = (int) (ball.positionOnScreen.x + ball.SIZE / 2);
        int centerY = (int) (ball.positionOnScreen.y + ball.SIZE / 2);

        int diffX = centerX - screenX;
        int diffY = centerY - screenY;

        if (Math.abs(diffX) > ball.SIZE || Math.abs(diffY) > ball.SIZE) {
            if ((Math.abs(diffX) > Math.abs(diffY))) {
                ball.setDirection((diffX < 0) ? ball.DIRECTION_RIGHT : ball.DIRECTION_LEFT);
            } else {
                ball.setDirection((diffY < 0) ? ball.DIRECTION_DOWN : ball.DIRECTION_UP);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
