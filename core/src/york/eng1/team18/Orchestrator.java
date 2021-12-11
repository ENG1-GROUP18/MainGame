package york.eng1.team18;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import york.eng1.team18.loader.AssetController;
import york.eng1.team18.views.*;

public class Orchestrator extends Game {

	//----------------------------------
	private boolean SPLASH_ENABLED = true;
	public boolean DEBUG_TABLES = false;
	//----------------------------------

	private SplashScreen splashScreen;
	private MenuScreen menuScreen;
	private LeaderBoardScreen leaderBoardScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	public AssetController assMan = new AssetController();

	public final static int MENU = 0;
	public final static int APPLICATION = 1;
	public final static int ENDGAME = 2;
	public final static int LEADERBOARD = 3;

	@Override
	public void create() {

		if (SPLASH_ENABLED) {
			splashScreen = new SplashScreen(this);
			this.setScreen(splashScreen);
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
				}
				this.setScreen(menuScreen);
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
			case LEADERBOARD:
				if(leaderBoardScreen == null) {
					leaderBoardScreen = new LeaderBoardScreen(this);
				}
				this.setScreen(leaderBoardScreen);
				break;
		}
	}

}
