package velychko.game.models.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.List;

import velychko.game.Game;
import velychko.game.models.ball.BallManager;
import velychko.game.models.ball.Level;
import velychko.game.models.uicomponent.MyClickListener;

/**
 * @author Yaroslav Velychko
 */
public class MenuState extends State {

    public Stage stage;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        stage = new Stage();

        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"), Game.getInstance().skin);

        BallManager ballManager = new BallManager();
        List<Level> levelList = ballManager.levelList;


        int width = (Game.getInstance().width - 120) / 3;
        int height = (Game.getInstance().height - 220) / 10;

        int xStep = 30;
        int yStep = 20;

        int xPosition = xStep;
        int yPosition = Game.getInstance().height - yStep - height;
        int counter = 1;


        //Create buttons

        for (final Level level : levelList) {
            String buttonName = level.levelName;
            TextButton button = new TextButton(buttonName, skin);

            button.getLabel().setFontScale(3);
            button.setPosition(xPosition, yPosition);
            button.setSize(width, height);
            button.addListener(new MyClickListener(gameStateManager) {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    gameStateManager.set(new PlayState(gameStateManager, level.levelNumber));
                }
            });
            xPosition += width + xStep;
            if (counter == 3) {
                yPosition -= (yStep + height);
                xPosition = xStep;
                counter = 0;
            }
            counter++;

            stage.addActor(button);
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
//            gameStateManager.set(new PlayState(gameStateManager));
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
    }

    @Override
    public void dispose() {
    }
}
