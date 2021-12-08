package york.eng1.team18.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import york.eng1.team18.Orchestrator;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Graphics.Monitor primaryMonitor = Lwjgl3ApplicationConfiguration.getPrimaryMonitor();
		Graphics.DisplayMode desktopMode = Lwjgl3ApplicationConfiguration.getDisplayMode(primaryMonitor);

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Sea Dogs Game");
		config.setWindowSizeLimits(720, 480, 1080, 720);
		config.setWindowedMode(1080, 720);
		//config.setFullscreenMode(desktopMode);
		new Lwjgl3Application(new Orchestrator(), config);
	}
}
