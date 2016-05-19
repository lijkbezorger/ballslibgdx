package velychko.game.models.uicomponent;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import velychko.game.models.states.GameStateManager;

/**
 * @author Yaroslav Velychko
 */
public class MyClickListener extends ClickListener{

    public GameStateManager gameStateManager;

    public MyClickListener(GameStateManager gsm) {
        super();
        gameStateManager = gsm;
    }

}
