package velychko.game.models.ball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Yaroslav Velychko
 */
public class BallManager {

    final Random random = new Random();

    public Texture[] ballTextures;

    private List<Level> levelList;

    private Level currentLevel;


    public BallManager() {
        this.pushBallTexture();
        this.readLevels();
    }

    public void pushBallTexture() {
        this.ballTextures = new Texture[6];

        this.ballTextures[0] = new Texture("balls/1_u.gif");
        this.ballTextures[1] = new Texture("balls/2_u.gif");
        this.ballTextures[2] = new Texture("balls/3_u.gif");
        this.ballTextures[3] = new Texture("balls/4_u.gif");
        this.ballTextures[4] = new Texture("balls/5_u.gif");
        this.ballTextures[5] = new Texture("balls/6_u.gif");
    }

    public int generateImage() {
        int _random = random.nextInt(5);
        return _random;
    }

    public void readLevels() {

        this.levelList = new ArrayList<Level>();

        try {
            FileHandle handle = Gdx.files.internal("lvl/levels.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            try {
                Document document = documentBuilder.parse(new ByteArrayInputStream(handle.readBytes()));

                document.getDocumentElement().normalize();

                NodeList nodeList = document.getElementsByTagName("level");

                Level level;

                for (int iterator = 0; iterator < nodeList.getLength(); iterator++) {
                    Node node = nodeList.item(iterator);
                    Element element = (Element) node;

                    String levelName = element.getAttribute("name");
                    int levelNumber = Integer.parseInt(element.getAttribute("order"));

                    NodeList ballList = element.getElementsByTagName("ball");

                    List<Ball> levelBallList = new ArrayList<Ball>();
                    Ball ball;

                    for (int iter = 0; iter < ballList.getLength(); iter++) {
                        Element ballIn = (Element) ballList.item(iter);

                        NodeList positionX = ballIn.getElementsByTagName("position-x");
                        NodeList positionY = ballIn.getElementsByTagName("position-y");

                        int x = Integer.parseInt(positionX.item(0).getChildNodes().item(0).getNodeValue());
                        int y = Integer.parseInt(positionY.item(0).getChildNodes().item(0).getNodeValue());

                        ball = new Ball(new Vector2(y, x), ballTextures[generateImage()]);
                        levelBallList.add(ball);
                    }

                    level = new Level(levelName, levelNumber, levelBallList);
                    this.levelList.add(level);
                }

            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentLevel(int levelNumber) {
        this.currentLevel = this.levelList.get(levelNumber - 1);
    }

    public String getLevelName() {
        Level level = this.currentLevel;
        if (level instanceof Level) {
            return level.getLevelName();
        } else {
            return "Level #";
        }
    }

    public List<Ball> getBalls() {
        return this.currentLevel.getBallList();
    }

}
