package velychko.game.models.ball;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import velychko.game.Game;

/**
 * @author Yaroslav Velychko
 */
public class Ball extends TextureRegion {

    public final static int DIRECTION_UP = 0;
    public final static int DIRECTION_LEFT = 1;
    public final static int DIRECTION_DOWN = 2;
    public final static int DIRECTION_RIGHT = 3;

    public static final float SIZE = Game.getInstance().width / 10;

    public static final float SPEED = 3f;

    final Random random = new Random();

    public String name;

    public Rectangle bounds = new Rectangle();

    public Vector2 position;

    public Vector2 positionOnScreen;

    public Vector2 velocity;

    public int direction;

    public boolean isTouched;

    public boolean isDoubleClicked;

    public boolean isPunched;

    public boolean isPush;

    public boolean isRamed;

    public Ball ramedBall;

    public List<Ball> ballLine;

    public Ball(int iter, Vector2 position, Texture texture) {
        this.name = Integer.toString(iter);
        this.position = position;
        this.positionOnScreen = new Vector2(Ball.SIZE * position.x, Ball.SIZE * position.y + Game.getInstance().verticalOffset);
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
        this.isPush = false;
        this.isDoubleClicked = false;
        this.isPunched = false;
        this.isTouched = false;
        this.isRamed = false;
        this.direction = generateDirection();
        this.velocity = new Vector2(0, 0);

        super.setTexture(texture);
        super.setV2(1.0f);
        super.setU2(1.0f);
    }

    public int generateDirection() {
        int _random = random.nextInt(4) + 1;
        return _random;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public int getRotation() {
        int rotation = 0;
        switch (this.getDirection()) {
            case DIRECTION_UP: {
                rotation = 0;
                break;
            }
            case DIRECTION_RIGHT: {
                rotation = 90;
                break;
            }
            case DIRECTION_DOWN: {
                rotation = 180;
                break;
            }
            case DIRECTION_LEFT: {
                rotation = 270;
                break;
            }
        }
        return rotation - 90;
    }

    public void setSpeed() {
        Vector2 speed;
        switch (this.getDirection()) {
            case DIRECTION_UP: {
                speed = new Vector2(0, -SPEED);
                break;
            }
            case DIRECTION_RIGHT: {
                speed = new Vector2(SPEED, 0);
                break;
            }
            case DIRECTION_DOWN: {
                speed = new Vector2(0, SPEED);
                break;
            }
            case DIRECTION_LEFT: {
                speed = new Vector2(-SPEED, 0);
                break;
            }
            default: {
                speed = new Vector2(0, 0);
            }
        }
        this.setVelocity(speed);
        Game.getInstance().isBallInMove = true;
    }

    public void setBallLine(List<Ball> balls) {
        int ballX = (int) this.position.x;
        int ballY = (int) this.position.y;
        List<Ball> ballsInLine = new ArrayList<Ball>();
        switch (this.getDirection()) {
            case DIRECTION_UP: {
                for (Ball ball : balls) {
                    if (ball.position.y < ballY && ball.position.x == ballX) {
                        ballsInLine.add(ball);
                    }
                }
                break;
            }
            case DIRECTION_RIGHT: {
                for (Ball ball : balls) {
                    if (ball.position.x > ballX && ball.position.y == ballY) {
                        ballsInLine.add(ball);
                    }
                }
                break;
            }
            case DIRECTION_DOWN: {
                for (Ball ball : balls) {
                    if (ball.position.y > ballY && ball.position.x == ballX) {
                        ballsInLine.add(ball);
                    }
                }
                break;
            }
            case DIRECTION_LEFT: {
                for (Ball ball : balls) {
                    if (ball.position.x < ballX && ball.position.y == ballY) {
                        ballsInLine.add(ball);
                    }
                }
                break;
            }
            default: {

            }
        }
        this.ballLine = ballsInLine;
    }

    public boolean checkIsOutside(List<Ball> balls) {
        int width = Game.getInstance().width;
        int height = Game.getInstance().height;
        int verticalOffset = Game.getInstance().verticalOffset;

        if (this.positionOnScreen.x > width - SIZE || this.positionOnScreen.x < 0
                || this.positionOnScreen.y > height - verticalOffset - SIZE || this.positionOnScreen.y < verticalOffset) {
            if (this.isPunched) {
                unsetBall(this, balls);
                return true;
            } else {
                this.isTouched = false;
                this.isDoubleClicked = false;
                this.ballLine = null;
                this.positionOnScreen = new Vector2(Ball.SIZE * position.x, Ball.SIZE * position.y + Game.getInstance().verticalOffset);
            }

            Game.getInstance().isBallInMove = false;
        }

        return false;
    }

    public void unsetBall(Ball ball, List<Ball> balls) {
        int index = balls.indexOf(ball);
        balls.remove(index);
    }


    public void changeSpeed(List<Ball> balls) {
        changeEnergy(this.ramedBall, this, balls);
    }

    public void stopOld(Ball ball) {
        ball.velocity = new Vector2(0, 0);
        ball.isTouched = false;
        ball.isPush = false;
        ball.isPunched = false;
        ball.isDoubleClicked = false;
        ball.ballLine = null;
    }

    public void changeEnergy(Ball ramedBall, Ball oldBall, List<Ball> balls) {
        this.isRamed = false;
        this.ramedBall = null;
        ramedBall.direction = oldBall.direction;
        ramedBall.setSpeed();
        ramedBall.setBallLine(balls);
        ramedBall.isPunched = true;
    }

    public void checkIntersection() {
        int ballX = (int) this.positionOnScreen.x;
        int ballY = (int) this.positionOnScreen.y;
        if (this.ballLine != null) {
            switch (this.getDirection()) {
                case DIRECTION_UP: {
                    for (Ball ball : this.ballLine) {
                        if (ballY <= ball.positionOnScreen.y + Ball.SIZE) {
                            this.position = new Vector2(ball.position.x, ball.position.y + 1);
                            this.positionOnScreen = new Vector2(Ball.SIZE * ball.position.x, Ball.SIZE * (ball.position.y + 1) + Game.getInstance().verticalOffset);
                            this.isRamed = true;
                            this.ramedBall = ball;
                            stopOld(this);
                        }
                    }
                    break;
                }
                case DIRECTION_RIGHT: {
                    for (Ball ball : this.ballLine) {
                        if (ballX >= ball.positionOnScreen.x - Ball.SIZE) {
                            this.position = new Vector2(ball.position.x - 1, ball.position.y);
                            this.positionOnScreen = new Vector2(Ball.SIZE * (ball.position.x - 1), Ball.SIZE * ball.position.y + Game.getInstance().verticalOffset);
                            this.isRamed = true;
                            this.ramedBall = ball;
                            stopOld(this);
                        }
                    }
                    break;
                }
                case DIRECTION_DOWN: {
                    for (Ball ball : this.ballLine) {
                        if (ballY >= ball.positionOnScreen.y - Ball.SIZE) {
                            this.position = new Vector2(ball.position.x, ball.position.y - 1);
                            this.positionOnScreen = new Vector2(Ball.SIZE * ball.position.x, Ball.SIZE * (ball.position.y - 1) + Game.getInstance().verticalOffset);
                            this.isRamed = true;
                            this.ramedBall = ball;
                            stopOld(this);
                        }
                    }
                    break;
                }
                case DIRECTION_LEFT: {
                    for (Ball ball : this.ballLine) {
                        if (ballX <= ball.positionOnScreen.x + Ball.SIZE) {
                            this.position = new Vector2(ball.position.x + 1, ball.position.y);
                            this.positionOnScreen = new Vector2(Ball.SIZE * (ball.position.x + 1), Ball.SIZE * ball.position.y + Game.getInstance().verticalOffset);
                            this.isRamed = true;
                            this.ramedBall = ball;
                            stopOld(this);
                        }
                    }
                    break;
                }
                default: {

                }
            }
        }
    }

}
