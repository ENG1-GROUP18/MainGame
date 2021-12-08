package york.eng1.team18;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import york.eng1.team18.views.EndScreen;
import york.eng1.team18.views.MainScreen;
import york.eng1.team18.views.MenuScreen;
import york.eng1.team18.views.SplashScreen;

public class Orchestrator extends Game {
	// TOGGLE TO DISABLE SPLASH SCREEN FOR DEBUGGING
	boolean splashScreenEnabled = true;

	private SplashScreen loadingScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;

	public final static int MENU = 0;
	public final static int APPLICATION = 1;
	public final static int ENDGAME = 2;

	@Override
	public void create() {

		if (splashScreenEnabled) {
			loadingScreen = new SplashScreen(this);
			this.setScreen(loadingScreen);
		} else {
			menuScreen = new MenuScreen(this);
			this.setScreen(menuScreen);
		}
	}

	public void changeScreen(int screen){
		switch(screen) {
			case MENU:
				if(menuScreen == null) {
					menuScreen = new MenuScreen(this);
					this.setScreen(menuScreen);
				}
				break;
			case APPLICATION:
				if(mainScreen == null) {
					mainScreen = new MainScreen(this);
					this.setScreen(mainScreen);
				}
				break;
			case ENDGAME:
				if(endScreen == null) {
					endScreen = new EndScreen(this);
					this.setScreen(endScreen);
				}
				break;
		}
	}

}
