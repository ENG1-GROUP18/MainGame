package york.eng1.team18;

import com.badlogic.gdx.Game;
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
	private GameOverScreen gameOverScreen;

	public final static int MENU = 0;
	public final static int APPLICATION = 1;
	public final static int ENDGAME = 2;
	public final static int LEADERBOARD = 3;
	public final static int GAMEOVER = 4;

	@Override
	public void create() {

		// Splash screen displays logo at the start of the game
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
			// Menu allows player to move through game screens
			case MENU:
				if(menuScreen == null) {
					menuScreen = new MenuScreen(this);
				}
				this.setScreen(menuScreen);
				break;
			// Application hosts the game
			case APPLICATION:
				if(mainScreen == null) {
					mainScreen = new MainScreen(this);
					this.setScreen(mainScreen);
				} else{
					mainScreen.dispose();
					mainScreen = new MainScreen(this);
					this.setScreen(mainScreen);
				}
				break;
			// Shown after the player has ended a game
			case ENDGAME:
				if(endScreen == null) {
					endScreen = new EndScreen(this);
					this.setScreen(endScreen);
				}
				break;
			// Displays the highest scores achieved by previous players
			case LEADERBOARD:
				if(leaderBoardScreen == null) {
					leaderBoardScreen = new LeaderBoardScreen(this);
				}
				this.setScreen(leaderBoardScreen);
				break;
			case GAMEOVER:
				if(gameOverScreen == null) {
					gameOverScreen = new GameOverScreen(this);
				}
				this.setScreen(gameOverScreen);
				break;
		}
	}

}
