package velychko.game.models.uicomponent;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import velychko.game.models.states.GameStateManager;
import velychko.game.models.states.PlayState;

/**
 * @author Yaroslav Velychko
 */
public class FinishDialog extends Dialog {

    public GameStateManager gameStateManager;

    public FinishDialog(String title, Skin skin) {
        super(title, skin);
    }

    public FinishDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public FinishDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    public FinishDialog(String title, Skin skin, GameStateManager gsm) {
        super(title, skin);
        gameStateManager = gsm;
    }

    {
        text("You pass this level");
        button("Go next one", "See you");
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        gameStateManager.set(new PlayState(gameStateManager, 1));
    }
}
