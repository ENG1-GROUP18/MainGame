package york.eng1.team18.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import york.eng1.team18.Orchestrator;

public class DesktopLauncher {
	public static void main (String[] arg) {

		//----------------------------------
		boolean FULLSCREEN = false;
		//----------------------------------

		Graphics.Monitor primaryMonitor = Lwjgl3ApplicationConfiguration.getPrimaryMonitor();
		Graphics.DisplayMode desktopMode = Lwjgl3ApplicationConfiguration.getDisplayMode(primaryMonitor);
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Sea Dogs Game");
		config.setWindowIcon("images/appIcon.png");

		if(FULLSCREEN) {
			config.setFullscreenMode(desktopMode);
		} else {
			// config.setResizable(false);
			config.setWindowedMode(1280, 720);
			config.setWindowSizeLimits(720, 480, 1920,1080);
		}

		new Lwjgl3Application(new Orchestrator(), config);
	}
}
