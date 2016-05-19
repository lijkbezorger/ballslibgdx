package velychko.game.models.ball;


import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yaroslav Velychko
 */
public class Level {

    public String levelName;

    public int levelNumber;

    public List<Ball> ballList;

    public Level(String levelName, int levelNumber, List<Ball> ballList) {
        this.levelName = levelName;
        this.levelNumber = levelNumber;
        this.ballList = ballList;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public List<Ball> getBallList() {
        return ballList;
    }

    public void setBallList(List<Ball> ballList) {
        this.ballList = ballList;
    }
}
