package velychko.game.models.ball;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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

    public static final float SIZE = 72;

    public static final float SPEED = 3f;


    final Random random = new Random();

    public Rectangle bounds = new Rectangle();

    public Vector2 position;

    public Vector2 positionOnScreen;
    public Vector2 originOnScreen;

    public Vector2 velocity;

    public int direction;

    public boolean isPush;

    public boolean isDoubleClicked;

    public boolean isPunched;

    public boolean isTouched;


    public Ball(Vector2 position, Texture texture) {
        this.position = position;
        this.positionOnScreen = new Vector2(Ball.SIZE * position.x, Ball.SIZE * position.y + 150);
        this.originOnScreen = new Vector2(Ball.SIZE * position.x, Ball.SIZE * position.y + 150);
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
        this.isPush = false;
        this.isDoubleClicked = false;
        this.isPunched = false;
        this.isTouched = false;
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

    public boolean isTouched() {
        return isTouched;
    }

    public void setIsTouched(boolean isTouched) {
        this.isTouched = isTouched;
    }

    public boolean isPush() {
        return isPush;
    }

    public void setIsPush(boolean isPush) {
        this.isPush = isPush;
    }

    public boolean isPunched() {
        return isPunched;
    }

    public void setIsPunched(boolean isPunched) {
        this.isPunched = isPunched;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getPositionX() {
        return position.y;
    }

    public float getPositionY() {
        return position.x;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPositionOnScreen() {
        return positionOnScreen;
    }

    public void setPositionOnScreen(Vector2 positionOnScreen) {
        this.positionOnScreen = positionOnScreen;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
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
    }

    public void checkIsOutside() {
        if (this.positionOnScreen.x > Game.WIDTH-SIZE || this.positionOnScreen.x < 0 || this.positionOnScreen.y > Game.HEIGHT-370-SIZE || this.positionOnScreen.y < 150) {
            this.isTouched = false;
            this.isDoubleClicked = false;
            this.positionOnScreen = this.originOnScreen;
        }
    }

}
