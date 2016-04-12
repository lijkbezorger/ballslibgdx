package velychko.game.models.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import velychko.game.Game;
import velychko.game.controllers.PlayController;

/**
 * @author Yaroslav Velychko
 */
public class MenuState extends State {

    private Stage stage;
    private Texture background;
    private int backgroundX;
    private int backgroundY;
    private TextButton buttonPlay;
    private TextButton.TextButtonStyle textButtonStyle;
    private TextureAtlas textureAtlas;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        stage = new Stage();

        background = new Texture("bg.png");
        backgroundX = (int) (Game.WIDTH / 2 - background.getWidth() / 2);
        backgroundY = (int) (Game.HEIGHT / 2 - background.getHeight() / 2 - 100);

        //Button
        textureAtlas = new TextureAtlas();
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        buttonPlay = new TextButton("Play", textButtonStyle);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("a");
            }
        });

        stage.addActor(buttonPlay);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gameStateManager.set(new PlayState(gameStateManager));
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(background, backgroundX, backgroundY);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
