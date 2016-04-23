package velychko.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import velychko.game.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Game.getInstance().width;
		config.height = Game.getInstance().height;
		config.title = Game.TITLE;
		new LwjglApplication(Game.getInstance(), config);
	}
}
