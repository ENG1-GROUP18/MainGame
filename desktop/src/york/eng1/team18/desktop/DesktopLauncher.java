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
		System.setProperty("org.lwjgl.opengl.Display.enableOSXFullscreenModeAPI", "true");
		config.setTitle("Sea Dogs Game");
		config.setWindowIcon("images/appIcon.png");

		if(FULLSCREEN) {
			config.setFullscreenMode(desktopMode);
		} else {

			config.setWindowedMode(1280, 720);
			config.setWindowSizeLimits(720, 480, 3840,2160);
		}

		new Lwjgl3Application(new Orchestrator(), config);


	}
}
